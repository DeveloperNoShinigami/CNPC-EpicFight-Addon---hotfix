/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package noppes.npcs.client.renderer.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BlockRendererInterface<T extends BlockEntity>
implements BlockEntityRenderer<T> {
    public static float[][] colorTable = new float[][]{{1.0f, 1.0f, 1.0f}, {0.95f, 0.7f, 0.2f}, {0.9f, 0.5f, 0.85f}, {0.6f, 0.7f, 0.95f}, {0.9f, 0.9f, 0.2f}, {0.5f, 0.8f, 0.1f}, {0.95f, 0.7f, 0.8f}, {0.3f, 0.3f, 0.3f}, {0.6f, 0.6f, 0.6f}, {0.3f, 0.6f, 0.7f}, {0.7f, 0.4f, 0.9f}, {0.2f, 0.4f, 0.8f}, {0.5f, 0.4f, 0.3f}, {0.4f, 0.5f, 0.2f}, {0.8f, 0.3f, 0.3f}, {0.1f, 0.1f, 0.1f}};

    public BlockRendererInterface(BlockEntityRendererProvider.Context dispatcher) {
    }

    public boolean playerTooFar(BlockEntity tile) {
        double d8;
        double d7;
        Minecraft mc = Minecraft.m_91087_();
        double d6 = mc.m_91288_().m_20185_() - (double)tile.m_58899_().m_123341_();
        return d6 * d6 + (d7 = mc.m_91288_().m_20186_() - (double)tile.m_58899_().m_123342_()) * d7 + (d8 = mc.m_91288_().m_20189_() - (double)tile.m_58899_().m_123343_()) * d8 > (double)(this.specialRenderDistance() * this.specialRenderDistance());
    }

    public int specialRenderDistance() {
        return 20;
    }
}

