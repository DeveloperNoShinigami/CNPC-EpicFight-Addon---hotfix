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
import noppes.npcs.shared.client.gui.listeners.IGuiClose;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiClose
extends PacketBasic {
    private final CompoundTag data;

    public PacketGuiClose(CompoundTag data) {
        this.data = data;
    }

    public PacketGuiClose() {
        this(new CompoundTag());
    }

    public static void encode(PacketGuiClose msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static PacketGuiClose decode(FriendlyByteBuf buf) {
        return new PacketGuiClose(buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui == null) {
            return;
        }
        if (gui instanceof IGuiClose) {
            ((IGuiClose)gui).setClose(this.data);
        }
        Minecraft mc = Minecraft.m_91087_();
        mc.popGuiLayer();
        mc.f_91067_.m_91601_();
    }
}

