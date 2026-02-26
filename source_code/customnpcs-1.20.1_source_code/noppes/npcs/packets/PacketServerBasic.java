/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.network.NetworkEvent$Context
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package noppes.npcs.packets;

import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.entity.EntityNPCInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PacketServerBasic {
    private static final Logger LOGGER = LogManager.getLogger();
    public ServerPlayer player;
    public EntityNPCInterface npc;

    public boolean requiresNpc() {
        return false;
    }

    public PermissionNode<Boolean> getPermission() {
        return null;
    }

    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.wand;
    }

    public static void handle(PacketServerBasic msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            msg.player = ((NetworkEvent.Context)ctx.get()).getSender();
            msg.npc = NoppesUtilServer.getEditingNpc((Player)msg.player);
            if (msg.requiresNpc() && msg.npc == null) {
                return;
            }
            if (msg.getPermission() != null && !CustomNpcsPermissions.hasPermission(msg.player, msg.getPermission())) {
                return;
            }
            if (!msg.toolAllowed(msg.player.m_150109_().m_36056_())) {
                msg.warn("tried to use custom npcs without a tool in hand, possibly a hacker");
                return;
            }
            msg.handle();
        });
        ctx.get().setPacketHandled(true);
    }

    private void warn(String warning) {
        LOGGER.warn(this.player.m_7755_().getString() + ": " + warning + " - " + this);
    }

    protected abstract void handle();
}

