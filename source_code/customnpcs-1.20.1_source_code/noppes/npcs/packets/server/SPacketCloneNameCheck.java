/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiData;

public class SPacketCloneNameCheck
extends PacketServerBasic {
    private String name;
    private int tab;

    public SPacketCloneNameCheck(String name, int tab) {
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

    public static void encode(SPacketCloneNameCheck msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.name);
        buf.writeInt(msg.tab);
    }

    public static SPacketCloneNameCheck decode(FriendlyByteBuf buf) {
        return new SPacketCloneNameCheck(buf.m_130136_(Short.MAX_VALUE), buf.readInt());
    }

    @Override
    protected void handle() {
        boolean bo = ServerCloneController.Instance.getCloneData(null, this.name, this.tab) != null;
        CompoundTag compound = new CompoundTag();
        compound.m_128379_("NameExists", bo);
        Packets.send(this.player, new PacketGuiData(compound));
    }
}

