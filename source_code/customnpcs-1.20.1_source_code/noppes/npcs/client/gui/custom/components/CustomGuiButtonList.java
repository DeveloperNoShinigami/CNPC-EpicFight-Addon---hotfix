/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.client.gui.custom.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonListWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.components.CustomGuiButton;
import noppes.npcs.client.gui.custom.components.CustomGuiTexturedRect;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketCustomGuiButtonList;

public class CustomGuiButtonList
extends CustomGuiButton {
    private CustomGuiTexturedRect left;
    private CustomGuiTexturedRect right;
    private CustomGuiTexturedRectWrapper leftWrapper;
    private CustomGuiTexturedRectWrapper rightWrapper;
    private boolean isRight = false;

    public CustomGuiButtonList(GuiCustom parent, CustomGuiButtonListWrapper component) {
        super(parent, component);
        this.onPress = button -> {
            CustomGuiButtonList list = (CustomGuiButtonList)button;
            component.setSelected(component.getSelected() + (list.isRight ? 1 : -1));
            list.m_93666_((Component)Component.m_237115_((String)component.getLabel()));
            this.sendPacket();
            if (!component.disablePackets) {
                Packets.sendServer(new SPacketCustomGuiButtonList(component.getUniqueID(), list.isRight));
            } else {
                component.onPress(parent.guiWrapper);
            }
        };
    }

    private void sendPacket() {
        Packets.sendServer(new SPacketCustomGuiButtonList(this.component.getUniqueID(), this.isRight));
    }

    public CustomGuiButtonList(GuiCustom parent, CustomGuiButtonListWrapper component, Button.OnPress onPress) {
        super(parent, component);
        this.component = component;
        this.onPress = onPress;
        this.init();
    }

    @Override
    public void init() {
        super.init();
        this.leftWrapper = ((CustomGuiButtonListWrapper)this.component).getLeftTexture();
        this.rightWrapper = ((CustomGuiButtonListWrapper)this.component).getRightTexture();
        this.left = new CustomGuiTexturedRect(this.parent, this.leftWrapper);
        this.right = new CustomGuiTexturedRect(this.parent, this.rightWrapper);
    }

    protected int getYImage(boolean p_93668_) {
        int i = 1;
        if (!this.f_93623_) {
            i = 0;
        } else if (p_93668_) {
            i = 2;
        }
        return i;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrixStack = graphics.m_280168_();
        super.onRender(graphics, mouseX, mouseY, partialTicks);
        matrixStack.m_85836_();
        matrixStack.m_252880_((float)this.m_252754_(), (float)this.m_252907_(), (float)this.id * 0.01f);
        this.isRight = mouseX >= this.m_252754_() + this.f_93618_ / 2;
        this.left.textureY = this.leftWrapper.getTextureY() + this.getYImage(this.hovered && !this.isRight) * this.leftWrapper.getHeight();
        this.left.onRender(graphics, mouseX - this.m_252754_(), mouseY - this.m_252907_(), partialTicks);
        this.right.textureY = this.rightWrapper.getTextureY() + this.getYImage(this.hovered && this.isRight) * this.rightWrapper.getHeight();
        this.right.onRender(graphics, mouseX - this.m_252754_(), mouseY - this.m_252907_(), partialTicks);
        this.renderLabel(graphics);
        matrixStack.m_85849_();
    }
}

