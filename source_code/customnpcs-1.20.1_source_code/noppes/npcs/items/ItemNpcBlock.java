/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.client.extensions.common.IClientItemExtensions
 */
package noppes.npcs.items;

import java.util.function.Consumer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import noppes.npcs.client.CustomTileEntityItemStackRenderer;

public class ItemNpcBlock
extends BlockItem {
    public final Block block;

    public ItemNpcBlock(Block block, Item.Properties builder) {
        super(block, builder);
        this.block = block;
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(CustomTileEntityItemStackRenderer.itemRenderProperties);
    }
}

