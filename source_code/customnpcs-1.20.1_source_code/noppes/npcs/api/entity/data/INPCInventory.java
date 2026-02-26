/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data;

import noppes.npcs.api.item.IItemStack;

public interface INPCInventory {
    public IItemStack getRightHand();

    public void setRightHand(IItemStack var1);

    public IItemStack getLeftHand();

    public void setLeftHand(IItemStack var1);

    public IItemStack getProjectile();

    public void setProjectile(IItemStack var1);

    public IItemStack getArmor(int var1);

    public void setArmor(int var1, IItemStack var2);

    public void setDropItem(int var1, IItemStack var2, float var3);

    public IItemStack getDropItem(int var1);

    public int getExpMin();

    public int getExpMax();

    public int getExpRNG();

    public void setExp(int var1, int var2);

    public IItemStack[] getItemsRNG();
}

