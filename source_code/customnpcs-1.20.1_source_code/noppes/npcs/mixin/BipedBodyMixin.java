/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package noppes.npcs.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.client.model.animation.AnimationHandler;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.roles.JobPuppet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={HumanoidModel.class})
public class BipedBodyMixin<T extends LivingEntity> {
    @Inject(at={@At(value="HEAD")}, method={"setupAnim"})
    private void setupAnimPre(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        HumanoidModel bipedModel = (HumanoidModel)this;
        if (livingEntity instanceof EntityCustomNpc && bipedModel instanceof PlayerModel) {
            EntityCustomNpc playerEntity = (EntityCustomNpc)((Object)livingEntity);
            ClientProxy.data = playerEntity.modelData;
            ClientProxy.playerModel = (PlayerModel)bipedModel;
            RenderCustomNpc renderer = (RenderCustomNpc)Minecraft.m_91087_().m_91290_().m_114382_(livingEntity);
            ClientProxy.armorLayer = renderer.armorLayer;
            AnimationHandler.animateBipedPre(ClientProxy.data, bipedModel, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Inject(at={@At(value="TAIL")}, method={"setupAnim"})
    private void setupAnimPost(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        HumanoidModel bipedModel = (HumanoidModel)this;
        if (livingEntity instanceof EntityCustomNpc) {
            JobPuppet job;
            EntityCustomNpc npc = (EntityCustomNpc)((Object)livingEntity);
            AnimationHandler.animateBipedPost(ClientProxy.data, bipedModel, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (npc.job.getType() == 9 && (job = (JobPuppet)npc.job).isActive()) {
                float pi = (float)Math.PI;
                float partialTicks = Minecraft.m_91087_().m_91297_();
                if (!job.head.disabled) {
                    bipedModel.f_102809_.f_104203_ = bipedModel.f_102808_.f_104203_ = job.getRotationX(job.head, job.head2, partialTicks) * pi;
                    bipedModel.f_102809_.f_104204_ = bipedModel.f_102808_.f_104204_ = job.getRotationY(job.head, job.head2, partialTicks) * pi;
                    bipedModel.f_102809_.f_104205_ = bipedModel.f_102808_.f_104205_ = job.getRotationZ(job.head, job.head2, partialTicks) * pi;
                }
                if (!job.body.disabled) {
                    bipedModel.f_102810_.f_104203_ = job.getRotationX(job.body, job.body2, partialTicks) * pi;
                    bipedModel.f_102810_.f_104204_ = job.getRotationY(job.body, job.body2, partialTicks) * pi;
                    bipedModel.f_102810_.f_104205_ = job.getRotationZ(job.body, job.body2, partialTicks) * pi;
                }
                if (!job.larm.disabled) {
                    bipedModel.f_102812_.f_104203_ = job.getRotationX(job.larm, job.larm2, partialTicks) * pi;
                    bipedModel.f_102812_.f_104204_ = job.getRotationY(job.larm, job.larm2, partialTicks) * pi;
                    bipedModel.f_102812_.f_104205_ = job.getRotationZ(job.larm, job.larm2, partialTicks) * pi;
                    if (npc.display.getHasLivingAnimation()) {
                        bipedModel.f_102812_.f_104205_ -= Mth.m_14089_((float)(ageInTicks * 0.09f)) * 0.05f + 0.05f;
                        bipedModel.f_102812_.f_104203_ -= Mth.m_14031_((float)(ageInTicks * 0.067f)) * 0.05f;
                    }
                }
                if (!job.rarm.disabled) {
                    bipedModel.f_102811_.f_104203_ = job.getRotationX(job.rarm, job.rarm2, partialTicks) * pi;
                    bipedModel.f_102811_.f_104204_ = job.getRotationY(job.rarm, job.rarm2, partialTicks) * pi;
                    bipedModel.f_102811_.f_104205_ = job.getRotationZ(job.rarm, job.rarm2, partialTicks) * pi;
                    if (npc.display.getHasLivingAnimation()) {
                        bipedModel.f_102811_.f_104205_ += Mth.m_14089_((float)(ageInTicks * 0.09f)) * 0.05f + 0.05f;
                        bipedModel.f_102811_.f_104203_ += Mth.m_14031_((float)(ageInTicks * 0.067f)) * 0.05f;
                    }
                }
                if (!job.rleg.disabled) {
                    bipedModel.f_102813_.f_104203_ = job.getRotationX(job.rleg, job.rleg2, partialTicks) * pi;
                    bipedModel.f_102813_.f_104204_ = job.getRotationY(job.rleg, job.rleg2, partialTicks) * pi;
                    bipedModel.f_102813_.f_104205_ = job.getRotationZ(job.rleg, job.rleg2, partialTicks) * pi;
                }
                if (!job.lleg.disabled) {
                    bipedModel.f_102814_.f_104203_ = job.getRotationX(job.lleg, job.lleg2, partialTicks) * pi;
                    bipedModel.f_102814_.f_104204_ = job.getRotationY(job.lleg, job.lleg2, partialTicks) * pi;
                    bipedModel.f_102814_.f_104205_ = job.getRotationZ(job.lleg, job.lleg2, partialTicks) * pi;
                }
            }
        }
    }
}

