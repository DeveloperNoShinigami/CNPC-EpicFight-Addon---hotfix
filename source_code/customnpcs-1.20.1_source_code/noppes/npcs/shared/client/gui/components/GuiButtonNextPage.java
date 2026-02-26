/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.shared.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiButtonNextPage
extends GuiButtonNop {
    private final boolean isLeftButton;
    private static final String __OBFID = "CL_00000745";
    private static final ResourceLocation field_110405_a = new ResourceLocation("textures/gui/book.png");

    public GuiButtonNextPage(IGuiInterface gui, int id, int x, int y, boolean par4, Button.OnPress press) {
        super(gui, id, x, y, 23, 13, "", press);
        this.isLeftButton = par4;
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.f_93624_) {
            boolean flag = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
            RenderSystem.setShader(GameRenderer::m_172817_);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)field_110405_a);
            int k = 0;
            int l = 192;
            if (flag) {
                k += 23;
            }
            if (!this.isLeftButton) {
                l += 13;
            }
            graphics.m_280218_(field_110405_a, this.m_252754_(), this.m_252907_(), k, l, 23, 13);
        }
    }
}

