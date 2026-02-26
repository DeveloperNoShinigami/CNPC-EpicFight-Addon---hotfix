/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.shared.common.PacketBasic;

public class PacketPlayMusic
extends PacketBasic {
    private final String name;
    private final boolean streaming;
    private final boolean looping;

    public PacketPlayMusic(String name, boolean streaming, boolean looping) {
        this.name = name;
        this.streaming = streaming;
        this.looping = looping;
    }

    public static void encode(PacketPlayMusic msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.name);
        buf.writeBoolean(msg.streaming);
        buf.writeBoolean(msg.looping);
    }

    public static PacketPlayMusic decode(FriendlyByteBuf buf) {
        return new PacketPlayMusic(buf.m_130136_(Short.MAX_VALUE), buf.readBoolean(), buf.readBoolean());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        if (this.streaming) {
            MusicController.Instance.playStreaming(this.name, (Entity)this.player, this.looping);
        } else {
            MusicController.Instance.playMusic(this.name, (Entity)this.player, this.looping);
        }
    }
}

