/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 */
package noppes.npcs.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import noppes.npcs.client.layer.LayerInterface;

public class LayerNpcCloak
extends LayerInterface {
    public LayerNpcCloak(LivingEntityRenderer render) {
        super(render);
    }

    @Override
    public void render(PoseStack mStack, MultiBufferSource typeBuffer, int lightmapUV, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
        ItemStack chestStack;
        if (this.npc.textureCloakLocation == null) {
            if (this.npc.display.getCapeTexture() == null || this.npc.display.getCapeTexture().isEmpty() || !(this.base instanceof PlayerModel)) {
                return;
            }
            this.npc.textureCloakLocation = new ResourceLocation(this.npc.display.getCapeTexture());
        }
        if ((chestStack = this.npc.m_6844_(EquipmentSlot.CHEST)).m_150930_(Items.f_42741_)) {
            return;
        }
        mStack.m_85836_();
        mStack.m_85837_(0.0, 0.0, 0.125);
        double d0 = Mth.m_14139_((double)partialTicks, (double)this.npc.prevChasingPosX, (double)this.npc.chasingPosX) - Mth.m_14139_((double)partialTicks, (double)this.npc.f_19854_, (double)this.npc.m_20185_());
        double d1 = Mth.m_14139_((double)partialTicks, (double)this.npc.prevChasingPosY, (double)this.npc.chasingPosY) - Mth.m_14139_((double)partialTicks, (double)this.npc.f_19855_, (double)this.npc.m_20186_());
        double d2 = Mth.m_14139_((double)partialTicks, (double)this.npc.prevChasingPosZ, (double)this.npc.chasingPosZ) - Mth.m_14139_((double)partialTicks, (double)this.npc.f_19856_, (double)this.npc.m_20189_());
        float f = this.npc.f_20884_ + (this.npc.f_20883_ - this.npc.f_20884_);
        double d3 = Mth.m_14031_((float)(f * ((float)Math.PI / 180)));
        double d4 = -Mth.m_14089_((float)(f * ((float)Math.PI / 180)));
        float f1 = (float)d1 * 10.0f;
        f1 = Mth.m_14036_((float)f1, (float)-6.0f, (float)32.0f);
        float f2 = (float)(d0 * d3 + d2 * d4) * 100.0f;
        f2 = Mth.m_14036_((float)f2, (float)0.0f, (float)150.0f);
        float f3 = (float)(d0 * d4 - d2 * d3) * 100.0f;
        f3 = Mth.m_14036_((float)f3, (float)-20.0f, (float)20.0f);
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        f1 += Mth.m_14031_((float)(Mth.m_14179_((float)partialTicks, (float)this.npc.f_19867_, (float)this.npc.f_19787_) * 6.0f)) * 32.0f * partialTicks;
        if (this.npc.m_6047_()) {
            f1 += 25.0f;
        }
        mStack.m_252781_(Axis.f_252529_.m_252977_(6.0f + f2 / 2.0f + f1));
        mStack.m_252781_(Axis.f_252403_.m_252977_(f3 / 2.0f));
        mStack.m_252781_(Axis.f_252436_.m_252977_(180.0f - f3 / 2.0f));
        VertexConsumer ivertexbuilder = typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)this.npc.textureCloakLocation));
        ((PlayerModel)this.base).m_103411_(mStack, ivertexbuilder, lightmapUV, OverlayTexture.f_118083_);
        mStack.m_85849_();
    }

    @Override
    public void rotate(PoseStack matrixStack, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}

