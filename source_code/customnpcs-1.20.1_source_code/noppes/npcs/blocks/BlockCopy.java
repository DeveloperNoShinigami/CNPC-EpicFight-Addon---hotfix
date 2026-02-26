/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 */
package noppes.npcs.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockInterface;
import noppes.npcs.blocks.tiles.TileCopy;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.packets.server.SPacketGuiOpen;

public class BlockCopy
extends BlockInterface {
    public BlockCopy() {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50375_).m_60918_(SoundType.f_56742_));
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (level.f_46443_) {
            return InteractionResult.PASS;
        }
        ItemStack currentItem = player.m_150109_().m_36056_();
        if (currentItem != null && currentItem.m_41720_() == CustomItems.wand) {
            SPacketGuiOpen.sendOpenGui(player, EnumGuiType.CopyBlock, null, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        if (!context.m_43725_().f_46443_) {
            SPacketGuiOpen.sendOpenGui(context.m_43723_(), EnumGuiType.CopyBlock, null, context.m_8083_());
        }
        return this.m_49966_();
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return new TileCopy(pos, state);
    }
}

