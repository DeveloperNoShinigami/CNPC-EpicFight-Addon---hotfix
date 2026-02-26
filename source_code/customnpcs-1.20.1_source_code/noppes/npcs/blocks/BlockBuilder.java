/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 */
package noppes.npcs.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.BlockInterface;
import noppes.npcs.blocks.tiles.TileBuilder;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.packets.server.SPacketGuiOpen;

public class BlockBuilder
extends BlockInterface {
    public static final IntegerProperty ROTATION = IntegerProperty.m_61631_((String)"rotation", (int)0, (int)3);

    public BlockBuilder() {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50375_).m_60918_(SoundType.f_56742_));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{ROTATION});
    }

    public RenderShape m_7514_(BlockState state) {
        return RenderShape.MODEL;
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (level.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        ItemStack currentItem = player.m_150109_().m_36056_();
        if (currentItem.m_41720_() == CustomItems.wand || currentItem.m_41720_() == CustomBlocks.builder_item) {
            SPacketGuiOpen.sendOpenGui(player, EnumGuiType.BuilderBlock, null, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        int var6 = Mth.m_14107_((double)((double)(context.m_43723_().m_146908_() / 90.0f) + 0.5)) & 3;
        if (!context.m_43725_().f_46443_) {
            SPacketGuiOpen.sendOpenGui(context.m_43723_(), EnumGuiType.BuilderBlock, null, context.m_8083_());
        }
        return (BlockState)this.m_49966_().m_61124_((Property)ROTATION, (Comparable)Integer.valueOf(var6));
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return new TileBuilder(pos, state);
    }

    public void m_6810_(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (TileBuilder.DrawPos != null && TileBuilder.DrawPos.equals((Object)pos)) {
            TileBuilder.SetDrawPos(null);
        }
        super.m_6810_(state, level, pos, newState, isMoving);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> type) {
        return BlockBuilder.m_152132_(type, CustomBlocks.tile_builder, TileBuilder::tick);
    }
}

