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
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.data.Bank;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.server.SPacketBankGet;
import noppes.npcs.packets.server.SPacketBanksGet;

public class SPacketBankSave
extends PacketServerBasic {
    private CompoundTag data;

    public SPacketBankSave(CompoundTag data) {
        this.data = data;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.GLOBAL_BANK;
    }

    public static void encode(SPacketBankSave msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static SPacketBankSave decode(FriendlyByteBuf buf) {
        return new SPacketBankSave(buf.m_130260_());
    }

    @Override
    protected void handle() {
        Bank bank = new Bank();
        bank.readAdditionalSaveData(this.data);
        BankController.getInstance().saveBank(bank);
        SPacketBanksGet.sendBankDataAll(this.player);
        SPacketBankGet.sendBank(this.player, bank);
    }
}

