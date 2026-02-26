/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.CustomNpcs;
import noppes.npcs.client.layer.LayerInterface;
import noppes.npcs.client.layer.LayerPreRender;
import noppes.npcs.client.model.ModelHeadwear;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.shared.client.model.Model2DRenderer;

public class LayerHeadwear
extends LayerInterface
implements LayerPreRender {
    private final ModelHeadwear headwear = new ModelHeadwear();

    public LayerHeadwear(LivingEntityRenderer render) {
        super(render);
    }

    @Override
    public void render(PoseStack mStack, MultiBufferSource typeBuffer, int lightmapUV, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
        if (CustomNpcs.HeadWearType != 1 || this.npc.textureLocation == null) {
            return;
        }
        float red = 1.0f;
        float blue = 1.0f;
        float green = 1.0f;
        if (this.npc.f_20916_ <= 0 && this.npc.f_20919_ <= 0) {
            int color = this.npc.display.getTint();
            red = (float)(color >> 16 & 0xFF) / 255.0f;
            green = (float)(color >> 8 & 0xFF) / 255.0f;
            blue = (float)(color & 0xFF) / 255.0f;
        }
        this.base.f_102808_.m_104299_(mStack);
        Model2DRenderer.textureOverride = this.npc.textureLocation;
        VertexConsumer ivertex = typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)this.npc.textureLocation));
        int m = OverlayTexture.m_118093_((int)OverlayTexture.m_118088_((float)0.0f), (int)OverlayTexture.m_118096_((this.npc.f_20916_ > 0 || this.npc.f_20919_ > 0 ? 1 : 0) != 0));
        this.headwear.render(mStack, ivertex, lightmapUV, m, red, green, blue, this.alpha());
        Model2DRenderer.textureOverride = null;
    }

    @Override
    public void rotate(PoseStack matrixStack, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void preRender(EntityCustomNpc npc) {
        boolean bl = this.base.f_102809_.f_104207_ = this.base.f_102808_.f_104207_ && CustomNpcs.HeadWearType != 1;
        if (!this.base.f_102809_.f_104207_) {
            this.headwear.config = null;
        }
    }
}

