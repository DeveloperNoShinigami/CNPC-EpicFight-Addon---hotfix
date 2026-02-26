/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIJob
extends Goal {
    private EntityNPCInterface npc;

    public EntityAIJob(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean m_8036_() {
        if (this.npc.isKilled()) {
            return false;
        }
        return this.npc.job.aiShouldExecute();
    }

    public void m_8056_() {
        this.npc.job.aiStartExecuting();
    }

    public boolean m_8045_() {
        if (this.npc.isKilled()) {
            return false;
        }
        return this.npc.job.aiContinueExecute();
    }

    public void m_8037_() {
        this.npc.job.aiUpdateTask();
    }

    public void m_8041_() {
        this.npc.job.stop();
    }

    public EnumSet<Goal.Flag> m_7684_() {
        return this.npc.job.getFlags();
    }
}

