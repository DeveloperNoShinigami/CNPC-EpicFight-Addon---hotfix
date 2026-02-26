/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api;

import noppes.npcs.api.IScoreboardScore;

public interface IScoreboardObjective {
    public String getName();

    public String getDisplayName();

    public void setDisplayName(String var1);

    public String getCriteria();

    public boolean isReadyOnly();

    public IScoreboardScore[] getScores();

    public IScoreboardScore getScore(String var1);

    public boolean hasScore(String var1);

    public IScoreboardScore createScore(String var1);

    public void removeScore(String var1);
}

