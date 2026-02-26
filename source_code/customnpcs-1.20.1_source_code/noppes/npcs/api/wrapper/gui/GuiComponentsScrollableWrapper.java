/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.api.wrapper.gui;

import net.minecraft.nbt.CompoundTag;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.gui.IComponentsScrollableWrapper;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.GuiComponentsWrapper;

public class GuiComponentsScrollableWrapper
extends GuiComponentsWrapper
implements IComponentsScrollableWrapper {
    private boolean enabled = false;
    public int x;
    public int y;
    public int width;
    public int height;
    public int scrollAmount = 0;
    public GuiComponentsWrapper parent;

    public GuiComponentsScrollableWrapper(GuiComponentsWrapper parent, IPlayer player) {
        super(player);
        this.parent = parent;
    }

    @Override
    public GuiComponentsScrollableWrapper init(int x, int y, int width, int height) {
        this.enabled = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public CompoundTag getComponentNbt() {
        CompoundTag comp = super.getComponentNbt();
        comp.m_128379_("enabled", this.enabled);
        comp.m_128405_("x", this.x);
        comp.m_128405_("y", this.y);
        comp.m_128405_("width", this.width);
        comp.m_128405_("height", this.height);
        return comp;
    }

    @Override
    public void setComponentNbt(CompoundTag comp) {
        super.setComponentNbt(comp);
        this.enabled = comp.m_128471_("enabled");
        this.x = comp.m_128451_("x");
        this.y = comp.m_128451_("y");
        this.width = comp.m_128451_("width");
        this.height = comp.m_128451_("height");
    }

    public boolean isVisible(ICustomGuiComponent component) {
        return component.getPosY() >= this.scrollAmount && component.getPosY() + component.getHeight() <= this.height + this.scrollAmount;
    }
}

