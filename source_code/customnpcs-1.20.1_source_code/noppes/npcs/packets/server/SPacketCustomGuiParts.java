/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketCustomGuiParts
extends PacketServerBasic {
    private final CompoundTag data;

    public SPacketCustomGuiParts(CompoundTag data) {
        this.data = data;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketCustomGuiParts msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static SPacketCustomGuiParts decode(FriendlyByteBuf buf) {
        return new SPacketCustomGuiParts(buf.m_130260_());
    }

    @Override
    protected void handle() {
        AbstractContainerMenu abstractContainerMenu = this.player.f_36096_;
        if (abstractContainerMenu instanceof ContainerCustomGui) {
            ContainerCustomGui container = (ContainerCustomGui)abstractContainerMenu;
            container.customGui.npc.modelData.load(this.data);
            container.customGui.npc.updateClient = true;
        }
    }
}

