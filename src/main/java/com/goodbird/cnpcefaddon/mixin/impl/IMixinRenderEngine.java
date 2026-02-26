package com.goodbird.cnpcefaddon.mixin.impl;

import com.google.common.collect.BiMap;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.client.events.engine.RenderEngine;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;

import java.util.function.Function;

@Mixin(RenderEngine.class)
public interface IMixinRenderEngine {
    @Accessor(remap = false)
    PHumanoidRenderer<?, ?, ?, ?, ?> getBasicHumanoidRenderer();

    @Accessor(remap = false)
    BiMap<EntityType<?>, Function<EntityType<?>, PatchedEntityRenderer>> getEntityRendererProvider();
}
