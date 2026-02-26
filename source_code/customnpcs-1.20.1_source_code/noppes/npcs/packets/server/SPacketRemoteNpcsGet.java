/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import java.text.DecimalFormat;
import java.util.HashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiScrollSelected;

public class SPacketRemoteNpcsGet
extends PacketServerBasic {
    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_GUI;
    }

    public static void encode(SPacketRemoteNpcsGet msg, FriendlyByteBuf buf) {
    }

    public static SPacketRemoteNpcsGet decode(FriendlyByteBuf buf) {
        return new SPacketRemoteNpcsGet();
    }

    @Override
    protected void handle() {
        SPacketRemoteNpcsGet.sendNearbyNpcs(this.player);
        Packets.send(this.player, new PacketGuiScrollSelected(CustomNpcs.FreezeNPCs ? "Unfreeze Npcs" : "Freeze Npcs"));
    }

    public static void sendNearbyNpcs(ServerPlayer player) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Entity entity : ((ServerLevel)player.m_9236_()).m_8583_()) {
            EntityNPCInterface npc;
            if (!(entity instanceof EntityNPCInterface) || (npc = (EntityNPCInterface)entity).m_213877_()) continue;
            float distance = player.m_20270_((Entity)npc);
            DecimalFormat df = new DecimalFormat("#.#");
            Object s = df.format(distance);
            if (distance < 10.0f) {
                s = "0" + (String)s;
            }
            map.put((String)s + " : " + npc.display.getName(), npc.m_19879_());
        }
        NoppesUtilServer.sendScrollData(player, map);
    }
}

