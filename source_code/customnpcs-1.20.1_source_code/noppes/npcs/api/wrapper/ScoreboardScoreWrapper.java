/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.scores.Score
 */
package noppes.npcs.api.wrapper;

import net.minecraft.world.scores.Score;
import noppes.npcs.api.IScoreboardScore;

public class ScoreboardScoreWrapper
implements IScoreboardScore {
    private Score score;

    public ScoreboardScoreWrapper(Score score) {
        this.score = score;
    }

    @Override
    public int getValue() {
        return this.score.m_83400_();
    }

    @Override
    public void setValue(int val) {
        this.score.m_83402_(val);
    }

    @Override
    public String getPlayerName() {
        return this.score.m_83405_();
    }
}

