/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 */
package noppes.npcs.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import noppes.npcs.client.layer.LayerInterface;
import noppes.npcs.client.model.ModelNpcElytra;
import noppes.npcs.entity.EntityCustomNpc;

public class LayerNpcElytra
extends LayerInterface {
    private static final ResourceLocation WINGS_LOCATION = new ResourceLocation("textures/entity/elytra.png");
    private final ModelNpcElytra elytraModel;

    public LayerNpcElytra(EntityRendererProvider.Context manager, LivingEntityRenderer render) {
        super(render);
        this.elytraModel = new ModelNpcElytra(manager.m_174023_(ModelLayers.f_171141_));
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource typeBuffer, int packedLightIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = this.npc.m_6844_(EquipmentSlot.CHEST);
        if (this.shouldRender(itemstack, this.npc)) {
            ResourceLocation resourcelocation = this.npc instanceof EntityCustomNpc ? (this.npc.display.getCapeTexture() != null && !this.npc.display.getCapeTexture().isEmpty() && this.base instanceof PlayerModel ? new ResourceLocation(this.npc.display.getCapeTexture()) : this.getElytraTexture(itemstack, this.npc)) : this.getElytraTexture(itemstack, this.npc);
            matrixStack.m_85836_();
            matrixStack.m_252880_(0.0f, 0.0f, 0.125f);
            this.m_117386_().m_102624_((EntityModel)this.elytraModel);
            this.elytraModel.setupAnim(this.npc, limbSwing, limbSwingAmount, partialTicks, netHeadYaw, headPitch);
            VertexConsumer vertexconsumer = ItemRenderer.m_115184_((MultiBufferSource)typeBuffer, (RenderType)RenderType.m_110431_((ResourceLocation)resourcelocation), (boolean)false, (boolean)itemstack.m_41790_());
            this.elytraModel.m_7695_(matrixStack, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.m_85849_();
        }
    }

    public boolean shouldRender(ItemStack stack, EntityCustomNpc entity) {
        return stack.m_41720_() == Items.f_42741_;
    }

    public ResourceLocation getElytraTexture(ItemStack stack, EntityCustomNpc entity) {
        return WINGS_LOCATION;
    }

    @Override
    public void rotate(PoseStack matrixStack, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}

