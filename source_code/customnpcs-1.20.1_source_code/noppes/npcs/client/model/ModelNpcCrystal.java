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
 *  org.joml.Quaternionf
 */
package noppes.npcs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import noppes.npcs.shared.client.model.NopModelPart;
import org.joml.Quaternionf;

public class ModelNpcCrystal
extends EntityModel {
    private static final float SIN_45 = (float)Math.sin(0.7853981633974483);
    private NopModelPart field_41057_g;
    private NopModelPart field_41058_h = new NopModelPart(64, 32, 0, 0);
    private NopModelPart field_41059_i;
    float ticks;
    float tickCount;

    public ModelNpcCrystal() {
        this.field_41058_h.addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.field_41057_g = new NopModelPart(64, 32, 32, 0);
        this.field_41057_g.addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.field_41059_i = new NopModelPart(64, 32, 0, 16);
        this.field_41059_i.addBox(-6.0f, 16.0f, -6.0f, 12.0f, 4.0f, 12.0f);
    }

    public void m_6973_(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
    }

    public void m_6839_(Entity par1EntityLiving, float f6, float f5, float par9) {
        this.ticks = par9;
        this.tickCount = par1EntityLiving.f_19797_;
    }

    public void m_7695_(PoseStack mStack, VertexConsumer ivertex, int lightmapUV, int packedOverlayIn, float red, float green, float blue, float alpha) {
        mStack.m_85836_();
        mStack.m_85841_(2.0f, 2.0f, 2.0f);
        mStack.m_252880_(0.0f, -0.5f, 0.0f);
        this.field_41059_i.render(mStack, ivertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        float f = this.tickCount + this.ticks;
        float f1 = Mth.m_14031_((float)(f * 0.2f)) / 2.0f + 0.5f;
        f1 = f1 * f1 + f1;
        float par3 = f * 3.0f;
        float par4 = f1 * 0.2f;
        mStack.m_252781_(Axis.f_252436_.m_252977_(par3));
        mStack.m_252880_(0.0f, 0.1f + par4, 0.0f);
        mStack.m_252781_(new Quaternionf().setAngleAxis(1.0471976f, SIN_45, 0.0f, SIN_45));
        this.field_41058_h.render(mStack, ivertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        float sca = 0.875f;
        mStack.m_85841_(sca, sca, sca);
        mStack.m_252781_(new Quaternionf().setAngleAxis(1.0471976f, SIN_45, 0.0f, SIN_45));
        mStack.m_252781_(Axis.f_252436_.m_252977_(par3));
        this.field_41058_h.render(mStack, ivertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        mStack.m_85841_(sca, sca, sca);
        mStack.m_252781_(new Quaternionf().setAngleAxis(1.0471976f, SIN_45, 0.0f, SIN_45));
        mStack.m_252781_(Axis.f_252436_.m_252977_(par3));
        this.field_41057_g.render(mStack, ivertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
        mStack.m_85849_();
    }
}

