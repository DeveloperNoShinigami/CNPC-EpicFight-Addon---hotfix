/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.quests;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NBTTags;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.quests.QuestInterface;

public class QuestDialog
extends QuestInterface {
    public HashMap<Integer, Integer> dialogs = new HashMap();

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.dialogs = NBTTags.getIntegerIntegerMap(compound.m_128437_("QuestDialogs", 10));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.m_128365_("QuestDialogs", (Tag)NBTTags.nbtIntegerIntegerMap(this.dialogs));
    }

    @Override
    public boolean isCompleted(Player player) {
        for (int dialogId : this.dialogs.values()) {
            if (PlayerData.get((Player)player).dialogData.dialogsRead.contains(dialogId)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void handleComplete(Player player) {
    }

    @Override
    public IQuestObjective[] getObjectives(Player player) {
        ArrayList<QuestDialogObjective> list = new ArrayList<QuestDialogObjective>();
        for (int i = 0; i < 3; ++i) {
            Dialog dialog;
            if (!this.dialogs.containsKey(i) || (dialog = DialogController.instance.dialogs.get(this.dialogs.get(i))) == null) continue;
            list.add(new QuestDialogObjective(player, dialog));
        }
        return list.toArray(new IQuestObjective[list.size()]);
    }

    class QuestDialogObjective
    implements IQuestObjective {
        private final Player player;
        private final Dialog dialog;

        public QuestDialogObjective(Player player, Dialog dialog) {
            this.player = player;
            this.dialog = dialog;
        }

        @Override
        public int getProgress() {
            return this.isCompleted() ? 1 : 0;
        }

        @Override
        public void setProgress(int progress) {
            if (progress < 0 || progress > 1) {
                throw new CustomNPCsException("Progress has to be 0 or 1", new Object[0]);
            }
            PlayerData data = PlayerData.get(this.player);
            boolean completed = data.dialogData.dialogsRead.contains(this.dialog.id);
            if (progress == 0 && completed) {
                data.dialogData.dialogsRead.remove(this.dialog.id);
                data.questData.checkQuestCompletion(this.player, 1);
                data.updateClient = true;
            }
            if (progress == 1 && !completed) {
                data.dialogData.dialogsRead.add(this.dialog.id);
                data.questData.checkQuestCompletion(this.player, 1);
                data.updateClient = true;
            }
        }

        @Override
        public int getMaxProgress() {
            return 1;
        }

        @Override
        public boolean isCompleted() {
            PlayerData data = PlayerData.get(this.player);
            return data.dialogData.dialogsRead.contains(this.dialog.id);
        }

        @Override
        public String getText() {
            return this.getMCText().getString();
        }

        @Override
        public Component getMCText() {
            return Component.m_237115_((String)this.dialog.title).m_130946_(" (").m_7220_((Component)Component.m_237115_((String)(this.isCompleted() ? "quest.read" : "quest.unread"))).m_130946_(")");
        }
    }
}

