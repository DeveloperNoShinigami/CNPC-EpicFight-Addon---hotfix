/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import java.util.UUID;

public interface ICustomGuiComponent {
    public int getID();

    public ICustomGuiComponent setID(int var1);

    public UUID getUniqueID();

    public int getPosX();

    public int getPosY();

    public ICustomGuiComponent setPos(int var1, int var2);

    public int getWidth();

    public int getHeight();

    public ICustomGuiComponent setSize(int var1, int var2);

    public boolean hasHoverText();

    public String[] getHoverText();

    public ICustomGuiComponent setHoverText(String var1);

    public ICustomGuiComponent setHoverText(String[] var1);

    public boolean getEnabled();

    public ICustomGuiComponent setEnabled(boolean var1);

    public boolean getVisible();

    public ICustomGuiComponent setVisible(boolean var1);

    public int getType();
}

