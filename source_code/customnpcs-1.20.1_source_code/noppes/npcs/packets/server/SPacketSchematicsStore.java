/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.tiles.TileCopy;
import noppes.npcs.controllers.SchematicController;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.server.SPacketTileEntitySave;

public class SPacketSchematicsStore
extends PacketServerBasic {
    private String name;
    private CompoundTag data;

    public SPacketSchematicsStore(String name, CompoundTag data) {
        this.name = name;
        this.data = data;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.wand || item.m_41720_() == CustomBlocks.copy_item;
    }

    public static void encode(SPacketSchematicsStore msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.name);
        buf.m_130079_(msg.data);
    }

    public static SPacketSchematicsStore decode(FriendlyByteBuf buf) {
        return new SPacketSchematicsStore(buf.m_130136_(Short.MAX_VALUE), buf.m_130260_());
    }

    @Override
    protected void handle() {
        TileCopy tile = (TileCopy)SPacketTileEntitySave.saveTileEntity(this.player, this.data);
        if (tile == null || this.name.isEmpty()) {
            return;
        }
        SchematicController.Instance.save(this.player.m_20203_(), this.name, tile.m_58899_(), tile.height, tile.width, tile.length);
    }
}

