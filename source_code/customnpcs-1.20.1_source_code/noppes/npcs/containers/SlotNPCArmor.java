/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.ElytraItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.containers;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.containers.SlotCompanionArmor;

class SlotNPCArmor
extends Slot {
    final EquipmentSlot armorType;

    SlotNPCArmor(Container iinventory, int i, int j, int k, EquipmentSlot l) {
        super(iinventory, i, j, k);
        this.armorType = l;
    }

    public int m_6641_() {
        return 1;
    }

    @OnlyIn(value=Dist.CLIENT)
    public Pair<ResourceLocation, ResourceLocation> m_7543_() {
        return Pair.of((Object)InventoryMenu.f_39692_, (Object)SlotCompanionArmor.ARMOR_SLOT_TEXTURES[this.armorType.m_20749_()]);
    }

    public boolean m_5857_(ItemStack itemstack) {
        if (itemstack.m_41720_() instanceof ElytraItem) {
            return ((ElytraItem)itemstack.m_41720_()).m_40402_() == this.armorType;
        }
        if (itemstack.m_41720_() instanceof ArmorItem) {
            return ((ArmorItem)itemstack.m_41720_()).m_40402_() == this.armorType;
        }
        if (itemstack.m_41720_() instanceof BlockItem) {
            return this.armorType == EquipmentSlot.HEAD;
        }
        return false;
    }
}

