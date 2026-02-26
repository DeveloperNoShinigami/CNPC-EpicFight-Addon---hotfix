/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketNbtBookEntitySave
extends PacketServerBasic {
    private int id;
    private CompoundTag data;

    public SPacketNbtBookEntitySave(int id, CompoundTag data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.nbt_book;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.TOOL_NBTBOOK;
    }

    public static void encode(SPacketNbtBookEntitySave msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        buf.m_130079_(msg.data);
    }

    public static SPacketNbtBookEntitySave decode(FriendlyByteBuf buf) {
        return new SPacketNbtBookEntitySave(buf.readInt(), buf.m_130260_());
    }

    @Override
    protected void handle() {
        Entity entity = this.player.m_9236_().m_6815_(this.id);
        if (entity != null) {
            entity.m_20258_(this.data);
        }
    }
}

