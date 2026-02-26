/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 */
package noppes.npcs.blocks;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.blocks.BlockInterface;
import noppes.npcs.blocks.tiles.TileRedstoneBlock;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.packets.server.SPacketGuiOpen;

public class BlockNpcRedstone
extends BlockInterface {
    public static final BooleanProperty ACTIVE = BooleanProperty.m_61465_((String)"active");

    public BlockNpcRedstone() {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50069_).m_60953_(state -> 12).m_60913_(50.0f, 2000.0f));
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (level.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        ItemStack currentItem = player.m_150109_().m_36056_();
        if (currentItem != null && currentItem.m_41720_() == CustomItems.wand && CustomNpcsPermissions.hasPermission((ServerPlayer)player, CustomNpcsPermissions.EDIT_BLOCKS)) {
            SPacketGuiOpen.sendOpenGui(player, EnumGuiType.RedstoneBlock, null, pos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public void m_6807_(BlockState state, Level par1Level, BlockPos pos, BlockState stateNew, boolean bo) {
        par1Level.m_46672_(pos, (Block)this);
        par1Level.m_46672_(pos.m_7495_(), (Block)this);
        par1Level.m_46672_(pos.m_7494_(), (Block)this);
        par1Level.m_46672_(pos.m_122024_(), (Block)this);
        par1Level.m_46672_(pos.m_122029_(), (Block)this);
        par1Level.m_46672_(pos.m_122019_(), (Block)this);
        par1Level.m_46672_(pos.m_122012_(), (Block)this);
    }

    public void m_6402_(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack item) {
        if (!level.f_46443_ && entity instanceof Player) {
            SPacketGuiOpen.sendOpenGui((Player)entity, EnumGuiType.RedstoneBlock, null, pos);
        }
    }

    public void m_6810_(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        this.m_6807_(state, level, pos, state, isMoving);
    }

    public int m_6378_(BlockState state, BlockGetter worldIn, BlockPos pos, Direction side) {
        return this.isActivated(state);
    }

    public int m_6376_(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
        return this.isActivated(state);
    }

    public boolean m_7899_(BlockState state) {
        return true;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{ACTIVE});
    }

    public int isActivated(BlockState state) {
        return (Boolean)state.m_61143_((Property)ACTIVE) != false ? 15 : 0;
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return new TileRedstoneBlock(pos, state);
    }

    public RenderShape m_7514_(BlockState state) {
        return RenderShape.MODEL;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> type) {
        return BlockNpcRedstone.m_152132_(type, CustomBlocks.tile_redstoneblock, TileRedstoneBlock::tick);
    }
}

