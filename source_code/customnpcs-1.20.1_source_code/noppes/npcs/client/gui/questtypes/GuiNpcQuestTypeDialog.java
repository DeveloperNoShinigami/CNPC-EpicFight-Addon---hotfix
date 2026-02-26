/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui.questtypes;

import java.util.HashMap;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.client.gui.select.GuiDialogSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketQuestDialogTitles;
import noppes.npcs.quests.QuestDialog;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.listeners.GuiSelectionListener;
import noppes.npcs.shared.client.gui.listeners.IGuiData;

public class GuiNpcQuestTypeDialog
extends GuiNPCInterface
implements GuiSelectionListener,
IGuiData {
    private Screen parent;
    private QuestDialog quest;
    private HashMap<Integer, String> data = new HashMap();
    private int selectedSlot;

    public GuiNpcQuestTypeDialog(EntityNPCInterface npc, Quest q, Screen parent) {
        this.npc = npc;
        this.parent = parent;
        this.title = "Quest Dialog Setup";
        this.quest = (QuestDialog)q.questInterface;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
        Packets.sendServer(new SPacketQuestDialogTitles(this.quest.dialogs.containsKey(0) ? this.quest.dialogs.get(0) : -1, this.quest.dialogs.containsKey(1) ? this.quest.dialogs.get(1) : -1, this.quest.dialogs.containsKey(2) ? this.quest.dialogs.get(2) : -1));
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        for (int i = 0; i < 3; ++i) {
            String title = "dialog.selectoption";
            if (this.data.containsKey(i)) {
                title = this.data.get(i);
            }
            this.addButton(new GuiButtonNop(this, i + 9, this.guiLeft + 10, 55 + i * 22, 20, 20, "X"));
            this.addButton(new GuiButtonNop(this, i + 3, this.guiLeft + 34, 55 + i * 22, 210, 20, title));
        }
        this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 150, this.guiTop + 190, 98, 20, "gui.back"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 0) {
            this.close();
        }
        if (button.id >= 3 && button.id < 9) {
            this.selectedSlot = button.id - 3;
            int id = -1;
            if (this.quest.dialogs.containsKey(this.selectedSlot)) {
                id = this.quest.dialogs.get(this.selectedSlot);
            }
            this.setSubGui(new GuiDialogSelection(id));
        }
        if (button.id >= 9 && button.id < 15) {
            int slot = button.id - 9;
            this.quest.dialogs.remove(slot);
            this.data.remove(slot);
            this.save();
            this.m_7856_();
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void selected(int id, String name) {
        this.quest.dialogs.put(this.selectedSlot, id);
        this.data.put(this.selectedSlot, name);
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.data.clear();
        if (compound.m_128441_("1")) {
            this.data.put(0, compound.m_128461_("1"));
        }
        if (compound.m_128441_("2")) {
            this.data.put(1, compound.m_128461_("2"));
        }
        if (compound.m_128441_("3")) {
            this.data.put(2, compound.m_128461_("3"));
        }
        this.m_7856_();
    }
}

