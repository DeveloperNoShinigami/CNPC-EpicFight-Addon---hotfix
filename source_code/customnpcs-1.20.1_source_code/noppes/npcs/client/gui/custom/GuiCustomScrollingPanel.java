/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 */
package noppes.npcs.client.gui.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.gui.IItemSlot;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;
import noppes.npcs.api.wrapper.gui.GuiComponentsScrollableWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.GuiCustomComponents;
import noppes.npcs.client.gui.custom.components.CustomGuiTexturedRect;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.mixin.MouseHelperMixin;
import noppes.npcs.util.ValueUtil;

public class GuiCustomScrollingPanel
extends GuiCustomComponents {
    public GuiComponentsScrollableWrapper comps;
    private int maxSize = 0;
    private int scrollMaxHeight = 0;
    private int scrollPercentage = 0;
    private GuiCustom gui;
    private boolean isScrolling = false;
    private final CustomGuiTexturedRect scrollbar = new CustomGuiTexturedRect(null, new CustomGuiTexturedRectWrapper(-1, resource.toString(), 0, 0, 14, 64, 65, 0).setRepeatingTexture(14, 64, 1));
    private final CustomGuiTexturedRect button = new CustomGuiTexturedRect(null, new CustomGuiTexturedRectWrapper(-1, resource.toString(), 0, 0, 12, 15, 0, 214));

    public void setComponents(GuiCustom gui, GuiComponentsScrollableWrapper comps) {
        super.setComponents(gui, comps);
        this.gui = gui;
        this.comps = comps;
        this.button.x = comps.width - 13;
        this.scrollbar.x = comps.width - 14;
        this.scrollbar.height = comps.height;
        this.scrollMaxHeight = comps.height - 17;
        this.maxSize = comps.getComponents().stream().mapToInt(v -> v.getPosY() + v.getHeight()).max().orElse(0);
        if (!this.canScroll()) {
            this.scrollPercentage = 0;
            comps.scrollAmount = 0;
        } else {
            this.setScrollAmount(this.scrollPercentage * (this.maxSize - comps.height) / 100);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrixStack = graphics.m_280168_();
        mouseX -= this.comps.x;
        mouseY -= this.comps.y;
        matrixStack.m_85836_();
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.m_85836_();
        posestack.m_252880_((float)this.comps.x, (float)this.comps.y, 10.0f);
        RenderSystem.applyModelViewMatrix();
        if (this.canScroll()) {
            this.scrollbar.onRender(graphics, mouseX, mouseY, partialTicks);
            if (this.isScrolling) {
                if (((MouseHelperMixin)Minecraft.m_91087_().f_91067_).getActiveButton() == 0) {
                    this.scrollPercentage = ValueUtil.CorrectInt((mouseY - 7) * 100 / this.scrollMaxHeight, 0, 100);
                } else {
                    this.isScrolling = false;
                }
            }
            this.button.textureX = 0;
            if (this.scrollButtonHovered(mouseX, mouseY) || this.isScrolling) {
                this.button.textureX = 24;
            }
            this.button.y = 1 + this.scrollPercentage * this.scrollMaxHeight / 100;
            this.button.onRender(graphics, mouseX, mouseY, partialTicks);
            this.setScrollAmount(this.scrollPercentage * (this.maxSize - this.comps.height) / 100);
            matrixStack.m_252880_(0.0f, (float)(-this.comps.scrollAmount), 0.0f);
            for (ICustomGuiComponent component : this.comps.getComponents()) {
                if (!this.comps.isVisible(component)) continue;
                ((IGuiComponent)this.components.get(component.getID())).onRender(graphics, mouseX, mouseY + this.comps.scrollAmount, partialTicks);
            }
            for (IItemSlot slot : this.slots) {
                if (!this.comps.isVisible(slot) || slot.getGuiType() <= 0) continue;
                this.renderSlot(graphics, slot);
            }
            for (ICustomGuiComponent component : this.comps.getComponents()) {
                if (!this.comps.isVisible(component)) continue;
                ((IGuiComponent)this.components.get(component.getID())).onRenderPost(graphics, mouseX, mouseY + this.comps.scrollAmount, partialTicks);
            }
        } else {
            super.render(graphics, mouseX, mouseY, partialTicks);
        }
        matrixStack.m_85849_();
        posestack.m_85849_();
        RenderSystem.applyModelViewMatrix();
    }

    private void setScrollAmount(int amount) {
        if (amount == this.comps.scrollAmount) {
            return;
        }
        this.comps.scrollAmount = amount;
        ((ContainerCustomGui)this.gui.m_6262_()).update();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        mouseX -= (double)this.comps.x;
        mouseY -= (double)this.comps.y;
        if (!this.canScroll()) {
            return super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (this.scrollBarHovered(mouseX, mouseY) && mouseButton == 0) {
            this.isScrolling = true;
            this.scrollPercentage = ValueUtil.CorrectInt((int)(mouseY - 7.0) * 100 / this.scrollMaxHeight, 0, 100);
            return true;
        }
        boolean clicked = false;
        for (ICustomGuiComponent component : this.comps.getComponents()) {
            GuiEventListener guiEvent;
            IGuiComponent comp;
            if (!this.comps.isVisible(component) || !((comp = (IGuiComponent)this.components.get(component.getID())) instanceof GuiEventListener) || !(guiEvent = (GuiEventListener)comp).m_6375_(mouseX, mouseY + (double)this.comps.scrollAmount, mouseButton)) continue;
            if (mouseButton == 0) {
                this.draggingId = comp.getID();
            }
            clicked = true;
        }
        return clicked;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int mouseButton, double dx, double dy) {
        if (this.isScrolling || this.draggingId < 0) {
            return false;
        }
        mouseX -= (double)this.comps.x;
        mouseY -= (double)this.comps.y;
        if (!this.canScroll()) {
            return super.mouseDragged(mouseX, mouseY, mouseButton, dx, dy);
        }
        for (ICustomGuiComponent component : this.comps.getComponents()) {
            IGuiComponent comp;
            if (!this.comps.isVisible(component) || !((comp = (IGuiComponent)this.components.get(component.getID())) instanceof GuiEventListener)) continue;
            GuiEventListener guiEvent = (GuiEventListener)comp;
            if (component.getID() != this.draggingId || !guiEvent.m_7979_(mouseX, mouseY + (double)this.comps.scrollAmount, mouseButton, dx, dy)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        mouseX -= (double)this.comps.x;
        mouseY -= (double)this.comps.y;
        if (!this.canScroll()) {
            return super.mouseReleased(mouseX, mouseY, mouseButton);
        }
        for (ICustomGuiComponent component : this.comps.getComponents()) {
            IGuiComponent comp;
            if (!this.comps.isVisible(component) || !((comp = (IGuiComponent)this.components.get(component.getID())) instanceof GuiEventListener)) continue;
            GuiEventListener guiEvent = (GuiEventListener)comp;
            if (component.getID() != this.draggingId || !guiEvent.m_6348_(mouseX, mouseY + (double)this.comps.scrollAmount, mouseButton)) continue;
            this.draggingId = -1;
            return true;
        }
        this.draggingId = -1;
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double mouseScrolled) {
        if (mouseScrolled != 0.0 && this.panelHovered(mouseX - (double)this.comps.x, mouseY - (double)this.comps.y)) {
            this.scrollPercentage += mouseScrolled > 0.0 ? -4 : 4;
            this.scrollPercentage = ValueUtil.CorrectInt(this.scrollPercentage, 0, 100);
            return true;
        }
        return false;
    }

    public boolean canScroll() {
        return this.maxSize > this.comps.height;
    }

    public boolean panelHovered(double x, double y) {
        return this.canScroll() && x >= 0.0 && y >= 0.0 && x < (double)this.comps.width && y < (double)this.comps.height;
    }

    private boolean scrollBarHovered(double x, double y) {
        return this.panelHovered(x, y) && x >= (double)this.scrollbar.x && y >= (double)this.scrollbar.y && x < (double)(this.scrollbar.x + this.scrollbar.width) && y < (double)(this.scrollbar.y + this.scrollbar.height);
    }

    private boolean scrollButtonHovered(double x, double y) {
        return this.scrollBarHovered(x, y) && y > (double)this.button.y && y < (double)(this.button.y + 15);
    }

    public void setMaxSize(int size) {
        this.maxSize = size;
    }
}

