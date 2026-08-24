package com.goodbird.cnpcefaddon.common.network;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import noppes.npcs.packets.client.PacketAchievement;
import noppes.npcs.util.CustomNPCsScheduler;

public class NetworkHandler {
    private static final String PROTOCOL = "CNPCEFADDON";

    public static SimpleChannel CHANNEL;

    public static int index = 0;

    public static void register() {
        CHANNEL = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(CNPCEpicFightAddon.MODID, "packets"))
                .clientAcceptedVersions(PROTOCOL::equals)
                .serverAcceptedVersions(PROTOCOL::equals)
                .networkProtocolVersion(() -> PROTOCOL)
                .simpleChannel();

        index = 0;
        CHANNEL.registerMessage(index++, SPDatapackSync.class, SPDatapackSync::toBytes, SPDatapackSync::fromBytes, SPDatapackSync::handle);
        CHANNEL.registerMessage(index++, SPNpcPatchSelection.class, SPNpcPatchSelection::toBytes,
                SPNpcPatchSelection::fromBytes, SPNpcPatchSelection::handle);
    }

    public static <MSG> void send(ServerPlayer player, MSG msg) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static <MSG> void sendDelayed(ServerPlayer player, MSG msg, int delay) {
        CustomNPCsScheduler.runTack(() -> CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg), delay);
    }

    public static <MSG> void sendNearby(Level level, BlockPos pos, int range, MSG msg) {
        CHANNEL.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), range, level.dimension())), msg);
    }

    public static <MSG> void sendNearby(Entity entity, MSG msg) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }

    public static <MSG> void sendAll(MSG msg) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static <MSG> void sendServer(MSG msg){
        if(msg instanceof Packet){
            Minecraft.getInstance().getConnection().getConnection().send((Packet)msg);
        }
        else{
            CHANNEL.sendToServer(msg);
        }
    }
}
