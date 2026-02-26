/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIPounceTarget
extends Goal {
    private EntityNPCInterface npc;
    private LivingEntity leapTarget;
    private float leapSpeed = 1.3f;

    public EntityAIPounceTarget(EntityNPCInterface leapingEntity) {
        this.npc = leapingEntity;
        this.m_7021_(EnumSet.of(Goal.Flag.JUMP));
    }

    public boolean m_8036_() {
        if (!this.npc.m_20096_()) {
            return false;
        }
        this.leapTarget = this.npc.m_5448_();
        if (this.leapTarget == null || !this.npc.m_21574_().m_148306_((Entity)this.leapTarget)) {
            return false;
        }
        return !this.npc.isInRange((Entity)this.leapTarget, 4.0) && this.npc.isInRange((Entity)this.leapTarget, 8.0) ? this.npc.m_217043_().m_188503_(5) == 0 : false;
    }

    public boolean m_8045_() {
        return !this.npc.m_20096_();
    }

    public void m_8056_() {
        double varX = this.leapTarget.m_20185_() - this.npc.m_20185_();
        double varY = this.leapTarget.m_20191_().f_82289_ - this.npc.m_20191_().f_82289_;
        double varZ = this.leapTarget.m_20189_() - this.npc.m_20189_();
        float varF = (float)Math.sqrt(varX * varX + varZ * varZ);
        float angle = this.getAngleForXYZ(varX, varY, varZ, varF);
        float yaw = (float)(Math.atan2(varX, varZ) * 180.0 / Math.PI);
        Vec3 mo = new Vec3((double)(Mth.m_14031_((float)(yaw / 180.0f * (float)Math.PI)) * Mth.m_14089_((float)(angle / 180.0f * (float)Math.PI))), (double)Mth.m_14031_((float)((angle + 1.0f) / 180.0f * (float)Math.PI)), (double)(Mth.m_14089_((float)(yaw / 180.0f * (float)Math.PI)) * Mth.m_14089_((float)(angle / 180.0f * (float)Math.PI))));
        mo.m_82490_((double)this.leapSpeed);
        this.npc.m_20256_(mo);
    }

    public float getAngleForXYZ(double varX, double varY, double varZ, double horiDist) {
        float g = 0.1f;
        float var1 = this.leapSpeed * this.leapSpeed;
        double var2 = (double)g * horiDist;
        double var3 = (double)g * horiDist * horiDist + 2.0 * varY * (double)var1;
        double var4 = (double)(var1 * var1) - (double)g * var3;
        if (var4 < 0.0) {
            return 90.0f;
        }
        float var6 = var1 - (float)Math.sqrt(var4);
        return (float)(Math.atan2(var6, var2) * 180.0 / Math.PI);
    }
}

