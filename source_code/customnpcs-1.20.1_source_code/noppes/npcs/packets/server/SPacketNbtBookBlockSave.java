/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketNbtBookBlockSave
extends PacketServerBasic {
    private BlockPos pos;
    private CompoundTag data;

    public SPacketNbtBookBlockSave(BlockPos pos, CompoundTag data) {
        this.pos = pos;
        this.data = data;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.nbt_book;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.TOOL_NBTBOOK;
    }

    public static void encode(SPacketNbtBookBlockSave msg, FriendlyByteBuf buf) {
        buf.m_130064_(msg.pos);
        buf.m_130079_(msg.data);
    }

    public static SPacketNbtBookBlockSave decode(FriendlyByteBuf buf) {
        return new SPacketNbtBookBlockSave(buf.m_130135_(), buf.m_130260_());
    }

    @Override
    protected void handle() {
        BlockEntity tile = this.player.m_9236_().m_7702_(this.pos);
        if (tile != null) {
            tile.m_142466_(this.data);
            tile.m_6596_();
        }
    }
}

