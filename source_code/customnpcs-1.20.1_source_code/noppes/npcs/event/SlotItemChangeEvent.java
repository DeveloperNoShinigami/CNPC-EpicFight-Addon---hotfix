/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.eventbus.api.Cancelable
 *  net.minecraftforge.eventbus.api.Event
 */
package noppes.npcs.event;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class SlotItemChangeEvent
extends Event {
    private final int slotIndex;
    private ItemStack slotStack;
    private final ItemStack handStack;
    private final Container container;

    public SlotItemChangeEvent(int slotIndex, ItemStack slotStack, ItemStack handStack, Container container) {
        this.slotIndex = slotIndex;
        this.slotStack = slotStack;
        this.handStack = handStack;
        this.container = container;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public ItemStack getSlotStack() {
        return this.slotStack;
    }

    public void setSlotStack(ItemStack slotStack) {
        this.slotStack = slotStack;
    }

    public ItemStack getHandStack() {
        return this.handStack;
    }

    public Container getContainer() {
        return this.container;
    }
}

