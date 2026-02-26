/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.EmptyBlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.blocks.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.CustomBlocks;
import noppes.npcs.NBTTags;
import noppes.npcs.controllers.SchematicController;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.controllers.data.BlockData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobBuilder;
import noppes.npcs.schematics.SchematicWrapper;

public class TileBuilder
extends BlockEntity {
    private SchematicWrapper schematic = null;
    public int rotation = 0;
    public int yOffest = 0;
    public boolean enabled = false;
    public boolean started = false;
    public boolean finished = false;
    public Availability availability = new Availability();
    private Stack<Integer> positions = new Stack();
    private Stack<Integer> positionsSecond = new Stack();
    public static BlockPos DrawPos = null;
    public static boolean Compiled = false;
    private int ticks = 20;

    public TileBuilder(BlockPos pos, BlockState state) {
        super(CustomBlocks.tile_builder, pos, state);
    }

    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        if (compound.m_128441_("SchematicName")) {
            this.schematic = SchematicController.Instance.load(compound.m_128461_("SchematicName"));
        }
        Stack<Integer> positions = new Stack<Integer>();
        positions.addAll(NBTTags.getIntegerList(compound.m_128437_("Positions", 10)));
        this.positions = positions;
        positions = new Stack();
        positions.addAll(NBTTags.getIntegerList(compound.m_128437_("PositionsSecond", 10)));
        this.positionsSecond = positions;
        this.readPartNBT(compound);
    }

    public void readPartNBT(CompoundTag compound) {
        this.rotation = compound.m_128451_("Rotation");
        this.yOffest = compound.m_128451_("YOffset");
        this.enabled = compound.m_128471_("Enabled");
        this.started = compound.m_128471_("Started");
        this.finished = compound.m_128471_("Finished");
        this.availability.load(compound.m_128469_("Availability"));
    }

    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        if (this.schematic != null) {
            compound.m_128359_("SchematicName", this.schematic.schema.getName());
        }
        compound.m_128365_("Positions", (Tag)NBTTags.nbtIntegerCollection(new ArrayList<Integer>(this.positions)));
        compound.m_128365_("PositionsSecond", (Tag)NBTTags.nbtIntegerCollection(new ArrayList<Integer>(this.positionsSecond)));
        this.writePartNBT(compound);
    }

    public CompoundTag writePartNBT(CompoundTag compound) {
        compound.m_128405_("Rotation", this.rotation);
        compound.m_128405_("YOffset", this.yOffest);
        compound.m_128379_("Enabled", this.enabled);
        compound.m_128379_("Started", this.started);
        compound.m_128379_("Finished", this.finished);
        compound.m_128365_("Availability", (Tag)this.availability.save(new CompoundTag()));
        return compound;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void setDrawSchematic(SchematicWrapper schematics) {
        this.schematic = schematics;
    }

    public void setSchematic(SchematicWrapper schematics) {
        this.schematic = schematics;
        if (schematics == null) {
            this.positions.clear();
            this.positionsSecond.clear();
            return;
        }
        Stack<Integer> positions = new Stack<Integer>();
        for (int y = 0; y < schematics.schema.getHeight(); ++y) {
            int x;
            int z;
            for (z = 0; z < schematics.schema.getLength() / 2; ++z) {
                for (x = 0; x < schematics.schema.getWidth() / 2; ++x) {
                    positions.add(0, this.xyzToIndex(x, y, z));
                }
            }
            for (z = 0; z < schematics.schema.getLength() / 2; ++z) {
                for (x = schematics.schema.getWidth() / 2; x < schematics.schema.getWidth(); ++x) {
                    positions.add(0, this.xyzToIndex(x, y, z));
                }
            }
            for (z = schematics.schema.getLength() / 2; z < schematics.schema.getLength(); ++z) {
                for (x = 0; x < schematics.schema.getWidth() / 2; ++x) {
                    positions.add(0, this.xyzToIndex(x, y, z));
                }
            }
            for (z = schematics.schema.getLength() / 2; z < schematics.schema.getLength(); ++z) {
                for (x = schematics.schema.getWidth() / 2; x < schematics.schema.getWidth(); ++x) {
                    positions.add(0, this.xyzToIndex(x, y, z));
                }
            }
        }
        this.positions = positions;
        this.positionsSecond.clear();
    }

    public int xyzToIndex(int x, int y, int z) {
        return (y * this.schematic.schema.getLength() + z) * this.schematic.schema.getWidth() + x;
    }

    public SchematicWrapper getSchematic() {
        return this.schematic;
    }

    public boolean hasSchematic() {
        return this.schematic != null;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileBuilder tile) {
        if (level.f_46443_ || !tile.hasSchematic() || tile.finished) {
            return;
        }
        --tile.ticks;
        if (tile.ticks > 0) {
            return;
        }
        tile.ticks = 200;
        if (tile.positions.isEmpty() && tile.positionsSecond.isEmpty()) {
            tile.finished = true;
            return;
        }
        if (!tile.started) {
            for (Player player : tile.getPlayerList()) {
                if (!tile.availability.isAvailable(player)) continue;
                tile.started = true;
                break;
            }
            if (!tile.started) {
                return;
            }
        }
        List list = level.m_45976_(EntityNPCInterface.class, new AABB(pos, pos).m_82377_(32.0, 32.0, 32.0));
        for (EntityNPCInterface npc : list) {
            if (npc.job.getType() != 10) continue;
            JobBuilder job = (JobBuilder)npc.job;
            if (job.build != null) continue;
            job.build = tile;
        }
    }

    private List<Player> getPlayerList() {
        return this.f_58857_.m_45976_(Player.class, new AABB((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_(), (double)(this.f_58858_.m_123341_() + 1), (double)(this.f_58858_.m_123342_() + 1), (double)(this.f_58858_.m_123343_() + 1)).m_82377_(10.0, 10.0, 10.0));
    }

    public Stack<BlockData> getBlock() {
        if (!this.enabled || this.finished || !this.hasSchematic()) {
            return null;
        }
        boolean bo = this.positions.isEmpty();
        Stack<BlockData> list = new Stack<BlockData>();
        int size = this.schematic.schema.getWidth() * this.schematic.schema.getLength() / 4;
        if (size > 30) {
            size = 30;
        }
        for (int i = 0; i < size; ++i) {
            if (this.positions.isEmpty() && !bo || this.positionsSecond.isEmpty() && bo) {
                return list;
            }
            int pos = bo ? this.positionsSecond.pop() : this.positions.pop();
            if (pos >= this.schematic.size) continue;
            int x = pos % this.schematic.schema.getWidth();
            int z = (pos - x) / this.schematic.schema.getWidth() % this.schematic.schema.getLength();
            int y = ((pos - x) / this.schematic.schema.getWidth() - z) / this.schematic.schema.getLength();
            BlockState state = this.schematic.schema.getBlockState(x, y, z);
            if (!state.m_60838_((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.f_121853_) && !bo && state.m_60734_() != Blocks.f_50016_) {
                this.positionsSecond.add(0, pos);
                continue;
            }
            BlockPos blockPos = this.m_58899_().m_7918_(1, this.yOffest, 1).m_121955_((Vec3i)this.schematic.rotatePos(x, y, z, this.rotation));
            BlockState original = this.f_58857_.m_8055_(blockPos);
            if (Block.m_49956_((BlockState)state) == Block.m_49956_((BlockState)original)) continue;
            state = this.schematic.rotationState(state, this.rotation);
            CompoundTag tile = null;
            if (state.m_60734_() instanceof EntityBlock) {
                tile = this.schematic.getBlockEntity(x, y, z, blockPos);
            }
            list.add(0, new BlockData(blockPos, state, tile));
        }
        return list;
    }

    public AABB getRenderBoundingBox() {
        if (this.schematic == null) {
            return super.getRenderBoundingBox();
        }
        return new AABB((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_(), (double)(this.f_58858_.m_123341_() + this.schematic.schema.getWidth() + 1), (double)(this.f_58858_.m_123342_() + this.schematic.schema.getHeight() + 1), (double)(this.f_58858_.m_123343_() + this.schematic.schema.getLength() + 1));
    }

    public static void SetDrawPos(BlockPos pos) {
        DrawPos = pos;
        Compiled = false;
    }
}

