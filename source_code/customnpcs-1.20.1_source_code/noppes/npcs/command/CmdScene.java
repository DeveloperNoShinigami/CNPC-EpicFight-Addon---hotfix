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
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Map;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import noppes.npcs.CustomNpcs;
import noppes.npcs.entity.data.DataScenes;

public class CmdScene {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"scene").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(((LiteralArgumentBuilder)Commands.m_82127_((String)"time").executes(context -> {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Active scenes:"), false);
            for (Map.Entry<String, DataScenes.SceneState> entry : DataScenes.StartedScenes.entrySet()) {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237110_((String)"Scene %s time is %s", (Object[])new Object[]{entry.getKey(), ((DataScenes.SceneState)entry.getValue()).ticks}), false);
            }
            return 1;
        })).then(((RequiredArgumentBuilder)Commands.m_82129_((String)"time", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            int ticks = IntegerArgumentType.getInteger((CommandContext)context, (String)"time");
            for (DataScenes.SceneState state : DataScenes.StartedScenes.values()) {
                state.ticks = ticks;
            }
            return 1;
        })).then(Commands.m_82129_((String)"name", (ArgumentType)StringArgumentType.string()).executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"name");
            DataScenes.SceneState state = DataScenes.StartedScenes.get(name.toLowerCase());
            if (state == null) {
                throw new CommandRuntimeException((Component)Component.m_237110_((String)"Unknown scene name %s", (Object[])new Object[]{name}));
            }
            state.ticks = IntegerArgumentType.getInteger((CommandContext)context, (String)"time");
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237110_((String)"Scene %s set to %s", (Object[])new Object[]{name, state.ticks}), false);
            return 1;
        }))))).then(((LiteralArgumentBuilder)Commands.m_82127_((String)"reset").executes(context -> {
            DataScenes.Reset((CommandSourceStack)context.getSource(), null);
            return 1;
        })).then(Commands.m_82129_((String)"name", (ArgumentType)StringArgumentType.string()).executes(context -> {
            DataScenes.Reset((CommandSourceStack)context.getSource(), StringArgumentType.getString((CommandContext)context, (String)"name"));
            return 1;
        })))).then(Commands.m_82127_((String)"start").then(Commands.m_82129_((String)"name", (ArgumentType)StringArgumentType.string()).executes(context -> {
            DataScenes.Start(((CommandSourceStack)context.getSource()).m_81377_(), StringArgumentType.getString((CommandContext)context, (String)"name"));
            return 1;
        })))).then(((LiteralArgumentBuilder)Commands.m_82127_((String)"pause").executes(context -> {
            DataScenes.Pause((CommandSourceStack)context.getSource(), null);
            return 1;
        })).then(Commands.m_82129_((String)"name", (ArgumentType)StringArgumentType.string()).executes(context -> {
            DataScenes.Pause((CommandSourceStack)context.getSource(), StringArgumentType.getString((CommandContext)context, (String)"name"));
            return 1;
        })));
        return command;
    }
}

