/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.client.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.overlay.IRenderItemOverlay;
import noppes.npcs.client.overlay.IOverlayRenderComponent;

public class OverlayRenderItemComponent
implements IOverlayRenderComponent {
    private final int x;
    private final int y;
    private final int id;
    private final ItemStack item;
    private final int alignment;

    public OverlayRenderItemComponent(IRenderItemOverlay item) {
        this.x = item.getPosX();
        this.y = item.getPosY();
        this.id = item.getId();
        this.item = item.getItem().getMCItemStack();
        this.alignment = item.getAlignment();
    }

    @Override
    public void render(GuiGraphics graphics, int linkSide) {
        graphics.m_280168_().m_85836_();
        graphics.m_280168_().m_85837_((double)this.x / (double)1.2f, (double)this.y / (double)1.2f, (double)this.id / (double)1.2f);
        graphics.m_280168_().m_85841_(1.2f, 1.2f, 1.0f);
        int width = (int)((float)Minecraft.m_91087_().m_91268_().m_85445_() / 1.2f);
        int height = (int)((float)Minecraft.m_91087_().m_91268_().m_85446_() / 1.2f);
        this.renderItemOverlay(graphics, linkSide, this.item, this.x, this.y, width, height);
        graphics.m_280168_().m_85849_();
    }

    public void renderItemOverlay(GuiGraphics graphics, int linkSide, ItemStack item, int x, int y, int width, int height) {
        if (this.alignment != -1) {
            linkSide = this.alignment + 1;
        }
        int offsetX = width / 2 * ((linkSide - 1) % 3);
        int offsetY = height / 2 * ((linkSide - 1) / 3);
        graphics.m_280480_(item, x + offsetX, y + offsetY);
        graphics.m_280370_(Minecraft.m_91087_().f_91062_, item, x + offsetX, y + offsetY);
    }
}

