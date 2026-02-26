/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerGamePacketListener
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.network.ServerGamePacketListenerImpl
 *  net.minecraft.util.thread.BlockableEventLoop
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.util.LogicalSidedProvider
 *  net.minecraftforge.fml.LogicalSide
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package noppes.npcs.packets;

import java.util.concurrent.CompletableFuture;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.common.util.LogWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class IPacketServer
implements Packet<ServerGamePacketListener> {
    private static final Logger LOGGER = LogManager.getLogger();
    public ServerPlayer player;
    public EntityNPCInterface npc;

    public void handle(ServerGamePacketListener handler) {
        this.enqueueWork(() -> {
            try {
                this.player = ((ServerGamePacketListenerImpl)handler).f_9743_;
                this.npc = NoppesUtilServer.getEditingNpc((Player)this.player);
                if (this.requiresNpc() && this.npc == null) {
                    return;
                }
                if (this.getPermission() != null && !CustomNpcsPermissions.hasPermission(this.player, this.getPermission())) {
                    return;
                }
                if (!this.toolAllowed(this.player.m_150109_().m_36056_())) {
                    this.warn("tried to use custom npcs without a tool in hand, possibly a hacker");
                    return;
                }
                this.handle();
            }
            catch (Throwable e) {
                LogWriter.except(e);
                throw e;
            }
        });
    }

    public boolean requiresNpc() {
        return false;
    }

    public PermissionNode<Boolean> getPermission() {
        return null;
    }

    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.wand;
    }

    public abstract void handle();

    private void warn(String warning) {
        LOGGER.warn(this.player.m_7755_().getString() + ": " + warning + " - " + this);
    }

    public CompletableFuture<Void> enqueueWork(Runnable runnable) {
        BlockableEventLoop executor = (BlockableEventLoop)LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
        if (!executor.m_18695_()) {
            return executor.m_18689_(runnable);
        }
        runnable.run();
        return CompletableFuture.completedFuture(null);
    }
}

