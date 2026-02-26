/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 */
package noppes.npcs.client.gui.custom.interfaces;

import net.minecraft.client.gui.GuiGraphics;
import noppes.npcs.api.gui.ICustomGuiComponent;

public interface IGuiComponent {
    public int getID();

    public void onRender(GuiGraphics var1, int var2, int var3, float var4);

    default public void onRenderPost(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    }

    public void m_7856_();

    public ICustomGuiComponent component();
}

