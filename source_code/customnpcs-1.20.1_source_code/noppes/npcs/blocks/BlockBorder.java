/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
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

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
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
import noppes.npcs.blocks.tiles.TileBorder;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.packets.server.SPacketGuiOpen;

public class BlockBorder
extends BlockInterface {
    public static final IntegerProperty ROTATION = IntegerProperty.m_61631_((String)"rotation", (int)0, (int)3);

    public BlockBorder() {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50375_).m_60918_(SoundType.f_56742_));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{ROTATION});
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        ItemStack currentItem = player.m_150109_().m_36056_();
        if (!level.f_46443_ && currentItem.m_41720_() == CustomItems.wand) {
            SPacketGuiOpen.sendOpenGui(player, EnumGuiType.Border, null, pos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public BlockState m_5573_(BlockPlaceContext context) {
        if (context.m_43723_() != null) {
            int l = Mth.m_14107_((double)((double)(context.m_43723_().m_146908_() * 4.0f / 360.0f) + 0.5)) & 3;
            return (BlockState)this.m_49966_().m_61124_((Property)ROTATION, (Comparable)Integer.valueOf(l %= 4));
        }
        return super.m_5573_(context);
    }

    public void m_6402_(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack item) {
        TileBorder tile = (TileBorder)level.m_7702_(pos);
        TileBorder adjacent = this.getTile(level, pos.m_122024_());
        if (adjacent == null) {
            adjacent = this.getTile(level, pos.m_122019_());
        }
        if (adjacent == null) {
            adjacent = this.getTile(level, pos.m_122012_());
        }
        if (adjacent == null) {
            adjacent = this.getTile(level, pos.m_122029_());
        }
        if (adjacent != null) {
            CompoundTag compound = new CompoundTag();
            adjacent.writeExtraNBT(compound);
            tile.readExtraNBT(compound);
        }
        tile.rotation = (Integer)state.m_61143_((Property)ROTATION);
        if (!level.f_46443_ && entity instanceof Player) {
            SPacketGuiOpen.sendOpenGui((Player)entity, EnumGuiType.Border, null, pos);
        }
    }

    private TileBorder getTile(Level level, BlockPos pos) {
        BlockEntity tile = level.m_7702_(pos);
        if (tile != null && tile instanceof TileBorder) {
            return (TileBorder)tile;
        }
        return null;
    }

    public RenderShape m_7514_(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return new TileBorder(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> type) {
        return BlockBorder.m_152132_(type, CustomBlocks.tile_border, TileBorder::tick);
    }
}

