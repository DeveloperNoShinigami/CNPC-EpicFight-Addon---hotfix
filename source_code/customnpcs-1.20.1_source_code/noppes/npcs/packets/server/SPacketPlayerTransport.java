/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.roles.RoleTransporter;

public class SPacketPlayerTransport
extends PacketServerBasic {
    private final String name;

    public SPacketPlayerTransport(String name) {
        this.name = name;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    public static void encode(SPacketPlayerTransport msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.name);
    }

    public static SPacketPlayerTransport decode(FriendlyByteBuf buf) {
        return new SPacketPlayerTransport(buf.m_130136_(Short.MAX_VALUE));
    }

    @Override
    protected void handle() {
        if (this.npc.role.getType() != 4) {
            return;
        }
        ((RoleTransporter)this.npc.role).transport(this.player, this.name);
    }
}

