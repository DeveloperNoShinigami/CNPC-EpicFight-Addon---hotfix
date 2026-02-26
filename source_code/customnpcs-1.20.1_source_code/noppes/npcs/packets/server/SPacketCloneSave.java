/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketCloneSave
extends PacketServerBasic {
    private String name;
    private int tab;

    public SPacketCloneSave(String name, int tab) {
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

    public static void encode(SPacketCloneSave msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.name);
        buf.writeInt(msg.tab);
    }

    public static SPacketCloneSave decode(FriendlyByteBuf buf) {
        return new SPacketCloneSave(buf.m_130136_(Short.MAX_VALUE), buf.readInt());
    }

    @Override
    protected void handle() {
        PlayerData data = PlayerData.get((Player)this.player);
        if (data.cloned == null) {
            return;
        }
        ServerCloneController.Instance.addClone(data.cloned, this.name, this.tab);
    }
}

