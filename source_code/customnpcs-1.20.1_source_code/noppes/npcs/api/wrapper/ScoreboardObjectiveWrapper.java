/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.scores.Objective
 *  net.minecraft.world.scores.Score
 *  net.minecraft.world.scores.Scoreboard
 */
package noppes.npcs.api.wrapper;

import java.util.Collection;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.IScoreboardObjective;
import noppes.npcs.api.IScoreboardScore;
import noppes.npcs.api.wrapper.ScoreboardScoreWrapper;

public class ScoreboardObjectiveWrapper
implements IScoreboardObjective {
    private Objective objective;
    private Scoreboard board;

    protected ScoreboardObjectiveWrapper(Scoreboard board, Objective objective) {
        this.objective = objective;
        this.board = board;
    }

    @Override
    public String getName() {
        return this.objective.m_83320_();
    }

    @Override
    public String getDisplayName() {
        return this.objective.m_83322_().getString();
    }

    @Override
    public void setDisplayName(String name) {
        if (name.length() <= 0 || name.length() > 32) {
            throw new CustomNPCsException("Score objective display name must be between 1-32 characters: %s", name);
        }
        this.objective.m_83316_((Component)Component.m_237115_((String)name));
    }

    @Override
    public String getCriteria() {
        return this.objective.m_83321_().m_83620_();
    }

    @Override
    public boolean isReadyOnly() {
        return this.objective.m_83321_().m_83621_();
    }

    @Override
    public IScoreboardScore[] getScores() {
        Collection list = this.board.m_83498_(this.objective);
        IScoreboardScore[] scores = new IScoreboardScore[list.size()];
        int i = 0;
        for (Score score : list) {
            scores[i] = new ScoreboardScoreWrapper(score);
            ++i;
        }
        return scores;
    }

    @Override
    public IScoreboardScore getScore(String player) {
        if (!this.hasScore(player)) {
            return null;
        }
        return new ScoreboardScoreWrapper(this.board.m_83471_(player, this.objective));
    }

    @Override
    public IScoreboardScore createScore(String player) {
        return new ScoreboardScoreWrapper(this.board.m_83471_(player, this.objective));
    }

    @Override
    public void removeScore(String player) {
        this.board.m_83479_(player, this.objective);
    }

    @Override
    public boolean hasScore(String player) {
        return this.board.m_83461_(player, this.objective);
    }
}

