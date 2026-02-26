/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.client.parts;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.ModelEyeData;
import noppes.npcs.client.parts.MpmPartAbstractClient;
import noppes.npcs.client.parts.MpmPartData;
import noppes.npcs.constants.BodyPart;
import noppes.npcs.shared.client.model.ModelPlaneRenderer;
import noppes.npcs.shared.client.model.NopModelPart;
import noppes.npcs.shared.common.util.EasingFunctions;
import noppes.npcs.shared.common.util.NopVector3f;

public class MpmPartEyes
extends MpmPartAbstractClient {
    private static final ResourceLocation glint = new ResourceLocation("moreplayermodels", "textures/parts/eyes/glint.png");
    private static final ResourceLocation brows = new ResourceLocation("moreplayermodels", "textures/parts/eyes/brows.png");
    private static final ResourceLocation pupils = new ResourceLocation("moreplayermodels", "textures/parts/eyes/pupils.png");
    private static final ResourceLocation sclera = new ResourceLocation("moreplayermodels", "textures/parts/eyes/sclera.png");
    private static final NopModelPart sclera1 = new ModelPlaneRenderer(64, 64, 9, 12).addPlane(-1.0f, -1.0f, 0.0f, 2, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(-2.0f, -3.0f, -4.002f));
    private static final NopModelPart sclera2 = new ModelPlaneRenderer(64, 64, 13, 12).addPlane(-1.0f, -1.0f, 0.0f, 2, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(2.0f, -3.0f, -4.002f));
    private static final NopModelPart sclera1M = new ModelPlaneRenderer(64, 64, 9, 12).mirror(true).addPlane(-1.0f, -1.0f, 0.0f, 2, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(-2.0f, -3.0f, -4.002f));
    private static final NopModelPart sclera2M = new ModelPlaneRenderer(64, 64, 13, 12).mirror(true).addPlane(-1.0f, -1.0f, 0.0f, 2, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(2.0f, -3.0f, -4.002f));
    private static final NopModelPart scleraBig1 = new ModelPlaneRenderer(64, 64, 9, 12).addPlane(-1.0f, -1.0f, 0.0f, 2, 2, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(-2.0f, -3.0f, -4.002f));
    private static final NopModelPart scleraBig2 = new ModelPlaneRenderer(64, 64, 13, 12).addPlane(-1.0f, -1.0f, 0.0f, 2, 2, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(2.0f, -3.0f, -4.002f));
    private static final NopModelPart scleraBig1M = new ModelPlaneRenderer(64, 64, 9, 12).mirror(true).addPlane(-1.0f, -1.0f, 0.0f, 2, 2, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(-2.0f, -3.0f, -4.002f));
    private static final NopModelPart scleraBig2M = new ModelPlaneRenderer(64, 64, 13, 12).mirror(true).addPlane(-1.0f, -1.0f, 0.0f, 2, 2, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(2.0f, -3.0f, -4.002f));
    private static final NopModelPart pupils1 = new ModelPlaneRenderer(64, 64, 10, 12).addPlane(-1.0f, -1.0f, 0.0f, 1, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(-1.0f, -3.0f, -4.004f));
    private static final NopModelPart pupils2 = new ModelPlaneRenderer(64, 64, 13, 12).addPlane(-1.0f, -1.0f, 0.0f, 1, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(2.0f, -3.0f, -4.004f));
    private static final NopModelPart pupilsBig1 = new ModelPlaneRenderer(64, 64, 10, 12).addPlane(-1.0f, -1.0f, 0.0f, 1, 2, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(-1.0f, -3.0f, -4.004f));
    private static final NopModelPart pupilsBig2 = new ModelPlaneRenderer(64, 64, 13, 12).addPlane(-1.0f, -1.0f, 0.0f, 1, 2, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(2.0f, -3.0f, -4.004f));
    private static final NopModelPart glint1 = new ModelPlaneRenderer(64, 64, 10, 12).addPlane(-0.5f, -0.5f, 0.0f, 1, 1, new NopVector3f(0.6f, 0.6f, 0.6f), Direction.NORTH).setPos(new NopVector3f(-1.4f, -3.44f, -4.006f));
    private static final NopModelPart glint2 = new ModelPlaneRenderer(64, 64, 13, 12).addPlane(-0.5f, -0.5f, 0.0f, 1, 1, new NopVector3f(0.6f, 0.6f, 0.6f), Direction.NORTH).setPos(new NopVector3f(1.6f, -3.44f, -4.006f));
    private static final NopModelPart lid1 = new ModelPlaneRenderer(64, 64, 9, 11).addPlane(-1.0f, 0.0f, 0.0f, 2, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(-2.0f, -4.0f, -4.008f));
    private static final NopModelPart lid2 = new ModelPlaneRenderer(64, 64, 13, 11).addPlane(-1.0f, 0.0f, 0.0f, 2, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(2.0f, -4.0f, -4.008f));
    private static final NopModelPart brows1 = new ModelPlaneRenderer(64, 64, 9, 11).addPlane(-1.0f, -1.0f, 0.0f, 2, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(-2.0f, -4.0f, -4.01f));
    private static final NopModelPart brows2 = new ModelPlaneRenderer(64, 64, 13, 11).addPlane(-1.0f, -1.0f, 0.0f, 2, 1, NopVector3f.ONE, Direction.NORTH).setPos(new NopVector3f(2.0f, -4.0f, -4.01f));
    public int type;

    public MpmPartEyes(int type, ResourceLocation id) {
        this.type = type;
        this.id = id;
        this.menu = "part.buildin";
        this.name = "Eyes";
        this.bodyPart = BodyPart.HEAD;
        this.hiddenParts = new ArrayList();
        this.isEnabled = true;
        this.author = "Noppes";
    }

    @Override
    public void render(MpmPartData data, PoseStack mStack, MultiBufferSource typeBuffer, int lightmapUV, LivingEntity player) {
        ModelEyeData eyeData = (ModelEyeData)data;
        mStack.m_85836_();
        mStack.m_252880_((float)((ModelEyeData)data).eyePos.x * -0.0625f, (float)((ModelEyeData)data).eyePos.y * -0.0625f, 0.0f);
        float offset = 0.0f;
        if (eyeData.blinkStart > 0L && player.m_6084_()) {
            float f = (float)(System.currentTimeMillis() - eyeData.blinkStart) / 150.0f;
            if (f > 1.0f) {
                f = 2.0f - f;
            }
            if (f < 0.0f) {
                eyeData.blinkStart = 0L;
                f = 0.0f;
            }
            offset = (float)(eyeData.eyeSize + 1) * EasingFunctions.easeInCubic(f);
        }
        if (this.type == 0 || this.type == 1) {
            if (eyeData.skinType == 1) {
                (eyeData.eyeSize == 0 ? sclera1 : scleraBig1).render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)sclera)), lightmapUV, OverlayTexture.f_118083_);
            } else if (eyeData.skinType == 2) {
                if (eyeData.mirror) {
                    (eyeData.eyeSize == 0 ? sclera1M : scleraBig1M).render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)eyeData.getUrlTexture())), lightmapUV, OverlayTexture.f_118083_);
                } else {
                    (eyeData.eyeSize == 0 ? sclera1 : scleraBig1).render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)eyeData.getUrlTexture())), lightmapUV, OverlayTexture.f_118083_);
                }
            }
            if (eyeData.mirror) {
                mStack.m_85837_(-0.0625, 0.0, 0.0);
            }
            if (eyeData.skinType == 1) {
                (eyeData.eyeSize == 0 ? pupils1 : pupilsBig1).render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)pupils)), lightmapUV, OverlayTexture.f_118083_, eyeData.color.x, eyeData.color.y, eyeData.color.z, 1.0f);
            }
            if (eyeData.glint) {
                glint1.render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)glint)), lightmapUV, OverlayTexture.f_118083_);
            }
            if (eyeData.mirror) {
                mStack.m_85837_(0.0625, 0.0, 0.0);
            }
            if (offset > 0.0f) {
                MpmPartEyes.lid1.scale = new NopVector3f(1.0f, offset, 1.0f);
                lid1.render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)brows)), lightmapUV, OverlayTexture.f_118083_, eyeData.lidColor.x, eyeData.lidColor.y, eyeData.lidColor.z, 1.0f);
            }
        }
        mStack.m_252880_((float)((ModelEyeData)data).eyePos.x * 0.0625f * 2.0f, 0.0f, 0.0f);
        if (this.type == 0 || this.type == 2) {
            if (eyeData.skinType == 1) {
                (eyeData.eyeSize == 0 ? sclera2 : scleraBig2).render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)sclera)), lightmapUV, OverlayTexture.f_118083_);
            } else if (eyeData.skinType == 2) {
                if (eyeData.mirror) {
                    (eyeData.eyeSize == 0 ? sclera2M : scleraBig2M).render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)eyeData.getUrlTexture())), lightmapUV, OverlayTexture.f_118083_);
                } else {
                    (eyeData.eyeSize == 0 ? sclera2 : scleraBig2).render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)eyeData.getUrlTexture())), lightmapUV, OverlayTexture.f_118083_);
                }
            }
            if (eyeData.mirror) {
                mStack.m_85837_(0.0625, 0.0, 0.0);
            }
            if (eyeData.skinType == 1) {
                (eyeData.eyeSize == 0 ? pupils2 : pupilsBig2).render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)pupils)), lightmapUV, OverlayTexture.f_118083_, eyeData.color.x, eyeData.color.y, eyeData.color.z, 1.0f);
            }
            if (eyeData.glint) {
                glint2.render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)glint)), lightmapUV, OverlayTexture.f_118083_);
            }
            if (eyeData.mirror) {
                mStack.m_85837_(-0.0625, 0.0, 0.0);
            }
            if (offset > 0.0f) {
                MpmPartEyes.lid2.scale = new NopVector3f(1.0f, offset, 1.0f);
                lid2.render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)brows)), lightmapUV, OverlayTexture.f_118083_, eyeData.lidColor.x, eyeData.lidColor.y, eyeData.lidColor.z, 1.0f);
            }
        }
        mStack.m_85836_();
        mStack.m_252880_(0.0f, offset * 0.0625f, 0.0f);
        if (this.type == 0 || this.type == 2) {
            MpmPartEyes.brows2.scale = eyeData.browThickness;
            brows2.render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)brows)), lightmapUV, OverlayTexture.f_118083_, eyeData.browColor.x, eyeData.browColor.y, eyeData.browColor.z, 1.0f);
        }
        mStack.m_252880_((float)((ModelEyeData)data).eyePos.x * -0.0625f * 2.0f, 0.0f, 0.0f);
        if (this.type == 0 || this.type == 1) {
            MpmPartEyes.brows1.scale = eyeData.browThickness;
            brows1.render(mStack, typeBuffer.m_6299_(RenderType.m_110473_((ResourceLocation)brows)), lightmapUV, OverlayTexture.f_118083_, eyeData.browColor.x, eyeData.browColor.y, eyeData.browColor.z, 1.0f);
        }
        mStack.m_85849_();
        mStack.m_85849_();
    }
}

