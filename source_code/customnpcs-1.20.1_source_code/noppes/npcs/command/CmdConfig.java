/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.ChunkController;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketConfigFont;

public class CmdConfig {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = Commands.m_82127_((String)"config");
        command.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"leavesdecay").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("LeavesDecay: " + CustomNpcs.LeavesDecayEnabled)), false);
            return 1;
        })).then(Commands.m_82129_((String)"boolean", (ArgumentType)BoolArgumentType.bool()).executes(context -> {
            CustomNpcs.LeavesDecayEnabled = BoolArgumentType.getBool((CommandContext)context, (String)"boolean");
            CustomNpcs.Config.updateConfig();
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("LeavesDecay: " + CustomNpcs.LeavesDecayEnabled)), false);
            return 1;
        })));
        command.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"vineinflateth").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("VineGrowth: " + CustomNpcs.VineGrowthEnabled)), false);
            return 1;
        })).then(Commands.m_82129_((String)"boolean", (ArgumentType)BoolArgumentType.bool()).executes(context -> {
            CustomNpcs.VineGrowthEnabled = BoolArgumentType.getBool((CommandContext)context, (String)"boolean");
            CustomNpcs.Config.updateConfig();
            Set names = ForgeRegistries.BLOCKS.getKeys();
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("VineGrowth: " + CustomNpcs.VineGrowthEnabled)), false);
            return 1;
        })));
        command.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"icemelts").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("IceMelts: " + CustomNpcs.IceMeltsEnabled)), false);
            return 1;
        })).then(Commands.m_82129_((String)"boolean", (ArgumentType)BoolArgumentType.bool()).executes(context -> {
            CustomNpcs.IceMeltsEnabled = BoolArgumentType.getBool((CommandContext)context, (String)"boolean");
            CustomNpcs.Config.updateConfig();
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("IceMelts: " + CustomNpcs.IceMeltsEnabled)), false);
            return 1;
        })));
        command.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"freezenpcs").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Frozen NPCs: " + CustomNpcs.FreezeNPCs)), false);
            return 1;
        })).then(Commands.m_82129_((String)"boolean", (ArgumentType)BoolArgumentType.bool()).executes(context -> {
            CustomNpcs.FreezeNPCs = BoolArgumentType.getBool((CommandContext)context, (String)"boolean");
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Frozen NPCs: " + CustomNpcs.FreezeNPCs)), false);
            return 1;
        })));
        command.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"debug").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Verbose debug is " + CustomNpcs.VerboseDebug)), false);
            return 1;
        })).then(Commands.m_82129_((String)"boolean", (ArgumentType)BoolArgumentType.bool()).executes(context -> {
            CustomNpcs.VerboseDebug = BoolArgumentType.getBool((CommandContext)context, (String)"boolean");
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Verbose debug is now" + CustomNpcs.VerboseDebug)), false);
            return 1;
        })));
        command.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"scripting").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Scripting is " + CustomNpcs.EnableScripting)), false);
            return 1;
        })).then(Commands.m_82129_((String)"boolean", (ArgumentType)BoolArgumentType.bool()).executes(context -> {
            CustomNpcs.EnableScripting = BoolArgumentType.getBool((CommandContext)context, (String)"boolean");
            CustomNpcs.Config.updateConfig();
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Scripting is now" + CustomNpcs.EnableScripting)), false);
            return 1;
        })));
        command.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"chunkloaders").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("ChunkLoaders: " + ChunkController.instance.size() + "/" + CustomNpcs.ChuckLoaders)), false);
            return 1;
        })).then(Commands.m_82129_((String)"number", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            CustomNpcs.ChuckLoaders = IntegerArgumentType.getInteger((CommandContext)context, (String)"number");
            CustomNpcs.Config.updateConfig();
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Max ChunkLoaders: " + CustomNpcs.ChuckLoaders)), false);
            return 1;
        })));
        command.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"font").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            Packets.send(((CommandSourceStack)context.getSource()).m_81375_(), new PacketConfigFont("", 0));
            return 1;
        })).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"font", (ArgumentType)StringArgumentType.string()).executes(context -> {
            Packets.send(((CommandSourceStack)context.getSource()).m_81375_(), new PacketConfigFont(StringArgumentType.getString((CommandContext)context, (String)"font"), 18));
            return 1;
        })).then(Commands.m_82129_((String)"size", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Packets.send(((CommandSourceStack)context.getSource()).m_81375_(), new PacketConfigFont(StringArgumentType.getString((CommandContext)context, (String)"font"), IntegerArgumentType.getInteger((CommandContext)context, (String)"size")));
            return 1;
        }))));
        return command;
    }
}

