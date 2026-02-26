/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.wrapper.ItemScriptedWrapper;
import noppes.npcs.blocks.tiles.TileNpcEntity;
import noppes.npcs.blocks.tiles.TileScripted;
import noppes.npcs.blocks.tiles.TileScriptedDoor;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketScriptSave
extends PacketServerBasic {
    private int type;
    private CompoundTag data;

    public SPacketScriptSave(int type, CompoundTag data) {
        this.type = type;
        this.data = data;
    }

    public SPacketScriptSave(FriendlyByteBuf buf) {
        this.type = buf.readInt();
        this.data = buf.m_130260_();
    }

    public static SPacketScriptSave decode(FriendlyByteBuf buf) {
        return new SPacketScriptSave(buf);
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.scripter || item.m_41720_() == CustomBlocks.scripted_door_item || item.m_41720_() == CustomItems.wand || item.m_41720_() == CustomItems.scripted_item || item.m_41720_() == CustomBlocks.scripted_item;
    }

    @Override
    public boolean requiresNpc() {
        return this.type == 0;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.TOOL_SCRIPTER;
    }

    @Override
    public void handle() {
        TileNpcEntity script;
        BlockEntity tile;
        PlayerData pd;
        if (this.type == 0) {
            this.npc.script.load(this.data);
            this.npc.updateAI = true;
            this.npc.script.lastInited = -1L;
        }
        if (this.type == 1) {
            pd = PlayerData.get((Player)this.player);
            tile = this.player.m_9236_().m_7702_(pd.scriptBlockPos);
            if (!(tile instanceof TileScripted)) {
                return;
            }
            script = (TileScripted)tile;
            ((TileScripted)script).setNBT(this.data);
            ((TileScripted)script).lastInited = -1L;
            this.player.m_9236_().m_151543_(pd.scriptBlockPos);
        }
        if (this.type == 2) {
            if (!this.player.m_7500_()) {
                return;
            }
            ItemScriptedWrapper wrapper = (ItemScriptedWrapper)NpcAPI.Instance().getIItemStack(this.player.m_21205_());
            wrapper.setMCNbt(this.data);
            wrapper.lastInited = -1L;
            wrapper.saveScriptData();
            wrapper.updateClient = true;
            this.player.f_36096_.m_150429_();
        }
        if (this.type == 3) {
            ScriptController.Instance.setForgeScripts(this.data);
        }
        if (this.type == 4) {
            ScriptController.Instance.setPlayerScripts(this.data);
        }
        if (this.type == 5) {
            pd = PlayerData.get((Player)this.player);
            tile = this.player.m_9236_().m_7702_(pd.scriptBlockPos);
            if (!(tile instanceof TileScriptedDoor)) {
                return;
            }
            script = (TileScriptedDoor)tile;
            ((TileScriptedDoor)script).setNBT(this.data);
            ((TileScriptedDoor)script).lastInited = -1L;
        }
    }

    public static void encode(SPacketScriptSave msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.type);
        buf.m_130079_(msg.data);
    }
}

