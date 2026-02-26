/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 */
package noppes.npcs.ai.selector;

import com.google.common.base.Predicate;
import noppes.npcs.entity.EntityNPCInterface;

public class NPCInteractSelector
implements Predicate {
    private EntityNPCInterface npc;

    public NPCInteractSelector(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean isEntityApplicable(EntityNPCInterface entity) {
        if (entity == this.npc || !this.npc.m_6084_()) {
            return false;
        }
        return !entity.isAttacking() && !this.npc.getFaction().isAggressiveToNpc(entity) && this.npc.ais.stopAndInteract;
    }

    public boolean apply(Object ob) {
        if (!(ob instanceof EntityNPCInterface)) {
            return false;
        }
        return this.isEntityApplicable((EntityNPCInterface)((Object)ob));
    }
}

