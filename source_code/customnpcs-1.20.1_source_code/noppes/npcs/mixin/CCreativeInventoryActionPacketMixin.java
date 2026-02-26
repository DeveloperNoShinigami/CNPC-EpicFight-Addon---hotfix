/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerGamePacketListener
 *  net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket
 *  org.spongepowered.asm.mixin.Mixin
 */
package noppes.npcs.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
import noppes.npcs.CustomItems;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={ServerboundSetCreativeModeSlotPacket.class})
public abstract class CCreativeInventoryActionPacketMixin
implements Packet<ServerGamePacketListener> {
    public void m_5779_(FriendlyByteBuf buffer) {
        ServerboundSetCreativeModeSlotPacket p = (ServerboundSetCreativeModeSlotPacket)this;
        if (p.m_134564_().m_41720_() == CustomItems.scripted_item) {
            buffer.writeShort(p.m_134561_());
            buffer.writeItemStack(p.m_134564_(), true);
        } else {
            buffer.writeShort(p.m_134561_());
            buffer.writeItemStack(p.m_134564_(), false);
        }
    }
}

