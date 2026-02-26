/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.gui.ICustomGuiComponent;

public interface IEntityDisplay
extends ICustomGuiComponent {
    public IEntity getEntity();

    public IEntityDisplay setEntity(IEntity var1);

    public IEntityDisplay setEntitySyncedById(IEntity var1);

    public int getRotation();

    public IEntityDisplay setRotation(int var1);

    public float getScale();

    public IEntityDisplay setScale(float var1);

    public boolean getBackground();

    public IEntityDisplay setBackground(boolean var1);

    public boolean isFollowingCursor();

    public IEntityDisplay setFollowingCursor(boolean var1);
}

