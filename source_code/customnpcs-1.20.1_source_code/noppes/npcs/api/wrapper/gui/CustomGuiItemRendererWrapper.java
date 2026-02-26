/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.gui.IItemRenderer;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;

public class CustomGuiItemRendererWrapper
extends CustomGuiComponentWrapper
implements IItemRenderer {
    IItemStack stack;
    public int width;
    public int height;
    public float scale;

    public CustomGuiItemRendererWrapper() {
        this.stack = ItemStackWrapper.AIR;
    }

    public CustomGuiItemRendererWrapper(int id, int x, int y, int width, int height, IItemStack stack) {
        this.setID(id);
        this.stack = ItemStackWrapper.AIR;
        this.scale = 1.0f;
        this.setPos(x, y);
        this.setStack(stack);
        this.setHoverBox(width, height);
    }

    @Override
    public boolean hasStack() {
        return !this.stack.isEmpty();
    }

    @Override
    public IItemStack getStack() {
        return this.stack;
    }

    @Override
    public IItemRenderer setStack(IItemStack itemStack) {
        this.stack = itemStack == null ? ItemStackWrapper.AIR : itemStack;
        return this;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public IItemRenderer setHoverBox(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public float getScale() {
        return this.scale;
    }

    @Override
    public IItemRenderer setScale(float scaleFactor) {
        this.scale = scaleFactor;
        return this;
    }

    @Override
    public int getType() {
        return 12;
    }

    @Override
    public CompoundTag toNBT(CompoundTag nbt) {
        super.toNBT(nbt);
        nbt.m_128365_("stack", (Tag)this.stack.getMCItemStack().serializeNBT());
        nbt.m_128350_("scale", this.scale);
        nbt.m_128405_("width", this.width);
        nbt.m_128405_("height", this.height);
        return nbt;
    }

    @Override
    public CustomGuiComponentWrapper fromNBT(CompoundTag nbt) {
        super.fromNBT(nbt);
        this.setStack(NpcAPI.Instance().getIItemStack(ItemStack.m_41712_((CompoundTag)nbt.m_128469_("stack"))));
        this.setScale(nbt.m_128457_("scale"));
        this.setHoverBox(nbt.m_128451_("width"), nbt.m_128451_("height"));
        return this;
    }
}

