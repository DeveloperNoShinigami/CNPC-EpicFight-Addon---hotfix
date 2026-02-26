/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.function.gui.GuiComponentUpdate;
import noppes.npcs.api.gui.ICustomGuiComponent;

public interface ISlider
extends ICustomGuiComponent {
    public float getValue();

    public ISlider setValue(float var1);

    public String getFormat();

    public ISlider setFormat(String var1);

    public float getMin();

    public ISlider setMin(float var1);

    public float getMax();

    public ISlider setMax(float var1);

    public int getDecimals();

    public ISlider setDecimals(int var1);

    public ISlider setOnChange(GuiComponentUpdate<ISlider> var1);
}

