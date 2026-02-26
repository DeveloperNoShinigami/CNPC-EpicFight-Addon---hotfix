/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package noppes.npcs.api.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import noppes.npcs.api.IContainer;
import noppes.npcs.api.INbt;
import noppes.npcs.api.IPos;
import noppes.npcs.api.IWorld;
import noppes.npcs.api.entity.data.IData;

public interface IBlock {
    public int getX();

    public int getY();

    public int getZ();

    public IPos getPos();

    public Object getProperty(String var1);

    public void setProperty(String var1, Object var2);

    public String[] getProperties();

    public String getName();

    public void remove();

    public boolean isRemoved();

    public boolean isAir();

    public IBlock setBlock(String var1);

    public IBlock setBlock(IBlock var1);

    public boolean hasTileEntity();

    public boolean isContainer();

    public IContainer getContainer();

    public IData getTempdata();

    public IData getStoreddata();

    public IWorld getWorld();

    public INbt getBlockEntityNBT();

    public void setTileEntityNBT(INbt var1);

    public BlockEntity getMCTileEntity();

    public Block getMCBlock();

    public void blockEvent(int var1, int var2);

    public String getDisplayName();

    public BlockState getMCBlockState();

    public void interact(int var1);
}

