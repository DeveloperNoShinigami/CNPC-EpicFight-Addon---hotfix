/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketQuestOpen
extends PacketServerBasic {
    private EnumGuiType gui;
    private CompoundTag data;

    public SPacketQuestOpen(EnumGuiType gui, CompoundTag data) {
        this.gui = gui;
        this.data = data;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.GLOBAL_DIALOG;
    }

    public static void encode(SPacketQuestOpen msg, FriendlyByteBuf buf) {
        buf.m_130068_((Enum)msg.gui);
        buf.m_130079_(msg.data);
    }

    public static SPacketQuestOpen decode(FriendlyByteBuf buf) {
        return new SPacketQuestOpen((EnumGuiType)buf.m_130066_(EnumGuiType.class), buf.m_130260_());
    }

    @Override
    protected void handle() {
        Quest quest = new Quest(null);
        quest.readNBT(this.data);
        NoppesUtilServer.setEditingQuest((Player)this.player, quest);
        NoppesUtilServer.openContainerGui(this.player, this.gui, buf -> buf.m_130064_(BlockPos.f_121853_));
    }
}

