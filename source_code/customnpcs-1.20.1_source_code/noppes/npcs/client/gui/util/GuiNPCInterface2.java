/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.client.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcMenu;
import noppes.npcs.entity.EntityNPCInterface;

public abstract class GuiNPCInterface2
extends GuiNPCInterface {
    private ResourceLocation background = new ResourceLocation("customnpcs:textures/gui/menubg.png");
    private GuiNpcMenu menu;

    public GuiNPCInterface2(EntityNPCInterface npc) {
        this(npc, -1);
    }

    public GuiNPCInterface2(EntityNPCInterface npc, int activeMenu) {
        super(npc);
        this.imageWidth = 420;
        this.imageHeight = 200;
        this.menu = new GuiNpcMenu(this, activeMenu, npc);
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.menu.initGui(this.guiLeft, this.guiTop, this.imageWidth);
    }

    @Override
    public boolean m_6375_(double i, double j, int k) {
        if (!this.hasSubGui() && this.menu.mouseClicked(i, j, k)) {
            return true;
        }
        return super.m_6375_(i, j, k);
    }

    @Override
    public abstract void save();

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.drawDefaultBackground) {
            this.m_280273_(graphics);
        }
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.background);
        graphics.m_280218_(this.background, this.guiLeft, this.guiTop, 0, 0, 200, 220);
        graphics.m_280218_(this.background, this.guiLeft + this.imageWidth - 230, this.guiTop, 26, 0, 230, 220);
        int x = mouseX;
        int y = mouseY;
        if (this.hasSubGui()) {
            y = 0;
            x = 0;
        }
        this.menu.drawElements(graphics, this.getFontRenderer(), x, y, this.f_96541_, partialTicks);
        boolean bo = this.drawDefaultBackground;
        this.drawDefaultBackground = false;
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        this.drawDefaultBackground = bo;
    }
}

