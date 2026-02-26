/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package noppes.npcs.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.entity.EntityCustomNpc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={LivingEntityRenderer.class})
public class LivingRendererMixin<T extends EntityCustomNpc, M extends HumanoidModel<T>> {
    @Inject(at={@At(value="HEAD")}, method={"addLayer"})
    private void spawnOriginalMobs(RenderLayer<T, M> layer, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityRenderer renderer = (LivingEntityRenderer)this;
        if (renderer instanceof RenderCustomNpc) {
            if (((RenderCustomNpc)renderer).npclayers == null) {
                ((RenderCustomNpc)renderer).npclayers = Lists.newArrayList();
            }
            ((RenderCustomNpc)renderer).npclayers.add(layer);
        }
    }
}

