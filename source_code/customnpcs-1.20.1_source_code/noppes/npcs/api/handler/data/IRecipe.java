/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.handler.data;

import net.minecraft.world.item.ItemStack;

public interface IRecipe {
    public String getName();

    public boolean isGlobal();

    public void setIsGlobal(boolean var1);

    public boolean getIgnoreNBT();

    public void setIgnoreNBT(boolean var1);

    public boolean getIgnoreDamage();

    public void setIgnoreDamage(boolean var1);

    public int getWidth();

    public int getHeight();

    public ItemStack getResult();

    public ItemStack[] getRecipe();

    public void saves(boolean var1);

    public boolean saves();

    public void save();

    public void delete();
}

