/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api;

public interface ITimers {
    public void start(int var1, int var2, boolean var3);

    public void forceStart(int var1, int var2, boolean var3);

    public boolean has(int var1);

    public boolean stop(int var1);

    public void reset(int var1);

    public void clear();
}

