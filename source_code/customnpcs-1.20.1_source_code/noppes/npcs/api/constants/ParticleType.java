/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.DustParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.api.constants;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleType {
    public static final int NONE = 0;
    public static final int SMOKE = 1;
    public static final int PORTAL = 2;
    public static final int REDSTONE = 3;
    public static final int LIGHTNING = 4;
    public static final int LARGE_SMOKE = 5;
    public static final int MAGIC = 6;
    public static final int ENCHANT = 7;
    public static final int CRIT = 8;

    public static ParticleOptions getMCType(int type) {
        if (type == 1) {
            return ParticleTypes.f_123762_;
        }
        if (type == 2) {
            return ParticleTypes.f_123760_;
        }
        if (type == 3) {
            return new RedstoneParticleType();
        }
        if (type == 4) {
            return ParticleTypes.f_123808_;
        }
        if (type == 5) {
            return ParticleTypes.f_123755_;
        }
        if (type == 6) {
            return ParticleTypes.f_123771_;
        }
        if (type == 7) {
            return ParticleTypes.f_123809_;
        }
        if (type == 8) {
            return ParticleTypes.f_123797_;
        }
        return null;
    }

    static class RedstoneParticleType
    extends DustParticleOptions {
        protected RedstoneParticleType() {
            super(DustParticleOptions.f_175788_, 1.0f);
        }

        public void m_7711_(FriendlyByteBuf p_197553_1_) {
        }

        public String m_5942_() {
            return ForgeRegistries.PARTICLE_TYPES.getKey((Object)ParticleTypes.f_123805_).toString();
        }
    }
}

