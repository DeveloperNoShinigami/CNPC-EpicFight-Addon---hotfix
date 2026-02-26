/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package noppes.npcs.blocks.tiles;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileNpcEntity
extends BlockEntity {
    public Map<String, Object> tempData = new HashMap<String, Object>();

    public TileNpcEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        CompoundTag extraData = compound.m_128469_("ExtraData");
        if (!extraData.m_128456_()) {
            this.getPersistentData().m_128365_("CustomNPCsData", (Tag)extraData);
        }
    }

    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
    }
}

