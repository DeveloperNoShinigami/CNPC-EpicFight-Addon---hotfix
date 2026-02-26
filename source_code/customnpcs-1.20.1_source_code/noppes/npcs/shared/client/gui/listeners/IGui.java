/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 */
package noppes.npcs.shared.client.gui.listeners;

import net.minecraft.client.gui.GuiGraphics;

public interface IGui {
    public int getID();

    public void render(GuiGraphics var1, int var2, int var3);

    public void tick();

    public boolean m_142518_();
}

