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
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiUpdate
extends PacketBasic {
    public static void encode(PacketGuiUpdate msg, FriendlyByteBuf buf) {
    }

    public static PacketGuiUpdate decode(FriendlyByteBuf buf) {
        return new PacketGuiUpdate();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui == null) {
            return;
        }
        if (gui instanceof IGuiInterface) {
            IGuiInterface igui = (IGuiInterface)gui;
            igui.initGui();
        }
    }
}

