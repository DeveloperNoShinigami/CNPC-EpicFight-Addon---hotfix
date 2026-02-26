/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.goal.Goal
 */
package noppes.npcs.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIWaterNav
extends Goal {
    private EntityNPCInterface entity;

    public EntityAIWaterNav(EntityNPCInterface npc) {
        this.entity = npc;
        npc.m_21573_().m_7008_(true);
    }

    public boolean m_8036_() {
        if (this.entity.m_20069_() || this.entity.m_20077_()) {
            if (this.entity.ais.canSwim) {
                return true;
            }
            return this.entity.f_19862_;
        }
        return false;
    }

    public void m_8037_() {
        if (this.entity.m_217043_().m_188501_() < 0.8f) {
            this.entity.m_21569_().m_24901_();
        }
    }
}

