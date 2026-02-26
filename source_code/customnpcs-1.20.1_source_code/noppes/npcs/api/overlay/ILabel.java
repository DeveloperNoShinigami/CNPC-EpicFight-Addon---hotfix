/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.overlay;

import noppes.npcs.api.overlay.IOverlayComponent;

public interface ILabel
extends IOverlayComponent {
    public String getText();

    public ILabel setText(String var1);

    public ILabel setCentered(boolean var1);

    public boolean isCentered();

    public float getScale();

    public void setScale(float var1);
}

