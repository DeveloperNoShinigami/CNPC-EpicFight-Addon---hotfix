/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIFollow
extends Goal {
    private EntityNPCInterface npc;
    private LivingEntity owner;
    public int updateTick = 0;

    public EntityAIFollow(EntityNPCInterface npc) {
        this.npc = npc;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (!this.canExcute()) {
            return false;
        }
        return !this.npc.isInRange((Entity)this.owner, this.npc.followRange());
    }

    public boolean canExcute() {
        return this.npc.m_6084_() && this.npc.isFollower() && !this.npc.isAttacking() && (this.owner = this.npc.getOwner()) != null && this.npc.ais.animationType != 1;
    }

    public void m_8056_() {
        this.updateTick = 10;
    }

    public boolean m_8045_() {
        return !this.npc.m_21573_().m_26571_() && !this.npc.isInRange((Entity)this.owner, 2.0) && this.canExcute();
    }

    public void m_8041_() {
        this.owner = null;
        this.npc.m_21573_().m_26573_();
    }

    public void m_8037_() {
        ++this.updateTick;
        if (this.updateTick < 10) {
            return;
        }
        this.updateTick = 0;
        this.npc.m_21563_().m_24960_((Entity)this.owner, 10.0f, (float)this.npc.m_8132_());
        double distance = this.npc.m_20280_((Entity)this.owner);
        double speed = 1.0 + distance / 150.0;
        if (speed > 3.0) {
            speed = 3.0;
        }
        if (this.owner.m_20142_()) {
            speed += 0.5;
        }
        if (this.npc.m_21573_().m_5624_((Entity)this.owner, speed) && this.npc.isInRange((Entity)this.owner, 16.0)) {
            return;
        }
        this.npc.tpTo(this.owner);
    }
}

