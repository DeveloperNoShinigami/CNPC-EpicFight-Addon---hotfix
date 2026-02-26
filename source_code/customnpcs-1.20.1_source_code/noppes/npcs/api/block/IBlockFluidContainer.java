/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.block;

import noppes.npcs.api.block.IBlock;

public interface IBlockFluidContainer
extends IBlock {
    public float getFluidPercentage();

    public float getFuildDensity();

    public float getFuildTemperature();

    public String getFluidName();
}

