/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.block.model.ItemTransform
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SwordItem
 */
package noppes.npcs.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.client.layer.LayerInterface;

public class LayerBackItem
extends LayerInterface {
    public LayerBackItem(LivingEntityRenderer render) {
        super(render);
    }

    @Override
    public void render(PoseStack mStack, MultiBufferSource typeBuffer, int lightmapUV, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
        Minecraft minecraft = Minecraft.m_91087_();
        ItemStack itemstack = ItemStackWrapper.MCItem(this.npc.inventory.getRightHand());
        if (NoppesUtilServer.IsItemStackNull(itemstack) || this.npc.isAttacking()) {
            return;
        }
        Item item = itemstack.m_41720_();
        if (item instanceof BlockItem) {
            return;
        }
        mStack.m_85836_();
        this.base.f_102810_.m_104299_(mStack);
        mStack.m_252880_(0.0f, 0.36f, 0.14f);
        mStack.m_252781_(Axis.f_252529_.m_252977_(180.0f));
        if (item instanceof SwordItem) {
            mStack.m_252781_(Axis.f_252495_.m_252977_(180.0f));
        }
        BakedModel model = minecraft.m_91291_().m_115103_().m_109406_(itemstack);
        ItemTransform p_175034_1_ = model.m_7442_().f_111788_;
        mStack.m_85841_(p_175034_1_.f_111757_.x(), p_175034_1_.f_111757_.y(), p_175034_1_.f_111757_.z());
        minecraft.m_91291_().m_269491_((LivingEntity)this.npc, itemstack, ItemDisplayContext.NONE, false, mStack, typeBuffer, this.npc.m_9236_(), lightmapUV, LivingEntityRenderer.m_115338_((LivingEntity)this.npc, (float)0.0f), 0);
        mStack.m_85849_();
    }

    @Override
    public void rotate(PoseStack matrixStack, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}

