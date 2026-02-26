/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.ChatMessages;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.util.NoppesStringUtils;
import noppes.npcs.shared.common.PacketBasic;

public class PacketChatBubble
extends PacketBasic {
    private final int id;
    private final Component message;
    private final boolean showMessage;

    public PacketChatBubble(int id, Component message, boolean showMessage) {
        this.id = id;
        this.message = message;
        this.showMessage = showMessage;
    }

    public static void encode(PacketChatBubble msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        buf.m_130083_(msg.message);
        buf.writeBoolean(msg.showMessage);
    }

    public static PacketChatBubble decode(FriendlyByteBuf buf) {
        return new PacketChatBubble(buf.readInt(), buf.m_130238_(), buf.readBoolean());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(this.id);
        if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
        }
        EntityNPCInterface npc = (EntityNPCInterface)entity;
        if (npc.messages == null) {
            npc.messages = new ChatMessages();
        }
        String text = NoppesStringUtils.formatText(this.message, new Object[]{this.player, npc});
        npc.messages.addMessage(text, npc);
        if (this.showMessage) {
            this.player.m_213846_((Component)Component.m_237113_((String)(npc.m_7755_().getString() + ": ")).m_7220_((Component)Component.m_237115_((String)text)));
        }
    }
}

