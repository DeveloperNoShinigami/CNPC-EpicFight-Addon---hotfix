/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.api.wrapper.gui;

import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.function.gui.GuiComponentUpdate;
import noppes.npcs.api.gui.ICustomGui;
import noppes.npcs.api.gui.ITextField;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;

public class CustomGuiTextFieldWrapper
extends CustomGuiComponentWrapper
implements ITextField {
    private int color = 0xE0E0E0;
    private int type = 0;
    private String text = "";
    private boolean focused = false;
    private GuiComponentUpdate<ITextField> onChange = null;
    private GuiComponentUpdate<ITextField> onFocusLost = null;
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    public CustomGuiTextFieldWrapper() {
    }

    public CustomGuiTextFieldWrapper(int id, int x, int y, int width, int height) {
        this.setID(id);
        this.setPos(x, y);
        this.setSize(width, height);
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public CustomGuiTextFieldWrapper setText(String text) {
        String prevText = this.text;
        this.text = Objects.requireNonNull(text, "");
        if (!(this.text.isEmpty() || this.getCharacterType() != 1 && this.getCharacterType() != 2)) {
            try {
                this.setInteger(this.getInteger());
            }
            catch (NumberFormatException e) {
                this.text = prevText;
            }
        }
        return this;
    }

    @Override
    public int getInteger() {
        if (this.type == 0) {
            throw new CustomNPCsException("Character Type 0 doesnt convert to integer", new Object[0]);
        }
        if (this.text.isEmpty()) {
            return Math.max(this.min, 0);
        }
        if (this.type == 1) {
            return Integer.parseInt(this.text);
        }
        return Integer.parseInt(this.text, 16);
    }

    @Override
    public CustomGuiTextFieldWrapper setInteger(int i) {
        if (this.type == 0) {
            throw new CustomNPCsException("Character Type 0 doesnt support setInteger", new Object[0]);
        }
        i = Math.max(this.min, i);
        i = Math.min(this.max, i);
        if (this.type == 1 || this.type == 3) {
            this.text = "" + i;
        }
        if (this.type == 2) {
            this.text = String.format("%01x", i);
        }
        return this;
    }

    @Override
    public float getFloat() {
        if (this.type == 0) {
            throw new CustomNPCsException("Character Type 0 doesnt convert to float", new Object[0]);
        }
        if (this.text.isEmpty()) {
            return Math.max(this.min, 0);
        }
        if (this.type == 1) {
            return Integer.parseInt(this.text);
        }
        if (this.type == 2) {
            return Integer.parseInt(this.text, 16);
        }
        return Float.parseFloat(this.text);
    }

    @Override
    public CustomGuiTextFieldWrapper setFloat(float f) {
        if (this.type == 0 || this.type == 2) {
            throw new CustomNPCsException("Character Type 0 doesnt support setFloat", new Object[0]);
        }
        f = Math.max((float)this.min, f);
        f = Math.min((float)this.max, f);
        if (this.type == 1) {
            this.text = "" + f;
        }
        return this;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public CustomGuiTextFieldWrapper setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public CustomGuiTextFieldWrapper setFocused(boolean bo) {
        this.focused = bo;
        return this;
    }

    @Override
    public boolean getFocused() {
        return this.focused;
    }

    @Override
    public CustomGuiTextFieldWrapper setCharacterType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public int getCharacterType() {
        return this.type;
    }

    @Override
    public CustomGuiTextFieldWrapper setMinMax(int min, int max) {
        if (this.type == 0) {
            throw new CustomNPCsException("Character Type 0 doesnt support setInteger", new Object[0]);
        }
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public CompoundTag toNBT(CompoundTag nbt) {
        super.toNBT(nbt);
        nbt.m_128359_("default", this.text);
        nbt.m_128379_("focused", this.focused);
        nbt.m_128405_("color", this.color);
        nbt.m_128405_("character_type", this.type);
        nbt.m_128405_("min", this.min);
        nbt.m_128405_("max", this.max);
        return nbt;
    }

    @Override
    public CustomGuiComponentWrapper fromNBT(CompoundTag nbt) {
        super.fromNBT(nbt);
        this.setText(nbt.m_128461_("default"));
        this.setFocused(nbt.m_128471_("focused"));
        this.setColor(nbt.m_128451_("color"));
        this.setCharacterType(nbt.m_128451_("character_type"));
        this.min = nbt.m_128451_("min");
        this.max = nbt.m_128451_("max");
        return this;
    }

    @Override
    public CustomGuiTextFieldWrapper setOnChange(GuiComponentUpdate<ITextField> onChange) {
        this.onChange = onChange;
        return this;
    }

    @Override
    public CustomGuiTextFieldWrapper setOnFocusLost(GuiComponentUpdate<ITextField> onFocusChange) {
        this.onFocusLost = onFocusChange;
        return this;
    }

    public final void onChange(ICustomGui gui) {
        if (this.onChange != null) {
            this.onChange.onChange(gui, this);
        }
    }

    public final void onFocusLost(ICustomGui gui) {
        if (this.onFocusLost != null) {
            this.onFocusLost.onChange(gui, this);
        }
    }
}

