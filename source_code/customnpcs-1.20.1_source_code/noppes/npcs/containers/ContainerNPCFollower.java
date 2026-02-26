/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomContainer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.containers.ContainerNpcInterface;
import noppes.npcs.containers.InventoryNPC;
import noppes.npcs.containers.SlotNpcMercenaryCurrency;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleFollower;

public class ContainerNPCFollower
extends ContainerNpcInterface {
    public InventoryNPC currencyMatrix;
    public RoleFollower role;

    public ContainerNPCFollower(int containerId, Inventory playerInventory, int entityId) {
        super(CustomContainer.container_follower, containerId, playerInventory);
        EntityNPCInterface npc = (EntityNPCInterface)this.player.m_9236_().m_6815_(entityId);
        this.role = (RoleFollower)npc.role;
        this.currencyMatrix = new InventoryNPC("currency", 1, this);
        this.m_38897_(new SlotNpcMercenaryCurrency(this.role, this.currencyMatrix, 0, 26, 9));
        for (int j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)this.player.m_150109_(), j1, 8 + j1 * 18, 142));
        }
    }

    @Override
    public ItemStack m_7648_(Player par1Player, int i) {
        return ItemStack.f_41583_;
    }

    public void m_6877_(Player entityplayer) {
        ItemStack itemstack;
        super.m_6877_(entityplayer);
        if (!(entityplayer.m_9236_().f_46443_ || NoppesUtilServer.IsItemStackNull(itemstack = this.currencyMatrix.m_8016_(0)) || entityplayer.m_9236_().f_46443_)) {
            entityplayer.m_5552_(itemstack, 0.0f);
        }
    }
}

