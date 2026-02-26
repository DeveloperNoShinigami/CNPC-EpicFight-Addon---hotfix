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

public class AniCrawling
implements AnimationBase {
    @Override
    public void animatePre(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
    }

    @Override
    public void animatePost(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
        model.f_102808_.f_104205_ = -netHeadYaw / 57.295776f;
        model.f_102808_.f_104204_ = 0.0f;
        model.f_102809_.f_104203_ = model.f_102808_.f_104203_ = -0.95993114f;
        model.f_102809_.f_104204_ = model.f_102808_.f_104204_;
        model.f_102809_.f_104205_ = model.f_102808_.f_104205_;
        if ((double)limbSwingAmount > 0.25) {
            limbSwingAmount = 0.25f;
        }
        float movement = Mth.m_14089_((float)(limbSwing * 0.8f + (float)Math.PI)) * limbSwingAmount;
        model.f_102812_.f_104203_ = (float)Math.PI - movement * 0.25f;
        model.f_102812_.f_104204_ = movement * -0.46f;
        model.f_102812_.f_104205_ = movement * -0.2f;
        model.f_102812_.f_104201_ = 2.0f - movement * 9.0f;
        model.f_102811_.f_104203_ = (float)Math.PI + movement * 0.25f;
        model.f_102811_.f_104204_ = movement * -0.4f;
        model.f_102811_.f_104205_ = movement * -0.2f;
        model.f_102811_.f_104201_ = 2.0f + movement * 9.0f;
        model.f_102810_.f_104204_ = movement * 0.1f;
        model.f_102810_.f_104203_ = 0.0f;
        model.f_102810_.f_104205_ = movement * 0.1f;
        model.f_102814_.f_104203_ = movement * 0.1f;
        model.f_102814_.f_104204_ = movement * 0.1f;
        model.f_102814_.f_104205_ = -0.122173056f - movement * 0.25f;
        model.f_102814_.f_104201_ = 10.4f + movement * 9.0f;
        model.f_102814_.f_104202_ = movement * 0.6f;
        model.f_102813_.f_104203_ = movement * -0.1f;
        model.f_102813_.f_104204_ = movement * 0.1f;
        model.f_102813_.f_104205_ = 0.122173056f - movement * 0.25f;
        model.f_102813_.f_104201_ = 10.4f - movement * 9.0f;
        model.f_102813_.f_104202_ = movement * -0.6f;
    }
}

