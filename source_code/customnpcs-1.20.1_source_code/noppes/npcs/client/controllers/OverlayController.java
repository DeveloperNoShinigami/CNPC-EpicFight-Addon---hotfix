/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  net.minecraft.client.gui.GuiGraphics
 */
package noppes.npcs.client.controllers;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.gui.GuiGraphics;
import noppes.npcs.api.overlay.IOverlay;
import noppes.npcs.client.overlay.Overlay;
import noppes.npcs.shared.client.gui.components.GuiBasic;

public class OverlayController
extends GuiBasic {
    private static final OverlayController instance = new OverlayController();
    private final Int2ObjectOpenHashMap<Overlay> overlays = new Int2ObjectOpenHashMap();

    public static OverlayController getInstance() {
        return instance;
    }

    public void addOverlay(IOverlay overlay) {
        this.overlays.put(overlay.getId(), (Object)new Overlay(overlay));
    }

    public void removeOverlay(int id) {
        this.overlays.remove(id);
    }

    public void clear() {
        this.overlays.clear();
    }

    public void renderOverlays(GuiGraphics graphics) {
        for (Overlay overlay : this.overlays.values()) {
            overlay.render(graphics);
        }
    }
}

