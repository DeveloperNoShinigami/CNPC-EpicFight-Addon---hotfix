/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraftforge.fml.ModList
 */
package noppes.npcs.controllers;

import com.google.common.collect.Lists;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraftforge.fml.ModList;
import noppes.npcs.client.renderer.RenderCustomNpc;

public class ArmorersWorkshopHelper {
    public static boolean Enabled = ModList.get().isLoaded("armourers_workshop");

    public static void onLayerAddEvent(LivingEntityRenderer<?, ?> renderer, RenderLayer<?, ?> layer) {
        if (renderer instanceof RenderCustomNpc) {
            RenderCustomNpc renderCustomNpc = (RenderCustomNpc)renderer;
            if (renderCustomNpc.npclayers == null) {
                renderCustomNpc.npclayers = Lists.newArrayList();
            }
            renderCustomNpc.npclayers.add(layer);
        }
    }

    public static void onLayerRemoveEvent(LivingEntityRenderer<?, ?> renderer, RenderLayer<?, ?> layer) {
        if (renderer instanceof RenderCustomNpc) {
            RenderCustomNpc renderCustomNpc = (RenderCustomNpc)renderer;
            List layers = renderCustomNpc.npclayers;
            if (layers == null) {
                return;
            }
            for (int i = 0; i < layers.size(); ++i) {
                if (layers.get(i) != layer) continue;
                layers.remove(i);
                --i;
            }
        }
    }

    public static void register() {
        if (!Enabled) {
            return;
        }
        try {
            Class<?> addEvent = Class.forName("moe.plushie.armourers_workshop.api.event.client.AddRendererLayerEvent");
            Method addEventGetRenderer = addEvent.getMethod("getRenderer", new Class[0]);
            Method addEventGetLayer = addEvent.getMethod("getLayer", new Class[0]);
            Class<?> removeEvent = Class.forName("moe.plushie.armourers_workshop.api.event.client.RemoveRendererLayerEvent");
            Method removeEventGetRenderer = removeEvent.getMethod("getRenderer", new Class[0]);
            Method removeEventGetLayer = removeEvent.getMethod("getLayer", new Class[0]);
            Class<?> eventBus = Class.forName("moe.plushie.armourers_workshop.api.event.EventBus");
            Method eventBusRegister = eventBus.getMethod("register", Class.class, Consumer.class);
            eventBusRegister.invoke(eventBus, addEvent, event -> {
                try {
                    Object renderer = addEventGetRenderer.invoke(event, new Object[0]);
                    Object layer = addEventGetLayer.invoke(event, new Object[0]);
                    ArmorersWorkshopHelper.onLayerAddEvent((LivingEntityRenderer)renderer, (RenderLayer)layer);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            });
            eventBusRegister.invoke(eventBus, removeEvent, event -> {
                try {
                    Object renderer = removeEventGetRenderer.invoke(event, new Object[0]);
                    Object layer = removeEventGetLayer.invoke(event, new Object[0]);
                    ArmorersWorkshopHelper.onLayerRemoveEvent((LivingEntityRenderer)renderer, (RenderLayer)layer);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            });
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

