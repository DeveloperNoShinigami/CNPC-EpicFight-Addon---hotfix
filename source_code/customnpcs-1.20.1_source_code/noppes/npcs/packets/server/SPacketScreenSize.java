/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketScreenSize
extends PacketServerBasic {
    public int width;
    public int height;

    public SPacketScreenSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static void encode(SPacketScreenSize msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.width);
        buf.writeInt(msg.height);
    }

    public static SPacketScreenSize decode(FriendlyByteBuf buf) {
        return new SPacketScreenSize(buf.readInt(), buf.readInt());
    }

    @Override
    protected void handle() {
        PlayerData.get((Player)this.player).screenSize.setSize(this.width, this.height);
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }
}

