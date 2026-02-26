/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 */
package noppes.npcs.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import noppes.npcs.CustomEntities;
import noppes.npcs.command.CmdClone;
import noppes.npcs.command.CmdConfig;
import noppes.npcs.command.CmdDialog;
import noppes.npcs.command.CmdFaction;
import noppes.npcs.command.CmdMark;
import noppes.npcs.command.CmdNPC;
import noppes.npcs.command.CmdQuest;
import noppes.npcs.command.CmdScene;
import noppes.npcs.command.CmdSchematics;
import noppes.npcs.command.CmdScript;
import noppes.npcs.command.CmdSlay;
import noppes.npcs.entity.EntityNPCInterface;

public class CmdNoppes {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"noppes").requires(p_198816_0_ -> p_198816_0_.m_6761_(2))).then(CmdClone.register())).then(CmdConfig.register())).then(CmdDialog.register())).then(CmdFaction.register())).then(CmdMark.register())).then(CmdNPC.register())).then(CmdQuest.register())).then(CmdScene.register())).then(CmdSchematics.register())).then(CmdScript.register())).then(CmdSlay.register()));
    }

    public static List<EntityNPCInterface> getNpcsByName(ServerLevel level, String name) {
        return level.m_143280_(CustomEntities.entityCustomNpc, npc -> npc.display.getName().equalsIgnoreCase(name));
    }

    public static <T extends Entity> List<T> getEntities(EntityType<T> type, ServerLevel level) {
        return level.m_143280_(type, entity -> true);
    }
}

