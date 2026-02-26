/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.client.renderer.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.blocks.tiles.TileMailbox;
import noppes.npcs.client.model.blocks.ModelMailboxUS;
import noppes.npcs.client.model.blocks.ModelMailboxWow;

public class BlockMailboxRenderer<T extends TileMailbox>
implements BlockEntityRenderer<T> {
    private final ModelMailboxUS model = new ModelMailboxUS();
    private final ModelMailboxWow model2 = new ModelMailboxWow();
    private static final ResourceLocation text1 = new ResourceLocation("customnpcs", "textures/models/mailbox1.png");
    private static final ResourceLocation text2 = new ResourceLocation("customnpcs", "textures/models/mailbox2.png");
    private static final ResourceLocation text3 = new ResourceLocation("customnpcs", "textures/models/mailbox3.png");
    private static final RenderType type1 = RenderType.m_110452_((ResourceLocation)text1);
    private static final RenderType type2 = RenderType.m_110452_((ResourceLocation)text2);
    private static final RenderType type3 = RenderType.m_110452_((ResourceLocation)text3);

    public BlockMailboxRenderer(BlockEntityRendererProvider.Context dispatcher) {
    }

    public void render(TileMailbox te, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        int meta = 0;
        int type = te.getModel();
        matrixStack.m_85836_();
        matrixStack.m_252880_(0.5f, 1.5f, 0.5f);
        matrixStack.m_252781_(Axis.f_252403_.m_252977_(180.0f));
        matrixStack.m_252781_(Axis.f_252436_.m_252977_((float)(90 * meta)));
        if (type == 0) {
            this.model.m_7695_(matrixStack, buffer.m_6299_(type1), light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        } else if (type == 1) {
            this.model2.m_7695_(matrixStack, buffer.m_6299_(type2), light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        } else if (type == 2) {
            this.model2.m_7695_(matrixStack, buffer.m_6299_(type3), light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        matrixStack.m_85849_();
    }
}

