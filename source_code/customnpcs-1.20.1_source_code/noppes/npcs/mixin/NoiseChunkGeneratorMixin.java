/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.WorldGenRegion
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package noppes.npcs.mixin;

import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import noppes.npcs.NPCSpawning;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={NoiseBasedChunkGenerator.class})
public class NoiseChunkGeneratorMixin {
    @Inject(at={@At(value="HEAD")}, method={"spawnOriginalMobs"}, cancellable=false)
    private void spawnOriginalMobs(WorldGenRegion region, CallbackInfo ci) {
        ChunkPos chunkpos = region.m_143488_();
        int x = chunkpos.m_45604_();
        int z = chunkpos.m_45605_();
        Biome biome = (Biome)region.m_204166_(new ChunkPos(x, z).m_45615_()).m_203334_();
        NPCSpawning.performLevelGenSpawning((ServerLevelAccessor)region, biome, x, z, region.m_213780_());
    }
}

