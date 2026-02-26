/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 */
package noppes.npcs.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class NpcWalkNodeEvaluator
extends WalkNodeEvaluator {
    public BlockPathTypes m_77567_(Mob p_77568_, int p_77569_, int p_77570_, int p_77571_) {
        return super.m_77567_(p_77568_, p_77569_, p_77570_, p_77571_);
    }

    public void m_6802_() {
        PathNavigationRegion level = this.f_77312_;
        Mob mob = this.f_77313_;
        super.m_6802_();
        this.f_77312_ = level;
        this.f_77313_ = mob;
    }
}

