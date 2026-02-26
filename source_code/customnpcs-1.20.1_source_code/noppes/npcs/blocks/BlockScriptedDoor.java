/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.DoubleBlockHalf
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.ForgeHooks
 */
package noppes.npcs.blocks;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.EventHooks;
import noppes.npcs.blocks.BlockNpcDoorInterface;
import noppes.npcs.blocks.tiles.TileScriptedDoor;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.packets.server.SPacketGuiOpen;

public class BlockScriptedDoor
extends BlockNpcDoorInterface {
    public BlockScriptedDoor() {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50166_).m_60913_(5.0f, 10.0f));
    }

    public ItemStack m_7397_(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack((ItemLike)CustomBlocks.scripted_door_item);
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return new TileScriptedDoor(pos, state);
    }

    public RenderShape m_7514_(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        BlockState iblockstate1;
        if (level.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        BlockPos blockpos1 = state.m_61143_((Property)f_52730_) == DoubleBlockHalf.LOWER ? pos : pos.m_7495_();
        BlockState blockState = iblockstate1 = pos.equals((Object)blockpos1) ? state : level.m_8055_(blockpos1);
        if (iblockstate1.m_60734_() != this) {
            return InteractionResult.FAIL;
        }
        ItemStack currentItem = player.m_150109_().m_36056_();
        if (currentItem != null && (currentItem.m_41720_() == CustomItems.wand || currentItem.m_41720_() == CustomItems.scripter || currentItem.m_41720_() == CustomBlocks.scripted_door_item)) {
            PlayerData data = PlayerData.get(player);
            data.scriptBlockPos = blockpos1;
            SPacketGuiOpen.sendOpenGui(player, EnumGuiType.ScriptDoor, null, blockpos1);
            return InteractionResult.SUCCESS;
        }
        TileScriptedDoor tile = (TileScriptedDoor)level.m_7702_(blockpos1);
        Vec3 vec = ray.m_82450_();
        float x = (float)(vec.f_82479_ - (double)pos.m_123341_());
        float y = (float)(vec.f_82480_ - (double)pos.m_123342_());
        float z = (float)(vec.f_82481_ - (double)pos.m_123343_());
        if (EventHooks.onScriptBlockInteract(tile, player, ray.m_82434_().m_122411_(), x, y, z)) {
            return InteractionResult.FAIL;
        }
        this.m_153165_((Entity)player, level, iblockstate1, blockpos1, ((Boolean)iblockstate1.m_61143_((Property)DoorBlock.f_52727_)).equals(false));
        return InteractionResult.SUCCESS;
    }

    public void m_6861_(BlockState state, Level worldIn, BlockPos pos, Block neighborBlock, BlockPos pos2, boolean isMoving) {
        if (state.m_61143_((Property)f_52730_) == DoubleBlockHalf.UPPER) {
            BlockPos blockpos1 = pos.m_7495_();
            BlockState iblockstate1 = worldIn.m_8055_(blockpos1);
            if (iblockstate1.m_60734_() != this) {
                worldIn.m_7471_(pos, false);
            } else if (neighborBlock != this) {
                this.m_6861_(iblockstate1, worldIn, blockpos1, neighborBlock, blockpos1, isMoving);
            }
        } else {
            BlockPos blockpos2 = pos.m_7494_();
            BlockState iblockstate2 = worldIn.m_8055_(blockpos2);
            if (iblockstate2.m_60734_() != this) {
                worldIn.m_7471_(pos, false);
            } else {
                boolean flag;
                TileScriptedDoor tile = (TileScriptedDoor)worldIn.m_7702_(pos);
                if (!worldIn.f_46443_) {
                    EventHooks.onScriptBlockNeighborChanged(tile, pos2);
                }
                boolean bl = flag = worldIn.m_276867_(pos) || worldIn.m_276867_(blockpos2);
                if ((flag || neighborBlock.m_49966_().m_60803_()) && neighborBlock != this && flag != (Boolean)iblockstate2.m_61143_((Property)f_52729_)) {
                    worldIn.m_7731_(blockpos2, (BlockState)iblockstate2.m_61124_((Property)f_52729_, (Comparable)Boolean.valueOf(flag)), 2);
                    if (flag != (Boolean)state.m_61143_((Property)f_52727_)) {
                        this.m_153165_(null, worldIn, state, pos, flag);
                    }
                }
                int power = 0;
                for (Direction enumfacing : Direction.values()) {
                    int p = worldIn.m_277185_(pos.m_121945_(enumfacing), enumfacing);
                    if (p <= power) continue;
                    power = p;
                }
                tile.newPower = power;
            }
        }
    }

    public void m_153165_(Entity entity, Level worldIn, BlockState state, BlockPos pos, boolean open) {
        TileScriptedDoor tile = (TileScriptedDoor)worldIn.m_7702_(pos);
        if (EventHooks.onScriptBlockDoorToggle(tile)) {
            return;
        }
        super.m_153165_(entity, worldIn, state, pos, open);
    }

    public void m_6256_(BlockState state, Level level, BlockPos pos, Player playerIn) {
        BlockState iblockstate1;
        if (level.f_46443_) {
            return;
        }
        BlockPos blockpos1 = state.m_61143_((Property)f_52730_) == DoubleBlockHalf.LOWER ? pos : pos.m_7495_();
        BlockState blockState = iblockstate1 = pos.equals((Object)blockpos1) ? state : level.m_8055_(blockpos1);
        if (iblockstate1.m_60734_() != this) {
            return;
        }
        TileScriptedDoor tile = (TileScriptedDoor)level.m_7702_(blockpos1);
        EventHooks.onScriptBlockClicked(tile, playerIn);
    }

    @Override
    public void m_6810_(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockState iblockstate1;
        if (state.m_60734_() == newState.m_60734_()) {
            return;
        }
        BlockPos blockpos1 = state.m_61143_((Property)f_52730_) == DoubleBlockHalf.LOWER ? pos : pos.m_7495_();
        BlockState blockState = iblockstate1 = pos.equals((Object)blockpos1) ? state : level.m_8055_(blockpos1);
        if (!level.f_46443_ && iblockstate1.m_60734_() == this) {
            TileScriptedDoor tile = (TileScriptedDoor)level.m_7702_(pos);
            EventHooks.onScriptBlockBreak(tile);
        }
        super.m_6810_(state, level, pos, newState, isMoving);
    }

    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        TileScriptedDoor tile;
        if (!level.f_46443_ && EventHooks.onScriptBlockHarvest(tile = (TileScriptedDoor)level.m_7702_(pos), player)) {
            return false;
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    public void m_7892_(BlockState state, Level level, BlockPos pos, Entity entityIn) {
        if (level.f_46443_) {
            return;
        }
        TileScriptedDoor tile = (TileScriptedDoor)level.m_7702_(pos);
        EventHooks.onScriptBlockCollide(tile, entityIn);
    }

    public void m_5707_(Level level, BlockPos pos, BlockState state, Player player) {
        BlockState iblockstate1;
        BlockPos blockpos1 = state.m_61143_((Property)f_52730_) == DoubleBlockHalf.LOWER ? pos : pos.m_7495_();
        BlockState blockState = iblockstate1 = pos.equals((Object)blockpos1) ? state : level.m_8055_(blockpos1);
        if (player.m_150110_().f_35937_ && iblockstate1.m_61143_((Property)f_52730_) == DoubleBlockHalf.LOWER && iblockstate1.m_60734_() == this) {
            level.m_7471_(blockpos1, false);
        }
    }

    public float m_5880_(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        float f = ((TileScriptedDoor)level.m_7702_((BlockPos)pos)).blockHardness;
        if (f == -1.0f) {
            return 0.0f;
        }
        int i = ForgeHooks.isCorrectToolForDrops((BlockState)state, (Player)player) ? 30 : 100;
        return player.getDigSpeed(state, pos) / f / (float)i;
    }

    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return ((TileScriptedDoor)level.m_7702_((BlockPos)pos)).blockResistance;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> type) {
        return BlockScriptedDoor.createTickerHelper(type, CustomBlocks.tile_scripteddoor, TileScriptedDoor::tick);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? p_152135_ : null;
    }
}

