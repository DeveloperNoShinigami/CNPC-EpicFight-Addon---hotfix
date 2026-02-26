/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.fml.DistExecutor
 *  net.minecraftforge.network.NetworkEvent$Context
 */
package noppes.npcs.shared.common;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public abstract class PacketBasic {
    public Player player;
    public Supplier<NetworkEvent.Context> ctx;

    public static void handle(PacketBasic msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            msg.ctx = ctx;
            DistExecutor.unsafeRunWhenOn((Dist)Dist.CLIENT, () -> () -> msg.handleClient());
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(value=Dist.CLIENT)
    private void handleClient() {
        this.player = Minecraft.m_91087_().f_91074_;
        this.handle();
    }

    public abstract void handle();
}

