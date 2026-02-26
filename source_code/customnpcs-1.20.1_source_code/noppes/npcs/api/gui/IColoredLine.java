/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.gui.ICustomGuiComponent;

public interface IColoredLine
extends ICustomGuiComponent {
    public int getColor();

    public IColoredLine setColor(int var1);

    public int getXEnd();

    public int getYEnd();

    public IColoredLine setEnd(int var1, int var2);

    public float getThickness();

    public IColoredLine setThickness(float var1);
}

