/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.shared.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiMenuTopButton
extends GuiButtonNop {
    public static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/menutopbutton.png");
    protected int height;
    public boolean active = false;
    public boolean hover = false;
    public boolean rotated = false;

    public GuiMenuTopButton(IGuiInterface gui, int i, int j, int k, String s) {
        super(gui, i, j, k, s);
        this.f_93618_ = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)this.m_6035_()) + 12;
        this.height = 20;
    }

    public GuiMenuTopButton(IGuiInterface gui, int i, GuiButtonNop parent, String s) {
        this(gui, i, parent.m_252754_() + parent.m_5711_(), parent.m_252907_(), s);
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int i, int j, float partialTicks) {
        if (!this.f_93624_) {
            return;
        }
        PoseStack matrixStack = graphics.m_280168_();
        Minecraft mc = Minecraft.m_91087_();
        matrixStack.m_85836_();
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)resource);
        int height = this.height - (this.active ? 0 : 2);
        boolean bl = this.hover = i >= this.m_252754_() && j >= this.m_252907_() && i < this.m_252754_() + this.m_5711_() && j < this.m_252907_() + height;
        int k = this.active ? 0 : (this.hover ? 2 : 1);
        graphics.m_280218_(resource, this.m_252754_(), this.m_252907_(), 0, k * 20, this.m_5711_() / 2, height);
        graphics.m_280218_(resource, this.m_252754_() + this.m_5711_() / 2, this.m_252907_(), 200 - this.m_5711_() / 2, k * 20, this.m_5711_() / 2, height);
        Font fontrenderer = mc.f_91062_;
        if (this.rotated) {
            matrixStack.m_252781_(Axis.f_252529_.m_252977_(90.0f));
        }
        if (this.active) {
            graphics.m_280653_(fontrenderer, this.m_6035_(), this.m_252754_() + this.m_5711_() / 2, this.m_252907_() + (height - 8) / 2, 0xFFFFA0);
        } else if (this.hover) {
            graphics.m_280653_(fontrenderer, this.m_6035_(), this.m_252754_() + this.m_5711_() / 2, this.m_252907_() + (height - 8) / 2, 0xFFFFA0);
        } else {
            graphics.m_280653_(fontrenderer, this.m_6035_(), this.m_252754_() + this.m_5711_() / 2, this.m_252907_() + (height - 8) / 2, 0xE0E0E0);
        }
        matrixStack.m_85849_();
    }

    public boolean m_7979_(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        return false;
    }

    public boolean m_6348_(double i, double j, int button) {
        return false;
    }

    public boolean m_6375_(double i, double j, int button) {
        boolean bo;
        boolean bl = bo = !this.active && this.f_93624_ && this.hover;
        if (bo) {
            this.m_5716_(i, j);
        }
        return bo;
    }

    @Override
    public void m_5716_(double x, double y) {
        this.gui.buttonEvent(this);
    }
}

