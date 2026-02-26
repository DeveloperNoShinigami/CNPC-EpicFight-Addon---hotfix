/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.CustomNpcs;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.shared.common.PacketBasic;

public class PacketConfigFont
extends PacketBasic {
    private final String font;
    private final int size;

    public PacketConfigFont(String font, int size) {
        this.font = font;
        this.size = size;
    }

    public static void encode(PacketConfigFont msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.font);
        buf.writeInt(msg.size);
    }

    public static PacketConfigFont decode(FriendlyByteBuf buf) {
        return new PacketConfigFont(buf.m_130136_(Short.MAX_VALUE), buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Runnable run = () -> {
            if (!this.font.isEmpty()) {
                CustomNpcs.FontType = this.font;
                CustomNpcs.FontSize = this.size;
                ClientProxy.Font.clear();
                ClientProxy.Font = new ClientProxy.FontContainer(CustomNpcs.FontType, CustomNpcs.FontSize);
                CustomNpcs.Config.updateConfig();
                this.player.m_213846_((Component)Component.m_237110_((String)"Font set to %s", (Object[])new Object[]{ClientProxy.Font.getName()}));
            } else {
                this.player.m_213846_((Component)Component.m_237115_((String)("Current font is " + ClientProxy.Font.getName())));
            }
        };
        Minecraft.m_91087_().m_18707_(run);
    }
}

