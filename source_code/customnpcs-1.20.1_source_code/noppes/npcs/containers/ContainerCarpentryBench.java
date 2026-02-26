/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.inventory.ResultContainer
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.inventory.TransientCraftingContainer
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomContainer;
import noppes.npcs.containers.SlotNpcCrafting;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.RecipeCarpentry;

public class ContainerCarpentryBench
extends AbstractContainerMenu {
    public CraftingContainer craftMatrix = new TransientCraftingContainer((AbstractContainerMenu)this, 4, 4);
    public Container craftResult = new ResultContainer();
    private Player player;
    private BlockPos pos;

    public ContainerCarpentryBench(int id, Inventory par1PlayerInventory, BlockPos pos) {
        super(CustomContainer.container_carpentrybench, id);
        int var7;
        int var6;
        this.pos = pos;
        this.player = par1PlayerInventory.f_35978_;
        this.m_38897_((Slot)new SlotNpcCrafting(par1PlayerInventory.f_35978_, this.craftMatrix, this.craftResult, 0, 133, 41));
        for (var6 = 0; var6 < 4; ++var6) {
            for (var7 = 0; var7 < 4; ++var7) {
                this.m_38897_(new Slot((Container)this.craftMatrix, var7 + var6 * 4, 17 + var7 * 18, 14 + var6 * 18));
            }
        }
        for (var6 = 0; var6 < 3; ++var6) {
            for (var7 = 0; var7 < 9; ++var7) {
                this.m_38897_(new Slot((Container)par1PlayerInventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 98 + var6 * 18));
            }
        }
        for (var6 = 0; var6 < 9; ++var6) {
            this.m_38897_(new Slot((Container)par1PlayerInventory, var6, 8 + var6 * 18, 156));
        }
        this.m_6199_((Container)this.craftMatrix);
    }

    public void m_6199_(Container par1Container) {
        if (!this.player.m_9236_().f_46443_) {
            RecipeCarpentry recipe = RecipeController.instance.findMatchingRecipe(this.craftMatrix);
            ItemStack item = ItemStack.f_41583_;
            if (recipe != null && recipe.availability.isAvailable(this.player)) {
                item = recipe.m_5874_(this.craftMatrix, this.player.m_9236_().m_9598_());
            }
            this.craftResult.m_6836_(0, item);
            ServerPlayer plmp = (ServerPlayer)this.player;
            plmp.f_8906_.m_9829_((Packet)new ClientboundContainerSetSlotPacket(this.f_38840_, this.m_182425_(), 0, item));
        }
    }

    public void m_6877_(Player par1Player) {
        super.m_6877_(par1Player);
        if (!par1Player.m_9236_().f_46443_) {
            for (int var2 = 0; var2 < 16; ++var2) {
                ItemStack var3 = this.craftMatrix.m_8016_(var2);
                if (var3 == null) continue;
                par1Player.m_36176_(var3, false);
            }
        }
    }

    public boolean m_6875_(Player par1Player) {
        return par1Player.m_9236_().m_8055_(this.pos).m_60734_() == CustomBlocks.carpenty && par1Player.m_20275_((double)this.pos.m_123341_() + 0.5, (double)this.pos.m_123342_() + 0.5, (double)this.pos.m_123343_() + 0.5) <= 64.0;
    }

    public ItemStack m_7648_(Player par1Player, int par1) {
        ItemStack var2 = ItemStack.f_41583_;
        Slot var3 = (Slot)this.f_38839_.get(par1);
        if (var3 != null && var3.m_6657_()) {
            ItemStack var4 = var3.m_7993_();
            var2 = var4.m_41777_();
            if (par1 == 0) {
                if (!this.m_38903_(var4, 17, 53, true)) {
                    return ItemStack.f_41583_;
                }
                var3.m_40234_(var4, var2);
            } else if (par1 >= 17 && par1 < 44 ? !this.m_38903_(var4, 44, 53, false) : (par1 >= 44 && par1 < 53 ? !this.m_38903_(var4, 17, 44, false) : !this.m_38903_(var4, 17, 53, false))) {
                return ItemStack.f_41583_;
            }
            if (var4.m_41613_() == 0) {
                var3.m_5852_(ItemStack.f_41583_);
            } else {
                var3.m_6654_();
            }
            if (var4.m_41613_() == var2.m_41613_()) {
                return ItemStack.f_41583_;
            }
            var3.m_142406_(par1Player, var4);
        }
        return var2;
    }

    public boolean m_5882_(ItemStack stack, Slot slotIn) {
        return slotIn.f_40218_ != this.craftResult && super.m_5882_(stack, slotIn);
    }
}

