package com.goodbird.cnpcefaddon.mixin.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.client.gui.TargetIndicator;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(TargetIndicator.class)
public class MixinTargetIndicator {
    @Inject(method = "shouldDraw", at=@At("RETURN"), cancellable = true, remap = false)
    public void shouldDraw(LivingEntity entityIn, LivingEntityPatch<?> entitypatch, LocalPlayerPatch playerpatch, float partialTicks, CallbackInfoReturnable<Boolean> cir) {
        if(Minecraft.getInstance().screen!=null){
            cir.setReturnValue(false);
        }
    }
}
