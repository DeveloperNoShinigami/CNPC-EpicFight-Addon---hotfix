/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.item.IItemStack;

public interface IItemRenderer
extends ICustomGuiComponent {
    public boolean hasStack();

    public IItemStack getStack();

    public IItemRenderer setStack(IItemStack var1);

    @Override
    public int getWidth();

    @Override
    public int getHeight();

    public IItemRenderer setHoverBox(int var1, int var2);

    public float getScale();

    public IItemRenderer setScale(float var1);
}

