/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package noppes.npcs;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerSkinData;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketSyncSkin;

@Mod.EventBusSubscriber(modid="customnpcs")
public class SkinEventHandler {
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (PlayerSkinData.needsAnyResync()) {
            for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().m_6846_().m_11314_()) {
                PlayerData playerData = PlayerData.get((Player)player);
                if (!playerData.skinData.isActive() || !playerData.skinData.hasChanged()) continue;
                Packets.sendAll(new PacketSyncSkin(playerData.playername, playerData.skinData));
                playerData.skinData.markSynced();
            }
            PlayerSkinData.resyncPerformed();
        }
    }

    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer)) {
            return;
        }
        ServerPlayer player = (ServerPlayer)event.getEntity();
        PlayerData playerData = PlayerData.get((Player)player);
        if (playerData.skinData.isActive()) {
            Packets.sendAll(new PacketSyncSkin(playerData.playername, playerData.skinData));
        }
        for (ServerPlayer otherPlayer : ServerLifecycleHooks.getCurrentServer().m_6846_().m_11314_()) {
            PlayerData otherPlayerData = PlayerData.get((Player)otherPlayer);
            if (!otherPlayerData.skinData.isActive()) continue;
            Packets.send(player, new PacketSyncSkin(otherPlayerData.playername, otherPlayerData.skinData));
        }
    }
}

