/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.layer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.shared.client.model.NopModelPart;

public abstract class LayerInterface
extends RenderLayer {
    protected LivingEntityRenderer render;
    protected EntityCustomNpc npc;
    protected ModelData playerdata;
    public HumanoidModel base;
    private int color;

    public LayerInterface(LivingEntityRenderer render) {
        super((RenderLayerParent)render);
        this.render = render;
        this.base = (HumanoidModel)render.m_7200_();
    }

    public void setColor(ModelPartData data, LivingEntity entity) {
    }

    protected float red() {
        if (this.npc.f_20916_ > 0 || this.npc.f_20919_ > 0) {
            return 1.0f;
        }
        return (float)(this.color >> 16 & 0xFF) / 255.0f;
    }

    protected float green() {
        if (this.npc.f_20916_ > 0 || this.npc.f_20919_ > 0) {
            return 0.0f;
        }
        return (float)(this.color >> 8 & 0xFF) / 255.0f;
    }

    protected float blue() {
        if (this.npc.f_20916_ > 0 || this.npc.f_20919_ > 0) {
            return 0.0f;
        }
        return (float)(this.color & 0xFF) / 255.0f;
    }

    protected float alpha() {
        boolean flag = !this.npc.m_20145_();
        boolean flag1 = !flag && !this.npc.m_20177_((Player)Minecraft.m_91087_().f_91074_);
        return flag1 ? 0.15f : 0.99f;
    }

    public void preRender(ModelPartData data) {
        if (this.npc.f_20916_ > 0 || this.npc.f_20919_ > 0) {
            return;
        }
        this.color = data.color;
        if (this.npc.display.getTint() != 0xFFFFFF) {
            this.color = data.color != 0xFFFFFF ? this.blend(data.color, this.npc.display.getTint(), 0.5f) : this.npc.display.getTint();
        }
    }

    public int blend(int color1, int color2, float ratio) {
        if (ratio >= 1.0f) {
            return color2;
        }
        if (ratio <= 0.0f) {
            return color1;
        }
        int aR = (color1 & 0xFF0000) >> 16;
        int aG = (color1 & 0xFF00) >> 8;
        int aB = color1 & 0xFF;
        int bR = (color2 & 0xFF0000) >> 16;
        int bG = (color2 & 0xFF00) >> 8;
        int bB = color2 & 0xFF;
        int R = (int)((float)aR + (float)(bR - aR) * ratio);
        int G = (int)((float)aG + (float)(bG - aG) * ratio);
        int B = (int)((float)aB + (float)(bB - aB) * ratio);
        return R << 16 | G << 8 | B;
    }

    public void m_6494_(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.npc = (EntityCustomNpc)entity;
        if (this.npc.m_20177_((Player)Minecraft.m_91087_().f_91074_)) {
            return;
        }
        this.playerdata = this.npc.modelData;
        this.base = (HumanoidModel)this.render.m_7200_();
        this.rotate(matrixStackIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        matrixStackIn.m_85836_();
        if (entity.m_20145_()) {
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)0.15f);
            RenderSystem.depthMask((boolean)false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc((int)770, (int)771);
        }
        if (this.npc.f_20916_ > 0 || this.npc.f_20919_ > 0) {
            // empty if block
        }
        if (this.npc.m_6047_()) {
            // empty if block
        }
        this.render(matrixStackIn, bufferIn, packedLightIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        if (entity.m_20145_()) {
            RenderSystem.disableBlend();
            RenderSystem.depthMask((boolean)true);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        matrixStackIn.m_85849_();
    }

    public RenderType getRenderType(ModelPartData data) {
        ResourceLocation resource = this.npc.textureLocation;
        if (!data.playerTexture) {
            resource = data.getResource();
        }
        return RenderType.m_110473_((ResourceLocation)resource);
    }

    public void setRotation(NopModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    public abstract void render(PoseStack var1, MultiBufferSource var2, int var3, float var4, float var5, float var6, float var7, float var8, float var9);

    public abstract void rotate(PoseStack var1, float var2, float var3, float var4, float var5, float var6, float var7);
}

