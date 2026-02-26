/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api;

import noppes.npcs.api.IScoreboardObjective;
import noppes.npcs.api.IScoreboardTeam;

public interface IScoreboard {
    public IScoreboardObjective[] getObjectives();

    public IScoreboardObjective getObjective(String var1);

    public boolean hasObjective(String var1);

    public void removeObjective(String var1);

    public IScoreboardObjective addObjective(String var1, String var2);

    public void setPlayerScore(String var1, String var2, int var3);

    public int getPlayerScore(String var1, String var2);

    public boolean hasPlayerObjective(String var1, String var2);

    public void deletePlayerScore(String var1, String var2);

    public IScoreboardTeam[] getTeams();

    public boolean hasTeam(String var1);

    public IScoreboardTeam addTeam(String var1);

    public IScoreboardTeam getTeam(String var1);

    public void removeTeam(String var1);

    public IScoreboardTeam getPlayerTeam(String var1);

    public void removePlayerTeam(String var1);

    public String[] getPlayerList();
}

