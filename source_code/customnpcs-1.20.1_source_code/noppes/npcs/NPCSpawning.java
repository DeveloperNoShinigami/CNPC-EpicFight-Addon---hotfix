/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ChunkHolder
 *  net.minecraft.server.level.ChunkMap
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.AbortableIterationConsumer$Continuation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.entity.SpawnPlacements$Type
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.LightLayer
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.entity.EntitySectionStorage
 *  net.minecraft.world.level.entity.EntityTypeTest
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.event.ForgeEventFactory
 */
package noppes.npcs;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;
import noppes.npcs.CustomEntities;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.SpawnController;
import noppes.npcs.controllers.data.SpawnData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.ChunkMapMixin;
import noppes.npcs.mixin.PersistentEntitySectionManagerMixin;
import noppes.npcs.mixin.ServerLevelMixin;

public class NPCSpawning {
    public static void findChunksForSpawning(ServerLevel level) {
        ChunkHolder chunkHolder;
        LevelChunk levelchunk;
        if (SpawnController.instance.data.isEmpty() || level.m_46467_() % 400L != 0L) {
            return;
        }
        EntitySectionStorage sectionManager = ((PersistentEntitySectionManagerMixin)((ServerLevelMixin)level).entityManager()).sectionStorage();
        ChunkMap chunkManager = level.m_7726_().f_8325_;
        ArrayList list = new ArrayList(((ChunkMapMixin)chunkManager).visibleChunkMap().values());
        Collections.shuffle(list);
        Iterator iterator = list.iterator();
        while (iterator.hasNext() && (levelchunk = (chunkHolder = (ChunkHolder)iterator.next()).m_140085_()) != null) {
            ChunkPos pos = levelchunk.m_7697_();
            Biome biome = (Biome)level.m_204166_(pos.m_45615_()).m_203334_();
            if (!SpawnController.instance.hasSpawnList(level.m_9598_().m_175515_(Registries.f_256952_).m_7981_((Object)biome))) continue;
            AABB bb = new AABB((double)pos.m_45604_(), 0.0, (double)pos.m_45605_(), (double)pos.m_45608_(), (double)level.m_151558_(), (double)pos.m_45609_());
            ArrayList entities = Lists.newArrayList();
            sectionManager.m_261191_((EntityTypeTest)EntityType.f_20532_, bb.m_82400_(4.0), e -> {
                entities.add(e);
                return AbortableIterationConsumer.Continuation.CONTINUE;
            });
            if (!entities.isEmpty()) continue;
            sectionManager.m_261191_(CustomEntities.entityCustomNpc, bb, e -> {
                entities.add(e);
                return AbortableIterationConsumer.Continuation.CONTINUE;
            });
            if (entities.size() >= CustomNpcs.NpcNaturalSpawningChunkLimit) continue;
            NPCSpawning.spawnChunk(level, levelchunk);
        }
    }

    private static void spawnChunk(ServerLevel level, LevelChunk chunk) {
        BlockPos chunkposition = NPCSpawning.getChunk((Level)level, chunk);
        int j1 = chunkposition.m_123341_();
        int k1 = chunkposition.m_123342_();
        int l1 = chunkposition.m_123343_();
        for (int i = 0; i < 3; ++i) {
            int x = j1;
            int y = k1;
            int z = l1;
            int b1 = 6;
            BlockPos pos = new BlockPos(x += level.f_46441_.m_188503_(b1) - level.f_46441_.m_188503_(b1), y, z += level.f_46441_.m_188503_(b1) - level.f_46441_.m_188503_(b1));
            ResourceLocation name = level.m_9598_().m_175515_(Registries.f_256952_).m_7981_((Object)((Biome)level.m_204166_(pos).m_203334_()));
            SpawnData data = SpawnController.instance.getRandomSpawnData(name);
            if (data == null || data.data.isEmpty() || !NPCSpawning.canCreatureTypeSpawnAtLocation(data, (LevelReader)level, pos)) continue;
            NPCSpawning.spawnData(data, (ServerLevelAccessor)level, pos);
        }
    }

    public static int countNPCs(ServerLevel level) {
        int count = 0;
        Iterable list = level.m_8583_();
        for (Entity entity : list) {
            if (!(entity instanceof EntityNPCInterface)) continue;
            ++count;
        }
        return count;
    }

    private static BlockPos getChunk(Level level, LevelChunk chunk) {
        ChunkPos chunkpos = chunk.m_7697_();
        int i = chunkpos.m_45604_() + level.f_46441_.m_188503_(16);
        int j = chunkpos.m_45605_() + level.f_46441_.m_188503_(16);
        int k = chunk.m_5885_(Heightmap.Types.WORLD_SURFACE, i, j) + 1;
        int l = level.f_46441_.m_216332_(-64, k + 1);
        return new BlockPos(i, l, j);
    }

    public static void performLevelGenSpawning(ServerLevelAccessor level, Biome biome, int x, int z, RandomSource rand) {
        if (biome.m_47518_().m_48344_() >= 1.0f || biome.m_47518_().m_48344_() < 0.0f || !SpawnController.instance.hasSpawnList(level.m_9598_().m_175515_(Registries.f_256952_).m_7981_((Object)biome))) {
            return;
        }
        int tries = 0;
        block0: while (rand.m_188501_() < biome.m_47518_().m_48344_() && ++tries <= 20) {
            SpawnData data = SpawnController.instance.getRandomSpawnData(level.m_9598_().m_175515_(Registries.f_256952_).m_7981_((Object)biome));
            int size = 16;
            int j1 = x + rand.m_188503_(size);
            int k1 = z + rand.m_188503_(size);
            int l1 = j1;
            int i2 = k1;
            for (int k2 = 0; k2 < 4; ++k2) {
                BlockPos pos = NPCSpawning.getTopNonCollidingPos((LevelReader)level, CustomEntities.entityCustomNpc, j1, k1);
                if (!NPCSpawning.canCreatureTypeSpawnAtLocation(data, (LevelReader)level, pos)) {
                    j1 += rand.m_188503_(5) - rand.m_188503_(5);
                    k1 += rand.m_188503_(5) - rand.m_188503_(5);
                    while (j1 < x || j1 >= x + size || k1 < z || k1 >= z + size) {
                        j1 = l1 + rand.m_188503_(5) - rand.m_188503_(5);
                        k1 = i2 + rand.m_188503_(5) - rand.m_188503_(5);
                    }
                    continue;
                }
                if (NPCSpawning.spawnData(data, level, pos)) continue block0;
            }
        }
    }

    private static boolean spawnData(SpawnData data, ServerLevelAccessor level, BlockPos pos) {
        try {
            CompoundTag nbt = data.getCompound(1);
            if (nbt == null) {
                return false;
            }
            Entity entity = EntityType.m_20642_((CompoundTag)nbt, (Level)level.m_6018_()).orElse(null);
            if (entity == null || !(entity instanceof Mob)) {
                return false;
            }
            Mob entityliving = (Mob)entity;
            if (entity instanceof EntityCustomNpc) {
                EntityCustomNpc npc = (EntityCustomNpc)entity;
                npc.stats.spawnCycle = 4;
                npc.stats.respawnTime = 0;
                npc.ais.returnToStart = false;
                npc.ais.setStartPos(pos);
            }
            entity.m_7678_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5, level.m_213780_().m_188501_() * 360.0f, 0.0f);
            if (!ForgeEventFactory.checkSpawnPosition((Mob)entityliving, (ServerLevelAccessor)level, (MobSpawnType)MobSpawnType.NATURAL)) {
                return false;
            }
            level.m_7654_().m_18707_(() -> level.m_7967_((Entity)entityliving));
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public static float getLightLevel(LevelReader level, BlockPos pos) {
        int blockLight = level.m_45517_(LightLayer.BLOCK, pos);
        int skyLight = level.m_45517_(LightLayer.SKY, pos);
        int skyDarken = level.m_7445_();
        float skyLightValue = (11.0f - (float)skyDarken) * 15.0f / 11.0f;
        return Math.max((float)blockLight, (float)skyLight / 15.0f * skyLightValue);
    }

    public static boolean canCreatureTypeSpawnAtLocation(SpawnData data, LevelReader level, BlockPos pos) {
        if (!level.m_6857_().m_61937_(pos) || !level.m_45772_(CustomEntities.entityCustomNpc.m_20585_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()))) {
            return false;
        }
        if (data.type == 1 && NPCSpawning.getLightLevel(level, pos) > 8.0f || data.type == 2 && NPCSpawning.getLightLevel(level, pos) <= 8.0f) {
            return false;
        }
        BlockState state = level.m_8055_(pos);
        Block block = state.m_60734_();
        if (data.liquid) {
            return state.m_278721_() && level.m_8055_(pos.m_7495_()).m_278721_() && !level.m_8055_(pos.m_7494_()).m_60796_((BlockGetter)level, pos.m_7494_());
        }
        BlockPos blockpos1 = pos.m_7495_();
        BlockState state1 = level.m_8055_(blockpos1);
        Block block1 = state1.m_60734_();
        boolean flag = block1 != Blocks.f_50752_ && block1 != Blocks.f_50375_;
        BlockPos down = blockpos1.m_7495_();
        return (flag |= level.m_8055_(down).m_60734_().isValidSpawn(level.m_8055_(down), (BlockGetter)level, down, SpawnPlacements.Type.ON_GROUND, CustomEntities.entityCustomNpc)) && !state.m_60803_() && !state.m_278721_() && !level.m_8055_(pos.m_7494_()).m_60803_();
    }

    private static BlockPos getTopNonCollidingPos(LevelReader p_208498_0_, EntityType<?> p_208498_1_, int p_208498_2_, int p_208498_3_) {
        BlockPos blockpos;
        int i = p_208498_0_.m_6924_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, p_208498_2_, p_208498_3_);
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos(p_208498_2_, i, p_208498_3_);
        if (p_208498_0_.m_6042_().f_63856_()) {
            do {
                blockpos$mutable.m_122173_(Direction.DOWN);
            } while (!p_208498_0_.m_8055_((BlockPos)blockpos$mutable).m_60795_());
            do {
                blockpos$mutable.m_122173_(Direction.DOWN);
            } while (p_208498_0_.m_8055_((BlockPos)blockpos$mutable).m_60795_() && blockpos$mutable.m_123342_() > 0);
        }
        if (p_208498_0_.m_8055_(blockpos = blockpos$mutable.m_7495_()).m_60647_((BlockGetter)p_208498_0_, blockpos, PathComputationType.LAND)) {
            return blockpos;
        }
        return blockpos$mutable.m_7949_();
    }
}

