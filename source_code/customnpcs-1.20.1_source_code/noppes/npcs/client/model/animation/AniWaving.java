/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.model.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import noppes.npcs.client.model.animation.AnimationBase;

public class AniWaving
implements AnimationBase {
    @Override
    public void animatePre(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
    }

    @Override
    public void animatePost(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
        float f = Mth.m_14031_((float)((float)entity.f_19797_ * 0.27f));
        float f2 = Mth.m_14031_((float)((float)(entity.f_19797_ + 1) * 0.27f));
        f += (f2 - f) * Minecraft.m_91087_().m_91297_();
        model.f_102811_.f_104203_ = -0.1f;
        model.f_102811_.f_104204_ = 0.0f;
        model.f_102811_.f_104205_ = (float)(2.141592653589793 - (double)(f * 0.5f));
    }
}

