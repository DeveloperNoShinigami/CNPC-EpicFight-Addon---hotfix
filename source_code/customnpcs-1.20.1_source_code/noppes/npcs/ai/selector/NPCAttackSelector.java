/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.ai.selector;

import com.google.common.base.Predicate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumCompanionJobs;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobGuard;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.companion.CompanionGuard;

public class NPCAttackSelector
implements Predicate<LivingEntity> {
    private EntityNPCInterface npc;

    public NPCAttackSelector(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean isEntityApplicable(LivingEntity entity) {
        if (!entity.m_6084_() || entity == this.npc || !this.npc.isInRange((Entity)entity, this.npc.stats.aggroRange) || entity.m_21223_() < 1.0f) {
            return false;
        }
        if (this.npc.ais.directLOS && !this.npc.m_21574_().m_148306_((Entity)entity)) {
            return false;
        }
        if (!this.npc.isFollower() && this.npc.ais.shouldReturnHome()) {
            int allowedDistance = this.npc.stats.aggroRange * 2;
            if (this.npc.ais.getMovingType() == 1) {
                allowedDistance += this.npc.ais.walkingRange;
            }
            double distance = entity.m_20275_((double)this.npc.getStartXPos(), this.npc.getStartYPos(), (double)this.npc.getStartZPos());
            if (this.npc.ais.getMovingType() == 2) {
                int[] arr = this.npc.ais.getCurrentMovingPath();
                distance = entity.m_20275_((double)arr[0], (double)arr[1], (double)arr[2]);
            }
            if (distance > (double)(allowedDistance * allowedDistance)) {
                return false;
            }
        }
        if (this.npc.job.getType() == 3 && ((JobGuard)this.npc.job).isEntityApplicable((Entity)entity)) {
            return true;
        }
        if (this.npc.role.getType() == 6) {
            RoleCompanion role = (RoleCompanion)this.npc.role;
            if (role.companionJobInterface.getType() == EnumCompanionJobs.GUARD && ((CompanionGuard)role.companionJobInterface).isEntityApplicable((Entity)entity)) {
                return true;
            }
        }
        if (entity instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)entity;
            if (!this.npc.faction.isAggressiveToPlayer((Player)player) || player.m_150110_().f_35934_) {
                return false;
            }
            if (CustomNpcs.EnableInvisibleNpcs && CustomNpcs.InvisibilityAlgorithm == 2) {
                return this.npc.display.isVisibleTo((Player)player) || player.m_5833_() || player.m_21205_().m_41720_() == CustomItems.wand;
            }
            return true;
        }
        if (entity instanceof EntityNPCInterface) {
            if (((EntityNPCInterface)entity).isKilled()) {
                return false;
            }
            if (this.npc.advanced.attackOtherFactions) {
                return this.npc.faction.isAggressiveToNpc((EntityNPCInterface)entity);
            }
        }
        return false;
    }

    public boolean apply(LivingEntity ob) {
        return this.isEntityApplicable(ob);
    }
}

