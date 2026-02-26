/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 */
package noppes.npcs.shared.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiMenuTopButton;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiMenuTopIconButton
extends GuiMenuTopButton {
    private static final ResourceLocation resource = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    protected static ItemRenderer itemRenderer;
    private ItemStack item;

    public GuiMenuTopIconButton(IGuiInterface gui, int i, int x, int y, String s, ItemStack item) {
        super(gui, i, x, y, s);
        this.f_93618_ = 28;
        this.height = 28;
        this.item = item;
        itemRenderer = Minecraft.m_91087_().m_91291_();
    }

    public GuiMenuTopIconButton(IGuiInterface gui, int i, GuiButtonNop parent, String s, ItemStack item) {
        super(gui, i, parent, s);
        this.f_93618_ = 28;
        this.height = 28;
        this.item = item;
        itemRenderer = Minecraft.m_91087_().m_91291_();
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (!this.f_93624_) {
            return;
        }
        if (this.item.m_41619_()) {
            this.item = new ItemStack((ItemLike)Blocks.f_50493_);
        }
        this.hover = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.m_5711_() && mouseY < this.m_252907_() + this.height;
        Minecraft mc = Minecraft.m_91087_();
        if (this.hover) {
            this.drawHoveringText(graphics, Arrays.asList(this.m_6035_()), mouseX, mouseY, Minecraft.m_91087_().f_91062_);
        }
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)resource);
        graphics.m_280168_().m_85836_();
        graphics.m_280218_(resource, this.m_252754_(), this.m_252907_() + (this.active ? 2 : 0), 0, this.active ? 32 : 0, 28, 28);
        graphics.m_280168_().m_252880_(0.0f, 0.0f, 100.0f);
        graphics.m_280480_(this.item, this.m_252754_() + 6, this.m_252907_() + 10);
        graphics.m_280370_(mc.f_91062_, this.item, this.m_252754_() + 6, this.m_252907_() + 10);
        graphics.m_280168_().m_85849_();
    }

    protected void drawHoveringText(GuiGraphics graphics, List<Component> list, int x, int y, Font font) {
        if (list.isEmpty()) {
            return;
        }
        RenderSystem.disableDepthTest();
        int k = 0;
        for (Component o : list) {
            int l = font.m_92852_((FormattedText)o);
            if (l <= k) continue;
            k = l;
        }
        int j2 = x;
        int k2 = y;
        int i1 = 8;
        if (list.size() > 1) {
            i1 += 2 + (list.size() - 1) * 10;
        }
        graphics.m_280168_().m_85836_();
        graphics.m_280168_().m_252880_(0.0f, 0.0f, 300.0f);
        int j1 = -267386864;
        graphics.m_280024_(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
        graphics.m_280024_(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
        graphics.m_280024_(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
        graphics.m_280024_(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
        graphics.m_280024_(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
        int k1 = 0x505000FF;
        int l1 = (k1 & 0xFEFEFE) >> 1 | k1 & 0xFF000000;
        graphics.m_280024_(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
        graphics.m_280024_(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
        graphics.m_280024_(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
        graphics.m_280024_(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);
        for (int i2 = 0; i2 < list.size(); ++i2) {
            Component s1 = list.get(i2);
            graphics.m_280430_(font, s1, j2, k2, -1);
            if (i2 == 0) {
                k2 += 2;
            }
            k2 += 10;
        }
        graphics.m_280168_().m_85849_();
        RenderSystem.enableDepthTest();
    }
}

