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
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.quests.QuestItem;

public class ContainerNpcQuestTypeItem
extends AbstractContainerMenu {
    public ContainerNpcQuestTypeItem(int containerId, Inventory playerInventory) {
        super(CustomContainer.container_questtypeitem, containerId);
        int i1;
        Quest quest = NoppesUtilServer.getEditingQuest(playerInventory.f_35978_);
        for (i1 = 0; i1 < 3; ++i1) {
            this.m_38897_(new Slot((Container)((QuestItem)quest.questInterface).items, i1, 44, 39 + i1 * 25));
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
        return null;
    }

    public boolean m_6875_(Player entityplayer) {
        return true;
    }
}

