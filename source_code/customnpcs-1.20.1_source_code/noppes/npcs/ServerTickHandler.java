/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
 *  net.minecraft.network.protocol.game.ClientboundSetScorePacket
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.ServerScoreboard
 *  net.minecraft.server.ServerScoreboard$Method
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ContainerListener
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.scores.Objective
 *  net.minecraft.world.scores.Score
 *  net.minecraftforge.event.TickEvent$LevelTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.LogicalSide
 */
package noppes.npcs;

import java.util.ArrayList;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import noppes.npcs.CustomItems;
import noppes.npcs.NPCSpawning;
import noppes.npcs.controllers.MassBlockController;
import noppes.npcs.controllers.SchematicController;
import noppes.npcs.controllers.SyncController;
import noppes.npcs.controllers.VisibilityController;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.entity.data.DataScenes;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketSync;

public class ServerTickHandler {
    public int ticks = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER || event.phase != TickEvent.Phase.START) {
            return;
        }
        Player player = event.player;
        PlayerData data = PlayerData.get(player);
        if (player.m_20193_().m_46468_() % 24000L == 1L || player.m_20193_().m_46468_() % 240000L == 12001L) {
            VisibilityController.instance.onUpdate((ServerPlayer)player);
        }
        if (data.updateClient) {
            Packets.send((ServerPlayer)player, new PacketSync(8, data.getSyncNBT(), true));
            VisibilityController.instance.onUpdate((ServerPlayer)player);
            data.updateClient = false;
        }
        if (data.prevHeldItem != player.m_21205_() && (data.prevHeldItem.m_41720_() == CustomItems.wand || player.m_21205_().m_41720_() == CustomItems.wand)) {
            VisibilityController.instance.onUpdate((ServerPlayer)player);
        }
        data.prevHeldItem = player.m_21205_();
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
            NPCSpawning.findChunksForSpawning((ServerLevel)event.level);
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START && this.ticks++ >= 20) {
            SchematicController.Instance.updateBuilding();
            MassBlockController.Update();
            this.ticks = 0;
            for (DataScenes.SceneState state : DataScenes.StartedScenes.values()) {
                if (state.paused) continue;
                ++state.ticks;
            }
            for (DataScenes.SceneContainer entry : DataScenes.ScenesToRun) {
                entry.update();
            }
            DataScenes.ScenesToRun = new ArrayList<DataScenes.SceneContainer>();
        }
    }

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        final ServerPlayer player = (ServerPlayer)event.getEntity();
        MinecraftServer server = event.getEntity().m_20194_();
        for (ServerLevel level : server.m_129785_()) {
            ServerScoreboard board = level.m_6188_();
            for (String objective : Availability.scores) {
                Objective so = board.m_83477_(objective);
                if (so == null) continue;
                if (board.m_136237_(so) == 0) {
                    player.f_8906_.m_9829_((Packet)new ClientboundSetObjectivePacket(so, 0));
                }
                Score sco = board.m_83471_(player.m_6302_(), so);
                player.f_8906_.m_9829_((Packet)new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, so.m_83320_(), sco.m_83405_(), sco.m_83400_()));
            }
        }
        player.f_36095_.m_38893_(new ContainerListener(){

            public void m_7934_(AbstractContainerMenu container, int slotInd, ItemStack stack) {
                if (player.m_9236_().f_46443_) {
                    return;
                }
                PlayerQuestData playerdata = PlayerData.get((Player)player).questData;
                playerdata.checkQuestCompletion((Player)player, 0);
            }

            public void m_142153_(AbstractContainerMenu container, int varToUpdate, int newValue) {
            }
        });
        PlayerData data = PlayerData.get(event.getEntity());
        String serverName = "local";
        if (server.m_6982_()) {
            serverName = "server";
        } else if (server.m_6992_()) {
            serverName = "lan";
        }
        SyncController.syncPlayer((ServerPlayer)event.getEntity());
    }
}

