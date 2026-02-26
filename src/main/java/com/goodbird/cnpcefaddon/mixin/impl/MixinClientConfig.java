package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.mixin.IClientConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.config.ClientConfig;

@Mixin(ClientConfig.class)
public class MixinClientConfig implements IClientConfig {
    @Shadow(remap = false)
    @Final
    private static ForgeConfigSpec.Builder BUILDER;
    
    @Unique
    private static ForgeConfigSpec.BooleanValue firstPersonRenderEnabled = BUILDER.define("ingame.firstPersonRenderEnabled", () -> Boolean.valueOf(true));

    @Unique
    @Override
    public boolean isFPRenderEnabled() {
        return firstPersonRenderEnabled.get();
    }
}
