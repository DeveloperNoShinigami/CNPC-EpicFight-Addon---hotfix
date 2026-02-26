/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs;

import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.entity.data.IData;
import noppes.npcs.api.wrapper.ItemStackWrapper;

public class ItemStackEmptyWrapper
extends ItemStackWrapper {
    public ItemStackEmptyWrapper() {
        super(ItemStack.f_41583_);
    }

    @Override
    public IData getTempdata() {
        return null;
    }

    @Override
    public IData getStoreddata() {
        return null;
    }
}

