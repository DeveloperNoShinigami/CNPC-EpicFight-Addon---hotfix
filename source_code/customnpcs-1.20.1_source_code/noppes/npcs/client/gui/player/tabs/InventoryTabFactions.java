/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package noppes.npcs.client.gui.player.tabs;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import noppes.npcs.client.gui.player.GuiFaction;
import noppes.npcs.client.gui.player.tabs.AbstractTab;

public class InventoryTabFactions
extends AbstractTab {
    private Component displayString = Component.m_237115_((String)"menu.factions");

    public InventoryTabFactions() {
        super(1, 0, 0, new ItemStack((ItemLike)Items.f_42727_, 1));
    }

    @Override
    public void onTabClicked() {
        Minecraft mc = Minecraft.m_91087_();
        mc.m_91152_((Screen)new GuiFaction());
    }

    @Override
    public boolean shouldAddToList() {
        return true;
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        boolean hovered;
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        if (!this.f_93624_) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        boolean bl = hovered = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        if (hovered) {
            graphics.m_280168_().m_252880_((float)mouseX, (float)(this.m_252907_() + 2), 0.0f);
            this.drawHoveringText(graphics, Arrays.asList(this.displayString), -mc.f_91062_.m_92852_((FormattedText)this.displayString), 0, mc.f_91062_);
            graphics.m_280168_().m_252880_((float)(-mouseX), (float)(-(this.m_252907_() + 2)), 0.0f);
        }
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
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

