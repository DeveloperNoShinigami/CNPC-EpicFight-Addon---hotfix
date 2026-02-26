/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAISprintToTarget
extends Goal {
    private EntityNPCInterface npc;

    public EntityAISprintToTarget(EntityNPCInterface par1EntityLiving) {
        this.npc = par1EntityLiving;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        LivingEntity runTarget = this.npc.m_5448_();
        if (runTarget == null || this.npc.m_21573_().m_26571_()) {
            return false;
        }
        switch (this.npc.ais.onAttack) {
            case 0: {
                return !this.npc.isInRange((Entity)runTarget, 8.0) ? this.npc.m_20096_() : false;
            }
            case 2: {
                return this.npc.isInRange((Entity)runTarget, 7.0) ? this.npc.m_20096_() : false;
            }
        }
        return false;
    }

    public boolean m_8045_() {
        Vec3 mo = this.npc.m_20184_();
        return this.npc.m_6084_() && this.npc.m_20096_() && this.npc.f_20916_ <= 0 && mo.f_82479_ != 0.0 && mo.f_82481_ != 0.0;
    }

    public void m_8056_() {
        this.npc.m_6858_(true);
    }

    public void m_8041_() {
        this.npc.m_6858_(false);
    }
}

