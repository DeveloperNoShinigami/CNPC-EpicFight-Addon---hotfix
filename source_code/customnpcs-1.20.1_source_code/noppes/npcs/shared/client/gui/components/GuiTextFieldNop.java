/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.shared.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiTextFieldNop
extends EditBox {
    public boolean enabled = true;
    public boolean inMenu = true;
    public boolean numbersOnly = false;
    public boolean floatsOnly = false;
    public ITextfieldListener listener;
    public int id;
    public int min = 0;
    public int max = Integer.MAX_VALUE;
    public int def = 0;
    public float minF = 0.0f;
    public float maxF = Float.MAX_VALUE;
    public float defF = 0.0f;
    private static GuiTextFieldNop activeTextfield = null;
    private String initialValue;
    private final int[] allowedSpecialChars = new int[]{14, 211, 203, 205};

    public GuiTextFieldNop(int id, Screen parent, int i, int j, int k, int l, String s) {
        this(id, parent, i, j, k, l, (Component)Component.m_237115_((String)(s != null ? s : "")));
    }

    public GuiTextFieldNop(int id, Screen parent, int i, int j, int k, int l, Component s) {
        super(Minecraft.m_91087_().f_91062_, i, j, k, l, s);
        this.m_94199_(500);
        if (!s.getString().isEmpty()) {
            this.initialValue = s.getString();
            this.m_94144_(s.getString());
        }
        this.id = id;
        if (parent instanceof ITextfieldListener) {
            this.listener = (ITextfieldListener)parent;
        }
    }

    public static boolean isAnyActive() {
        return activeTextfield != null;
    }

    public static GuiTextFieldNop getActive() {
        return activeTextfield;
    }

    private boolean charAllowed(char c, int i) {
        if (!this.numbersOnly || Character.isDigit(c)) {
            return true;
        }
        for (int j : this.allowedSpecialChars) {
            if (j != i) continue;
            return true;
        }
        return false;
    }

    public boolean m_5534_(char c, int i) {
        if (!this.charAllowed(c, i)) {
            return false;
        }
        return super.m_5534_(c, i);
    }

    public boolean isEmpty() {
        return this.m_94155_().trim().length() == 0;
    }

    public int getInteger() {
        return Integer.parseInt(this.m_94155_());
    }

    public boolean isInteger() {
        try {
            Integer.parseInt(this.m_94155_());
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFloat() {
        try {
            Float.parseFloat(this.m_94155_());
            return true;
        }
        catch (NumberFormatException var2) {
            return false;
        }
    }

    public float getFloat() {
        return Float.parseFloat(this.m_94155_());
    }

    public boolean m_6375_(double i, double j, int k) {
        if (!this.enabled) {
            return false;
        }
        boolean wasFocused = this.m_93696_();
        boolean flag = i >= (double)this.m_252754_() && i < (double)(this.m_252754_() + this.f_93618_) && j >= (double)this.m_252907_() && j < (double)(this.m_252907_() + this.f_93619_);
        this.m_93692_(flag);
        boolean clicked = super.m_6375_(i, j, k);
        if (!wasFocused && this.m_93696_()) {
            GuiTextFieldNop.unfocus();
            activeTextfield = this;
        }
        if (wasFocused && !this.m_93696_()) {
            this.unFocused();
        }
        return clicked;
    }

    public void unFocused() {
        if (this.numbersOnly) {
            if (this.isEmpty() || !this.isInteger()) {
                this.m_94144_("" + this.def);
            } else if (this.getInteger() < this.min) {
                this.m_94144_("" + this.min);
            } else if (this.getInteger() > this.max) {
                this.m_94144_("" + this.max);
            }
        }
        if (this.floatsOnly) {
            if (this.isEmpty() || !this.isFloat()) {
                this.m_94144_("" + this.defF);
            } else if (this.getFloat() < this.minF) {
                this.m_94144_("" + this.minF);
            } else if (this.getFloat() > this.maxF) {
                this.m_94144_("" + this.maxF);
            }
        }
        if (this.listener != null) {
            this.listener.unFocused(this);
        }
        if (this == activeTextfield) {
            activeTextfield = null;
        }
    }

    public int getTextColor() {
        if (this.numbersOnly || this.floatsOnly) {
            if (this.numbersOnly && (!this.isInteger() || this.getInteger() < this.min || this.getInteger() > this.max)) {
                return 16515909;
            }
            if (this.floatsOnly && (!this.isFloat() || this.getFloat() < this.minF || this.getFloat() > this.maxF)) {
                return 16515909;
            }
        }
        return 0xE0E0E0;
    }

    public void m_87963_(GuiGraphics graphics, int x, int y, float f) {
        if (this.enabled) {
            this.m_94202_(this.getTextColor());
            super.m_87963_(graphics, y, x, f);
        }
    }

    public GuiTextFieldNop setMinMaxDefault(int i, int j, int k) {
        this.min = i;
        this.max = j;
        this.def = k;
        return this;
    }

    public GuiTextFieldNop setMinMaxDefault(float i, float j, float k) {
        this.minF = i;
        this.maxF = j;
        this.defF = k;
        return this;
    }

    public static void unfocus() {
        GuiTextFieldNop field = activeTextfield;
        activeTextfield = null;
        if (field != null) {
            field.unFocused();
        }
    }

    public GuiTextFieldNop setNumbersOnly() {
        this.numbersOnly = true;
        return this;
    }

    public GuiTextFieldNop setFloatsOnly() {
        this.floatsOnly = true;
        return this;
    }
}

