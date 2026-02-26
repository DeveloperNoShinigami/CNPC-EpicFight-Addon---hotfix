/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.client.gui.custom.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiEntityDisplayWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiItemRendererWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.components.CustomGuiEntityDisplay;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;

public class CustomGuiItemRenderer
extends AbstractWidget
implements IGuiComponent {
    private GuiCustom parent;
    public CustomGuiItemRendererWrapper component;
    private ItemStack stack;
    public int id;
    Minecraft minecraft;

    public CustomGuiItemRenderer(GuiCustom parent, CustomGuiItemRendererWrapper component) {
        super(component.getPosX(), component.getPosY(), component.getWidth(), component.getHeight(), (Component)Component.m_237119_());
        this.component = component;
        this.parent = parent;
        this.minecraft = Minecraft.m_91087_();
        this.init();
    }

    public void init() {
        this.id = this.component.getID();
        this.m_252865_(this.component.getPosX());
        this.m_253211_(this.component.getPosY());
        this.m_93674_(this.component.getWidth());
        this.setHeight(this.component.getHeight());
        this.stack = this.component.hasStack() ? this.component.getStack().getMCItemStack() : ItemStack.f_41583_;
        this.f_93623_ = true;
        this.f_93624_ = true;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.f_93624_) {
            boolean hovered;
            if (!NoppesUtilServer.IsItemStackNull(this.stack)) {
                double scale = this.component.getScale();
                graphics.m_280168_().m_85836_();
                graphics.m_280168_().m_85841_((float)scale, (float)scale, 1.0f);
                graphics.m_280168_().m_252880_(0.0f, 0.0f, (float)this.id * 0.01f);
                graphics.m_280480_(this.stack, (int)((double)this.m_252754_() / scale), (int)((double)this.m_252907_() / scale));
                graphics.m_280370_(this.minecraft.f_91062_, this.stack, (int)((double)this.m_252754_() / scale), (int)((double)this.m_252907_() / scale));
                graphics.m_280168_().m_85849_();
            }
            boolean bl = hovered = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
            if (hovered && this.component.hasHoverText()) {
                this.parent.hoverText = this.component.getHoverTextList();
            }
        }
    }

    protected int getYImage(boolean p_getYImage_1_) {
        return 0;
    }

    protected void m_87963_(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {
    }

    public boolean m_7979_(double mouseX, double mouseY, int mouseButton, double dx, double dy) {
        return true;
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
    }

    public static CustomGuiEntityDisplay fromComponent(GuiCustom parent, CustomGuiEntityDisplayWrapper component) {
        CustomGuiEntityDisplay btn = new CustomGuiEntityDisplay(parent, component);
        return btn;
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }

    public void m_7435_(SoundManager p_93665_) {
    }
}

