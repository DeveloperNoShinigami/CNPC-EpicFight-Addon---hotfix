/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.monster.RangedAttackMob
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIRangedAttack
extends Goal {
    private final EntityNPCInterface npc;
    private LivingEntity attackTarget;
    private int rangedAttackTime = 0;
    private int moveTries = 0;
    private int burstCount = 0;
    private int attackTick = 0;
    private boolean hasFired = false;

    public EntityAIRangedAttack(RangedAttackMob par1RangedAttackMob) {
        if (!(par1RangedAttackMob instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.npc = (EntityNPCInterface)par1RangedAttackMob;
        this.rangedAttackTime = this.npc.stats.ranged.getDelayMin() / 2;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        this.attackTarget = this.npc.m_5448_();
        if (this.attackTarget == null || !this.attackTarget.m_6084_() || !this.npc.isInRange((Entity)this.attackTarget, this.npc.stats.aggroRange) || this.npc.inventory.getProjectile() == null) {
            return false;
        }
        LivingEntity livingEntity = this.attackTarget;
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            if (CustomNpcs.EnableInvisibleNpcs && CustomNpcs.InvisibilityAlgorithm == 2 && !this.npc.display.isVisibleTo(player) && !player.m_5833_() && player.m_21205_().m_41720_() != CustomItems.wand) {
                return false;
            }
        }
        return this.npc.stats.ranged.getMeleeRange() < 1 || !this.npc.isInRange((Entity)this.attackTarget, this.npc.stats.ranged.getMeleeRange());
    }

    public void m_8041_() {
        this.attackTarget = null;
        this.npc.m_6710_(null);
        this.npc.m_21573_().m_26573_();
        this.moveTries = 0;
        this.hasFired = false;
        this.rangedAttackTime = this.npc.stats.ranged.getDelayMin() / 2;
    }

    public boolean m_183429_() {
        return true;
    }

    public void m_8037_() {
        this.npc.m_21563_().m_24960_((Entity)this.attackTarget, 30.0f, 30.0f);
        double var1 = this.npc.m_20275_(this.attackTarget.m_20185_(), this.attackTarget.m_20191_().f_82289_, this.attackTarget.m_20189_());
        float range = this.npc.stats.ranged.getRange() * this.npc.stats.ranged.getRange();
        if (this.npc.ais.directLOS) {
            this.moveTries = this.npc.m_21574_().m_148306_((Entity)this.attackTarget) ? ++this.moveTries : 0;
            int v = 15;
            if (var1 <= (double)range && this.moveTries >= v) {
                this.npc.m_21573_().m_26573_();
            } else {
                this.npc.m_21573_().m_5624_((Entity)this.attackTarget, 1.0);
            }
        }
        if (this.rangedAttackTime-- <= 0 && var1 <= (double)range && (this.npc.m_21574_().m_148306_((Entity)this.attackTarget) || this.npc.stats.ranged.getFireType() == 2)) {
            if (this.burstCount++ <= this.npc.stats.ranged.getBurst()) {
                this.rangedAttackTime = this.npc.stats.ranged.getBurstDelay();
            } else {
                this.burstCount = 0;
                this.hasFired = true;
                this.rangedAttackTime = this.npc.stats.ranged.getDelayRNG();
            }
            if (this.burstCount > 1) {
                boolean indirect = false;
                switch (this.npc.stats.ranged.getFireType()) {
                    case 1: {
                        indirect = var1 > (double)range / 2.0;
                        break;
                    }
                    case 2: {
                        indirect = !this.npc.m_21574_().m_148306_((Entity)this.attackTarget);
                    }
                }
                this.npc.m_6504_(this.attackTarget, indirect ? 1.0f : 0.0f);
                if (this.npc.currentAnimation != 6) {
                    this.npc.m_6674_(InteractionHand.MAIN_HAND);
                }
            }
        }
    }

    public boolean hasFired() {
        return this.hasFired;
    }
}

