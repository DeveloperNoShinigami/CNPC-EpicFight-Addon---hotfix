/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 */
package noppes.npcs.client.gui;

import net.minecraft.client.gui.screens.Screen;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class SubGuiNpcCommand
extends GuiBasic
implements ITextfieldListener {
    public String command;

    public SubGuiNpcCommand(String command) {
        this.command = command;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addTextField(new GuiTextFieldNop(4, (Screen)this, this.guiLeft + 4, this.guiTop + 84, 248, 20, this.command));
        this.getTextField(4).m_94199_(Short.MAX_VALUE);
        this.addLabel(new GuiLabel(4, "advMode.command", this.guiLeft + 4, this.guiTop + 110));
        this.addLabel(new GuiLabel(5, "advMode.nearestPlayer", this.guiLeft + 4, this.guiTop + 125));
        this.addLabel(new GuiLabel(6, "advMode.randomPlayer", this.guiLeft + 4, this.guiTop + 140));
        this.addLabel(new GuiLabel(7, "advMode.allPlayers", this.guiLeft + 4, this.guiTop + 155));
        this.addLabel(new GuiLabel(8, "dialog.commandoptionplayer", this.guiLeft + 4, this.guiTop + 170));
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 82, this.guiTop + 190, 98, 20, "gui.done"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 66) {
            this.m_7379_();
        }
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 4) {
            this.command = textfield.m_94155_();
        }
    }
}

