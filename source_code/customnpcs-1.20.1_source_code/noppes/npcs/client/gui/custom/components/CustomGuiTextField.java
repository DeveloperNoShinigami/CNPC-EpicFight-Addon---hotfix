/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.client.gui.custom.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketCustomGuiFocusUpdate;
import noppes.npcs.packets.server.SPacketCustomGuiTextUpdate;

public class CustomGuiTextField
extends EditBox
implements IGuiComponent {
    private static CustomGuiTextField focused = null;
    private GuiCustom parent;
    private CustomGuiTextFieldWrapper component;
    public int id;

    public CustomGuiTextField(GuiCustom parent, CustomGuiTextFieldWrapper component) {
        super(Minecraft.m_91087_().f_91062_, component.getPosX(), component.getPosY(), component.getWidth(), component.getHeight(), (Component)Component.m_237115_((String)component.getText()));
        this.m_94199_(500);
        this.component = component;
        this.parent = parent;
        this.m_7856_();
    }

    @Override
    public void m_7856_() {
        this.id = this.component.getID();
        this.m_252865_(this.component.getPosX());
        this.m_253211_(this.component.getPosY());
        this.f_93618_ = this.component.getWidth();
        this.f_93619_ = this.component.getHeight();
        this.m_94202_(this.component.getColor());
        if (this.component.getText() != null) {
            this.m_94144_(this.component.getText());
        }
        this.f_93623_ = this.component.getEnabled() && this.component.getVisible();
        this.f_93624_ = this.component.getVisible();
        this.m_93692_(this.component.getFocused());
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrixStack = graphics.m_280168_();
        matrixStack.m_85836_();
        matrixStack.m_252880_(0.0f, 0.0f, (float)this.id * 0.01f);
        boolean hovered = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        this.m_87963_(graphics, mouseX, mouseY, partialTicks);
        if (hovered && this.component.hasHoverText()) {
            this.parent.hoverText = this.component.getHoverTextList();
        }
        matrixStack.m_85849_();
    }

    @Override
    public int getID() {
        return this.id;
    }

    public boolean m_7933_(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        String text = this.m_94155_();
        boolean bo = super.m_7933_(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        this.component.setText(this.m_94155_());
        if (!this.m_94155_().equals(this.component.getText())) {
            this.m_94144_(this.component.getText());
        }
        if (!text.equals(this.m_94155_())) {
            if (!this.component.disablePackets) {
                Packets.sendServer(new SPacketCustomGuiTextUpdate(this.component.getUniqueID(), this.m_94155_()));
            } else {
                this.component.onChange(null);
            }
        }
        return bo;
    }

    public boolean m_6375_(double i, double j, int k) {
        boolean flag = i >= (double)this.m_252754_() && i < (double)(this.m_252754_() + this.f_93618_) && j >= (double)this.m_252907_() && j < (double)(this.m_252907_() + this.f_93619_);
        this.m_93692_(flag);
        return super.m_6375_(i, j, k);
    }

    private boolean isValidChar(char c) {
        if (this.component.getCharacterType() == 1) {
            return Character.isDigit(c);
        }
        if (this.component.getCharacterType() == 2) {
            return Character.isDigit(c) || Character.toLowerCase(c) >= 'a' && Character.toLowerCase(c) <= 'f';
        }
        if (this.component.getCharacterType() == 3) {
            return Character.isDigit(c) || c == '.' && !this.m_94155_().contains(".") || c == '-' && this.m_94207_() == 0;
        }
        return true;
    }

    public boolean m_5534_(char c, int i) {
        if (!this.isValidChar(c)) {
            return false;
        }
        String text = this.m_94155_();
        boolean bo = super.m_5534_(c, i);
        if (!text.equals(this.m_94155_())) {
            this.component.setText(this.m_94155_());
            if (!this.component.disablePackets) {
                Packets.sendServer(new SPacketCustomGuiTextUpdate(this.component.getUniqueID(), this.m_94155_()));
            } else {
                this.component.onChange(null);
            }
        }
        return bo;
    }

    public void m_93692_(boolean bo) {
        if (this.m_93696_() == bo) {
            return;
        }
        super.m_93692_(bo);
        if (this.component.getFocused() != bo) {
            if (!(this.component.getText().isEmpty() || this.component.getCharacterType() != 1 && this.component.getCharacterType() != 2)) {
                this.component.setInteger(this.component.getInteger());
                this.m_94144_(this.component.getText());
                if (!this.component.disablePackets) {
                    Packets.sendServer(new SPacketCustomGuiTextUpdate(this.component.getUniqueID(), this.component.getText()));
                }
                this.component.onChange(null);
            }
            this.component.setFocused(bo);
            if (!this.component.disablePackets) {
                Packets.sendServer(new SPacketCustomGuiFocusUpdate(this.component.getUniqueID(), bo));
            } else {
                this.component.onFocusLost(null);
            }
        }
        if (this.m_93696_() && focused != this) {
            if (focused != null) {
                focused.m_93692_(false);
            }
            focused = this;
        }
        if (!this.m_93696_() && focused != this) {
            focused = null;
        }
    }

    public void m_7435_(SoundManager p_93665_) {
    }
}

