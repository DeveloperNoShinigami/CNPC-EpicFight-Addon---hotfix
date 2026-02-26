/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.goal.Goal
 */
package noppes.npcs.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIWorldLines
extends Goal {
    private EntityNPCInterface npc;
    private int cooldown = 100;

    public EntityAIWorldLines(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean m_8036_() {
        if (this.cooldown > 0) {
            --this.cooldown;
        }
        return !this.npc.isAttacking() && !this.npc.isKilled() && this.npc.advanced.hasLevelLines() && this.npc.m_217043_().m_188503_(1800) == 1;
    }

    public void m_8056_() {
        this.cooldown = 100;
        this.npc.saySurrounding(this.npc.advanced.getLevelLine());
    }
}

