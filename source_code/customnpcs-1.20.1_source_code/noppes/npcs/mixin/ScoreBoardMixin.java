/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.scores.Objective
 *  net.minecraft.world.scores.Score
 *  net.minecraft.world.scores.Scoreboard
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package noppes.npcs.mixin;

import java.util.Map;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={Scoreboard.class})
public interface ScoreBoardMixin {
    @Accessor(value="playerScores")
    public Map<String, Map<Objective, Score>> getScores();
}

