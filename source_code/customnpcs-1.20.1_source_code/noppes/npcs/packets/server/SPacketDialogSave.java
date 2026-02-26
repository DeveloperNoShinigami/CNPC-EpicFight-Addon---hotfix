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
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.DialogCategory;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiUpdate;

public class SPacketDialogSave
extends PacketServerBasic {
    private int category;
    private CompoundTag data;

    public SPacketDialogSave(int category, CompoundTag data) {
        this.data = data;
        this.category = category;
    }

    public SPacketDialogSave(FriendlyByteBuf buf) {
        this.category = buf.readInt();
        this.data = buf.m_130260_();
    }

    public static SPacketDialogSave decode(FriendlyByteBuf buf) {
        return new SPacketDialogSave(buf);
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.GLOBAL_DIALOG;
    }

    @Override
    public void handle() {
        DialogCategory dcategory = DialogController.instance.categories.get(this.category);
        if (dcategory == null) {
            return;
        }
        Dialog dialog = new Dialog(dcategory);
        dialog.readNBT(this.data);
        DialogController.instance.saveDialog(dcategory, dialog);
        Packets.send(this.player, new PacketGuiUpdate());
    }

    public static void encode(SPacketDialogSave msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.category);
        buf.m_130079_(msg.data);
    }
}

