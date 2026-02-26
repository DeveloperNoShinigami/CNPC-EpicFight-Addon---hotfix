/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.controllers.data;

import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.EventHooks;
import noppes.npcs.constants.EnumQuestCompletion;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketAchievement;
import noppes.npcs.packets.client.PacketChat;
import noppes.npcs.quests.QuestInterface;

public class PlayerQuestData {
    public HashMap<Integer, QuestData> activeQuests = new HashMap();
    public HashMap<Integer, Long> finishedQuests = new HashMap();

    public void loadNBTData(CompoundTag mainCompound) {
        ListTag list2;
        if (mainCompound == null) {
            return;
        }
        CompoundTag compound = mainCompound.m_128469_("QuestData");
        ListTag list = compound.m_128437_("CompletedQuests", 10);
        if (list != null) {
            HashMap<Integer, Long> finishedQuests = new HashMap<Integer, Long>();
            for (int i = 0; i < list.size(); ++i) {
                CompoundTag nbttagcompound = list.m_128728_(i);
                finishedQuests.put(nbttagcompound.m_128451_("Quest"), nbttagcompound.m_128454_("Date"));
            }
            this.finishedQuests = finishedQuests;
        }
        if ((list2 = compound.m_128437_("ActiveQuests", 10)) != null) {
            HashMap<Integer, QuestData> activeQuests = new HashMap<Integer, QuestData>();
            for (int i = 0; i < list2.size(); ++i) {
                CompoundTag nbttagcompound = list2.m_128728_(i);
                int id = nbttagcompound.m_128451_("Quest");
                Quest quest = QuestController.instance.quests.get(id);
                if (quest == null) continue;
                QuestData data = new QuestData(quest);
                data.readAdditionalSaveData(nbttagcompound);
                activeQuests.put(id, data);
            }
            this.activeQuests = activeQuests;
        }
    }

    public void saveNBTData(CompoundTag maincompound) {
        CompoundTag compound = new CompoundTag();
        ListTag list = new ListTag();
        for (int quest : this.finishedQuests.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Quest", quest);
            nbttagcompound.m_128356_("Date", this.finishedQuests.get(quest).longValue());
            list.add((Object)nbttagcompound);
        }
        compound.m_128365_("CompletedQuests", (Tag)list);
        ListTag list2 = new ListTag();
        for (int quest : this.activeQuests.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Quest", quest);
            this.activeQuests.get(quest).addAdditionalSaveData(nbttagcompound);
            list2.add((Object)nbttagcompound);
        }
        compound.m_128365_("ActiveQuests", (Tag)list2);
        maincompound.m_128365_("QuestData", (Tag)compound);
    }

    public QuestData getQuestCompletion(Player player, EntityNPCInterface npc) {
        for (QuestData data : this.activeQuests.values()) {
            Quest quest = data.quest;
            if (quest == null || quest.completion != EnumQuestCompletion.Npc || !quest.completerNpc.equals(npc.display.getName()) || !quest.questInterface.isCompleted(player)) continue;
            return data;
        }
        return null;
    }

    public boolean checkQuestCompletion(Player player, int type) {
        boolean bo = false;
        for (QuestData data : this.activeQuests.values()) {
            if (data.quest.type != type && type >= 0) continue;
            QuestInterface inter = data.quest.questInterface;
            if (inter.isCompleted(player)) {
                if (data.isCompleted) continue;
                if (!data.quest.complete(player, data)) {
                    Packets.send((ServerPlayer)player, new PacketAchievement((Component)Component.m_237115_((String)"quest.completed"), (Component)Component.m_237115_((String)data.quest.title), 2));
                    Packets.send((ServerPlayer)player, new PacketChat((Component)Component.m_237115_((String)"quest.completed").m_130946_(": ").m_7220_((Component)Component.m_237115_((String)data.quest.title))));
                }
                data.isCompleted = true;
                bo = true;
                EventHooks.onQuestFinished(PlayerData.get((Player)player).scriptData, data.quest);
                continue;
            }
            data.isCompleted = false;
        }
        return bo;
    }
}

