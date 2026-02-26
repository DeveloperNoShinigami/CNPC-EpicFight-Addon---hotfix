/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.schematics;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomNpcs;
import noppes.npcs.schematics.ISchematic;

public class Schematic
implements ISchematic {
    private static final HashMap<String, BlockState> staticBlockIds = new HashMap();
    public String name;
    public short width;
    public short height;
    public short length;
    private ListTag entityList;
    public ListTag tileList;
    public short[] blockArray;
    public byte[] blockDataArray;
    public HashMap<String, BlockState> blockIds = staticBlockIds;

    private static <T extends Comparable<T>> BlockState setValue(BlockState state, Property<T> prop, String val) {
        Optional optional = prop.m_6215_(val);
        if (optional.isPresent()) {
            return (BlockState)state.m_61124_(prop, (Comparable)optional.get());
        }
        return state;
    }

    public Schematic(String name) {
        this.name = name;
    }

    public void load(CompoundTag compound) {
        this.width = compound.m_128448_("Width");
        this.height = compound.m_128448_("Height");
        this.length = compound.m_128448_("Length");
        byte[] addId = compound.m_128441_("AddBlocks") ? compound.m_128463_("AddBlocks") : new byte[]{};
        this.setBlockBytes(compound.m_128463_("Blocks"), addId);
        this.blockDataArray = compound.m_128463_("Data");
        this.entityList = compound.m_128437_("Entities", 10);
        this.tileList = compound.m_128437_("TileEntities", 10);
        if (compound.m_128425_("BlockIDs", 10)) {
            CompoundTag comp = compound.m_128469_("BlockIDs");
            this.blockIds = new HashMap();
            for (String idStr : comp.m_128431_()) {
                String key = comp.m_128461_(idStr);
                try {
                    int id = Integer.parseInt(idStr);
                    Block block = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(key));
                    if (block == null) continue;
                    this.blockIds.put(id + ":0", block.m_49966_());
                }
                catch (NumberFormatException e) {}
            }
        }
    }

    @Override
    public CompoundTag getNBT() {
        CompoundTag compound = new CompoundTag();
        compound.m_128376_("Width", this.width);
        compound.m_128376_("Height", this.height);
        compound.m_128376_("Length", this.length);
        byte[][] arr = this.getBlockBytes();
        compound.m_128382_("Blocks", arr[0]);
        if (arr.length > 1) {
            compound.m_128382_("AddBlocks", arr[1]);
        }
        compound.m_128382_("Data", this.blockDataArray);
        compound.m_128365_("TileEntities", (Tag)this.tileList);
        CompoundTag comp = new CompoundTag();
        for (Map.Entry<String, BlockState> entry : this.blockIds.entrySet()) {
            comp.m_128359_("" + Block.m_49956_((BlockState)entry.getValue()), ForgeRegistries.BLOCKS.getKey((Object)entry.getValue().m_60734_()).toString());
        }
        compound.m_128365_("BlockIDs", (Tag)comp);
        return compound;
    }

    public void setBlockBytes(byte[] blockId, byte[] addId) {
        this.blockArray = new short[blockId.length];
        for (int index = 0; index < blockId.length; ++index) {
            short id = (short)(blockId[index] & 0xFF);
            if (index >> 1 < addId.length) {
                id = (index & 1) == 0 ? (short)(id + (short)((addId[index >> 1] & 0xF) << 8)) : (short)(id + (short)((addId[index >> 1] & 0xF0) << 4));
            }
            this.blockArray[index] = id;
        }
    }

    public byte[][] getBlockBytes() {
        byte[] blocks = new byte[this.blockArray.length];
        byte[] addBlocks = null;
        for (int i = 0; i < blocks.length; ++i) {
            short id = this.blockArray[i];
            if (id > 255) {
                if (addBlocks == null) {
                    addBlocks = new byte[(blocks.length >> 1) + 1];
                }
                addBlocks[i >> 1] = (i & 1) == 0 ? (byte)(addBlocks[i >> 1] & 0xF0 | id >> 8 & 0xF) : (byte)(addBlocks[i >> 1] & 0xF | (id >> 8 & 0xF) << 4);
            }
            blocks[i] = (byte)id;
        }
        if (addBlocks == null) {
            return new byte[][]{blocks};
        }
        return new byte[][]{blocks, addBlocks};
    }

    public int xyzToIndex(int x, int y, int z) {
        return (y * this.length + z) * this.width + x;
    }

    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return this.getBlockState(this.xyzToIndex(x, y, z));
    }

    @Override
    public BlockState getBlockState(int i) {
        BlockState b = this.blockIds.get(this.blockArray[i] + ":" + this.blockDataArray[i]);
        if (b == null) {
            return Blocks.f_50016_.m_49966_();
        }
        return b;
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
        if (this.tileList == null) {
            return 0;
        }
        return this.tileList.size();
    }

    @Override
    public CompoundTag getBlockEntity(int i) {
        return this.tileList.m_128728_(i);
    }

    @Override
    public String getName() {
        return this.name;
    }

    static {
        ResourceLocation resource = new ResourceLocation("customnpcs", "legacy_blockids.json");
        Resource ir = CustomNpcs.Server.getServerResources().f_206584_().m_213713_(resource).orElse(null);
        if (ir != null) {
            try {
                InputStream stream = ir.m_215507_();
                InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
                JsonObject result = ((JsonObject)new Gson().fromJson((Reader)reader, JsonObject.class)).getAsJsonObject("blocks");
                for (Map.Entry entry : result.entrySet()) {
                    Block block;
                    String val = ((JsonElement)entry.getValue()).getAsString();
                    String[] properties = null;
                    if (val.indexOf(91) > 0) {
                        properties = val.substring(val.indexOf(91) + 1, val.length() - 1).split(",");
                        val = val.substring(0, val.indexOf(91));
                    }
                    if ((block = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(val))) == null) continue;
                    BlockState state = block.m_49966_();
                    if (properties != null) {
                        for (Property prop : state.m_61147_()) {
                            for (String r : properties) {
                                if (!r.startsWith(prop.m_61708_() + "=")) continue;
                                state = Schematic.setValue(state, prop, r.split("=")[1]);
                            }
                        }
                    }
                    staticBlockIds.put((String)entry.getKey(), state);
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }
}

