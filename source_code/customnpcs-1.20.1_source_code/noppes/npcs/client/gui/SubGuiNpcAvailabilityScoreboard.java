/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 */
package noppes.npcs.client.gui;

import net.minecraft.client.gui.screens.Screen;
import noppes.npcs.constants.EnumAvailabilityScoreboard;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;
import noppes.npcs.shared.client.util.NoppesStringUtils;

public class SubGuiNpcAvailabilityScoreboard
extends GuiBasic
implements ITextfieldListener {
    private Availability availabitily;
    private boolean selectFaction = false;
    private int slot = 0;

    public SubGuiNpcAvailabilityScoreboard(Availability availabitily) {
        this.availabitily = availabitily;
        this.setBackground("menubg.png");
        this.imageWidth = 316;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(1, "availability.available", this.guiLeft, this.guiTop + 4, this.imageWidth, 0));
        int y = this.guiTop + 12;
        this.addTextField(new GuiTextFieldNop(10, (Screen)this, this.guiLeft + 4, y, 140, 20, this.availabitily.scoreboardObjective));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 0, this.guiLeft + 148, y, 90, 20, new String[]{"availability.smaller", "availability.equals", "availability.bigger"}, this.availabitily.scoreboardType.ordinal()));
        this.addTextField(new GuiTextFieldNop(20, (Screen)this, this.guiLeft + 244, y, 60, 20, "" + this.availabitily.scoreboardValue));
        this.getTextField((int)20).numbersOnly = true;
        this.addTextField(new GuiTextFieldNop(11, (Screen)this, this.guiLeft + 4, y += 23, 140, 20, this.availabitily.scoreboard2Objective));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 1, this.guiLeft + 148, y, 90, 20, new String[]{"availability.smaller", "availability.equals", "availability.bigger"}, this.availabitily.scoreboard2Type.ordinal()));
        this.addTextField(new GuiTextFieldNop(21, (Screen)this, this.guiLeft + 244, y, 60, 20, "" + this.availabitily.scoreboard2Value));
        this.getTextField((int)21).numbersOnly = true;
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 82, this.guiTop + 192, 98, 20, "gui.done"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (guibutton.id == 0) {
            this.availabitily.scoreboardType = EnumAvailabilityScoreboard.values()[button.getValue()];
        }
        if (guibutton.id == 1) {
            this.availabitily.scoreboard2Type = EnumAvailabilityScoreboard.values()[button.getValue()];
        }
        if (guibutton.id == 66) {
            this.close();
        }
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 10) {
            this.availabitily.scoreboardObjective = textfield.m_94155_();
        }
        if (textfield.id == 11) {
            this.availabitily.scoreboard2Objective = textfield.m_94155_();
        }
        if (textfield.id == 20) {
            this.availabitily.scoreboardValue = NoppesStringUtils.parseInt(textfield.m_94155_(), 0);
        }
        if (textfield.id == 21) {
            this.availabitily.scoreboard2Value = NoppesStringUtils.parseInt(textfield.m_94155_(), 0);
        }
    }
}

