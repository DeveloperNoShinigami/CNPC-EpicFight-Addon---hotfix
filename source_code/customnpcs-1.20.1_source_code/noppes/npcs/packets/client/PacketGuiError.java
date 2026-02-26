/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.shared.client.gui.listeners.IGuiError;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiError
extends PacketBasic {
    private final int error;
    private final CompoundTag data;

    public PacketGuiError(int error, CompoundTag data) {
        this.error = error;
        this.data = data;
    }

    public static void encode(PacketGuiError msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.error);
        buf.m_130079_(msg.data);
    }

    public static PacketGuiError decode(FriendlyByteBuf buf) {
        return new PacketGuiError(buf.readInt(), buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui == null || !(gui instanceof IGuiError)) {
            return;
        }
        ((IGuiError)gui).setError(this.error, this.data);
    }
}

