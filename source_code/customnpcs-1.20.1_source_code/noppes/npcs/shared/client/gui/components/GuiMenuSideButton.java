/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.shared.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiMenuSideButton
extends GuiButtonNop {
    public static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/menusidebutton.png");
    public boolean active;

    public GuiMenuSideButton(IGuiInterface gui, int i, int j, int k, String s) {
        this(gui, i, j, k, 200, 20, s);
    }

    public GuiMenuSideButton(IGuiInterface gui, int i, int j, int k, int l, int i1, String s) {
        super(gui, i, j, k, l, i1, s);
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int i, int j, float partialTicks) {
        if (!this.f_93624_) {
            return;
        }
        Minecraft minecraft = Minecraft.m_91087_();
        Font fontrenderer = minecraft.f_91062_;
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)resource);
        int width = this.f_93618_ + (this.active ? 2 : 0);
        boolean bl = this.f_93622_ = i >= this.m_252754_() && j >= this.m_252907_() && i < this.m_252754_() + width && j < this.m_252907_() + this.f_93619_;
        int k = this.active ? 0 : (this.f_93622_ ? 2 : 1);
        graphics.m_280218_(resource, this.m_252754_(), this.m_252907_(), 0, k * 22, width, this.f_93619_);
        Object text = "";
        float maxWidth = (float)width * 0.75f;
        String displayString = this.m_6035_().getString();
        if ((float)fontrenderer.m_92895_(displayString) > maxWidth) {
            char c;
            for (int h = 0; h < displayString.length() && !((float)fontrenderer.m_92895_((String)text + (c = displayString.charAt(h))) > maxWidth); ++h) {
                text = (String)text + c;
            }
            text = (String)text + "...";
        } else {
            text = displayString;
        }
        if (this.active) {
            graphics.m_280137_(fontrenderer, (String)text, this.m_252754_() + width / 2, this.m_252907_() + (this.f_93619_ - 8) / 2, 0xFFFFA0);
        } else if (this.f_93622_) {
            graphics.m_280137_(fontrenderer, (String)text, this.m_252754_() + width / 2, this.m_252907_() + (this.f_93619_ - 8) / 2, 0xFFFFA0);
        } else {
            graphics.m_280137_(fontrenderer, (String)text, this.m_252754_() + width / 2, this.m_252907_() + (this.f_93619_ - 8) / 2, 0xE0E0E0);
        }
    }

    public boolean m_6375_(double i, double j, int button) {
        if (!this.active) {
            return super.m_6375_(i, j, button);
        }
        return false;
    }
}

