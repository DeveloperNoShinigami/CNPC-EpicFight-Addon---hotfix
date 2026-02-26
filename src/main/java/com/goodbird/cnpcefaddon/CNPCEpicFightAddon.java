package com.goodbird.cnpcefaddon;

import com.goodbird.cnpcefaddon.common.NpcPatchReloadListener;
import com.goodbird.cnpcefaddon.common.network.NetworkHandler;
import com.goodbird.cnpcefaddon.common.network.SPDatapackSync;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.Logging;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import noppes.npcs.CustomEntities;

import java.util.Objects;
import java.util.stream.Stream;

@Mod(CNPCEpicFightAddon.MODID)
public class CNPCEpicFightAddon {
    public static final String MODID = "cnpcefaddon";

    public CNPCEpicFightAddon() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::doCommonStuff);
        bus.addListener(this::onEntityAttributeModification);
        MinecraftForge.EVENT_BUS.addListener(this::reloadListenerEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onDatapackSync);
    }

    private void doCommonStuff(FMLCommonSetupEvent event) {
        NetworkHandler.register();
    }

    private void reloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(new NpcPatchReloadListener());
    }

    private void onDatapackSync(OnDatapackSyncEvent event) {
        ServerPlayer player = event.getPlayer();
        SPDatapackSync mobPatchPacket = new SPDatapackSync(NpcPatchReloadListener.TAGMAP.size());
        for (CompoundTag tag : NpcPatchReloadListener.getDataStream().toList()) {
            mobPatchPacket.write(tag);
        }
        if (player != null) {
            if (!player.getServer().isSingleplayerOwner(player.getGameProfile())) {
                NetworkHandler.send(player, mobPatchPacket);
            }
        } else {
            event.getPlayerList().getPlayers().forEach((serverPlayer) -> {
                NetworkHandler.send(serverPlayer, mobPatchPacket);
            });
        }

    }

    private void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.WEIGHT.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.ARMOR_NEGATION.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.IMPACT.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.MAX_STRIKES.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.STUN_ARMOR.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.OFFHAND_ATTACK_SPEED.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.OFFHAND_MAX_STRIKES.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.OFFHAND_ARMOR_NEGATION.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.OFFHAND_IMPACT.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.MAX_STAMINA.get());
        event.add(CustomEntities.entityCustomNpc, EpicFightAttributes.STAMINA_REGEN.get());
    }

}
