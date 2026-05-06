package com.goodbird.cnpcefaddon.mixin.impl;

import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.client.gui.model.GuiCreationExtra;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GuiCreationExtra.class, priority = 1001)
public class MixinGuiCreationExtra {
    @Redirect(method = "getData", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getEncodeId()Ljava/lang/String;"), remap = false)
    private String cnpcefaddon$guardNullEncodeId(LivingEntity entity) {
        String encodeId = entity.getEncodeId();
        return encodeId != null ? encodeId : "";
    }
}