/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.blaze3d.platform.TextureUtil
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.SimpleTexture
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.apache.commons.compress.utils.IOUtils
 *  org.lwjgl.opengl.GL11
 */
package noppes.npcs.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.data.PlayerSkinData;
import noppes.npcs.shared.common.util.LogWriter;
import org.apache.commons.compress.utils.IOUtils;
import org.lwjgl.opengl.GL11;

public class SkinUtil {
    private static final HashSet<ResourceLocation> createdSkins = new HashSet();

    public static ResourceLocation createPlayerSkin(PlayerSkinData skin) {
        LogWriter.debug("Check skin: " + skin);
        if (createdSkins.contains(skin.getResLoc())) {
            return skin.getResLoc();
        }
        String locSkin = String.format("assets/%s/%s", skin.getResLoc().m_135827_(), skin.getResLoc().m_135815_());
        File file = new File(CustomNpcs.Dir, locSkin);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists() && file.isFile()) {
            return null;
        }
        TextureManager tm = Minecraft.m_91087_().m_91097_();
        ResourceManager rm = Minecraft.m_91087_().m_91098_();
        ArrayList listBuffers = Lists.newArrayList();
        BufferedImage bodyImage = SkinUtil.readBufferedImage(rm, skin.getPartResLocByNumber(rm, "torsos", skin.getBodyType()));
        bodyImage = SkinUtil.colorTexture(bodyImage, new Color(skin.getBodyColor()));
        BufferedImage hairImage = SkinUtil.readBufferedImage(rm, skin.getPartResLocByNumber(rm, "hairs", skin.getHairType()));
        hairImage = SkinUtil.colorTexture(hairImage, new Color(skin.getHairColor()));
        BufferedImage faceImage = SkinUtil.readBufferedImage(rm, skin.getPartResLocByNumber(rm, "faces", skin.getFaceType()));
        faceImage = SkinUtil.colorTexture(faceImage, new Color(skin.getEyesColor()));
        BufferedImage legsImage = SkinUtil.readBufferedImage(rm, skin.getPartResLocByNumber(rm, "legs", skin.getPantsType()));
        BufferedImage jacketsImage = SkinUtil.readBufferedImage(rm, skin.getPartResLocByNumber(rm, "jackets", skin.getJacketType()));
        BufferedImage shoesImage = SkinUtil.readBufferedImage(rm, skin.getPartResLocByNumber(rm, "shoes", skin.getShoesType()));
        for (int pec : skin.getPeculiarities()) {
            listBuffers.add(SkinUtil.readBufferedImage(rm, skin.getPartResLocByNumber(rm, "peculiarities", pec)));
        }
        BufferedImage skinImage = SkinUtil.combineTextures(bodyImage, SkinUtil.readBufferedImage(rm, skin.getPartResLocByNumber(rm, "torsos", -1)));
        skinImage = SkinUtil.combineTextures(skinImage, faceImage);
        skinImage = SkinUtil.combineTextures(skinImage, legsImage);
        skinImage = SkinUtil.combineTextures(skinImage, shoesImage);
        skinImage = SkinUtil.combineTextures(skinImage, jacketsImage);
        skinImage = SkinUtil.combineTextures(skinImage, faceImage);
        skinImage = SkinUtil.combineTextures(skinImage, hairImage);
        if (!listBuffers.isEmpty()) {
            for (BufferedImage buffer : listBuffers) {
                skinImage = SkinUtil.combineTextures(skinImage, buffer);
            }
        }
        try {
            ImageIO.write((RenderedImage)skinImage, "PNG", file);
            LogWriter.debug("Create new player skin: " + file.getAbsolutePath());
        }
        catch (Exception pec) {
            // empty catch block
        }
        SimpleTexture texture = new SimpleTexture(skin.getResLoc());
        TextureUtil.prepareImage((int)texture.m_117963_(), (int)skinImage.getWidth(), (int)skinImage.getHeight());
        SkinUtil.uploadBufferedImageContents(skinImage, texture.m_117963_());
        tm.m_118495_(skin.getResLoc(), (AbstractTexture)texture);
        createdSkins.add(skin.getResLoc());
        return skin.getResLoc();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static BufferedImage readBufferedImage(ResourceManager rm, ResourceLocation resLoc) {
        BufferedImage bufferedImage;
        if (resLoc == null) {
            return null;
        }
        InputStream imageStream = null;
        try {
            imageStream = ((Resource)rm.m_213713_(resLoc).get()).m_215507_();
            bufferedImage = ImageIO.read(imageStream);
        }
        catch (IOException e) {
            BufferedImage bufferedImage2;
            try {
                bufferedImage2 = null;
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly(imageStream);
                throw throwable;
            }
            IOUtils.closeQuietly((Closeable)imageStream);
            return bufferedImage2;
        }
        IOUtils.closeQuietly((Closeable)imageStream);
        return bufferedImage;
    }

    private static void uploadBufferedImageContents(BufferedImage bufferedimage, int id) {
        int j = bufferedimage.getWidth();
        int k = bufferedimage.getHeight();
        int[] lvt_8_1_ = new int[j * k];
        bufferedimage.getRGB(0, 0, j, k, lvt_8_1_, 0, j);
        IntBuffer intbuffer = ByteBuffer.allocateDirect(4 * j * k).order(ByteOrder.nativeOrder()).asIntBuffer();
        intbuffer.put(lvt_8_1_);
        intbuffer.flip();
        RenderSystem.activeTexture((int)33984);
        RenderSystem.bindTexture((int)id);
        SkinUtil.initTexture(intbuffer, j, k);
    }

    public static void initTexture(IntBuffer p_225685_0_, int p_225685_1_, int p_225685_2_) {
        GL11.glPixelStorei((int)3312, (int)0);
        GL11.glPixelStorei((int)3313, (int)0);
        GL11.glPixelStorei((int)3314, (int)0);
        GL11.glPixelStorei((int)3315, (int)0);
        GL11.glPixelStorei((int)3316, (int)0);
        GL11.glPixelStorei((int)3317, (int)4);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)p_225685_1_, (int)p_225685_2_, (int)0, (int)32993, (int)33639, (IntBuffer)p_225685_0_);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
    }

    private static BufferedImage colorTexture(BufferedImage buffer, Color color) {
        if (buffer == null || color == null) {
            return buffer;
        }
        for (int v = 0; v < buffer.getHeight(); ++v) {
            for (int u = 0; u < buffer.getWidth(); ++u) {
                Object b;
                Object g;
                Object r;
                int c = buffer.getRGB(u, v);
                int al = c >> 24 & 0xFF;
                if (al == 0) continue;
                int r0 = c >> 16 & 0xFF;
                int g0 = c >> 8 & 0xFF;
                int b0 = c & 0xFF;
                Object a = Integer.toHexString(Math.min(al + color.getAlpha(), 255));
                if (((String)a).length() == 1) {
                    a = "0" + (String)a;
                }
                if (((String)(r = Integer.toHexString((r0 + color.getRed()) / 2))).length() == 1) {
                    r = "0" + (String)r;
                }
                if (((String)(g = Integer.toHexString((g0 + color.getGreen()) / 2))).length() == 1) {
                    g = "0" + (String)g;
                }
                if (((String)(b = Integer.toHexString((b0 + color.getBlue()) / 2))).length() == 1) {
                    b = "0" + (String)b;
                }
                buffer.setRGB(u, v, (int)Long.parseLong((String)a + (String)r + (String)g + (String)b, 16));
            }
        }
        return buffer;
    }

    private static BufferedImage combineTextures(BufferedImage buffer_0, BufferedImage buffer_1) {
        if (buffer_0 == null) {
            return buffer_1;
        }
        if (buffer_1 == null) {
            return buffer_0;
        }
        int w0 = buffer_0.getWidth();
        int w1 = buffer_1.getWidth();
        int h0 = buffer_0.getHeight();
        int h1 = buffer_1.getHeight();
        int w = Math.max(w0, w1);
        int h = Math.max(h0, h1);
        float sw0 = (float)w0 / (float)w;
        float sh0 = (float)h0 / (float)h;
        float sw1 = (float)w1 / (float)w;
        float sh1 = (float)h1 / (float)h;
        BufferedImage total = new BufferedImage(w, h, 6);
        for (int v = 0; v < h; ++v) {
            for (int u = 0; u < w; ++u) {
                Object b;
                Object g;
                Object r;
                int c1;
                int a1;
                int c0 = buffer_0.getRGB((int)((float)u * sw0), (int)((float)v * sh0));
                int a0 = c0 >> 24 & 0xFF;
                if (a0 != 0) {
                    total.setRGB(u, v, c0);
                }
                if ((a1 = (c1 = buffer_1.getRGB((int)((float)u * sw1), (int)((float)v * sh1))) >> 24 & 0xFF) == 0) continue;
                if (a1 == 255) {
                    total.setRGB(u, v, c1);
                    continue;
                }
                int r0 = c0 >> 16 & 0xFF;
                int g0 = c0 >> 8 & 0xFF;
                int b0 = c0 & 0xFF;
                int r1 = c1 >> 16 & 0xFF;
                int g1 = c1 >> 8 & 0xFF;
                int b1 = c1 & 0xFF;
                Object a = Integer.toHexString(Math.min(a0 + a1, 255));
                if (((String)a).length() == 1) {
                    a = "0" + (String)a;
                }
                if (((String)(r = Integer.toHexString((r0 + r1) / 2))).length() == 1) {
                    r = "0" + (String)r;
                }
                if (((String)(g = Integer.toHexString((g0 + g1) / 2))).length() == 1) {
                    g = "0" + (String)g;
                }
                if (((String)(b = Integer.toHexString((b0 + b1) / 2))).length() == 1) {
                    b = "0" + (String)b;
                }
                total.setRGB(u, v, (int)Long.parseLong((String)a + (String)r + (String)g + (String)b, 16));
            }
        }
        return total;
    }
}

