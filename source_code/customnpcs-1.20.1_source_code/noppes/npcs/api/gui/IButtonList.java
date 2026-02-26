/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.gui.IButton;
import noppes.npcs.api.gui.ITexturedRect;

public interface IButtonList
extends IButton {
    public IButtonList setValues(String ... var1);

    public String[] getValues();

    public IButtonList setSelected(int var1);

    public int getSelected();

    public ITexturedRect getLeftTexture();

    public ITexturedRect getRightTexture();
}

