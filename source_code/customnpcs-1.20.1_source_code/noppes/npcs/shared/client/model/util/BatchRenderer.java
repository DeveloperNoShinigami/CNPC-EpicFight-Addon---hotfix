/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.ShaderInstance
 *  net.minecraft.resources.ResourceLocation
 *  org.joml.Matrix4f
 *  org.lwjgl.system.MemoryUtil
 */
package noppes.npcs.shared.client.model.util;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.nio.FloatBuffer;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.shared.common.util.NopVector2i;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

public class BatchRenderer {
    private static final FloatBuffer MATRIX_BUFFER = MemoryUtil.memAllocFloat((int)16);
    public static RenderType lastType = null;
    private static final BatchRenderer instance = new BatchRenderer();
    private final Map<RenderType, List<Batch>> queue = new LinkedHashMap<RenderType, List<Batch>>();

    public static BatchRenderer getInstance() {
        return instance;
    }

    public void add(RenderType renderType, ResourceLocation resource, int id, VertexFormat format, Matrix4f matrix, int vertexCount, NopVector2i texPos, int light, int overlay, float red, float green, float blue, float alpha) {
        if (renderType == null) {
            renderType = lastType;
        }
        this.queue.computeIfAbsent(renderType, k -> new LinkedList()).add(new Batch(resource, id, format, matrix, vertexCount, texPos, light, overlay, red, green, blue, alpha));
    }

    public void draw() {
        this.queue.forEach((renderType, batches) -> {
            if (batches.isEmpty()) {
                return;
            }
            RenderSystem.assertOnRenderThread();
            renderType.m_110185_();
            RenderSystem.setShader(GameRenderer::m_172838_);
            ShaderInstance shaderinstance = RenderSystem.getShader();
            for (Batch b : batches) {
                RenderSystem.setShaderTexture((int)0, (ResourceLocation)b.resource);
                shaderinstance.f_173312_.m_5941_(new float[]{b.red, b.green, b.blue, b.alpha});
                if (shaderinstance.f_173313_ != null) {
                    shaderinstance.f_173313_.m_142617_(b.light1);
                }
                if (shaderinstance.f_173314_ != null) {
                    shaderinstance.f_173314_.m_142617_(b.light2);
                }
                shaderinstance.f_173308_.m_5679_(b.matrix);
                if (shaderinstance.f_200956_ != null) {
                    shaderinstance.f_200956_.m_200759_(RenderSystem.getInverseViewRotationMatrix());
                }
                if (shaderinstance.f_173315_ != null) {
                    shaderinstance.f_173315_.m_5985_(RenderSystem.getShaderFogStart());
                }
                if (shaderinstance.f_173316_ != null) {
                    shaderinstance.f_173316_.m_5985_(RenderSystem.getShaderFogEnd());
                }
                if (shaderinstance.f_173317_ != null) {
                    shaderinstance.f_173317_.m_5941_(RenderSystem.getShaderFogColor());
                }
                if (shaderinstance.f_202432_ != null) {
                    shaderinstance.f_202432_.m_142617_(RenderSystem.getShaderFogShape().m_202324_());
                }
                if (shaderinstance.f_173310_ != null) {
                    shaderinstance.f_173310_.m_5679_(BatchRenderer.createTranslateMatrix(b.texPos.x, b.texPos.y, 0.0f));
                }
                if (shaderinstance.f_173319_ != null) {
                    shaderinstance.f_173319_.m_5985_(RenderSystem.getShaderGameTime());
                }
                if (shaderinstance.f_173311_ != null) {
                    Window window = Minecraft.m_91087_().m_91268_();
                    shaderinstance.f_173311_.m_7971_((float)window.m_85441_(), (float)window.m_85442_());
                }
                RenderSystem.glBindBuffer((int)34962, () -> b.id);
                b.format.m_166912_();
                shaderinstance.m_173363_();
                RenderSystem.drawElements((int)4, (int)0, (int)b.vertexCount);
                shaderinstance.m_173362_();
                b.format.m_86024_();
                RenderSystem.glBindBuffer((int)34962, () -> 0);
            }
            renderType.m_110188_();
        });
        this.queue.clear();
    }

    public static Matrix4f createTranslateMatrix(float p_27654_, float p_27655_, float p_27656_) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.m00(1.0f);
        matrix4f.m11(1.0f);
        matrix4f.m22(1.0f);
        matrix4f.m33(1.0f);
        matrix4f.m03(p_27654_);
        matrix4f.m13(p_27655_);
        matrix4f.m23(p_27656_);
        return matrix4f;
    }

    class Batch {
        final Matrix4f matrix;
        final int vertexCount;
        final ResourceLocation resource;
        final int id;
        final VertexFormat format;
        final int light1;
        final int light2;
        final int overlay1;
        final int overlay2;
        final float red;
        final float green;
        final float blue;
        final float alpha;
        final NopVector2i texPos;

        public Batch(ResourceLocation resource, int id, VertexFormat format, Matrix4f matrix, int vertexCount, NopVector2i texPos, int light, int overlay, float red, float green, float blue, float alpha) {
            this.resource = resource;
            this.id = id;
            this.format = format;
            this.matrix = matrix;
            this.vertexCount = vertexCount;
            this.texPos = texPos;
            this.light1 = light & 0xFFFF;
            this.light2 = light >> 16 & 0xFFFF;
            this.overlay1 = overlay & 0xFFFF;
            this.overlay2 = overlay >> 16 & 0xFFFF;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
    }
}

