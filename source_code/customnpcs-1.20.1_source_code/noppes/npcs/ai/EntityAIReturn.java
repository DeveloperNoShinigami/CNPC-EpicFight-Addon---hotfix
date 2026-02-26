/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.CustomNpcs;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIReturn
extends Goal {
    public static final int MaxTotalTicks = 600;
    private final EntityNPCInterface npc;
    private int stuckTicks = 0;
    private int totalTicks = 0;
    private double endPosX;
    private double endPosY;
    private double endPosZ;
    private boolean wasAttacked = false;
    private double[] preAttackPos;
    private int stuckCount = 0;

    public EntityAIReturn(EntityNPCInterface npc) {
        this.npc = npc;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (this.npc.hasOwner() || this.npc.m_20159_() || !this.npc.ais.shouldReturnHome() || this.npc.isKilled() || !this.npc.m_21573_().m_26571_() || this.npc.isInteracting()) {
            return false;
        }
        if (!(this.npc.ais.findShelter != 0 || this.npc.m_9236_().m_46461_() && !this.npc.m_9236_().m_46471_() || this.npc.m_9236_().m_6042_().f_223549_())) {
            BlockPos pos = new BlockPos((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos());
            if (this.npc.m_9236_().m_45527_(pos) || this.npc.m_9236_().m_7146_(pos) <= 8) {
                return false;
            }
        } else if (this.npc.ais.findShelter == 1 && this.npc.m_9236_().m_46461_()) {
            BlockPos pos = new BlockPos((int)this.npc.getStartXPos(), (int)this.npc.getStartYPos(), (int)this.npc.getStartZPos());
            if (this.npc.m_9236_().m_45527_(pos)) {
                return false;
            }
        }
        if (this.npc.isAttacking()) {
            if (!this.wasAttacked) {
                this.wasAttacked = true;
                this.preAttackPos = new double[]{this.npc.m_20185_(), this.npc.m_20186_(), this.npc.m_20189_()};
            }
            return false;
        }
        if (!this.npc.isAttacking() && this.wasAttacked) {
            return true;
        }
        if (this.npc.ais.getMovingType() == 2 && this.npc.ais.distanceToSqrToPathPoint() < (double)(CustomNpcs.NpcNavRange * CustomNpcs.NpcNavRange)) {
            return false;
        }
        if (this.npc.ais.getMovingType() == 1) {
            double x = this.npc.m_20185_() - (double)this.npc.getStartXPos();
            double z = this.npc.m_20189_() - (double)this.npc.getStartZPos();
            return !this.npc.isInRange(this.npc.getStartXPos(), -6666.0, this.npc.getStartZPos(), this.npc.ais.walkingRange);
        }
        if (this.npc.ais.getMovingType() == 0) {
            return !this.npc.isVeryNearAssignedPlace();
        }
        return false;
    }

    public boolean m_8045_() {
        if (this.npc.isFollower() || this.npc.isKilled() || this.npc.isAttacking() || this.npc.isVeryNearAssignedPlace() || this.npc.isInteracting() || this.npc.m_20159_()) {
            return false;
        }
        if (this.npc.m_21573_().m_26571_() && this.wasAttacked && !this.isTooFar()) {
            return false;
        }
        return this.totalTicks <= 600;
    }

    public void m_8037_() {
        ++this.totalTicks;
        if (this.totalTicks > 600) {
            this.npc.m_6034_(this.endPosX, this.endPosY, this.endPosZ);
            this.npc.m_21573_().m_26573_();
            return;
        }
        if (this.stuckTicks > 0) {
            --this.stuckTicks;
        } else if (this.npc.m_21573_().m_26571_()) {
            ++this.stuckCount;
            this.stuckTicks = 10;
            if (this.totalTicks > 30 && this.wasAttacked && this.isTooFar() || this.stuckCount > 5) {
                this.npc.m_6034_(this.endPosX, this.endPosY, this.endPosZ);
                this.npc.m_21573_().m_26573_();
            } else {
                this.navigate(this.stuckCount % 2 == 1);
            }
        } else {
            this.stuckCount = 0;
        }
    }

    private boolean isTooFar() {
        double z;
        double x;
        int allowedDistance = this.npc.stats.aggroRange * 2;
        if (this.npc.ais.getMovingType() == 1) {
            allowedDistance += this.npc.ais.walkingRange;
        }
        return (x = this.npc.m_20185_() - this.endPosX) * x + (z = this.npc.m_20189_() - this.endPosZ) * z > (double)(allowedDistance * allowedDistance);
    }

    public void m_8056_() {
        this.stuckTicks = 0;
        this.totalTicks = 0;
        this.stuckCount = 0;
        this.navigate(false);
    }

    private void navigate(boolean towards) {
        if (!this.wasAttacked) {
            this.endPosX = this.npc.getStartXPos();
            this.endPosY = this.npc.getStartYPos();
            this.endPosZ = this.npc.getStartZPos();
        } else {
            this.endPosX = this.preAttackPos[0];
            this.endPosY = this.preAttackPos[1];
            this.endPosZ = this.preAttackPos[2];
        }
        double posX = this.endPosX;
        double posY = this.endPosY;
        double posZ = this.endPosZ;
        double range = Math.sqrt(this.npc.m_20275_(posX, posY, posZ));
        if (range > (double)CustomNpcs.NpcNavRange || towards) {
            Vec3 start;
            Vec3 pos;
            int distance = (int)range;
            distance = distance > CustomNpcs.NpcNavRange ? CustomNpcs.NpcNavRange / 2 : (distance /= 2);
            if (distance > 2 && (pos = DefaultRandomPos.m_148412_((PathfinderMob)this.npc, (int)(distance / 2), (int)(distance / 2 > 7 ? 7 : distance / 2), (Vec3)(start = new Vec3(posX, posY, posZ)), (double)1.5707963267948966)) != null) {
                posX = pos.f_82479_;
                posY = pos.f_82480_;
                posZ = pos.f_82481_;
            }
        }
        this.npc.m_21573_().m_26573_();
        this.npc.m_21573_().m_26519_(posX, posY, posZ, 1.0);
    }

    public void m_8041_() {
        this.wasAttacked = false;
        this.npc.m_21573_().m_26573_();
    }
}

