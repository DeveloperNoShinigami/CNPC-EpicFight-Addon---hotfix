/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 */
package noppes.npcs.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import noppes.npcs.client.model.ModelNpcCrystal;
import noppes.npcs.client.renderer.RenderNPCInterface;

public class RenderNpcCrystal
extends RenderNPCInterface {
    ModelNpcCrystal mainmodel;

    public RenderNpcCrystal(EntityRendererProvider.Context manager, ModelNpcCrystal model) {
        super(manager, model, 0.0f);
        this.mainmodel = model;
    }
}

