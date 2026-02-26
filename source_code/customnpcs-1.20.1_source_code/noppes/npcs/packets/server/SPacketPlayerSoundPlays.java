/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.EventHooks;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketPlayerSoundPlays
extends PacketServerBasic {
    private final String sound;
    private final String category;
    private final boolean looping;

    public SPacketPlayerSoundPlays(String sound, String category, boolean looping) {
        this.sound = sound;
        this.category = category;
        this.looping = looping;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketPlayerSoundPlays msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.sound == null ? "" : msg.sound);
        buf.m_130070_(msg.category == null ? "" : msg.category);
        buf.writeBoolean(msg.looping);
    }

    public static SPacketPlayerSoundPlays decode(FriendlyByteBuf buf) {
        return new SPacketPlayerSoundPlays(buf.m_130136_(Short.MAX_VALUE), buf.m_130136_(Short.MAX_VALUE), buf.readBoolean());
    }

    @Override
    protected void handle() {
        EventHooks.onPlayerPlaySound(this.player, this.sound, this.category, this.looping);
    }
}

