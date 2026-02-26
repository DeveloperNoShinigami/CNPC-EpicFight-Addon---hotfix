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

public class SlotValid
extends Slot {
    private boolean canPutIn = true;

    public SlotValid(Container par1iInventory, int limbSwingAmount, int par3, int par4) {
        super(par1iInventory, limbSwingAmount, par3, par4);
    }

    public SlotValid(Container par1iInventory, int limbSwingAmount, int par3, int par4, boolean bo) {
        super(par1iInventory, limbSwingAmount, par3, par4);
        this.canPutIn = bo;
    }

    public boolean m_5857_(ItemStack itemstack) {
        return this.canPutIn && this.f_40218_.m_7013_(0, itemstack);
    }
}

