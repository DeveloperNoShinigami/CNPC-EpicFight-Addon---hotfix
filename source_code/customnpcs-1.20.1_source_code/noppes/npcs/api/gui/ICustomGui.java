/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.gui.IComponentsScrollableWrapper;
import noppes.npcs.api.gui.IComponentsWrapper;
import noppes.npcs.api.gui.ICustomGuiComponent;

public interface ICustomGui
extends IComponentsWrapper {
    public int getID();

    public int getWidth();

    public int getHeight();

    public void setSize(int var1, int var2);

    public void setDoesPauseGame(boolean var1);

    public void setClosesOnEsc(boolean var1);

    public void setBackgroundTexture(String var1);

    public void update();

    public void update(ICustomGuiComponent var1);

    public IComponentsScrollableWrapper getScrollingPanel();

    public void openSubGui(ICustomGui var1);

    public ICustomGui getSubGui();

    public boolean hasSubGui();

    public ICustomGui closeSubGui();

    public void close();

    public ICustomGui getParentGui();

    public ICustomGui getRootGui();

    public ICustomGui getActiveGui();

    public IPlayer getPlayer();
}

