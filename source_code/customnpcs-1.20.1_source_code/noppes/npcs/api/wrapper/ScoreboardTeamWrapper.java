/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.scores.PlayerTeam
 *  net.minecraft.world.scores.Scoreboard
 */
package noppes.npcs.api.wrapper;

import java.util.ArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.IScoreboardTeam;

public class ScoreboardTeamWrapper
implements IScoreboardTeam {
    private PlayerTeam team;
    private Scoreboard board;

    protected ScoreboardTeamWrapper(PlayerTeam team, Scoreboard board) {
        this.team = team;
        this.board = board;
    }

    @Override
    public String getName() {
        return this.team.m_5758_();
    }

    @Override
    public String getDisplayName() {
        return this.team.m_83364_().getString();
    }

    @Override
    public void setDisplayName(String name) {
        if (name.length() <= 0 || name.length() > 32) {
            throw new CustomNPCsException("Score team display name must be between 1-32 characters: %s", name);
        }
        this.team.m_83353_((Component)Component.m_237115_((String)name));
    }

    @Override
    public void addPlayer(String player) {
        this.board.m_6546_(player, this.team);
    }

    @Override
    public void removePlayer(String player) {
        this.board.m_6519_(player, this.team);
    }

    @Override
    public String[] getPlayers() {
        ArrayList list = new ArrayList(this.team.m_6809_());
        return list.toArray(new String[list.size()]);
    }

    @Override
    public void clearPlayers() {
        ArrayList list = new ArrayList(this.team.m_6809_());
        for (String player : list) {
            this.board.m_6519_(player, this.team);
        }
    }

    @Override
    public boolean getFriendlyFire() {
        return this.team.m_6260_();
    }

    @Override
    public void setFriendlyFire(boolean bo) {
        this.team.m_83355_(bo);
    }

    @Override
    public void setColor(String color) {
        ChatFormatting enumchatformatting = ChatFormatting.m_126657_((String)color);
        if (enumchatformatting == null || enumchatformatting.m_126661_()) {
            throw new CustomNPCsException("Not a proper color name: %s", color);
        }
        this.team.m_83360_((Component)Component.m_237113_((String)enumchatformatting.toString()));
        this.team.m_83365_((Component)Component.m_237113_((String)ChatFormatting.RESET.toString()));
    }

    @Override
    public String getColor() {
        Component prefix = this.team.m_83370_();
        if (prefix == null || prefix.getString().isEmpty()) {
            return null;
        }
        for (ChatFormatting format : ChatFormatting.values()) {
            if (!prefix.getString().equals(format.toString()) || format == ChatFormatting.RESET) continue;
            return format.m_126666_();
        }
        return null;
    }

    @Override
    public void setSeeInvisibleTeamPlayers(boolean bo) {
        this.team.m_83362_(bo);
    }

    @Override
    public boolean getSeeInvisibleTeamPlayers() {
        return this.team.m_6259_();
    }

    @Override
    public boolean hasPlayer(String player) {
        return this.board.m_83500_(player) != null;
    }
}

