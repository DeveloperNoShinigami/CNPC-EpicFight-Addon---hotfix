/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.network.PlayMessages$SpawnEntity
 */
package noppes.npcs.packets.client;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PlayMessages;
import noppes.npcs.shared.common.PacketBasic;

public class PacketNpcVisibleTrue
extends PacketBasic {
    private static final Constructor<PlayMessages.SpawnEntity> constructor;
    private final PlayMessages.SpawnEntity pkt;
    private final int id;

    public PacketNpcVisibleTrue(Entity entity) {
        this.id = entity.m_19879_();
        PlayMessages.SpawnEntity p = null;
        try {
            p = constructor.newInstance(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.pkt = p;
    }

    public PacketNpcVisibleTrue(int id, PlayMessages.SpawnEntity pkt) {
        this.id = id;
        this.pkt = pkt;
    }

    public static void encode(PacketNpcVisibleTrue msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        PlayMessages.SpawnEntity.encode((PlayMessages.SpawnEntity)msg.pkt, (FriendlyByteBuf)buf);
    }

    public static PacketNpcVisibleTrue decode(FriendlyByteBuf buf) {
        return new PacketNpcVisibleTrue(buf.readInt(), PlayMessages.SpawnEntity.decode((FriendlyByteBuf)buf));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        ClientLevel w = Minecraft.m_91087_().f_91073_;
        Entity entity = w.m_6815_(this.id);
        if (entity == null) {
            PlayMessages.SpawnEntity.handle((PlayMessages.SpawnEntity)this.pkt, (Supplier)this.ctx);
        }
    }

    static {
        Constructor con = null;
        try {
            con = PlayMessages.SpawnEntity.class.getDeclaredConstructor(Entity.class);
            con.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        constructor = con;
    }
}

