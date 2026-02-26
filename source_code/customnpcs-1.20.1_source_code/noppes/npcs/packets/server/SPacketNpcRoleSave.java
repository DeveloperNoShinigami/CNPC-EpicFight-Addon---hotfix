/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketNpcRoleSave
extends PacketServerBasic {
    private CompoundTag data;

    public SPacketNpcRoleSave(CompoundTag data) {
        this.data = data;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_ADVANCED;
    }

    public static void encode(SPacketNpcRoleSave msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static SPacketNpcRoleSave decode(FriendlyByteBuf buf) {
        return new SPacketNpcRoleSave(buf.m_130260_());
    }

    @Override
    protected void handle() {
        this.npc.role.load(this.data);
        this.npc.updateClient = true;
    }
}

