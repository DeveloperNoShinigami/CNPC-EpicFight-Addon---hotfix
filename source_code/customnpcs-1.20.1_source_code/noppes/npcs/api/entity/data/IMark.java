/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data;

import noppes.npcs.api.handler.data.IAvailability;

public interface IMark {
    public IAvailability getAvailability();

    public int getColor();

    public void setColor(int var1);

    public int getType();

    public void setType(int var1);

    public void update();
}

