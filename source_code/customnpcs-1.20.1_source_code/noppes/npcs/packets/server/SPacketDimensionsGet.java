/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import java.util.HashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketDimensionsGet
extends PacketServerBasic {
    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.teleporter;
    }

    public static void encode(SPacketDimensionsGet msg, FriendlyByteBuf buf) {
    }

    public static SPacketDimensionsGet decode(FriendlyByteBuf buf) {
        return new SPacketDimensionsGet();
    }

    @Override
    protected void handle() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (ResourceKey key : CustomNpcs.Server.m_129784_()) {
            map.put(key.m_135782_().toString(), 0);
        }
        NoppesUtilServer.sendScrollData(this.player, map);
    }
}

