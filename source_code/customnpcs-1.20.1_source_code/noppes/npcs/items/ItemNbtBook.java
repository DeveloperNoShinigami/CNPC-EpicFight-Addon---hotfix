/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$EntityInteract
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 */
package noppes.npcs.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiData;
import noppes.npcs.packets.client.PacketGuiOpen;

public class ItemNbtBook
extends Item {
    public ItemNbtBook() {
        super(new Item.Properties().m_41487_(1));
    }

    public void blockEvent(PlayerInteractEvent.RightClickBlock event) {
        Packets.send((ServerPlayer)event.getEntity(), new PacketGuiOpen(EnumGuiType.NbtBook, event.getPos()));
        BlockState state = event.getLevel().m_8055_(event.getPos());
        CompoundTag data = new CompoundTag();
        BlockEntity tile = event.getLevel().m_7702_(event.getPos());
        if (tile != null) {
            data = tile.m_187480_();
        }
        CompoundTag compound = new CompoundTag();
        compound.m_128365_("Data", (Tag)data);
        Packets.send((ServerPlayer)event.getEntity(), new PacketGuiData(compound));
    }

    public void entityEvent(PlayerInteractEvent.EntityInteract event) {
        Packets.send((ServerPlayer)event.getEntity(), new PacketGuiOpen(EnumGuiType.NbtBook, BlockPos.f_121853_));
        CompoundTag data = new CompoundTag();
        event.getTarget().m_20086_(data);
        CompoundTag compound = new CompoundTag();
        compound.m_128405_("EntityId", event.getTarget().m_19879_());
        compound.m_128365_("Data", (Tag)data);
        Packets.send((ServerPlayer)event.getEntity(), new PacketGuiData(compound));
    }
}

