/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.ai.attributes.Attributes
 */
package noppes.npcs.entity.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attributes;
import noppes.npcs.api.entity.data.INPCMelee;
import noppes.npcs.entity.EntityNPCInterface;

public class DataMelee
implements INPCMelee {
    private EntityNPCInterface npc;
    private int attackStrength = 5;
    private int attackSpeed = 20;
    private int attackRange = 2;
    private int knockback = 0;
    private int potionType = 0;
    private int potionDuration = 5;
    private int potionAmp = 0;

    public DataMelee(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public void load(CompoundTag compound) {
        this.attackSpeed = compound.m_128451_("AttackSpeed");
        this.setStrength(compound.m_128451_("AttackStrenght"));
        this.attackRange = compound.m_128451_("AttackRange");
        this.knockback = compound.m_128451_("KnockBack");
        this.potionType = compound.m_128451_("PotionEffect");
        this.potionDuration = compound.m_128451_("PotionDuration");
        this.potionAmp = compound.m_128451_("PotionAmp");
    }

    public CompoundTag save(CompoundTag compound) {
        compound.m_128405_("AttackStrenght", this.attackStrength);
        compound.m_128405_("AttackSpeed", this.attackSpeed);
        compound.m_128405_("AttackRange", this.attackRange);
        compound.m_128405_("KnockBack", this.knockback);
        compound.m_128405_("PotionEffect", this.potionType);
        compound.m_128405_("PotionDuration", this.potionDuration);
        compound.m_128405_("PotionAmp", this.potionAmp);
        return compound;
    }

    @Override
    public int getStrength() {
        return this.attackStrength;
    }

    @Override
    public void setStrength(int strength) {
        this.attackStrength = strength;
        this.npc.m_21051_(Attributes.f_22281_).m_22100_((double)this.attackStrength);
    }

    @Override
    public int getDelay() {
        return this.attackSpeed;
    }

    @Override
    public void setDelay(int speed) {
        this.attackSpeed = speed;
    }

    @Override
    public int getRange() {
        return this.attackRange;
    }

    @Override
    public void setRange(int range) {
        this.attackRange = range;
    }

    @Override
    public int getKnockback() {
        return this.knockback;
    }

    @Override
    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    @Override
    public int getEffectType() {
        return this.potionType;
    }

    @Override
    public int getEffectTime() {
        return this.potionDuration;
    }

    @Override
    public int getEffectStrength() {
        return this.potionAmp;
    }

    @Override
    public void setEffect(int type, int strength, int time) {
        this.potionType = type;
        this.potionDuration = time;
        this.potionAmp = strength;
    }
}

