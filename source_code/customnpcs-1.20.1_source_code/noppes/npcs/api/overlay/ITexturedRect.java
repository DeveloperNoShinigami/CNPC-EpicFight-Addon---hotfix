/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.overlay;

import noppes.npcs.api.overlay.IOverlayComponent;

public interface ITexturedRect
extends IOverlayComponent {
    public String getTexture();

    public ITexturedRect setTexture(String var1);

    public int getWidth();

    public ITexturedRect setWidth(int var1);

    public int getHeight();

    public ITexturedRect setHeight(int var1);

    public float[] getUV();

    public ITexturedRect setUV(float var1, float var2, float var3, float var4);

    public ITexturedRect setRGB(float var1, float var2, float var3, float var4);

    public float[] getRGB();

    public int getTextureX();

    public int getTextureY();

    public int getTextureMaxX();

    public int getTextureMaxY();

    public ITexturedRect setTextureOffset(int var1, int var2);

    public ITexturedRect setTextureMaxSize(int var1, int var2);
}

