/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityAIFindShade
extends Goal {
    private PathfinderMob theCreature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private Level level;

    public EntityAIFindShade(PathfinderMob par1Mob) {
        this.theCreature = par1Mob;
        this.level = par1Mob.m_9236_();
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (!this.level.m_46461_()) {
            return false;
        }
        if (!this.level.m_45527_(new BlockPos((int)this.theCreature.m_20185_(), (int)this.theCreature.m_20191_().f_82289_, (int)this.theCreature.m_20189_()))) {
            return false;
        }
        Vec3 var1 = this.findPossibleShelter();
        if (var1 == null) {
            return false;
        }
        this.shelterX = var1.f_82479_;
        this.shelterY = var1.f_82480_;
        this.shelterZ = var1.f_82481_;
        return true;
    }

    public boolean m_8045_() {
        return !this.theCreature.m_21573_().m_26571_();
    }

    public void m_8056_() {
        this.theCreature.m_21573_().m_26519_(this.shelterX, this.shelterY, this.shelterZ, 1.0);
    }

    private Vec3 findPossibleShelter() {
        RandomSource random = this.theCreature.m_217043_();
        BlockPos blockpos = new BlockPos((int)this.theCreature.m_20185_(), (int)this.theCreature.m_20191_().f_82289_, (int)this.theCreature.m_20189_());
        for (int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.m_7918_(random.m_188503_(20) - 10, random.m_188503_(6) - 3, random.m_188503_(20) - 10);
            if (this.level.m_45527_(blockpos1) || !(this.theCreature.m_21692_(blockpos1) < 0.0f)) continue;
            return new Vec3((double)blockpos1.m_123341_(), (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_());
        }
        return null;
    }
}

