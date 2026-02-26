/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.containers.ContainerNPCBankInterface;

public class SlotNpcBankCurrency
extends Slot {
    public ItemStack item = ItemStack.f_41583_;

    public SlotNpcBankCurrency(ContainerNPCBankInterface containerplayer, Container iinventory, int i, int j, int k) {
        super(iinventory, i, j, k);
    }

    public int m_6641_() {
        return 64;
    }

    public boolean m_5857_(ItemStack itemstack) {
        if (NoppesUtilServer.IsItemStackNull(itemstack)) {
            return false;
        }
        return this.item.m_41720_() == itemstack.m_41720_();
    }
}

