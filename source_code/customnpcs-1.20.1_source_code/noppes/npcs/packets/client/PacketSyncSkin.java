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
import noppes.npcs.client.controllers.ClientSkinController;
import noppes.npcs.controllers.data.PlayerSkinData;
import noppes.npcs.shared.common.PacketBasic;

public class PacketSyncSkin
extends PacketBasic {
    private final String name;
    private final PlayerSkinData skinData;

    public PacketSyncSkin(String name, PlayerSkinData skinData) {
        this.name = name;
        this.skinData = skinData;
    }

    public static void encode(PacketSyncSkin msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.name);
        CompoundTag tag = new CompoundTag();
        msg.skinData.saveNBTData(tag);
        buf.m_130079_(tag);
    }

    public static PacketSyncSkin decode(FriendlyByteBuf buf) {
        String name = buf.m_130277_();
        CompoundTag tag = buf.m_130260_();
        PlayerSkinData skinData = new PlayerSkinData();
        skinData.loadNBTData(tag);
        return new PacketSyncSkin(name, skinData);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        ClientSkinController.addSkinForPlayer(this.name, this.skinData);
    }
}

