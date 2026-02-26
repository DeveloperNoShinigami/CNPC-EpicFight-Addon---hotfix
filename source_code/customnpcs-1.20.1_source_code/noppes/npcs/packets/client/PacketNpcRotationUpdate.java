/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.common.PacketBasic;

public class PacketNpcRotationUpdate
extends PacketBasic {
    private final int id;
    private final int orientation;

    public PacketNpcRotationUpdate(int id, int orientation) {
        this.id = id;
        this.orientation = orientation;
    }

    public static void encode(PacketNpcRotationUpdate msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        buf.writeInt(msg.orientation);
    }

    public static PacketNpcRotationUpdate decode(FriendlyByteBuf buf) {
        return new PacketNpcRotationUpdate(buf.readInt(), buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(this.id);
        if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
        }
        ((EntityNPCInterface)entity).ais.orientation = this.orientation;
    }
}

