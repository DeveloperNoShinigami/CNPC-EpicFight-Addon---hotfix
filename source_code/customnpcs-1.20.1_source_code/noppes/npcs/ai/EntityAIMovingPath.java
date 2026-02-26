/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIMovingPath
extends Goal {
    private EntityNPCInterface npc;
    private int[] pos;
    private int retries = 0;

    public EntityAIMovingPath(EntityNPCInterface iNpc) {
        this.npc = iNpc;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (this.npc.isAttacking() || this.npc.isInteracting() || this.npc.m_217043_().m_188503_(40) != 0 && this.npc.ais.movingPause || !this.npc.m_21573_().m_26571_()) {
            return false;
        }
        List<int[]> list = this.npc.ais.getMovingPath();
        if (list.size() < 2) {
            return false;
        }
        this.npc.ais.incrementMovingPath();
        this.pos = this.npc.ais.getCurrentMovingPath();
        this.retries = 0;
        return true;
    }

    public boolean m_8045_() {
        if (this.npc.isAttacking() || this.npc.isInteracting()) {
            this.npc.ais.decreaseMovingPath();
            return false;
        }
        if (this.npc.m_21573_().m_26571_()) {
            this.npc.m_21573_().m_26573_();
            if (this.npc.m_20275_(this.pos[0], this.pos[1], this.pos[2]) < 3.0) {
                return false;
            }
            if (this.retries++ < 3) {
                this.m_8056_();
                return true;
            }
            return false;
        }
        return true;
    }

    public void m_8056_() {
        this.npc.m_21573_().m_26519_((double)this.pos[0] + 0.5, (double)this.pos[1], (double)this.pos[2] + 0.5, 1.0);
    }
}

