/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.block;

import noppes.npcs.api.ITimers;
import noppes.npcs.api.block.IBlock;

public interface IBlockScriptedDoor
extends IBlock {
    public ITimers getTimers();

    public boolean getOpen();

    public void setOpen(boolean var1);

    public void setBlockModel(String var1);

    public String getBlockModel();

    public float getHardness();

    public void setHardness(float var1);

    public float getResistance();

    public void setResistance(float var1);

    public String executeCommand(String var1);
}

