/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.model.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import noppes.npcs.client.model.animation.AnimationBase;

public class AniDancing
implements AnimationBase {
    @Override
    public void animatePost(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
        float dancing = (float)entity.f_19797_ / 4.0f;
        float dancing2 = (float)(entity.f_19797_ + 1) / 4.0f;
        dancing += (dancing2 - dancing) * Minecraft.m_91087_().m_91297_();
        float x = (float)Math.sin(dancing);
        float y = (float)Math.abs(Math.cos(dancing));
        model.f_102809_.f_104200_ = model.f_102808_.f_104200_ = x * 0.75f;
        model.f_102809_.f_104201_ = model.f_102808_.f_104201_ = y * 1.25f - 0.02f + (float)(entity.m_6047_() ? 4 : 0);
        model.f_102809_.f_104202_ = model.f_102808_.f_104202_ = -y * 0.75f;
        model.f_102812_.f_104200_ += x * 0.25f;
        model.f_102812_.f_104201_ += y * 1.25f;
        model.f_102811_.f_104200_ += x * 0.25f;
        model.f_102811_.f_104201_ += y * 1.25f;
        model.f_102810_.f_104200_ = x * 0.25f;
    }

    @Override
    public void animatePre(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
    }
}

