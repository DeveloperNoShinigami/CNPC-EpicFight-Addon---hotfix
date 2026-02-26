/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.RandomSource
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.shared.common.PacketBasic;

public class PacketParticle
extends PacketBasic {
    private final double posX;
    private final double posY;
    private final double posZ;
    private final float height;
    private final float width;
    private final String name;

    public PacketParticle(double posX, double posY, double posZ, float height, float width, String name) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.height = height;
        this.width = width;
        this.name = name;
    }

    public static void encode(PacketParticle msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.posX);
        buf.writeDouble(msg.posY);
        buf.writeDouble(msg.posZ);
        buf.writeFloat(msg.height);
        buf.writeFloat(msg.width);
        buf.m_130070_(msg.name);
    }

    public static PacketParticle decode(FriendlyByteBuf buf) {
        return new PacketParticle(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readFloat(), buf.readFloat(), buf.m_130136_(Short.MAX_VALUE));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        ClientLevel world = Minecraft.m_91087_().f_91073_;
        RandomSource rand = world.f_46441_;
        if (this.name.equals("heal")) {
            for (int k = 0; k < 6; ++k) {
                world.m_7106_((ParticleOptions)ParticleTypes.f_123751_, this.posX + (rand.m_188500_() - 0.5) * (double)this.width, this.posY + rand.m_188500_() * (double)this.height, this.posZ + (rand.m_188500_() - 0.5) * (double)this.width, 0.0, 0.0, 0.0);
                world.m_7106_((ParticleOptions)ParticleTypes.f_123806_, this.posX + (rand.m_188500_() - 0.5) * (double)this.width, this.posY + rand.m_188500_() * (double)this.height, this.posZ + (rand.m_188500_() - 0.5) * (double)this.width, 0.0, 0.0, 0.0);
            }
        }
    }
}

