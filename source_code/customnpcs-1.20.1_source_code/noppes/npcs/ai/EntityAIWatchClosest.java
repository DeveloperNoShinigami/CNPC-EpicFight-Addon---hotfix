/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIWatchClosest
extends Goal {
    private EntityNPCInterface npc;
    protected Entity closestEntity;
    private float maxDistance;
    private int lookTime;
    private float change;
    private Class<? extends LivingEntity> watchedClass;
    protected final TargetingConditions predicate;

    public EntityAIWatchClosest(EntityNPCInterface par1EntityLiving, Class<? extends LivingEntity> limbSwingAmountClass, float par3) {
        this.npc = par1EntityLiving;
        this.watchedClass = limbSwingAmountClass;
        this.maxDistance = par3;
        this.change = 0.002f;
        this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
        this.predicate = TargetingConditions.m_148353_().m_26883_((double)par3);
    }

    public boolean m_8036_() {
        if (this.npc.m_217043_().m_188501_() >= this.change || this.npc.isInteracting()) {
            return false;
        }
        if (this.npc.m_5448_() != null) {
            this.closestEntity = this.npc.m_5448_();
        }
        if (this.watchedClass == Player.class) {
            this.closestEntity = this.npc.m_9236_().m_45930_((Entity)this.npc, (double)this.maxDistance);
        } else {
            this.closestEntity = this.npc.m_9236_().m_45963_(this.watchedClass, this.predicate, (LivingEntity)this.npc, this.npc.m_20185_(), this.npc.m_20188_(), this.npc.m_20189_(), this.npc.m_20191_().m_82377_((double)this.maxDistance, 3.0, (double)this.maxDistance));
            if (this.closestEntity != null) {
                return this.npc.canNpcSee(this.closestEntity);
            }
        }
        return this.closestEntity != null;
    }

    public boolean m_8045_() {
        if (this.npc.isInteracting() || this.npc.isAttacking() || !this.closestEntity.m_6084_() || !this.npc.m_6084_()) {
            return false;
        }
        return !this.npc.isInRange(this.closestEntity, this.maxDistance) ? false : this.lookTime > 0;
    }

    public void m_8056_() {
        this.lookTime = 60 + this.npc.m_217043_().m_188503_(60);
    }

    public void m_8041_() {
        this.closestEntity = null;
    }

    public void m_8037_() {
        this.npc.m_21563_().m_24950_(this.closestEntity.m_20185_(), this.closestEntity.m_20186_() + (double)this.closestEntity.m_20192_(), this.closestEntity.m_20189_(), 10.0f, (float)this.npc.m_8132_());
        --this.lookTime;
    }
}

