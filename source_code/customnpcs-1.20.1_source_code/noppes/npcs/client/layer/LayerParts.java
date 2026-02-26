/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.client.parts.ModelPartWrapper;
import noppes.npcs.client.parts.MpmPart;
import noppes.npcs.client.parts.MpmPartAbstractClient;
import noppes.npcs.client.parts.MpmPartData;
import noppes.npcs.client.parts.PartBehaviorType;
import noppes.npcs.client.parts.PartRenderType;
import noppes.npcs.constants.BodyPart;
import noppes.npcs.constants.EnumParts;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.shared.common.util.NopVector3f;

public class LayerParts<T extends EntityCustomNpc, M extends HumanoidModel<T>>
extends RenderLayer<T, M> {
    public LayerParts(LivingEntityRenderer<T, M> render) {
        super(render);
    }

    public void render(PoseStack mStack, MultiBufferSource typeBuffer, int lightmapUV, EntityCustomNpc player, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
        ModelData data = ModelData.get(player);
        for (MpmPartData part : data.mpmParts) {
            MpmPart mp = part.getPart();
            if (mp == null || mp.renderType == PartRenderType.NONE || !mp.isEnabled) continue;
            MpmPartAbstractClient partc = (MpmPartAbstractClient)mp;
            this.rotate(data, partc, player, (HumanoidModel)this.m_117386_(), limbSwing, limbSwingAmount, partialTicks, age, netHeadYaw, headPitch);
            LayerParts.renderPart(part, partc, mStack, typeBuffer, lightmapUV, player, (HumanoidModel)this.m_117386_(), data);
        }
        data.startMoveAnimation = false;
        data.startAnimation = false;
    }

    public static void renderPart(MpmPartData data, MpmPartAbstractClient partc, PoseStack mStack, MultiBufferSource typeBuffer, int lightmapUV, EntityCustomNpc player, HumanoidModel model, ModelData pdata) {
        ModelPartConfig config;
        ModelPartWrapper lmodelPart;
        ModelPartWrapper rmodelPart;
        mStack.m_85836_();
        boolean shouldRender = true;
        if (partc.bodyPart == BodyPart.HEAD) {
            model.f_102808_.m_104299_(mStack);
        }
        if (partc.bodyPart == BodyPart.BODY) {
            model.f_102810_.m_104299_(mStack);
        }
        if (partc.bodyPart == BodyPart.LEGS) {
            rmodelPart = partc.getPart("right_leg");
            lmodelPart = partc.getPart("left_leg");
            if (rmodelPart != null) {
                shouldRender = false;
                mStack.m_85836_();
                config = pdata.getPartConfig(EnumParts.LEG_RIGHT);
                mStack.m_252880_(0.0f, config.transY * 2.0f, 0.0f);
                mStack.m_85841_(config.scaleX, config.scaleY, config.scaleZ);
                if (lmodelPart != null) {
                    lmodelPart.setVisible(false);
                }
                rmodelPart.setVisible(true);
                partc.render(data, mStack, typeBuffer, lightmapUV, (LivingEntity)player);
                mStack.m_85849_();
            }
            if (lmodelPart != null) {
                shouldRender = false;
                mStack.m_85836_();
                config = pdata.getPartConfig(EnumParts.LEG_LEFT);
                mStack.m_252880_(0.0f, config.transY * 2.0f, 0.0f);
                mStack.m_85841_(config.scaleX, config.scaleY, config.scaleZ);
                if (rmodelPart != null) {
                    rmodelPart.setVisible(false);
                }
                lmodelPart.setVisible(true);
                partc.render(data, mStack, typeBuffer, lightmapUV, (LivingEntity)player);
                mStack.m_85849_();
            }
            if (shouldRender) {
                config = pdata.getPartConfig(EnumParts.LEG_LEFT);
                mStack.m_252880_(0.0f, config.transY * 2.0f, 0.0f);
                mStack.m_85841_(config.scaleX, config.scaleY, config.scaleZ);
            }
        }
        if (partc.bodyPart == BodyPart.ARMS) {
            rmodelPart = partc.getPart("right_arm");
            lmodelPart = partc.getPart("left_arm");
            if (rmodelPart != null) {
                shouldRender = false;
                mStack.m_85836_();
                config = pdata.getPartConfig(EnumParts.ARM_RIGHT);
                mStack.m_252880_(0.25f * (config.scaleX - 1.0f), config.transY + (1.0f - config.scaleY) * 0.125f, 0.0f);
                mStack.m_85841_(config.scaleX, config.scaleY, config.scaleZ);
                if (lmodelPart != null) {
                    lmodelPart.setVisible(false);
                }
                rmodelPart.setVisible(true);
                partc.render(data, mStack, typeBuffer, lightmapUV, (LivingEntity)player);
                mStack.m_85849_();
            }
            if (lmodelPart != null) {
                shouldRender = false;
                mStack.m_85836_();
                config = pdata.getPartConfig(EnumParts.ARM_LEFT);
                mStack.m_252880_(-0.25f * (config.scaleX - 1.0f), config.transY + (1.0f - config.scaleY) * 0.125f, 0.0f);
                mStack.m_85841_(config.scaleX, config.scaleY, config.scaleZ);
                if (rmodelPart != null) {
                    rmodelPart.setVisible(false);
                }
                lmodelPart.setVisible(true);
                partc.render(data, mStack, typeBuffer, lightmapUV, (LivingEntity)player);
                mStack.m_85849_();
            }
            if (shouldRender) {
                config = pdata.getPartConfig(EnumParts.ARM_LEFT);
                mStack.m_252880_(0.0f, config.transY + (1.0f - config.scaleY) * 0.125f, 0.0f);
                mStack.m_85841_(config.scaleX, config.scaleY, config.scaleZ);
            }
        }
        if (shouldRender) {
            partc.render(data, mStack, typeBuffer, lightmapUV, (LivingEntity)player);
        }
        mStack.m_85849_();
    }

    private void rotate(ModelData playerdata, MpmPartAbstractClient part, EntityCustomNpc player, HumanoidModel base, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
        ModelPartWrapper modelPartL;
        ModelPartWrapper modelPart;
        ModelPartWrapper modelPart2;
        HumanoidModel model;
        part.animationData.animation(19, (int)age, partialTicks);
        if ((double)limbSwingAmount > 0.01) {
            if (player.m_20096_()) {
                if (player.ais.getAnimation() == 7) {
                    playerdata.setAnimation(7);
                } else {
                    playerdata.setAnimation(15);
                }
            } else {
                playerdata.setAnimation(17);
            }
        } else if (player.ais.getAnimation() == 0) {
            playerdata.setAnimation(16);
        } else {
            playerdata.setAnimation(player.ais.getAnimation());
        }
        int moveAnimation = playerdata.getMoveAnimtion((LivingEntity)player);
        if (playerdata.startMoveAnimation) {
            part.animationData.start(moveAnimation);
        }
        boolean didAnimation = false;
        if (playerdata.animation != 0) {
            if (playerdata.startAnimation) {
                part.animationData.start(playerdata.animation);
            }
            didAnimation = part.animationData.animation(playerdata.animation, (int)age, partialTicks);
        }
        if (!(didAnimation || moveAnimation != 16 && moveAnimation != 18)) {
            part.animationData.animation(moveAnimation, (int)age, partialTicks);
        } else {
            part.animationData.animation(moveAnimation, Mth.m_14089_((float)(limbSwing * 0.6662f)) * limbSwingAmount / 2.0f + 0.5f);
        }
        if (part.animationType == PartBehaviorType.LEGS) {
            model = (HumanoidModel)this.m_117386_();
            modelPart2 = part.getPart("right_leg");
            if (modelPart2 != null) {
                modelPart2.setRot(new NopVector3f(model.f_102813_.f_104203_, model.f_102813_.f_104204_, model.f_102813_.f_104205_));
                modelPart2.setPos(new NopVector3f(model.f_102813_.f_104200_, model.f_102813_.f_104201_, model.f_102813_.f_104202_));
            }
            if ((modelPart2 = part.getPart("left_leg")) != null) {
                modelPart2.setRot(new NopVector3f(model.f_102814_.f_104203_, model.f_102814_.f_104204_, model.f_102814_.f_104205_));
                modelPart2.setPos(new NopVector3f(model.f_102814_.f_104200_, model.f_102814_.f_104201_, model.f_102814_.f_104202_));
            }
        }
        if (part.animationType == PartBehaviorType.ARMS) {
            model = (HumanoidModel)this.m_117386_();
            modelPart2 = part.getPart("right_arm");
            if (modelPart2 != null) {
                modelPart2.setRot(new NopVector3f(model.f_102811_.f_104203_, model.f_102811_.f_104204_, model.f_102811_.f_104205_));
                modelPart2.setPos(new NopVector3f(model.f_102811_.f_104200_, model.f_102811_.f_104201_, model.f_102811_.f_104202_));
            }
            if ((modelPart2 = part.getPart("left_arm")) != null) {
                modelPart2.setRot(new NopVector3f(model.f_102812_.f_104203_, model.f_102812_.f_104204_, model.f_102812_.f_104205_));
                modelPart2.setPos(new NopVector3f(model.f_102812_.f_104200_, model.f_102812_.f_104201_, model.f_102812_.f_104202_));
            }
        }
        if (part.animationType == PartBehaviorType.BEARD) {
            part.rot = part.rot.set(base.f_102808_.f_104203_ < 0.0f ? 0.0f : -base.f_102808_.f_104203_, part.rot.y, part.rot.z);
        }
        if (part.animationType == PartBehaviorType.HAIR) {
            ModelPart head = base.f_102808_;
            if (head.f_104203_ < 0.0f) {
                part.rot = part.rot.set(-head.f_104203_ * 1.2f, part.rot.y, part.rot.z);
                if (head.f_104203_ > -1.0f) {
                    part.pos = part.pos.set(part.pos.x, -head.f_104203_ * 1.5f, -head.f_104203_ * 1.5f);
                }
            } else {
                part.pos = NopVector3f.ZERO;
            }
        }
        if (part.animationType == PartBehaviorType.WINGS) {
            float xRot;
            float zRot;
            modelPart = part.getPart("right_wing");
            modelPartL = part.getPart("left_wing");
            if (player.m_9236_().m_46859_(player.m_20183_().m_7495_())) {
                float motion = Math.abs(Mth.m_14031_((float)(limbSwing * 0.033f + (float)Math.PI)) * 0.4f) * limbSwingAmount;
                float speed = 0.55f + 0.5f * motion;
                float y = Mth.m_14031_((float)(age * 0.35f));
                xRot = zRot = y * 0.5f * speed;
            } else {
                zRot = Mth.m_14089_((float)(age * 0.09f)) * 0.05f + 0.05f;
                xRot = Mth.m_14031_((float)(age * 0.067f)) * 0.05f;
            }
            modelPart.setRot(modelPart.oriRot.add(xRot, xRot, zRot));
            modelPartL.setRot(modelPartL.oriRot.add(xRot, -xRot, -zRot));
        }
        if (part.animationType == PartBehaviorType.WINGS2) {
            float yRot;
            modelPart = part.getPart("right_wing");
            modelPartL = part.getPart("left_wing");
            if (player.m_9236_().m_46859_(player.m_20183_().m_7495_())) {
                float motion = Math.abs(Mth.m_14031_((float)(limbSwing * 0.033f + (float)Math.PI)) * 0.4f) * limbSwingAmount;
                float speed = 0.55f + 0.5f * motion;
                float y = Mth.m_14031_((float)(age * 0.35f));
                yRot = y * 0.5f * speed;
            } else {
                yRot = Mth.m_14031_((float)(age * 0.07f)) * 0.44f;
            }
            modelPart.setRot(modelPart.oriRot.add(0.0f, yRot, 0.0f));
            modelPartL.setRot(modelPartL.oriRot.add(0.0f, -yRot, 0.0f));
        }
    }
}

