/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.item;

import noppes.npcs.api.item.IItemStack;

public interface IItemArmor
extends IItemStack {
    public int getArmorSlot();

    public String getArmorMaterial();
}

