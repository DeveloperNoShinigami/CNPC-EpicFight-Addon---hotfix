/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.Container
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomContainer;
import noppes.npcs.EventHooks;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.event.CustomGuiEvent;
import noppes.npcs.api.gui.IItemSlot;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.api.wrapper.gui.GuiComponentsScrollableWrapper;
import noppes.npcs.client.gui.custom.components.CustomGuiSlot;
import noppes.npcs.mixin.AbstractContainerMenuMixin;
import noppes.npcs.util.CustomNPCsScheduler;

public class ContainerCustomGui
extends AbstractContainerMenu {
    public CustomGuiWrapper customGui;
    public CustomGuiWrapper activeGui;
    public SimpleContainer guiInventory;
    public CompoundTag data;

    public ContainerCustomGui(int containerId, CompoundTag data) {
        super(CustomContainer.container_customgui, containerId);
        this.data = data;
        this.guiInventory = new SimpleContainer(0);
    }

    public boolean m_6875_(Player playerIn) {
        return true;
    }

    public void setGui(CustomGuiWrapper gui, Player player) {
        this.activeGui = gui.getActiveGui();
        this.guiInventory = new SimpleContainer(this.activeGui.getSlots().size() + this.activeGui.getScrollingPanel().getSlots().size());
        this.customGui = gui;
        AbstractContainerMenuMixin mix = (AbstractContainerMenuMixin)((Object)this);
        this.f_38839_.clear();
        mix.remoteSlots().clear();
        mix.lastSlots().clear();
        for (IItemSlot slot : this.activeGui.getSlots()) {
            Slot s = this.m_38897_(new CustomGuiSlot(gui, (Container)this.guiInventory, slot.getID(), slot, player));
            this.guiInventory.m_6836_(s.f_40219_, slot.getStack().getMCItemStack());
        }
        GuiComponentsScrollableWrapper panel = this.activeGui.getScrollingPanel();
        for (IItemSlot slot : panel.getSlots()) {
            Slot s = this.m_38897_(new CustomGuiSlot(gui, (Container)this.guiInventory, slot.getID(), slot, player).update(panel.x, panel.y));
            this.guiInventory.m_6836_(s.f_40219_, slot.getStack().getMCItemStack());
        }
        for (IItemSlot slot : this.activeGui.getPlayerSlots()) {
            this.m_38897_(new CustomGuiSlot(gui, (Container)player.m_150109_(), slot.getID(), slot, player));
        }
        this.update();
    }

    public void update() {
        GuiComponentsScrollableWrapper panel = this.activeGui.getScrollingPanel();
        for (int i = 0; i < this.activeGui.getScrollingPanel().getSlots().size(); ++i) {
            CustomGuiSlot slot = (CustomGuiSlot)this.m_38853_(i + this.activeGui.getSlots().size());
            if (panel.isVisible(slot.slot)) {
                slot.update(panel.x, panel.y - panel.scrollAmount);
                continue;
            }
            slot.update(-1073741824, -1073741824);
        }
    }

    public ItemStack m_7648_(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.f_41583_;
        Slot slot = (Slot)this.f_38839_.get(index);
        if (slot.m_6657_()) {
            ItemStack itemstack1 = slot.m_7993_();
            itemstack = itemstack1.m_41777_();
            if (index < this.guiInventory.m_6643_() ? !this.m_38903_(itemstack1, this.guiInventory.m_6643_(), this.f_38839_.size(), true) : !this.m_38903_(itemstack1, 0, this.guiInventory.m_6643_(), false)) {
                return ItemStack.f_41583_;
            }
            if (itemstack1.m_41619_()) {
                slot.m_5852_(ItemStack.f_41583_);
            } else {
                slot.m_6654_();
            }
        }
        return itemstack;
    }

    public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId < 0) {
            super.m_150399_(slotId, dragType, clickTypeIn, player);
            return;
        }
        if (!player.m_9236_().f_46443_) {
            CustomGuiSlot slot = (CustomGuiSlot)this.m_38853_(slotId);
            CustomGuiEvent.SlotClickEvent event = new CustomGuiEvent.SlotClickEvent((PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)player), ((ContainerCustomGui)player.f_36096_).activeGui, slot.slot, dragType, clickTypeIn.toString(), NpcAPI.Instance().getIItemStack(this.m_142621_()));
            if (!EventHooks.onCustomGuiSlotClicked(event)) {
                this.m_142503_(event.carried.getMCItemStack());
                super.m_150399_(slotId, dragType, clickTypeIn, player);
                CustomNPCsScheduler.runTack(() -> ((ContainerCustomGui)this).m_150429_(), 10);
            } else {
                this.m_142503_(event.carried.getMCItemStack());
            }
        }
    }

    public void m_6877_(Player player) {
        super.m_6877_(player);
        if (!player.m_9236_().f_46443_) {
            EventHooks.onCustomGuiClose((PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)player), this.customGui);
        }
    }
}

