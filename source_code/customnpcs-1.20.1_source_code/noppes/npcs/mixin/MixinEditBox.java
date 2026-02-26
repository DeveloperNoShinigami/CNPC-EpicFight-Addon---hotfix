/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.SharedConstants
 *  net.minecraft.client.gui.components.EditBox
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package noppes.npcs.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.gui.components.EditBox;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={EditBox.class})
public class MixinEditBox {
    @Redirect(method={"insertText"}, at=@At(value="INVOKE", target="Lnet/minecraft/SharedConstants;filterText(Ljava/lang/String;)Ljava/lang/String;"))
    public String filterTextProxy(String p_136191_) {
        if (this instanceof GuiTextFieldNop) {
            return p_136191_;
        }
        return SharedConstants.m_136190_((String)p_136191_);
    }
}

