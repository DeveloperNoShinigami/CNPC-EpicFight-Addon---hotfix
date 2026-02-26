/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api;

import noppes.npcs.api.IPos;
import noppes.npcs.api.block.IBlock;

public interface IRayTrace {
    public IPos getPos();

    public IBlock getBlock();

    public int getSideHit();
}

