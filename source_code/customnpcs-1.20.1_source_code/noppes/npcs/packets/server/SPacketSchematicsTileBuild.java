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
import noppes.npcs.schematics.SchematicWrapper;

public class SPacketSchematicsTileBuild
extends PacketServerBasic {
    private BlockPos pos;

    public SPacketSchematicsTileBuild(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.wand || item.m_41720_() == CustomBlocks.builder_item || item.m_41720_() == CustomBlocks.copy_item;
    }

    public static void encode(SPacketSchematicsTileBuild msg, FriendlyByteBuf buf) {
        buf.m_130064_(msg.pos);
    }

    public static SPacketSchematicsTileBuild decode(FriendlyByteBuf buf) {
        return new SPacketSchematicsTileBuild(buf.m_130135_());
    }

    @Override
    protected void handle() {
        TileBuilder tile = (TileBuilder)this.player.m_9236_().m_7702_(this.pos);
        SchematicWrapper schem = tile.getSchematic();
        schem.init(this.pos.m_7918_(1, tile.yOffest, 1), this.player.m_9236_(), tile.rotation * 90);
        SchematicController.Instance.build(tile.getSchematic(), this.player.m_20203_());
        this.player.m_9236_().m_7471_(this.pos, false);
    }
}

