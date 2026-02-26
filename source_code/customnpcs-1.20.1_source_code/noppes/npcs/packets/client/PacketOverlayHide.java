/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.controllers.OverlayController;
import noppes.npcs.shared.common.PacketBasic;

public class PacketOverlayHide
extends PacketBasic {
    private final int id;

    public PacketOverlayHide(int id) {
        this.id = id;
    }

    public static void encode(PacketOverlayHide msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
    }

    public static PacketOverlayHide decode(FriendlyByteBuf buf) {
        return new PacketOverlayHide(buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        OverlayController.getInstance().removeOverlay(this.id);
    }
}

