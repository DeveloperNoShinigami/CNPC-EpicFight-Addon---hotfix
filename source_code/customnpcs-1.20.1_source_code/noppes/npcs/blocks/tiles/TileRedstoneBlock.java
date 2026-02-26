/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 */
package noppes.npcs.blocks.tiles;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomNpcs;
import noppes.npcs.blocks.BlockNpcRedstone;
import noppes.npcs.blocks.tiles.TileNpcEntity;
import noppes.npcs.controllers.data.Availability;

public class TileRedstoneBlock
extends TileNpcEntity {
    public int onRange = 12;
    public int offRange = 20;
    public int onRangeX = 12;
    public int onRangeY = 12;
    public int onRangeZ = 12;
    public int offRangeX = 20;
    public int offRangeY = 20;
    public int offRangeZ = 20;
    public boolean isDetailed = false;
    public Availability availability = new Availability();
    public boolean isActivated = false;
    private int ticks = 10;

    public TileRedstoneBlock(BlockPos pos, BlockState state) {
        super(CustomBlocks.tile_redstoneblock, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileRedstoneBlock tile) {
        if (tile.f_58857_.f_46443_) {
            return;
        }
        --tile.ticks;
        if (tile.ticks > 0) {
            return;
        }
        tile.ticks = tile.onRange > 10 ? 20 : 10;
        Block block = state.m_60734_();
        if (block == null || !(block instanceof BlockNpcRedstone)) {
            return;
        }
        if (CustomNpcs.FreezeNPCs) {
            if (tile.isActivated) {
                tile.setActive(block, false);
            }
            return;
        }
        if (!tile.isActivated) {
            int z;
            int y;
            int x = tile.isDetailed ? tile.onRangeX : tile.onRange;
            List<Player> list = tile.getPlayerList(x, y = tile.isDetailed ? tile.onRangeY : tile.onRange, z = tile.isDetailed ? tile.onRangeZ : tile.onRange);
            if (list.isEmpty()) {
                return;
            }
            for (Player player : list) {
                if (!tile.availability.isAvailable(player)) continue;
                tile.setActive(block, true);
                return;
            }
        } else {
            int x = tile.isDetailed ? tile.offRangeX : tile.offRange;
            int y = tile.isDetailed ? tile.offRangeY : tile.offRange;
            int z = tile.isDetailed ? tile.offRangeZ : tile.offRange;
            List<Player> list = tile.getPlayerList(x, y, z);
            for (Player player : list) {
                if (!tile.availability.isAvailable(player)) continue;
                return;
            }
            tile.setActive(block, false);
        }
    }

    private void setActive(Block block, boolean bo) {
        this.isActivated = bo;
        BlockState state = (BlockState)block.m_49966_().m_61124_((Property)BlockNpcRedstone.ACTIVE, (Comparable)Boolean.valueOf(this.isActivated));
        this.f_58857_.m_7731_(this.f_58858_, state, 2);
        this.m_6596_();
        this.f_58857_.m_7260_(this.f_58858_, state, state, 3);
        block.m_6807_(state, this.f_58857_, this.f_58858_, state, false);
    }

    private List<Player> getPlayerList(int x, int y, int z) {
        return this.f_58857_.m_45976_(Player.class, new AABB((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_(), (double)(this.f_58858_.m_123341_() + 1), (double)(this.f_58858_.m_123342_() + 1), (double)(this.f_58858_.m_123343_() + 1)).m_82377_((double)x, (double)y, (double)z));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.onRange = compound.m_128451_("BlockOnRange");
        this.offRange = compound.m_128451_("BlockOffRange");
        this.isDetailed = compound.m_128471_("BlockIsDetailed");
        if (compound.m_128441_("BlockOnRangeX")) {
            this.isDetailed = true;
            this.onRangeX = compound.m_128451_("BlockOnRangeX");
            this.onRangeY = compound.m_128451_("BlockOnRangeY");
            this.onRangeZ = compound.m_128451_("BlockOnRangeZ");
            this.offRangeX = compound.m_128451_("BlockOffRangeX");
            this.offRangeY = compound.m_128451_("BlockOffRangeY");
            this.offRangeZ = compound.m_128451_("BlockOffRangeZ");
        }
        if (compound.m_128441_("BlockActivated")) {
            this.isActivated = compound.m_128471_("BlockActivated");
        }
        this.availability.load(compound);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        compound.m_128405_("BlockOnRange", this.onRange);
        compound.m_128405_("BlockOffRange", this.offRange);
        compound.m_128379_("BlockActivated", this.isActivated);
        compound.m_128379_("BlockIsDetailed", this.isDetailed);
        if (this.isDetailed) {
            compound.m_128405_("BlockOnRangeX", this.onRangeX);
            compound.m_128405_("BlockOnRangeY", this.onRangeY);
            compound.m_128405_("BlockOnRangeZ", this.onRangeZ);
            compound.m_128405_("BlockOffRangeX", this.offRangeX);
            compound.m_128405_("BlockOffRangeY", this.offRangeY);
            compound.m_128405_("BlockOffRangeZ", this.offRangeZ);
        }
        this.availability.save(compound);
        super.m_183515_(compound);
    }
}

