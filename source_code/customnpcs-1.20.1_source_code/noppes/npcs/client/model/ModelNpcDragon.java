/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import noppes.npcs.entity.EntityNpcDragon;
import noppes.npcs.shared.client.model.NopModelPart;

public class ModelNpcDragon<T extends Entity>
extends EntityModel<T> {
    private NopModelPart head;
    private NopModelPart neck;
    private NopModelPart jaw;
    private NopModelPart body;
    private NopModelPart leftWing;
    private NopModelPart leftWingTip;
    private NopModelPart leftFrontLeg;
    private NopModelPart leftFrontLegTip;
    private NopModelPart leftFrontFoot;
    private NopModelPart leftRearLeg;
    private NopModelPart leftRearLegTip;
    private NopModelPart leftRearFoot;
    private NopModelPart rightWing;
    private NopModelPart rightWingTip;
    private NopModelPart rightFrontLeg;
    private NopModelPart rightFrontLegTip;
    private NopModelPart rightFrontFoot;
    private NopModelPart rightRearLeg;
    private NopModelPart rightRearLegTip;
    private NopModelPart rightRearFoot;
    private float field_40317_s;
    private EntityNpcDragon entitydragon;
    private float animationPos;
    private float animationSpeed;

    public ModelNpcDragon() {
        float f = -16.0f;
        this.head = new NopModelPart(256, 256);
        this.head.addBox("upperlip", -6.0f, -1.0f, -24.0f, 12, 5, 16, 0.0f, 176, 44);
        this.head.addBox("upperhead", -8.0f, -8.0f, -10.0f, 16, 16, 16, 0.0f, 112, 30);
        this.head.mirror = true;
        this.head.addBox("scale", -5.0f, -12.0f, -4.0f, 2, 4, 6, 0.0f, 0, 0);
        this.head.addBox("nostril", -5.0f, -3.0f, -22.0f, 2, 2, 4, 0.0f, 112, 0);
        this.head.mirror = false;
        this.head.addBox("scale", 3.0f, -12.0f, -4.0f, 2, 4, 6, 0.0f, 0, 0);
        this.head.addBox("nostril", 3.0f, -3.0f, -22.0f, 2, 2, 4, 0.0f, 112, 0);
        this.jaw = new NopModelPart(256, 256);
        this.jaw.setPos(0.0f, 4.0f, -8.0f);
        this.jaw.addBox("jaw", -6.0f, 0.0f, -16.0f, 12, 4, 16, 0.0f, 176, 65);
        this.head.addChild(this.jaw);
        this.neck = new NopModelPart(256, 256);
        this.neck.addBox("box", -5.0f, -5.0f, -5.0f, 10, 10, 10, 0.0f, 192, 104);
        this.neck.addBox("scale", -1.0f, -9.0f, -3.0f, 2, 4, 6, 0.0f, 48, 0);
        this.body = new NopModelPart(256, 256);
        this.body.setPos(0.0f, 4.0f, 8.0f);
        this.body.addBox("body", -12.0f, 0.0f, -16.0f, 24, 24, 64, 0.0f, 0, 0);
        this.body.addBox("scale", -1.0f, -6.0f, -10.0f, 2, 6, 12, 0.0f, 220, 53);
        this.body.addBox("scale", -1.0f, -6.0f, 10.0f, 2, 6, 12, 0.0f, 220, 53);
        this.body.addBox("scale", -1.0f, -6.0f, 30.0f, 2, 6, 12, 0.0f, 220, 53);
        this.leftWing = new NopModelPart(256, 256);
        this.leftWing.mirror = true;
        this.leftWing.setPos(12.0f, 5.0f, 2.0f);
        this.leftWing.addBox("bone", 0.0f, -4.0f, -4.0f, 56, 8, 8, 0.0f, 112, 88);
        this.leftWing.addBox("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 88);
        this.leftWingTip = new NopModelPart(256, 256);
        this.leftWingTip.mirror = true;
        this.leftWingTip.setPos(56.0f, 0.0f, 0.0f);
        this.leftWingTip.addBox("bone", 0.0f, -2.0f, -2.0f, 56, 4, 4, 0.0f, 112, 136);
        this.leftWingTip.addBox("skin", 0.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 144);
        this.leftWing.addChild(this.leftWingTip);
        this.leftFrontLeg = new NopModelPart(256, 256);
        this.leftFrontLeg.setPos(12.0f, 20.0f, 2.0f);
        this.leftFrontLeg.addBox("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 0.0f, 112, 104);
        this.leftFrontLegTip = new NopModelPart(256, 256);
        this.leftFrontLegTip.setPos(0.0f, 20.0f, -1.0f);
        this.leftFrontLegTip.addBox("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 0.0f, 226, 138);
        this.leftFrontLeg.addChild(this.leftFrontLegTip);
        this.leftFrontFoot = new NopModelPart(256, 256);
        this.leftFrontFoot.setPos(0.0f, 23.0f, 0.0f);
        this.leftFrontFoot.addBox("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 0.0f, 144, 104);
        this.leftFrontLegTip.addChild(this.leftFrontFoot);
        this.leftRearLeg = new NopModelPart(256, 256);
        this.leftRearLeg.setPos(16.0f, 16.0f, 42.0f);
        this.leftRearLeg.addBox("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0.0f, 0, 0);
        this.leftRearLegTip = new NopModelPart(256, 256);
        this.leftRearLegTip.setPos(0.0f, 32.0f, -4.0f);
        this.leftRearLegTip.addBox("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 0.0f, 196, 0);
        this.leftRearLeg.addChild(this.leftRearLegTip);
        this.leftRearFoot = new NopModelPart(256, 256);
        this.leftRearFoot.setPos(0.0f, 31.0f, 4.0f);
        this.leftRearFoot.addBox("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 0.0f, 112, 0);
        this.leftRearLegTip.addChild(this.leftRearFoot);
        this.rightWing = new NopModelPart(256, 256);
        this.rightWing.setPos(-12.0f, 5.0f, 2.0f);
        this.rightWing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8, 0.0f, 112, 88);
        this.rightWing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 88);
        this.rightWingTip = new NopModelPart(256, 256);
        this.rightWingTip.setPos(-56.0f, 0.0f, 0.0f);
        this.rightWingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4, 0.0f, 112, 136);
        this.rightWingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56, 0.0f, -56, 144);
        this.rightWing.addChild(this.rightWingTip);
        this.rightFrontLeg = new NopModelPart(256, 256);
        this.rightFrontLeg.setPos(-12.0f, 20.0f, 2.0f);
        this.rightFrontLeg.addBox("main", -4.0f, -4.0f, -4.0f, 8, 24, 8, 0.0f, 112, 104);
        this.rightFrontLegTip = new NopModelPart(256, 256);
        this.rightFrontLegTip.setPos(0.0f, 20.0f, -1.0f);
        this.rightFrontLegTip.addBox("main", -3.0f, -1.0f, -3.0f, 6, 24, 6, 0.0f, 226, 138);
        this.rightFrontLeg.addChild(this.rightFrontLegTip);
        this.rightFrontFoot = new NopModelPart(256, 256);
        this.rightFrontFoot.setPos(0.0f, 23.0f, 0.0f);
        this.rightFrontFoot.addBox("main", -4.0f, 0.0f, -12.0f, 8, 4, 16, 0.0f, 144, 104);
        this.rightFrontLegTip.addChild(this.rightFrontFoot);
        this.rightRearLeg = new NopModelPart(256, 256);
        this.rightRearLeg.setPos(-16.0f, 16.0f, 42.0f);
        this.rightRearLeg.addBox("main", -8.0f, -4.0f, -8.0f, 16, 32, 16, 0.0f, 0, 0);
        this.rightRearLegTip = new NopModelPart(256, 256);
        this.rightRearLegTip.setPos(0.0f, 32.0f, -4.0f);
        this.rightRearLegTip.addBox("main", -6.0f, -2.0f, 0.0f, 12, 32, 12, 0.0f, 196, 0);
        this.rightRearLeg.addChild(this.rightRearLegTip);
        this.rightRearFoot = new NopModelPart(256, 256);
        this.rightRearFoot.setPos(0.0f, 31.0f, 4.0f);
        this.rightRearFoot.addBox("main", -9.0f, 0.0f, -20.0f, 18, 6, 24, 0.0f, 112, 0);
        this.rightRearLegTip.addChild(this.rightRearFoot);
    }

    public void m_6973_(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
    }

    public void m_6839_(Entity entityliving, float animationPos, float animationSpeed, float f2) {
        this.field_40317_s = f2;
        this.entitydragon = (EntityNpcDragon)entityliving;
        this.animationPos = animationPos;
        this.animationSpeed = animationSpeed;
    }

    public void m_7695_(PoseStack mStack, VertexConsumer iVertex, int lightmapUV, int packedOverlayIn, float red, float green, float blue, float alpha) {
        mStack.m_85836_();
        float f6 = this.entitydragon.prevAnimTime + (this.entitydragon.animTime - this.entitydragon.prevAnimTime) * this.field_40317_s;
        this.jaw.xRot = (float)(Math.sin(f6 * (float)Math.PI * 2.0f) + 1.0) * 0.2f;
        float f7 = (float)(Math.sin(f6 * (float)Math.PI * 2.0f - 1.0f) + 1.0);
        f7 = (f7 * f7 * 1.0f + f7 * 2.0f) * 0.05f;
        mStack.m_252880_(0.0f, f7 - 2.0f, -3.0f);
        mStack.m_252781_(Axis.f_252529_.m_252977_(f7 * 2.0f));
        float f8 = -30.0f;
        float f9 = 22.0f;
        float f10 = 0.0f;
        float f11 = 1.5f;
        double[] ad = this.entitydragon.getMovementOffsets(6, this.field_40317_s);
        float f12 = this.func_40307_a(this.entitydragon.getMovementOffsets(5, this.field_40317_s)[0] - this.entitydragon.getMovementOffsets(10, this.field_40317_s)[0]);
        float f13 = this.func_40307_a(this.entitydragon.getMovementOffsets(5, this.field_40317_s)[0] + (double)(f12 / 2.0f));
        f8 += 2.0f;
        float f14 = 0.0f;
        float f15 = f6 * 3.141593f * 2.0f;
        f8 = 20.0f;
        f9 = -12.0f;
        for (int i = 0; i < 5; ++i) {
            double[] ad3 = this.entitydragon.getMovementOffsets(5 - i, this.field_40317_s);
            f14 = (float)Math.cos((float)i * 0.45f + f15) * 0.15f;
            this.neck.yRot = this.func_40307_a(ad3[0] - ad[0]) * (float)Math.PI / 180.0f * f11;
            this.neck.xRot = f14 + (float)(ad3[1] - ad[1]) * (float)Math.PI / 180.0f * f11 * 5.0f;
            this.neck.zRot = -this.func_40307_a(ad3[0] - (double)f13) * (float)Math.PI / 180.0f * f11;
            this.neck.y = f8;
            this.neck.z = f9;
            this.neck.x = f10;
            f8 = (float)((double)f8 + Math.sin(this.neck.xRot) * 10.0);
            f9 = (float)((double)f9 - Math.cos(this.neck.yRot) * Math.cos(this.neck.xRot) * 10.0);
            f10 = (float)((double)f10 - Math.sin(this.neck.yRot) * Math.cos(this.neck.xRot) * 10.0);
            this.neck.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        }
        this.head.y = f8;
        this.head.z = f9;
        this.head.x = f10;
        double[] ad1 = this.entitydragon.getMovementOffsets(0, this.field_40317_s);
        this.head.yRot = this.func_40307_a(ad1[0] - ad[0]) * (float)Math.PI / 180.0f * 1.0f;
        this.head.zRot = -this.func_40307_a(ad1[0] - (double)f13) * (float)Math.PI / 180.0f * 1.0f;
        this.head.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        mStack.m_85836_();
        mStack.m_252880_(0.0f, 1.0f, 0.0f);
        if (this.entitydragon.m_20096_()) {
            mStack.m_252781_(Axis.f_252403_.m_252977_(-f12 * f11 * 0.3f));
        } else {
            mStack.m_252781_(Axis.f_252403_.m_252977_(-f12 * f11 * 1.0f));
        }
        mStack.m_252880_(0.0f, -1.18f, 0.0f);
        this.body.zRot = 0.0f;
        this.body.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        if (this.entitydragon.m_20096_()) {
            this.leftWing.xRot = 0.25f;
            this.leftWing.yRot = -0.95f;
            this.leftWing.zRot = 0.5f;
            this.rightWing.xRot = this.leftWing.xRot;
            this.rightWing.yRot = -this.leftWing.yRot;
            this.rightWing.zRot = -this.leftWing.zRot;
            this.leftWingTip.zRot = 0.4f;
            this.rightWingTip.zRot = -0.4f;
            this.leftFrontLeg.xRot = this.rightFrontLeg.xRot = Mth.m_14089_((float)((float)((double)(this.animationPos * 0.6662f) + Math.PI))) * 0.6f * this.animationSpeed + 0.45f + f7 * 0.5f;
            this.leftRearLeg.xRot = this.rightRearLeg.xRot = Mth.m_14089_((float)(this.animationPos * 0.6662f + 0.0f)) * 0.6f * this.animationSpeed + 0.75f + f7 * 0.5f;
            this.leftFrontLegTip.xRot = this.rightFrontLegTip.xRot = -1.3f - f7 * 1.2f;
            this.leftFrontFoot.xRot = this.rightFrontFoot.xRot = 0.85f + f7 * 0.5f;
            this.leftRearLegTip.xRot = this.rightRearLegTip.xRot = -1.6f - f7 * 0.8f;
            this.rightRearLegTip.y = 20.0f;
            this.leftRearLegTip.y = 20.0f;
            this.rightRearLegTip.z = 2.0f;
            this.leftRearLegTip.z = 2.0f;
            this.leftRearFoot.xRot = this.rightRearFoot.xRot = 0.85f + f7 * 0.2f;
            this.leftFrontLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.rightFrontLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.leftRearLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.rightRearLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.leftWing.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.rightWing.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        } else {
            float f16 = f6 * (float)Math.PI * 2.0f;
            this.leftWing.xRot = 0.125f - (float)Math.cos(f16) * 0.2f;
            this.leftWing.yRot = -0.25f;
            this.leftWing.zRot = -((float)(Math.sin(f16) + 0.125)) * 0.8f;
            this.rightWing.xRot = this.leftWing.xRot;
            this.rightWing.yRot = -this.leftWing.yRot;
            this.rightWing.zRot = -this.leftWing.zRot;
            this.leftWingTip.zRot = (float)(Math.sin(f16 + 2.0f) + 0.5) * 0.75f;
            this.rightWingTip.zRot = -this.leftWingTip.zRot;
            this.rightRearLegTip.y = 32.0f;
            this.leftRearLegTip.y = 32.0f;
            this.rightRearLegTip.z = -2.0f;
            this.leftRearLegTip.z = -2.0f;
            this.leftRearLeg.xRot = this.rightRearLeg.xRot = 1.0f + f7 * 0.1f;
            this.leftRearLegTip.xRot = this.rightRearLegTip.xRot = 0.5f + f7 * 0.1f;
            this.leftRearFoot.xRot = this.rightRearFoot.xRot = 0.75f + f7 * 0.1f;
            this.leftFrontLeg.xRot = this.rightFrontLeg.xRot = 1.3f + f7 * 0.1f;
            this.leftFrontLegTip.xRot = this.rightFrontLegTip.xRot = -0.5f - f7 * 0.1f;
            this.leftFrontFoot.xRot = this.rightFrontFoot.xRot = 0.75f + f7 * 0.1f;
            this.leftWing.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.rightWing.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.leftFrontLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.rightFrontLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.leftRearLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn);
            this.rightRearLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        }
        mStack.m_85849_();
        f14 = -((float)Math.sin(f6 * 3.141593f * 2.0f)) * 0.0f;
        f15 = f6 * (float)Math.PI * 2.0f;
        f8 = 10.0f;
        f9 = 60.0f;
        f10 = 0.0f;
        ad = this.entitydragon.getMovementOffsets(11, this.field_40317_s);
        for (int k = 0; k < 12; ++k) {
            double[] ad2 = this.entitydragon.getMovementOffsets(12 + k, this.field_40317_s);
            f14 = (float)((double)f14 + Math.sin((float)k * 0.45f + f15) * (double)0.05f);
            this.neck.yRot = (this.func_40307_a(ad2[0] - ad[0]) * f11 + 180.0f) * (float)Math.PI / 180.0f;
            this.neck.xRot = f14 + (float)(ad2[1] - ad[1]) * (float)Math.PI / 180.0f * f11 * 5.0f;
            this.neck.zRot = this.func_40307_a(ad2[0] - (double)f13) * (float)Math.PI / 180.0f * f11;
            this.neck.y = f8;
            this.neck.z = f9;
            this.neck.x = f10;
            f8 = (float)((double)f8 + Math.sin(this.neck.xRot) * 10.0);
            f9 = (float)((double)f9 - Math.cos(this.neck.yRot) * Math.cos(this.neck.xRot) * 10.0);
            f10 = (float)((double)f10 - Math.sin(this.neck.yRot) * Math.cos(this.neck.xRot) * 10.0);
            this.neck.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        }
        mStack.m_85849_();
    }

    private float func_40307_a(double d) {
        while (d >= 180.0) {
            d -= 360.0;
        }
        while (d < -180.0) {
            d += 360.0;
        }
        return (float)d;
    }
}

