/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.shared.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.net.URI;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiMenuSideButton;
import noppes.npcs.shared.client.gui.components.GuiMenuTopButton;
import noppes.npcs.shared.client.gui.components.GuiSliderNop;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.components.GuiWrapper;
import noppes.npcs.shared.client.gui.listeners.IGui;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiBasic
extends Screen
implements IGuiInterface {
    public LocalPlayer player;
    public boolean drawDefaultBackground = true;
    public String title;
    public ResourceLocation background = null;
    public boolean closeOnEsc = true;
    public int guiLeft;
    public int guiTop;
    public int imageWidth;
    public int imageHeight;
    public float bgScale = 1.0f;
    public GuiWrapper wrapper = new GuiWrapper(this);

    public GuiBasic() {
        super((Component)Component.m_237119_());
        this.player = Minecraft.m_91087_().f_91074_;
        this.f_96541_ = Minecraft.m_91087_();
        this.title = "";
        this.imageWidth = 200;
        this.imageHeight = 222;
        this.f_96541_ = Minecraft.m_91087_();
        this.f_96547_ = this.f_96541_.f_91062_;
    }

    public void setBackground(String texture) {
        this.background = new ResourceLocation("customnpcs", "textures/gui/" + texture);
    }

    public ResourceLocation getResource(String texture) {
        return new ResourceLocation("customnpcs", "textures/gui/" + texture);
    }

    public void m_7856_() {
        super.m_7856_();
        this.m_7522_(null);
        this.guiLeft = (this.f_96543_ - this.imageWidth) / 2;
        this.guiTop = (this.f_96544_ - this.imageHeight) / 2;
        this.f_169369_.clear();
        this.m_6702_().clear();
        this.wrapper.init(this.f_96541_, this.f_96543_, this.f_96544_);
    }

    @Override
    public GuiWrapper getWrapper() {
        return this.wrapper;
    }

    @Override
    public void initGui() {
        this.m_7856_();
    }

    public void m_86600_() {
        this.wrapper.tick();
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
    }

    public boolean m_6050_(double mouseX, double mouseY, double scrolled) {
        if (this.wrapper.mouseScrolled(mouseX, mouseY, scrolled)) {
            return true;
        }
        return super.m_6050_(mouseX, mouseY, scrolled);
    }

    public boolean m_6375_(double i, double j, int k) {
        if (this.wrapper.mouseClicked(i, j, k)) {
            return true;
        }
        return super.m_6375_(i, j, k);
    }

    public boolean m_7979_(double x, double y, int button, double dx, double dy) {
        if (this.wrapper.mouseDragged(x, y, button, dx, dy)) {
            return true;
        }
        return super.m_7979_(x, y, button, dx, dy);
    }

    public boolean m_6348_(double x, double y, int button) {
        if (this.wrapper.mouseReleased(x, y, button)) {
            return true;
        }
        return super.m_6348_(x, y, button);
    }

    public void m_7522_(@Nullable GuiEventListener gui) {
        if (this.wrapper.subgui != null) {
            this.wrapper.subgui.m_7522_(gui);
        } else {
            if (gui != null && !this.m_6702_().contains(gui)) {
                return;
            }
            this.wrapper.changeFocus(this.m_7222_(), gui);
            super.m_7522_(gui);
        }
    }

    public GuiEventListener m_7222_() {
        if (this.wrapper.subgui != null) {
            return this.wrapper.subgui.m_7222_();
        }
        return super.m_7222_();
    }

    @Override
    public void elementClicked() {
        if (this.wrapper.subgui != null && this.wrapper.subgui instanceof GuiBasic) {
            ((GuiBasic)this.wrapper.subgui).elementClicked();
        }
    }

    @Override
    public void subGuiClosed(Screen subgui) {
    }

    public boolean m_5534_(char c, int i) {
        if (this.wrapper.charTyped(c, i)) {
            return true;
        }
        return super.m_5534_(c, i);
    }

    public boolean m_7933_(int key, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (this.wrapper.keyPressed(key, p_keyPressed_2_, p_keyPressed_3_)) {
            return true;
        }
        return super.m_7933_(key, p_keyPressed_2_, p_keyPressed_3_);
    }

    public boolean isInventoryKey(int i) {
        return this.f_96541_.f_91066_.f_92092_.getKey().m_84873_() == i;
    }

    public boolean m_6913_() {
        return this.closeOnEsc;
    }

    public void close() {
        this.m_7379_();
    }

    public void m_7379_() {
        this.wrapper.close();
    }

    public void addButton(GuiButtonNop button) {
        this.wrapper.npcbuttons.put(button.id, button);
        super.m_142416_((GuiEventListener)button);
    }

    public void addTopButton(GuiMenuTopButton button) {
        this.wrapper.topbuttons.put(button.id, button);
        super.m_142416_((GuiEventListener)button);
    }

    public void addSideButton(GuiMenuSideButton button) {
        this.wrapper.sidebuttons.put(button.id, button);
        super.m_142416_((GuiEventListener)button);
    }

    public GuiButtonNop getButton(int i) {
        return this.wrapper.npcbuttons.get(i);
    }

    public GuiMenuSideButton getSideButton(int i) {
        return this.wrapper.sidebuttons.get(i);
    }

    public GuiMenuTopButton getTopButton(int i) {
        return this.wrapper.topbuttons.get(i);
    }

    public void addTextField(GuiTextFieldNop tf) {
        this.wrapper.textfields.put(tf.id, tf);
    }

    public GuiTextFieldNop getTextField(int i) {
        return this.wrapper.textfields.get(i);
    }

    public void add(IGui gui) {
        this.wrapper.components.add(gui);
    }

    public IGui get(int id) {
        for (IGui comp : this.wrapper.components) {
            if (comp.getID() != id) continue;
            return comp;
        }
        return null;
    }

    public void addLabel(GuiLabel label) {
        this.wrapper.labels.put(label.id, label);
    }

    public GuiLabel getLabel(int i) {
        return this.wrapper.labels.get(i);
    }

    public void addSlider(GuiSliderNop slider) {
        this.wrapper.sliders.put(slider.id, slider);
        this.m_142416_((GuiEventListener)slider);
    }

    public GuiSliderNop getSlider(int i) {
        return this.wrapper.sliders.get(i);
    }

    public void addScroll(GuiCustomScrollNop scroll) {
        scroll.m_6575_(this.f_96541_, scroll.f_96543_, scroll.f_96544_);
        this.wrapper.scrolls.put(scroll.id, scroll);
    }

    public GuiCustomScrollNop getScroll(int id) {
        return this.wrapper.scrolls.get(id);
    }

    @Override
    public void save() {
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        PoseStack matrixStack = graphics.m_280168_();
        this.wrapper.mouseX = mouseX;
        this.wrapper.mouseY = mouseY;
        int x = mouseX;
        int y = mouseY;
        if (this.wrapper.subgui != null) {
            y = 0;
            x = 0;
        }
        if (this.drawDefaultBackground && this.wrapper.subgui == null) {
            this.m_280273_(graphics);
        }
        if (this.background != null) {
            matrixStack.m_85836_();
            matrixStack.m_252880_((float)this.guiLeft, (float)this.guiTop, 0.0f);
            matrixStack.m_85841_(this.bgScale, this.bgScale, this.bgScale);
            RenderSystem.setShader(GameRenderer::m_172817_);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.background);
            if (this.imageWidth > 256) {
                graphics.m_280218_(this.background, 0, 0, 0, 0, 250, this.imageHeight);
                graphics.m_280218_(this.background, 250, 0, 256 - (this.imageWidth - 250), 0, this.imageWidth - 250, this.imageHeight);
            } else {
                graphics.m_280218_(this.background, 0, 0, 0, 0, this.imageWidth, this.imageHeight);
            }
            matrixStack.m_85849_();
        }
        graphics.m_280137_(this.f_96547_, this.title, this.f_96543_ / 2, 8, 0xFFFFFF);
        for (GuiLabel label : new ArrayList<GuiLabel>(this.wrapper.labels.values())) {
            label.m_88315_(graphics, mouseX, mouseY, partialTicks);
        }
        for (GuiTextFieldNop tf : new ArrayList<GuiTextFieldNop>(this.wrapper.textfields.values())) {
            tf.m_87963_(graphics, x, y, partialTicks);
        }
        for (GuiCustomScrollNop scroll : new ArrayList<GuiCustomScrollNop>(this.wrapper.scrolls.values())) {
            scroll.m_88315_(graphics, x, y, partialTicks);
        }
        for (IGui comp : new ArrayList<IGui>(this.wrapper.components)) {
            comp.render(graphics, x, y);
        }
        for (Screen gui : new ArrayList<Screen>(this.wrapper.extra.values())) {
            gui.m_88315_(graphics, x, y, partialTicks);
        }
        super.m_88315_(graphics, x, y, partialTicks);
        if (this.wrapper.subgui != null) {
            matrixStack.m_252880_(0.0f, 0.0f, 60.0f);
            this.wrapper.subgui.m_88315_(graphics, mouseX, mouseY, partialTicks);
            matrixStack.m_252880_(0.0f, 0.0f, -60.0f);
        }
    }

    public Font getFontRenderer() {
        return this.f_96547_;
    }

    public boolean m_7043_() {
        return false;
    }

    public void doubleClicked() {
    }

    public void setScreen(Screen gui) {
        this.f_96541_.m_91152_(gui);
    }

    public void setSubGui(Screen gui) {
        this.wrapper.setSubgui(gui);
        this.m_7856_();
    }

    @Override
    public boolean hasSubGui() {
        return this.wrapper.subgui != null;
    }

    @Override
    public Screen getSubGui() {
        return this.wrapper.getSubGui();
    }

    public void drawNpc(GuiGraphics graphics, LivingEntity entity, int x, int y, float zoomed, int rotation) {
        this.wrapper.drawNpc(graphics, entity, x, y, zoomed, rotation, this.guiLeft, this.guiTop);
    }

    @Override
    public int getWidth() {
        return this.f_96543_;
    }

    @Override
    public int getHeight() {
        return this.f_96544_;
    }

    public void openLink(String link) {
        try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            oclass.getMethod("browse", URI.class).invoke(object, new URI(link));
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    @Override
    public Screen getParent() {
        return this.wrapper.getParent();
    }
}

