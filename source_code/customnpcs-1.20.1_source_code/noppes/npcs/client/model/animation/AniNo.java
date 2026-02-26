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

public class AniNo
implements AnimationBase {
    @Override
    public void animatePost(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
        float ticks = (float)(entity.f_19797_ - animationStart) / 8.0f;
        float ticks2 = (float)(entity.f_19797_ + 1 - animationStart) / 8.0f;
        ticks += (ticks2 - ticks) * Minecraft.m_91087_().m_91297_();
        float ani = (ticks %= 2.0f) - 0.5f;
        if (ticks > 1.0f) {
            ani = 1.5f - ticks;
        }
        model.f_102808_.f_104204_ = ani;
    }

    @Override
    public void animatePre(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
    }
}

