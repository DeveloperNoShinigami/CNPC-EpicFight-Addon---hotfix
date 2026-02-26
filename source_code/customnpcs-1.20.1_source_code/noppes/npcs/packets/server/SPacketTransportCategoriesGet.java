/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 */
package noppes.npcs.packets.server;

import java.util.HashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.data.TransportCategory;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketTransportCategoriesGet
extends PacketServerBasic {
    public static void encode(SPacketTransportCategoriesGet msg, FriendlyByteBuf buf) {
    }

    public static SPacketTransportCategoriesGet decode(FriendlyByteBuf buf) {
        return new SPacketTransportCategoriesGet();
    }

    @Override
    protected void handle() {
        SPacketTransportCategoriesGet.sendTransportCategoryData(this.player);
    }

    public static void sendTransportCategoryData(ServerPlayer player) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (TransportCategory category : TransportController.getInstance().categories.values()) {
            map.put(category.title, category.id);
        }
        NoppesUtilServer.sendScrollData(player, map);
    }
}

