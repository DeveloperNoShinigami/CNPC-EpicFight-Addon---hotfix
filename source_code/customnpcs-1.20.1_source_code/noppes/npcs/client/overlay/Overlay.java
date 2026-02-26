/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 */
package noppes.npcs.client.overlay;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;
import net.minecraft.client.gui.GuiGraphics;
import noppes.npcs.api.overlay.ILabel;
import noppes.npcs.api.overlay.IOverlay;
import noppes.npcs.api.overlay.IOverlayComponent;
import noppes.npcs.api.overlay.IRenderItemOverlay;
import noppes.npcs.api.overlay.ITexturedRect;
import noppes.npcs.client.overlay.IOverlayRenderComponent;
import noppes.npcs.client.overlay.OverlayLabelComponent;
import noppes.npcs.client.overlay.OverlayRenderItemComponent;
import noppes.npcs.client.overlay.OverlayTexturedRectComponent;

public class Overlay {
    private final Queue<IOverlayRenderComponent> components = new ArrayDeque<IOverlayRenderComponent>();
    private final int linkSide;

    public Overlay(IOverlay overlay) {
        this.linkSide = overlay.getLinkSide();
        for (IOverlayComponent component : overlay.getComponents().stream().sorted(Comparator.comparingInt(IOverlayComponent::getId)).toList()) {
            if (component instanceof ILabel) {
                this.components.add(new OverlayLabelComponent((ILabel)component));
                continue;
            }
            if (component instanceof IRenderItemOverlay) {
                this.components.add(new OverlayRenderItemComponent((IRenderItemOverlay)component));
                continue;
            }
            if (!(component instanceof ITexturedRect)) continue;
            this.components.add(new OverlayTexturedRectComponent((ITexturedRect)component));
        }
    }

    public void render(GuiGraphics graphics) {
        graphics.m_280168_().m_85836_();
        for (IOverlayRenderComponent component : this.components) {
            component.render(graphics, this.linkSide);
        }
        graphics.m_280168_().m_85849_();
    }
}

