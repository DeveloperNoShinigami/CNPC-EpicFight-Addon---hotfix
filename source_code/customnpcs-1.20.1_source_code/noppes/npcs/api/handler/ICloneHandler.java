/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.handler;

import noppes.npcs.api.IWorld;
import noppes.npcs.api.entity.IEntity;

public interface ICloneHandler {
    public IEntity spawn(double var1, double var3, double var5, int var7, String var8, IWorld var9);

    public IEntity get(int var1, String var2, IWorld var3);

    public void set(int var1, String var2, IEntity var3);

    public void remove(int var1, String var2);
}

