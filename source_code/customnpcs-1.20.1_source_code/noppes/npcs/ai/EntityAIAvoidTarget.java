/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIAvoidTarget
extends Goal {
    private EntityNPCInterface npc;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private float health;
    private Path entityPathEntity;
    private PathNavigation entityPathNavigate;
    private Class targetEntityClass;

    public EntityAIAvoidTarget(EntityNPCInterface par1EntityNPC) {
        this.npc = par1EntityNPC;
        this.distanceFromEntity = this.npc.stats.aggroRange;
        this.health = this.npc.m_21223_();
        this.entityPathNavigate = par1EntityNPC.m_21573_();
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        LivingEntity target = this.npc.m_5448_();
        if (target == null) {
            return false;
        }
        this.targetEntityClass = target.getClass();
        if (this.targetEntityClass == Player.class) {
            this.closestLivingEntity = this.npc.m_9236_().m_45930_((Entity)this.npc, (double)this.distanceFromEntity);
            if (this.closestLivingEntity == null) {
                return false;
            }
        } else {
            List var1 = this.npc.m_9236_().m_45976_(this.targetEntityClass, this.npc.m_20191_().m_82377_((double)this.distanceFromEntity, 3.0, (double)this.distanceFromEntity));
            if (var1.isEmpty()) {
                return false;
            }
            this.closestLivingEntity = (Entity)var1.get(0);
        }
        if (!this.npc.m_21574_().m_148306_(this.closestLivingEntity) && this.npc.ais.directLOS) {
            return false;
        }
        Vec3 var2 = DefaultRandomPos.m_148407_((PathfinderMob)this.npc, (int)16, (int)7, (Vec3)new Vec3(this.closestLivingEntity.m_20185_(), this.closestLivingEntity.m_20186_(), this.closestLivingEntity.m_20189_()));
        if (var2 == null || var2 == Vec3.f_82478_) {
            return false;
        }
        if (this.closestLivingEntity.m_20275_(var2.f_82479_, var2.f_82480_, var2.f_82481_) < this.closestLivingEntity.m_20280_((Entity)this.npc)) {
            return false;
        }
        this.entityPathEntity = this.entityPathNavigate.m_26524_(var2.f_82479_, var2.f_82480_, var2.f_82481_, 0);
        return this.entityPathEntity != null;
    }

    public boolean m_8045_() {
        return !this.entityPathNavigate.m_26571_();
    }

    public void m_8056_() {
        this.entityPathNavigate.m_26536_(this.entityPathEntity, 1.0);
    }

    public void m_8041_() {
        this.closestLivingEntity = null;
        this.npc.m_6710_(null);
    }

    public void m_8037_() {
        if (this.npc.isInRange(this.closestLivingEntity, 7.0)) {
            this.npc.m_21573_().m_26517_(1.2);
        } else {
            this.npc.m_21573_().m_26517_(1.0);
        }
    }
}

