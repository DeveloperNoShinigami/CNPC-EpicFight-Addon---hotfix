/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.Font$DisplayMode
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package noppes.npcs.client.renderer.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;
import noppes.npcs.blocks.tiles.TileScripted;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.renderer.blocks.BlockRendererInterface;

public class BlockScriptedRenderer
extends BlockRendererInterface<TileScripted> {
    private static RandomSource random = RandomSource.m_216327_();

    public BlockScriptedRenderer(BlockEntityRendererProvider.Context dispatcher) {
        super(dispatcher);
    }

    public void render(TileScripted tile, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        matrixStack.m_85836_();
        if (this.overrideModel()) {
            matrixStack.m_252880_(0.5f, 0.5f, 0.5f);
            matrixStack.m_85841_(2.0f, 2.0f, 2.0f);
            this.renderItem(new ItemStack((ItemLike)CustomBlocks.scripted), matrixStack, buffer, light, overlay);
        } else {
            matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)tile.rotationY));
            matrixStack.m_252781_(Axis.f_252529_.m_252977_((float)tile.rotationX));
            matrixStack.m_252781_(Axis.f_252403_.m_252977_((float)tile.rotationZ));
            matrixStack.m_85841_(tile.scaleX, tile.scaleY, tile.scaleZ);
            Block b = tile.blockModel;
            if (b == null || b == Blocks.f_50016_ || b == CustomBlocks.scripted) {
                matrixStack.m_252880_(0.5f, 0.5f, 0.5f);
                matrixStack.m_85841_(2.0f, 2.0f, 2.0f);
                this.renderItem(tile.itemModel, matrixStack, buffer, light, overlay);
            } else {
                BlockState state = b.m_49966_();
                this.renderBlock(tile, b, state, matrixStack, buffer, light, overlay);
                if (state.m_155947_() && !tile.renderTileErrored) {
                    try {
                        BlockEntityRenderer renderer;
                        if (tile.renderTile == null) {
                            BlockEntity entity = ((EntityBlock)b).m_142194_(tile.m_58899_(), state);
                            entity.m_142339_(tile.m_58904_());
                            tile.renderTile = entity;
                            tile.renderState = state;
                            tile.renderTileUpdate = ((EntityBlock)b).m_142354_(tile.m_58904_(), state, entity.m_58903_());
                        }
                        if ((renderer = Minecraft.m_91087_().m_167982_().m_112265_(tile.renderTile)) != null) {
                            renderer.m_6922_(tile.renderTile, partialTicks, matrixStack, buffer, light, overlay);
                        } else {
                            tile.renderTileErrored = true;
                        }
                    }
                    catch (Exception e) {
                        tile.renderTileErrored = true;
                    }
                }
            }
        }
        matrixStack.m_85849_();
        if (!tile.text1.text.isEmpty()) {
            this.drawText(matrixStack, tile.text1, buffer, light, overlay);
        }
        if (!tile.text2.text.isEmpty()) {
            this.drawText(matrixStack, tile.text2, buffer, light, overlay);
        }
        if (!tile.text3.text.isEmpty()) {
            this.drawText(matrixStack, tile.text3, buffer, light, overlay);
        }
        if (!tile.text4.text.isEmpty()) {
            this.drawText(matrixStack, tile.text4, buffer, light, overlay);
        }
        if (!tile.text5.text.isEmpty()) {
            this.drawText(matrixStack, tile.text5, buffer, light, overlay);
        }
        if (!tile.text6.text.isEmpty()) {
            this.drawText(matrixStack, tile.text6, buffer, light, overlay);
        }
    }

    private void drawText(PoseStack matrixStack, TileScripted.TextPlane text1, MultiBufferSource buffer, int light, int overlay) {
        if (text1.textBlock == null || text1.textHasChanged) {
            text1.textBlock = new TextBlockClient(text1.text, 336, true, Minecraft.m_91087_().f_91074_);
            text1.textHasChanged = false;
        }
        matrixStack.m_85836_();
        matrixStack.m_85837_(0.5, 0.5, 0.5);
        matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)text1.rotationY));
        matrixStack.m_252781_(Axis.f_252529_.m_252977_((float)text1.rotationX));
        matrixStack.m_252781_(Axis.f_252403_.m_252977_((float)text1.rotationZ));
        matrixStack.m_85841_(text1.scale, text1.scale, 1.0f);
        matrixStack.m_252880_(text1.offsetX, text1.offsetY, text1.offsetZ);
        float f1 = 0.6666667f;
        float f3 = 0.0133f * f1;
        matrixStack.m_252880_(0.0f, 0.5f, 0.01f);
        matrixStack.m_85841_(f3, -f3, f3);
        Font fontrenderer = Minecraft.m_91087_().f_91062_;
        float lineOffset = 0.0f;
        if (text1.textBlock.lines.size() < 14) {
            lineOffset = (14.0f - (float)text1.textBlock.lines.size()) / 2.0f;
        }
        for (int i = 0; i < text1.textBlock.lines.size(); ++i) {
            Component text = text1.textBlock.lines.get(i);
            float f = -fontrenderer.m_92852_((FormattedText)text) / 2;
            double d = lineOffset + (float)i;
            Objects.requireNonNull(fontrenderer);
            fontrenderer.m_272077_(text, f, (float)((int)(d * (9.0 - 0.3))), 0, false, matrixStack.m_85850_().m_252922_(), buffer, Font.DisplayMode.NORMAL, light, overlay);
        }
        matrixStack.m_85849_();
    }

    private void renderItem(ItemStack item, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        Minecraft.m_91087_().m_91291_().m_269128_(item, ItemDisplayContext.FIXED, light, OverlayTexture.f_118083_, matrixStack, buffer, null, 0);
    }

    private void renderBlock(TileScripted tile, Block b, BlockState state, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        matrixStack.m_85836_();
        Minecraft.m_91087_().m_91289_().m_110912_(state, matrixStack, buffer, light, OverlayTexture.f_118083_);
        if (random.m_188503_(12) == 1) {
            state.m_60734_().m_214162_(state, tile.m_58904_(), tile.m_58899_(), random);
        }
        matrixStack.m_85849_();
    }

    private boolean overrideModel() {
        ItemStack held = Minecraft.m_91087_().f_91074_.m_21205_();
        if (held == null) {
            return false;
        }
        return held.m_41720_() == CustomItems.wand || held.m_41720_() == CustomItems.scripter;
    }
}

