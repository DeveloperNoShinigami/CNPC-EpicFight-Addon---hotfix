/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.state.BlockState
 */
package noppes.npcs.schematics;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public interface ISchematic {
    public short getWidth();

    public short getHeight();

    public short getLength();

    public int getBlockEntityDimensions();

    public CompoundTag getBlockEntity(int var1);

    public String getName();

    public BlockState getBlockState(int var1, int var2, int var3);

    public BlockState getBlockState(int var1);

    public CompoundTag getNBT();
}

