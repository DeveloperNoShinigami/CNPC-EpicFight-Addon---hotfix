/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.api.wrapper;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.api.item.IItemBlock;
import noppes.npcs.api.wrapper.ItemStackWrapper;

public class ItemBlockWrapper
extends ItemStackWrapper
implements IItemBlock {
    protected String blockName;

    protected ItemBlockWrapper(ItemStack item) {
        super(item);
        Block b = Block.m_49814_((Item)item.m_41720_());
        this.blockName = "" + ForgeRegistries.BLOCKS.getKey((Object)b);
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String getBlockName() {
        return this.blockName;
    }
}

