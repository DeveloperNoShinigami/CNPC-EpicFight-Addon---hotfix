/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.client.model;

import net.minecraft.resources.ResourceLocation;
import noppes.npcs.client.model.ModelScaleRenderer;
import noppes.npcs.constants.EnumParts;
import noppes.npcs.shared.client.model.Model2DRenderer;
import noppes.npcs.shared.client.model.NopModelPart;

public class ModelHeadwear
extends ModelScaleRenderer {
    public ModelHeadwear() {
        super(null, EnumParts.HEAD);
        ResourceLocation location = new ResourceLocation("");
        Model2DRenderer right = new Model2DRenderer(64, 64, 32, 8, 8, 8, location);
        right.setPos(-4.641f, 0.8f, 4.64f);
        right.setScale(0.58f);
        right.setThickness(0.65f);
        this.setRotation(right, 0.0f, 1.5707964f, 0.0f);
        this.addChild(right);
        Model2DRenderer left = new Model2DRenderer(64, 64, 48, 8, 8, 8, location);
        left.setPos(4.639f, 0.8f, -4.64f);
        left.setScale(0.58f);
        left.setThickness(0.65f);
        this.setRotation(left, 0.0f, -1.5707964f, 0.0f);
        this.addChild(left);
        Model2DRenderer front = new Model2DRenderer(64, 64, 40, 8, 8, 8, location);
        front.setPos(-4.64f, 0.801f, -4.641f);
        front.setScale(0.58f);
        front.setThickness(0.65f);
        this.setRotation(front, 0.0f, 0.0f, 0.0f);
        this.addChild(front);
        Model2DRenderer back = new Model2DRenderer(64, 64, 56, 8, 8, 8, location);
        back.setPos(4.64f, 0.801f, 4.639f);
        back.setScale(0.58f);
        back.setThickness(0.65f);
        this.setRotation(back, 0.0f, (float)Math.PI, 0.0f);
        this.addChild(back);
        Model2DRenderer top = new Model2DRenderer(64, 64, 40, 0, 8, 8, location);
        top.setPos(-4.64f, -8.5f, -4.64f);
        top.setScale(0.5799f);
        top.setThickness(0.65f);
        this.setRotation(top, -1.5707964f, 0.0f, 0.0f);
        this.addChild(top);
        Model2DRenderer bottom = new Model2DRenderer(64, 64, 48, 0, 8, 8, location);
        bottom.setPos(-4.64f, 0.0f, -4.64f);
        bottom.setScale(0.5799f);
        bottom.setThickness(0.65f);
        this.setRotation(bottom, -1.5707964f, 0.0f, 0.0f);
        this.addChild(bottom);
    }

    @Override
    public void setRotation(NopModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }
}

