/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexBuffer
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.ItemBlockRenderTypes
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.BlockRenderDispatcher
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RenderLivingEvent$Post
 *  net.minecraftforge.client.event.ScreenEvent$Init$Post
 *  net.minecraftforge.client.event.sound.PlaySoundEvent
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package noppes.npcs.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import noppes.npcs.CustomNpcs;
import noppes.npcs.blocks.tiles.TileBuilder;
import noppes.npcs.client.gui.player.tabs.InventoryTabFactions;
import noppes.npcs.client.gui.player.tabs.InventoryTabQuests;
import noppes.npcs.client.gui.player.tabs.InventoryTabVanilla;
import noppes.npcs.client.renderer.MarkRenderer;
import noppes.npcs.controllers.data.MarkData;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketPlayerSoundPlays;
import noppes.npcs.schematics.SchematicWrapper;
import noppes.npcs.shared.common.util.LogWriter;

public class ClientEventHandler {
    private VertexBuffer cache = null;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onRenderTick(PoseStack matrixStack, BlockPos rpos, BlockEntity te) {
        MultiBufferSource.BufferSource buffer = Minecraft.m_91087_().m_91269_().m_110104_();
        LocalPlayer player = Minecraft.m_91087_().f_91074_;
        if (rpos == null || rpos == BlockPos.f_121853_ || rpos.m_123331_((Vec3i)player.m_20183_()) > 1000000.0) {
            return;
        }
        TileBuilder tile = (TileBuilder)te;
        SchematicWrapper schem = tile.getSchematic();
        if (schem == null) {
            return;
        }
        matrixStack.m_85836_();
        matrixStack.m_252880_(1.0f, (float)tile.yOffest, 1.0f);
        if (!TileBuilder.Compiled) {
            BlockRenderDispatcher dispatcher = Minecraft.m_91087_().m_91289_();
            try {
                for (int i = 0; i < schem.size && i < 25000; ++i) {
                    BlockState state = schem.schema.getBlockState(i);
                    if (state.m_60799_() == RenderShape.INVISIBLE || state.m_60799_() != RenderShape.MODEL) continue;
                    int posX = i % schem.schema.getWidth();
                    int posZ = (i - posX) / schem.schema.getWidth() % schem.schema.getLength();
                    int posY = ((i - posX) / schem.schema.getWidth() - posZ) / schem.schema.getLength();
                    BlockPos pos = schem.rotatePos(posX, posY, posZ, tile.rotation);
                    matrixStack.m_85836_();
                    matrixStack.m_252880_((float)pos.m_123341_(), (float)pos.m_123342_(), (float)pos.m_123343_());
                    state = schem.rotationState(state, tile.rotation);
                    try {
                        BakedModel ibakedmodel = dispatcher.m_110910_(state);
                        BufferBuilder builder = (BufferBuilder)buffer.m_6299_(ItemBlockRenderTypes.m_109284_((BlockState)state, (boolean)false));
                        dispatcher.m_110937_().renderModel(matrixStack.m_85850_(), (VertexConsumer)builder, state, ibakedmodel, 1.0f, 1.0f, 1.0f, 0xF000F0, OverlayTexture.f_118083_, ModelData.EMPTY, ItemBlockRenderTypes.m_109284_((BlockState)state, (boolean)false));
                        continue;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    finally {
                        matrixStack.m_85849_();
                    }
                }
            }
            catch (Exception e) {
                LogWriter.error("Error preview builder block", e);
            }
        }
        if (tile.rotation % 2 == 0) {
            ClientEventHandler.drawSelectionBox(matrixStack, (MultiBufferSource)buffer, new BlockPos((int)schem.schema.getWidth(), (int)schem.schema.getHeight(), (int)schem.schema.getLength()));
        } else {
            ClientEventHandler.drawSelectionBox(matrixStack, (MultiBufferSource)buffer, new BlockPos((int)schem.schema.getLength(), (int)schem.schema.getHeight(), (int)schem.schema.getWidth()));
        }
        matrixStack.m_85849_();
    }

    @SubscribeEvent
    public void post(RenderLivingEvent.Post event) {
        MarkData data = MarkData.get(event.getEntity());
        LocalPlayer player = Minecraft.m_91087_().f_91074_;
        for (MarkData.Mark m : data.marks) {
            if (m.getType() == 0 || !m.availability.isAvailable((Player)player)) continue;
            MarkRenderer.render(event, m);
            break;
        }
    }

    @SubscribeEvent
    public void playSound(PlaySoundEvent event) {
        Minecraft mc = Minecraft.m_91087_();
        if (mc == null || mc.f_91073_ == null || mc.m_91403_() == null || event == null || event.getSound() == null) {
            return;
        }
        SoundInstance sound = event.getSound();
        Packets.sendServer(new SPacketPlayerSoundPlays(sound.m_7904_().toString(), sound.m_8070_().m_12676_(), sound.m_7775_()));
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void guiPostInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen) {
            InventoryScreen screen2 = (InventoryScreen)screen;
            if (CustomNpcs.InventoryGuiEnabled) {
                event.addListener((GuiEventListener)new InventoryTabVanilla().init((Screen)screen2));
                event.addListener((GuiEventListener)new InventoryTabFactions().init((Screen)screen2));
                event.addListener((GuiEventListener)new InventoryTabQuests().init((Screen)screen2));
            }
        }
    }

    public static void drawSelectionBox(PoseStack matrixStack, MultiBufferSource buffer, BlockPos pos) {
        matrixStack.m_85836_();
        AABB bb = new AABB(BlockPos.f_121853_, pos);
        matrixStack.m_252880_(0.001f, 0.001f, 0.001f);
        LevelRenderer.m_109646_((PoseStack)matrixStack, (VertexConsumer)buffer.m_6299_(RenderType.m_110504_()), (AABB)bb, (float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        matrixStack.m_85849_();
    }
}

