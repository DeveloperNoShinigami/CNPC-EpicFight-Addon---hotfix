/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.function.gui;

import noppes.npcs.api.gui.ICustomGui;
import noppes.npcs.api.gui.ICustomGuiComponent;

@FunctionalInterface
public interface GuiComponentUpdate<T extends ICustomGuiComponent> {
    public void onChange(ICustomGui var1, T var2);
}

