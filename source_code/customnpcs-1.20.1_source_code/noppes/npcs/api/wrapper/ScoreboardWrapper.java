/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.scores.Objective
 *  net.minecraft.world.scores.PlayerTeam
 *  net.minecraft.world.scores.Score
 *  net.minecraft.world.scores.Scoreboard
 *  net.minecraft.world.scores.criteria.ObjectiveCriteria
 *  net.minecraft.world.scores.criteria.ObjectiveCriteria$RenderType
 */
package noppes.npcs.api.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.IScoreboard;
import noppes.npcs.api.IScoreboardObjective;
import noppes.npcs.api.IScoreboardTeam;
import noppes.npcs.api.wrapper.ScoreboardObjectiveWrapper;
import noppes.npcs.api.wrapper.ScoreboardTeamWrapper;

public class ScoreboardWrapper
implements IScoreboard {
    private Scoreboard board;
    private MinecraftServer server;

    protected ScoreboardWrapper(MinecraftServer server) {
        this.server = server;
        this.board = server.m_129880_(Level.f_46428_).m_6188_();
    }

    @Override
    public IScoreboardObjective[] getObjectives() {
        ArrayList collection = new ArrayList(this.board.m_83466_());
        IScoreboardObjective[] objectives = new IScoreboardObjective[collection.size()];
        for (int i = 0; i < collection.size(); ++i) {
            objectives[i] = new ScoreboardObjectiveWrapper(this.board, (Objective)collection.get(i));
        }
        return objectives;
    }

    @Override
    public String[] getPlayerList() {
        Collection collection = this.board.m_83474_();
        return collection.toArray(new String[collection.size()]);
    }

    @Override
    public IScoreboardObjective getObjective(String name) {
        Objective obj = this.board.m_83477_(name);
        if (obj == null) {
            return null;
        }
        return new ScoreboardObjectiveWrapper(this.board, obj);
    }

    @Override
    public boolean hasObjective(String objective) {
        return this.board.m_83477_(objective) != null;
    }

    @Override
    public void removeObjective(String objective) {
        Objective obj = this.board.m_83477_(objective);
        if (obj != null) {
            this.board.m_83502_(obj);
        }
    }

    @Override
    public IScoreboardObjective addObjective(String objective, String criteria) {
        ObjectiveCriteria icriteria = ObjectiveCriteria.m_83614_((String)criteria).orElse(null);
        if (icriteria == null) {
            throw new CustomNPCsException("Unknown score criteria: %s", criteria);
        }
        if (objective.length() <= 0 || objective.length() > 16) {
            throw new CustomNPCsException("Score objective must be between 1-16 characters: %s", objective);
        }
        Objective obj = this.board.m_83436_(objective, icriteria, (Component)Component.m_237115_((String)objective), ObjectiveCriteria.RenderType.INTEGER);
        return new ScoreboardObjectiveWrapper(this.board, obj);
    }

    @Override
    public void setPlayerScore(String player, String objective, int score) {
        Objective objec = this.getObjectiveWithException(objective);
        if (objec.m_83321_().m_83621_() || score < Integer.MIN_VALUE || score > Integer.MAX_VALUE) {
            return;
        }
        Score sco = this.board.m_83471_(player, objec);
        sco.m_83402_(score);
    }

    private Objective getObjectiveWithException(String objective) {
        Objective objec = this.board.m_83477_(objective);
        if (objec == null) {
            throw new CustomNPCsException("Score objective does not exist: %s", objective);
        }
        return objec;
    }

    @Override
    public int getPlayerScore(String player, String objective) {
        Objective objec = this.getObjectiveWithException(objective);
        if (objec.m_83321_().m_83621_()) {
            return 0;
        }
        return this.board.m_83471_(player, objec).m_83400_();
    }

    @Override
    public boolean hasPlayerObjective(String player, String objective) {
        Objective objec = this.getObjectiveWithException(objective);
        return this.board.m_83483_(player).get(objec) != null;
    }

    @Override
    public void deletePlayerScore(String player, String objective) {
        Objective objec = this.getObjectiveWithException(objective);
        if (this.board.m_83483_(player).remove(objec) != null) {
            this.board.m_83495_(player);
        }
    }

    @Override
    public IScoreboardTeam[] getTeams() {
        ArrayList list = new ArrayList(this.board.m_83491_());
        IScoreboardTeam[] teams = new IScoreboardTeam[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            teams[i] = new ScoreboardTeamWrapper((PlayerTeam)list.get(i), this.board);
        }
        return teams;
    }

    @Override
    public boolean hasTeam(String name) {
        return this.board.m_83489_(name) != null;
    }

    @Override
    public IScoreboardTeam addTeam(String name) {
        if (this.hasTeam(name)) {
            throw new CustomNPCsException("Team %s already exists", name);
        }
        return new ScoreboardTeamWrapper(this.board.m_83492_(name), this.board);
    }

    @Override
    public IScoreboardTeam getTeam(String name) {
        PlayerTeam team = this.board.m_83489_(name);
        if (team == null) {
            return null;
        }
        return new ScoreboardTeamWrapper(team, this.board);
    }

    @Override
    public void removeTeam(String name) {
        PlayerTeam team = this.board.m_83489_(name);
        if (team != null) {
            this.board.m_83475_(team);
        }
    }

    @Override
    public IScoreboardTeam getPlayerTeam(String player) {
        PlayerTeam team = this.board.m_83500_(player);
        if (team == null) {
            return null;
        }
        return new ScoreboardTeamWrapper(team, this.board);
    }

    @Override
    public void removePlayerTeam(String player) {
        this.board.m_83495_(player);
    }
}

