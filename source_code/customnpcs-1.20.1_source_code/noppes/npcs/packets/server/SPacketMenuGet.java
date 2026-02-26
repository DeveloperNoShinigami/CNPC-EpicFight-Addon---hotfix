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
import noppes.npcs.constants.EnumMenuType;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiData;

public class SPacketMenuGet
extends PacketServerBasic {
    private EnumMenuType type;

    public SPacketMenuGet(EnumMenuType type) {
        this.type = type;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        if (this.type == EnumMenuType.MOVING_PATH) {
            return item.m_41720_() == CustomItems.moving;
        }
        return item.m_41720_() == CustomItems.wand;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_GUI;
    }

    public static void encode(SPacketMenuGet msg, FriendlyByteBuf buf) {
        buf.m_130068_((Enum)msg.type);
    }

    public static SPacketMenuGet decode(FriendlyByteBuf buf) {
        return new SPacketMenuGet((EnumMenuType)buf.m_130066_(EnumMenuType.class));
    }

    @Override
    protected void handle() {
        CompoundTag data = new CompoundTag();
        if (this.type == EnumMenuType.DISPLAY) {
            this.npc.display.save(data);
        }
        if (this.type == EnumMenuType.STATS) {
            this.npc.stats.save(data);
        }
        if (this.type == EnumMenuType.INVENTORY) {
            this.npc.inventory.save(data);
        }
        if (this.type == EnumMenuType.AI || this.type == EnumMenuType.MOVING_PATH) {
            this.npc.ais.save(data);
        }
        if (this.type == EnumMenuType.ADVANCED) {
            this.npc.advanced.save(data);
        }
        if (this.type == EnumMenuType.TRANSFORM) {
            this.npc.transform.writeOptions(data);
        }
        Packets.send(this.player, new PacketGuiData(data));
    }
}

