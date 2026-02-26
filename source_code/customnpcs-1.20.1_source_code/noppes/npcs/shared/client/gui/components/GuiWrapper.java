/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.shared.client.gui.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.client.gui.custom.components.CustomGuiEntityDisplay;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiMenuSideButton;
import noppes.npcs.shared.client.gui.components.GuiMenuTopButton;
import noppes.npcs.shared.client.gui.components.GuiSliderNop;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGui;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiWrapper {
    public Map<Integer, GuiButtonNop> npcbuttons = new ConcurrentHashMap<Integer, GuiButtonNop>();
    public Map<Integer, GuiMenuTopButton> topbuttons = new ConcurrentHashMap<Integer, GuiMenuTopButton>();
    public Map<Integer, GuiMenuSideButton> sidebuttons = new ConcurrentHashMap<Integer, GuiMenuSideButton>();
    public Map<Integer, GuiTextFieldNop> textfields = new ConcurrentHashMap<Integer, GuiTextFieldNop>();
    public Map<Integer, GuiLabel> labels = new ConcurrentHashMap<Integer, GuiLabel>();
    public Map<Integer, GuiCustomScrollNop> scrolls = new ConcurrentHashMap<Integer, GuiCustomScrollNop>();
    public Map<Integer, GuiSliderNop> sliders = new ConcurrentHashMap<Integer, GuiSliderNop>();
    public Map<Integer, Screen> extra = new ConcurrentHashMap<Integer, Screen>();
    public List<IGui> components = new ArrayList<IGui>();
    public Screen parent;
    public Screen gui;
    public Screen subgui;
    public int mouseX;
    public int mouseY;

    public GuiWrapper(Screen gui) {
        this.gui = gui;
    }

    public void init(Minecraft mc, int width, int height) {
        GuiTextFieldNop.unfocus();
        if (this.subgui != null) {
            this.subgui.m_6575_(mc, width, height);
        }
        this.npcbuttons = new ConcurrentHashMap<Integer, GuiButtonNop>();
        this.topbuttons = new ConcurrentHashMap<Integer, GuiMenuTopButton>();
        this.sidebuttons = new ConcurrentHashMap<Integer, GuiMenuSideButton>();
        this.textfields = new ConcurrentHashMap<Integer, GuiTextFieldNop>();
        this.labels = new ConcurrentHashMap<Integer, GuiLabel>();
        this.scrolls = new ConcurrentHashMap<Integer, GuiCustomScrollNop>();
        this.sliders = new ConcurrentHashMap<Integer, GuiSliderNop>();
        this.extra = new ConcurrentHashMap<Integer, Screen>();
        this.components = new ArrayList<IGui>();
    }

    public void tick() {
        if (this.subgui != null) {
            this.subgui.m_86600_();
        } else {
            for (GuiTextFieldNop tf : new ArrayList<GuiTextFieldNop>(this.textfields.values())) {
                if (!tf.enabled) continue;
                tf.m_94120_();
            }
            for (IGui comp : new ArrayList<IGui>(this.components)) {
                comp.tick();
            }
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrolled) {
        if (this.subgui != null) {
            this.subgui.m_6050_(mouseX, mouseY, scrolled);
            return true;
        }
        for (IGui comp : new ArrayList<IGui>(this.components)) {
            if (!(comp instanceof GuiEventListener) || !comp.m_142518_() || !((GuiEventListener)comp).m_6050_(mouseX, mouseY, scrolled)) continue;
            return true;
        }
        for (GuiCustomScrollNop scroll : this.scrolls.values()) {
            if (!scroll.visible || !scroll.m_6050_(mouseX, mouseY, scrolled)) continue;
            return true;
        }
        return false;
    }

    public boolean mouseClicked(double i, double j, int k) {
        if (this.subgui != null) {
            this.subgui.m_6375_(i, j, k);
            return true;
        }
        boolean clickedAnyTF = false;
        for (GuiTextFieldNop tf : new ArrayList<GuiTextFieldNop>(this.textfields.values())) {
            if (!tf.m_6375_(i, j, k)) continue;
            clickedAnyTF = true;
        }
        if (clickedAnyTF) {
            return true;
        }
        for (IGui comp : new ArrayList<IGui>(this.components)) {
            if (!(comp instanceof GuiEventListener) || !((GuiEventListener)comp).m_6375_(i, j, k)) continue;
            return true;
        }
        if (k == 0) {
            for (GuiCustomScrollNop scroll : new ArrayList<GuiCustomScrollNop>(this.scrolls.values())) {
                if (!scroll.m_6375_(i, j, k)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean mouseDragged(double x, double y, int button, double dx, double dy) {
        if (this.subgui != null) {
            this.subgui.m_7979_(x, y, button, dx, dy);
            return true;
        }
        return false;
    }

    public boolean mouseReleased(double x, double y, int button) {
        if (this.subgui != null) {
            this.subgui.m_6348_(x, y, button);
            return true;
        }
        return false;
    }

    public boolean charTyped(char c, int i) {
        if (this.subgui != null) {
            this.subgui.m_5534_(c, i);
            return true;
        }
        for (GuiTextFieldNop tf : new ArrayList<GuiTextFieldNop>(this.textfields.values())) {
            tf.m_5534_(c, i);
        }
        for (GuiCustomScrollNop scroll : new ArrayList<GuiCustomScrollNop>(this.scrolls.values())) {
            scroll.m_5534_(c, i);
        }
        for (IGui comp : new ArrayList<IGui>(this.components)) {
            if (!(comp instanceof GuiEventListener)) continue;
            ((GuiEventListener)comp).m_5534_(c, i);
        }
        return true;
    }

    public boolean keyPressed(int key, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (this.subgui != null) {
            this.subgui.m_7933_(key, p_keyPressed_2_, p_keyPressed_3_);
            return true;
        }
        boolean active = GuiTextFieldNop.isAnyActive();
        for (IGui gui : this.components) {
            if (!gui.m_142518_()) continue;
            active = true;
            break;
        }
        if (this.gui.m_6913_() && (key == 256 || !active && Minecraft.m_91087_().f_91066_.f_92092_.getKey().m_84873_() == key)) {
            this.gui.m_7379_();
            return true;
        }
        for (GuiTextFieldNop tf : this.textfields.values()) {
            tf.m_7933_(key, p_keyPressed_2_, p_keyPressed_3_);
        }
        for (GuiCustomScrollNop scroll : new ArrayList<GuiCustomScrollNop>(this.scrolls.values())) {
            scroll.m_7933_(key, p_keyPressed_2_, p_keyPressed_3_);
        }
        for (IGui comp : new ArrayList<IGui>(this.components)) {
            if (!(comp instanceof GuiEventListener) || !((GuiEventListener)comp).m_7933_(key, p_keyPressed_2_, p_keyPressed_3_)) continue;
            return true;
        }
        return active;
    }

    public void drawNpc(GuiGraphics graphics, LivingEntity entity, int x, int y, float zoomed, int rotation, int guiLeft, int guiTop) {
        CustomGuiEntityDisplay.drawEntity(graphics, (Entity)entity, x, y, zoomed, rotation, this.mouseX, this.mouseY, guiLeft, guiTop);
    }

    public void changeFocus(GuiEventListener old, GuiEventListener gui) {
        if (old instanceof GuiSliderNop && gui != old) {
            ((GuiSliderNop)old).m_7691_(0.0, 0.0);
        }
    }

    public void setSubgui(Screen subgui) {
        this.gui.m_7522_(null);
        this.subgui = subgui;
        subgui.m_6575_(Minecraft.m_91087_(), this.gui.f_96543_, this.gui.f_96544_);
        if (subgui instanceof IGuiInterface) {
            ((IGuiInterface)subgui).getWrapper().parent = this.gui;
        }
    }

    public Screen getSubGui() {
        if (this.subgui instanceof IGuiInterface && ((IGuiInterface)this.subgui).hasSubGui()) {
            return ((IGuiInterface)this.subgui).getSubGui();
        }
        return this.subgui;
    }

    public Screen getParent() {
        if (this.parent != null) {
            return ((IGuiInterface)this.parent).getParent();
        }
        return this.gui;
    }

    public void close() {
        GuiTextFieldNop.unfocus();
        ((IGuiInterface)this.gui).save();
        if (this.parent != null) {
            if (this.parent instanceof IGuiInterface) {
                this.parent.m_7522_(null);
                ((IGuiInterface)this.parent).getWrapper().subgui = null;
                ((IGuiInterface)this.parent).subGuiClosed(this.gui);
                ((IGuiInterface)this.parent).initGui();
            } else {
                this.gui.m_7379_();
            }
        } else {
            Minecraft minecraft = Minecraft.m_91087_();
            minecraft.m_91152_(this.gui);
            minecraft.f_91067_.m_91601_();
        }
    }
}

