/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomContainer;
import noppes.npcs.controllers.data.Bank;

public class ContainerManageBanks
extends AbstractContainerMenu {
    public Bank bank = new Bank();

    public ContainerManageBanks(int containerId, Inventory playerInventory) {
        super(CustomContainer.container_managebanks, containerId);
        int y;
        int x;
        int i;
        for (i = 0; i < 6; ++i) {
            x = 36;
            y = 38;
            this.m_38897_(new Slot((Container)this.bank.currencyInventory, i, x, y += i * 22));
        }
        for (i = 0; i < 6; ++i) {
            x = 142;
            y = 38;
            this.m_38897_(new Slot((Container)this.bank.upgradeInventory, i, x, y += i * 22));
        }
        for (int j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)playerInventory, j1, 8 + j1 * 18, 171));
        }
    }

    public ItemStack m_7648_(Player par1Player, int i) {
        return ItemStack.f_41583_;
    }

    public boolean m_6875_(Player entityplayer) {
        return true;
    }

    public void setBank(Bank bank2) {
        for (int i = 0; i < 6; ++i) {
            this.bank.currencyInventory.m_6836_(i, bank2.currencyInventory.m_8020_(i));
            this.bank.upgradeInventory.m_6836_(i, bank2.upgradeInventory.m_8020_(i));
        }
    }
}

