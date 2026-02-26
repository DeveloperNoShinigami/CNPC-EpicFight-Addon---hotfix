/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.model.NopModelPart;

public class ModelNPCGolem
extends EntityModel {
    public NopModelPart head;
    public NopModelPart hat;
    public NopModelPart body;
    public NopModelPart rightArm;
    public NopModelPart leftArm;
    public NopModelPart rightLeg;
    public NopModelPart leftLeg;
    private NopModelPart bipedLowerBody;

    public ModelNPCGolem(float scale) {
        this.init(0.0f, 0.0f);
    }

    public void init(float f, float f1) {
        int short1 = 128;
        int short2 = 128;
        float f2 = -7.0f;
        this.head = new NopModelPart(128, 128).setTexSize(short1, short2);
        this.head.setPos(0.0f, f2, -2.0f);
        this.head.texOffs(0, 0).addBox(-4.0f, -12.0f, -5.5f, 8.0f, 10.0f, 8.0f, f);
        this.head.texOffs(24, 0).addBox(-1.0f, -5.0f, -7.5f, 2.0f, 4.0f, 2.0f, f);
        this.hat = new NopModelPart(128, 128).setTexSize(short1, short2);
        this.hat.setPos(0.0f, f2, -2.0f);
        this.hat.texOffs(0, 85).addBox(-4.0f, -12.0f, -5.5f, 8.0f, 10.0f, 8.0f, f + 0.5f);
        this.body = new NopModelPart(128, 128).setTexSize(short1, short2);
        this.body.setPos(0.0f, 0.0f + f2, 0.0f);
        this.body.texOffs(0, 40).addBox(-9.0f, -2.0f, -6.0f, 18.0f, 12.0f, 11.0f, f + 0.2f);
        this.body.texOffs(0, 21).addBox(-9.0f, -2.0f, -6.0f, 18.0f, 8.0f, 11.0f, f);
        this.bipedLowerBody = new NopModelPart(128, 128).setTexSize(short1, short2);
        this.bipedLowerBody.setPos(0.0f, 0.0f + f2, 0.0f);
        this.bipedLowerBody.texOffs(0, 70).addBox(-4.5f, 10.0f, -3.0f, 9.0f, 5.0f, 6.0f, f + 0.5f);
        this.bipedLowerBody.texOffs(30, 70).addBox(-4.5f, 6.0f, -3.0f, 9.0f, 9.0f, 6.0f, f + 0.4f);
        this.rightArm = new NopModelPart(128, 128).setTexSize(short1, short2);
        this.rightArm.setPos(0.0f, f2, 0.0f);
        this.rightArm.texOffs(60, 21).addBox(-13.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f, f + 0.2f);
        this.rightArm.texOffs(80, 21).addBox(-13.0f, -2.5f, -3.0f, 4.0f, 20.0f, 6.0f, f);
        this.rightArm.texOffs(100, 21).addBox(-13.0f, -2.5f, -3.0f, 4.0f, 20.0f, 6.0f, f + 1.0f);
        this.leftArm = new NopModelPart(128, 128).setTexSize(short1, short2);
        this.leftArm.setPos(0.0f, f2, 0.0f);
        this.leftArm.texOffs(60, 58).addBox(9.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f, f + 0.2f);
        this.leftArm.texOffs(80, 58).addBox(9.0f, -2.5f, -3.0f, 4.0f, 20.0f, 6.0f, f);
        this.leftArm.texOffs(100, 58).addBox(9.0f, -2.5f, -3.0f, 4.0f, 20.0f, 6.0f, f + 1.0f);
        this.leftLeg = new NopModelPart(64, 64, 0, 22).setTexSize(short1, short2);
        this.leftLeg.setPos(-4.0f, 18.0f + f2, 0.0f);
        this.leftLeg.texOffs(37, 0).addBox(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f, f);
        this.rightLeg = new NopModelPart(64, 64, 0, 22).setTexSize(short1, short2);
        this.rightLeg.mirror = true;
        this.rightLeg.texOffs(60, 0).addBox(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f, f);
        this.rightLeg.setPos(5.0f, 18.0f + f2, 0.0f);
    }

    public void m_7695_(PoseStack mStack, VertexConsumer iVertex, int lightmapUV, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.head.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.hat.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.body.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.rightArm.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.leftArm.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.rightLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.leftLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.bipedLowerBody.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
    }

    public void m_6973_(Entity entity, float par1, float limbSwingAmount, float par3, float par4, float par5) {
        EntityNPCInterface npc = (EntityNPCInterface)entity;
        this.f_102609_ = npc.m_20159_();
        this.head.yRot = par4 / 57.295776f;
        this.head.xRot = par5 / 57.295776f;
        this.hat.yRot = this.head.yRot;
        this.hat.xRot = this.head.xRot;
        this.leftLeg.xRot = -1.5f * this.func_78172_a(par1, 13.0f) * limbSwingAmount;
        this.rightLeg.xRot = 1.5f * this.func_78172_a(par1, 13.0f) * limbSwingAmount;
        this.leftLeg.yRot = 0.0f;
        this.rightLeg.yRot = 0.0f;
        float f6 = Mth.m_14031_((float)(this.f_102608_ * (float)Math.PI));
        float f7 = Mth.m_14031_((float)((16.0f - (1.0f - this.f_102608_) * (1.0f - this.f_102608_)) * (float)Math.PI));
        if ((double)this.f_102608_ > 0.0) {
            this.rightArm.zRot = 0.0f;
            this.leftArm.zRot = 0.0f;
            this.rightArm.yRot = -(0.1f - f6 * 0.6f);
            this.leftArm.yRot = 0.1f - f6 * 0.6f;
            this.rightArm.xRot = 0.0f;
            this.leftArm.xRot = 0.0f;
            this.rightArm.xRot = -1.5707964f;
            this.leftArm.xRot = -1.5707964f;
            this.rightArm.xRot -= f6 * 1.2f - f7 * 0.4f;
            this.leftArm.xRot -= f6 * 1.2f - f7 * 0.4f;
        } else {
            this.rightArm.xRot = (-0.2f + 1.5f * this.func_78172_a(par1, 13.0f)) * limbSwingAmount;
            this.leftArm.xRot = (-0.2f - 1.5f * this.func_78172_a(par1, 13.0f)) * limbSwingAmount;
            this.body.yRot = 0.0f;
            this.rightArm.yRot = 0.0f;
            this.leftArm.yRot = 0.0f;
            this.rightArm.zRot = 0.0f;
            this.leftArm.zRot = 0.0f;
        }
        if (this.f_102609_) {
            this.rightArm.xRot += -0.62831855f;
            this.leftArm.xRot += -0.62831855f;
            this.leftLeg.xRot = -1.2566371f;
            this.rightLeg.xRot = -1.2566371f;
            this.leftLeg.yRot = 0.31415927f;
            this.rightLeg.yRot = -0.31415927f;
        }
    }

    private float func_78172_a(float par1, float limbSwingAmount) {
        return (Math.abs(par1 % limbSwingAmount - limbSwingAmount * 0.5f) - limbSwingAmount * 0.25f) / (limbSwingAmount * 0.25f);
    }
}

