/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.Path
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIAttackTarget
extends Goal {
    private Level level;
    private EntityNPCInterface npc;
    private LivingEntity entityTarget;
    private int attackTick = 0;
    private Path entityPathEntity;
    private int field_75445_i;
    private BlockPos startPos = BlockPos.f_121853_;

    public EntityAIAttackTarget(EntityNPCInterface par1EntityLiving) {
        this.npc = par1EntityLiving;
        this.level = par1EntityLiving.m_9236_();
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        LivingEntity entitylivingbase = this.npc.m_5448_();
        if (entitylivingbase == null || !entitylivingbase.m_6084_()) {
            return false;
        }
        int melee = this.npc.stats.ranged.getMeleeRange();
        if (!(this.npc.inventory.getProjectile() == null || melee > 0 && this.npc.isInRange((Entity)entitylivingbase, melee))) {
            return false;
        }
        if (entitylivingbase instanceof Player) {
            Player player = (Player)entitylivingbase;
            if (CustomNpcs.EnableInvisibleNpcs && CustomNpcs.InvisibilityAlgorithm == 2 && !this.npc.display.isVisibleTo(player) && !player.m_5833_() && player.m_21205_().m_41720_() != CustomItems.wand) {
                return false;
            }
        }
        this.entityTarget = entitylivingbase;
        this.entityPathEntity = this.npc.m_21573_().m_6570_((Entity)entitylivingbase, 0);
        return this.entityPathEntity != null;
    }

    public boolean m_8045_() {
        this.entityTarget = this.npc.m_5448_();
        if (this.entityTarget == null) {
            this.entityTarget = this.npc.m_21188_();
        }
        if (this.entityTarget == null || !this.entityTarget.m_6084_()) {
            return false;
        }
        if (!this.npc.isInRange((Entity)this.entityTarget, this.npc.stats.aggroRange)) {
            return false;
        }
        int melee = this.npc.stats.ranged.getMeleeRange();
        if (melee > 0 && !this.npc.isInRange((Entity)this.entityTarget, melee)) {
            return false;
        }
        LivingEntity livingEntity = this.entityTarget;
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            if (CustomNpcs.EnableInvisibleNpcs && CustomNpcs.InvisibilityAlgorithm == 2 && !this.npc.display.isVisibleTo(player) && !player.m_5833_() && player.m_21205_().m_41720_() != CustomItems.wand) {
                return false;
            }
        }
        return this.isWithinRestriction(this.entityTarget.m_20183_());
    }

    public boolean isWithinRestriction(BlockPos pos) {
        int range = Math.max(this.npc.stats.aggroRange * 2, 64);
        return this.startPos.m_123331_((Vec3i)pos) < (double)(range * range);
    }

    public void m_8056_() {
        this.startPos = this.npc.m_20183_();
        this.npc.m_21573_().m_26536_(this.entityPathEntity, 1.3);
        this.field_75445_i = 0;
    }

    public void m_8041_() {
        this.entityPathEntity = null;
        this.entityTarget = null;
        this.npc.m_21573_().m_26573_();
    }

    public boolean m_183429_() {
        return true;
    }

    public void m_8037_() {
        this.npc.m_21563_().m_24960_((Entity)this.entityTarget, 30.0f, 30.0f);
        if (--this.field_75445_i <= 0) {
            this.field_75445_i = 4 + this.npc.m_217043_().m_188503_(7);
            this.npc.m_21573_().m_5624_((Entity)this.entityTarget, (double)1.3f);
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
        double y = this.entityTarget.m_20186_();
        if (this.entityTarget.m_20191_() != null) {
            y = this.entityTarget.m_20191_().f_82289_;
        }
        double distance = this.npc.m_20275_(this.entityTarget.m_20185_(), y, this.entityTarget.m_20189_());
        double range = (float)(this.npc.stats.melee.getRange() * this.npc.stats.melee.getRange()) + this.entityTarget.m_20205_();
        double minRange = this.npc.m_20205_() * 2.0f * this.npc.m_20205_() * 2.0f + this.entityTarget.m_20205_();
        if (minRange > range) {
            range = minRange;
        }
        if (distance <= range && (this.npc.canNpcSee((Entity)this.entityTarget) || distance < minRange) && this.attackTick <= 0) {
            this.attackTick = this.npc.stats.melee.getDelay();
            this.npc.m_6674_(InteractionHand.MAIN_HAND);
            this.npc.m_7327_((Entity)this.entityTarget);
        }
    }
}

