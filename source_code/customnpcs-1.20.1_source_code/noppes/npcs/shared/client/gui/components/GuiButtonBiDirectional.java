/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.shared.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiButtonBiDirectional
extends GuiButtonNop {
    public static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/arrowbuttons.png");
    private int color = 0xFFFFFF;

    public GuiButtonBiDirectional(IGuiInterface gui, int id, int x, int y, int width, int height, String[] arr, int current) {
        super(gui, id, x, y, width, height, arr, current);
    }

    public GuiButtonBiDirectional(IGuiInterface gui, int id, int x, int y, int width, int height, int current, String ... arr) {
        super(gui, id, x, y, width, height, arr, current);
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (!this.f_93624_) {
            return;
        }
        boolean hover = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        boolean hoverL = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + 14 && mouseY < this.m_252907_() + this.f_93619_;
        boolean hoverR = !hoverL && mouseX >= this.m_252754_() + this.f_93618_ - 14 && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        Minecraft mc = Minecraft.m_91087_();
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)resource);
        graphics.m_280218_(resource, this.m_252754_(), this.m_252907_(), 0, hoverL ? 40 : 20, 11, 20);
        graphics.m_280218_(resource, this.m_252754_() + this.f_93618_ - 11, this.m_252907_(), 11, hover && !hoverL || hoverR ? 40 : 20, 11, 20);
        int l = this.color;
        if (this.packedFGColor != 0) {
            l = this.packedFGColor;
        } else if (!this.f_93623_) {
            l = 0xA0A0A0;
        } else if (hover) {
            l = 0xFFFFA0;
        }
        Object text = "";
        float maxWidth = this.f_93618_ - 36;
        String displayString = this.m_6035_().getString();
        if ((float)mc.f_91062_.m_92895_(displayString) > maxWidth) {
            char c;
            for (int h = 0; h < displayString.length() && !((float)mc.f_91062_.m_92895_((String)(text = (String)text + (c = displayString.charAt(h)))) > maxWidth); ++h) {
            }
            text = (String)text + "...";
        } else {
            text = displayString;
        }
        if (hover) {
            text = "\u00a7n" + (String)text;
        }
        graphics.m_280488_(mc.f_91062_, (String)text, this.m_252754_() + this.f_93618_ / 2 - mc.f_91062_.m_92895_((String)text) / 2, this.m_252907_() + (this.f_93619_ - 8) / 2, l);
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        int value = this.getValue();
        if (this.m_5953_(mouseX, mouseY) && this.display != null && this.display.length != 0) {
            boolean hoverR;
            boolean hoverL = mouseX >= (double)this.m_252754_() && mouseY >= (double)this.m_252907_() && mouseX < (double)(this.m_252754_() + 14) && mouseY < (double)(this.m_252907_() + this.f_93619_);
            boolean bl = hoverR = !hoverL && mouseX >= (double)(this.m_252754_() + 14) && mouseY >= (double)this.m_252907_() && mouseX < (double)(this.m_252754_() + this.f_93618_) && mouseY < (double)(this.m_252907_() + this.f_93619_);
            if (hoverR) {
                value = (value + 1) % this.display.length;
            }
            if (hoverL) {
                if (value <= 0) {
                    value = this.display.length;
                }
                --value;
            }
            this.setDisplay(value);
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    @Override
    public void m_5716_(double x, double y) {
        if (this.gui.hasSubGui()) {
            return;
        }
        this.gui.buttonEvent(this);
    }
}

