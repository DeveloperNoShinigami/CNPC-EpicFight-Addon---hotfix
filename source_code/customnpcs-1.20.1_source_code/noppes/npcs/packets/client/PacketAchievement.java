/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.toasts.Toast
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.gui.GuiAchievement;
import noppes.npcs.shared.common.PacketBasic;

public class PacketAchievement
extends PacketBasic {
    private final Component title;
    private final Component message;
    private final int type;

    public PacketAchievement(Component title, Component message, int type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public static void encode(PacketAchievement msg, FriendlyByteBuf buf) {
        buf.m_130083_(msg.title);
        buf.m_130083_(msg.message);
        buf.writeInt(msg.type);
    }

    public static PacketAchievement decode(FriendlyByteBuf buf) {
        return new PacketAchievement(buf.m_130238_(), buf.m_130238_(), buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Minecraft.m_91087_().m_91300_().m_94922_((Toast)new GuiAchievement(this.title, this.message, this.type));
    }
}

