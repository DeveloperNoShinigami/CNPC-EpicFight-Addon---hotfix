/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.client.gui.SubGuiNpcAvailability;
import noppes.npcs.client.gui.roles.SubGuiNpcConversationLine;
import noppes.npcs.client.gui.select.GuiQuestSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketNpcJobSave;
import noppes.npcs.roles.JobConversation;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.GuiSelectionListener;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiNpcConversation
extends GuiNPCInterface2
implements ITextfieldListener,
GuiSelectionListener {
    private JobConversation job;
    private int slot = -1;

    public GuiNpcConversation(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobConversation)npc.job;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(40, "gui.name", this.guiLeft + 40, this.guiTop + 4));
        this.addLabel(new GuiLabel(41, "gui.name", this.guiLeft + 240, this.guiTop + 4));
        this.addLabel(new GuiLabel(42, "conversation.delay", this.guiLeft + 164, this.guiTop + 4));
        this.addLabel(new GuiLabel(43, "conversation.delay", this.guiLeft + 364, this.guiTop + 4));
        for (int i = 0; i < 14; ++i) {
            JobConversation.ConversationLine line = this.job.getLine(i);
            int offset = i >= 7 ? 200 : 0;
            this.addLabel(new GuiLabel(i, "" + (i + 1), this.guiLeft + 5 + offset - (i > 8 ? 6 : 0), this.guiTop + 18 + i % 7 * 22));
            this.addTextField(new GuiTextFieldNop(i, (Screen)this, this.guiLeft + 13 + offset, this.guiTop + 13 + i % 7 * 22, 100, 20, line.npc));
            this.addButton(new GuiButtonNop(this, i, this.guiLeft + 115 + offset, this.guiTop + 13 + i % 7 * 22, 46, 20, "conversation.line"));
            if (i <= 0) continue;
            this.addTextField(new GuiTextFieldNop(i + 14, (Screen)this, this.guiLeft + 164 + offset, this.guiTop + 13 + i % 7 * 22, 30, 20, "" + line.delay));
            this.getTextField((int)(i + 14)).numbersOnly = true;
            this.getTextField(i + 14).setMinMaxDefault(5, 1000, 40);
        }
        this.addLabel(new GuiLabel(50, "conversation.delay", this.guiLeft + 202, this.guiTop + 175));
        this.addTextField(new GuiTextFieldNop(50, (Screen)this, this.guiLeft + 260, this.guiTop + 170, 40, 20, "" + this.job.generalDelay));
        this.getTextField((int)50).numbersOnly = true;
        this.getTextField(50).setMinMaxDefault(10, 1000000, 12000);
        this.addLabel(new GuiLabel(54, "gui.range", this.guiLeft + 202, this.guiTop + 196));
        this.addTextField(new GuiTextFieldNop(54, (Screen)this, this.guiLeft + 260, this.guiTop + 191, 40, 20, "" + this.job.range));
        this.getTextField((int)54).numbersOnly = true;
        this.getTextField(54).setMinMaxDefault(4, 60, 20);
        this.addLabel(new GuiLabel(51, "quest.quest", this.guiLeft + 13, this.guiTop + 175));
        String title = this.job.questTitle;
        if (title.isEmpty()) {
            title = "gui.select";
        }
        this.addButton(new GuiButtonNop(this, 51, this.guiLeft + 70, this.guiTop + 170, 100, 20, title));
        this.addButton(new GuiButtonNop(this, 52, this.guiLeft + 171, this.guiTop + 170, 20, 20, "X"));
        this.addLabel(new GuiLabel(53, "availability.name", this.guiLeft + 13, this.guiTop + 196));
        this.addButton(new GuiButtonNop(this, 53, this.guiLeft + 110, this.guiTop + 191, 60, 20, "selectServer.edit"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 55, this.guiLeft + 310, this.guiTop + 181, 96, 20, new String[]{"gui.always", "gui.playernearby"}, this.job.mode));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id >= 0 && button.id < 14) {
            this.slot = button.id;
            JobConversation.ConversationLine line = this.job.getLine(this.slot);
            this.setSubGui(new SubGuiNpcConversationLine(line.getText(), line.getSound()));
        }
        if (button.id == 51) {
            this.setSubGui(new GuiQuestSelection(this.job.quest));
        }
        if (button.id == 52) {
            this.job.quest = -1;
            this.job.questTitle = "";
            this.m_7856_();
        }
        if (button.id == 53) {
            this.setSubGui(new SubGuiNpcAvailability(this.job.availability));
        }
        if (button.id == 55) {
            this.job.mode = button.getValue();
        }
    }

    @Override
    public void selected(int ob, String name) {
        this.job.quest = ob;
        this.job.questTitle = name;
        this.m_7856_();
    }

    @Override
    public void subGuiClosed(Screen gui) {
        if (gui instanceof SubGuiNpcConversationLine) {
            SubGuiNpcConversationLine sub = (SubGuiNpcConversationLine)gui;
            JobConversation.ConversationLine line = this.job.getLine(this.slot);
            line.setText(sub.line);
            line.setSound(sub.sound);
        }
    }

    @Override
    public void save() {
        Packets.sendServer(new SPacketNpcJobSave(this.job.save(new CompoundTag())));
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        JobConversation.ConversationLine line;
        if (textfield.id >= 0 && textfield.id < 14) {
            line = this.job.getLine(textfield.id);
            line.npc = textfield.m_94155_();
        }
        if (textfield.id >= 14 && textfield.id < 28) {
            line = this.job.getLine(textfield.id - 14);
            line.delay = textfield.getInteger();
        }
        if (textfield.id == 50) {
            this.job.generalDelay = textfield.getInteger();
        }
        if (textfield.id == 54) {
            this.job.range = textfield.getInteger();
        }
    }
}

