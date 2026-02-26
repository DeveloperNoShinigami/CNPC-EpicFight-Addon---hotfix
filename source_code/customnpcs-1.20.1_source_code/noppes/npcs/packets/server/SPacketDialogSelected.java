/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.EventHooks;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiClose;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleDialog;

public class SPacketDialogSelected
extends PacketServerBasic {
    private final int dialogId;
    private final int optionId;

    public SPacketDialogSelected(int dialogId, int optionId) {
        this.dialogId = dialogId;
        this.optionId = optionId;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    public static void encode(SPacketDialogSelected msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.dialogId);
        buf.writeInt(msg.optionId);
    }

    public static SPacketDialogSelected decode(FriendlyByteBuf buf) {
        return new SPacketDialogSelected(buf.readInt(), buf.readInt());
    }

    @Override
    protected void handle() {
        PlayerData data = PlayerData.get((Player)this.player);
        if (data.dialogId != this.dialogId) {
            return;
        }
        if (data.dialogId < 0 && this.npc.role.getType() == 7) {
            String text = ((RoleDialog)this.npc.role).optionsTexts.get(this.optionId);
            if (text != null && !text.isEmpty()) {
                Dialog d = new Dialog(null);
                d.text = text;
                NoppesUtilServer.openDialog((Player)this.player, this.npc, d);
            }
            return;
        }
        Dialog dialog = DialogController.instance.dialogs.get(data.dialogId);
        if (dialog == null) {
            return;
        }
        if (!dialog.hasDialogs((Player)this.player) && !dialog.hasOtherOptions()) {
            this.closeDialog(this.player, this.npc, true);
            return;
        }
        DialogOption option = dialog.options.get(this.optionId);
        if (option == null || EventHooks.onNPCDialogOption(this.npc, this.player, dialog, option) || option.optionType == 1 && (!option.isAvailable((Player)this.player) || !option.hasDialog()) || option.optionType == 2 || option.optionType == 0) {
            this.closeDialog(this.player, this.npc, true);
            return;
        }
        if (option.optionType == 3) {
            this.closeDialog(this.player, this.npc, true);
            if (this.npc.role.getType() == 6) {
                ((RoleCompanion)this.npc.role).interact((Player)this.player, true);
            } else {
                this.npc.role.interact((Player)this.player);
            }
        } else if (option.optionType == 1) {
            this.closeDialog(this.player, this.npc, false);
            NoppesUtilServer.openDialog((Player)this.player, this.npc, option.getDialog());
        } else if (option.optionType == 4) {
            this.closeDialog(this.player, this.npc, true);
            NoppesUtilServer.runCommand((Entity)this.npc, this.npc.m_7755_().getString(), option.command, (Player)this.player);
        } else {
            this.closeDialog(this.player, this.npc, true);
        }
    }

    public void closeDialog(ServerPlayer player, EntityNPCInterface npc, boolean notifyClient) {
        PlayerData data = PlayerData.get((Player)player);
        Dialog dialog = DialogController.instance.dialogs.get(data.dialogId);
        EventHooks.onNPCDialogClose(npc, player, dialog);
        if (notifyClient) {
            Packets.send(player, new PacketGuiClose(new CompoundTag()));
        }
        data.dialogId = -1;
    }
}

