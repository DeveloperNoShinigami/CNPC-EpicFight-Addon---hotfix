/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.commands.arguments.coordinates.BlockPosArgument
 *  net.minecraft.commands.synchronization.SuggestionProviders
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.CustomEntities;
import noppes.npcs.CustomNpcs;
import noppes.npcs.command.CmdNoppes;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleFollower;

public class CmdNPC {
    public static final SuggestionProvider<CommandSourceStack> VISIBLE = SuggestionProviders.m_121658_((ResourceLocation)new ResourceLocation("visible"), (context, builder) -> SharedSuggestionProvider.m_82967_((String[])new String[]{"true", "false", "semi"}, (SuggestionsBuilder)builder));

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"npc").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_((String)"npc", (ArgumentType)StringArgumentType.string()).then(Commands.m_82127_((String)"home").then(Commands.m_82129_((String)"pos", (ArgumentType)BlockPosArgument.m_118239_()).executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"npc");
            List<EntityNPCInterface> npcs = CmdNoppes.getNpcsByName(((CommandSourceStack)context.getSource()).m_81372_(), name);
            if (!npcs.isEmpty()) {
                npcs.get((int)0).ais.setStartPos(BlockPosArgument.m_118242_((CommandContext)context, (String)"pos"));
            }
            return 1;
        })))).then(Commands.m_82127_((String)"visible").then(Commands.m_82129_((String)"visibility", (ArgumentType)StringArgumentType.word()).suggests(VISIBLE).executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"npc");
            List<EntityNPCInterface> npcs = CmdNoppes.getNpcsByName(((CommandSourceStack)context.getSource()).m_81372_(), name);
            String val = StringArgumentType.getString((CommandContext)context, (String)"visibility");
            int vis = 0;
            if (val.equalsIgnoreCase("false")) {
                vis = 1;
            } else if (val.equalsIgnoreCase("semi")) {
                vis = 2;
            }
            for (EntityNPCInterface npc : npcs) {
                npc.display.setVisible(vis);
            }
            return 1;
        })))).then(Commands.m_82127_((String)"delete").executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"npc");
            List<EntityNPCInterface> npcs = CmdNoppes.getNpcsByName(((CommandSourceStack)context.getSource()).m_81372_(), name);
            for (EntityNPCInterface npc : npcs) {
                npc.delete();
            }
            return 1;
        }))).then(((LiteralArgumentBuilder)Commands.m_82127_((String)"owner").executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"npc");
            List<EntityNPCInterface> npcs = CmdNoppes.getNpcsByName(((CommandSourceStack)context.getSource()).m_81372_(), name);
            for (EntityNPCInterface npc : npcs) {
                LivingEntity owner = npc.getOwner();
                if (owner == null) {
                    ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)"No owner"), false);
                    continue;
                }
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_((String)("Owner is: " + owner.m_7755_())), false);
            }
            return 1;
        })).then(Commands.m_82129_((String)"player", (ArgumentType)EntityArgument.m_91466_()).executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"npc");
            List<EntityNPCInterface> npcs = CmdNoppes.getNpcsByName(((CommandSourceStack)context.getSource()).m_81372_(), name);
            ServerPlayer player = EntityArgument.m_91474_((CommandContext)context, (String)"player");
            for (EntityNPCInterface npc : npcs) {
                if (npc.role instanceof RoleFollower) {
                    ((RoleFollower)npc.role).setOwner((Player)player);
                }
                if (!(npc.role instanceof RoleCompanion)) continue;
                ((RoleCompanion)npc.role).setOwner((Player)player);
            }
            return 1;
        })))).then(Commands.m_82127_((String)"delete").then(Commands.m_82129_((String)"name", (ArgumentType)StringArgumentType.greedyString()).executes(context -> {
            List<EntityNPCInterface> npcs = CmdNoppes.getNpcsByName(((CommandSourceStack)context.getSource()).m_81372_(), StringArgumentType.getString((CommandContext)context, (String)"npc"));
            String name = StringArgumentType.getString((CommandContext)context, (String)"name");
            for (EntityNPCInterface npc : npcs) {
                npc.display.setName(name);
                npc.updateClient = true;
            }
            return 1;
        })))).then(Commands.m_82127_((String)"reset").executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"npc");
            List<EntityNPCInterface> npcs = CmdNoppes.getNpcsByName(((CommandSourceStack)context.getSource()).m_81372_(), name);
            for (EntityNPCInterface npc : npcs) {
                npc.reset();
            }
            return 1;
        }))).then(Commands.m_82127_((String)"create").executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"npc");
            ServerLevel pw = ((CommandSourceStack)context.getSource()).m_81372_();
            EntityCustomNpc npc = new EntityCustomNpc(CustomEntities.entityCustomNpc, (Level)pw);
            npc.display.setName(name);
            Vec3 pos = ((CommandSourceStack)context.getSource()).m_81371_();
            npc.m_19890_(pos.f_82479_, pos.f_82480_, pos.f_82481_, 0.0f, 0.0f);
            npc.ais.setStartPos(new BlockPos((int)pos.f_82479_, (int)pos.f_82480_, (int)pos.f_82481_));
            pw.m_7967_((Entity)npc);
            npc.m_21153_(npc.m_21233_());
            return 1;
        })));
        return command;
    }
}

