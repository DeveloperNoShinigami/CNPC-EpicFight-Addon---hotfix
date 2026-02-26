/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.tiles.TileBuilder;
import noppes.npcs.controllers.SchematicController;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiData;

public class SPacketSchematicsTileSet
extends PacketServerBasic {
    private BlockPos pos;
    private String name;

    public SPacketSchematicsTileSet(BlockPos pos, String name) {
        this.pos = pos;
        this.name = name;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.wand || item.m_41720_() == CustomBlocks.builder_item || item.m_41720_() == CustomBlocks.copy_item;
    }

    public static void encode(SPacketSchematicsTileSet msg, FriendlyByteBuf buf) {
        buf.m_130064_(msg.pos);
        buf.m_130070_(msg.name);
    }

    public static SPacketSchematicsTileSet decode(FriendlyByteBuf buf) {
        return new SPacketSchematicsTileSet(buf.m_130135_(), buf.m_130136_(Short.MAX_VALUE));
    }

    @Override
    protected void handle() {
        TileBuilder tile = (TileBuilder)this.player.m_9236_().m_7702_(this.pos);
        tile.setSchematic(SchematicController.Instance.load(this.name));
        if (tile.hasSchematic()) {
            Packets.send(this.player, new PacketGuiData(tile.getSchematic().getNBTSmall()));
        }
    }
}

