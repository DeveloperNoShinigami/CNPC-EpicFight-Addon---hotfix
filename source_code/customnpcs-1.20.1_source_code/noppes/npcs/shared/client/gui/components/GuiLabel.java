/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Tooltip
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.Style
 */
package noppes.npcs.shared.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import noppes.npcs.client.CustomNpcResourceListener;

public class GuiLabel
extends AbstractWidget
implements GuiEventListener {
    public int id;
    private boolean centered = false;
    public boolean enabled = true;
    private boolean labelBgEnabled;
    private int textColor;
    private int backColor;
    private int ulColor;
    private int brColor;
    private int border;

    public GuiLabel(int id, Component label, int color, int x, int y, int width, int height) {
        super(x, y, width, height, label);
        this.id = id;
        this.textColor = color;
        this.f_93618_ = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)this.m_6035_());
    }

    public GuiLabel(int id, String s, int x, int y) {
        this(id, (Component)Component.m_237115_((String)s), CustomNpcResourceListener.getDefaultTextColor(), x, y, 40, 0);
    }

    public GuiLabel(int id, String s, int x, int y, String tooltip) {
        this(id, (Component)Component.m_237115_((String)s), CustomNpcResourceListener.getDefaultTextColor(), x, y, 40, 10);
        this.m_257544_(Tooltip.m_257550_((Component)Component.m_237115_((String)tooltip).m_6270_(Style.f_131099_.m_178520_(16762460))));
    }

    public GuiLabel(int id, String s, int x, int y, int color) {
        this(id, (Component)Component.m_237115_((String)s), color, x, y, 40, 0);
    }

    public GuiLabel(int id, String s, int x, int y, int width, int height) {
        this(id, (Component)Component.m_237115_((String)s), CustomNpcResourceListener.getDefaultTextColor(), x, y, width, height);
        this.centered = true;
    }

    public GuiLabel(int id, String s, int x, int y, int color, int width, int height) {
        this(id, (Component)Component.m_237115_((String)s), color, x, y, width, height);
        this.centered = true;
    }

    public void setColor(int color) {
        this.textColor = color;
    }

    public void setCentered(boolean bo) {
        this.centered = bo;
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.enabled) {
            this.drawBox(graphics);
            int i = this.m_252907_() + this.f_93619_ / 2 + this.border / 2;
            if (this.centered) {
                graphics.m_280614_(Minecraft.m_91087_().f_91062_, this.m_6035_(), (int)((float)this.m_252754_() + (float)(this.f_93618_ - Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)this.m_6035_())) / 2.0f), this.m_252907_(), this.textColor, false);
            } else {
                graphics.m_280614_(Minecraft.m_91087_().f_91062_, this.m_6035_(), this.m_252754_(), this.m_252907_(), this.textColor, false);
            }
            super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        }
    }

    protected void m_87963_(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
    }

    protected void drawBox(GuiGraphics graphics) {
        if (this.labelBgEnabled) {
            int i = this.f_93618_ + this.border * 2;
            int j = this.f_93619_ + this.border * 2;
            int k = this.m_252754_() - this.border;
            int l = this.m_252907_() - this.border;
            graphics.m_280509_(k, l, k + i, l + j, this.backColor);
            graphics.m_280656_(k, k + i, l, this.ulColor);
            graphics.m_280656_(k, k + i, l + j, this.brColor);
            graphics.m_280656_(k, l, l + j, this.ulColor);
            graphics.m_280656_(k + i, l, l + j, this.brColor);
        }
    }
}

