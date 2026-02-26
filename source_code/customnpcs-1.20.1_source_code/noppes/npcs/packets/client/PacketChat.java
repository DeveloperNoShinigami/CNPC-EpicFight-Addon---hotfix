/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.shared.common.PacketBasic;

public class PacketChat
extends PacketBasic {
    private final Component message;

    public PacketChat(Component message) {
        this.message = message;
    }

    public static void encode(PacketChat msg, FriendlyByteBuf buf) {
        buf.m_130083_(msg.message);
    }

    public static PacketChat decode(FriendlyByteBuf buf) {
        return new PacketChat(buf.m_130238_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        this.player.m_213846_(this.message);
    }
}

