package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.common.compatibility.CnpcEpicFightCombatBridge;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Routes CNPC-originated native melee damage through the active Epic Fight
 * source for players and every other LivingEntity. CustomNPCs overrides hurt
 * on its own entity class, so MixinEntityNpcInterface remains necessary for
 * CNPC victims as well.
 */
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @ModifyVariable(
            method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private DamageSource cnpcefaddon$useActiveEpicFightSource(DamageSource incoming) {
        return CnpcEpicFightCombatBridge.replaceActiveEpicFightSource((LivingEntity) (Object) this, incoming);
    }
}
