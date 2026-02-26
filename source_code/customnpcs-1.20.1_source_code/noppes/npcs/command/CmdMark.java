/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.data.MarkData;

public class CmdMark {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)Commands.m_82127_((String)"mark").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2));
        command.then(Commands.m_82129_((String)"clear", (ArgumentType)EntityArgument.m_91460_()).executes(context -> {
            Collection entities = EntityArgument.m_91461_((CommandContext)context, (String)"entities");
            for (Entity entity : entities) {
                if (!(entity instanceof LivingEntity)) continue;
                MarkData data = MarkData.get((LivingEntity)entity);
                data.marks.clear();
                data.syncClients();
            }
            return 1;
        }));
        command.then(Commands.m_82129_((String)"entities", (ArgumentType)EntityArgument.m_91460_()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_((String)"type", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Collection entities = EntityArgument.m_91461_((CommandContext)context, (String)"entities");
            if (entities.isEmpty()) {
                return 1;
            }
            int type = IntegerArgumentType.getInteger((CommandContext)context, (String)"type");
            for (Entity entity : entities) {
                if (!(entity instanceof LivingEntity)) continue;
                MarkData data = MarkData.get((LivingEntity)entity);
                data.marks.clear();
                data.addMark(type, 0xFFFFFF);
            }
            return 1;
        })).then((ArgumentBuilder)Commands.m_82129_((String)"color", (ArgumentType)StringArgumentType.word()))).executes(context -> {
            Collection entities = EntityArgument.m_91461_((CommandContext)context, (String)"entities");
            if (entities.isEmpty()) {
                return 1;
            }
            int type = IntegerArgumentType.getInteger((CommandContext)context, (String)"type");
            int color = 0xFFFFFF;
            try {
                color = Integer.parseInt(StringArgumentType.getString((CommandContext)context, (String)"color"), 16);
            }
            catch (Exception exception) {
                // empty catch block
            }
            for (Entity entity : entities) {
                if (!(entity instanceof LivingEntity)) continue;
                MarkData data = MarkData.get((LivingEntity)entity);
                data.marks.clear();
                data.addMark(type, color);
            }
            return 1;
        })));
        return command;
    }
}

