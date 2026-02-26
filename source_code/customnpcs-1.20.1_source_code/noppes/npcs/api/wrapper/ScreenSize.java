/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.wrapper;

import noppes.npcs.api.IScreenSize;

public class ScreenSize
implements IScreenSize {
    private int width;
    private int height;

    public ScreenSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getWidthPercent(double percent) {
        return (int)((double)this.width * percent / 100.0);
    }

    @Override
    public int getHeightPercent(double percent) {
        return (int)((double)this.height * percent / 100.0);
    }
}

