/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.inventory.Slot
 */
package noppes.npcs.api.gui;

import net.minecraft.world.inventory.Slot;
import noppes.npcs.api.function.gui.GuiItemSlotUpdate;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.item.IItemStack;

public interface IItemSlot
extends ICustomGuiComponent {
    public boolean hasStack();

    public IItemStack getStack();

    public IItemSlot setStack(IItemStack var1);

    public int getGuiType();

    public IItemSlot setGuiType(int var1);

    public boolean isPlayerSlot();

    public IItemSlot setOnUpdate(GuiItemSlotUpdate var1);

    public Slot getMCSlot();
}

