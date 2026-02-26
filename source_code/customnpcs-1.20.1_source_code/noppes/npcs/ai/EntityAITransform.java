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

public class EntityAITransform
extends Goal {
    private EntityNPCInterface npc;

    public EntityAITransform(EntityNPCInterface npc) {
        this.npc = npc;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (this.npc.isKilled() || this.npc.isAttacking() || this.npc.transform.editingModus) {
            return false;
        }
        return this.npc.m_9236_().m_46461_() ? this.npc.transform.isActive : !this.npc.transform.isActive;
    }

    public void m_8056_() {
        this.npc.transform.transform(!this.npc.transform.isActive);
    }
}

