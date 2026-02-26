/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.resources.ResourceLocation
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package noppes.npcs.mixin;

import com.google.common.base.MoreObjects;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.client.controllers.ClientSkinController;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerInfo.class})
public abstract class NetworkPlayerInfoMixin {
    @Shadow
    @Final
    private GameProfile f_105298_;

    @Inject(at={@At(value="RETURN")}, method={"getSkinLocation"}, cancellable=true)
    public void getSkinLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue((Object)((ResourceLocation)MoreObjects.firstNonNull((Object)ClientSkinController.getSkinForPlayer(this.f_105298_.getName()), (Object)((ResourceLocation)cir.getReturnValue()))));
    }
}

