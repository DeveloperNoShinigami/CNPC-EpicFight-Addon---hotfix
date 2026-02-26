/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.client.parts;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.client.parts.MpmPart;
import noppes.npcs.client.parts.MpmPartReader;
import noppes.npcs.shared.client.util.ImageDownloadAlt;
import noppes.npcs.shared.client.util.NoppesStringUtils;
import noppes.npcs.shared.client.util.ResourceDownloader;
import noppes.npcs.shared.common.util.NopVector3f;

public class MpmPartData {
    public static final NopVector3f WHITE = new NopVector3f(1.0f, 1.0f, 1.0f);
    public ResourceLocation partId;
    public boolean usePlayerSkin = false;
    public NopVector3f color = WHITE;
    public ResourceLocation texture = null;
    private ResourceLocation textureUrl = null;
    public String url = "";

    public MpmPart getPart() {
        return MpmPartReader.PARTS.get(this.partId);
    }

    public ResourceLocation getTexture() {
        if (this.getUrlTexture() != null) {
            return this.getUrlTexture();
        }
        if (this.texture != null) {
            return this.texture;
        }
        MpmPart part = this.getPart();
        if (part != null && part.texture != null) {
            return this.getPart().texture;
        }
        return MissingTextureAtlasSprite.m_118071_();
    }

    public ResourceLocation getUrlTexture() {
        if (this.textureUrl != null) {
            return this.textureUrl;
        }
        if (!this.url.isEmpty()) {
            ResourceLocation resource = ResourceDownloader.getUrlResourceLocation(this.url, false);
            File file = ResourceDownloader.getUrlFile(this.url, false);
            TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
            AbstractTexture object = texturemanager.m_174786_(resource, null);
            if (object == null) {
                this.textureUrl = this.getDefaultTexture();
                ResourceDownloader.load(new ImageDownloadAlt(file, this.url, resource, this.getDefaultTexture(), false, () -> {
                    this.textureUrl = resource;
                }));
            } else {
                this.textureUrl = resource;
            }
        }
        return this.textureUrl;
    }

    public void setTexture(String s) {
        this.texture = s == null || s.isEmpty() ? null : new ResourceLocation(s);
    }

    public void setUrl(String url) {
        if (NoppesStringUtils.areEqual(this.url, url)) {
            return;
        }
        this.url = url;
        this.textureUrl = null;
    }

    public ResourceLocation getDefaultTexture() {
        if (this.texture != null) {
            return this.texture;
        }
        return this.getPart().texture;
    }

    public int getColor() {
        int r = (int)(this.color.x * 255.0f) << 16;
        int g = (int)(this.color.y * 255.0f) << 8;
        int b = (int)(this.color.z * 255.0f);
        return r + g + b;
    }

    public void setColor(int color) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        this.color = new NopVector3f(r, g, b);
    }

    public CompoundTag getNbt() {
        CompoundTag item = new CompoundTag();
        item.m_128359_("Id", this.partId.toString());
        item.m_128379_("UsePlayerSkin", this.usePlayerSkin);
        item.m_128359_("Url", this.url);
        item.m_128359_("Texture", this.texture == null ? "" : this.texture.toString());
        item.m_128350_("ColorR", this.color.x);
        item.m_128350_("ColorG", this.color.y);
        item.m_128350_("ColorB", this.color.z);
        return item;
    }

    public void setNbt(CompoundTag compound) {
        this.partId = new ResourceLocation(compound.m_128461_("Id"));
        this.usePlayerSkin = compound.m_128471_("UsePlayerSkin");
        this.setUrl(compound.m_128461_("Url"));
        this.setTexture(compound.m_128461_("Texture"));
        this.color = new NopVector3f(compound.m_128457_("ColorR"), compound.m_128457_("ColorG"), compound.m_128457_("ColorB"));
    }
}

