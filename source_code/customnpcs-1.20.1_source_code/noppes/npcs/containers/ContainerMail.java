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
import noppes.npcs.containers.ContainerNpcInterface;
import noppes.npcs.containers.SlotValid;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerMail;
import noppes.npcs.controllers.data.PlayerMailData;

public class ContainerMail
extends ContainerNpcInterface {
    public static PlayerMail staticmail = new PlayerMail();
    public PlayerMail mail = staticmail;
    public final boolean canEdit;
    public final boolean canSend;

    public ContainerMail(int containerId, Inventory playerInventory, boolean canEdit, boolean canSend) {
        super(CustomContainer.container_mail, containerId, playerInventory);
        int j;
        int k;
        staticmail = new PlayerMail();
        this.canEdit = canEdit;
        this.canSend = canSend;
        playerInventory.m_5856_(this.player);
        for (k = 0; k < 4; ++k) {
            this.m_38897_(new SlotValid(this.mail, k, 179 + k * 24, 138, canEdit));
        }
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.m_38897_(new Slot((Container)playerInventory, k + j * 9 + 9, 28 + k * 18, 175 + j * 18));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.m_38897_(new Slot((Container)playerInventory, j, 28 + j * 18, 230));
        }
    }

    @Override
    public ItemStack m_7648_(Player par1Player, int limbSwingAmount) {
        ItemStack itemstack = ItemStack.f_41583_;
        Slot slot = (Slot)this.f_38839_.get(limbSwingAmount);
        if (slot != null && slot.m_6657_()) {
            ItemStack itemstack1 = slot.m_7993_();
            itemstack = itemstack1.m_41777_();
            if (limbSwingAmount < 4) {
                if (!this.m_38903_(itemstack1, 4, this.f_38839_.size(), true)) {
                    return ItemStack.f_41583_;
                }
            } else if (!this.canEdit || !this.m_38903_(itemstack1, 0, 4, false)) {
                return null;
            }
            if (itemstack1.m_41613_() == 0) {
                slot.m_5852_(ItemStack.f_41583_);
            } else {
                slot.m_6654_();
            }
        }
        return itemstack;
    }

    public void m_6877_(Player player) {
        super.m_6877_(player);
        if (!this.canEdit && !player.m_9236_().f_46443_) {
            PlayerMailData data = PlayerData.get((Player)player).mailData;
            for (PlayerMail mail : data.playermail) {
                if (mail.time != this.mail.time || !mail.sender.equals(this.mail.sender)) continue;
                mail.readNBT(this.mail.writeNBT());
                break;
            }
        }
    }
}

