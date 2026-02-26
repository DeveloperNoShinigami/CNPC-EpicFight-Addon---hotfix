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

public class TileMailbox
extends TileNpcEntity {
    private int type = 0;

    public TileMailbox(BlockPos pos, BlockState state) {
        super(CustomBlocks.tile_mailbox, pos, state);
    }

    public TileMailbox setModel(int type) {
        this.type = type;
        return this;
    }

    public int getModel() {
        return this.type;
    }
}

