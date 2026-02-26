/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 */
package noppes.npcs.constants;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum EnumCompanionTalent {
    INVENTORY(Item.m_41439_((Block)Blocks.f_50091_)),
    ARMOR(Items.f_42469_),
    SWORD(Items.f_42388_),
    RANGED(Items.f_42411_),
    ACROBATS(Items.f_42463_),
    INTEL(Items.f_42517_);

    public ItemStack item;

    private EnumCompanionTalent(Item item) {
        this.item = new ItemStack((ItemLike)item);
    }
}

