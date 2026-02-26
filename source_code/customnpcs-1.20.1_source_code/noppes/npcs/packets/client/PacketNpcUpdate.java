/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.common.PacketBasic;

public class PacketNpcUpdate
extends PacketBasic {
    private final int id;
    private final CompoundTag data;

    public PacketNpcUpdate(int id, CompoundTag data) {
        this.id = id;
        this.data = data;
    }

    public static void encode(PacketNpcUpdate msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        buf.m_130079_(msg.data);
    }

    public static PacketNpcUpdate decode(FriendlyByteBuf buf) {
        return new PacketNpcUpdate(buf.readInt(), buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(this.id);
        if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
        }
        ((EntityNPCInterface)entity).readSpawnData(this.data);
    }
}

