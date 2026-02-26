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
import noppes.npcs.roles.RoleTrader;

public class ContainerNPCTraderSetup
extends AbstractContainerMenu {
    public RoleTrader role;

    public ContainerNPCTraderSetup(int containerId, Inventory playerInventory, int entityId) {
        super(CustomContainer.container_tradersetup, containerId);
        EntityNPCInterface npc = (EntityNPCInterface)playerInventory.f_35978_.m_9236_().m_6815_(entityId);
        this.role = (RoleTrader)npc.role;
        for (int i = 0; i < 18; ++i) {
            int x = 7;
            int y = 15;
            this.m_38897_(new Slot((Container)this.role.inventoryCurrency, i + 18, x += i % 3 * 94, y += i / 3 * 22));
            this.m_38897_(new Slot((Container)this.role.inventoryCurrency, i, x + 18, y));
            this.m_38897_(new Slot((Container)this.role.inventorySold, i, x + 43, y));
        }
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int l1 = 0; l1 < 9; ++l1) {
                this.m_38897_(new Slot((Container)playerInventory, l1 + i1 * 9 + 9, 48 + l1 * 18, 147 + i1 * 18));
            }
        }
        for (int j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)playerInventory, j1, 48 + j1 * 18, 205));
        }
    }

    public ItemStack m_7648_(Player par1Player, int i) {
        return ItemStack.f_41583_;
    }

    public boolean m_6875_(Player entityplayer) {
        return true;
    }
}

