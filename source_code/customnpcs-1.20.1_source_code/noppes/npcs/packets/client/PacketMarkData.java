/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.controllers.data.MarkData;
import noppes.npcs.shared.common.PacketBasic;

public class PacketMarkData
extends PacketBasic {
    private final int id;
    private final CompoundTag data;

    public PacketMarkData(int id, CompoundTag data) {
        this.id = id;
        this.data = data;
    }

    public static void encode(PacketMarkData msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        buf.m_130079_(msg.data);
    }

    public static PacketMarkData decode(FriendlyByteBuf buf) {
        return new PacketMarkData(buf.readInt(), buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(this.id);
        if (entity == null || !(entity instanceof LivingEntity)) {
            return;
        }
        MarkData mark = MarkData.get((LivingEntity)entity);
        mark.setNBT(this.data);
    }
}

