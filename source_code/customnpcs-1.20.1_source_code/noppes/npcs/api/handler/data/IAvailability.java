/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.handler.data;

import noppes.npcs.api.entity.IPlayer;

public interface IAvailability {
    public boolean isAvailable(IPlayer var1);

    public int getDaytime();

    public void setDaytime(int var1);

    public int getMinPlayerLevel();

    public void setMinPlayerLevel(int var1);

    public int getDialog(int var1);

    public void setDialog(int var1, int var2, int var3);

    public void removeDialog(int var1);

    public int getQuest(int var1);

    public void setQuest(int var1, int var2, int var3);

    public void removeQuest(int var1);

    public void setFaction(int var1, int var2, int var3, int var4);

    public void removeFaction(int var1);

    public void setScoreboard(int var1, String var2, int var3, int var4);
}

