/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.block;

import noppes.npcs.api.ITimers;
import noppes.npcs.api.block.IBlock;
import noppes.npcs.api.block.ITextPlane;
import noppes.npcs.api.item.IItemStack;

public interface IBlockScripted
extends IBlock {
    public void setModel(IItemStack var1);

    public void setModel(String var1);

    public IItemStack getModel();

    public ITimers getTimers();

    public void setRedstonePower(int var1);

    public int getRedstonePower();

    public void setIsLadder(boolean var1);

    public boolean getIsLadder();

    public void setIsWaterlogged(boolean var1);

    public boolean getIsWaterlogged();

    public void setLight(int var1);

    public int getLight();

    public void setScale(float var1, float var2, float var3);

    public float getScaleX();

    public float getScaleY();

    public float getScaleZ();

    public void setRotation(int var1, int var2, int var3);

    public int getRotationX();

    public int getRotationY();

    public int getRotationZ();

    public String executeCommand(String var1);

    public boolean getIsPassible();

    public void setIsPassible(boolean var1);

    public float getHardness();

    public void setHardness(float var1);

    public float getResistance();

    public void setResistance(float var1);

    public ITextPlane getTextPlane();

    public ITextPlane getTextPlane2();

    public ITextPlane getTextPlane3();

    public ITextPlane getTextPlane4();

    public ITextPlane getTextPlane5();

    public ITextPlane getTextPlane6();

    public void trigger(int var1, Object ... var2);
}

