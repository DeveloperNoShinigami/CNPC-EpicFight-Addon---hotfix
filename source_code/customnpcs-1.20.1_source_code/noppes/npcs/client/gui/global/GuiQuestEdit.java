/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui.global;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.gui.SubGuiMailmanSendSetup;
import noppes.npcs.client.gui.SubGuiNpcCommand;
import noppes.npcs.client.gui.SubGuiNpcFactionOptions;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeDialog;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeKill;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeLocation;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeManual;
import noppes.npcs.client.gui.select.GuiQuestSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.PlayerMail;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketQuestOpen;
import noppes.npcs.packets.server.SPacketQuestSave;
import noppes.npcs.shared.client.gui.GuiTextAreaScreen;
import noppes.npcs.shared.client.gui.components.GuiButtonBiDirectional;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.GuiSelectionListener;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiQuestEdit
extends GuiNPCInterface
implements GuiSelectionListener,
ITextfieldListener {
    private Quest quest;
    private boolean questlogTA = false;

    public GuiQuestEdit(Quest quest) {
        this.quest = quest;
        this.setBackground("menubg.png");
        this.imageWidth = 386;
        this.imageHeight = 226;
        NoppesUtilServer.setEditingQuest((Player)this.player, quest);
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(1, "gui.title", this.guiLeft + 4, this.guiTop + 8));
        this.addTextField(new GuiTextFieldNop(1, (Screen)this, this.guiLeft + 46, this.guiTop + 3, 220, 20, this.quest.title));
        this.addLabel(new GuiLabel(0, "ID", this.guiLeft + 268, this.guiTop + 4));
        this.addLabel(new GuiLabel(2, "" + this.quest.id, this.guiLeft + 268, this.guiTop + 14));
        this.addLabel(new GuiLabel(3, "quest.completedtext", this.guiLeft + 4, this.guiTop + 30));
        this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 120, this.guiTop + 25, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiLabel(4, "quest.questlogtext", this.guiLeft + 4, this.guiTop + 51));
        this.addButton(new GuiButtonNop(this, 4, this.guiLeft + 120, this.guiTop + 46, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiLabel(5, "quest.reward", this.guiLeft + 4, this.guiTop + 72));
        this.addButton(new GuiButtonNop(this, 5, this.guiLeft + 120, this.guiTop + 67, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiLabel(6, "gui.type", this.guiLeft + 4, this.guiTop + 93));
        this.addButton(new GuiButtonBiDirectional((IGuiInterface)this, 6, this.guiLeft + 70, this.guiTop + 88, 90, 20, new String[]{"quest.item", "quest.dialog", "quest.kill", "quest.location", "quest.areakill", "quest.manual"}, this.quest.type));
        this.addButton(new GuiButtonNop(this, 7, this.guiLeft + 162, this.guiTop + 88, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiLabel(8, "quest.repeatable", this.guiLeft + 4, this.guiTop + 114));
        this.addButton(new GuiButtonBiDirectional((IGuiInterface)this, 8, this.guiLeft + 70, this.guiTop + 109, 140, 20, new String[]{"gui.no", "gui.yes", "quest.mcdaily", "quest.mcweekly", "quest.rldaily", "quest.rlweekly"}, this.quest.repeat.ordinal()));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 9, this.guiLeft + 4, this.guiTop + 131, 90, 20, new String[]{"quest.npc", "quest.instant"}, this.quest.completion.ordinal()));
        if (this.quest.completerNpc.isEmpty()) {
            this.quest.completerNpc = this.npc.display.getName();
        }
        this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 96, this.guiTop + 131, 114, 20, this.quest.completerNpc));
        this.getTextField((int)2).enabled = this.quest.completion == EnumQuestCompletion.Npc;
        this.addLabel(new GuiLabel(10, "faction.options", this.guiLeft + 214, this.guiTop + 30));
        this.addButton(new GuiButtonNop(this, 10, this.guiLeft + 330, this.guiTop + 25, 50, 20, "selectServer.edit"));
        this.addLabel(new GuiLabel(15, "advMode.command", this.guiLeft + 214, this.guiTop + 52));
        this.addButton(new GuiButtonNop(this, 15, this.guiLeft + 330, this.guiTop + 47, 50, 20, "selectServer.edit"));
        this.addButton(new GuiButtonNop(this, 13, this.guiLeft + 4, this.guiTop + 153, 164, 20, "mailbox.setup"));
        this.addButton(new GuiButtonNop(this, 14, this.guiLeft + 170, this.guiTop + 153, 20, 20, "X"));
        if (!this.quest.mail.subject.isEmpty()) {
            this.getButton(13).setDisplayText(this.quest.mail.subject);
        }
        this.addButton(new GuiButtonNop(this, 11, this.guiLeft + 4, this.guiTop + 175, 164, 20, "quest.next"));
        this.addButton(new GuiButtonNop(this, 12, this.guiLeft + 170, this.guiTop + 175, 20, 20, "X"));
        Quest q = QuestController.instance.get(this.quest.nextQuestid);
        if (q != null) {
            this.getButton(11).setDisplayText(q.getName());
        }
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 362, this.guiTop + 4, 20, 20, "X"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 3) {
            this.questlogTA = false;
            this.setSubGui(new GuiTextAreaScreen(this.quest.completeText));
        }
        if (button.id == 4) {
            this.questlogTA = true;
            this.setSubGui(new GuiTextAreaScreen(this.quest.logText));
        }
        if (button.id == 5) {
            Packets.sendServer(new SPacketQuestOpen(EnumGuiType.QuestReward, this.quest.save(new CompoundTag())));
        }
        if (button.id == 6) {
            this.quest.setType(button.getValue());
        }
        if (button.id == 7) {
            if (this.quest.type == 0) {
                Packets.sendServer(new SPacketQuestOpen(EnumGuiType.QuestItem, this.quest.save(new CompoundTag())));
            }
            if (this.quest.type == 1) {
                this.setSubGui(new GuiNpcQuestTypeDialog(this.npc, this.quest, this.wrapper.parent));
            }
            if (this.quest.type == 2) {
                this.setSubGui(new GuiNpcQuestTypeKill(this.npc, this.quest, this.wrapper.parent));
            }
            if (this.quest.type == 3) {
                this.setSubGui(new GuiNpcQuestTypeLocation(this.npc, this.quest, this.wrapper.parent));
            }
            if (this.quest.type == 4) {
                this.setSubGui(new GuiNpcQuestTypeKill(this.npc, this.quest, this.wrapper.parent));
            }
            if (this.quest.type == 5) {
                this.setSubGui(new GuiNpcQuestTypeManual(this.npc, this.quest, this.wrapper.parent));
            }
        }
        if (button.id == 8) {
            this.quest.repeat = EnumQuestRepeat.values()[button.getValue()];
        }
        if (button.id == 9) {
            this.quest.completion = EnumQuestCompletion.values()[button.getValue()];
            boolean bl = this.getTextField((int)2).enabled = this.quest.completion == EnumQuestCompletion.Npc;
        }
        if (button.id == 15) {
            this.setSubGui(new SubGuiNpcCommand(this.quest.command));
        }
        if (button.id == 10) {
            this.setSubGui(new SubGuiNpcFactionOptions(this.quest.factionOptions));
        }
        if (button.id == 11) {
            this.setSubGui(new GuiQuestSelection(this.quest.nextQuestid));
        }
        if (button.id == 12) {
            this.quest.nextQuestid = -1;
            this.m_7856_();
        }
        if (button.id == 13) {
            this.setSubGui(new SubGuiMailmanSendSetup(this.quest.mail));
        }
        if (button.id == 14) {
            this.quest.mail = new PlayerMail();
            this.m_7856_();
        }
        if (button.id == 66) {
            this.close();
        }
    }

    @Override
    public void unFocused(GuiTextFieldNop guiNpcTextField) {
        if (guiNpcTextField.id == 1) {
            this.quest.title = guiNpcTextField.m_94155_();
            while (QuestController.instance.containsQuestName(this.quest.category, this.quest)) {
                this.quest.title = this.quest.title + "_";
            }
        }
        if (guiNpcTextField.id == 2) {
            this.quest.completerNpc = guiNpcTextField.m_94155_();
        }
    }

    @Override
    public void subGuiClosed(Screen subgui) {
        if (subgui instanceof GuiTextAreaScreen) {
            GuiTextAreaScreen gui = (GuiTextAreaScreen)subgui;
            if (this.questlogTA) {
                this.quest.logText = gui.text;
            } else {
                this.quest.completeText = gui.text;
            }
        } else if (subgui instanceof SubGuiNpcCommand) {
            SubGuiNpcCommand sub = (SubGuiNpcCommand)subgui;
            this.quest.command = sub.command;
        } else {
            this.m_7856_();
        }
    }

    @Override
    public void selected(int id, String name) {
        this.quest.nextQuestid = id;
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void save() {
        GuiTextFieldNop.unfocus();
        Packets.sendServer(new SPacketQuestSave(this.quest.category.id, this.quest.save(new CompoundTag())));
    }
}

