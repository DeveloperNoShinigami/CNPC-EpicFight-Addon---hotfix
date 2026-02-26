/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.scores.Team
 */
package noppes.npcs.ai.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.Team;
import noppes.npcs.entity.EntityNPCInterface;

public class NpcNearestAttackableTargetGoal<T extends LivingEntity>
extends NearestAttackableTargetGoal<T> {
    private int unseenTicks1;

    public NpcNearestAttackableTargetGoal(EntityNPCInterface npc, Class<T> c, int range, boolean b, boolean b2, @Nullable Predicate<LivingEntity> selector) {
        super((Mob)npc, c, range, b, b2, selector);
        if (npc.ais.attackInvisible) {
            this.f_26051_.m_26893_();
        }
        if (!npc.ais.directLOS) {
            this.f_26051_.m_148355_();
        }
    }

    public void m_8056_() {
        this.unseenTicks1 = 0;
        this.f_26135_.m_6710_(this.f_26050_);
        super.m_8056_();
    }

    public void m_8041_() {
        this.f_26135_.m_6710_(null);
        this.f_26137_ = null;
    }

    public boolean m_8045_() {
        LivingEntity livingentity = this.f_26135_.m_5448_();
        if (livingentity == null) {
            livingentity = this.f_26137_;
        }
        if (livingentity == null) {
            return false;
        }
        if (!this.f_26135_.m_6779_(livingentity)) {
            return false;
        }
        Team team = this.f_26135_.m_5647_();
        Team team1 = livingentity.m_5647_();
        if (team != null && team1 == team) {
            return false;
        }
        double d0 = this.m_7623_();
        if (this.f_26135_.m_20280_((Entity)livingentity) > d0 * d0) {
            return false;
        }
        if (this.f_26136_) {
            if (this.f_26135_.m_21574_().m_148306_((Entity)livingentity)) {
                this.unseenTicks1 = 0;
            } else if (++this.unseenTicks1 > NpcNearestAttackableTargetGoal.m_186073_((int)this.f_26138_)) {
                return false;
            }
        }
        this.f_26135_.m_6710_(livingentity);
        return true;
    }

    protected AABB m_7255_(double p_26069_) {
        return this.f_26135_.m_20191_().m_82377_(p_26069_, p_26069_, p_26069_);
    }
}

