/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager
 *  com.mojang.blaze3d.platform.MemoryTracker
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package noppes.npcs.shared.client.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.MemoryTracker;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

@OnlyIn(value=Dist.CLIENT)
public class CTextureUtil {
    private static final IntBuffer DATA_BUFFER = MemoryTracker.m_182527_((int)0x1000000).asIntBuffer();
    private static final float[] COLOR_GAMMAS = new float[256];

    public static void deleteTexture(int textureId) {
        RenderSystem.deleteTexture((int)textureId);
    }

    public static int uploadTextureImage(int textureId, BufferedImage texture) {
        return CTextureUtil.uploadTextureImageSub(textureId, texture, 0, 0);
    }

    public static void allocateTexture(int textureId, int width, int height) {
        CTextureUtil.allocateTextureImpl(textureId, 0, width, height);
    }

    public static void allocateTextureImpl(int glTextureId, int mipmapLevels, int width, int height) {
        RenderSystem.assertOnRenderThreadOrInit();
        CTextureUtil.deleteTexture(glTextureId);
        CTextureUtil.bind(glTextureId);
        if (mipmapLevels >= 0) {
            RenderSystem.texParameter((int)3553, (int)33085, (int)mipmapLevels);
            RenderSystem.texParameter((int)3553, (int)33082, (int)0);
            RenderSystem.texParameter((int)3553, (int)33083, (int)mipmapLevels);
            GlStateManager._texParameter((int)3553, (int)34049, (float)0.0f);
        }
        for (int i = 0; i <= mipmapLevels; ++i) {
            GlStateManager._texImage2D((int)3553, (int)i, (int)6408, (int)(width >> i), (int)(height >> i), (int)0, (int)6408, (int)5121, (IntBuffer)null);
        }
    }

    public static int uploadTextureImageSub(int textureId, BufferedImage p_110995_1_, int p_110995_2_, int p_110995_3_) {
        CTextureUtil.uploadTextureImageSubImpl(textureId, p_110995_1_, p_110995_2_, p_110995_3_);
        return textureId;
    }

    private static void uploadTextureImageSubImpl(int textureId, BufferedImage bufferedimage, int p_110993_1_, int p_110993_2_) {
        if (bufferedimage != null) {
            int i = bufferedimage.getWidth();
            int j = bufferedimage.getHeight();
            int[] aint = new int[i * j];
            bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
            IntBuffer intbuffer = BufferUtils.createIntBuffer((int)(i * j));
            intbuffer.put(aint);
            RenderSystem.activeTexture((int)33984);
            RenderSystem.bindTextureForSetup((int)textureId);
            GL11.glPixelStorei((int)3312, (int)0);
            GL11.glPixelStorei((int)3313, (int)0);
            GL11.glPixelStorei((int)3314, (int)0);
            GL11.glPixelStorei((int)3315, (int)0);
            GL11.glPixelStorei((int)3316, (int)0);
            GL11.glPixelStorei((int)3317, (int)4);
            GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)bufferedimage.getWidth(), (int)bufferedimage.getHeight(), (int)0, (int)32993, (int)33639, (IntBuffer)intbuffer);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        }
    }

    private static void setTextureClamped(boolean p_110997_0_) {
        if (p_110997_0_) {
            RenderSystem.texParameter((int)3553, (int)10242, (int)10496);
            RenderSystem.texParameter((int)3553, (int)10243, (int)10496);
        } else {
            RenderSystem.texParameter((int)3553, (int)10242, (int)10497);
            RenderSystem.texParameter((int)3553, (int)10243, (int)10497);
        }
    }

    private static void setTextureBlurred(boolean p_147951_0_) {
        CTextureUtil.setTextureBlurMipmap(p_147951_0_, false);
    }

    private static void setTextureBlurMipmap(boolean p_147954_0_, boolean p_147954_1_) {
        if (p_147954_0_) {
            RenderSystem.texParameter((int)3553, (int)10241, (int)(p_147954_1_ ? 9987 : 9729));
            RenderSystem.texParameter((int)3553, (int)10240, (int)9729);
        } else {
            RenderSystem.texParameter((int)3553, (int)10241, (int)(p_147954_1_ ? 9986 : 9728));
            RenderSystem.texParameter((int)3553, (int)10240, (int)9728);
        }
    }

    private static void copyToBuffer(int[] p_110990_0_, int p_110990_1_) {
        CTextureUtil.copyToBufferPos(p_110990_0_, 0, p_110990_1_);
    }

    private static void copyToBufferPos(int[] p_110994_0_, int p_110994_1_, int p_110994_2_) {
        int[] aint = p_110994_0_;
        DATA_BUFFER.clear();
        DATA_BUFFER.put(aint, p_110994_1_, p_110994_2_);
        DATA_BUFFER.position(0).limit(p_110994_2_);
    }

    static void bind(int p_94277_0_) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlStateManager._bindTexture((int)p_94277_0_);
    }

    public static int[] updateAnaglyph(int[] p_110985_0_) {
        int[] aint = new int[p_110985_0_.length];
        for (int i = 0; i < p_110985_0_.length; ++i) {
            aint[i] = CTextureUtil.anaglyphColor(p_110985_0_[i]);
        }
        return aint;
    }

    public static int anaglyphColor(int p_177054_0_) {
        int i = p_177054_0_ >> 24 & 0xFF;
        int j = p_177054_0_ >> 16 & 0xFF;
        int k = p_177054_0_ >> 8 & 0xFF;
        int l = p_177054_0_ & 0xFF;
        int i1 = (j * 30 + k * 59 + l * 11) / 100;
        int j1 = (j * 30 + k * 70) / 100;
        int k1 = (j * 30 + l * 70) / 100;
        return i << 24 | i1 << 16 | j1 << 8 | k1;
    }

    static {
        for (int i1 = 0; i1 < COLOR_GAMMAS.length; ++i1) {
            CTextureUtil.COLOR_GAMMAS[i1] = (float)Math.pow((float)i1 / 255.0f, 2.2);
        }
    }
}

