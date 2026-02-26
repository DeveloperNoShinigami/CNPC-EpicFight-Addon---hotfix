/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.goal.Goal
 */
package noppes.npcs.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIAnimation
extends Goal {
    private EntityNPCInterface npc;
    private boolean isAttacking = false;
    private boolean removed = false;
    private boolean isAtStartpoint = false;
    private boolean hasPath = false;
    private int tick = 4;
    public int temp = 0;

    public EntityAIAnimation(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean m_8036_() {
        boolean bl = this.removed = !this.npc.m_6084_();
        if (this.removed) {
            return this.npc.currentAnimation != 2;
        }
        if (this.npc.stats.ranged.getHasAimAnimation() && this.npc.isAttacking()) {
            return this.npc.currentAnimation != 6;
        }
        this.hasPath = !this.npc.m_21573_().m_26571_();
        this.isAttacking = this.npc.isAttacking();
        boolean bl2 = this.isAtStartpoint = this.npc.ais.shouldReturnHome() && this.npc.isVeryNearAssignedPlace();
        if (this.temp != 0) {
            if (!this.hasNavigation()) {
                return this.npc.currentAnimation != this.temp;
            }
            this.temp = 0;
        }
        if (this.hasNavigation() && !EntityAIAnimation.isWalkingAnimation(this.npc.currentAnimation)) {
            return this.npc.currentAnimation != 0;
        }
        return this.npc.currentAnimation != this.npc.ais.animationType;
    }

    public void m_8037_() {
        if (this.npc.stats.ranged.getHasAimAnimation() && this.npc.isAttacking()) {
            this.setAnimation(6);
            return;
        }
        int type = this.npc.ais.animationType;
        if (this.removed) {
            type = 2;
        } else if (!EntityAIAnimation.isWalkingAnimation(this.npc.ais.animationType) && this.hasNavigation()) {
            type = 0;
        } else if (this.temp != 0) {
            if (this.hasNavigation()) {
                this.temp = 0;
            } else {
                type = this.temp;
            }
        }
        this.setAnimation(type);
    }

    public void m_8041_() {
    }

    public static int getWalkingAnimationGuiIndex(int animation) {
        if (animation == 4) {
            return 1;
        }
        if (animation == 6) {
            return 2;
        }
        if (animation == 5) {
            return 3;
        }
        if (animation == 7) {
            return 4;
        }
        if (animation == 3) {
            return 5;
        }
        return 0;
    }

    public static boolean isWalkingAnimation(int animation) {
        return EntityAIAnimation.getWalkingAnimationGuiIndex(animation) != 0;
    }

    private void setAnimation(int animation) {
        this.npc.setCurrentAnimation(animation);
        this.npc.m_6210_();
        this.npc.m_6034_(this.npc.m_20185_(), this.npc.m_20186_(), this.npc.m_20189_());
    }

    private boolean hasNavigation() {
        return this.isAttacking || this.npc.ais.shouldReturnHome() && !this.isAtStartpoint && !this.npc.isFollower() || this.hasPath;
    }
}

