/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.FormattedText
 */
package noppes.npcs.client.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.FormattedText;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class SubGuiNpcFactionPoints
extends GuiBasic
implements ITextfieldListener {
    private Faction faction;

    public SubGuiNpcFactionPoints(Faction faction) {
        this.faction = faction;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(2, "faction.default", this.guiLeft + 4, this.guiTop + 33));
        this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 8 + this.f_96547_.m_92852_((FormattedText)this.getLabel(2).m_6035_()), this.guiTop + 28, 70, 20, "" + this.faction.defaultPoints));
        this.getTextField(2).m_94199_(6);
        this.getTextField((int)2).numbersOnly = true;
        String title = I18n.m_118938_((String)"faction.unfriendly", (Object[])new Object[0]) + "<->" + I18n.m_118938_((String)"faction.neutral", (Object[])new Object[0]);
        this.addLabel(new GuiLabel(3, title, this.guiLeft + 4, this.guiTop + 80));
        this.addTextField(new GuiTextFieldNop(3, (Screen)this, this.guiLeft + 8 + this.f_96547_.m_92895_(title), this.guiTop + 75, 70, 20, "" + this.faction.neutralPoints));
        title = I18n.m_118938_((String)"faction.neutral", (Object[])new Object[0]) + "<->" + I18n.m_118938_((String)"faction.friendly", (Object[])new Object[0]);
        this.addLabel(new GuiLabel(4, title, this.guiLeft + 4, this.guiTop + 105));
        this.addTextField(new GuiTextFieldNop(4, (Screen)this, this.guiLeft + 8 + this.f_96547_.m_92895_(title), this.guiTop + 100, 70, 20, "" + this.faction.friendlyPoints));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField((int)4).numbersOnly = true;
        if (this.getTextField(3).m_252754_() > this.getTextField(4).m_252754_()) {
            this.getTextField(4).m_252865_(this.getTextField(3).m_252754_());
        } else {
            this.getTextField(3).m_252865_(this.getTextField(4).m_252754_());
        }
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 20, this.guiTop + 192, 90, 20, "gui.done"));
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 2) {
            this.faction.defaultPoints = textfield.getInteger();
        } else if (textfield.id == 3) {
            this.faction.neutralPoints = textfield.getInteger();
        } else if (textfield.id == 4) {
            this.faction.friendlyPoints = textfield.getInteger();
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 66) {
            this.close();
        }
    }
}

