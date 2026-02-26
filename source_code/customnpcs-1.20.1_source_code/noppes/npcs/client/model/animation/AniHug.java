/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.model.animation;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import noppes.npcs.client.model.animation.AnimationBase;

public class AniHug
implements AnimationBase {
    @Override
    public void animatePost(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
        float f6 = Mth.m_14031_((float)(model.f_102608_ * 3.141593f));
        float f7 = Mth.m_14031_((float)((1.0f - (1.0f - model.f_102608_) * (1.0f - model.f_102608_)) * 3.141593f));
        model.f_102811_.f_104205_ = 0.0f;
        model.f_102812_.f_104205_ = 0.0f;
        model.f_102811_.f_104204_ = -(0.1f - f6 * 0.6f);
        model.f_102812_.f_104204_ = 0.1f;
        model.f_102811_.f_104203_ = -1.570796f;
        model.f_102812_.f_104203_ = -1.570796f;
        model.f_102811_.f_104203_ -= f6 * 1.2f - f7 * 0.4f;
        model.f_102811_.f_104205_ += Mth.m_14089_((float)(ageInTicks * 0.09f)) * 0.05f + 0.05f;
        model.f_102812_.f_104205_ -= Mth.m_14089_((float)(ageInTicks * 0.09f)) * 0.05f + 0.05f;
        model.f_102811_.f_104203_ += Mth.m_14031_((float)(ageInTicks * 0.067f)) * 0.05f;
        model.f_102812_.f_104203_ -= Mth.m_14031_((float)(ageInTicks * 0.067f)) * 0.05f;
    }

    @Override
    public void animatePre(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
    }
}

