/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs;

import net.minecraft.nbt.CompoundTag;

public interface ICompatibilty {
    public int getVersion();

    public void setVersion(int var1);

    public CompoundTag save(CompoundTag var1);
}

