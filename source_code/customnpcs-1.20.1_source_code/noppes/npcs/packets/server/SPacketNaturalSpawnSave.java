/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.SpawnController;
import noppes.npcs.controllers.data.SpawnData;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketNaturalSpawnSave
extends PacketServerBasic {
    private CompoundTag data;

    public SPacketNaturalSpawnSave(CompoundTag data) {
        this.data = data;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.GLOBAL_NATURALSPAWN;
    }

    public static void encode(SPacketNaturalSpawnSave msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static SPacketNaturalSpawnSave decode(FriendlyByteBuf buf) {
        return new SPacketNaturalSpawnSave(buf.m_130260_());
    }

    @Override
    protected void handle() {
        SpawnData sdata = new SpawnData();
        sdata.readNBT(this.data);
        SpawnController.instance.saveSpawnData(sdata);
        NoppesUtilServer.sendScrollData(this.player, SpawnController.instance.getScroll());
    }
}

