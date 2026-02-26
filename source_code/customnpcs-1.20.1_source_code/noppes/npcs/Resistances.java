/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.world.damagesource.DamageSource
 */
package noppes.npcs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;

public class Resistances {
    public float knockback = 1.0f;
    public float arrow = 1.0f;
    public float melee = 1.0f;
    public float explosion = 1.0f;

    public CompoundTag save() {
        CompoundTag compound = new CompoundTag();
        compound.m_128350_("Knockback", this.knockback);
        compound.m_128350_("Arrow", this.arrow);
        compound.m_128350_("Melee", this.melee);
        compound.m_128350_("Explosion", this.explosion);
        return compound;
    }

    public void readToNBT(CompoundTag compound) {
        this.knockback = compound.m_128457_("Knockback");
        this.arrow = compound.m_128457_("Arrow");
        this.melee = compound.m_128457_("Melee");
        this.explosion = compound.m_128457_("Explosion");
    }

    public float applyResistance(DamageSource source, float damage) {
        if (source.m_19385_().equals("arrow") || source.m_19385_().equals("thrown") || source.m_269533_(DamageTypeTags.f_268524_)) {
            damage *= 2.0f - this.arrow;
        } else if (source.m_19385_().equals("player") || source.m_19385_().equals("mob") || source.m_19385_().equals("npc")) {
            damage *= 2.0f - this.melee;
        } else if (source.m_19385_().equals("explosion") || source.m_19385_().equals("explosion.player")) {
            damage *= 2.0f - this.explosion;
        }
        return damage;
    }
}

