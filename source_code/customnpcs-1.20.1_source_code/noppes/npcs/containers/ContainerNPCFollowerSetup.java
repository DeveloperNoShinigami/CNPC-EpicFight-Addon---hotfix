/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomContainer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;

public class ContainerNPCFollowerSetup
extends AbstractContainerMenu {
    private RoleFollower role;

    public ContainerNPCFollowerSetup(int containerId, Inventory playerInventory, int entityId) {
        super(CustomContainer.container_followersetup, containerId);
        int i1;
        EntityNPCInterface npc = (EntityNPCInterface)playerInventory.f_35978_.m_9236_().m_6815_(entityId);
        this.role = (RoleFollower)npc.role;
        for (i1 = 0; i1 < 3; ++i1) {
            this.m_38897_(new Slot((Container)this.role.inventory, i1, 44, 39 + i1 * 25));
        }
        for (i1 = 0; i1 < 3; ++i1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.m_38897_(new Slot((Container)playerInventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 113 + i1 * 18));
            }
        }
        for (int j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)playerInventory, j1, 8 + j1 * 18, 171));
        }
    }

    public ItemStack m_7648_(Player par1Player, int i) {
        ItemStack itemstack = ItemStack.f_41583_;
        Slot slot = (Slot)this.f_38839_.get(i);
        if (slot != null && slot.m_6657_()) {
            ItemStack itemstack1 = slot.m_7993_();
            itemstack = itemstack1.m_41777_();
            if (i >= 0 && i < 3 ? !this.m_38903_(itemstack1, 3, 38, true) : (i >= 3 && i < 30 ? !this.m_38903_(itemstack1, 30, 38, false) : (i >= 30 && i < 38 ? !this.m_38903_(itemstack1, 3, 29, false) : !this.m_38903_(itemstack1, 3, 38, false)))) {
                return ItemStack.f_41583_;
            }
            if (itemstack1.m_41613_() == 0) {
                slot.m_5852_(ItemStack.f_41583_);
            } else {
                slot.m_6654_();
            }
            if (itemstack1.m_41613_() != itemstack.m_41613_()) {
                slot.m_142406_(par1Player, itemstack1);
            } else {
                return ItemStack.f_41583_;
            }
        }
        return itemstack;
    }

    public boolean m_6875_(Player entityplayer) {
        return true;
    }
}

