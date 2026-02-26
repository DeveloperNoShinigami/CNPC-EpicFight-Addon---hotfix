/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class EntityAIPanic
extends Goal {
    private PathfinderMob entityCreature;
    private float speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIPanic(PathfinderMob par1Mob, float limbSwingAmount) {
        this.entityCreature = par1Mob;
        this.speed = limbSwingAmount;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (this.entityCreature.m_5448_() == null && !this.entityCreature.m_6060_()) {
            return false;
        }
        Vec3 var1 = DefaultRandomPos.m_148403_((PathfinderMob)this.entityCreature, (int)5, (int)4);
        if (var1 == null) {
            return false;
        }
        this.randPosX = var1.f_82479_;
        this.randPosY = var1.f_82480_;
        this.randPosZ = var1.f_82481_;
        return true;
    }

    public void m_8056_() {
        this.entityCreature.m_21573_().m_26519_(this.randPosX, this.randPosY, this.randPosZ, (double)this.speed);
    }

    public boolean m_8045_() {
        if (this.entityCreature.m_5448_() == null) {
            return false;
        }
        return !this.entityCreature.m_21573_().m_26571_();
    }
}

