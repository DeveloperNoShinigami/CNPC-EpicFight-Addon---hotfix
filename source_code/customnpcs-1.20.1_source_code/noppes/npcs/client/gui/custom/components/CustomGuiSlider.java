/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 */
package noppes.npcs.client.gui.custom.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiSliderWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.components.CustomGuiTextField;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketCustomGuiSliderUpdate;

public class CustomGuiSlider
extends AbstractWidget
implements IGuiComponent {
    private GuiCustom parent;
    private CustomGuiSliderWrapper component;
    private CustomGuiTextFieldWrapper tfcomponent;
    private CustomGuiTextField textfield;
    private float sliderValue;
    private float startValue;
    private long lastClickedTime = 0L;
    private float total;
    public int id;
    private boolean disablePackets = false;

    public CustomGuiSlider(GuiCustom parent, CustomGuiSliderWrapper component) {
        super(component.getPosX(), component.getPosY(), component.getWidth(), component.getHeight(), (Component)Component.m_237110_((String)component.getFormat(), (Object[])new Object[]{Float.valueOf(component.getValue())}));
        this.component = component;
        this.parent = parent;
        this.tfcomponent = new CustomGuiTextFieldWrapper(this.id, this.m_252754_(), this.m_252907_(), this.f_93618_, this.f_93619_).setCharacterType(3);
        this.m_7856_();
    }

    @Override
    public void m_7856_() {
        this.id = this.component.getID();
        this.m_252865_(this.component.getPosX());
        this.m_253211_(this.component.getPosY());
        this.m_93674_(this.component.getWidth());
        this.setHeight(this.component.getHeight());
        this.total = this.component.getMax() - this.component.getMin();
        this.startValue = this.sliderValue = (this.component.getValue() - this.component.getMin()) / this.total;
        this.tfcomponent.setID(this.id);
        this.tfcomponent.setPos(this.component.getPosX(), this.component.getPosY());
        this.tfcomponent.setSize(this.component.getWidth(), this.component.getHeight());
        this.f_93623_ = this.component.getEnabled() && this.component.getVisible();
        this.f_93624_ = this.component.getVisible();
        this.m_93666_((Component)Component.m_237110_((String)this.component.getFormat(), (Object[])new Object[]{Float.valueOf(this.component.getValue())}));
    }

    public CustomGuiSlider disablePackets() {
        this.disablePackets = true;
        return this;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        boolean hovered;
        PoseStack matrixStack = graphics.m_280168_();
        if (!this.f_93624_) {
            return;
        }
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        if (this.textfield != null) {
            this.textfield.onRender(graphics, mouseX, mouseY, partialTicks);
            if (!this.textfield.m_93696_()) {
                this.closeTextfield();
            }
        }
        boolean bl = hovered = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        if (hovered && this.component.hasHoverText()) {
            this.parent.hoverText = this.component.getHoverTextList();
        }
    }

    private void setSliderValue(float value) {
        if ((value = Mth.m_14036_((float)value, (float)0.0f, (float)1.0f)) == this.sliderValue) {
            return;
        }
        this.sliderValue = value;
        this.component.setValue(value * this.total + this.component.getMin());
        this.m_93666_((Component)Component.m_237110_((String)this.component.getFormat(), (Object[])new Object[]{Float.valueOf(this.component.getValue())}));
        if (!this.disablePackets) {
            Packets.sendServer(new SPacketCustomGuiSliderUpdate(this.component.getUniqueID(), this.component.getValue()));
        } else {
            this.component.onChange(null);
        }
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        if (this.textfield != null && (keyCode == 257 || keyCode == 335)) {
            this.closeTextfield();
        }
        if (this.textfield != null) {
            return this.textfield.m_7933_(keyCode, scanCode, modifiers);
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }

    private void closeTextfield() {
        this.setSliderValue((this.tfcomponent.getFloat() + this.component.getMin()) / this.total);
        this.textfield = null;
    }

    public boolean m_5534_(char c, int i) {
        if (this.textfield != null) {
            return this.textfield.m_5534_(c, i);
        }
        return super.m_5534_(c, i);
    }

    protected void m_87963_(GuiGraphics p_93676_, int p_93677_, int p_93678_, float p_93679_) {
        Minecraft minecraft = Minecraft.m_91087_();
        Font font = minecraft.f_91062_;
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)f_93617_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)this.f_93625_);
        int i = 0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        p_93676_.m_280218_(f_93617_, this.m_252754_(), this.m_252907_(), 0, 46 + i * 20, this.f_93618_ / 2, this.f_93619_);
        p_93676_.m_280218_(f_93617_, this.m_252754_() + this.f_93618_ / 2, this.m_252907_(), 200 - this.f_93618_ / 2, 46 + i * 20, this.f_93618_ / 2, this.f_93619_);
        this.renderBg(p_93676_, minecraft, p_93677_, p_93678_);
        int j = this.getFGColor();
        p_93676_.m_280653_(font, this.m_6035_(), this.m_252754_() + this.f_93618_ / 2, this.m_252907_() + (this.f_93619_ - 8) / 2, j | Mth.m_14167_((float)(this.f_93625_ * 255.0f)) << 24);
    }

    public void m_5716_(double x, double y) {
        if (!this.f_93624_ || !this.f_93623_) {
            return;
        }
        long time = System.currentTimeMillis();
        if (time - this.lastClickedTime < 500L) {
            this.tfcomponent.setText("" + this.component.getValue());
            this.textfield = new CustomGuiTextField(this.parent, this.tfcomponent);
            this.textfield.m_93692_(true);
        } else if (this.textfield != null) {
            this.textfield.m_6375_(x, y, 0);
            return;
        }
        this.lastClickedTime = time;
        this.setSliderValue((float)(x - (double)(this.m_252754_() + 4)) / (float)(this.f_93618_ - 8));
    }

    public boolean m_6375_(double mouseX, double mouseY, int mouseButton) {
        if (this.textfield != null) {
            return this.textfield.m_6375_(mouseX, mouseY, mouseButton);
        }
        return super.m_6375_(mouseX, mouseY, mouseButton);
    }

    public boolean m_7979_(double mouseX, double mouseY, int mouseButton, double dx, double dy) {
        this.setSliderValue((float)(mouseX - (double)(this.m_252754_() + 4)) / (float)(this.f_93618_ - 8));
        return true;
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
    }

    public void tick() {
        if (this.textfield != null) {
            this.textfield.m_94120_();
        }
    }

    public void m_7691_(double x, double y) {
        if (this.sliderValue == this.startValue) {
            return;
        }
        super.m_7435_(Minecraft.m_91087_().m_91106_());
        this.startValue = this.sliderValue;
    }

    public void renderBg(GuiGraphics graphics, Minecraft mc, int p_146119_2_, int p_146119_3_) {
        if (!this.f_93624_) {
            return;
        }
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)f_93617_);
        int lvt_4_1_ = (this.f_93622_ ? 2 : 1) * 20;
        graphics.m_280168_().m_85836_();
        graphics.m_280168_().m_252880_(0.0f, 0.0f, 10.0f);
        graphics.m_280218_(f_93617_, this.m_252754_() + (int)((double)this.sliderValue * (double)(this.f_93618_ - 8)), this.m_252907_(), 0, 46 + lvt_4_1_, 4, this.f_93619_ / 2);
        graphics.m_280218_(f_93617_, this.m_252754_() + (int)((double)this.sliderValue * (double)(this.f_93618_ - 8)), this.m_252907_() + this.f_93619_ / 2, 0, 46 + lvt_4_1_ + 20 - this.f_93619_ / 2, 4, this.f_93619_ / 2);
        graphics.m_280218_(f_93617_, this.m_252754_() + (int)((double)this.sliderValue * (double)(this.f_93618_ - 8)) + 4, this.m_252907_(), 196, 46 + lvt_4_1_, 4, this.f_93619_ / 2);
        graphics.m_280218_(f_93617_, this.m_252754_() + (int)((double)this.sliderValue * (double)(this.f_93618_ - 8)) + 4, this.m_252907_() + this.f_93619_ / 2, 196, 46 + lvt_4_1_ + 20 - this.f_93619_ / 2, 4, this.f_93619_ / 2);
        graphics.m_280168_().m_85849_();
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }
}

