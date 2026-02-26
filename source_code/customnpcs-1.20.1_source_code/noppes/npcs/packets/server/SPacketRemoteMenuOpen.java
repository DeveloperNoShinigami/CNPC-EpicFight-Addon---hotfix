/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketRemoteMenuOpen
extends PacketServerBasic {
    private int entityId;

    public SPacketRemoteMenuOpen(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_GUI;
    }

    public static void encode(SPacketRemoteMenuOpen msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
    }

    public static SPacketRemoteMenuOpen decode(FriendlyByteBuf buf) {
        return new SPacketRemoteMenuOpen(buf.readInt());
    }

    @Override
    protected void handle() {
        Entity entity = this.player.m_9236_().m_6815_(this.entityId);
        if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
        }
        NoppesUtilServer.sendOpenGui((Player)this.player, EnumGuiType.MainMenuDisplay, (EntityNPCInterface)entity);
    }
}

