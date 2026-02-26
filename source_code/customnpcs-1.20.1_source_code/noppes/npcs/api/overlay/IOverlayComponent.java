/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.api.overlay;

import net.minecraft.nbt.CompoundTag;

public interface IOverlayComponent {
    public int getId();

    public int getPosX();

    public int getPosY();

    public IOverlayComponent setPos(int var1, int var2);

    public int getType();

    public void toNbt(CompoundTag var1);

    public void fromNbt(CompoundTag var1);

    public int getAlignment();

    public void setAlignment(int var1);
}

