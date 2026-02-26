/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data.role;

import noppes.npcs.api.entity.data.INPCRole;
import noppes.npcs.api.item.IItemStack;

public interface IRoleTrader
extends INPCRole {
    public IItemStack getSold(int var1);

    public IItemStack getCurrency1(int var1);

    public IItemStack getCurrency2(int var1);

    public void set(int var1, IItemStack var2, IItemStack var3, IItemStack var4);

    public void remove(int var1);

    public void setMarket(String var1);

    public String getMarket();
}

