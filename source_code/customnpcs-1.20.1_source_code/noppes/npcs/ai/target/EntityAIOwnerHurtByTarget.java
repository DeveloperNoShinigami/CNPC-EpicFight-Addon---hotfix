/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 */
package noppes.npcs.ai.target;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIOwnerHurtByTarget
extends TargetGoal {
    EntityNPCInterface npc;
    LivingEntity theOwnerAttacker;
    private int timer;

    public EntityAIOwnerHurtByTarget(EntityNPCInterface npc) {
        super((Mob)npc, false);
        this.npc = npc;
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean m_8036_() {
        if (!this.npc.isFollower() || !this.npc.role.defendOwner()) {
            return false;
        }
        LivingEntity entitylivingbase = this.npc.getOwner();
        if (entitylivingbase == null) {
            return false;
        }
        this.theOwnerAttacker = entitylivingbase.m_21188_();
        int i = entitylivingbase.m_21213_();
        return i != this.timer && this.m_26150_(this.theOwnerAttacker, TargetingConditions.f_26872_);
    }

    public void m_8056_() {
        this.npc.m_6710_(this.theOwnerAttacker);
        LivingEntity entitylivingbase = this.npc.getOwner();
        if (entitylivingbase != null) {
            this.timer = entitylivingbase.m_21213_();
        }
        super.m_8056_();
    }
}

