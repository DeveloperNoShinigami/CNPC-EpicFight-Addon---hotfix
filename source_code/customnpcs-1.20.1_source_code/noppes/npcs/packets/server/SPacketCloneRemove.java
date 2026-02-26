/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.server.SPacketCloneList;

public class SPacketCloneRemove
extends PacketServerBasic {
    private String name;
    private int tab;

    public SPacketCloneRemove(String name, int tab) {
        this.name = name;
        this.tab = tab;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.cloner;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_CLONE;
    }

    public static void encode(SPacketCloneRemove msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.name);
        buf.writeInt(msg.tab);
    }

    public static SPacketCloneRemove decode(FriendlyByteBuf buf) {
        return new SPacketCloneRemove(buf.m_130136_(Short.MAX_VALUE), buf.readInt());
    }

    @Override
    protected void handle() {
        ServerCloneController.Instance.removeClone(this.name, this.tab);
        SPacketCloneList.sendList(this.player, this.tab);
    }
}

