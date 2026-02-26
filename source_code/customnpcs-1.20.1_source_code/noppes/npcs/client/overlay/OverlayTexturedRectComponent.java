/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.client.overlay;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.api.overlay.ITexturedRect;
import noppes.npcs.client.overlay.IOverlayRenderComponent;

public class OverlayTexturedRectComponent
implements IOverlayRenderComponent {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final String texture;
    private final int textureX;
    private final int textureY;
    private final int textureMaxX;
    private final int textureMaxY;
    private final int id;
    private final int alignment;

    public OverlayTexturedRectComponent(ITexturedRect component) {
        this.x = component.getPosX();
        this.y = component.getPosY();
        this.id = component.getId();
        this.width = component.getWidth();
        this.height = component.getHeight();
        this.texture = component.getTexture();
        this.textureX = component.getTextureX();
        this.textureY = component.getTextureY();
        this.textureMaxX = component.getTextureMaxX();
        this.textureMaxY = component.getTextureMaxY();
        this.alignment = component.getAlignment();
    }

    @Override
    public void render(GuiGraphics graphics, int linkSide) {
        graphics.m_280168_().m_85836_();
        graphics.m_280168_().m_85837_((double)(2 * this.x), (double)(2 * this.y), (double)this.id);
        if (this.alignment != -1) {
            linkSide = this.alignment + 1;
        }
        int width = Minecraft.m_91087_().m_91268_().m_85445_();
        int height = Minecraft.m_91087_().m_91268_().m_85446_();
        int i = width / 2;
        if (Objects.equals(this.texture, "")) {
            this.renderGradientRect(graphics, this.x, this.y, linkSide, width, height, this.width, this.height, i, -1072689136, -804253680);
        } else {
            ResourceLocation resLoc = new ResourceLocation(this.texture);
            if (this.textureX >= 0 && this.textureY >= 0) {
                if (this.textureMaxX >= 0 && this.textureMaxY >= 0) {
                    this.renderRectTextureCustomSize(graphics, resLoc, this.x, this.y, linkSide, width, height, this.width, this.height, this.textureX, this.textureY, this.textureMaxX, this.textureMaxY);
                } else {
                    this.renderRectTextureSize(graphics, resLoc, this.x, this.y, linkSide, width, height, this.width, this.height, this.textureX, this.textureY);
                }
            } else {
                this.renderRectTexture(graphics, resLoc, this.x, this.y, linkSide, width, height, this.width, this.height);
            }
        }
        graphics.m_280168_().m_85849_();
    }

    public void renderGradientRect(GuiGraphics graphics, int x, int y, int linkSide, int widthScaled, int heightScaled, int width, int height, int i, int startColor, int endColor) {
        int offsetX = widthScaled / 2 * ((linkSide - 1) % 3);
        int offsetY = heightScaled / 2 * ((linkSide - 1) / 3);
        graphics.m_280024_(offsetX, offsetY, offsetX + width, offsetY + height, startColor, endColor);
    }

    public void renderRectTexture(GuiGraphics graphics, ResourceLocation resLoc, int x, int y, int linkSide, int widthScaled, int heightScaled, int width, int height) {
        this.renderRectTextureCustomSize(graphics, resLoc, 0, 0, linkSide, widthScaled, heightScaled, width, height, 0, 0, 256, 256);
    }

    public void renderRectTextureSize(GuiGraphics graphics, ResourceLocation resLoc, int x, int y, int linkSide, int widthScaled, int heightScaled, int width, int height, int textureX, int textureY) {
        this.renderRectTextureCustomSize(graphics, resLoc, 0, 0, linkSide, widthScaled, heightScaled, width, height, textureX, textureY, 256, 256);
    }

    public void renderRectTextureCustomSize(GuiGraphics graphics, ResourceLocation resLoc, int x, int y, int linkSide, int widthScaled, int heightScaled, int width, int height, int textureX, int textureY, int textureMaxX, int textureMaxY) {
        int offsetX = widthScaled / 2 * ((linkSide - 1) % 3);
        int offsetY = heightScaled / 2 * ((linkSide - 1) / 3);
        graphics.m_280163_(resLoc, offsetX, offsetY, (float)textureX, (float)textureY, width, height, textureMaxX, textureMaxY);
    }
}

