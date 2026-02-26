/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.shared.client.gui.components;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.client.gui.util.GuiTooltipUtils;
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

public class GuiBasicContainer<T extends AbstractContainerMenu>
extends AbstractContainerScreen<T>
implements IGuiInterface {
    public boolean drawDefaultBackground = true;
    public int guiLeft;
    public int guiTop;
    public LocalPlayer player;
    public GuiWrapper wrapper = new GuiWrapper((Screen)this);
    public String title;
    public boolean closeOnEsc = true;
    public int mouseX;
    public int mouseY;

    public GuiBasicContainer(T cont, Inventory inv, Component titleIn) {
        super(cont, inv, titleIn);
        this.player = Minecraft.m_91087_().f_91074_;
        this.title = "";
        this.f_96541_ = Minecraft.m_91087_();
        this.f_96547_ = this.f_96541_.f_91062_;
    }

    public boolean m_6913_() {
        return this.closeOnEsc;
    }

    public void m_7856_() {
        super.m_7856_();
        this.m_7522_(null);
        this.guiLeft = (this.f_96543_ - this.f_97726_) / 2;
        this.guiTop = (this.f_96544_ - this.f_97727_) / 2;
        this.f_169369_.clear();
        this.m_6702_().clear();
        this.wrapper.init(this.f_96541_, this.f_96543_, this.f_96544_);
    }

    public ResourceLocation getResource(String texture) {
        return new ResourceLocation("customnpcs", "textures/gui/" + texture);
    }

    public void m_181908_() {
        this.wrapper.tick();
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
        if (this.m_7222_() != null && this.m_7282_() && button == 0) {
            this.m_7222_().m_7979_(x, y, button, dx, dy);
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

    @Override
    public void elementClicked() {
        if (this.wrapper.subgui != null) {
            ((IGuiInterface)this.wrapper.subgui).elementClicked();
        }
    }

    @Override
    public void subGuiClosed(Screen subgui) {
    }

    @Override
    public GuiWrapper getWrapper() {
        return this.wrapper;
    }

    @Override
    public void initGui() {
        this.m_7856_();
    }

    public boolean isInventoryKey(int i) {
        return this.f_96541_.f_91066_.f_92092_.getKey().m_84873_() == i;
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

    public void buttonEvent(Button guibutton) {
    }

    public void close() {
        this.save();
        this.player.m_6915_();
        this.setScreen(null);
        this.f_96541_.f_91067_.m_91601_();
    }

    public void m_7379_() {
        this.close();
        GuiTextFieldNop.unfocus();
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

    protected void m_280003_(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
    }

    protected void m_7286_(GuiGraphics graphics, float partialTicks, int x, int y) {
    }

    @Override
    public void buttonEvent(GuiButtonNop button) {
    }

    @Override
    public void save() {
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.wrapper.mouseX = mouseX;
        this.wrapper.mouseY = mouseY;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        ArrayList slots = new ArrayList(this.f_97732_.f_38839_);
        if (this.wrapper.subgui != null) {
            this.f_97732_.f_38839_.clear();
        }
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        graphics.m_280137_(this.getFontRenderer(), I18n.m_118938_((String)this.title, (Object[])new Object[0]), this.f_96543_ / 2, this.guiTop - 8, 0xFFFFFF);
        for (GuiLabel label : new ArrayList<GuiLabel>(this.wrapper.labels.values())) {
            label.m_88315_(graphics, mouseX, mouseY, partialTicks);
        }
        for (GuiTextFieldNop tf : new ArrayList<GuiTextFieldNop>(this.wrapper.textfields.values())) {
            tf.m_87963_(graphics, mouseX, mouseY, partialTicks);
        }
        for (GuiCustomScrollNop scroll : new ArrayList<GuiCustomScrollNop>(this.wrapper.scrolls.values())) {
            scroll.m_88315_(graphics, mouseX, mouseY, partialTicks);
        }
        for (IGui comp : new ArrayList<IGui>(this.wrapper.components)) {
            comp.render(graphics, mouseX, mouseY);
            for (Screen gui : new ArrayList<Screen>(this.wrapper.extra.values())) {
                gui.m_88315_(graphics, mouseX, mouseY, partialTicks);
            }
        }
        if (this.wrapper.subgui != null) {
            this.f_97732_.f_38839_.addAll(slots);
            graphics.m_280168_().m_85836_();
            graphics.m_280168_().m_252880_(0.0f, 0.0f, 100.0f);
            this.wrapper.subgui.m_88315_(graphics, mouseX, mouseY, partialTicks);
            graphics.m_280168_().m_85849_();
        } else {
            this.m_280072_(graphics, mouseX, mouseY);
        }
    }

    public void m_280072_(GuiGraphics p_283594_, int p_282171_, int p_281909_) {
        if (this.f_97732_.m_142621_().m_41619_() && this.f_97734_ != null && this.f_97734_.m_6657_()) {
            ItemStack itemstack = this.f_97734_.m_7993_();
            GuiTooltipUtils.renderTooltip(p_283594_, this.f_96547_, this.m_280553_(itemstack), itemstack.m_150921_(), itemstack, p_282171_, p_281909_);
        }
    }

    public void m_280273_(GuiGraphics graphics) {
        if (this.drawDefaultBackground && this.wrapper.subgui == null) {
            super.m_280273_(graphics);
        }
    }

    public Font getFontRenderer() {
        return this.f_96547_;
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

    @Override
    public int getWidth() {
        return this.f_96543_;
    }

    @Override
    public int getHeight() {
        return this.f_96544_;
    }

    @Override
    public Screen getParent() {
        return this.wrapper.getParent();
    }
}

