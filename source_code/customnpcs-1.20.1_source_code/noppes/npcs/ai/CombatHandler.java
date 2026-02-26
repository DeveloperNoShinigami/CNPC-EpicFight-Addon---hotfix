/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.ai;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.ability.AbstractAbility;
import noppes.npcs.entity.EntityNPCInterface;

public class CombatHandler {
    private Map<LivingEntity, Float> aggressors = new HashMap<LivingEntity, Float>();
    private EntityNPCInterface npc;
    private long startTime = 0L;
    private int combatResetTimer = 0;

    public CombatHandler(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public void update() {
        if (this.npc.isKilled()) {
            if (this.npc.isAttacking()) {
                this.reset();
            }
            return;
        }
        if (this.npc.m_5448_() != null && !this.npc.isAttacking()) {
            this.start();
        }
        if (!this.shouldCombatContinue()) {
            if (this.combatResetTimer++ > 40) {
                this.reset();
            }
            return;
        }
        this.combatResetTimer = 0;
    }

    private boolean shouldCombatContinue() {
        if (this.npc.m_5448_() == null) {
            return false;
        }
        return this.isValidTarget(this.npc.m_5448_());
    }

    public void damage(DamageSource source, float damageAmount) {
        this.combatResetTimer = 0;
        Entity e = NoppesUtilServer.GetDamageSourcee(source);
        if (e instanceof LivingEntity) {
            LivingEntity el = (LivingEntity)e;
            Float f = this.aggressors.get(el);
            if (f == null) {
                f = Float.valueOf(0.0f);
            }
            this.aggressors.put(el, Float.valueOf(f.floatValue() + damageAmount));
        }
    }

    public void start() {
        this.combatResetTimer = 0;
        this.startTime = this.npc.m_9236_().m_6106_().m_6793_();
        this.npc.m_20088_().m_135381_(EntityNPCInterface.Attacking, (Object)true);
        for (AbstractAbility ab : this.npc.abilities.abilities) {
            ab.startCombat();
        }
    }

    public void reset() {
        this.combatResetTimer = 0;
        this.aggressors.clear();
        this.npc.m_20088_().m_135381_(EntityNPCInterface.Attacking, (Object)false);
        this.npc.m_6710_(null);
    }

    public boolean checkTarget() {
        if (this.aggressors.isEmpty() || this.npc.f_19797_ % 10 != 0) {
            return false;
        }
        LivingEntity target = this.npc.m_5448_();
        Float current = Float.valueOf(0.0f);
        if (this.isValidTarget(target)) {
            current = this.aggressors.get(target);
            if (current == null) {
                current = Float.valueOf(0.0f);
            }
        } else {
            target = null;
        }
        for (Map.Entry<LivingEntity, Float> entry : this.aggressors.entrySet()) {
            if (!(entry.getValue().floatValue() > current.floatValue()) || !this.isValidTarget(entry.getKey())) continue;
            current = entry.getValue();
            target = entry.getKey();
        }
        return target == null;
    }

    public boolean isValidTarget(LivingEntity target) {
        if (target == null || !target.m_6084_()) {
            return false;
        }
        if (target instanceof Player && ((Player)target).m_150110_().f_35934_) {
            return false;
        }
        return this.npc.isInRange((Entity)target, this.npc.stats.aggroRange);
    }
}

