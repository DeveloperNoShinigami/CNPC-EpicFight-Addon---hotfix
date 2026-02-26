/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.EventHooks;
import noppes.npcs.api.event.RoleEvent;
import noppes.npcs.containers.ContainerNPCBankInterface;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.data.Bank;
import noppes.npcs.controllers.data.BankData;
import noppes.npcs.controllers.data.PlayerBankData;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketBankUpgrade
extends PacketServerBasic {
    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    public static void encode(SPacketBankUpgrade msg, FriendlyByteBuf buf) {
    }

    public static SPacketBankUpgrade decode(FriendlyByteBuf buf) {
        return new SPacketBankUpgrade();
    }

    @Override
    protected void handle() {
        if (this.npc.role.getType() != 3) {
            return;
        }
        AbstractContainerMenu con = this.player.f_36096_;
        if (con == null || !(con instanceof ContainerNPCBankInterface)) {
            return;
        }
        ContainerNPCBankInterface container = (ContainerNPCBankInterface)con;
        Bank bank = BankController.getInstance().getBank(container.bankid);
        ItemStack item = bank.upgradeInventory.m_8020_(container.slot);
        if (item == null || item.m_41619_()) {
            return;
        }
        int price = item.m_41613_();
        ItemStack currency = container.currencyMatrix.m_8020_(0);
        if (currency == null || currency.m_41619_() || price > currency.m_41613_()) {
            return;
        }
        if (currency.m_41613_() - price == 0) {
            container.currencyMatrix.m_6836_(0, ItemStack.f_41583_);
        } else {
            currency = currency.m_41620_(price);
        }
        this.player.m_6915_();
        PlayerBankData data = PlayerDataController.instance.getBankData((Player)this.player, bank.id);
        BankData bankData = data.getBank(bank.id);
        bankData.upgradedSlots.put(container.slot, true);
        RoleEvent.BankUpgradedEvent event = new RoleEvent.BankUpgradedEvent((Player)this.player, this.npc.wrappedNPC, container.slot);
        EventHooks.onNPCRole(this.npc, event);
        bankData.openBankGui(this.player, this.npc, bank.id, container.slot);
    }
}

