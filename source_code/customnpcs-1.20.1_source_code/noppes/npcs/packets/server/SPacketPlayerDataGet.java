/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import java.util.HashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumPlayerData;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.data.Bank;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.PlayerBankData;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerDialogData;
import noppes.npcs.controllers.data.PlayerFactionData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.controllers.data.PlayerTransportData;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.TransportLocation;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketPlayerDataGet
extends PacketServerBasic {
    private EnumPlayerData type;
    private String name;

    public SPacketPlayerDataGet(EnumPlayerData type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_GUI;
    }

    public static void encode(SPacketPlayerDataGet msg, FriendlyByteBuf buf) {
        buf.m_130068_((Enum)msg.type);
        buf.m_130070_(msg.name);
    }

    public static SPacketPlayerDataGet decode(FriendlyByteBuf buf) {
        return new SPacketPlayerDataGet((EnumPlayerData)buf.m_130066_(EnumPlayerData.class), buf.m_130136_(Short.MAX_VALUE));
    }

    @Override
    protected void handle() {
        SPacketPlayerDataGet.sendPlayerData(this.type, this.player, this.name);
    }

    public static void sendPlayerData(EnumPlayerData type, ServerPlayer player, String name) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        if (type == EnumPlayerData.Players) {
            for (String username : PlayerDataController.instance.nameUUIDs.keySet()) {
                map.put(username, 0);
            }
            for (String username : player.m_20194_().m_6846_().m_11291_()) {
                map.put(username, 0);
            }
        } else {
            PlayerData playerdata = PlayerDataController.instance.getDataFromUsername(player.m_20194_(), name);
            if (type == EnumPlayerData.Dialog) {
                PlayerDialogData data = playerdata.dialogData;
                for (int questId : data.dialogsRead) {
                    Dialog dialog = DialogController.instance.dialogs.get(questId);
                    if (dialog == null) continue;
                    map.put(dialog.category.title + ": " + dialog.title, questId);
                }
            } else if (type == EnumPlayerData.Quest) {
                Quest quest;
                PlayerQuestData data = playerdata.questData;
                for (int questId : data.activeQuests.keySet()) {
                    quest = QuestController.instance.quests.get(questId);
                    if (quest == null) continue;
                    map.put(quest.category.title + ": " + quest.title + "(Active quest)", questId);
                }
                for (int questId : data.finishedQuests.keySet()) {
                    quest = QuestController.instance.quests.get(questId);
                    if (quest == null) continue;
                    map.put(quest.category.title + ": " + quest.title + "(Finished quest)", questId);
                }
            } else if (type == EnumPlayerData.Transport) {
                PlayerTransportData data = playerdata.transportData;
                for (int questId : data.transports) {
                    TransportLocation location = TransportController.getInstance().getTransport(questId);
                    if (location == null) continue;
                    map.put(location.category.title + ": " + location.name, questId);
                }
            } else if (type == EnumPlayerData.Bank) {
                PlayerBankData data = playerdata.bankData;
                for (int bankId : data.banks.keySet()) {
                    Bank bank = BankController.getInstance().banks.get(bankId);
                    if (bank == null) continue;
                    map.put(bank.name, bankId);
                }
            } else if (type == EnumPlayerData.Factions) {
                PlayerFactionData data = playerdata.factionData;
                for (int factionId : data.factionData.keySet()) {
                    Faction faction = FactionController.instance.factions.get(factionId);
                    if (faction == null) continue;
                    map.put(faction.name + "(" + data.getFactionPoints((Player)player, factionId) + ")", factionId);
                }
            }
        }
        NoppesUtilServer.sendScrollData(player, map);
    }
}

