/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.item;

import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.INbt;
import noppes.npcs.api.entity.IMob;
import noppes.npcs.api.entity.data.IData;

public interface IItemStack {
    public int getStackSize();

    public void setStackSize(int var1);

    public int getMaxStackSize();

    public boolean isDamageable();

    public int getDamage();

    public void setDamage(int var1);

    public int getMaxDamage();

    public double getAttackDamage();

    public void damageItem(int var1, IMob var2);

    public void addEnchantment(String var1, int var2);

    public boolean isEnchanted();

    public boolean hasEnchant(String var1);

    public boolean removeEnchant(String var1);

    public boolean isBlock();

    public boolean isWearable();

    public boolean hasCustomName();

    public void setCustomName(String var1);

    public String getDisplayName();

    public String getItemName();

    public String getName();

    public boolean isBook();

    public IItemStack copy();

    public ItemStack getMCItemStack();

    public INbt getNbt();

    public boolean hasNbt();

    public void removeNbt();

    public INbt getItemNbt();

    public boolean isEmpty();

    public int getType();

    public String[] getLore();

    public void setLore(String[] var1);

    public void setAttribute(String var1, double var2);

    public void setAttribute(String var1, double var2, int var4);

    public double getAttribute(String var1);

    public boolean hasAttribute(String var1);

    public IData getTempdata();

    public IData getStoreddata();

    public int getFoodLevel();

    public boolean compare(IItemStack var1, boolean var2);
}

