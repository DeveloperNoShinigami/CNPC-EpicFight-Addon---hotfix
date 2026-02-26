/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.IContainer;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.item.IItemStack;

public class ContainerWrapper
implements IContainer {
    private Container inventory;
    private AbstractContainerMenu container;

    public ContainerWrapper(Container inventory) {
        this.inventory = inventory;
    }

    public ContainerWrapper(AbstractContainerMenu container) {
        this.container = container;
    }

    @Override
    public int getSize() {
        if (this.inventory != null) {
            return this.inventory.m_6643_();
        }
        return this.container.f_38839_.size();
    }

    @Override
    public IItemStack getSlot(int slot) {
        if (slot < 0 || slot >= this.getSize()) {
            throw new CustomNPCsException("Slot is out of range " + slot, new Object[0]);
        }
        if (this.inventory != null) {
            return NpcAPI.Instance().getIItemStack(this.inventory.m_8020_(slot));
        }
        return NpcAPI.Instance().getIItemStack(this.container.m_38853_(slot).m_7993_());
    }

    @Override
    public void setSlot(int slot, IItemStack item) {
        ItemStack itemstack;
        if (slot < 0 || slot >= this.getSize()) {
            throw new CustomNPCsException("Slot is out of range " + slot, new Object[0]);
        }
        ItemStack itemStack = itemstack = item == null ? ItemStack.f_41583_ : item.getMCItemStack();
        if (this.inventory != null) {
            this.inventory.m_6836_(slot, itemstack);
        } else {
            this.container.m_182406_(slot, this.container.m_182424_(), itemstack);
            this.container.m_38946_();
        }
    }

    @Override
    public int count(IItemStack item, boolean ignoreDamage, boolean ignoreNBT) {
        int count = 0;
        for (int i = 0; i < this.getSize(); ++i) {
            IItemStack toCompare = this.getSlot(i);
            if (!NoppesUtilPlayer.compareItems(item.getMCItemStack(), toCompare.getMCItemStack(), ignoreDamage, ignoreNBT)) continue;
            count += toCompare.getStackSize();
        }
        return count;
    }

    @Override
    public Container getMCInventory() {
        return this.inventory;
    }

    @Override
    public AbstractContainerMenu getMCContainer() {
        return this.container;
    }

    @Override
    public IItemStack[] getItems() {
        IItemStack[] items = new IItemStack[this.getSize()];
        for (int i = 0; i < this.getSize(); ++i) {
            items[i] = this.getSlot(i);
        }
        return items;
    }
}

