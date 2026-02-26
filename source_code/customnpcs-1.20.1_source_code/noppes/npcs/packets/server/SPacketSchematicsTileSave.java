/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.tiles.TileBuilder;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketSchematicsTileSave
extends PacketServerBasic {
    private BlockPos pos;
    private CompoundTag data;

    public SPacketSchematicsTileSave(BlockPos pos, CompoundTag data) {
        this.pos = pos;
        this.data = data;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.wand || item.m_41720_() == CustomBlocks.builder_item || item.m_41720_() == CustomBlocks.copy_item;
    }

    public static void encode(SPacketSchematicsTileSave msg, FriendlyByteBuf buf) {
        buf.m_130064_(msg.pos);
        buf.m_130079_(msg.data);
    }

    public static SPacketSchematicsTileSave decode(FriendlyByteBuf buf) {
        return new SPacketSchematicsTileSave(buf.m_130135_(), buf.m_130260_());
    }

    @Override
    protected void handle() {
        TileBuilder tile = (TileBuilder)this.player.m_9236_().m_7702_(this.pos);
        if (tile != null) {
            tile.readPartNBT(this.data);
        }
    }
}

