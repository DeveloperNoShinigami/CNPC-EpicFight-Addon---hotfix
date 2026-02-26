/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketNpcDelete;

public class SPacketNpcDelete
extends PacketServerBasic {
    @Override
    public boolean requiresNpc() {
        return true;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_DELETE;
    }

    public static void encode(SPacketNpcDelete msg, FriendlyByteBuf buf) {
    }

    public static SPacketNpcDelete decode(FriendlyByteBuf buf) {
        return new SPacketNpcDelete();
    }

    @Override
    protected void handle() {
        Packets.sendNearby((Entity)this.npc, new PacketNpcDelete(this.npc.m_19879_()));
        this.npc.delete();
    }
}

