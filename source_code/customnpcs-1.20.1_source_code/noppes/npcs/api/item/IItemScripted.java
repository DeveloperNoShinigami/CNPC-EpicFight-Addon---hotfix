/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.item;

import noppes.npcs.api.item.IItemStack;

public interface IItemScripted
extends IItemStack {
    public boolean hasTexture(int var1);

    public String getTexture(int var1);

    public String getTexture();

    public void setTexture(int var1, String var2);

    public void setTexture(String var1);

    public void setMaxStackSize(int var1);

    public double getDurabilityValue();

    public void setDurabilityValue(float var1);

    public boolean getDurabilityShow();

    public void setDurabilityShow(boolean var1);

    public int getDurabilityColor();

    public void setDurabilityColor(int var1);

    public int getColor();

    public void setColor(int var1);
}

