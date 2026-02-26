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
import noppes.npcs.ModelData;
import noppes.npcs.ModelEyeData;
import noppes.npcs.client.parts.MpmPartData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.common.PacketBasic;

public class PacketEyeBlink
extends PacketBasic {
    private final int id;

    public PacketEyeBlink(int id) {
        this.id = id;
    }

    public static void encode(PacketEyeBlink msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
    }

    public static PacketEyeBlink decode(FriendlyByteBuf buf) {
        return new PacketEyeBlink(buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(this.id);
        if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
        }
        ModelData data = ((EntityCustomNpc)entity).modelData;
        for (MpmPartData pd : data.mpmParts) {
            if (!(pd instanceof ModelEyeData)) continue;
            ((ModelEyeData)pd).blinkStart = System.currentTimeMillis();
        }
    }
}

