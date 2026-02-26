/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.function.gui.GuiComponentUpdate;
import noppes.npcs.api.gui.ICustomGuiComponent;

public interface ITextField
extends ICustomGuiComponent {
    public String getText();

    public ITextField setText(String var1);

    public int getColor();

    public ITextField setColor(int var1);

    public ITextField setOnChange(GuiComponentUpdate<ITextField> var1);

    public ITextField setOnFocusLost(GuiComponentUpdate<ITextField> var1);

    public ITextField setFocused(boolean var1);

    public boolean getFocused();

    public ITextField setCharacterType(int var1);

    public int getCharacterType();

    public int getInteger();

    public ITextField setInteger(int var1);

    public float getFloat();

    public ITextField setFloat(float var1);

    public ITextField setMinMax(int var1, int var2);
}

