/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.controllers.LinkedNpcController;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketLinkedSet
extends PacketServerBasic {
    private String name;

    public SPacketLinkedSet(String name) {
        this.name = name;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_ADVANCED;
    }

    public static void encode(SPacketLinkedSet msg, FriendlyByteBuf buf) {
        buf.m_130072_(msg.name, Short.MAX_VALUE);
    }

    public static SPacketLinkedSet decode(FriendlyByteBuf buf) {
        return new SPacketLinkedSet(buf.m_130136_(Short.MAX_VALUE));
    }

    @Override
    protected void handle() {
        this.npc.linkedName = this.name;
        LinkedNpcController.Instance.loadNpcData(this.npc);
    }
}

