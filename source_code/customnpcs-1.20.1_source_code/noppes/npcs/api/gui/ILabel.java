/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.gui.ICustomGuiComponent;

public interface ILabel
extends ICustomGuiComponent {
    public String getText();

    public ILabel setText(String var1);

    public int getColor();

    public ILabel setColor(int var1);

    public float getScale();

    public ILabel setScale(float var1);

    public boolean getCentered();

    public ILabel setCentered(boolean var1);
}

