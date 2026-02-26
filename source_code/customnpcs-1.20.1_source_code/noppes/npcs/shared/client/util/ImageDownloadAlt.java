/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  com.mojang.blaze3d.platform.TextureUtil
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.SimpleTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package noppes.npcs.shared.client.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.shared.SharedReferences;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(value=Dist.CLIENT)
public class ImageDownloadAlt
extends SimpleTexture {
    private static final Logger logger = LogManager.getLogger();
    public final File cacheFile;
    private final String imageUrl;
    private boolean fix64;
    private Runnable r;
    public final ResourceLocation location;
    public boolean uploaded = false;

    public ImageDownloadAlt(File file, String url, ResourceLocation location, ResourceLocation defaultLocation, boolean fix64, Runnable r) {
        super(defaultLocation);
        this.location = location;
        this.cacheFile = file;
        this.imageUrl = url;
        this.fix64 = fix64;
        this.r = r;
    }

    public void setImage(NativeImage image) {
        Minecraft.m_91087_().execute(() -> {
            this.uploaded = true;
            if (!RenderSystem.isOnRenderThread()) {
                RenderSystem.recordRenderCall(() -> this.upload(image));
            } else {
                this.upload(image);
            }
            this.r.run();
        });
    }

    private void upload(NativeImage imageIn) {
        TextureUtil.prepareImage((int)this.m_117963_(), (int)imageIn.m_84982_(), (int)imageIn.m_85084_());
        imageIn.m_85040_(0, 0, 0, true);
    }

    public void m_6704_(ResourceManager resourceManager) throws IOException {
        if (this.cacheFile != null && this.cacheFile.isFile()) {
            logger.debug("Loading http texture from local cache ({})", new Object[]{this.cacheFile});
            NativeImage image = null;
            try {
                image = NativeImage.m_85058_((InputStream)new FileInputStream(this.cacheFile));
                this.setImage(this.parseUserSkin(image));
                return;
            }
            catch (IOException ioexception) {
                super.m_6704_(resourceManager);
                logger.error("Couldn't load skin " + this.cacheFile, (Throwable)ioexception);
            }
        }
        if (!this.uploaded) {
            try {
                this.uploaded = true;
                super.m_6704_(resourceManager);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void loadTextureFromServer() {
        this.load(this.imageUrl, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void load(String url, boolean wasRedirect) {
        HttpURLConnection connection = null;
        logger.debug("Downloading http texture from {} to {}", new Object[]{url, this.cacheFile});
        try {
            connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
            connection.setRequestProperty("Content-Type", "image/png");
            connection.setRequestProperty("Accept", "image/png");
            connection.setRequestProperty("Expect", "100-continue");
            connection.connect();
            String type = connection.getContentType();
            long size = connection.getContentLengthLong();
            int statusCode = connection.getResponseCode();
            if (!(wasRedirect || statusCode != 302 && statusCode != 301 && statusCode != 303)) {
                String newUrl = connection.getHeaderField("Location");
                if (newUrl != null && !newUrl.trim().isEmpty()) {
                    this.load(newUrl, true);
                }
                return;
            }
            if (statusCode / 100 != 2 || !type.equals("image/png") || size > 2000000L && !Minecraft.m_91087_().m_91091_()) {
                return;
            }
            FileUtils.copyInputStreamToFile((InputStream)connection.getInputStream(), (File)this.cacheFile);
        }
        catch (Exception exception) {
            logger.error("Couldn't download http texture", (Throwable)exception);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public NativeImage parseUserSkin(NativeImage image) {
        boolean lvt_2_1_;
        if (image.m_85084_() != image.m_84982_() && image.m_84982_() / 2 != image.m_85084_()) {
            throw new IllegalArgumentException("Invalid texture size: " + image.m_84982_() + "x" + image.m_85084_());
        }
        int scale = image.m_84982_() / 64;
        boolean bl = lvt_2_1_ = image.m_85084_() != image.m_84982_();
        if (lvt_2_1_ && this.fix64) {
            NativeImage nativeImage = new NativeImage(64 * scale, 64 * scale, true);
            nativeImage.m_85054_(image);
            image.close();
            image = nativeImage;
            nativeImage.m_84997_(0, 32 * scale, 64 * scale, 32 * scale, 0);
            nativeImage.m_85025_(4 * scale, 16 * scale, 16 * scale, 32 * scale, 4 * scale, 4 * scale, true, false);
            nativeImage.m_85025_(8 * scale, 16 * scale, 16 * scale, 32 * scale, 4 * scale, 4 * scale, true, false);
            nativeImage.m_85025_(0, 20 * scale, 24 * scale, 32 * scale, 4 * scale, 12 * scale, true, false);
            nativeImage.m_85025_(4 * scale, 20 * scale, 16 * scale, 32 * scale, 4 * scale, 12 * scale, true, false);
            nativeImage.m_85025_(8 * scale, 20 * scale, 8 * scale, 32 * scale, 4 * scale, 12 * scale, true, false);
            nativeImage.m_85025_(12 * scale, 20 * scale, 16 * scale, 32 * scale, 4 * scale, 12 * scale, true, false);
            nativeImage.m_85025_(44 * scale, 16 * scale, -8 * scale, 32 * scale, 4 * scale, 4 * scale, true, false);
            nativeImage.m_85025_(48 * scale, 16 * scale, -8 * scale, 32 * scale, 4 * scale, 4 * scale, true, false);
            nativeImage.m_85025_(40 * scale, 20 * scale, 0, 32 * scale, 4 * scale, 12 * scale, true, false);
            nativeImage.m_85025_(44 * scale, 20 * scale, -8 * scale, 32 * scale, 4 * scale, 12 * scale, true, false);
            nativeImage.m_85025_(48 * scale, 20 * scale, -16 * scale, 32 * scale, 4 * scale, 12 * scale, true, false);
            nativeImage.m_85025_(52 * scale, 20 * scale, -8 * scale, 32 * scale, 4 * scale, 12 * scale, true, false);
        }
        if (!SharedReferences.AllowFullyInvisibleSkins()) {
            ImageDownloadAlt.setAreaOpaque(image, 0, 0, 32 * scale, 16 * scale);
        }
        if (lvt_2_1_ && this.fix64) {
            ImageDownloadAlt.setAreaTransparent(image, 32 * scale, 0, 64 * scale, 32 * scale);
        }
        return image;
    }

    private static void setAreaTransparent(NativeImage image, int x, int y, int width, int height) {
        for (int i = x; i < width; ++i) {
            for (int j = y; j < height; ++j) {
                int k = image.m_84985_(i, j);
                if ((k >> 24 & 0xFF) >= 128) continue;
                return;
            }
        }
        for (int l = x; l < width; ++l) {
            for (int i1 = y; i1 < height; ++i1) {
                image.m_84988_(l, i1, image.m_84985_(l, i1) & 0xFFFFFF);
            }
        }
    }

    private static void setAreaOpaque(NativeImage image, int x, int y, int width, int height) {
        for (int i = x; i < width; ++i) {
            for (int j = y; j < height; ++j) {
                image.m_84988_(i, j, image.m_84985_(i, j) | 0xFF000000);
            }
        }
    }
}

