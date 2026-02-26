/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandRuntimeException
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.CustomNpcs;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.SyncController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketAchievement;
import noppes.npcs.packets.client.PacketChat;

public class CmdQuest {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = Commands.m_82127_((String)"quest");
        command.then(Commands.m_82127_((String)"start").then(((RequiredArgumentBuilder)Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"quest", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Quest quest = QuestController.instance.quests.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"quest"));
            if (quest == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown QuestID"));
            }
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                QuestData questdata = new QuestData(quest);
                data.questData.activeQuests.put(quest.id, questdata);
                data.save(true);
                Packets.send(player, new PacketAchievement((Component)Component.m_237115_((String)"quest.newquest"), (Component)Component.m_237115_((String)quest.title), 2));
                Packets.send(player, new PacketChat((Component)Component.m_237115_((String)"quest.newquest").m_130946_(":").m_7220_((Component)Component.m_237115_((String)quest.title))));
            }
            return 1;
        }))));
        command.then(Commands.m_82127_((String)"finish").then(((RequiredArgumentBuilder)Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"quest", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Quest quest = QuestController.instance.quests.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"quest"));
            if (quest == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown QuestID"));
            }
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                data.questData.finishedQuests.put(quest.id, System.currentTimeMillis());
                data.save(true);
            }
            return 1;
        }))));
        command.then(Commands.m_82127_((String)"stop").then(((RequiredArgumentBuilder)Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"quest", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Quest quest = QuestController.instance.quests.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"quest"));
            if (quest == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown QuestID"));
            }
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                data.questData.activeQuests.remove(quest.id);
                data.save(true);
            }
            return 1;
        }))));
        command.then(Commands.m_82127_((String)"remove").then(((RequiredArgumentBuilder)Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"quest", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Quest quest = QuestController.instance.quests.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"quest"));
            if (quest == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown QuestID"));
            }
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                data.questData.activeQuests.remove(quest.id);
                data.questData.finishedQuests.remove(quest.id);
                data.save(true);
            }
            return 1;
        }))));
        command.then(Commands.m_82127_((String)"objective").then(((RequiredArgumentBuilder)Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"quest", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Quest quest = QuestController.instance.quests.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"quest"));
            if (quest == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown QuestID"));
            }
            for (ServerPlayer player : players) {
                IQuestObjective[] objectives;
                PlayerData data = PlayerData.get((Player)player);
                if (!data.questData.activeQuests.containsKey(quest.id)) continue;
                for (IQuestObjective ob : objectives = quest.questInterface.getObjectives((Player)player)) {
                    player.m_213846_(ob.getMCText());
                }
            }
            return 1;
        })).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"objective", (ArgumentType)IntegerArgumentType.integer((int)0, (int)3)).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Quest quest = QuestController.instance.quests.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"quest"));
            if (quest == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown QuestID"));
            }
            int objective = IntegerArgumentType.getInteger((CommandContext)context, (String)"objective");
            for (ServerPlayer player : players) {
                IQuestObjective[] objectives;
                PlayerData data = PlayerData.get((Player)player);
                if (!data.questData.activeQuests.containsKey(quest.id) || objective >= (objectives = quest.questInterface.getObjectives((Player)player)).length) continue;
                player.m_213846_(objectives[objective].getMCText());
            }
            return 1;
        })).then(Commands.m_82129_((String)"value", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Quest quest = QuestController.instance.quests.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"quest"));
            if (quest == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown QuestID"));
            }
            int objective = IntegerArgumentType.getInteger((CommandContext)context, (String)"objective");
            int value = IntegerArgumentType.getInteger((CommandContext)context, (String)"value");
            for (ServerPlayer player : players) {
                IQuestObjective[] objectives;
                PlayerData data = PlayerData.get((Player)player);
                if (!data.questData.activeQuests.containsKey(quest.id) || objective >= (objectives = quest.questInterface.getObjectives((Player)player)).length) continue;
                objectives[objective].setProgress(value);
            }
            return 1;
        }))))));
        ((LiteralArgumentBuilder)command.requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82127_((String)"reload").executes(context -> {
            new QuestController().load();
            SyncController.syncAllQuests();
            return 1;
        }));
        return command;
    }
}

