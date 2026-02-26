/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import noppes.npcs.client.model.ModelNpcSlime;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.EntityNpcSlime;

public class LayerSlimeNpc<T extends EntityNpcSlime>
extends RenderLayer<T, ModelNpcSlime<T>> {
    private final LivingEntityRenderer renderer;
    private final EntityModel slimeModel = new ModelNpcSlime(0);

    public LayerSlimeNpc(LivingEntityRenderer renderer) {
        super((RenderLayerParent)renderer);
        this.renderer = renderer;
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!((EntityNPCInterface)((Object)living)).m_20145_()) {
            ((ModelNpcSlime)this.m_117386_()).m_102624_(this.slimeModel);
            this.slimeModel.m_6839_(living, limbSwing, limbSwingAmount, partialTicks);
            this.slimeModel.m_6973_(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110473_((ResourceLocation)this.m_117347_((Entity)living)));
            this.slimeModel.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.m_115338_(living, (float)0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}

