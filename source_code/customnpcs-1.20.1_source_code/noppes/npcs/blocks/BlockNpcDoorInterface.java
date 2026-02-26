/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockSetType
 *  net.minecraft.world.level.storage.loot.LootParams$Builder
 */
package noppes.npcs.blocks;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.storage.loot.LootParams;

public abstract class BlockNpcDoorInterface
extends DoorBlock
implements EntityBlock {
    public BlockNpcDoorInterface(BlockBehaviour.Properties properties) {
        super(properties, BlockSetType.f_271479_);
    }

    public void m_6810_(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.m_6810_(state, level, pos, newState, isMoving);
        level.m_46747_(pos);
    }

    public List<ItemStack> m_49635_(BlockState p_287732_, LootParams.Builder p_287596_) {
        return Collections.emptyList();
    }

    public void m_6240_(Level p_180657_1_, Player p_180657_2_, BlockPos p_180657_3_, BlockState p_180657_4_, @Nullable BlockEntity p_180657_5_, ItemStack p_180657_6_) {
        p_180657_2_.m_36246_(Stats.f_12949_.m_12902_((Object)this));
        p_180657_2_.m_36399_(0.005f);
        BlockNpcDoorInterface.m_49881_((BlockState)p_180657_4_, (Level)p_180657_1_, (BlockPos)p_180657_3_, (BlockEntity)p_180657_5_, (Entity)p_180657_2_, (ItemStack)p_180657_6_);
    }
}

