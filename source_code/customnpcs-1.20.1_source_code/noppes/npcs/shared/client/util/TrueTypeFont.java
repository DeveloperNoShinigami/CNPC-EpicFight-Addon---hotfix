/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  org.joml.Matrix4f
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package noppes.npcs.shared.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import noppes.npcs.shared.common.util.LRUHashMap;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class TrueTypeFont {
    private static final int MaxWidth = 512;
    private static final List<Font> allFonts = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
    private List<Font> usedFonts = new ArrayList<Font>();
    private LinkedHashMap<String, GlyphCache> textcache = new LRUHashMap<String, GlyphCache>(100);
    private Map<Character, Glyph> glyphcache = new HashMap<Character, Glyph>();
    private List<TextureCache> textures = new ArrayList<TextureCache>();
    private Font font;
    private int lineHeight = 1;
    private Graphics2D globalG = (Graphics2D)new BufferedImage(1, 1, 2).getGraphics();
    public float scale = 1.0f;
    private int specialChar = 167;

    public TrueTypeFont(Font font, float scale) {
        this.font = font;
        this.scale = scale;
        this.globalG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.lineHeight = this.globalG.getFontMetrics(font).getHeight();
    }

    public TrueTypeFont(ResourceLocation resource, int fontSize, float scale) throws IOException, FontFormatException {
        Resource r = Minecraft.m_91087_().m_91098_().m_213713_(resource).orElse(null);
        if (r != null) {
            InputStream stream = r.m_215507_();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font font = Font.createFont(0, stream);
            ge.registerFont(font);
            this.font = font.deriveFont(0, fontSize);
            this.scale = scale;
            this.globalG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            this.lineHeight = this.globalG.getFontMetrics(font).getHeight();
        }
    }

    public void setSpecial(char c) {
        this.specialChar = c;
    }

    public void draw(PoseStack posestack, String text, float x, float y, int color) {
        GlyphCache cache = this.getOrCreateCache(text);
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        RenderSystem.enableBlend();
        posestack.m_85836_();
        posestack.m_252880_(x, y, 0.0f);
        posestack.m_85841_(this.scale, this.scale, 1.0f);
        int rr = r;
        int gg = g;
        int bb = b;
        float i = 0.0f;
        for (Glyph gl : cache.glyphs) {
            if (gl.type != GlyphType.NORMAL) {
                if (gl.type == GlyphType.RESET) {
                    rr = r;
                    gg = g;
                    bb = b;
                    continue;
                }
                if (gl.type != GlyphType.COLOR) continue;
                rr = gl.color >> 16 & 0xFF;
                gg = gl.color >> 8 & 0xFF;
                bb = gl.color & 0xFF;
                continue;
            }
            RenderSystem.setShaderTexture((int)0, (int)gl.texture);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.fillGradient(posestack.m_85850_().m_252922_(), i, 0.0f, (float)gl.x * this.textureScale(), (float)gl.y * this.textureScale(), (float)gl.width * this.textureScale(), (float)gl.height * this.textureScale(), rr, gg, bb);
            i += (float)gl.width * this.textureScale();
        }
        RenderSystem.disableBlend();
        posestack.m_85849_();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public void fillGradient(Matrix4f m, float x, float y, float textureX, float textureY, float width, float height, int r, int g, int b) {
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        boolean zLevel = false;
        RenderSystem.setShader(GameRenderer::m_172820_);
        BufferBuilder tessellator = Tesselator.m_85913_().m_85915_();
        tessellator.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        tessellator.m_252986_(m, x, y + height, (float)zLevel).m_7421_(textureX * f, (textureY + height) * f1).m_6122_(r, g, b, 255).m_5752_();
        tessellator.m_252986_(m, x + width, y + height, (float)zLevel).m_7421_((textureX + width) * f, (textureY + height) * f1).m_6122_(r, g, b, 255).m_5752_();
        tessellator.m_252986_(m, x + width, y, (float)zLevel).m_7421_((textureX + width) * f, textureY * f1).m_6122_(r, g, b, 255).m_5752_();
        tessellator.m_252986_(m, x, y, (float)zLevel).m_7421_(textureX * f, textureY * f1).m_6122_(r, g, b, 255).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)tessellator.m_231175_());
    }

    private GlyphCache getOrCreateCache(String text) {
        GlyphCache cache = this.textcache.get(text);
        if (cache != null) {
            return cache;
        }
        cache = new GlyphCache();
        for (int i = 0; i < text.length(); ++i) {
            char next;
            int index;
            char c = text.charAt(i);
            if (c == this.specialChar && i + 1 < text.length() && (index = "0123456789abcdefklmnor".indexOf(next = text.toLowerCase(Locale.ENGLISH).charAt(i + 1))) >= 0) {
                Glyph g = new Glyph();
                if (index < 16) {
                    g.type = GlyphType.COLOR;
                    g.color = ChatFormatting.m_126645_((char)next).m_126665_();
                } else {
                    g.type = index == 16 ? GlyphType.RANDOM : (index == 17 ? GlyphType.BOLD : (index == 18 ? GlyphType.STRIKETHROUGH : (index == 19 ? GlyphType.UNDERLINE : (index == 20 ? GlyphType.ITALIC : GlyphType.RESET))));
                }
                cache.glyphs.add(g);
                ++i;
                continue;
            }
            Glyph g = this.getOrCreateGlyph(c);
            cache.glyphs.add(g);
            cache.width += g.width;
            cache.height = Math.max(cache.height, g.height);
        }
        this.textcache.put(text, cache);
        return cache;
    }

    private Glyph getOrCreateGlyph(char c) {
        Glyph g = this.glyphcache.get(Character.valueOf(c));
        if (g != null) {
            return g;
        }
        TextureCache cache = this.getCurrentTexture();
        Font font = this.getFontForChar(c);
        FontMetrics metrics = this.globalG.getFontMetrics(font);
        g = new Glyph();
        g.width = Math.max(metrics.charWidth(c), 1);
        g.height = Math.max(metrics.getHeight(), 1);
        if (cache.x + g.width >= 512) {
            cache.x = 0;
            cache.y += this.lineHeight + 1;
            if (cache.y >= 512) {
                cache.full = true;
                cache = this.getCurrentTexture();
            }
        }
        g.x = cache.x;
        g.y = cache.y;
        cache.x += g.width + 3;
        this.lineHeight = Math.max(this.lineHeight, g.height);
        cache.g.setFont(font);
        cache.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        cache.g.drawString("" + c, g.x, g.y + metrics.getAscent());
        g.texture = cache.textureId;
        int[] aint = new int[262144];
        cache.bufferedImage.getRGB(0, 0, 512, 512, aint, 0, 512);
        IntBuffer intbuffer = BufferUtils.createIntBuffer((int)262144);
        intbuffer.put(aint);
        intbuffer.flip();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.bindTextureForSetup((int)cache.textureId);
        GL11.glPixelStorei((int)3312, (int)0);
        GL11.glPixelStorei((int)3313, (int)0);
        GL11.glPixelStorei((int)3314, (int)0);
        GL11.glPixelStorei((int)3315, (int)0);
        GL11.glPixelStorei((int)3316, (int)0);
        GL11.glPixelStorei((int)3317, (int)4);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)512, (int)512, (int)0, (int)32993, (int)33639, (IntBuffer)intbuffer);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        this.glyphcache.put(Character.valueOf(c), g);
        return g;
    }

    private TextureCache getCurrentTexture() {
        TextureCache cache = null;
        for (TextureCache t : this.textures) {
            if (t.full) continue;
            cache = t;
            break;
        }
        if (cache == null) {
            cache = new TextureCache();
            this.textures.add(cache);
        }
        return cache;
    }

    private Font getFontForChar(char c) {
        if (this.font.canDisplay(c)) {
            return this.font;
        }
        for (Font f : this.usedFonts) {
            if (!f.canDisplay(c)) continue;
            return f;
        }
        Font fa = new Font("Arial Unicode MS", 0, this.font.getSize());
        if (fa.canDisplay(c)) {
            return fa;
        }
        for (Font f : allFonts) {
            if (!f.canDisplay(c)) continue;
            f = f.deriveFont(0, this.font.getSize());
            this.usedFonts.add(f);
            return f;
        }
        return this.font;
    }

    public int width(String text) {
        GlyphCache cache = this.getOrCreateCache(text);
        return (int)((float)cache.width * this.scale * this.textureScale());
    }

    public int height(String text) {
        if (text == null || text.trim().isEmpty()) {
            return (int)((float)this.lineHeight * this.scale * this.textureScale());
        }
        GlyphCache cache = this.getOrCreateCache(text);
        return Math.max(1, (int)((float)cache.height * this.scale * this.textureScale()));
    }

    private float textureScale() {
        return 0.5f;
    }

    public void dispose() {
        for (TextureCache cache : this.textures) {
            RenderSystem.deleteTexture((int)cache.textureId);
        }
        this.textcache.clear();
    }

    public String getFontName() {
        return this.font.getFontName();
    }

    class GlyphCache {
        public int width;
        public int height;
        List<Glyph> glyphs = new ArrayList<Glyph>();

        GlyphCache() {
        }
    }

    class Glyph {
        GlyphType type = GlyphType.NORMAL;
        int color = -1;
        int x;
        int y;
        int height;
        int width;
        int texture;

        Glyph() {
        }
    }

    static enum GlyphType {
        NORMAL,
        COLOR,
        RANDOM,
        BOLD,
        STRIKETHROUGH,
        UNDERLINE,
        ITALIC,
        RESET,
        OTHER;

    }

    class TextureCache {
        int x;
        int y;
        int textureId = GL11.glGenTextures();
        BufferedImage bufferedImage = new BufferedImage(512, 512, 2);
        Graphics2D g = (Graphics2D)this.bufferedImage.getGraphics();
        boolean full;

        TextureCache() {
        }
    }
}

