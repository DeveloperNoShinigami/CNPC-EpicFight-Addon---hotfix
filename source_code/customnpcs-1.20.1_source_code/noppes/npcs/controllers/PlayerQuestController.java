/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.controllers;

import java.util.Vector;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.EventHooks;
import noppes.npcs.constants.EnumQuestRepeat;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketAchievement;
import noppes.npcs.packets.client.PacketChat;
import noppes.npcs.quests.QuestDialog;
import noppes.npcs.shared.common.util.LogWriter;

public class PlayerQuestController {
    public static boolean hasActiveQuests(Player player) {
        PlayerQuestData data = PlayerData.get((Player)player).questData;
        return !data.activeQuests.isEmpty();
    }

    public static boolean isQuestActive(Player player, int quest) {
        PlayerQuestData data = PlayerData.get((Player)player).questData;
        return data.activeQuests.containsKey(quest);
    }

    public static boolean isQuestCompleted(Player player, int quest) {
        PlayerQuestData data = PlayerData.get((Player)player).questData;
        QuestData q = data.activeQuests.get(quest);
        if (q == null) {
            return false;
        }
        return q.isCompleted;
    }

    public static boolean isQuestFinished(Player player, int questid) {
        PlayerQuestData data = PlayerData.get((Player)player).questData;
        return data.finishedQuests.containsKey(questid);
    }

    public static boolean canQuestBeAccepted(Player player, int questId) {
        Quest quest = QuestController.instance.quests.get(questId);
        if (quest == null) {
            return false;
        }
        PlayerQuestData data = PlayerData.get((Player)player).questData;
        if (data.activeQuests.containsKey(quest.id)) {
            return false;
        }
        if (!data.finishedQuests.containsKey(quest.id) || quest.repeat == EnumQuestRepeat.REPEATABLE) {
            return true;
        }
        if (quest.repeat == EnumQuestRepeat.NONE) {
            return false;
        }
        long questTime = data.finishedQuests.get(quest.id);
        if (quest.repeat == EnumQuestRepeat.MCDAILY) {
            return player.m_9236_().m_46467_() - questTime >= 24000L;
        }
        if (quest.repeat == EnumQuestRepeat.MCWEEKLY) {
            return player.m_9236_().m_46467_() - questTime >= 168000L;
        }
        if (quest.repeat == EnumQuestRepeat.RLDAILY) {
            return System.currentTimeMillis() - questTime >= 86400000L;
        }
        if (quest.repeat == EnumQuestRepeat.RLWEEKLY) {
            return System.currentTimeMillis() - questTime >= 604800000L;
        }
        return false;
    }

    public static void addActiveQuest(Quest quest, Player player) {
        PlayerData playerdata = PlayerData.get(player);
        LogWriter.debug("AddActiveQuest: " + quest.title + " + " + playerdata);
        PlayerQuestData data = playerdata.questData;
        if (playerdata.scriptData.getPlayer().canQuestBeAccepted(quest.id)) {
            if (EventHooks.onQuestStarted(playerdata.scriptData, quest)) {
                return;
            }
            data.activeQuests.put(quest.id, new QuestData(quest));
            Packets.send((ServerPlayer)player, new PacketAchievement((Component)Component.m_237115_((String)"quest.newquest"), (Component)Component.m_237115_((String)quest.title), 2));
            Packets.send((ServerPlayer)player, new PacketChat((Component)Component.m_237115_((String)"quest.newquest").m_130946_(":").m_7220_((Component)Component.m_237115_((String)quest.title))));
            playerdata.updateClient = true;
        }
    }

    public static void setQuestFinished(Quest quest, Player player) {
        PlayerData playerdata = PlayerData.get(player);
        PlayerQuestData data = playerdata.questData;
        data.activeQuests.remove(quest.id);
        if (quest.repeat == EnumQuestRepeat.RLDAILY || quest.repeat == EnumQuestRepeat.RLWEEKLY) {
            data.finishedQuests.put(quest.id, System.currentTimeMillis());
        } else {
            data.finishedQuests.put(quest.id, player.m_9236_().m_46467_());
        }
        if (quest.repeat != EnumQuestRepeat.NONE && quest.type == 1) {
            QuestDialog questdialog = (QuestDialog)quest.questInterface;
            for (int dialog : questdialog.dialogs.values()) {
                playerdata.dialogData.dialogsRead.remove(dialog);
            }
        }
        playerdata.updateClient = true;
    }

    public static Vector<Quest> getActiveQuests(Player player) {
        Vector<Quest> quests = new Vector<Quest>();
        PlayerQuestData data = PlayerData.get((Player)player).questData;
        for (QuestData questdata : data.activeQuests.values()) {
            if (questdata.quest == null) continue;
            quests.add(questdata.quest);
        }
        return quests;
    }
}

