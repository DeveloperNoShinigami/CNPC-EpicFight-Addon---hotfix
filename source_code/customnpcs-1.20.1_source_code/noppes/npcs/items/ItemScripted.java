/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.items;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemScriptedWrapper;

public class ItemScripted
extends Item {
    public ItemScripted(Item.Properties props) {
        super(props);
    }

    public static ItemScriptedWrapper GetWrapper(ItemStack stack) {
        return (ItemScriptedWrapper)NpcAPI.Instance().getIItemStack(stack);
    }

    public boolean m_142522_(ItemStack stack) {
        IItemStack istack = NpcAPI.Instance().getIItemStack(stack);
        if (istack instanceof ItemScriptedWrapper) {
            return ((ItemScriptedWrapper)istack).durabilityShow;
        }
        return super.m_142522_(stack);
    }

    public int m_142158_(ItemStack stack) {
        IItemStack istack = NpcAPI.Instance().getIItemStack(stack);
        if (istack instanceof ItemScriptedWrapper) {
            return Math.round(13.0f - ((ItemScriptedWrapper)istack).durabilityValue * 13.0f);
        }
        return super.m_142158_(stack);
    }

    public int m_142159_(ItemStack stack) {
        IItemStack istack = NpcAPI.Instance().getIItemStack(stack);
        if (!(istack instanceof ItemScriptedWrapper)) {
            return super.m_142159_(stack);
        }
        int color = ((ItemScriptedWrapper)istack).durabilityColor;
        if (color >= 0) {
            return color;
        }
        return Mth.m_14169_((float)(Math.max(0.0f, 1.0f - (float)this.m_142158_(stack)) / 3.0f), (float)1.0f, (float)1.0f);
    }

    public int getMaxStackSize(ItemStack stack) {
        IItemStack istack = NpcAPI.Instance().getIItemStack(stack);
        if (istack instanceof ItemScriptedWrapper) {
            return ((ItemScriptedWrapper)istack).getMaxStackSize();
        }
        return super.getMaxStackSize(stack);
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    public boolean m_41468_() {
        return true;
    }

    public CompoundTag getShareTag(ItemStack stack) {
        IItemStack istack = NpcAPI.Instance().getIItemStack(stack);
        CompoundTag generalTag = super.getShareTag(stack);
        if (istack instanceof ItemScriptedWrapper) {
            if (generalTag != null) {
                return generalTag.m_128391_(((ItemScriptedWrapper)istack).getMCNbt());
            }
            return ((ItemScriptedWrapper)istack).getMCNbt();
        }
        return generalTag;
    }

    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if (nbt == null) {
            return;
        }
        super.readShareTag(stack, nbt);
        IItemStack istack = NpcAPI.Instance().getIItemStack(stack);
        if (istack instanceof ItemScriptedWrapper) {
            ((ItemScriptedWrapper)istack).setMCNbt(nbt);
        }
    }
}

