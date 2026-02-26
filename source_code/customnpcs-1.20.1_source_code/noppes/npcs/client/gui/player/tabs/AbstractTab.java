/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractButton
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.client.gui.player.tabs;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractTab
extends AbstractButton {
    public int id;
    ResourceLocation texture = new ResourceLocation("customnpcs", "textures/gui/tabs.png");
    ItemStack renderStack;

    public AbstractTab(int id, int posX, int posY, ItemStack renderStack) {
        super(posX, posY, 28, 32, (Component)Component.m_237115_((String)""));
        this.renderStack = renderStack;
        this.id = id;
    }

    public AbstractTab init(Screen s) {
        int guiLeft = (s.f_96543_ - 176) / 2;
        int guiTop = (s.f_96544_ - 166) / 2;
        this.m_252865_(guiLeft + this.id * 28);
        this.m_253211_(guiTop - 28);
        return this;
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.f_93624_) {
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft mc = Minecraft.m_91087_();
            int yTexPos = this.f_93623_ ? 3 : 32;
            int ySize = this.f_93623_ ? 25 : 32;
            int xOffset = this.id == 2 ? 0 : 1;
            int yPos = this.m_252907_() + (this.f_93623_ ? 3 : 0);
            ItemRenderer itemRender = mc.m_91291_();
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.texture);
            graphics.m_280218_(this.texture, this.m_252754_(), yPos, xOffset * 28, yTexPos, 28, ySize);
            graphics.m_280168_().m_85836_();
            graphics.m_280168_().m_252880_(0.0f, 0.0f, 30.0f);
            graphics.m_280480_(this.renderStack, this.m_252754_() + 6, this.m_252907_() + 8);
            graphics.m_280302_(mc.f_91062_, this.renderStack, this.m_252754_() + 6, this.m_252907_() + 8, null);
            graphics.m_280168_().m_85849_();
        }
    }

    public void m_5716_(double mouseX, double mouseY) {
        this.onTabClicked();
    }

    public void m_5691_() {
    }

    public abstract void onTabClicked();

    public abstract boolean shouldAddToList();
}

