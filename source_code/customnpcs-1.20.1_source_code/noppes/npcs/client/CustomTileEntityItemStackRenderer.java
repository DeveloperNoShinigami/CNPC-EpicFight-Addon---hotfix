/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.geom.EntityModelSet
 *  net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.client.extensions.common.IClientItemExtensions
 */
package noppes.npcs.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import noppes.npcs.items.ItemNpcBlock;

public class CustomTileEntityItemStackRenderer
extends BlockEntityWithoutLevelRenderer {
    private static CustomTileEntityItemStackRenderer i = null;
    public static IClientItemExtensions itemRenderProperties = new IClientItemExtensions(){

        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return CustomTileEntityItemStackRenderer.instance();
        }
    };
    private HashMap<Block, BlockEntity> data = new HashMap();
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    public CustomTileEntityItemStackRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet model) {
        super(dispatcher, model);
        this.blockEntityRenderDispatcher = dispatcher;
    }

    public void m_108829_(ItemStack stack, ItemDisplayContext p_239207_2_, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (stack.m_41720_() instanceof ItemNpcBlock) {
            ItemNpcBlock item = (ItemNpcBlock)stack.m_41720_();
            BlockEntity tile = this.data.get(item.block);
            if (tile == null) {
                tile = ((BaseEntityBlock)item.block).m_142194_(BlockPos.f_121853_, item.block.m_49966_());
                this.data.put(item.block, tile);
            }
            this.blockEntityRenderDispatcher.m_112272_(tile, matrixStack, buffer, combinedLight, combinedOverlay);
        }
    }

    public static CustomTileEntityItemStackRenderer instance() {
        if (i != null) {
            return i;
        }
        Minecraft mc = Minecraft.m_91087_();
        i = new CustomTileEntityItemStackRenderer(mc.m_167982_(), mc.m_167973_());
        return i;
    }
}

