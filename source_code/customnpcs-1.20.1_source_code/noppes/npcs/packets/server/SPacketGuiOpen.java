/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.packets.server;

import java.util.ArrayList;
import java.util.Vector;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerTransportData;
import noppes.npcs.controllers.data.TransportLocation;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiOpen;
import noppes.npcs.packets.client.PacketGuiScrollList;
import noppes.npcs.packets.client.PacketNpcRole;
import noppes.npcs.roles.RoleTransporter;
import noppes.npcs.util.CustomNPCsScheduler;

public class SPacketGuiOpen
extends PacketServerBasic {
    private EnumGuiType type;
    private BlockPos pos;

    public SPacketGuiOpen(EnumGuiType type, BlockPos pos) {
        this.type = type;
        this.pos = pos;
    }

    public static void encode(SPacketGuiOpen msg, FriendlyByteBuf buf) {
        buf.m_130068_((Enum)msg.type);
        buf.m_130064_(msg.pos);
    }

    public static SPacketGuiOpen decode(FriendlyByteBuf buf) {
        return new SPacketGuiOpen((EnumGuiType)buf.m_130066_(EnumGuiType.class), buf.m_130135_());
    }

    @Override
    protected void handle() {
        SPacketGuiOpen.sendOpenGui((Player)this.player, this.type, this.npc, this.pos);
    }

    public static void sendOpenGui(Player player, EnumGuiType gui, EntityNPCInterface npc, BlockPos pos) {
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        NoppesUtilServer.setEditingNpc(player, npc);
        if (gui == EnumGuiType.PlayerFollower || gui == EnumGuiType.PlayerFollowerHire || gui == EnumGuiType.PlayerTrader || gui == EnumGuiType.PlayerTransporter) {
            if (npc.role.getType() == 0) {
                return;
            }
            CompoundTag comp = new CompoundTag();
            npc.role.save(comp);
            comp.m_128405_("Role", npc.role.getType());
            Packets.send((ServerPlayer)player, new PacketNpcRole(npc.m_19879_(), comp));
        }
        CustomNPCsScheduler.runTack(() -> player.m_20194_().m_18707_(() -> {
            if (!gui.hasContainer) {
                Packets.send((ServerPlayer)player, new PacketGuiOpen(gui, pos));
            } else {
                NoppesUtilServer.openContainerGui((ServerPlayer)player, gui, buffer -> {
                    buffer.writeInt(npc.m_19879_());
                    if (pos != null) {
                        buffer.m_130064_(pos);
                    }
                });
            }
            ArrayList<String> list = SPacketGuiOpen.getScrollData(player, gui, npc);
            if (list == null || list.isEmpty()) {
                return;
            }
            Packets.send((ServerPlayer)player, new PacketGuiScrollList(new Vector<String>(list)));
        }), 200);
    }

    private static ArrayList<String> getScrollData(Player player, EnumGuiType gui, EntityNPCInterface npc) {
        if (gui == EnumGuiType.PlayerTransporter) {
            RoleTransporter role = (RoleTransporter)npc.role;
            ArrayList<String> list = new ArrayList<String>();
            TransportLocation location = role.getLocation();
            String name = role.getLocation().name;
            for (TransportLocation loc : location.category.getDefaultLocations()) {
                if (list.contains(loc.name)) continue;
                list.add(loc.name);
            }
            PlayerTransportData playerdata = PlayerData.get((Player)player).transportData;
            for (int i : playerdata.transports) {
                TransportLocation loc = TransportController.getInstance().getTransport(i);
                if (loc == null || !location.category.locations.containsKey(loc.id) || list.contains(loc.name)) continue;
                list.add(loc.name);
            }
            list.remove(name);
            return list;
        }
        return null;
    }
}

