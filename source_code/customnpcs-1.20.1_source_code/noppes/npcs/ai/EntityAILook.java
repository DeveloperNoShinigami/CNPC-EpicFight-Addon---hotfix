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
import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAILook
extends Goal {
    private final EntityNPCInterface npc;
    private int idle = 0;
    private double lookX;
    private double lookZ;
    private boolean forced = false;
    private Entity forcedEntity = null;

    public EntityAILook(EntityNPCInterface npc) {
        this.npc = npc;
        this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (this.forced) {
            return true;
        }
        if (this.npc.isAttacking() || !this.npc.m_21573_().m_26571_() || this.npc.m_5803_() || !this.npc.m_6084_()) {
            return false;
        }
        if (this.npc.isInteracting() || this.npc.ais.getStandingType() > 0 || this.idle > 0) {
            return true;
        }
        return this.npc.m_217043_().m_188501_() < 0.004f;
    }

    public void m_8056_() {
        if (this.npc.ais.getStandingType() == 0 || this.npc.ais.getStandingType() == 3) {
            double var1 = Math.PI * 2 * this.npc.m_217043_().m_188500_();
            if (this.npc.ais.getStandingType() == 3) {
                var1 = Math.PI / 180 * (double)this.npc.ais.orientation + 0.6283185307179586 + 1.8849555921538759 * this.npc.m_217043_().m_188500_();
            }
            this.lookX = Math.cos(var1);
            this.lookZ = Math.sin(var1);
            this.idle = 20 + this.npc.m_217043_().m_188503_(20);
        }
    }

    public void rotate(Entity entity) {
        this.forced = true;
        this.forcedEntity = entity;
    }

    public void rotate(int degrees) {
        this.forced = true;
        this.npc.f_20885_ = this.npc.f_20883_ = (float)degrees;
        this.npc.m_146922_(degrees);
    }

    public void m_8041_() {
        this.forced = false;
        this.forcedEntity = null;
    }

    public void m_8037_() {
        Entity lookat = null;
        if (this.forced && this.forcedEntity != null) {
            lookat = this.forcedEntity;
        } else if (this.npc.isInteracting()) {
            Iterator<LivingEntity> ita = this.npc.interactingEntities.iterator();
            double closestDistance = 12.0;
            while (ita.hasNext()) {
                LivingEntity entity = ita.next();
                double distance = entity.m_20280_((Entity)this.npc);
                if (distance < closestDistance) {
                    closestDistance = entity.m_20280_((Entity)this.npc);
                    lookat = entity;
                    continue;
                }
                if (!(distance > 12.0)) continue;
                ita.remove();
            }
        } else if (this.npc.ais.getStandingType() == 2) {
            lookat = this.npc.m_9236_().m_45930_((Entity)this.npc, 16.0);
        }
        if (lookat != null) {
            this.npc.m_21563_().m_24960_(lookat, 10.0f, (float)this.npc.m_8132_());
            return;
        }
        if (this.idle > 0) {
            --this.idle;
            this.npc.m_21563_().m_24950_(this.npc.m_20185_() + this.lookX, this.npc.m_20186_() + (double)this.npc.m_20192_(), this.npc.m_20189_() + this.lookZ, 10.0f, (float)this.npc.m_8132_());
        }
        if (this.npc.ais.getStandingType() == 1 && !this.forced) {
            this.npc.f_20885_ = this.npc.f_20883_ = (float)this.npc.ais.orientation;
            this.npc.m_146922_(this.npc.ais.orientation);
        }
    }
}

