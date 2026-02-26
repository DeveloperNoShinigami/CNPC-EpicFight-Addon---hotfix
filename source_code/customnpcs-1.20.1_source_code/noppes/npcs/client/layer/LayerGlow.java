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
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

@OnlyIn(value=Dist.CLIENT)
public class LayerGlow<T extends EntityNPCInterface, M extends EntityModel<T>>
extends RenderLayer<T, M> {
    public LayerGlow(RenderCustomNpc npcRenderer) {
        super((RenderLayerParent)npcRenderer);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource typeBuffer, int packedLightIn, T npc, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (((EntityNPCInterface)((Object)npc)).display.getOverlayTexture().isEmpty()) {
            return;
        }
        if (((EntityNPCInterface)((Object)npc)).textureGlowLocation == null) {
            ((EntityNPCInterface)((Object)npc)).textureGlowLocation = new ResourceLocation(((EntityNPCInterface)((Object)npc)).display.getOverlayTexture());
        }
        VertexConsumer ivertexbuilder = null;
        ivertexbuilder = ((EntityNPCInterface)((Object)npc)).display.isOverlayGlowing() ? typeBuffer.m_6299_(RenderType.m_234338_((ResourceLocation)((EntityNPCInterface)((Object)npc)).textureGlowLocation)) : typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)((EntityNPCInterface)((Object)npc)).textureGlowLocation));
        this.m_117386_().m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.m_115338_(npc, (float)0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

