/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 */
package noppes.npcs.blocks;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public abstract class BlockInterface
extends BaseEntityBlock
implements EntityBlock {
    protected BlockInterface(BlockBehaviour.Properties properties) {
        super(properties);
    }
}

