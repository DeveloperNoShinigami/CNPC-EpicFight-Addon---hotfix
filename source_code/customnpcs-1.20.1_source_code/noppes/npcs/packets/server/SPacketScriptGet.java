/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package noppes.npcs.packets.server;

import java.util.Arrays;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.ForgeEventHandler;
import noppes.npcs.NBTTags;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.wrapper.ItemScriptedWrapper;
import noppes.npcs.blocks.tiles.TileScripted;
import noppes.npcs.blocks.tiles.TileScriptedDoor;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiData;

public class SPacketScriptGet
extends PacketServerBasic {
    private int type;

    public SPacketScriptGet(int type) {
        this.type = type;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.scripted_item || item.m_41720_() == CustomItems.scripter || item.m_41720_() == CustomItems.wand || item.m_41720_() == CustomBlocks.scripted_door_item || item.m_41720_() == CustomBlocks.scripted_item;
    }

    @Override
    public boolean requiresNpc() {
        return this.type == 0;
    }

    public static void encode(SPacketScriptGet msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.type);
    }

    public static SPacketScriptGet decode(FriendlyByteBuf buf) {
        return new SPacketScriptGet(buf.readInt());
    }

    @Override
    protected void handle() {
        BlockEntity tile;
        PlayerData data;
        CompoundTag compound = new CompoundTag();
        if (this.type == 0) {
            this.npc.script.save(compound);
            compound.m_128365_("Methods", (Tag)NBTTags.nbtStringList(Arrays.stream(EnumScriptType.npcScripts).map(type -> type.function).collect(Collectors.toList())));
        }
        if (this.type == 1) {
            data = PlayerData.get((Player)this.player);
            tile = this.player.m_9236_().m_7702_(data.scriptBlockPos);
            if (!(tile instanceof TileScripted)) {
                return;
            }
            ((TileScripted)tile).getNBT(compound);
            compound.m_128365_("Methods", (Tag)NBTTags.nbtStringList(Arrays.stream(EnumScriptType.blockScripts).map(type -> type.function).collect(Collectors.toList())));
        }
        if (this.type == 2) {
            ItemScriptedWrapper iw = (ItemScriptedWrapper)NpcAPI.Instance().getIItemStack(this.player.m_21205_());
            compound = iw.getMCNbt();
            compound.m_128365_("Methods", (Tag)NBTTags.nbtStringList(Arrays.stream(EnumScriptType.itemScripts).map(type -> type.function).collect(Collectors.toList())));
        }
        if (this.type == 3) {
            ScriptController.Instance.forgeScripts.save(compound);
            compound.m_128365_("Methods", (Tag)NBTTags.nbtStringList(ForgeEventHandler.eventNames));
        }
        if (this.type == 4) {
            ScriptController.Instance.playerScripts.save(compound);
            compound.m_128365_("Methods", (Tag)NBTTags.nbtStringList(Arrays.stream(EnumScriptType.playerScripts).map(type -> type.function).collect(Collectors.toList())));
        }
        if (this.type == 5) {
            data = PlayerData.get((Player)this.player);
            tile = this.player.m_9236_().m_7702_(data.scriptBlockPos);
            if (!(tile instanceof TileScriptedDoor)) {
                return;
            }
            ((TileScriptedDoor)tile).getNBT(compound);
            compound.m_128365_("Methods", (Tag)NBTTags.nbtStringList(Arrays.stream(EnumScriptType.doorScripts).map(type -> type.function).collect(Collectors.toList())));
        }
        compound.m_128365_("Languages", (Tag)ScriptController.Instance.nbtLanguages());
        Packets.send(this.player, new PacketGuiData(compound));
    }
}

