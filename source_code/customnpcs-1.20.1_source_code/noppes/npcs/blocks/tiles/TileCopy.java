/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package noppes.npcs.blocks.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import noppes.npcs.CustomBlocks;

public class TileCopy
extends BlockEntity {
    public short length = (short)10;
    public short width = (short)10;
    public short height = (short)10;
    public String name = "";

    public TileCopy(BlockPos pos, BlockState state) {
        super(CustomBlocks.tile_copy, pos, state);
    }

    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.length = compound.m_128448_("Length");
        this.width = compound.m_128448_("Width");
        this.height = compound.m_128448_("Height");
        this.name = compound.m_128461_("Name");
    }

    public void m_183515_(CompoundTag compound) {
        compound.m_128376_("Length", this.length);
        compound.m_128376_("Width", this.width);
        compound.m_128376_("Height", this.height);
        compound.m_128359_("Name", this.name);
        super.m_183515_(compound);
    }

    public void handleUpdateTag(CompoundTag compound) {
        this.length = compound.m_128448_("Length");
        this.width = compound.m_128448_("Width");
        this.height = compound.m_128448_("Height");
    }

    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.handleUpdateTag(pkt.m_131708_());
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.m_195640_((BlockEntity)this);
    }

    public CompoundTag m_5995_() {
        CompoundTag compound = new CompoundTag();
        compound.m_128405_("x", this.f_58858_.m_123341_());
        compound.m_128405_("y", this.f_58858_.m_123342_());
        compound.m_128405_("z", this.f_58858_.m_123343_());
        compound.m_128376_("Length", this.length);
        compound.m_128376_("Width", this.width);
        compound.m_128376_("Height", this.height);
        return compound;
    }

    public AABB getRenderBoundingBox() {
        return new AABB((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_(), (double)(this.f_58858_.m_123341_() + this.width + 1), (double)(this.f_58858_.m_123342_() + this.height + 1), (double)(this.f_58858_.m_123343_() + this.length + 1));
    }
}

