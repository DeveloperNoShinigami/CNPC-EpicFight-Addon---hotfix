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

public class ModelMailboxUS
extends Model {
    NopModelPart Shape1 = new NopModelPart(64, 128, 0, 48);
    NopModelPart Shape2;
    NopModelPart Shape3;
    NopModelPart Shape4;
    NopModelPart Shape5;
    NopModelPart Shape6;
    NopModelPart Shape7;
    NopModelPart Shape8;
    NopModelPart Shape9;
    NopModelPart Shape10;
    NopModelPart Shape11;
    NopModelPart Shape12;
    NopModelPart Shape13;

    public ModelMailboxUS() {
        super(RenderType::m_110452_);
        this.Shape1.addBox(0.0f, 0.0f, 0.0f, 16.0f, 14.0f, 16.0f);
        this.Shape1.setPos(-8.0f, 8.0f, -8.0f);
        this.Shape2 = new NopModelPart(64, 128, 0, 79);
        this.Shape2.addBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        this.Shape2.setPos(-8.0f, 22.0f, -8.0f);
        this.Shape3 = new NopModelPart(64, 128, 5, 79);
        this.Shape3.addBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        this.Shape3.setPos(-8.0f, 22.0f, 7.0f);
        this.Shape4 = new NopModelPart(64, 128, 10, 79);
        this.Shape4.addBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        this.Shape4.setPos(7.0f, 22.0f, -8.0f);
        this.Shape5 = new NopModelPart(64, 128, 15, 79);
        this.Shape5.addBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        this.Shape5.setPos(7.0f, 22.0f, 7.0f);
        this.Shape6 = new NopModelPart(64, 128, 0, 14);
        this.Shape6.addBox(0.0f, 0.0f, 0.0f, 16.0f, 3.0f, 7.0f);
        this.Shape6.setPos(-8.0f, 5.0f, 0.0f);
        this.Shape7 = new NopModelPart(64, 128, 0, 6);
        this.Shape7.addBox(0.0f, 0.0f, 0.0f, 16.0f, 2.0f, 6.0f);
        this.Shape7.setPos(-8.0f, 3.0f, 0.0f);
        this.Shape8 = new NopModelPart(64, 128, 0, 0);
        this.Shape8.addBox(0.0f, 0.0f, 0.0f, 16.0f, 1.0f, 5.0f);
        this.Shape8.setPos(-8.0f, 2.0f, 0.0f);
        this.Shape9 = new NopModelPart(64, 128, 0, 37);
        this.Shape9.addBox(0.0f, 0.0f, 0.0f, 1.0f, 3.0f, 7.0f);
        this.Shape9.setPos(-8.0f, 5.0f, -7.0f);
        this.Shape10 = new NopModelPart(64, 128, 16, 37);
        this.Shape10.addBox(0.0f, 0.0f, 0.0f, 1.0f, 3.0f, 7.0f);
        this.Shape10.setPos(7.0f, 5.0f, -7.0f);
        this.Shape11 = new NopModelPart(64, 128, 0, 29);
        this.Shape11.addBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 6.0f);
        this.Shape11.setPos(-8.0f, 3.0f, -6.0f);
        this.Shape12 = new NopModelPart(64, 128, 14, 29);
        this.Shape12.addBox(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 6.0f);
        this.Shape12.setPos(7.0f, 3.0f, -6.0f);
        this.Shape13 = new NopModelPart(64, 128, 0, 25);
        this.Shape13.addBox(0.0f, 0.0f, 0.0f, 16.0f, 1.0f, 3.0f);
        this.Shape13.setPos(-8.0f, 2.0f, -3.0f);
    }

    public void m_7695_(PoseStack mStack, VertexConsumer iVertex, int lightmapUV, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Shape1.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape2.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape3.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape4.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape5.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape6.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape7.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape8.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape9.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape10.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape11.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape12.render(mStack, iVertex, lightmapUV, packedOverlayIn);
        this.Shape13.render(mStack, iVertex, lightmapUV, packedOverlayIn);
    }
}

