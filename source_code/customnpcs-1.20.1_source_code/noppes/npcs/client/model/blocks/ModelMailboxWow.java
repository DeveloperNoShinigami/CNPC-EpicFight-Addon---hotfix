/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.Model
 *  net.minecraft.client.renderer.RenderType
 */
package noppes.npcs.client.model.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import noppes.npcs.shared.client.model.NopModelPart;

public class ModelMailboxWow
extends Model {
    NopModelPart Shape4 = new NopModelPart(128, 64, 59, 0);
    NopModelPart Shape1;
    NopModelPart Shape2;
    NopModelPart Shape3;

    public ModelMailboxWow() {
        super(RenderType::m_110452_);
        this.Shape4.addBox(0.0f, 0.0f, 0.0f, 8.0f, 6.0f, 0.0f);
        this.Shape4.setPos(-4.0f, -4.0f, 0.0f);
        this.Shape1 = new NopModelPart(128, 64, 0, 39);
        this.Shape1.addBox(0.0f, 0.0f, 0.0f, 8.0f, 5.0f, 8.0f);
        this.Shape1.setPos(-4.0f, 19.0f, -4.0f);
        this.Shape2 = new NopModelPart(128, 64, 0, 21);
        this.Shape2.addBox(0.0f, 0.0f, 0.0f, 6.0f, 9.0f, 6.0f);
        this.Shape2.setPos(-3.0f, 10.0f, -3.0f);
        this.Shape3 = new NopModelPart(128, 64, 0, 0);
        this.Shape3.addBox(0.0f, 0.0f, 0.0f, 12.0f, 8.0f, 12.0f);
        this.Shape3.setPos(-6.0f, 2.0f, -6.0f);
    }

    public void m_7695_(PoseStack mStack, VertexConsumer iVertex, int lightmapUV, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Shape4.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape1.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape2.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape3.render(mStack, iVertex, lightmapUV, packedOverlayIn);
    }
}

