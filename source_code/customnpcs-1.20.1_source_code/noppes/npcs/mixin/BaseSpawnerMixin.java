/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BaseSpawner
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.SpawnData
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package noppes.npcs.mixin;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={BaseSpawner.class})
public interface BaseSpawnerMixin {
    @Invoker
    public void callSetNextSpawnData(@Nullable Level var1, BlockPos var2, SpawnData var3);
}

