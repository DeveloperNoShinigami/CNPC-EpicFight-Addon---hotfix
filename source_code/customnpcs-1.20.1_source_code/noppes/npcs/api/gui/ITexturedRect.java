/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.gui.ICustomGuiComponent;

public interface ITexturedRect
extends ICustomGuiComponent {
    public String getTexture();

    public ITexturedRect setTexture(String var1);

    public float getScale();

    public ITexturedRect setScale(float var1);

    public int getTextureX();

    public int getTextureY();

    public ITexturedRect setTextureOffset(int var1, int var2);

    public ITexturedRect setTextureMaxSize(int var1, int var2);

    public ITexturedRect setRepeatingTexture(int var1, int var2, int var3);

    public int getTextureMaxX();

    public int getTextureMaxY();
}

