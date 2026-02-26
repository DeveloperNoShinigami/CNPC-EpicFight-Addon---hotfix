/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 */
package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import noppes.npcs.controllers.data.FactionOptions;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketFactionsGet;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.IScrollData;

public class SubGuiNpcFactionOptions
extends GuiBasic
implements IScrollData,
ICustomScrollListener {
    private FactionOptions options;
    private Map<String, Integer> data = new HashMap<String, Integer>();
    private GuiCustomScrollNop scrollFactions;
    private int selected = -1;

    public SubGuiNpcFactionOptions(FactionOptions options) {
        this.options = options;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
        Packets.sendServer(new SPacketFactionsGet());
    }

    @Override
    public void m_7856_() {
        Object label;
        super.m_7856_();
        if (this.scrollFactions == null) {
            this.scrollFactions = new GuiCustomScrollNop(this, 0);
            this.scrollFactions.setSize(120, 208);
        }
        this.scrollFactions.guiLeft = this.guiLeft + 130;
        this.scrollFactions.guiTop = this.guiTop + 4;
        this.addScroll(this.scrollFactions);
        this.addLabel(new GuiLabel(0, "1: ", this.guiLeft + 4, this.guiTop + 12));
        if (this.data.containsValue(this.options.factionId)) {
            this.addLabel(new GuiLabel(1, this.getFactionName(this.options.factionId), this.guiLeft + 12, this.guiTop + 8));
            label = "";
            label = this.options.decreaseFactionPoints ? (String)label + I18n.m_118938_((String)"gui.decrease", (Object[])new Object[0]) : (String)label + I18n.m_118938_((String)"gui.increase", (Object[])new Object[0]);
            label = (String)label + " " + this.options.factionPoints + " " + I18n.m_118938_((String)"faction.points", (Object[])new Object[0]);
            this.addLabel(new GuiLabel(3, (String)label, this.guiLeft + 12, this.guiTop + 16));
            this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 110, this.guiTop + 7, 20, 20, "X"));
        }
        this.addLabel(new GuiLabel(4, "2: ", this.guiLeft + 4, this.guiTop + 40));
        if (this.data.containsValue(this.options.faction2Id)) {
            this.addLabel(new GuiLabel(5, this.getFactionName(this.options.faction2Id), this.guiLeft + 12, this.guiTop + 36));
            label = "";
            label = this.options.decreaseFaction2Points ? (String)label + I18n.m_118938_((String)"gui.decrease", (Object[])new Object[0]) : (String)label + I18n.m_118938_((String)"gui.increase", (Object[])new Object[0]);
            label = (String)label + " " + this.options.faction2Points + " " + I18n.m_118938_((String)"faction.points", (Object[])new Object[0]);
            this.addLabel(new GuiLabel(6, (String)label, this.guiLeft + 12, this.guiTop + 44));
            this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 110, this.guiTop + 35, 20, 20, "X"));
        }
        if (!(this.selected < 0 || this.data.containsValue(this.options.faction2Id) && this.data.containsValue(this.options.factionId) || this.options.hasFaction(this.selected))) {
            this.addButton(new GuiButtonNop((IGuiInterface)this, 2, this.guiLeft + 4, this.guiTop + 60, 90, 20, new String[]{"gui.increase", "gui.decrease"}, 0));
            this.addTextField(new GuiTextFieldNop(1, (Screen)this, this.guiLeft + 4, this.guiTop + 82, 110, 20, "10"));
            this.getTextField((int)1).numbersOnly = true;
            this.getTextField(1).setMinMaxDefault(1, 100000, 10);
            this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 4, this.guiTop + 104, 60, 20, "gui.add"));
        }
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 20, this.guiTop + 192, 90, 20, "gui.done"));
    }

    private String getFactionName(int faction) {
        for (String s : this.data.keySet()) {
            if (this.data.get(s) != faction) continue;
            return s;
        }
        return null;
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 0) {
            this.options.factionId = -1;
            this.m_7856_();
        }
        if (id == 1) {
            this.options.faction2Id = -1;
            this.m_7856_();
        }
        if (id == 3) {
            if (!this.data.containsValue(this.options.factionId)) {
                this.options.factionId = this.selected;
                this.options.decreaseFactionPoints = this.getButton(2).getValue() == 1;
                this.options.factionPoints = this.getTextField(1).getInteger();
            } else if (!this.data.containsValue(this.options.faction2Id)) {
                this.options.faction2Id = this.selected;
                this.options.decreaseFaction2Points = this.getButton(2).getValue() == 1;
                this.options.faction2Points = this.getTextField(1).getInteger();
            }
            this.m_7856_();
        }
        if (id == 66) {
            this.close();
        }
    }

    @Override
    public void scrollClicked(double i, double j, int k, GuiCustomScrollNop guiCustomScroll) {
        this.selected = this.data.get(guiCustomScroll.getSelected());
        this.m_7856_();
    }

    @Override
    public void setData(Vector<String> list, Map<String, Integer> data) {
        GuiCustomScrollNop scroll = this.getScroll(0);
        String name = scroll.getSelected();
        this.data = data;
        scroll.setList(list);
        if (name != null) {
            scroll.setSelected(name);
        }
        this.m_7856_();
    }

    @Override
    public void setSelected(String selected) {
    }

    @Override
    public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
    }
}

