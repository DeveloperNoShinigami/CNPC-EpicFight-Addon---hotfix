/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.ability;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.ability.AbstractAbility;
import noppes.npcs.ability.IAbilityDamaged;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.constants.EnumAbilityType;
import noppes.npcs.entity.EntityNPCInterface;

public class AbilityBlock
extends AbstractAbility
implements IAbilityDamaged {
    public AbilityBlock(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public boolean canRun(LivingEntity target) {
        return super.canRun(target);
    }

    @Override
    public boolean isType(EnumAbilityType type) {
        return type == EnumAbilityType.ATTACKED;
    }

    @Override
    public void handleEvent(NpcEvent.DamagedEvent event) {
        ServerLevel level = (ServerLevel)this.npc.m_20193_();
        level.m_7605_((Entity)this.npc, (byte)29);
        event.setCanceled(true);
        this.endAbility();
    }
}

