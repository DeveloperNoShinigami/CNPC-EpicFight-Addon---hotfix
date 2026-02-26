/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package noppes.npcs.blocks.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import noppes.npcs.CustomBlocks;
import noppes.npcs.blocks.tiles.TileNpcEntity;

public class TileBlockAnvil
extends TileNpcEntity {
    public TileBlockAnvil(BlockPos pos, BlockState state) {
        super(CustomBlocks.tile_anvil, pos, state);
    }

    public boolean canUpdate() {
        return false;
    }
}

