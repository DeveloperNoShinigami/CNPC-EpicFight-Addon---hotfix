/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.goal.Goal
 */
package noppes.npcs.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIRole
extends Goal {
    private EntityNPCInterface npc;

    public EntityAIRole(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean m_8036_() {
        if (this.npc.isKilled()) {
            return false;
        }
        return this.npc.role.aiShouldExecute();
    }

    public void m_8056_() {
        this.npc.role.aiStartExecuting();
    }

    public boolean m_8045_() {
        if (this.npc.isKilled()) {
            return false;
        }
        return this.npc.role.aiContinueExecute();
    }

    public void m_8037_() {
        this.npc.role.aiUpdateTask();
    }
}

