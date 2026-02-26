/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data.role;

import noppes.npcs.api.entity.IEntityLiving;

public interface IJobSpawner {
    public IEntityLiving spawnEntity(int var1);

    public void removeAllSpawned();
}

