/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.goal.Goal
 */
package noppes.npcs.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import noppes.npcs.ability.AbstractAbility;
import noppes.npcs.ability.IAbilityUpdate;
import noppes.npcs.constants.EnumAbilityType;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIAbilities
extends Goal {
    private EntityNPCInterface npc;
    private IAbilityUpdate ability;

    public EntityAIAbilities(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public boolean m_8036_() {
        if (!this.npc.isAttacking()) {
            return false;
        }
        this.ability = (IAbilityUpdate)((Object)this.npc.abilities.getAbility(EnumAbilityType.UPDATE));
        return this.ability != null;
    }

    public boolean m_8045_() {
        return this.npc.isAttacking() && this.ability.isActive();
    }

    public void m_8037_() {
        this.ability.update();
    }

    public void m_8041_() {
        ((AbstractAbility)((Object)this.ability)).endAbility();
        this.ability = null;
    }
}

