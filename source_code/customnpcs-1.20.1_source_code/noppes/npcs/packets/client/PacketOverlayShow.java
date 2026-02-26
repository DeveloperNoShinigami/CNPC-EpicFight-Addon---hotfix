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
import noppes.npcs.api.wrapper.OverlayWrapper;
import noppes.npcs.client.controllers.OverlayController;
import noppes.npcs.shared.common.PacketBasic;

public class PacketOverlayShow
extends PacketBasic {
    private final CompoundTag compound;

    public PacketOverlayShow(CompoundTag compound) {
        this.compound = compound;
    }

    public static void encode(PacketOverlayShow msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.compound);
    }

    public static PacketOverlayShow decode(FriendlyByteBuf buf) {
        return new PacketOverlayShow(buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        OverlayWrapper wrapper = new OverlayWrapper(0);
        wrapper.fromNbt(this.compound);
        OverlayController.getInstance().addOverlay(wrapper);
    }
}

