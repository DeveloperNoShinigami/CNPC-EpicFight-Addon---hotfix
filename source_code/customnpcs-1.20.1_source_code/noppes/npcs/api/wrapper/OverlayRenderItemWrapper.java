/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.overlay.IRenderItemOverlay;
import noppes.npcs.api.wrapper.OverlayComponentWrapper;

public class OverlayRenderItemWrapper
extends OverlayComponentWrapper
implements IRenderItemOverlay {
    private ItemStack item;

    public OverlayRenderItemWrapper(int id, int x, int y, IItemStack item) {
        super(id, x, y);
        this.item = item == null ? ItemStack.f_41583_ : item.getMCItemStack();
    }

    @Override
    public IItemStack getItem() {
        return NpcAPI.Instance().getIItemStack(this.item);
    }

    @Override
    public IRenderItemOverlay setItem(IItemStack item) {
        this.item = item.getMCItemStack();
        return this;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void toNbt(CompoundTag compound) {
        super.toNbt(compound);
        compound.m_128365_("item", (Tag)this.item.serializeNBT());
    }

    @Override
    public void fromNbt(CompoundTag compound) {
        super.fromNbt(compound);
        this.item = ItemStack.m_41712_((CompoundTag)compound.m_128469_("item"));
    }
}

