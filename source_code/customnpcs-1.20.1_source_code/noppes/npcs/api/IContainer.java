/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.AbstractContainerMenu
 */
package noppes.npcs.api;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import noppes.npcs.api.item.IItemStack;

public interface IContainer {
    public int getSize();

    public IItemStack getSlot(int var1);

    public void setSlot(int var1, IItemStack var2);

    public Container getMCInventory();

    public AbstractContainerMenu getMCContainer();

    public int count(IItemStack var1, boolean var2, boolean var3);

    public IItemStack[] getItems();
}

