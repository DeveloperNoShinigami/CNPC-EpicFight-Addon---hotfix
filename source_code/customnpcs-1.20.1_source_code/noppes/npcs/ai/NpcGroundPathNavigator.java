/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.navigation.GroundPathNavigation
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.level.pathfinder.PathFinder
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.ai.NpcWalkNodeEvaluator;

public class NpcGroundPathNavigator
extends GroundPathNavigation {
    public NpcGroundPathNavigator(Mob p_26448_, Level p_26449_) {
        super(p_26448_, p_26449_);
    }

    protected PathFinder m_5532_(int p_26453_) {
        this.f_26508_ = new NpcWalkNodeEvaluator();
        this.f_26508_.m_77351_(true);
        return new PathFinder(this.f_26508_, p_26453_);
    }

    protected void m_7636_() {
        Vec3 vec3d = this.m_7475_();
        int i = this.f_26496_.m_77398_();
        for (int j = this.f_26496_.m_77399_(); j < this.f_26496_.m_77398_(); ++j) {
            if ((double)this.f_26496_.m_77375_((int)j).f_77272_ == Math.floor(vec3d.f_82480_)) continue;
            i = j;
            break;
        }
        this.f_26505_ = this.f_26494_.m_20205_() > 0.75f ? this.f_26494_.m_20205_() / 2.0f : 0.75f - this.f_26494_.m_20205_() / 2.0f;
        BlockPos vec3i = this.f_26496_.m_77400_();
        if (Mth.m_14154_((float)((float)(this.f_26494_.m_20185_() - ((double)vec3i.m_123341_() + 0.5)))) < this.f_26505_ && Mth.m_14154_((float)((float)(this.f_26494_.m_20189_() - ((double)vec3i.m_123343_() + 0.5)))) < this.f_26505_ && Math.abs(this.f_26494_.m_20186_() - (double)vec3i.m_123342_()) < 1.0) {
            this.f_26496_.m_77374_();
        }
        int k = Mth.m_14167_((float)this.f_26494_.m_20205_());
        int l = Mth.m_14167_((float)this.f_26494_.m_20206_());
        int i1 = k;
        for (int j1 = i - 1; j1 >= this.f_26496_.m_77399_(); --j1) {
            if (!this.isDirectPathBetweenPoints(vec3d, this.f_26496_.m_77382_((Entity)this.f_26494_, j1), k, l, i1)) continue;
            this.f_26496_.m_77393_(j1);
            break;
        }
        this.m_6481_(vec3d);
    }

    protected boolean isDirectPathBetweenPoints(Vec3 posVec31, Vec3 posVec32, int sizeX, int sizeY, int sizeZ) {
        int i = Mth.m_14107_((double)posVec31.f_82479_);
        int j = Mth.m_14107_((double)posVec31.f_82481_);
        double d0 = posVec32.f_82479_ - posVec31.f_82479_;
        double d1 = posVec32.f_82481_ - posVec31.f_82481_;
        double d2 = d0 * d0 + d1 * d1;
        if (d2 < 1.0E-8) {
            return false;
        }
        double d3 = 1.0 / Math.sqrt(d2);
        if (!this.isSafeToStandAt(i, (int)posVec31.f_82480_, j, sizeX += 2, sizeY, sizeZ += 2, posVec31, d0 *= d3, d1 *= d3)) {
            return false;
        }
        sizeX -= 2;
        sizeZ -= 2;
        double d4 = 1.0 / Math.abs(d0);
        double d5 = 1.0 / Math.abs(d1);
        double d6 = (double)i - posVec31.f_82479_;
        double d7 = (double)j - posVec31.f_82481_;
        if (d0 >= 0.0) {
            d6 += 1.0;
        }
        if (d1 >= 0.0) {
            d7 += 1.0;
        }
        d6 /= d0;
        d7 /= d1;
        int k = d0 < 0.0 ? -1 : 1;
        int l = d1 < 0.0 ? -1 : 1;
        int i1 = Mth.m_14107_((double)posVec32.f_82479_);
        int j1 = Mth.m_14107_((double)posVec32.f_82481_);
        int k1 = i1 - i;
        int l1 = j1 - j;
        while (k1 * k > 0 || l1 * l > 0) {
            if (d6 < d7) {
                d6 += d4;
                k1 = i1 - (i += k);
            } else {
                d7 += d5;
                l1 = j1 - (j += l);
            }
            if (this.isSafeToStandAt(i, (int)posVec31.f_82480_, j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) continue;
            return false;
        }
        return true;
    }

    private boolean isPositionClear(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 p_179692_7_, double p_179692_8_, double p_179692_10_) {
        for (BlockPos blockpos : BlockPos.m_121940_((BlockPos)new BlockPos(x, y, z), (BlockPos)new BlockPos(x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1))) {
            double d1;
            double d0 = (double)blockpos.m_123341_() + 0.5 - p_179692_7_.f_82479_;
            if (!(d0 * p_179692_8_ + (d1 = (double)blockpos.m_123343_() + 0.5 - p_179692_7_.f_82481_) * p_179692_10_ >= 0.0) || this.f_26495_.m_8055_(blockpos).m_60647_((BlockGetter)this.f_26495_, blockpos, PathComputationType.LAND)) continue;
            return false;
        }
        return true;
    }

    private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 vec31, double p_179683_8_, double p_179683_10_) {
        int i = x - sizeX / 2;
        int j = z - sizeZ / 2;
        if (!this.isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_)) {
            return false;
        }
        for (int k = i; k < i + sizeX; ++k) {
            for (int l = j; l < j + sizeZ; ++l) {
                double d0 = (double)k + 0.5 - vec31.f_82479_;
                double d1 = (double)l + 0.5 - vec31.f_82481_;
                if (!(d0 * p_179683_8_ + d1 * p_179683_10_ >= 0.0)) continue;
                BlockPathTypes pathnodetype = ((NpcWalkNodeEvaluator)this.f_26508_).m_77567_(this.f_26494_, k, y - 1, l);
                if (pathnodetype == BlockPathTypes.WATER) {
                    return false;
                }
                if (pathnodetype == BlockPathTypes.LAVA) {
                    return false;
                }
                if (pathnodetype == BlockPathTypes.OPEN) {
                    return false;
                }
                pathnodetype = ((NpcWalkNodeEvaluator)this.f_26508_).m_77567_(this.f_26494_, k, y, l);
                float f = this.f_26494_.m_21439_(pathnodetype);
                if (f < 0.0f || f >= 8.0f) {
                    return false;
                }
                if (pathnodetype != BlockPathTypes.DAMAGE_FIRE && pathnodetype != BlockPathTypes.DANGER_FIRE && pathnodetype != BlockPathTypes.DAMAGE_OTHER) continue;
                return false;
            }
        }
        return true;
    }
}

