/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.state.properties.Property
 */
package noppes.npcs.client.renderer.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.Property;
import noppes.npcs.blocks.BlockCarpentryBench;
import noppes.npcs.blocks.tiles.TileBlockAnvil;
import noppes.npcs.client.model.blocks.ModelCarpentryBench;

public class BlockCarpentryBenchRenderer
implements BlockEntityRenderer<TileBlockAnvil> {
    private final ModelCarpentryBench model = new ModelCarpentryBench();
    private static final ResourceLocation TEXTURE = new ResourceLocation("customnpcs", "textures/models/carpentrybench.png");
    private static final RenderType type = RenderType.m_110452_((ResourceLocation)TEXTURE);

    public BlockCarpentryBenchRenderer(BlockEntityRendererProvider.Context dispatcher) {
    }

    public void render(TileBlockAnvil te, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        int rotation = 0;
        if (te.m_58899_() != BlockPos.f_121853_) {
            rotation = (Integer)te.m_58900_().m_61143_((Property)BlockCarpentryBench.ROTATION);
        }
        matrixStack.m_85836_();
        RenderSystem.disableBlend();
        matrixStack.m_252880_(0.5f, 1.4f, 0.5f);
        matrixStack.m_85841_(0.95f, 0.95f, 0.95f);
        matrixStack.m_252781_(Axis.f_252403_.m_252977_(180.0f));
        matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)(90 * rotation)));
        this.model.m_7695_(matrixStack, buffer.m_6299_(type), light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.m_85849_();
    }
}

