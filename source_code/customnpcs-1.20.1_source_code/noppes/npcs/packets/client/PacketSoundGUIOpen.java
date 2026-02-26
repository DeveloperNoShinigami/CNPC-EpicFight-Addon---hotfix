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
import noppes.npcs.client.gui.select.GuiSoundSelection;
import noppes.npcs.shared.common.PacketBasic;

public class PacketSoundGUIOpen
extends PacketBasic {
    public static void encode(PacketSoundGUIOpen msg, FriendlyByteBuf buf) {
    }

    public static PacketSoundGUIOpen decode(FriendlyByteBuf buf) {
        return new PacketSoundGUIOpen();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        try {
            Minecraft minecraft = Minecraft.m_91087_();
            minecraft.m_91152_((Screen)new GuiSoundSelection(""));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

