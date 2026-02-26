/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 */
package noppes.npcs.shared.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import noppes.npcs.shared.client.gui.listeners.ISliderListener;

public class GuiSliderNop
extends AbstractWidget {
    private ISliderListener listener;
    public int id;
    public float sliderValue = 1.0f;
    public float startValue = 1.0f;

    public GuiSliderNop(Screen parent, int id, int xPos, int yPos, String displayString, float sliderValue) {
        super(xPos, yPos, 150, 20, (Component)Component.m_237115_((String)displayString));
        this.id = id;
        this.sliderValue = sliderValue;
        this.startValue = sliderValue;
        this.listener = (ISliderListener)parent;
    }

    public GuiSliderNop(Screen parent, int id, int xPos, int yPos, float sliderValue) {
        this(parent, id, xPos, yPos, "", sliderValue);
        this.listener.mouseDragged(this);
    }

    public GuiSliderNop(Screen parent, int id, int xPos, int yPos, int width, int height, float sliderValue) {
        this(parent, id, xPos, yPos, "", sliderValue);
        this.f_93618_ = width;
        this.f_93619_ = height;
        this.listener.mouseDragged(this);
    }

    public void setString(String str) {
        this.m_93666_((Component)Component.m_237115_((String)str));
    }

    private void setSliderValue(float value) {
        if ((value = Mth.m_14036_((float)value, (float)0.0f, (float)1.0f)) == this.sliderValue) {
            return;
        }
        this.sliderValue = value;
        this.listener.mouseDragged(this);
    }

    protected void m_87963_(GuiGraphics p_93676_, int p_93677_, int p_93678_, float p_93679_) {
        Minecraft minecraft = Minecraft.m_91087_();
        Font font = minecraft.f_91062_;
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)f_93617_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)this.f_93625_);
        int i = 0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        p_93676_.m_280218_(f_93617_, this.m_252754_(), this.m_252907_(), 0, 46 + i * 20, this.f_93618_ / 2, this.f_93619_);
        p_93676_.m_280218_(f_93617_, this.m_252754_() + this.f_93618_ / 2, this.m_252907_(), 200 - this.f_93618_ / 2, 46 + i * 20, this.f_93618_ / 2, this.f_93619_);
        this.renderBg(p_93676_, minecraft, p_93677_, p_93678_);
        int j = this.getFGColor();
        p_93676_.m_280653_(font, this.m_6035_(), this.m_252754_() + this.f_93618_ / 2, this.m_252907_() + (this.f_93619_ - 8) / 2, j | Mth.m_14167_((float)(this.f_93625_ * 255.0f)) << 24);
    }

    public void m_5716_(double x, double y) {
        if (!this.f_93624_ || !this.f_93623_) {
            return;
        }
        this.setSliderValue((float)(x - (double)(this.m_252754_() + 4)) / (float)(this.f_93618_ - 8));
        super.m_5716_(x, y);
    }

    protected void m_7212_(double x, double y, double p_onDrag_5_, double p_onDrag_7_) {
        this.setSliderValue((float)(x - (double)(this.m_252754_() + 4)) / (float)(this.f_93618_ - 8));
        super.m_7212_(x, y, p_onDrag_5_, p_onDrag_7_);
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
    }

    public void m_7691_(double x, double y) {
        if (this.sliderValue == this.startValue) {
            return;
        }
        super.m_7435_(Minecraft.m_91087_().m_91106_());
        this.listener.mouseReleased(this);
        this.startValue = this.sliderValue;
    }

    public void renderBg(GuiGraphics graphics, Minecraft mc, int p_146119_2_, int p_146119_3_) {
        if (!this.f_93624_) {
            return;
        }
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)f_93617_);
        int lvt_4_1_ = (this.f_93622_ ? 2 : 1) * 20;
        graphics.m_280218_(f_93617_, this.m_252754_() + (int)((double)this.sliderValue * (double)(this.f_93618_ - 8)), this.m_252907_(), 0, 46 + lvt_4_1_, 4, 20);
        graphics.m_280218_(f_93617_, this.m_252754_() + (int)((double)this.sliderValue * (double)(this.f_93618_ - 8)) + 4, this.m_252907_(), 196, 46 + lvt_4_1_, 4, 20);
    }
}

