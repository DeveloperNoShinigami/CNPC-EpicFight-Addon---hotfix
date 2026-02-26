/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomNpcs;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.function.gui.GuiItemSlotUpdate;
import noppes.npcs.api.gui.ICustomGui;
import noppes.npcs.api.gui.IItemSlot;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;

public class CustomGuiItemSlotWrapper
extends CustomGuiComponentWrapper
implements IItemSlot {
    private IItemStack stack = ItemStackWrapper.AIR;
    private int guiType = 1;
    private Player player;
    private GuiItemSlotUpdate onSlotUpdate = null;

    public CustomGuiItemSlotWrapper() {
    }

    public CustomGuiItemSlotWrapper(int x, int y, IItemStack stack) {
        this.setPos(x, y);
        this.setSize(14, 14);
        this.setStack(stack);
    }

    public CustomGuiItemSlotWrapper(int x, int y, Player player) {
        this.player = player;
        this.setPos(x, y);
        this.setSize(14, 14);
    }

    @Override
    public boolean hasStack() {
        return !this.stack.isEmpty();
    }

    @Override
    public IItemStack getStack() {
        if (this.player != null) {
            this.stack = NpcAPI.Instance().getIItemStack(this.player.m_150109_().m_8020_(this.getID()));
        }
        return this.stack;
    }

    @Override
    public IItemSlot setStack(IItemStack itemStack) {
        this.stack = itemStack == null ? ItemStackWrapper.AIR : itemStack;
        if (this.player != null) {
            this.player.m_150109_().m_6836_(this.getID(), this.stack.getMCItemStack());
        }
        return this;
    }

    @Override
    public int getGuiType() {
        return this.guiType;
    }

    @Override
    public CustomGuiItemSlotWrapper setGuiType(int type) {
        this.guiType = type;
        return this;
    }

    @Override
    public Slot getMCSlot() {
        return null;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public CompoundTag toNBT(CompoundTag nbt) {
        super.toNBT(nbt);
        nbt.m_128365_("stack", (Tag)this.stack.getMCItemStack().serializeNBT());
        nbt.m_128405_("guiType", this.guiType);
        nbt.m_128379_("playerSlot", this.isPlayerSlot());
        return nbt;
    }

    @Override
    public CustomGuiComponentWrapper fromNBT(CompoundTag nbt) {
        super.fromNBT(nbt);
        this.setStack(NpcAPI.Instance().getIItemStack(ItemStack.m_41712_((CompoundTag)nbt.m_128469_("stack"))));
        this.setGuiType(nbt.m_128451_("guiType"));
        if (nbt.m_128471_("playerSlot")) {
            this.player = CustomNpcs.proxy.getPlayer();
        }
        return this;
    }

    @Override
    public boolean isPlayerSlot() {
        return this.player != null;
    }

    @Override
    public CustomGuiItemSlotWrapper setOnUpdate(GuiItemSlotUpdate onPress) {
        this.onSlotUpdate = onPress;
        return this;
    }

    public final void onUpdate(ICustomGui gui) {
        if (this.onSlotUpdate != null) {
            this.onSlotUpdate.onUpdate(gui, this);
        }
    }
}

