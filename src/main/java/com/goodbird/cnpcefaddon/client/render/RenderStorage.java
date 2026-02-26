package com.goodbird.cnpcefaddon.client.render;

import com.goodbird.cnpcefaddon.mixin.impl.IMixinRenderEngine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;

import java.util.HashMap;
import java.util.Map;

public class RenderStorage {
    public static Map<ResourceLocation, PatchedEntityRenderer> renderersMap = new HashMap<>();

    public static void registerRenderer(ResourceLocation resourceLocation, String renderer) {
        IMixinRenderEngine renderEngine = (IMixinRenderEngine) ClientEngine.getInstance().renderEngine;
        if ("".equals(renderer))
            return;
        if ("player".equals(renderer)) {
            renderersMap.put(resourceLocation, renderEngine.getBasicHumanoidRenderer());
        } else {
            EntityType<?> presetEntityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(renderer));
            if (renderEngine.getEntityRendererProvider().containsKey(presetEntityType)) {
                // Updated: Function.apply() instead of Supplier.get()
                renderersMap.put(resourceLocation, renderEngine.getEntityRendererProvider().get(presetEntityType).apply(presetEntityType));
            } else {
                throw new IllegalArgumentException("Datapack Mob Patch Crash: Invalid Renderer type " + renderer);
            }
        }
    }


}
