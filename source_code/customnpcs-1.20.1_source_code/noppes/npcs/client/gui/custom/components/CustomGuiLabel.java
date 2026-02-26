/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 */
package noppes.npcs.client.gui.custom.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiLabelWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;

public class CustomGuiLabel
extends AbstractWidget
implements IGuiComponent {
    private CustomGuiLabelWrapper component;
    private int id;
    private GuiCustom parent;

    public CustomGuiLabel(GuiCustom parent, CustomGuiLabelWrapper component) {
        super(component.getPosX(), component.getPosY(), component.getWidth(), component.getHeight(), (Component)Component.m_237115_((String)component.getText()));
        this.component = component;
        this.parent = parent;
        this.init();
    }

    public void init() {
        this.id = this.component.getID();
        this.m_252865_(this.component.getPosX());
        this.m_253211_(this.component.getPosY());
        this.m_93674_(this.component.getWidth());
        this.setHeight(this.component.getHeight());
        this.f_93623_ = this.component.getEnabled() && this.component.getVisible();
        this.f_93624_ = this.component.getVisible();
        this.m_93666_((Component)Component.m_237115_((String)this.component.getText()));
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        boolean hovered;
        PoseStack matrixStack = graphics.m_280168_();
        if (!this.f_93623_) {
            return;
        }
        matrixStack.m_85836_();
        matrixStack.m_252880_(0.0f, 0.0f, (float)this.id * 0.01f);
        matrixStack.m_85841_(this.component.getScale(), this.component.getScale(), 0.0f);
        boolean bl = hovered = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        if (this.component.getCentered()) {
            graphics.m_280614_(Minecraft.m_91087_().f_91062_, this.m_6035_(), (int)(((float)this.m_252754_() + (float)(this.f_93618_ - Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)this.m_6035_())) / 2.0f) / this.component.getScale()), (int)((float)this.m_252907_() / this.component.getScale()), this.component.getColor(), false);
        } else {
            graphics.m_280614_(Minecraft.m_91087_().f_91062_, this.m_6035_(), (int)((float)this.m_252754_() / this.component.getScale()), (int)((float)this.m_252907_() / this.component.getScale()), this.component.getColor(), false);
        }
        if (hovered && this.component.hasHoverText()) {
            this.parent.hoverText = this.component.getHoverTextList();
        }
        matrixStack.m_85849_();
    }

    @Override
    public int getID() {
        return this.id;
    }

    public void setText(String s) {
        this.m_93666_((Component)Component.m_237115_((String)s));
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }

    protected void m_87963_(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
    }

    public void m_7435_(SoundManager p_93665_) {
    }
}

