/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.HumanoidArm
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.entity;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomEntities;

public class EntityFakeLiving
extends LivingEntity {
    public EntityFakeLiving(Level par1Level) {
        super(CustomEntities.entityCustomNpc, par1Level);
    }

    public Iterable<ItemStack> m_6168_() {
        return null;
    }

    public ItemStack m_6844_(EquipmentSlot slotIn) {
        return null;
    }

    public void m_8061_(EquipmentSlot slotIn, ItemStack stack) {
    }

    public HumanoidArm m_5737_() {
        return null;
    }
}

