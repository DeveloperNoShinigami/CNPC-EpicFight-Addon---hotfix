/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  net.minecraft.commands.arguments.blocks.BlockStateParser
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.nbt.Tag
 *  net.minecraft.util.datafix.DataFixers
 *  net.minecraft.util.datafix.fixes.References
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.schematics;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomBlocks;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.schematics.ISchematic;
import noppes.npcs.shared.common.CommonUtil;

public class SpongeSchem
implements ISchematic {
    public static final int latestDataVersion = 2586;
    public String name;
    public short width;
    public short height;
    public short length;
    public long timestamp = System.currentTimeMillis();
    public int[] data;
    public Map<Integer, BlockState> palette = new HashMap<Integer, BlockState>();
    public List<CompoundTag> tileData = new ArrayList<CompoundTag>();

    public SpongeSchem(String name) {
        this.name = name;
    }

    @Override
    public short getWidth() {
        return this.width;
    }

    @Override
    public short getHeight() {
        return this.height;
    }

    @Override
    public short getLength() {
        return this.length;
    }

    @Override
    public int getBlockEntityDimensions() {
        return this.tileData.size();
    }

    @Override
    public CompoundTag getBlockEntity(int i) {
        return this.tileData.get(i);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return this.getBlockState(this.xyzToIndex(x, y, z));
    }

    public int xyzToIndex(int x, int y, int z) {
        return (y * this.length + z) * this.width + x;
    }

    @Override
    public BlockState getBlockState(int i) {
        return this.palette.get(this.data[i]);
    }

    @Override
    public CompoundTag getNBT() {
        CompoundTag root = new CompoundTag();
        CompoundTag compound = new CompoundTag();
        root.m_128365_("", (Tag)compound);
        CompoundTag data = new CompoundTag();
        compound.m_128365_("Schematic", (Tag)data);
        data.m_128405_("Width", (int)this.width);
        data.m_128405_("Height", (int)this.height);
        data.m_128405_("Length", (int)this.length);
        data.m_128405_("Version", 3);
        data.m_128405_("DataVersion", 2586);
        CompoundTag metadata = new CompoundTag();
        metadata.m_128356_("Date", this.timestamp);
        data.m_128365_("Metadata", (Tag)metadata);
        CompoundTag blockData = new CompoundTag();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(this.data.length);
        for (int i = 0; i < this.data.length; ++i) {
            int blockId = this.data[i];
            while ((blockId & 0xFFFFFF80) != 0) {
                buffer.write(blockId & 0x7F | 0x80);
                blockId >>>= 7;
            }
            buffer.write(blockId);
        }
        blockData.m_128382_("Data", buffer.toByteArray());
        CompoundTag palletteNBT = new CompoundTag();
        for (Map.Entry<Integer, BlockState> en : this.palette.entrySet()) {
            palletteNBT.m_128405_(BlockStateParser.m_116769_((BlockState)en.getValue()), en.getKey().intValue());
        }
        blockData.m_128365_("Palette", (Tag)palletteNBT);
        ListTag tileNBT = new ListTag();
        for (CompoundTag tile : this.tileData) {
            tile = tile.m_6426_();
            tile.m_128385_("Pos", new int[]{tile.m_128451_("x"), tile.m_128451_("y"), tile.m_128451_("z")});
            tile.m_128359_("Id", tile.m_128461_("id"));
            tile.m_128473_("x");
            tile.m_128473_("y");
            tile.m_128473_("z");
            tile.m_128473_("id");
            tileNBT.add((Object)tile);
        }
        blockData.m_128365_("BlockEntities", (Tag)tileNBT);
        data.m_128365_("Blocks", (Tag)blockData);
        return root;
    }

    public void load(CompoundTag compound) {
        int version;
        if (compound.m_128440_() == 1) {
            compound = compound.m_128469_("").m_128469_("Schematic");
        }
        this.width = compound.m_128448_("Width");
        this.height = compound.m_128448_("Height");
        this.length = compound.m_128448_("Length");
        CompoundTag metadata = compound.m_128469_("Metadata");
        this.timestamp = 0L;
        if (!metadata.m_128456_()) {
            this.timestamp = metadata.m_128454_("Date");
        }
        int dataVersion = 1631;
        if (compound.m_128441_("DataVersion")) {
            dataVersion = compound.m_128451_("DataVersion");
            if (dataVersion > 2586) {
                // empty if block
            }
            if (dataVersion < 2586) {
                // empty if block
            }
        }
        if ((version = compound.m_128451_("Version")) < 3) {
            this.palette = this.readPalette(compound.m_128469_("Palette"), dataVersion);
            ListTag tileEntities = compound.m_128437_("BlockEntities", 10);
            if (tileEntities.isEmpty()) {
                tileEntities = compound.m_128437_("TileEntities", 10);
            }
            this.tileData = this.readTileData(tileEntities, dataVersion);
            this.data = this.readBlockData(compound.m_128463_("BlockData"));
        } else {
            CompoundTag blocks = compound.m_128469_("Blocks");
            this.palette = this.readPalette(blocks.m_128469_("Palette"), dataVersion);
            this.tileData = this.readTileData(blocks.m_128437_("BlockEntities", 10), dataVersion);
            this.data = this.readBlockData(blocks.m_128463_("Data"));
        }
    }

    private int[] readBlockData(byte[] bytes) {
        int[] data = new int[this.width * this.length * this.height];
        int index = 0;
        int i = 0;
        while (i < bytes.length) {
            int value = 0;
            int varintLength = 0;
            while (true) {
                value |= (bytes[i] & 0x7F) << varintLength++ * 7;
                if (varintLength > 5) {
                    throw new CustomNPCsException("VarInt too big (probably corrupted data)", new Object[0]);
                }
                if ((bytes[i] & 0x80) != 128) {
                    ++i;
                    break;
                }
                ++i;
            }
            data[index] = value;
            ++index;
        }
        return data;
    }

    private Map<Integer, BlockState> readPalette(CompoundTag comp, int dataVersion) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        HashMap<Integer, BlockState> palette = new HashMap<Integer, BlockState>();
        for (String blockState : comp.m_128431_()) {
            int id = comp.m_128451_(blockState);
            if (dataVersion < 2586) {
                CompoundTag stateNBT = SpongeSchem.stateToNBT(blockState);
                Dynamic dynamic = new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)stateNBT);
                stateNBT = (CompoundTag)DataFixers.m_14512_().update(References.f_16783_, dynamic, dataVersion, 2586).getValue();
                blockState = this.nbtToState(stateNBT);
            }
            map.put(blockState, id);
        }
        for (Block block : ForgeRegistries.BLOCKS) {
            block.m_49965_().m_61056_().forEach(state -> {
                String name = BlockStateParser.m_116769_((BlockState)state);
                if (map.containsKey(name)) {
                    int id = (Integer)map.remove(name);
                    palette.put(id, (BlockState)state);
                }
            });
        }
        Iterator iterator = map.values().iterator();
        while (iterator.hasNext()) {
            int id = (Integer)iterator.next();
            palette.put(id, Blocks.f_50016_.m_49966_());
        }
        return palette;
    }

    private List<CompoundTag> readTileData(ListTag list, int dataVersion) {
        ArrayList<CompoundTag> tileData = new ArrayList<CompoundTag>();
        if (list.isEmpty()) {
            return tileData;
        }
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag data = list.m_128728_(i);
            int[] posArr = data.m_128465_("Pos");
            BlockPos pos = new BlockPos(posArr[0], posArr[1], posArr[2]);
            data.m_128405_("x", pos.m_123341_());
            data.m_128405_("y", pos.m_123342_());
            data.m_128405_("z", pos.m_123343_());
            data.m_128365_("id", data.m_128423_("Id"));
            data.m_128473_("Id");
            data.m_128473_("Pos");
            if (dataVersion < 2586) {
                Dynamic dynamic = new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)data);
                data = (CompoundTag)DataFixers.m_14512_().update(References.f_16781_, dynamic, dataVersion, 2586).getValue();
            } else {
                data = data.m_6426_();
            }
            tileData.add(data);
        }
        return tileData;
    }

    private String nbtToState(CompoundTag tagCompound) {
        StringBuilder sb = new StringBuilder();
        sb.append(tagCompound.m_128461_("Name"));
        if (tagCompound.m_128425_("Properties", 10)) {
            sb.append('[');
            CompoundTag props = tagCompound.m_128469_("Properties");
            sb.append(props.m_128431_().stream().map(k -> k + "=" + props.m_128461_(k).replace("\"", "")).collect(Collectors.joining(",")));
            sb.append(']');
        }
        return sb.toString();
    }

    private static CompoundTag stateToNBT(String blockState) {
        int propIdx = blockState.indexOf(91);
        CompoundTag tag = new CompoundTag();
        if (propIdx < 0) {
            tag.m_128359_("Name", blockState);
        } else {
            String[] propArr;
            tag.m_128359_("Name", blockState.substring(0, propIdx));
            CompoundTag propTag = new CompoundTag();
            String props = blockState.substring(propIdx + 1, blockState.length() - 1);
            for (String pair : propArr = props.split(",")) {
                String[] split = pair.split("=");
                propTag.m_128359_(split[0], split[1]);
            }
            tag.m_128365_("Properties", (Tag)propTag);
        }
        return tag;
    }

    public static SpongeSchem Create(Level level, String name, BlockPos pos, short height, short width, short length) {
        SpongeSchem schema = new SpongeSchem(name);
        schema.height = height;
        schema.width = width;
        schema.length = length;
        int size = height * width * length;
        CommonUtil.NotifyOPs(level.m_7654_(), "Creating schematic at: " + pos + " might lag slightly", new Object[0]);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        schema.data = new int[size];
        int uniqueBlockId = 0;
        for (int i = 0; i < size; ++i) {
            int x = i % width;
            int z = (i - x) / width % length;
            int y = ((i - x) / width - z) / length;
            BlockState state = level.m_8055_(pos.m_7918_(x, y, z));
            if (state.m_60734_() == CustomBlocks.copy) continue;
            String stateName = BlockStateParser.m_116769_((BlockState)state);
            Integer blockId = (Integer)map.get(stateName);
            if (!map.containsKey(stateName)) {
                blockId = uniqueBlockId++;
                map.put(stateName, blockId);
            }
            schema.palette.put(blockId, state);
            schema.data[i] = blockId;
            if (!(state.m_60734_() instanceof EntityBlock)) continue;
            BlockEntity tile = level.m_7702_(pos.m_7918_(x, y, z));
            CompoundTag compound = tile.m_187480_();
            compound.m_128405_("x", x);
            compound.m_128405_("y", y);
            compound.m_128405_("z", z);
            schema.tileData.add(compound);
        }
        return schema;
    }
}

