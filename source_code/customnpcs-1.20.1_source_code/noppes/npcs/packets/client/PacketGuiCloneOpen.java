/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.GuiNpcMobSpawnerAdd;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiCloneOpen
extends PacketBasic {
    private final CompoundTag data;

    public PacketGuiCloneOpen(CompoundTag data) {
        this.data = data;
    }

    public static void encode(PacketGuiCloneOpen msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static PacketGuiCloneOpen decode(FriendlyByteBuf buf) {
        return new PacketGuiCloneOpen(buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        NoppesUtil.openGUI(this.player, new GuiNpcMobSpawnerAdd(this.data));
    }
}

