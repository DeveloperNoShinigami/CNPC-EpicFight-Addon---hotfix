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
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.containers.ContainerMail;
import noppes.npcs.controllers.data.PlayerMail;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketMailSetup
extends PacketServerBasic {
    private CompoundTag data;

    public SPacketMailSetup(CompoundTag data) {
        this.data = data;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_GUI;
    }

    public static void encode(SPacketMailSetup msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static SPacketMailSetup decode(FriendlyByteBuf buf) {
        return new SPacketMailSetup(buf.m_130260_());
    }

    @Override
    protected void handle() {
        PlayerMail mail = new PlayerMail();
        mail.readNBT(this.data);
        ContainerMail.staticmail = mail;
        NoppesUtilServer.openContainerGui(this.player, EnumGuiType.PlayerMailman, buf -> {
            buf.writeBoolean(true);
            buf.writeBoolean(false);
        });
    }
}

