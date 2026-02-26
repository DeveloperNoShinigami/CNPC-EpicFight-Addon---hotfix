/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.roles.RoleCompanion;

class SlotCompanionWeapon
extends Slot {
    final RoleCompanion role;

    public SlotCompanionWeapon(RoleCompanion role, Container iinventory, int id, int x, int y) {
        super(iinventory, id, x, y);
        this.role = role;
    }

    public int m_6641_() {
        return 1;
    }

    public boolean m_5857_(ItemStack itemstack) {
        if (NoppesUtilServer.IsItemStackNull(itemstack)) {
            return false;
        }
        return this.role.canWearSword(NpcAPI.Instance().getIItemStack(itemstack));
    }
}

