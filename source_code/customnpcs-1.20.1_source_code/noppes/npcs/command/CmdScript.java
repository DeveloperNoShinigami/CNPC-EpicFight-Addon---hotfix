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
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EventHooks;
import noppes.npcs.api.IPos;
import noppes.npcs.api.IWorld;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.controllers.ScriptController;

public class CmdScript {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"script").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82127_((String)"reload").executes(context -> {
            ScriptController.Instance.loadCategories();
            if (ScriptController.Instance.loadPlayerScripts()) {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Reload player scripts succesfully"), false);
            } else {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Failed reloading player scripts"), false);
            }
            if (ScriptController.Instance.loadForgeScripts()) {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Reload forge scripts succesfully"), false);
            } else {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Failed reloading forge scripts"), false);
            }
            if (ScriptController.Instance.loadStoredData()) {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Reload stored data succesfully"), false);
            } else {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"Failed reloading stored data"), false);
            }
            return 1;
        }))).then(Commands.m_82127_((String)"trigger").then(((RequiredArgumentBuilder)Commands.m_82129_((String)"id", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            IWorld level = NpcAPI.Instance().getIWorld(((CommandSourceStack)context.getSource()).m_81372_());
            Vec3 bpos = ((CommandSourceStack)context.getSource()).m_81371_();
            IPos pos = NpcAPI.Instance().getIPos(bpos.f_82479_, bpos.f_82480_, bpos.f_82481_);
            int id = IntegerArgumentType.getInteger((CommandContext)context, (String)"id");
            IEntity e = NpcAPI.Instance().getIEntity(((CommandSourceStack)context.getSource()).m_81373_());
            EventHooks.onScriptTriggerEvent(id, level, pos, e, new String[0]);
            return 1;
        })).then(Commands.m_82129_((String)"args", (ArgumentType)StringArgumentType.greedyString()).executes(context -> {
            IWorld level = NpcAPI.Instance().getIWorld(((CommandSourceStack)context.getSource()).m_81372_());
            Vec3 bpos = ((CommandSourceStack)context.getSource()).m_81371_();
            IPos pos = NpcAPI.Instance().getIPos(bpos.f_82479_, bpos.f_82480_, bpos.f_82481_);
            IEntity e = NpcAPI.Instance().getIEntity(((CommandSourceStack)context.getSource()).m_81373_());
            int id = IntegerArgumentType.getInteger((CommandContext)context, (String)"id");
            EventHooks.onScriptTriggerEvent(id, level, pos, e, StringArgumentType.getString((CommandContext)context, (String)"args").split(" "));
            return 1;
        }))));
        return command;
    }
}

