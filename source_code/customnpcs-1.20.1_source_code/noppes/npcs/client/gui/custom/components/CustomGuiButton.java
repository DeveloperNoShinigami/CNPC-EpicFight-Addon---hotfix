/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.client.gui.custom.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.components.CustomGuiTexturedRect;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketCustomGuiButton;

public class CustomGuiButton
extends Button
implements IGuiComponent {
    protected GuiCustom parent;
    private CustomGuiTexturedRect background;
    public CustomGuiButtonWrapper component;
    protected boolean hovered;
    private int colour = 0xFFFFFF;
    protected Button.OnPress onPress = button -> {
        if (!component.disablePackets) {
            Packets.sendServer(new SPacketCustomGuiButton(component.getUniqueID()));
        } else {
            component.onPress(parent.guiWrapper);
        }
    };
    public int id;

    public CustomGuiButton(GuiCustom parent, CustomGuiButtonWrapper component) {
        super(component.getPosX(), component.getPosY(), component.getWidth(), component.getHeight(), (Component)Component.m_237115_((String)component.getLabel()), btn -> {}, null);
        this.parent = parent;
        this.component = component;
        this.init();
    }

    public void m_5691_() {
        this.onPress.m_93750_((Button)this);
    }

    public void init() {
        this.id = this.component.getID();
        this.m_252865_(this.component.getPosX());
        this.m_253211_(this.component.getPosY());
        this.m_93674_(this.component.getWidth());
        this.setHeight(this.component.getHeight());
        this.background = new CustomGuiTexturedRect(this.parent, this.component.getTextureRect());
        this.m_93666_((Component)Component.m_237115_((String)this.component.getLabel()));
        this.f_93623_ = this.component.getEnabled() && this.component.getVisible();
        this.f_93624_ = this.component.getVisible();
    }

    public boolean m_7933_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        return false;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrixStack = graphics.m_280168_();
        if (!this.f_93624_) {
            return;
        }
        matrixStack.m_85836_();
        matrixStack.m_252880_((float)this.m_252754_(), (float)this.m_252907_(), (float)this.id * 0.01f);
        Minecraft mc = Minecraft.m_91087_();
        this.hovered = this.isHovered(mouseX, mouseY);
        int i = 0;
        i = this.component.getTexture().equals("textures/gui/widgets.png") ? (!this.f_93623_ ? 0 : (this.hovered ? 2 : 1)) : (this.hovered ? 1 : 0);
        this.background.textureY = this.component.getTextureY() + i * this.component.getTextureHoverOffset();
        this.background.onRender(graphics, mouseX - this.m_252754_(), mouseY - this.m_252907_(), partialTicks);
        this.renderLabel(graphics);
        if (!this.component.getDisplayItem().isEmpty()) {
            int xx = (int)(((float)this.f_93618_ - 16.0f) / 2.0f);
            int yy = (int)(((float)this.f_93619_ - 16.0f) / 2.0f) + 1;
            graphics.m_280168_().m_85836_();
            graphics.m_280168_().m_252880_(0.0f, 0.0f, -90.0f);
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.m_85836_();
            posestack.m_252880_((float)this.m_252754_(), (float)this.m_252907_(), -90.0f);
            RenderSystem.applyModelViewMatrix();
            graphics.m_280480_(this.component.getDisplayItem().getMCItemStack(), xx, yy);
            graphics.m_280370_(mc.f_91062_, this.component.getDisplayItem().getMCItemStack(), xx, yy);
            posestack.m_85849_();
            graphics.m_280168_().m_85849_();
            RenderSystem.applyModelViewMatrix();
        }
        if (this.hovered && this.component.hasHoverText()) {
            this.parent.hoverText = this.component.getHoverTextList();
        }
        matrixStack.m_85849_();
    }

    public void renderLabel(GuiGraphics graphics) {
        if (!this.component.getLabel().isEmpty()) {
            int j = 0xE0E0E0;
            if (this.colour != 0) {
                j = this.colour;
            } else if (!this.f_93623_) {
                j = 0xA0A0A0;
            } else if (this.hovered) {
                j = 0xFFFFA0;
            }
            Minecraft mc = Minecraft.m_91087_();
            graphics.m_280168_().m_252880_(0.0f, 0.0f, (float)this.id);
            graphics.m_280653_(mc.f_91062_, this.m_6035_(), this.f_93618_ / 2, (this.f_93619_ - 8) / 2, j);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    protected int hoverState(boolean mouseOver) {
        int i = 0;
        if (mouseOver) {
            i = 1;
        }
        return i;
    }
}

