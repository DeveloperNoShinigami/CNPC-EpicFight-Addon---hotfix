/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.phys.AABB
 */
package noppes.npcs.client.renderer.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.AABB;
import noppes.npcs.CustomBlocks;
import noppes.npcs.blocks.tiles.TileBuilder;
import noppes.npcs.client.ClientEventHandler;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;
import noppes.npcs.schematics.Schematic;

public class BlockBuilderRenderer
extends BlockRendererInterface<TileBuilder> {
    private static final ItemStack item = new ItemStack((ItemLike)CustomBlocks.builder);
    public static Schematic schematic = null;
    public static BlockPos pos = null;

    public BlockBuilderRenderer(BlockEntityRendererProvider.Context dispatcher) {
        super(dispatcher);
    }

    public void render(TileBuilder tile, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        matrixStack.m_85836_();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.disableBlend();
        if (tile.m_58899_().equals((Object)TileBuilder.DrawPos)) {
            ClientEventHandler.onRenderTick(matrixStack, tile.m_58899_(), tile);
        }
        matrixStack.m_252880_(0.5f, 0.5f, 0.5f);
        matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0f));
        matrixStack.m_85849_();
    }

    public void drawSelectionBox(PoseStack matrixStack, MultiBufferSource buffer, BlockPos pos) {
        AABB bb = new AABB(BlockPos.f_121853_, pos);
        matrixStack.m_252880_(0.001f, 0.001f, 0.001f);
        LevelRenderer.m_109646_((PoseStack)matrixStack, (VertexConsumer)buffer.m_6299_(RenderType.m_110504_()), (AABB)bb, (float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
    }
}

