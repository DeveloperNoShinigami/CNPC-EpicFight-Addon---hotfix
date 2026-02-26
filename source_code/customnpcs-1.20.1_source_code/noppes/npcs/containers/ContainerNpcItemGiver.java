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
import noppes.npcs.roles.JobItemGiver;

public class ContainerNpcItemGiver
extends AbstractContainerMenu {
    private JobItemGiver role;

    public ContainerNpcItemGiver(int containerId, Inventory playerInventory, int entityId) {
        super(CustomContainer.container_itemgiver, containerId);
        int j1;
        EntityNPCInterface npc = (EntityNPCInterface)playerInventory.f_35978_.m_9236_().m_6815_(entityId);
        this.role = (JobItemGiver)npc.job;
        for (j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)this.role.inventory, j1, 6 + j1 * 18, 90));
        }
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.m_38897_(new Slot((Container)playerInventory, l1 + i1 * 9 + 9, 6 + l1 * 18, 116 + i1 * 18));
            }
        }
        for (j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)playerInventory, j1, 6 + j1 * 18, 174));
        }
    }

    public ItemStack m_7648_(Player par1Player, int i) {
        return ItemStack.f_41583_;
    }

    public boolean m_6875_(Player entityplayer) {
        return true;
    }
}

