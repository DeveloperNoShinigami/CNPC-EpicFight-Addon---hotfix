/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.blocks.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomBlocks;
import noppes.npcs.blocks.tiles.TileNpcEntity;

public class TileDoor
extends TileNpcEntity {
    public int tickCount = 0;
    public Block blockModel = CustomBlocks.scripted_door;
    public boolean needsClientUpdate = false;

    public TileDoor(BlockEntityType<?> p_i48289_1_, BlockPos pos, BlockState state) {
        super(p_i48289_1_, pos, state);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.setDoorNBT(compound);
    }

    public void setDoorNBT(CompoundTag compound) {
        this.blockModel = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(compound.m_128461_("ScriptDoorBlockModel")));
        if (this.blockModel == null || !(this.blockModel instanceof DoorBlock)) {
            this.blockModel = CustomBlocks.scripted_door;
        }
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        this.getDoorNBT(compound);
        super.m_183515_(compound);
    }

    public CompoundTag getDoorNBT(CompoundTag compound) {
        compound.m_128359_("ScriptDoorBlockModel", "" + ForgeRegistries.BLOCKS.getKey((Object)this.blockModel));
        return compound;
    }

    public void setItemModel(Block block) {
        if (block == null || !(block instanceof DoorBlock)) {
            block = CustomBlocks.scripted_door;
        }
        if (this.blockModel == block) {
            return;
        }
        this.blockModel = block;
        this.needsClientUpdate = true;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileDoor tile) {
        ++tile.tickCount;
        if (tile.tickCount >= 10) {
            tile.tickCount = 0;
            if (tile.needsClientUpdate) {
                tile.m_6596_();
                level.m_46597_(pos, state);
                tile.needsClientUpdate = false;
            }
        }
    }

    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.handleUpdateTag(pkt.m_131708_());
    }

    public void handleUpdateTag(CompoundTag compound) {
        this.setDoorNBT(compound);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.m_195640_((BlockEntity)this);
    }

    public CompoundTag m_5995_() {
        CompoundTag compound = new CompoundTag();
        compound.m_128405_("x", this.f_58858_.m_123341_());
        compound.m_128405_("y", this.f_58858_.m_123342_());
        compound.m_128405_("z", this.f_58858_.m_123343_());
        this.getDoorNBT(compound);
        return compound;
    }
}

