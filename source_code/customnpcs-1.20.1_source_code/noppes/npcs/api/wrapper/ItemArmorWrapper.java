/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.item.IItemArmor;
import noppes.npcs.api.wrapper.ItemStackWrapper;

public class ItemArmorWrapper
extends ItemStackWrapper
implements IItemArmor {
    protected ArmorItem armor;

    protected ItemArmorWrapper(ItemStack item) {
        super(item);
        this.armor = (ArmorItem)item.m_41720_();
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public int getArmorSlot() {
        return this.armor.m_40402_().m_20749_();
    }

    @Override
    public String getArmorMaterial() {
        return this.armor.m_40401_().m_6082_();
    }
}

