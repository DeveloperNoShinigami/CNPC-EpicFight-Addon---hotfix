/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandRuntimeException
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.SyncController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityDialogNpc;

public class CmdDialog {
    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = Commands.m_82127_((String)"dialog");
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"reload").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).executes(context -> {
            new DialogController().load();
            SyncController.syncAllDialogs();
            return 1;
        }));
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"read").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).then(Commands.m_82129_((String)"dialog", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Dialog dialog = DialogController.instance.dialogs.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"dialog"));
            if (dialog == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown DialogID"));
            }
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                if (data.dialogData.dialogsRead.contains(dialog.id)) continue;
                data.dialogData.dialogsRead.add(dialog.id);
                data.save(true);
            }
            return 1;
        }))));
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"unread").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).then(Commands.m_82129_((String)"dialog", (ArgumentType)IntegerArgumentType.integer((int)0)).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Dialog dialog = DialogController.instance.dialogs.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"dialog"));
            if (dialog == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown DialogID"));
            }
            for (ServerPlayer player : players) {
                PlayerData data = PlayerData.get((Player)player);
                if (!data.dialogData.dialogsRead.contains(dialog.id)) continue;
                data.dialogData.dialogsRead.remove(dialog.id);
                data.save(true);
            }
            return 1;
        }))));
        command.then(((LiteralArgumentBuilder)Commands.m_82127_((String)"show").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"players", (ArgumentType)EntityArgument.m_91470_()).then(Commands.m_82129_((String)"dialog", (ArgumentType)IntegerArgumentType.integer((int)0)).then(Commands.m_82129_((String)"name", (ArgumentType)StringArgumentType.string()).executes(context -> {
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"players");
            if (players.isEmpty()) {
                return 1;
            }
            Dialog dialog = DialogController.instance.dialogs.get(IntegerArgumentType.getInteger((CommandContext)context, (String)"dialog"));
            if (dialog == null) {
                throw new CommandRuntimeException((Component)Component.m_237113_((String)"Unknown DialogID"));
            }
            EntityDialogNpc npc = new EntityDialogNpc((Level)((CommandSourceStack)context.getSource()).m_81372_());
            DialogOption option = new DialogOption();
            option.dialogId = dialog.id;
            option.title = dialog.title;
            npc.dialogs.put(0, option);
            npc.display.setName(StringArgumentType.getString((CommandContext)context, (String)"name"));
            for (ServerPlayer player : players) {
                EntityUtil.Copy((LivingEntity)player, (LivingEntity)npc);
                NoppesUtilServer.openDialog((Player)player, npc, dialog);
            }
            return 1;
        })))));
        return command;
    }
}

