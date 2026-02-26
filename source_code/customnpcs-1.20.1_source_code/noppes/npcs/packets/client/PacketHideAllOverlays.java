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

public class PacketHideAllOverlays
extends PacketBasic {
    private final boolean id;

    public PacketHideAllOverlays(boolean id) {
        this.id = id;
    }

    public static void encode(PacketHideAllOverlays msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.id);
    }

    public static PacketHideAllOverlays decode(FriendlyByteBuf buf) {
        return new PacketHideAllOverlays(buf.readBoolean());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        OverlayController.getInstance().clear();
    }
}

