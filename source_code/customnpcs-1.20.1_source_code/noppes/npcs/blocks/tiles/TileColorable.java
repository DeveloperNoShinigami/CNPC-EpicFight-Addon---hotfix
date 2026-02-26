/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package noppes.npcs.blocks.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import noppes.npcs.blocks.tiles.TileNpcEntity;

public class TileColorable
extends TileNpcEntity {
    public int color = 14;
    public int rotation;

    public TileColorable(BlockEntityType<?> p_i48289_1_, BlockPos pos, BlockState state) {
        super(p_i48289_1_, pos, state);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.color = compound.m_128451_("BannerColor");
        this.rotation = compound.m_128451_("BannerRotation");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        compound.m_128405_("BannerColor", this.color);
        compound.m_128405_("BannerRotation", this.rotation);
        super.m_183515_(compound);
    }

    public boolean canUpdate() {
        return false;
    }

    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag compound = pkt.m_131708_();
        this.m_142466_(compound);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.m_195640_((BlockEntity)this);
    }

    public CompoundTag m_5995_() {
        CompoundTag compound = new CompoundTag();
        this.m_183515_(compound);
        compound.m_128473_("Items");
        compound.m_128473_("ExtraData");
        return compound;
    }

    public AABB getRenderBoundingBox() {
        return new AABB((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_(), (double)(this.f_58858_.m_123341_() + 1), (double)(this.f_58858_.m_123342_() + 1), (double)(this.f_58858_.m_123343_() + 1));
    }

    public int powerProvided() {
        return 0;
    }
}

