/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api;

public interface IScoreboardTeam {
    public String getName();

    public String getDisplayName();

    public void setDisplayName(String var1);

    public void addPlayer(String var1);

    public boolean hasPlayer(String var1);

    public void removePlayer(String var1);

    public String[] getPlayers();

    public void clearPlayers();

    public boolean getFriendlyFire();

    public void setFriendlyFire(boolean var1);

    public void setColor(String var1);

    public String getColor();

    public void setSeeInvisibleTeamPlayers(boolean var1);

    public boolean getSeeInvisibleTeamPlayers();
}

