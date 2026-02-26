/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package noppes.npcs.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import noppes.npcs.client.gui.custom.GuiCustom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Screen.class})
public class ScreenMixin {
    @Inject(at={@At(value="TAIL")}, method={"init*"})
    private void renderToBuffer(Minecraft mc, int width, int height, CallbackInfo callbackInfo) {
        ScreenMixin screenMixin = this;
        if (screenMixin instanceof GuiCustom) {
            GuiCustom gui = (GuiCustom)((Object)screenMixin);
            if (gui.subgui != null) {
                gui.subgui.m_6575_(mc, width, height);
            }
        }
    }
}

