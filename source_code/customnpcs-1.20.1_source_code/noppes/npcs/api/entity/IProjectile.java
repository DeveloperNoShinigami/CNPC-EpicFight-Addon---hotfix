/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 */
package noppes.npcs.api.entity;

import net.minecraft.world.entity.projectile.ThrowableProjectile;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.entity.IThrowable;
import noppes.npcs.api.item.IItemStack;

public interface IProjectile<T extends ThrowableProjectile>
extends IThrowable<T> {
    public IItemStack getItem();

    public void setItem(IItemStack var1);

    public boolean getHasGravity();

    public void setHasGravity(boolean var1);

    public int getAccuracy();

    public void setAccuracy(int var1);

    public void setHeading(IEntity var1);

    public void setHeading(double var1, double var3, double var5);

    public void setHeading(float var1, float var2);

    public void enableEvents();
}

