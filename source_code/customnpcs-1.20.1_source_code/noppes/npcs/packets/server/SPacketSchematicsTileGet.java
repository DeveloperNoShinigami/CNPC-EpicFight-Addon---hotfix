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

import java.util.Vector;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.tiles.TileBuilder;
import noppes.npcs.controllers.SchematicController;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiData;
import noppes.npcs.packets.client.PacketGuiScrollList;

public class SPacketSchematicsTileGet
extends PacketServerBasic {
    private BlockPos pos;

    public SPacketSchematicsTileGet(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.wand || item.m_41720_() == CustomBlocks.builder_item || item.m_41720_() == CustomBlocks.copy_item;
    }

    public static void encode(SPacketSchematicsTileGet msg, FriendlyByteBuf buf) {
        buf.m_130064_(msg.pos);
    }

    public static SPacketSchematicsTileGet decode(FriendlyByteBuf buf) {
        return new SPacketSchematicsTileGet(buf.m_130135_());
    }

    @Override
    protected void handle() {
        TileBuilder tile = (TileBuilder)this.player.m_9236_().m_7702_(this.pos);
        if (tile == null) {
            return;
        }
        Packets.send(this.player, new PacketGuiData(tile.writePartNBT(new CompoundTag())));
        Packets.send(this.player, new PacketGuiScrollList(new Vector<String>(SchematicController.Instance.list())));
        if (tile.hasSchematic()) {
            Packets.send(this.player, new PacketGuiData(tile.getSchematic().getNBTSmall()));
        }
    }
}

