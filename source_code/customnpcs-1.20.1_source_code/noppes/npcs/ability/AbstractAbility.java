/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.ability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.ability.IAbility;
import noppes.npcs.constants.EnumAbilityType;
import noppes.npcs.entity.EntityNPCInterface;

public abstract class AbstractAbility
implements IAbility {
    private long cooldown = 0L;
    private int cooldownTime = 10;
    private int startCooldownTime = 10;
    protected EntityNPCInterface npc;
    public float maxHP = 1.0f;
    public float minHP = 0.0f;

    public AbstractAbility(EntityNPCInterface npc) {
        this.npc = npc;
    }

    private boolean onCooldown() {
        return System.currentTimeMillis() < this.cooldown;
    }

    public int getRNG() {
        return 0;
    }

    public boolean canRun(LivingEntity target) {
        if (this.onCooldown()) {
            return false;
        }
        float f = this.npc.m_21223_() / this.npc.m_21233_();
        if (f < this.minHP || f > this.maxHP) {
            return false;
        }
        if (this.getRNG() > 1 && this.npc.m_217043_().m_188503_(this.getRNG()) != 0) {
            return false;
        }
        return this.npc.canNpcSee((Entity)target);
    }

    public void endAbility() {
        this.cooldown = System.currentTimeMillis() + (long)(this.cooldownTime * 1000);
    }

    public abstract boolean isType(EnumAbilityType var1);

    public void startCombat() {
        this.cooldown = System.currentTimeMillis() + (long)(this.startCooldownTime * 1000);
    }
}

