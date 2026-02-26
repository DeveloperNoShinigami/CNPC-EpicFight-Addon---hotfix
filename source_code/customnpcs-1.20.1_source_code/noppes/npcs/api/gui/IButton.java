/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.function.gui.GuiComponentClicked;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.gui.ITexturedRect;
import noppes.npcs.api.item.IItemStack;

public interface IButton
extends ICustomGuiComponent {
    public String getLabel();

    public IButton setLabel(String var1);

    public ITexturedRect getTextureRect();

    public void setTextureRect(ITexturedRect var1);

    @Deprecated
    public String getTexture();

    @Deprecated
    public boolean hasTexture();

    @Deprecated
    public IButton setTexture(String var1);

    @Deprecated
    public int getTextureX();

    @Deprecated
    public int getTextureY();

    @Deprecated
    public IButton setTextureOffset(int var1, int var2);

    public int getTextureHoverOffset();

    public IButton setTextureHoverOffset(int var1);

    public IItemStack getDisplayItem();

    public IButton setDisplayItem(IItemStack var1);

    public IButton setOnPress(GuiComponentClicked<IButton> var1);
}

