/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.EventHooks;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.entity.data.role.IRoleTransporter;
import noppes.npcs.api.event.RoleEvent;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerTransportData;
import noppes.npcs.controllers.data.TransportLocation;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketChatBubble;
import noppes.npcs.packets.server.SPacketDimensionTeleport;
import noppes.npcs.roles.RoleInterface;

public class RoleTransporter
extends RoleInterface
implements IRoleTransporter {
    public int transportId = -1;
    public String name;
    private int ticks = 10;

    public RoleTransporter(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128405_("TransporterId", this.transportId);
        return nbttagcompound;
    }

    @Override
    public void load(CompoundTag nbttagcompound) {
        this.transportId = nbttagcompound.m_128451_("TransporterId");
        TransportLocation loc = this.getLocation();
        if (loc != null) {
            this.name = loc.name;
        }
    }

    @Override
    public boolean aiShouldExecute() {
        --this.ticks;
        if (this.ticks > 0) {
            return false;
        }
        this.ticks = 10;
        if (!this.hasTransport()) {
            return false;
        }
        TransportLocation loc = this.getLocation();
        if (loc.type != 0) {
            return false;
        }
        List inRange = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_(6.0, 6.0, 6.0));
        for (Player player : inRange) {
            if (!this.npc.canNpcSee((Entity)player)) continue;
            this.unlock(player, loc);
        }
        return false;
    }

    @Override
    public void interact(Player player) {
        if (this.hasTransport()) {
            TransportLocation loc = this.getLocation();
            if (loc.type == 2) {
                this.unlock(player, loc);
            }
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTransporter, this.npc);
        }
    }

    public void transport(ServerPlayer player, String location) {
        TransportLocation loc = TransportController.getInstance().getTransport(location);
        PlayerTransportData playerdata = PlayerData.get((Player)player).transportData;
        if (loc == null || !loc.isDefault() && !playerdata.transports.contains(loc.id)) {
            return;
        }
        RoleEvent.TransporterUseEvent event = new RoleEvent.TransporterUseEvent((Player)player, this.npc.wrappedNPC, loc);
        if (EventHooks.onNPCRole(this.npc, event)) {
            return;
        }
        SPacketDimensionTeleport.teleportPlayer(player, loc.pos.m_123341_(), loc.pos.m_123342_(), loc.pos.m_123343_(), loc.dimension);
    }

    private void unlock(Player player, TransportLocation loc) {
        PlayerTransportData data = PlayerData.get((Player)player).transportData;
        if (data.transports.contains(this.transportId)) {
            return;
        }
        RoleEvent.TransporterUnlockedEvent event = new RoleEvent.TransporterUnlockedEvent(player, this.npc.wrappedNPC);
        if (EventHooks.onNPCRole(this.npc, event)) {
            return;
        }
        data.transports.add(this.transportId);
        this.npc.say(player, new Line("mailbox.gotmail"));
        Packets.send((ServerPlayer)player, new PacketChatBubble(this.npc.m_19879_(), (Component)Component.m_237110_((String)"transporter.unlock", (Object[])new Object[]{loc.name}), true));
    }

    @Override
    public TransportLocation getLocation() {
        if (this.npc.isClientSide()) {
            return null;
        }
        return TransportController.getInstance().getTransport(this.transportId);
    }

    public boolean hasTransport() {
        TransportLocation loc = this.getLocation();
        return loc != null && loc.id == this.transportId;
    }

    public void setTransport(TransportLocation location) {
        this.transportId = location.id;
        this.name = location.name;
    }

    @Override
    public int getType() {
        return 4;
    }
}

