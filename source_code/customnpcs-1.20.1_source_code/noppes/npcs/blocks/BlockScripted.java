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
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.SpawnPlacements$Type
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Explosion
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.biome.Biome$Precipitation
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.level.storage.loot.LootParams$Builder
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.common.ForgeHooks
 */
package noppes.npcs.blocks;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.EventHooks;
import noppes.npcs.blocks.BlockInterface;
import noppes.npcs.blocks.tiles.TileScripted;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.packets.server.SPacketGuiOpen;

public class BlockScripted
extends BlockInterface {
    public static final VoxelShape AABB = Shapes.m_83064_((AABB)new AABB((double)0.001f, (double)0.001f, (double)0.001f, (double)0.998f, (double)0.998f, (double)0.998f));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;

    public BlockScripted() {
        super(BlockBehaviour.Properties.m_60926_((BlockBehaviour)Blocks.f_50069_).m_60918_(SoundType.f_56742_).m_60913_(5.0f, 10.0f));
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(false)));
    }

    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return new TileScripted(pos, state);
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    public VoxelShape m_5939_(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        if (tile != null && tile.isPassible) {
            return Shapes.m_83040_();
        }
        return AABB;
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (level.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        ItemStack currentItem = player.m_150109_().m_36056_();
        if (currentItem != null && (currentItem.m_41720_() == CustomItems.wand || currentItem.m_41720_() == CustomItems.scripter)) {
            PlayerData data = PlayerData.get(player);
            data.scriptBlockPos = pos;
            SPacketGuiOpen.sendOpenGui(player, EnumGuiType.ScriptBlock, null, pos);
            return InteractionResult.SUCCESS;
        }
        Vec3 vec = ray.m_82450_();
        float x = (float)(vec.f_82479_ - (double)pos.m_123341_());
        float y = (float)(vec.f_82480_ - (double)pos.m_123342_());
        float z = (float)(vec.f_82481_ - (double)pos.m_123343_());
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        return EventHooks.onScriptBlockInteract(tile, player, ray.m_82434_().m_122411_(), x, y, z) ? InteractionResult.FAIL : InteractionResult.SUCCESS;
    }

    public void m_6402_(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack item) {
        if (!level.f_46443_ && entity instanceof Player) {
            Player player = (Player)entity;
            PlayerData data = PlayerData.get(player);
            data.scriptBlockPos = pos;
            SPacketGuiOpen.sendOpenGui(player, EnumGuiType.ScriptBlock, null, pos);
        }
    }

    public void m_7892_(BlockState state, Level level, BlockPos pos, Entity entityIn) {
        if (level.f_46443_) {
            return;
        }
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        EventHooks.onScriptBlockCollide(tile, entityIn);
    }

    public void m_141997_(BlockState state, Level level, BlockPos pos, Biome.Precipitation type) {
        if (level.f_46443_ || type != Biome.Precipitation.RAIN) {
            return;
        }
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        EventHooks.onScriptBlockRainFill(tile);
    }

    public void m_142072_(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (level.f_46443_) {
            return;
        }
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        fallDistance = EventHooks.onScriptBlockFallenUpon(tile, entity, fallDistance);
        super.m_142072_(level, state, pos, entity, fallDistance);
    }

    public void m_6256_(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.f_46443_) {
            return;
        }
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        EventHooks.onScriptBlockClicked(tile, player);
    }

    public void m_6810_(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.f_46443_) {
            TileScripted tile = (TileScripted)level.m_7702_(pos);
            EventHooks.onScriptBlockBreak(tile);
        }
        super.m_6810_(state, level, pos, newState, isMoving);
    }

    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        TileScripted tile;
        if (!level.f_46443_ && EventHooks.onScriptBlockHarvest(tile = (TileScripted)level.m_7702_(pos), player)) {
            return false;
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    public List<ItemStack> m_49635_(BlockState p_287732_, LootParams.Builder p_287596_) {
        return Collections.emptyList();
    }

    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        TileScripted tile;
        if (!level.f_46443_ && EventHooks.onScriptBlockExploded(tile = (TileScripted)level.m_7702_(pos))) {
            return;
        }
        super.onBlockExploded(state, level, pos, explosion);
    }

    public void m_6861_(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos pos2, boolean isMoving) {
        if (level.f_46443_) {
            return;
        }
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        EventHooks.onScriptBlockNeighborChanged(tile, pos2);
        int power = 0;
        for (Direction enumfacing : Direction.values()) {
            int p = level.m_277185_(pos.m_121945_(enumfacing), enumfacing);
            if (p <= power) continue;
            power = p;
        }
        if (tile.prevPower != power && tile.powering <= 0) {
            tile.newPower = power;
        }
    }

    public boolean m_7899_(BlockState state) {
        return true;
    }

    public int m_6378_(BlockState state, BlockGetter worldIn, BlockPos pos, Direction side) {
        return this.m_6376_(state, worldIn, pos, side);
    }

    public int m_6376_(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        if (tile != null) {
            return tile.activePowering;
        }
        return 0;
    }

    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        if (tile != null) {
            return tile.isLadder;
        }
        return false;
    }

    public boolean isValidSpawn(BlockState state, BlockGetter level, BlockPos pos, SpawnPlacements.Type type, @Nullable EntityType<?> entityType) {
        return true;
    }

    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        if (tile == null) {
            return 0;
        }
        return tile.lightValue;
    }

    public boolean m_7357_(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        if (tile != null) {
            return tile.isPassible;
        }
        return false;
    }

    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return super.canEntityDestroy(state, level, pos, entity);
    }

    public float m_5880_(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        float f = -1.0f;
        if (tile != null) {
            f = tile.blockHardness;
        }
        if (f == -1.0f) {
            return 0.0f;
        }
        int i = ForgeHooks.isCorrectToolForDrops((BlockState)state, (Player)player) ? 30 : 100;
        return player.getDigSpeed(state, pos) / f / (float)i;
    }

    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        TileScripted tile = (TileScripted)level.m_7702_(pos);
        if (tile != null) {
            return tile.blockResistance;
        }
        return 0.0f;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> type) {
        return BlockScripted.m_152132_(type, CustomBlocks.tile_scripted, TileScripted::tick);
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54447_) {
        p_54447_.m_61104_(new Property[]{WATERLOGGED});
    }

    public FluidState m_5888_(BlockState p_221384_) {
        return (Boolean)p_221384_.m_61143_((Property)WATERLOGGED) != false ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_221384_);
    }

    public BlockState m_7417_(BlockState p_54440_, Direction p_54441_, BlockState p_54442_, LevelAccessor p_54443_, BlockPos p_54444_, BlockPos p_54445_) {
        if (((Boolean)p_54440_.m_61143_((Property)WATERLOGGED)).booleanValue()) {
            p_54443_.m_186469_(p_54444_, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)p_54443_));
        }
        return p_54440_;
    }
}

