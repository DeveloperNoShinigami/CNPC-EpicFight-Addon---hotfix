/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.SimpleTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.server.packs.resources.ResourceManager
 */
package noppes.npcs.shared.client.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import noppes.npcs.shared.client.util.CTextureUtil;
import noppes.npcs.shared.common.util.LogWriter;

public class TextureCache
extends SimpleTexture {
    private final ResourceLocation original;

    public TextureCache(ResourceLocation location, ResourceLocation original) {
        super(location);
        this.original = original;
    }

    public void m_6704_(ResourceManager p_195413_1_) throws IOException {
        ResourceManager manager = Minecraft.m_91087_().m_91098_();
        Resource r = manager.m_213713_(this.original).orElse(null);
        if (r != null) {
            try {
                BufferedImage bufferedimage = ImageIO.read(r.m_215507_());
                int i = bufferedimage.getWidth();
                int j = bufferedimage.getHeight();
                BufferedImage bufferedImage = new BufferedImage(i * 4, j * 2, 1);
                Graphics g = bufferedImage.getGraphics();
                g.drawImage(bufferedimage, 0, 0, null);
                g.drawImage(bufferedimage, i, 0, null);
                g.drawImage(bufferedimage, i * 2, 0, null);
                g.drawImage(bufferedimage, i * 3, 0, null);
                g.drawImage(bufferedimage, 0, i, null);
                g.drawImage(bufferedimage, i, j, null);
                g.drawImage(bufferedimage, i * 2, j, null);
                g.drawImage(bufferedimage, i * 3, j, null);
                Minecraft.m_91087_().m_18691_(() -> CTextureUtil.uploadTextureImage(super.m_117963_(), bufferedImage));
            }
            catch (Exception e) {
                LogWriter.error("Failed caching texture: " + this.f_118129_, e);
            }
        }
    }
}

