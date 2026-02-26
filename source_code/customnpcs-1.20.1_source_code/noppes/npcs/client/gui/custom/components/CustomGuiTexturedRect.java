/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.joml.Matrix4f
 */
package noppes.npcs.client.gui.custom.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import org.joml.Matrix4f;

public class CustomGuiTexturedRect
extends AbstractWidget
implements IGuiComponent {
    private CustomGuiTexturedRectWrapper component = null;
    GuiCustom parent;
    ResourceLocation texture;
    public int id;
    public int x;
    public int y;
    public int width;
    public int height;
    public int textureX;
    public int textureY;
    public int textureMaxX;
    public int textureMaxY;
    float scale = 1.0f;
    List<Component> hoverText;
    public boolean hasRepeatingTexture = false;
    public int texRepWidth;
    public int texRepHeight;
    public int texRepBorderSize = 0;

    public CustomGuiTexturedRect(GuiCustom parent, CustomGuiTexturedRectWrapper component) {
        super(component.getPosX(), component.getPosY(), component.getWidth(), component.getHeight(), (Component)Component.m_237119_());
        this.component = component;
        this.parent = parent;
        this.init();
    }

    public void init() {
        this.id = this.component.getID();
        this.texture = new ResourceLocation(this.component.getTexture());
        this.x = this.component.getPosX();
        this.y = this.component.getPosY();
        this.width = this.component.getWidth();
        this.height = this.component.getHeight();
        this.textureX = this.component.getTextureX();
        this.textureY = this.component.getTextureY();
        this.textureMaxX = this.component.getTextureMaxX();
        this.textureMaxY = this.component.getTextureMaxY();
        this.scale = this.component.getScale();
        this.hasRepeatingTexture = this.component.hasRepeatingTexture;
        this.texRepWidth = this.component.texRepWidth;
        this.texRepHeight = this.component.texRepHeight;
        this.texRepBorderSize = this.component.texRepBorderSize;
        if (this.component.hasHoverText()) {
            this.hoverText = this.component.getHoverTextList();
        }
    }

    public CustomGuiTexturedRect setRep(int texRepWidth, int texRepHeight, int texRepBorderSize) {
        this.texRepWidth = texRepWidth;
        this.texRepHeight = texRepHeight;
        this.texRepBorderSize = texRepBorderSize;
        this.hasRepeatingTexture = true;
        return this;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.component.getTexture().isEmpty() || !this.component.getVisible()) {
            return;
        }
        boolean hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        graphics.m_280168_().m_85836_();
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.texture);
        Matrix4f m = graphics.m_280168_().m_85850_().m_252922_();
        if (this.textureMaxX > 0 && this.textureMaxY > 0) {
            graphics.m_280163_(this.texture, this.x, this.y, (float)this.textureX, (float)this.textureY, this.width, this.height, this.textureMaxX, this.textureMaxY);
        } else if (!this.hasRepeatingTexture) {
            this.draw(m, this.x, this.y, this.textureX, this.textureY, this.width, this.height);
        } else {
            if (this.texRepBorderSize > 0) {
                this.draw(m, this.x, this.y, this.textureX, this.textureY, this.texRepBorderSize, this.texRepBorderSize);
                this.draw(m, this.x + this.width - this.texRepBorderSize, this.y, this.textureX + this.texRepWidth - this.texRepBorderSize, this.textureY, this.texRepBorderSize, this.texRepBorderSize);
                this.draw(m, this.x, this.y + this.height - this.texRepBorderSize, this.textureX, this.textureY + this.texRepHeight - this.texRepBorderSize, this.texRepBorderSize, this.texRepBorderSize);
                this.draw(m, this.x + this.width - this.texRepBorderSize, this.y + this.height - this.texRepBorderSize, this.textureX + this.texRepWidth - this.texRepBorderSize, this.textureY + this.texRepHeight - this.texRepBorderSize, this.texRepBorderSize, this.texRepBorderSize);
            }
            float w = (float)this.width - (float)this.texRepBorderSize * 2.0f;
            float h = (float)this.height - (float)this.texRepBorderSize * 2.0f;
            float tw = (float)this.texRepWidth - (float)this.texRepBorderSize * 2.0f;
            float th = (float)this.texRepHeight - (float)this.texRepBorderSize * 2.0f;
            float mx = w / tw;
            float my = h / th;
            int i = 0;
            while ((float)i < my) {
                float dh = th * Math.min(1.0f, my - (float)i);
                this.draw(m, this.x, (float)(this.y + this.texRepBorderSize) + th * (float)i, this.textureX, this.textureY + this.texRepBorderSize, this.texRepBorderSize, dh);
                this.draw(m, this.x + this.width - this.texRepBorderSize, (float)(this.y + this.texRepBorderSize) + th * (float)i, this.textureX + this.texRepWidth - this.texRepBorderSize, this.textureY + this.texRepBorderSize, this.texRepBorderSize, dh);
                int j = 0;
                while ((float)j < mx) {
                    float dw = tw * Math.min(1.0f, mx - (float)j);
                    this.draw(m, (float)(this.x + this.texRepBorderSize) + tw * (float)j, this.y, this.textureX + this.texRepBorderSize, this.textureY, dw, this.texRepBorderSize);
                    this.draw(m, (float)(this.x + this.texRepBorderSize) + tw * (float)j, this.y + this.height - this.texRepBorderSize, this.textureX + this.texRepBorderSize, this.textureY + this.texRepHeight - this.texRepBorderSize, dw, this.texRepBorderSize);
                    this.draw(m, (float)(this.x + this.texRepBorderSize) + tw * (float)j, (float)(this.y + this.texRepBorderSize) + th * (float)i, this.textureX + this.texRepBorderSize, this.textureY + this.texRepBorderSize, dw, dh);
                    ++j;
                }
                ++i;
            }
        }
        if (hovered && this.hoverText != null && this.hoverText.size() > 0) {
            this.parent.hoverText = this.hoverText;
        }
        graphics.m_280168_().m_85849_();
    }

    private void draw(Matrix4f m, float x, float y, float texX, float texY, float width, float height) {
        BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        float blitLevel = Math.max(0.0f, (float)this.id * 0.01f);
        bufferbuilder.m_252986_(m, x, y + height * this.scale, blitLevel).m_7421_(texX * 0.00390625f, (texY + height) * 0.00390625f).m_5752_();
        bufferbuilder.m_252986_(m, x + width * this.scale, y + height * this.scale, blitLevel).m_7421_((texX + width) * 0.00390625f, (texY + height) * 0.00390625f).m_5752_();
        bufferbuilder.m_252986_(m, x + width * this.scale, y, blitLevel).m_7421_((texX + width) * 0.00390625f, texY * 0.00390625f).m_5752_();
        bufferbuilder.m_252986_(m, x, y, blitLevel).m_7421_(texX * 0.00390625f, texY * 0.00390625f).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferbuilder.m_231175_());
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }

    public void m_7435_(SoundManager p_93665_) {
    }

    protected void m_87963_(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
    }
}

