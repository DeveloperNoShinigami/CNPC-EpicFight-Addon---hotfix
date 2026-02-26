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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.roles.RoleCompanion;

public class SlotCompanionArmor
extends Slot {
    public static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[]{InventoryMenu.f_39696_, InventoryMenu.f_39695_, InventoryMenu.f_39694_, InventoryMenu.f_39693_};
    final EquipmentSlot armorType;
    final RoleCompanion role;

    public SlotCompanionArmor(RoleCompanion role, Container iinventory, int id, int x, int y, EquipmentSlot type) {
        super(iinventory, id, x, y);
        this.armorType = type;
        this.role = role;
    }

    public int m_6641_() {
        return 1;
    }

    @OnlyIn(value=Dist.CLIENT)
    public Pair<ResourceLocation, ResourceLocation> m_7543_() {
        return Pair.of((Object)InventoryMenu.f_39692_, (Object)ARMOR_SLOT_TEXTURES[this.armorType.m_20749_()]);
    }

    public boolean m_5857_(ItemStack itemstack) {
        if (itemstack.m_41720_() instanceof ArmorItem && this.role.canWearArmor(itemstack)) {
            return ((ArmorItem)itemstack.m_41720_()).m_40402_() == this.armorType;
        }
        if (itemstack.m_41720_() instanceof BlockItem) {
            return this.armorType == EquipmentSlot.HEAD;
        }
        return false;
    }
}

