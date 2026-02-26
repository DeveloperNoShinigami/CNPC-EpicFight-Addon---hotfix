/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 */
package noppes.npcs.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.entity.EntityNPCInterface;

public class RenderNpcDragon<T extends EntityNPCInterface, M extends EntityModel<T>>
extends RenderNPCInterface<T, M> {
    public RenderNpcDragon(EntityRendererProvider.Context manager, M model, float f) {
        super(manager, model, f);
    }

    @Override
    protected void scale(T npc, PoseStack matrixScale, float f) {
        matrixScale.m_252880_(0.0f, 0.0f, 0.120000005f * (float)((EntityNPCInterface)((Object)npc)).display.getSize());
        super.scale(npc, matrixScale, f);
    }
}

