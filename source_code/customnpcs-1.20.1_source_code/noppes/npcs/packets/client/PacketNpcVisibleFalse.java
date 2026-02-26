/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.common.PacketBasic;

public class PacketNpcVisibleFalse
extends PacketBasic {
    private final int id;

    public PacketNpcVisibleFalse(int id) {
        this.id = id;
    }

    public static void encode(PacketNpcVisibleFalse msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
    }

    public static PacketNpcVisibleFalse decode(FriendlyByteBuf buf) {
        return new PacketNpcVisibleFalse(buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        ClientLevel w = Minecraft.m_91087_().f_91073_;
        Entity entity = w.m_6815_(this.id);
        if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
        }
        w.m_171642_(this.id, Entity.RemovalReason.DISCARDED);
    }
}

