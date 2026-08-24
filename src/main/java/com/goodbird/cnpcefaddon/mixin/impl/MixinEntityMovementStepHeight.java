package com.goodbird.cnpcefaddon.mixin.impl;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.entity.EntityNPCInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Applies CNPC's step height even when animation/root motion calls Entity.move directly. */
@Mixin(Entity.class)
public abstract class MixinEntityMovementStepHeight {
    @Inject(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"))
    private void cnpcefaddon$enforceStepHeightDuringAnimationMove(MoverType moverType, Vec3 movement, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;
        if (self instanceof EntityNPCInterface npc) {
            npc.setMaxUpStep(1.5F);
        }
    }
}
