/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.gui.IButton;

public interface ITexturedButton
extends IButton {
    @Override
    public String getTexture();

    @Override
    public ITexturedButton setTexture(String var1);

    @Override
    public int getTextureX();

    @Override
    public int getTextureY();

    @Override
    public ITexturedButton setTextureOffset(int var1, int var2);
}

