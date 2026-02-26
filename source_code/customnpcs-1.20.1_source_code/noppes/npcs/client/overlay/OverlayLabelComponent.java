/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.resources.language.I18n
 */
package noppes.npcs.client.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import noppes.npcs.api.overlay.ILabel;
import noppes.npcs.client.overlay.IOverlayRenderComponent;

public class OverlayLabelComponent
implements IOverlayRenderComponent {
    private final String text;
    private final int x;
    private final int y;
    private final int id;
    private final float scale;
    private final int alignment;

    public OverlayLabelComponent(ILabel label) {
        String[] split;
        String text = label.getText();
        this.x = label.getPosX();
        this.y = label.getPosY();
        this.id = label.getId();
        this.scale = label.getScale();
        this.alignment = label.getAlignment();
        StringBuilder stringBuilder = new StringBuilder();
        String[] values = split = text.split("&t");
        for (String s : split) {
            if (I18n.m_118936_((String)s)) {
                stringBuilder.append(I18n.m_118938_((String)s, (Object[])new Object[0]));
                continue;
            }
            stringBuilder.append(s);
        }
        this.text = stringBuilder.toString();
    }

    @Override
    public void render(GuiGraphics graphics, int linkSide) {
        graphics.m_280168_().m_85836_();
        graphics.m_280168_().m_85837_((double)this.x, (double)this.y, (double)this.id);
        graphics.m_280168_().m_85841_(this.scale, this.scale, this.scale);
        int width = Minecraft.m_91087_().m_91268_().m_85445_();
        int height = Minecraft.m_91087_().m_91268_().m_85446_();
        this.renderString(graphics, this.text, this.x, this.y, linkSide, width, height);
        graphics.m_280168_().m_85849_();
    }

    private void renderString(GuiGraphics graphics, String text, int x, int y, int linkSide, int width, int height) {
        if (this.alignment != -1) {
            linkSide = this.alignment + 1;
        }
        int offsetX = width / 2 * ((linkSide - 1) % 3);
        int offsetY = height / 2 * ((linkSide - 1) / 3);
        graphics.m_280488_(Minecraft.m_91087_().f_91062_, text, x + offsetX, y + offsetY, 0xFFFFFF);
    }
}

