/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 */
package noppes.npcs.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.entity.EntityProjectile;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(value=Dist.CLIENT)
public class RenderProjectile<T extends EntityProjectile>
extends EntityRenderer<T> {
    public boolean renderWithColor = true;
    private static final ResourceLocation field_110780_a = new ResourceLocation("textures/entity/projectiles/arrow.png");
    private static final ResourceLocation field_110798_h = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private boolean crash = false;
    private boolean crash2 = false;

    public RenderProjectile(EntityRendererProvider.Context manager) {
        super(manager);
    }

    public void render(T projectile, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        Minecraft mc = Minecraft.m_91087_();
        matrixStack.m_85836_();
        float scale = (float)((EntityProjectile)((Object)projectile)).getSize() / 10.0f;
        ItemStack item = ((EntityProjectile)((Object)projectile)).getItemDisplay();
        matrixStack.m_85841_(scale, scale, scale);
        if (((EntityProjectile)((Object)projectile)).isArrow()) {
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_((float)partialTicks, (float)((EntityProjectile)((Object)projectile)).f_19859_, (float)projectile.m_146908_()) - 90.0f));
            matrixStack.m_252781_(Axis.f_252403_.m_252977_(Mth.m_14179_((float)partialTicks, (float)((EntityProjectile)((Object)projectile)).f_19860_, (float)projectile.m_146909_())));
            float f9 = (float)((EntityProjectile)((Object)projectile)).arrowShake - partialTicks;
            if (f9 > 0.0f) {
                float f10 = -Mth.m_14031_((float)(f9 * 3.0f)) * f9;
                matrixStack.m_252781_(Axis.f_252403_.m_252977_(f10));
            }
            matrixStack.m_252781_(Axis.f_252529_.m_252977_(45.0f));
            matrixStack.m_85841_(0.05625f, 0.05625f, 0.05625f);
            matrixStack.m_85837_(-4.0, 0.0, 0.0);
            VertexConsumer ivertexbuilder = buffer.m_6299_(RenderType.m_110452_((ResourceLocation)this.getTextureLocation(projectile)));
            PoseStack.Pose matrixstack$entry = matrixStack.m_85850_();
            Matrix4f matrix4f = matrixstack$entry.m_252922_();
            Matrix3f matrix3f = matrixstack$entry.m_252943_();
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0f, 0.15625f, -1, 0, 0, packedLight);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625f, 0.15625f, -1, 0, 0, packedLight);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625f, 0.3125f, -1, 0, 0, packedLight);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0f, 0.3125f, -1, 0, 0, packedLight);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, -2, 0.0f, 0.15625f, 1, 0, 0, packedLight);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, 2, 2, 0.15625f, 0.15625f, 1, 0, 0, packedLight);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, 2, 0.15625f, 0.3125f, 1, 0, 0, packedLight);
            this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -7, -2, -2, 0.0f, 0.3125f, 1, 0, 0, packedLight);
            for (int j = 0; j < 4; ++j) {
                matrixStack.m_252781_(Axis.f_252529_.m_252977_(90.0f));
                this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -8, -2, 0, 0.0f, 0.0f, 0, 1, 0, packedLight);
                this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 8, -2, 0, 0.5f, 0.0f, 0, 1, 0, packedLight);
                this.drawVertex(matrix4f, matrix3f, ivertexbuilder, 8, 2, 0, 0.5f, 0.15625f, 0, 1, 0, packedLight);
                this.drawVertex(matrix4f, matrix3f, ivertexbuilder, -8, 2, 0, 0.0f, 0.15625f, 0, 1, 0, packedLight);
            }
        } else if (((EntityProjectile)((Object)projectile)).is3D()) {
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_((float)partialTicks, (float)((EntityProjectile)((Object)projectile)).f_19859_, (float)projectile.m_146908_()) - 180.0f));
            matrixStack.m_252781_(Axis.f_252403_.m_252977_(Mth.m_14179_((float)partialTicks, (float)((EntityProjectile)((Object)projectile)).f_19860_, (float)projectile.m_146909_())));
            matrixStack.m_252880_(0.0f, -0.125f, 0.25f);
            if (item.m_41720_() instanceof BlockItem && Block.m_49814_((Item)item.m_41720_()).m_49966_().m_60799_() == RenderShape.ENTITYBLOCK_ANIMATED) {
                matrixStack.m_252880_(0.0f, 0.1875f, -0.3125f);
                matrixStack.m_252781_(Axis.f_252529_.m_252977_(20.0f));
                matrixStack.m_252781_(Axis.f_252436_.m_252977_(45.0f));
                float f8 = 0.375f;
                matrixStack.m_85841_(-f8, -f8, f8);
            }
            if (!this.crash) {
                try {
                    mc.m_91291_().m_269128_(item, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, null, 0);
                }
                catch (Throwable e) {
                    this.crash = true;
                }
            } else if (!this.crash2) {
                try {
                    mc.m_91291_().m_269128_(item, ItemDisplayContext.NONE, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, null, 0);
                }
                catch (Throwable ee) {
                    this.crash2 = true;
                }
            } else {
                mc.m_91291_().m_269128_(new ItemStack((ItemLike)Blocks.f_50493_), ItemDisplayContext.GROUND, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, null, 0);
            }
        } else {
            matrixStack.m_85841_(0.5f, 0.5f, 0.5f);
            matrixStack.m_252781_(this.f_114476_.f_114358_.m_253121_());
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0f));
            mc.m_91291_().m_269128_(item, ItemDisplayContext.GROUND, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, null, 0);
        }
        if (!((EntityProjectile)((Object)projectile)).is3D() || ((EntityProjectile)((Object)projectile)).glows()) {
            // empty if block
        }
        matrixStack.m_85849_();
    }

    protected ResourceLocation func_110779_a(EntityProjectile projectile) {
        return projectile.isArrow() ? field_110780_a : TextureAtlas.f_118259_;
    }

    public ResourceLocation getTextureLocation(T par1Entity) {
        return ((EntityProjectile)((Object)par1Entity)).isArrow() ? field_110780_a : TextureAtlas.f_118259_;
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, int offsetX, int offsetY, int offsetZ, float textureX, float textureY, int p_229039_9_, int p_229039_10_, int p_229039_11_, int packedLightIn) {
        vertexBuilder.m_252986_(matrix, (float)offsetX, (float)offsetY, (float)offsetZ).m_6122_(255, 255, 255, 255).m_7421_(textureX, textureY).m_86008_(OverlayTexture.f_118083_).m_85969_(packedLightIn).m_252939_(normals, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_).m_5752_();
    }
}

