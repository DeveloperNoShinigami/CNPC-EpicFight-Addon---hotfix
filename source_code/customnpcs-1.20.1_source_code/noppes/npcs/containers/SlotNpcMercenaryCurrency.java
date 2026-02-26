/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.roles.RoleFollower;

class SlotNpcMercenaryCurrency
extends Slot {
    RoleFollower role;

    public SlotNpcMercenaryCurrency(RoleFollower role, Container inv, int i, int j, int k) {
        super(inv, i, j, k);
        this.role = role;
    }

    public int m_6641_() {
        return 64;
    }

    public boolean m_5857_(ItemStack itemstack) {
        Item item = itemstack.m_41720_();
        for (ItemStack is : this.role.inventory.items) {
            if (item != is.m_41720_()) continue;
            return true;
        }
        return false;
    }
}

