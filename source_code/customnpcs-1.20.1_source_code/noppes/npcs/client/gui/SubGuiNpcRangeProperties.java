/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 */
package noppes.npcs.client.gui;

import net.minecraft.client.gui.screens.Screen;
import noppes.npcs.client.gui.select.GuiSoundSelection;
import noppes.npcs.entity.data.DataRanged;
import noppes.npcs.entity.data.DataStats;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiButtonYesNo;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class SubGuiNpcRangeProperties
extends GuiBasic
implements ITextfieldListener {
    private DataRanged ranged;
    private DataStats stats;
    private GuiSoundSelection gui;
    private int soundSelected = -1;

    public SubGuiNpcRangeProperties(DataStats stats) {
        this.ranged = stats.ranged;
        this.stats = stats;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        int y = this.guiTop + 4;
        this.addTextField(new GuiTextFieldNop(1, (Screen)this, this.guiLeft + 80, y, 50, 18, "" + this.ranged.getAccuracy()));
        this.addLabel(new GuiLabel(1, "stats.accuracy", this.guiLeft + 5, y + 5, "guihint.npcaccuracy"));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(0, 100, 90);
        this.addTextField(new GuiTextFieldNop(8, (Screen)this, this.guiLeft + 200, y, 50, 18, "" + this.ranged.getShotCount()));
        this.addLabel(new GuiLabel(8, "stats.shotcount", this.guiLeft + 135, y + 5, "guihint.npcshotcount"));
        this.getTextField((int)8).numbersOnly = true;
        this.getTextField(8).setMinMaxDefault(1, 10, 1);
        this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 80, y += 22, 50, 18, "" + this.ranged.getRange()));
        this.addLabel(new GuiLabel(2, "gui.range", this.guiLeft + 5, y + 5, "guihint.npcrange"));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(1, 100, 2);
        this.addTextField(new GuiTextFieldNop(9, (Screen)this, this.guiLeft + 200, y, 30, 20, "" + this.ranged.getMeleeRange()));
        this.addLabel(new GuiLabel(16, "stats.meleerange", this.guiLeft + 135, y + 5, "guihint.npcmeleerange"));
        this.getTextField((int)9).numbersOnly = true;
        this.getTextField(9).setMinMaxDefault(0, this.stats.aggroRange, 5);
        this.addTextField(new GuiTextFieldNop(3, (Screen)this, this.guiLeft + 80, y += 22, 50, 18, "" + this.ranged.getDelayMin()));
        this.addLabel(new GuiLabel(3, "stats.mindelay", this.guiLeft + 5, y + 5, "guihint.npcmindelay"));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField(3).setMinMaxDefault(1, 9999, 20);
        this.addTextField(new GuiTextFieldNop(4, (Screen)this, this.guiLeft + 200, y, 50, 18, "" + this.ranged.getDelayMax()));
        this.addLabel(new GuiLabel(4, "stats.maxdelay", this.guiLeft + 135, y + 5, "guihint.npcmaxdelay"));
        this.getTextField((int)4).numbersOnly = true;
        this.getTextField(4).setMinMaxDefault(1, 9999, 20);
        this.addTextField(new GuiTextFieldNop(6, (Screen)this, this.guiLeft + 80, y += 22, 50, 18, "" + this.ranged.getBurst()));
        this.addLabel(new GuiLabel(6, "stats.burstcount", this.guiLeft + 5, y + 5, "guihint.npcburstcount"));
        this.getTextField((int)6).numbersOnly = true;
        this.getTextField(6).setMinMaxDefault(1, 100, 20);
        this.addTextField(new GuiTextFieldNop(5, (Screen)this, this.guiLeft + 200, y, 50, 18, "" + this.ranged.getBurstDelay()));
        this.addLabel(new GuiLabel(5, "stats.burstspeed", this.guiLeft + 135, y + 5, "guihint.npcburstrate"));
        this.getTextField((int)5).numbersOnly = true;
        this.getTextField(5).setMinMaxDefault(0, 30, 0);
        this.addTextField(new GuiTextFieldNop(7, (Screen)this, this.guiLeft + 80, y += 22, 100, 20, this.ranged.getSound(0)));
        this.addLabel(new GuiLabel(7, "stats.firesound", this.guiLeft + 5, y + 5, "guihint.npcfiresound"));
        this.addButton(new GuiButtonNop(this, 7, this.guiLeft + 187, y, 60, 20, "mco.template.button.select"));
        this.addTextField(new GuiTextFieldNop(11, (Screen)this, this.guiLeft + 80, y += 22, 100, 20, this.ranged.getSound(1)));
        this.addLabel(new GuiLabel(11, "stats.hitsound", this.guiLeft + 5, y + 5, "guihint.npchitsound"));
        this.addButton(new GuiButtonNop(this, 11, this.guiLeft + 187, y, 60, 20, "mco.template.button.select"));
        this.addTextField(new GuiTextFieldNop(10, (Screen)this, this.guiLeft + 80, y += 22, 100, 20, this.ranged.getSound(2)));
        this.addLabel(new GuiLabel(10, "stats.groundsound", this.guiLeft + 5, y + 5, "guihint.npcgroundsound"));
        this.addButton(new GuiButtonNop(this, 10, this.guiLeft + 187, y, 60, 20, "mco.template.button.select"));
        this.addButton(new GuiButtonYesNo((IGuiInterface)this, 9, this.guiLeft + 100, y += 22, this.ranged.getHasAimAnimation()));
        this.addLabel(new GuiLabel(9, "stats.aimWhileShooting", this.guiLeft + 5, y + 5, "guihint.npcwhileshooting"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 13, this.guiLeft + 100, y += 22, 80, 20, new String[]{"gui.no", "gui.whendistant", "gui.whenhidden"}, this.ranged.getFireType()));
        this.addLabel(new GuiLabel(13, "stats.indirect", this.guiLeft + 5, y + 5, "guihint.npcshootindirect"));
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 190, this.guiTop + 190, 60, 20, "gui.done"));
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 1) {
            this.ranged.setAccuracy(textfield.getInteger());
        } else if (textfield.id == 2) {
            this.ranged.setRange(textfield.getInteger());
        } else if (textfield.id == 3) {
            this.ranged.setDelay(textfield.getInteger(), this.ranged.getDelayMax());
        } else if (textfield.id == 4) {
            this.ranged.setDelay(this.ranged.getDelayMin(), textfield.getInteger());
        } else if (textfield.id == 5) {
            this.ranged.setBurstDelay(textfield.getInteger());
        } else if (textfield.id == 6) {
            this.ranged.setBurst(textfield.getInteger());
        } else if (textfield.id == 7) {
            this.ranged.setSound(0, textfield.m_94155_());
        } else if (textfield.id == 8) {
            this.ranged.setShotCount(textfield.getInteger());
        } else if (textfield.id == 9) {
            this.ranged.setMeleeRange(textfield.getInteger());
        } else if (textfield.id == 10) {
            this.ranged.setSound(2, textfield.m_94155_());
        } else if (textfield.id == 11) {
            this.ranged.setSound(1, textfield.m_94155_());
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 7) {
            this.soundSelected = 0;
            this.setSubGui(new GuiSoundSelection(this.getTextField(7).m_94155_()));
        }
        if (id == 11) {
            this.soundSelected = 1;
            this.setSubGui(new GuiSoundSelection(this.getTextField(11).m_94155_()));
        }
        if (id == 10) {
            this.soundSelected = 2;
            this.setSubGui(new GuiSoundSelection(this.getTextField(10).m_94155_()));
        } else if (id == 66) {
            this.close();
        } else if (id == 9) {
            this.ranged.setHasAimAnimation(((GuiButtonYesNo)guibutton).getBoolean());
        } else if (id == 13) {
            this.ranged.setFireType(guibutton.getValue());
        }
    }

    @Override
    public void subGuiClosed(Screen subgui) {
        GuiSoundSelection gss = (GuiSoundSelection)subgui;
        if (gss.selectedResource != null) {
            this.ranged.setSound(this.soundSelected, gss.selectedResource.toString());
        }
    }
}

