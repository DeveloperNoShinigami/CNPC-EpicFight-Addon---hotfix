/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.shared.client.gui.listeners.IScrollData;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiScrollSelected
extends PacketBasic {
    private final String selected;

    public PacketGuiScrollSelected(String selected) {
        this.selected = selected;
    }

    public static void encode(PacketGuiScrollSelected msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.selected);
    }

    public static PacketGuiScrollSelected decode(FriendlyByteBuf buf) {
        return new PacketGuiScrollSelected(buf.m_130136_(Short.MAX_VALUE));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui == null || !(gui instanceof IScrollData)) {
            return;
        }
        ((IScrollData)gui).setSelected(this.selected);
    }
}

