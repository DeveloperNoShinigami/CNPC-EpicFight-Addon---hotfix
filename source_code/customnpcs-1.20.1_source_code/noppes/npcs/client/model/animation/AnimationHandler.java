/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.client.model.animation;

import java.util.HashMap;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.ModelData;
import noppes.npcs.client.model.animation.AniAim;
import noppes.npcs.client.model.animation.AniBlank;
import noppes.npcs.client.model.animation.AniBow;
import noppes.npcs.client.model.animation.AniCrawling;
import noppes.npcs.client.model.animation.AniDancing;
import noppes.npcs.client.model.animation.AniHug;
import noppes.npcs.client.model.animation.AniNo;
import noppes.npcs.client.model.animation.AniPoint;
import noppes.npcs.client.model.animation.AniWaving;
import noppes.npcs.client.model.animation.AniYes;
import noppes.npcs.client.model.animation.AnimationBase;
import noppes.npcs.constants.EnumParts;
import noppes.npcs.entity.EntityNPCInterface;

public class AnimationHandler {
    private static final HashMap<Integer, AnimationBase> ANIMATIONS = new HashMap();

    public static void animateBipedPre(ModelData data, HumanoidModel bipedModel, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        EntityNPCInterface npc = (EntityNPCInterface)livingEntity;
        bipedModel.f_102810_.f_104202_ = 0.0f;
        bipedModel.f_102810_.f_104201_ = 0.0f;
        bipedModel.f_102810_.f_104200_ = 0.0f;
        bipedModel.f_102810_.f_104205_ = 0.0f;
        bipedModel.f_102810_.f_104204_ = 0.0f;
        bipedModel.f_102810_.f_104203_ = 0.0f;
        bipedModel.f_102808_.f_104203_ = 0.0f;
        bipedModel.f_102809_.f_104203_ = 0.0f;
        bipedModel.f_102808_.f_104205_ = 0.0f;
        bipedModel.f_102809_.f_104205_ = 0.0f;
        bipedModel.f_102808_.f_104200_ = 0.0f;
        bipedModel.f_102809_.f_104200_ = 0.0f;
        bipedModel.f_102808_.f_104201_ = 0.0f;
        bipedModel.f_102809_.f_104201_ = 0.0f;
        bipedModel.f_102808_.f_104202_ = 0.0f;
        bipedModel.f_102809_.f_104202_ = 0.0f;
        bipedModel.f_102814_.f_104203_ = 0.0f;
        bipedModel.f_102814_.f_104204_ = 0.0f;
        bipedModel.f_102814_.f_104205_ = 0.0f;
        bipedModel.f_102813_.f_104203_ = 0.0f;
        bipedModel.f_102813_.f_104204_ = 0.0f;
        bipedModel.f_102813_.f_104205_ = 0.0f;
        bipedModel.f_102812_.f_104200_ = 0.0f;
        bipedModel.f_102812_.f_104201_ = 2.0f;
        bipedModel.f_102812_.f_104202_ = 0.0f;
        bipedModel.f_102811_.f_104200_ = 0.0f;
        bipedModel.f_102811_.f_104201_ = 2.0f;
        bipedModel.f_102811_.f_104202_ = 0.0f;
        AnimationBase animation = AnimationHandler.getAnimationFor(npc.currentAnimation);
        if (animation != null) {
            animation.animatePre(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, (Entity)livingEntity, bipedModel, npc.animationStart);
        }
    }

    public static void animateBipedPost(ModelData data, HumanoidModel bipedModel, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        AnimationBase animation;
        EntityNPCInterface npc = (EntityNPCInterface)livingEntity;
        if (livingEntity.m_5803_() && bipedModel.f_102808_.f_104203_ < 0.0f) {
            bipedModel.f_102808_.f_104203_ = 90.0f;
            bipedModel.f_102809_.f_104203_ = 90.0f;
        }
        if ((animation = AnimationHandler.getAnimationFor(npc.currentAnimation)) != null) {
            animation.animatePost(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, (Entity)livingEntity, bipedModel, npc.animationStart);
        }
        if (bipedModel.f_102817_) {
            bipedModel.f_102810_.f_104203_ = 0.5f / data.getPartConfig((EnumParts)EnumParts.BODY).scaleY;
        }
        if (bipedModel instanceof PlayerModel) {
            PlayerModel playerModel = (PlayerModel)bipedModel;
            playerModel.f_103376_.m_104315_(playerModel.f_102814_);
            playerModel.f_103377_.m_104315_(playerModel.f_102813_);
            playerModel.f_103374_.m_104315_(playerModel.f_102812_);
            playerModel.f_103375_.m_104315_(playerModel.f_102811_);
            playerModel.f_103378_.m_104315_(playerModel.f_102810_);
        }
        bipedModel.f_102809_.m_104315_(bipedModel.f_102808_);
    }

    public static void addAnimation(int enumAnimation, AnimationBase animationBase) {
        ANIMATIONS.put(enumAnimation, animationBase);
    }

    public static HashMap<Integer, AnimationBase> getAllAnimations() {
        return ANIMATIONS;
    }

    public static AnimationBase getAnimationFor(int animation) {
        return ANIMATIONS.get(animation);
    }

    static {
        AnimationHandler.addAnimation(0, new AniBlank());
        AnimationHandler.addAnimation(2, new AniBlank());
        AnimationHandler.addAnimation(7, new AniCrawling());
        AnimationHandler.addAnimation(3, new AniHug());
        AnimationHandler.addAnimation(5, new AniDancing());
        AnimationHandler.addAnimation(10, new AniWaving());
        AnimationHandler.addAnimation(11, new AniBow());
        AnimationHandler.addAnimation(13, new AniYes());
        AnimationHandler.addAnimation(12, new AniNo());
        AnimationHandler.addAnimation(8, new AniPoint());
        AnimationHandler.addAnimation(14, new AniBlank());
        AnimationHandler.addAnimation(6, new AniAim());
        AnimationHandler.addAnimation(9, new AnimationBase(){

            @Override
            public void animatePre(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
            }

            @Override
            public void animatePost(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
                model.f_102808_.f_104203_ = 0.7f;
                model.f_102809_.f_104203_ = 0.7f;
            }
        });
        AnimationHandler.addAnimation(1, new AnimationBase(){

            @Override
            public void animatePre(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
                model.f_102609_ = true;
            }

            @Override
            public void animatePost(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
                model.f_102609_ = false;
            }
        });
        AnimationHandler.addAnimation(4, new AnimationBase(){

            @Override
            public void animatePre(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
                model.f_102817_ = true;
            }

            @Override
            public void animatePost(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, Entity entity, HumanoidModel model, int animationStart) {
                model.f_102817_ = false;
            }
        });
    }
}

