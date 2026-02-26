/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Mob
 */
package noppes.npcs.api.entity;

import net.minecraft.world.entity.Mob;
import noppes.npcs.api.IPos;
import noppes.npcs.api.entity.IEntityLiving;

public interface IMob<T extends Mob>
extends IEntityLiving<T> {
    public boolean isNavigating();

    public void clearNavigation();

    public void navigateTo(double var1, double var3, double var5, double var7);

    public void jump();

    @Override
    public T getMCEntity();

    public IPos getNavigationPath();
}

