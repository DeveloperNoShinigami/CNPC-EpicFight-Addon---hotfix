/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.geom.ModelPart
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package noppes.npcs.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.constants.EnumParts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ModelPart.class})
public class ModelRendererMixin {
    public ModelPartConfig cnpcconfig;

    @Inject(at={@At(value="HEAD")}, method={"translateAndRotate"})
    private void translateAndRotatePre(PoseStack mStack, CallbackInfo callbackInfo) {
        this.cnpcconfig = this.getCnpcconfig();
        if (this.cnpcconfig != null) {
            mStack.m_252880_(this.cnpcconfig.transX, this.cnpcconfig.transY, this.cnpcconfig.transZ);
        }
    }

    @Inject(at={@At(value="TAIL")}, method={"translateAndRotate"})
    private void translateAndRotatePost(PoseStack mStack, CallbackInfo callbackInfo) {
        this.cnpcconfig = this.getCnpcconfig();
        if (this.cnpcconfig != null) {
            mStack.m_85841_(this.cnpcconfig.scaleX, this.cnpcconfig.scaleY, this.cnpcconfig.scaleZ);
        }
    }

    private ModelPartConfig getCnpcconfig() {
        if (ClientProxy.data == null) {
            return null;
        }
        ModelPart model = (ModelPart)this;
        if (model == ClientProxy.playerModel.f_102810_ || model == ClientProxy.playerModel.f_103378_ || model == ((HumanoidModel)ClientProxy.armorLayer.getOuter()).f_102810_ || model == ((HumanoidModel)ClientProxy.armorLayer.getInner()).f_102810_) {
            return ClientProxy.data.getPartConfig(EnumParts.BODY);
        }
        if (model == ClientProxy.playerModel.f_102808_ || model == ClientProxy.playerModel.f_102809_ || model == ((HumanoidModel)ClientProxy.armorLayer.getOuter()).f_102808_) {
            return ClientProxy.data.getPartConfig(EnumParts.HEAD);
        }
        if (model == ClientProxy.playerModel.f_102814_ || model == ClientProxy.playerModel.f_103376_ || model == ((HumanoidModel)ClientProxy.armorLayer.getOuter()).f_102814_ || model == ((HumanoidModel)ClientProxy.armorLayer.getInner()).f_102814_) {
            return ClientProxy.data.getPartConfig(EnumParts.LEG_LEFT);
        }
        if (model == ClientProxy.playerModel.f_102813_ || model == ClientProxy.playerModel.f_103377_ || model == ((HumanoidModel)ClientProxy.armorLayer.getOuter()).f_102813_ || model == ((HumanoidModel)ClientProxy.armorLayer.getInner()).f_102813_) {
            return ClientProxy.data.getPartConfig(EnumParts.LEG_RIGHT);
        }
        if (model == ClientProxy.playerModel.f_102812_ || model == ClientProxy.playerModel.f_103374_ || model == ((HumanoidModel)ClientProxy.armorLayer.getOuter()).f_102812_) {
            return ClientProxy.data.getPartConfig(EnumParts.ARM_LEFT);
        }
        if (model == ClientProxy.playerModel.f_102811_ || model == ClientProxy.playerModel.f_103375_ || model == ((HumanoidModel)ClientProxy.armorLayer.getOuter()).f_102811_) {
            return ClientProxy.data.getPartConfig(EnumParts.ARM_RIGHT);
        }
        return null;
    }
}

