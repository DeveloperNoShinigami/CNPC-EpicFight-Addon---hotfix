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

public class ModelPonyArmor
extends EntityModel {
    private boolean rainboom;
    public NopModelPart head;
    public NopModelPart Body;
    public NopModelPart BodyBack;
    public NopModelPart rightarm;
    public NopModelPart LeftArm;
    public NopModelPart RightLeg;
    public NopModelPart LeftLeg;
    public NopModelPart rightarm2;
    public NopModelPart LeftArm2;
    public NopModelPart RightLeg2;
    public NopModelPart LeftLeg2;
    public boolean isPegasus = false;
    public boolean isUnicorn = false;
    public boolean isSleeping = false;
    public boolean isFlying = false;
    public boolean isGlow = false;
    public boolean isSneak = false;
    public boolean aimedBow;
    public int heldItemRight;

    public ModelPonyArmor(float f) {
        this.init(f, 0.0f);
    }

    public void init(float strech, float f) {
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        this.head = new NopModelPart(64, 32, 0, 0);
        this.head.addBox(-4.0f, -4.0f, -6.0f, 8.0f, 8.0f, 8.0f, strech);
        this.head.setPos(f2, f3, f4);
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        this.Body = new NopModelPart(64, 32, 16, 16);
        this.Body.addBox(-4.0f, 4.0f, -2.0f, 8.0f, 8.0f, 4.0f, strech);
        this.Body.setPos(f5, f6 + f, f7);
        this.BodyBack = new NopModelPart(64, 32, 0, 0);
        this.BodyBack.addBox(-4.0f, 4.0f, 6.0f, 8.0f, 8.0f, 8.0f, strech);
        this.BodyBack.setPos(f5, f6 + f, f7);
        this.rightarm = new NopModelPart(64, 32, 0, 16);
        this.rightarm.addBox(-2.0f, 4.0f, -2.0f, 4.0f, 12.0f, 4.0f, strech);
        this.rightarm.setPos(-3.0f, 8.0f + f, 0.0f);
        this.LeftArm = new NopModelPart(64, 32, 0, 16);
        this.LeftArm.mirror = true;
        this.LeftArm.addBox(-2.0f, 4.0f, -2.0f, 4.0f, 12.0f, 4.0f, strech);
        this.LeftArm.setPos(3.0f, 8.0f + f, 0.0f);
        this.RightLeg = new NopModelPart(64, 32, 0, 16);
        this.RightLeg.addBox(-2.0f, 4.0f, -2.0f, 4.0f, 12.0f, 4.0f, strech);
        this.RightLeg.setPos(-3.0f, 0.0f + f, 0.0f);
        this.LeftLeg = new NopModelPart(64, 32, 0, 16);
        this.LeftLeg.mirror = true;
        this.LeftLeg.addBox(-2.0f, 4.0f, -2.0f, 4.0f, 12.0f, 4.0f, strech);
        this.LeftLeg.setPos(3.0f, 0.0f + f, 0.0f);
        this.rightarm2 = new NopModelPart(64, 32, 0, 16);
        this.rightarm2.addBox(-2.0f, 4.0f, -2.0f, 4.0f, 12.0f, 4.0f, strech * 0.5f);
        this.rightarm2.setPos(-3.0f, 8.0f + f, 0.0f);
        this.LeftArm2 = new NopModelPart(64, 32, 0, 16);
        this.LeftArm2.mirror = true;
        this.LeftArm2.addBox(-2.0f, 4.0f, -2.0f, 4.0f, 12.0f, 4.0f, strech * 0.5f);
        this.LeftArm2.setPos(3.0f, 8.0f + f, 0.0f);
        this.RightLeg2 = new NopModelPart(64, 32, 0, 16);
        this.RightLeg2.addBox(-2.0f, 4.0f, -2.0f, 4.0f, 12.0f, 4.0f, strech * 0.5f);
        this.RightLeg2.setPos(-3.0f, 0.0f + f, 0.0f);
        this.LeftLeg2 = new NopModelPart(64, 32, 0, 16);
        this.LeftLeg2.mirror = true;
        this.LeftLeg2.addBox(-2.0f, 4.0f, -2.0f, 4.0f, 12.0f, 4.0f, strech * 0.5f);
        this.LeftLeg2.setPos(3.0f, 0.0f + f, 0.0f);
    }

    public void m_6973_(Entity entity, float aniPosition, float aniSpeed, float age, float yHead, float xHead) {
        float f11;
        float f10;
        float f9;
        float f8;
        float f7;
        float f6;
        EntityNPCInterface npc = (EntityNPCInterface)entity;
        if (!this.f_102609_) {
            boolean bl = this.f_102609_ = npc.currentAnimation == 1;
        }
        if (this.isSneak && (npc.currentAnimation == 7 || npc.currentAnimation == 2)) {
            this.isSneak = false;
        }
        this.rainboom = false;
        if (this.isSleeping) {
            f6 = 1.4f;
            f7 = 0.1f;
        } else {
            f6 = yHead / 57.29578f;
            f7 = xHead / 57.29578f;
        }
        this.head.yRot = f6;
        this.head.xRot = f7;
        if (!this.isFlying || !this.isPegasus) {
            f8 = Mth.m_14089_((float)(aniPosition * 0.6662f + 3.141593f)) * 0.6f * aniSpeed;
            f9 = Mth.m_14089_((float)(aniPosition * 0.6662f)) * 0.6f * aniSpeed;
            f10 = Mth.m_14089_((float)(aniPosition * 0.6662f)) * 0.3f * aniSpeed;
            f11 = Mth.m_14089_((float)(aniPosition * 0.6662f + 3.141593f)) * 0.3f * aniSpeed;
            this.rightarm.yRot = 0.0f;
            this.LeftArm.yRot = 0.0f;
            this.RightLeg.yRot = 0.0f;
            this.LeftLeg.yRot = 0.0f;
            this.rightarm2.yRot = 0.0f;
            this.LeftArm2.yRot = 0.0f;
            this.RightLeg2.yRot = 0.0f;
            this.LeftLeg2.yRot = 0.0f;
        } else {
            if (aniSpeed < 0.9999f) {
                this.rainboom = false;
                f8 = Mth.m_14031_((float)(0.0f - aniSpeed * 0.5f));
                f9 = Mth.m_14031_((float)(0.0f - aniSpeed * 0.5f));
                f10 = Mth.m_14031_((float)(aniSpeed * 0.5f));
                f11 = Mth.m_14031_((float)(aniSpeed * 0.5f));
            } else {
                this.rainboom = true;
                f8 = 4.712f;
                f9 = 4.712f;
                f10 = 1.571f;
                f11 = 1.571f;
            }
            this.rightarm.yRot = 0.2f;
            this.LeftArm.yRot = -0.2f;
            this.RightLeg.yRot = -0.2f;
            this.LeftLeg.yRot = 0.2f;
            this.rightarm2.yRot = 0.2f;
            this.LeftArm2.yRot = -0.2f;
            this.RightLeg2.yRot = -0.2f;
            this.LeftLeg2.yRot = 0.2f;
        }
        if (this.isSleeping) {
            f8 = 4.712f;
            f9 = 4.712f;
            f10 = 1.571f;
            f11 = 1.571f;
        }
        this.rightarm.xRot = f8;
        this.LeftArm.xRot = f9;
        this.RightLeg.xRot = f10;
        this.LeftLeg.xRot = f11;
        this.rightarm.zRot = 0.0f;
        this.LeftArm.zRot = 0.0f;
        this.rightarm2.xRot = f8;
        this.LeftArm2.xRot = f9;
        this.RightLeg2.xRot = f10;
        this.LeftLeg2.xRot = f11;
        this.rightarm2.zRot = 0.0f;
        this.LeftArm2.zRot = 0.0f;
        if (this.heldItemRight != 0 && !this.rainboom && !this.isUnicorn) {
            this.rightarm.xRot = this.rightarm.xRot * 0.5f - 0.3141593f;
            this.rightarm2.xRot = this.rightarm2.xRot * 0.5f - 0.3141593f;
        }
        float f13 = Mth.m_14031_((float)this.Body.yRot) * 5.0f;
        float f14 = Mth.m_14089_((float)this.Body.yRot) * 5.0f;
        float f15 = 4.0f;
        if (this.isSneak && !this.isFlying) {
            f15 = 0.0f;
        }
        if (this.isSleeping) {
            f15 = 2.6f;
        }
        if (this.rainboom) {
            this.rightarm.z = f13 + 2.0f;
            this.rightarm2.z = f13 + 2.0f;
            this.LeftArm.z = 0.0f - f13 + 2.0f;
            this.LeftArm2.z = 0.0f - f13 + 2.0f;
        } else {
            this.rightarm.z = f13 + 1.0f;
            this.rightarm2.z = f13 + 1.0f;
            this.LeftArm.z = 0.0f - f13 + 1.0f;
            this.LeftArm2.z = 0.0f - f13 + 1.0f;
        }
        this.rightarm.x = 0.0f - f14 - 1.0f + f15;
        this.rightarm2.x = 0.0f - f14 - 1.0f + f15;
        this.LeftArm.x = f14 + 1.0f - f15;
        this.LeftArm2.x = f14 + 1.0f - f15;
        this.RightLeg.x = 0.0f - f14 - 1.0f + f15;
        this.RightLeg2.x = 0.0f - f14 - 1.0f + f15;
        this.LeftLeg.x = f14 + 1.0f - f15;
        this.LeftLeg2.x = f14 + 1.0f - f15;
        this.rightarm.yRot += this.Body.yRot;
        this.rightarm2.yRot += this.Body.yRot;
        this.LeftArm.yRot += this.Body.yRot;
        this.LeftArm2.yRot += this.Body.yRot;
        this.LeftArm.xRot += this.Body.yRot;
        this.LeftArm2.xRot += this.Body.yRot;
        this.rightarm.y = 8.0f;
        this.LeftArm.y = 8.0f;
        this.RightLeg.y = 4.0f;
        this.LeftLeg.y = 4.0f;
        this.rightarm2.y = 8.0f;
        this.LeftArm2.y = 8.0f;
        this.RightLeg2.y = 4.0f;
        this.LeftLeg2.y = 4.0f;
        if (this.isSneak && !this.isFlying) {
            float f35;
            float f33;
            float f31;
            float f17 = 0.4f;
            float f22 = 7.0f;
            float f27 = -4.0f;
            this.Body.xRot = f17;
            this.Body.y = f22;
            this.Body.z = f27;
            this.BodyBack.xRot = f17;
            this.BodyBack.y = f22;
            this.BodyBack.z = f27;
            this.RightLeg.xRot -= 0.0f;
            this.LeftLeg.xRot -= 0.0f;
            this.rightarm.xRot -= 0.4f;
            this.LeftArm.xRot -= 0.4f;
            this.RightLeg.z = 10.0f;
            this.LeftLeg.z = 10.0f;
            this.RightLeg.y = 7.0f;
            this.LeftLeg.y = 7.0f;
            this.RightLeg2.xRot -= 0.0f;
            this.LeftLeg2.xRot -= 0.0f;
            this.rightarm2.xRot -= 0.4f;
            this.LeftArm2.xRot -= 0.4f;
            this.RightLeg2.z = 10.0f;
            this.LeftLeg2.z = 10.0f;
            this.RightLeg2.y = 7.0f;
            this.LeftLeg2.y = 7.0f;
            if (this.isSleeping) {
                f31 = 2.0f;
                f33 = -1.0f;
                f35 = 1.0f;
            } else {
                f31 = 6.0f;
                f33 = -2.0f;
                f35 = 0.0f;
            }
            this.head.y = f31;
            this.head.z = f33;
            this.head.x = f35;
        } else {
            float f18 = 0.0f;
            float f23 = 0.0f;
            float f28 = 0.0f;
            this.Body.xRot = f18;
            this.Body.y = f23;
            this.Body.z = f28;
            this.BodyBack.xRot = f18;
            this.BodyBack.y = f23;
            this.BodyBack.z = f28;
            this.RightLeg.z = 10.0f;
            this.LeftLeg.z = 10.0f;
            this.RightLeg.y = 8.0f;
            this.LeftLeg.y = 8.0f;
            this.RightLeg2.z = 10.0f;
            this.LeftLeg2.z = 10.0f;
            this.RightLeg2.y = 8.0f;
            this.LeftLeg2.y = 8.0f;
            float f36 = 0.0f;
            float f37 = 0.0f;
            this.head.y = f36;
            this.head.z = f37;
        }
        if (this.isSleeping) {
            this.rightarm.z += 6.0f;
            this.LeftArm.z += 6.0f;
            this.RightLeg.z -= 8.0f;
            this.LeftLeg.z -= 8.0f;
            this.rightarm.y += 2.0f;
            this.LeftArm.y += 2.0f;
            this.RightLeg.y += 2.0f;
            this.LeftLeg.y += 2.0f;
            this.rightarm2.z += 6.0f;
            this.LeftArm2.z += 6.0f;
            this.RightLeg2.z -= 8.0f;
            this.LeftLeg2.z -= 8.0f;
            this.rightarm2.y += 2.0f;
            this.LeftArm2.y += 2.0f;
            this.RightLeg2.y += 2.0f;
            this.LeftLeg2.y += 2.0f;
        }
        if (this.aimedBow && !this.isUnicorn) {
            float f20 = 0.0f;
            float f25 = 0.0f;
            this.rightarm.zRot = 0.0f;
            this.rightarm.yRot = -(0.1f - f20 * 0.6f) + this.head.yRot;
            this.rightarm.xRot = 4.712f + this.head.xRot;
            this.rightarm.xRot -= f20 * 1.2f - f25 * 0.4f;
            float f29 = age;
            this.rightarm.zRot += Mth.m_14089_((float)(f29 * 0.09f)) * 0.05f + 0.05f;
            this.rightarm.xRot += Mth.m_14031_((float)(f29 * 0.067f)) * 0.05f;
            this.rightarm2.zRot = 0.0f;
            this.rightarm2.yRot = -(0.1f - f20 * 0.6f) + this.head.yRot;
            this.rightarm2.xRot = 4.712f + this.head.xRot;
            this.rightarm2.xRot -= f20 * 1.2f - f25 * 0.4f;
            this.rightarm2.zRot += Mth.m_14089_((float)(f29 * 0.09f)) * 0.05f + 0.05f;
            this.rightarm2.xRot += Mth.m_14031_((float)(f29 * 0.067f)) * 0.05f;
            this.rightarm.z += 1.0f;
            this.rightarm2.z += 1.0f;
        }
    }

    public void m_7695_(PoseStack mStack, VertexConsumer iVertex, int lightmapUV, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.head.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.Body.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.BodyBack.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.LeftArm.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.rightarm.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.LeftLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.RightLeg.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.LeftArm2.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.rightarm2.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.LeftLeg2.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        this.RightLeg2.render(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
    }
}

