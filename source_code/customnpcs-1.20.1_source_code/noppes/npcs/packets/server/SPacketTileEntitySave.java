/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package noppes.npcs.packets.server;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketTileEntitySave
extends PacketServerBasic {
    private CompoundTag data;

    public SPacketTileEntitySave(CompoundTag data) {
        this.data = data;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.wand || item.m_41720_() == CustomBlocks.border_item || item.m_41720_() == CustomBlocks.copy_item || item.m_41720_() == CustomBlocks.redstone_item || item.m_41720_() == CustomBlocks.scripted_item || item.m_41720_() == CustomBlocks.waypoint_item;
    }

    public static void encode(SPacketTileEntitySave msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static SPacketTileEntitySave decode(FriendlyByteBuf buf) {
        return new SPacketTileEntitySave(buf.m_130260_());
    }

    @Override
    protected void handle() {
        SPacketTileEntitySave.saveTileEntity(this.player, this.data);
    }

    public static BlockEntity saveTileEntity(ServerPlayer player, CompoundTag compound) {
        int x = compound.m_128451_("x");
        int y = compound.m_128451_("y");
        int z = compound.m_128451_("z");
        BlockEntity tile = player.m_9236_().m_7702_(new BlockPos(x, y, z));
        if (tile != null) {
            tile.m_142466_(compound);
        }
        player.m_9236_().m_151543_(new BlockPos(x, y, z));
        return tile;
    }
}

