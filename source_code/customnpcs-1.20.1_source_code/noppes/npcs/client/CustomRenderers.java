/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.Model
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package noppes.npcs.client;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomEntities;
import noppes.npcs.client.model.ModelClassicPlayer;
import noppes.npcs.client.model.ModelNPCGolem;
import noppes.npcs.client.model.ModelNpcCrystal;
import noppes.npcs.client.model.ModelNpcDragon;
import noppes.npcs.client.model.ModelNpcSlime;
import noppes.npcs.client.model.ModelPlayer64x32;
import noppes.npcs.client.model.ModelPony;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.client.renderer.RenderNPCPony;
import noppes.npcs.client.renderer.RenderNpcCrystal;
import noppes.npcs.client.renderer.RenderNpcDragon;
import noppes.npcs.client.renderer.RenderNpcSlime;
import noppes.npcs.client.renderer.RenderProjectile;
import noppes.npcs.client.renderer.blocks.BlockBuilderRenderer;
import noppes.npcs.client.renderer.blocks.BlockCarpentryBenchRenderer;
import noppes.npcs.client.renderer.blocks.BlockCopyRenderer;
import noppes.npcs.client.renderer.blocks.BlockDoorRenderer;
import noppes.npcs.client.renderer.blocks.BlockMailboxRenderer;
import noppes.npcs.client.renderer.blocks.BlockScriptedRenderer;

@OnlyIn(value=Dist.CLIENT)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid="customnpcs", value={Dist.CLIENT})
public class CustomRenderers {
    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CustomEntities.entityNpcPony, manager -> new RenderNPCPony(manager, new ModelPony()));
        event.registerEntityRenderer(CustomEntities.entityNpcCrystal, manager -> new RenderNpcCrystal(manager, new ModelNpcCrystal()));
        event.registerEntityRenderer(CustomEntities.entityNpcDragon, manager -> new RenderNpcDragon(manager, new ModelNpcDragon(), 0.5f));
        event.registerEntityRenderer(CustomEntities.entityNpcSlime, manager -> new RenderNpcSlime(manager, new ModelNpcSlime(16), (Model)new ModelNpcSlime(0), 0.25f));
        event.registerEntityRenderer(CustomEntities.entityProjectile, manager -> new RenderProjectile(manager));
        event.registerEntityRenderer(CustomEntities.entityCustomNpc, manager -> new RenderCustomNpc(manager, new PlayerModel(manager.m_174027_().m_171103_(ModelLayers.f_171162_), false)));
        event.registerEntityRenderer(CustomEntities.entityNPC64x32, manager -> new RenderCustomNpc(manager, new ModelPlayer64x32(manager.m_174027_().m_171103_(ModelLayers.f_171162_))));
        event.registerEntityRenderer(CustomEntities.entityNPCGolem, manager -> new RenderNPCInterface(manager, new ModelNPCGolem(0.0f), 0.0f));
        event.registerEntityRenderer(CustomEntities.entityNpcAlex, manager -> new RenderCustomNpc(manager, new PlayerModel(manager.m_174027_().m_171103_(ModelLayers.f_171166_), true)));
        event.registerEntityRenderer(CustomEntities.entityNpcClassicPlayer, manager -> new RenderCustomNpc(manager, new ModelClassicPlayer(manager.m_174027_().m_171103_(ModelLayers.f_171162_), 0.0f)));
        event.registerBlockEntityRenderer(CustomBlocks.tile_anvil, BlockCarpentryBenchRenderer::new);
        event.registerBlockEntityRenderer(CustomBlocks.tile_mailbox, BlockMailboxRenderer::new);
        event.registerBlockEntityRenderer(CustomBlocks.tile_scripted, BlockScriptedRenderer::new);
        event.registerBlockEntityRenderer(CustomBlocks.tile_scripteddoor, BlockDoorRenderer::new);
        event.registerBlockEntityRenderer(CustomBlocks.tile_copy, BlockCopyRenderer::new);
        event.registerBlockEntityRenderer(CustomBlocks.tile_builder, BlockBuilderRenderer::new);
    }
}

