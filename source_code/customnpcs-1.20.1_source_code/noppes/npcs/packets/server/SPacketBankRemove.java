/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.data.Bank;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.server.SPacketBankGet;
import noppes.npcs.packets.server.SPacketBanksGet;

public class SPacketBankRemove
extends PacketServerBasic {
    private int bank;

    public SPacketBankRemove(int bank) {
        this.bank = bank;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.GLOBAL_BANK;
    }

    public static void encode(SPacketBankRemove msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.bank);
    }

    public static SPacketBankRemove decode(FriendlyByteBuf buf) {
        return new SPacketBankRemove(buf.readInt());
    }

    @Override
    protected void handle() {
        BankController.getInstance().removeBank(this.bank);
        SPacketBanksGet.sendBankDataAll(this.player);
        SPacketBankGet.sendBank(this.player, new Bank());
    }
}

