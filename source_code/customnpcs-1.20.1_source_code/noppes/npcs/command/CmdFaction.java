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
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerFactionData;

public class CmdFaction {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"faction").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_((String)"faction", (ArgumentType)IntegerArgumentType.integer((int)0)).then(Commands.m_82127_((String)"add").then(Commands.m_82129_((String)"points", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Faction faction = FactionController.instance.factions.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"faction"));
            if (faction == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown FactionID"));
            }
            int points = IntegerArgumentType.getInteger((CommandContext)context, (String)"points");
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                PlayerFactionData playerfactiondata = data.factionData;
                playerfactiondata.increasePoints((Player)player, faction.id, points);
                data.save(true);
            }
            return 1;
        })))).then(Commands.m_82127_((String)"set").then(Commands.m_82129_((String)"points", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Faction faction = FactionController.instance.factions.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"faction"));
            if (faction == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown FactionID"));
            }
            int points = IntegerArgumentType.getInteger((CommandContext)context, (String)"points");
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                PlayerFactionData playerfactiondata = data.factionData;
                playerfactiondata.factionData.put(faction.id, points);
                data.save(true);
            }
            return 1;
        })))).then(Commands.m_82127_((String)"reset").executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Faction faction = FactionController.instance.factions.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"faction"));
            if (faction == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown FactionID"));
            }
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                data.factionData.factionData.put(faction.id, faction.defaultPoints);
                data.save(true);
            }
            return 1;
        }))).then(Commands.m_82127_((String)"drop").executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Faction faction = FactionController.instance.factions.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"faction"));
            if (faction == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown FactionID"));
            }
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                data.factionData.factionData.remove(faction.id);
                data.save(true);
            }
            return 1;
        }))));
        return command;
    }
}

