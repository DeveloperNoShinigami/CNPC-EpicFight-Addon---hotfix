/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandRuntimeException
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.coordinates.BlockPosArgument
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomNpcs;
import noppes.npcs.command.CmdNoppes;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.entity.EntityNPCInterface;

public class CmdClone {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = Commands.m_82127_((String)"clone");
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"list").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"tab", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            int tab = IntegerArgumentType.getInteger((CommandContext)context, (String)"tab");
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"--- Stored NPCs --- (server side)"), false);
            for (String name : ServerCloneController.Instance.getClones(tab)) {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)name), false);
            }
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"------------------------------------"), false);
            return 1;
        })));
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"add").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"npc", (ArgumentType)StringArgumentType.string()).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"tab", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            CmdClone.addClone((CommandContext<CommandSourceStack>)context, "");
            return 1;
        })).then(Commands.m_82129_((String)"name", (ArgumentType)StringArgumentType.string()).executes(context -> {
            CmdClone.addClone((CommandContext<CommandSourceStack>)context, StringArgumentType.getString((CommandContext)context, (String)"name"));
            return 1;
        })))));
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"remove").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"npc", (ArgumentType)StringArgumentType.string()).then(Commands.m_82129_((String)"tab", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            String nametodel = StringArgumentType.getString((CommandContext)context, (String)"npc");
            int tab = IntegerArgumentType.getInteger((CommandContext)context, (String)"tab");
            boolean deleted = false;
            for (String name : ServerCloneController.Instance.getClones(tab)) {
                if (!nametodel.equalsIgnoreCase(name)) continue;
                ServerCloneController.Instance.removeClone(name, tab);
                deleted = true;
                break;
            }
            if (!deleted) {
                throw new CommandRuntimeException((Component)Component.m_237110_((String)"Npc '%s' wasn't found", (Object[])new Object[]{nametodel}));
            }
            return 1;
        }))));
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"spawn").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"npc", (ArgumentType)StringArgumentType.string()).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"tab", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            CmdClone.spawnClone((CommandContext<CommandSourceStack>)context, new BlockPos((int)((CommandSourceStack)context.getSource()).m_81371_().f_82479_, (int)((CommandSourceStack)context.getSource()).m_81371_().f_82480_, (int)((CommandSourceStack)context.getSource()).m_81371_().f_82481_), "");
            return 1;
        })).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"pos", (ArgumentType)BlockPosArgument.m_118239_()).executes(context -> {
            CmdClone.spawnClone((CommandContext<CommandSourceStack>)context, BlockPosArgument.m_118242_((CommandContext)context, (String)"pos"), "");
            return 1;
        })).then(Commands.m_82129_((String)"display_name", (ArgumentType)StringArgumentType.string()).executes(context -> {
            CmdClone.spawnClone((CommandContext<CommandSourceStack>)context, BlockPosArgument.m_118242_((CommandContext)context, (String)"pos"), StringArgumentType.getString((CommandContext)context, (String)"display_name"));
            return 1;
        }))))));
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"grid").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"npc", (ArgumentType)StringArgumentType.string()).then(Commands.m_82129_((String)"tab", (ArgumentType)IntegerArgumentType.integer((int)0)).then(Commands.m_82129_((String)"length", (ArgumentType)IntegerArgumentType.integer()).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"width", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
            int length = IntegerArgumentType.getInteger((CommandContext)context, (String)"length");
            int width = IntegerArgumentType.getInteger((CommandContext)context, (String)"width");
            for (int x = 0; x < length; ++x) {
                for (int z = 0; z < width; ++z) {
                    CmdClone.spawnClone((CommandContext<CommandSourceStack>)context, new BlockPos((int)((CommandSourceStack)context.getSource()).m_81371_().f_82479_, (int)((CommandSourceStack)context.getSource()).m_81371_().f_82480_, (int)((CommandSourceStack)context.getSource()).m_81371_().f_82481_).m_7918_(length, 0, width), "");
                }
            }
            return 1;
        })).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"pos", (ArgumentType)BlockPosArgument.m_118239_()).executes(context -> {
            int length = IntegerArgumentType.getInteger((CommandContext)context, (String)"length");
            int width = IntegerArgumentType.getInteger((CommandContext)context, (String)"width");
            for (int x = 0; x < length; ++x) {
                for (int z = 0; z < width; ++z) {
                    CmdClone.spawnClone((CommandContext<CommandSourceStack>)context, BlockPosArgument.m_118242_((CommandContext)context, (String)"pos").m_7918_(length, 0, width), "");
                }
            }
            return 1;
        })).then(Commands.m_82129_((String)"display_name", (ArgumentType)StringArgumentType.string()).executes(context -> {
            int length = IntegerArgumentType.getInteger((CommandContext)context, (String)"length");
            int width = IntegerArgumentType.getInteger((CommandContext)context, (String)"width");
            for (int x = 0; x < length; ++x) {
                for (int z = 0; z < width; ++z) {
                    CmdClone.spawnClone((CommandContext<CommandSourceStack>)context, BlockPosArgument.m_118242_((CommandContext)context, (String)"pos").m_7918_(length, 0, width), StringArgumentType.getString((CommandContext)context, (String)"display_name"));
                }
            }
            return 1;
        }))))))));
        return command;
    }

    private static void addClone(CommandContext<CommandSourceStack> context, String newName) {
        CompoundTag compound;
        String name = StringArgumentType.getString(context, (String)"npc");
        if (newName.isEmpty()) {
            newName = name;
        }
        int tab = IntegerArgumentType.getInteger(context, (String)"tab");
        List<EntityNPCInterface> list = CmdNoppes.getNpcsByName(((CommandSourceStack)context.getSource()).m_81372_(), name);
        if (list.isEmpty()) {
            return;
        }
        EntityNPCInterface npc = list.get(0);
        if (!npc.m_20086_(compound = new CompoundTag())) {
            return;
        }
        ServerCloneController.Instance.addClone(compound, newName, tab);
    }

    private static void spawnClone(CommandContext<CommandSourceStack> context, BlockPos pos, String newName) {
        String name = StringArgumentType.getString(context, (String)"npc").replaceAll("%", " ");
        int tab = IntegerArgumentType.getInteger(context, (String)"tab");
        CompoundTag compound = ServerCloneController.Instance.getCloneData((CommandSourceStack)context.getSource(), name, tab);
        if (compound == null) {
            throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown npc"));
        }
        if (pos == BlockPos.f_121853_) {
            throw new CommandRuntimeException((Component)Component.m_237113_((String)"Location needed"));
        }
        ServerLevel world = ((CommandSourceStack)context.getSource()).m_81372_();
        Entity entity = (Entity)EntityType.m_20642_((CompoundTag)compound, (Level)world).get();
        entity.m_6034_((double)pos.m_123341_() + 0.5, (double)(pos.m_123342_() + 1), (double)pos.m_123343_() + 0.5);
        if (entity instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)entity;
            npc.ais.setStartPos(pos);
            if (!newName.isEmpty()) {
                npc.display.setName(newName.replaceAll("%", " "));
            }
        }
        world.m_7967_(entity);
    }
}

