/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.api.wrapper;

import net.minecraft.nbt.CompoundTag;
import noppes.npcs.api.overlay.ITexturedRect;
import noppes.npcs.api.wrapper.OverlayComponentWrapper;

public class OverlayTexturedRectWrapper
extends OverlayComponentWrapper
implements ITexturedRect {
    private String texture;
    private int width;
    private int height;
    private float[] uv;
    private float[] rgb;
    int textureX;
    int textureY = -1;
    int textureMaxX;
    int textureMaxY = -1;

    public OverlayTexturedRectWrapper(int id, int x, int y, String texture, int width, int height) {
        super(id, x, y);
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public OverlayTexturedRectWrapper(int id, int x, int y, String texture, int width, int height, int textureX, int textureY) {
        super(id, x, y);
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.setTextureOffset(textureX, textureY);
    }

    public OverlayTexturedRectWrapper(int id, int x, int y, String texture, int width, int height, int textureX, int textureY, int textureMaxX, int textureMaxY) {
        super(id, x, y);
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.setTextureOffset(textureX, textureY);
        this.setTextureMaxSize(textureMaxX, textureMaxY);
    }

    @Override
    public int getTextureX() {
        return this.textureX;
    }

    @Override
    public int getTextureY() {
        return this.textureY;
    }

    @Override
    public int getTextureMaxX() {
        return this.textureMaxX;
    }

    @Override
    public int getTextureMaxY() {
        return this.textureMaxY;
    }

    @Override
    public ITexturedRect setTextureOffset(int offsetX, int offsetY) {
        this.textureX = offsetX;
        this.textureY = offsetY;
        return this;
    }

    @Override
    public ITexturedRect setTextureMaxSize(int textureMaxX, int textureMaxY) {
        this.textureMaxX = textureMaxX;
        this.textureMaxY = textureMaxY;
        return this;
    }

    @Override
    public String getTexture() {
        return this.texture;
    }

    @Override
    public ITexturedRect setTexture(String texture) {
        this.texture = texture;
        return this;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public ITexturedRect setWidth(int width) {
        this.width = width;
        return this;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public ITexturedRect setHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public ITexturedRect setUV(float x1, float y1, float x2, float y2) {
        this.uv = new float[]{x1, y1, x2, y2};
        return this;
    }

    @Override
    public ITexturedRect setRGB(float r, float g, float b, float a) {
        this.rgb = new float[]{r, g, b, a};
        return this;
    }

    @Override
    public float[] getRGB() {
        return this.rgb;
    }

    @Override
    public float[] getUV() {
        return this.uv;
    }

    @Override
    public void toNbt(CompoundTag compound) {
        int rgb;
        int a;
        int b;
        int g;
        int r;
        super.toNbt(compound);
        compound.m_128359_("texture", this.texture);
        compound.m_128405_("width", this.width);
        compound.m_128405_("height", this.height);
        if (this.uv != null) {
            r = (int)(this.uv[0] * 255.0f);
            g = (int)(this.uv[1] * 255.0f);
            b = (int)(this.uv[2] * 255.0f);
            a = (int)(this.uv[3] * 255.0f);
            rgb = (r << 24) + (g << 16) + (b << 8) + a;
            compound.m_128405_("u", rgb);
        }
        if (this.rgb != null) {
            r = (int)(this.rgb[0] * 255.0f);
            g = (int)(this.rgb[1] * 255.0f);
            b = (int)(this.rgb[2] * 255.0f);
            a = (int)(this.rgb[3] * 255.0f);
            rgb = (r << 24) + (g << 16) + (b << 8) + a;
            compound.m_128405_("c", rgb);
        }
        if (this.textureX >= 0 && this.textureY >= 0) {
            compound.m_128385_("texPos", new int[]{this.textureX, this.textureY});
        }
        if (this.textureMaxX >= 0 && this.textureMaxY >= 0) {
            compound.m_128385_("texPosMax", new int[]{this.textureMaxX, this.textureMaxY});
        }
    }

    @Override
    public void fromNbt(CompoundTag compound) {
        int uv;
        super.fromNbt(compound);
        this.texture = compound.m_128461_("texture");
        this.width = compound.m_128451_("width");
        this.height = compound.m_128451_("height");
        if (compound.m_128441_("c")) {
            uv = compound.m_128451_("c");
            this.setRGB((float)(uv >> 24 & 0xFF) / 255.0f, (float)(uv >> 16 & 0xFF) / 255.0f, (float)(uv >> 8 & 0xFF) / 255.0f, (float)(uv & 0xFF) / 255.0f);
        } else {
            this.setRGB(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (compound.m_128441_("u")) {
            uv = compound.m_128451_("u");
            this.setUV((float)(uv >> 24 & 0xFF) / 255.0f, (float)(uv >> 16 & 0xFF) / 255.0f, (float)(uv >> 8 & 0xFF) / 255.0f, (float)(uv & 0xFF) / 255.0f);
        } else {
            this.setUV(0.0f, 0.0f, 1.0f, 1.0f);
        }
        if (compound.m_128441_("texPos")) {
            this.setTextureOffset(compound.m_128465_("texPos")[0], compound.m_128465_("texPos")[1]);
        }
        if (compound.m_128441_("texPosMax")) {
            this.setTextureMaxSize(compound.m_128465_("texPosMax")[0], compound.m_128465_("texPosMax")[1]);
        }
    }
}

