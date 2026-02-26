/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.overlay;

import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.overlay.IOverlayComponent;

public interface IRenderItemOverlay
extends IOverlayComponent {
    public IItemStack getItem();

    public IRenderItemOverlay setItem(IItemStack var1);
}

