/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.inventory.ResultSlot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;

public class SlotNpcCrafting
extends ResultSlot {
    private final CraftingContainer craftMatrix;

    public SlotNpcCrafting(Player player, CraftingContainer craftingInventory, Container inventory, int slotIndex, int x, int y) {
        super(player, craftingInventory, inventory, slotIndex, x, y);
        this.craftMatrix = craftingInventory;
    }

    public void m_142406_(Player player, ItemStack itemStack) {
        this.m_5845_(itemStack);
        for (int i = 0; i < this.craftMatrix.m_6643_(); ++i) {
            ItemStack itemstack2;
            ItemStack itemstack1 = this.craftMatrix.m_8020_(i);
            if (NoppesUtilServer.IsItemStackNull(itemstack1)) continue;
            this.craftMatrix.m_7407_(i, 1);
            if (!itemstack1.m_41720_().hasCraftingRemainingItem(itemstack1) || !NoppesUtilServer.IsItemStackNull(itemstack2 = itemstack1.m_41720_().getCraftingRemainingItem(itemstack1)) && itemstack2.m_41763_() && itemstack2.m_41773_() > itemstack2.m_41776_() || player.m_150109_().m_36054_(itemstack2)) continue;
            if (NoppesUtilServer.IsItemStackNull(this.craftMatrix.m_8020_(i))) {
                this.craftMatrix.m_6836_(i, itemstack2);
                continue;
            }
            player.m_36176_(itemstack2, false);
        }
    }
}

