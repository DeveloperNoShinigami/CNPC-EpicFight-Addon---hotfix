/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.client.gui.custom.components;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.EventHooks;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.gui.IItemSlot;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiItemSlotWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.containers.ContainerCustomGui;

public class CustomGuiSlot
extends Slot {
    private final Player player;
    public final IItemSlot slot;
    private final CustomGuiWrapper gui;
    private static Field xField;
    private static Field yField;

    public CustomGuiSlot(CustomGuiWrapper gui, Container inventoryIn, int id, IItemSlot slot, Player player) {
        super(inventoryIn, id, -666667, -666666);
        this.gui = gui;
        this.player = player;
        this.slot = slot;
        if (yField == null) {
            for (Field f : Slot.class.getDeclaredFields()) {
                if (Modifier.isPrivate(f.getModifiers())) continue;
                try {
                    Integer i;
                    Object object = f.get((Object)this);
                    if (object instanceof Integer && (i = (Integer)object) == -666666) {
                        yField = f;
                        yField.setAccessible(true);
                    }
                    if (!((object = f.get((Object)this)) instanceof Integer) || (i = (Integer)object) != -666667) continue;
                    xField = f;
                    xField.setAccessible(true);
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        this.update(0, 0);
    }

    public CustomGuiSlot update(int x, int y) {
        try {
            xField.set((Object)this, x + this.slot.getPosX());
            yField.set((Object)this, y + this.slot.getPosY());
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void m_5852_(ItemStack is) {
        super.m_5852_(is);
        if (!this.player.m_9236_().f_46443_ && this.m_7993_() != this.slot.getStack().getMCItemStack()) {
            AbstractContainerMenu abstractContainerMenu;
            if (!this.slot.isPlayerSlot()) {
                this.slot.setStack(NpcAPI.Instance().getIItemStack(this.m_7993_()));
                ((CustomGuiItemSlotWrapper)this.slot).onUpdate(this.gui);
            }
            if ((abstractContainerMenu = this.player.f_36096_) instanceof ContainerCustomGui) {
                ContainerCustomGui container = (ContainerCustomGui)abstractContainerMenu;
                EventHooks.onCustomGuiSlot((PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)this.player), container.customGui, this.slot);
            }
        }
    }
}

