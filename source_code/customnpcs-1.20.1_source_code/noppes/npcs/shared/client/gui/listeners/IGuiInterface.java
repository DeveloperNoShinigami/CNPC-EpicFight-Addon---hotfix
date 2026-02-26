/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 */
package noppes.npcs.shared.client.gui.listeners;

import net.minecraft.client.gui.screens.Screen;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiWrapper;

public interface IGuiInterface {
    public void buttonEvent(GuiButtonNop var1);

    public void save();

    public boolean hasSubGui();

    public Screen getSubGui();

    public int getWidth();

    public int getHeight();

    public Screen getParent();

    public void elementClicked();

    public void subGuiClosed(Screen var1);

    public GuiWrapper getWrapper();

    public void initGui();
}

