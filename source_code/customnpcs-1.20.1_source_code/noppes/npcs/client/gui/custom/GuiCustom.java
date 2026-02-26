/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 */
package noppes.npcs.client.gui.custom;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.client.gui.custom.GuiCustomComponents;
import noppes.npcs.client.gui.custom.GuiCustomScrollingPanel;
import noppes.npcs.client.gui.custom.components.CustomGuiTexturedRect;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.client.gui.util.GuiTooltipUtils;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketCustomGuiSubGuiClosed;
import noppes.npcs.shared.client.gui.listeners.IGuiData;

public class GuiCustom
extends AbstractContainerScreen<ContainerCustomGui>
implements IGuiData {
    protected CustomGuiTexturedRect background;
    public CustomGuiWrapper guiWrapper;
    public List<Component> hoverText;
    protected GuiCustomComponents components = new GuiCustomComponents();
    protected GuiCustomScrollingPanel scrollingPanel = new GuiCustomScrollingPanel();
    public GuiCustom subgui = null;
    public GuiCustom parent = null;
    public Inventory inv;
    public InitCallback initCallback;

    public GuiCustom(ContainerCustomGui container, Inventory inv, Component titleIn) {
        super((AbstractContainerMenu)container, inv, titleIn);
        this.inv = inv;
    }

    public void m_7856_() {
        super.m_7856_();
        if (this.guiWrapper != null) {
            this.scrollingPanel.setComponents(this, this.guiWrapper.getScrollingPanel());
            this.components.setComponents(this, this.guiWrapper);
        }
        if (this.initCallback != null) {
            this.initCallback.init();
        }
        if (this.subgui != null) {
            this.subgui.m_7856_();
        }
    }

    public void m_181908_() {
        if (this.subgui != null) {
            this.subgui.m_181908_();
        } else {
            this.components.containerTick();
            this.scrollingPanel.containerTick();
        }
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.hoverText = null;
        PoseStack matrixStack = graphics.m_280168_();
        this.m_280273_(graphics);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.m_85836_();
        posestack.m_252880_((float)this.getGuiLeft(), (float)this.getGuiTop(), 0.0f);
        RenderSystem.applyModelViewMatrix();
        matrixStack.m_85836_();
        if (this.background != null) {
            this.background.onRender(graphics, mouseX, mouseY, partialTicks);
        }
        this.components.render(graphics, mouseX - this.getGuiLeft(), mouseY - this.getGuiTop(), partialTicks);
        this.scrollingPanel.render(graphics, mouseX - this.getGuiLeft(), mouseY - this.getGuiTop(), partialTicks);
        if (this.hoverText != null && !this.hoverText.isEmpty() && this.subgui == null) {
            GuiTooltipUtils.renderTooltip(graphics, this.f_96547_, this.hoverText, Optional.empty(), mouseX - this.getGuiLeft(), mouseY - this.getGuiTop());
        }
        posestack.m_85849_();
        RenderSystem.applyModelViewMatrix();
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        if (this.subgui == null) {
            this.m_280072_(graphics, mouseX, mouseY);
        }
        matrixStack.m_85849_();
        if (this.subgui != null) {
            matrixStack.m_85836_();
            posestack.m_85836_();
            posestack.m_252880_(0.0f, 0.0f, 40.0f);
            RenderSystem.applyModelViewMatrix();
            matrixStack.m_252880_(0.0f, 0.0f, 40.0f);
            this.subgui.m_88315_(graphics, mouseX, mouseY, partialTicks);
            matrixStack.m_85849_();
            posestack.m_85849_();
            RenderSystem.applyModelViewMatrix();
        }
    }

    protected void m_7286_(GuiGraphics p_283065_, float p_97788_, int p_97789_, int p_97790_) {
    }

    protected void m_280003_(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
    }

    public boolean m_5534_(char typedChar, int keyCode) {
        if (this.subgui != null) {
            return this.subgui.m_5534_(typedChar, keyCode);
        }
        if (this.components.charTyped(typedChar, keyCode)) {
            return true;
        }
        if (this.scrollingPanel.charTyped(typedChar, keyCode)) {
            return true;
        }
        return super.m_5534_(typedChar, keyCode);
    }

    public boolean m_7933_(int key, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (this.subgui != null) {
            return this.subgui.m_7933_(key, p_keyPressed_2_, p_keyPressed_3_);
        }
        if (this.components.keyPressed(key, p_keyPressed_2_, p_keyPressed_3_)) {
            return true;
        }
        if (this.scrollingPanel.keyPressed(key, p_keyPressed_2_, p_keyPressed_3_)) {
            return true;
        }
        if (this.f_96541_.f_91066_.f_92092_.isActiveAndMatches(InputConstants.m_84827_((int)key, (int)p_keyPressed_2_))) {
            return true;
        }
        return super.m_7933_(key, p_keyPressed_2_, p_keyPressed_3_);
    }

    public boolean m_6375_(double mouseX, double mouseY, int mouseButton) {
        if (this.subgui != null) {
            return this.subgui.m_6375_(mouseX, mouseY, mouseButton);
        }
        boolean clicked = false;
        clicked |= this.components.mouseClicked(mouseX - (double)this.getGuiLeft(), mouseY - (double)this.getGuiTop(), mouseButton);
        return (clicked |= this.scrollingPanel.mouseClicked(mouseX - (double)this.getGuiLeft(), mouseY - (double)this.getGuiTop(), mouseButton)) | super.m_6375_(mouseX, mouseY, mouseButton);
    }

    public boolean m_6050_(double mouseX, double mouseY, double mouseScrolled) {
        if (this.subgui != null) {
            return this.subgui.m_6050_(mouseX, mouseY, mouseScrolled);
        }
        if (super.m_6050_(mouseX, mouseY, mouseScrolled)) {
            return true;
        }
        return this.scrollingPanel.mouseScrolled(mouseX - (double)this.getGuiLeft(), mouseY - (double)this.getGuiTop(), mouseScrolled);
    }

    public boolean m_7979_(double mouseX, double mouseY, int mouseButton, double dx, double dy) {
        if (this.subgui != null) {
            return this.subgui.m_7979_(mouseX, mouseY, mouseButton, dx, dy);
        }
        boolean clicked = false;
        clicked |= this.components.mouseDragged(mouseX - (double)this.getGuiLeft(), mouseY - (double)this.getGuiTop(), mouseButton, dx, dy);
        return (clicked |= this.scrollingPanel.mouseDragged(mouseX - (double)this.getGuiLeft(), mouseY - (double)this.getGuiTop(), mouseButton, dx, dy)) | super.m_7979_(mouseX, mouseY, mouseButton, dx, dy);
    }

    public boolean m_6348_(double mouseX, double mouseY, int mouseButton) {
        if (this.subgui != null) {
            return this.subgui.m_6348_(mouseX, mouseY, mouseButton);
        }
        boolean clicked = false;
        clicked |= this.components.mouseReleased(mouseX - (double)this.getGuiLeft(), mouseY - (double)this.getGuiTop(), mouseButton);
        return (clicked |= this.scrollingPanel.mouseReleased(mouseX - (double)this.getGuiLeft(), mouseY - (double)this.getGuiTop(), mouseButton)) | super.m_6348_(mouseX, mouseY, mouseButton);
    }

    public boolean m_7043_() {
        if (this.guiWrapper != null) {
            return this.guiWrapper.getDoesPauseGame();
        }
        return true;
    }

    public boolean m_6913_() {
        if (this.guiWrapper != null) {
            return this.guiWrapper.getClosesOnEsc();
        }
        return true;
    }

    public void m_7379_() {
        if (this.subgui == null) {
            if (this.parent == null) {
                super.m_7379_();
            } else {
                Packets.sendServer(new SPacketCustomGuiSubGuiClosed());
                this.parent.subgui = null;
            }
        } else {
            this.subgui.m_7379_();
        }
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.setGuiWrapper((CustomGuiWrapper)new CustomGuiWrapper((IPlayer)NpcAPI.Instance().getIEntity((Entity)Minecraft.m_91087_().f_91074_)).fromNBT(compound));
        this.m_7856_();
    }

    public void m_6574_(Minecraft minecraft, int width, int height) {
        super.m_6574_(minecraft, width, height);
        if (this.subgui != null) {
            this.subgui.m_6574_(minecraft, width, height);
        }
    }

    public void setGuiWrapper(CustomGuiWrapper guiWrapper) {
        this.guiWrapper = guiWrapper;
        this.f_97726_ = guiWrapper.getWidth();
        this.f_97727_ = guiWrapper.getHeight();
        this.background = new CustomGuiTexturedRect(this, (CustomGuiTexturedRectWrapper)guiWrapper.getBackgroundRect());
        if (guiWrapper.hasSubGui()) {
            if (this.subgui == null) {
                this.subgui = new GuiCustom((ContainerCustomGui)this.f_97732_, Minecraft.m_91087_().f_91074_.m_150109_(), (Component)Component.m_237119_());
                this.subgui.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
            }
            this.subgui.parent = this;
            this.subgui.setGuiWrapper(guiWrapper.getSubGui());
        } else {
            ((ContainerCustomGui)this.f_97732_).setGui(guiWrapper, (Player)Minecraft.m_91087_().f_91074_);
            this.subgui = null;
            if (this.parent == null) {
                this.m_7856_();
            }
        }
    }

    public IGuiComponent getComponent(UUID id) {
        Optional<IGuiComponent> c = this.components.components.values().stream().filter(t -> t.component() != null && t.component().getUniqueID().equals(id)).findFirst();
        if (c.isPresent()) {
            return c.get();
        }
        c = this.scrollingPanel.components.values().stream().filter(t -> t.component() != null && t.component().getUniqueID().equals(id)).findFirst();
        if (c.isPresent()) {
            return c.get();
        }
        if (this.subgui != null) {
            return this.subgui.getComponent(id);
        }
        return null;
    }

    public int getTotalGuiLeft() {
        if (this.parent != null) {
            return this.parent.getTotalGuiLeft() + this.getGuiLeft();
        }
        return this.getGuiLeft();
    }

    public int getTotalGuiTop() {
        if (this.parent != null) {
            return this.parent.getTotalGuiTop() + this.getGuiTop();
        }
        return this.getGuiTop();
    }

    public void add(IGuiComponent component) {
        this.components.components.put(component.getID(), component);
    }

    public void addPanel(IGuiComponent component) {
        this.scrollingPanel.components.put(component.getID(), component);
    }

    public static interface InitCallback {
        public void init();
    }
}

