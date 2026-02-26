/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.api.wrapper.gui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.function.gui.GuiComponentUpdate;
import noppes.npcs.api.gui.ICustomGui;
import noppes.npcs.api.gui.ISlider;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;

public class CustomGuiSliderWrapper
extends CustomGuiComponentWrapper
implements ISlider {
    private String format = "%s%%";
    private float min = 0.0f;
    private float max = 100.0f;
    private int decimals = 0;
    private float value = 100.0f;
    private GuiComponentUpdate<ISlider> onChange = null;

    public CustomGuiSliderWrapper() {
    }

    public CustomGuiSliderWrapper(int id, String format, int x, int y, int width, int height) {
        this.setID(id);
        if (!format.isEmpty()) {
            this.setFormat(format);
        }
        this.setPos(x, y);
        this.setSize(width, height);
    }

    @Override
    public float getValue() {
        return this.value;
    }

    @Override
    public CustomGuiSliderWrapper setValue(float value) {
        BigDecimal bd = new BigDecimal(value);
        this.value = bd.setScale(this.decimals, RoundingMode.FLOOR).floatValue();
        return this;
    }

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    public CustomGuiSliderWrapper setFormat(String format) {
        this.format = format;
        return this;
    }

    @Override
    public float getMin() {
        return this.min;
    }

    @Override
    public CustomGuiSliderWrapper setMin(float min) {
        this.min = min;
        return this;
    }

    @Override
    public float getMax() {
        return this.max;
    }

    @Override
    public CustomGuiSliderWrapper setMax(float max) {
        this.max = max;
        return this;
    }

    @Override
    public int getDecimals() {
        return this.decimals;
    }

    @Override
    public CustomGuiSliderWrapper setDecimals(int decimals) {
        if (decimals < 0) {
            throw new CustomNPCsException("Decimals cant be lower then 0", new Object[0]);
        }
        this.decimals = decimals;
        return this;
    }

    @Override
    public int getType() {
        return 8;
    }

    @Override
    public CompoundTag toNBT(CompoundTag compound) {
        super.toNBT(compound);
        compound.m_128359_("format", this.format);
        compound.m_128405_("decimals", this.decimals);
        compound.m_128350_("min", this.min);
        compound.m_128350_("max", this.max);
        compound.m_128350_("value", this.value);
        return compound;
    }

    @Override
    public CustomGuiComponentWrapper fromNBT(CompoundTag compound) {
        super.fromNBT(compound);
        this.setFormat(compound.m_128461_("format"));
        this.setDecimals(compound.m_128451_("decimals"));
        this.setMin(compound.m_128457_("min"));
        this.setMax(compound.m_128457_("max"));
        this.setValue(compound.m_128457_("value"));
        return this;
    }

    @Override
    public CustomGuiSliderWrapper setOnChange(GuiComponentUpdate<ISlider> onChange) {
        this.onChange = onChange;
        return this;
    }

    public final void onChange(ICustomGui gui) {
        if (this.onChange != null) {
            this.onChange.onChange(gui, this);
        }
    }
}

