/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.entity.EntityNPCInterface;

public abstract class EntityNPCFlying
extends EntityNPCInterface {
    public EntityNPCFlying(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
    }

    @Override
    public boolean canFly() {
        return this.ais.movementType == 1;
    }

    @Override
    public boolean m_142535_(float distance, float damageMultiplier, DamageSource source) {
        if (!this.canFly()) {
            return super.m_142535_(distance, damageMultiplier, source);
        }
        return false;
    }

    protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        if (!this.canFly()) {
            super.m_7840_(y, onGroundIn, state, pos);
        }
    }

    @Override
    public void m_7023_(Vec3 v) {
        if (!this.canFly() || this.m_6084_() && this.m_20160_() && this.ais.mountControl && this.m_6688_() != null) {
            super.m_7023_(v);
            return;
        }
        Vec3 m = this.m_20184_();
        if (!this.m_20069_() && this.ais.movementType == 2) {
            m = new Vec3(0.0, -0.15, 0.0);
            this.m_6478_(MoverType.SELF, m);
        } else if (this.m_20069_() && this.ais.movementType == 1) {
            this.m_19920_(0.02f, v);
            this.m_6478_(MoverType.SELF, m);
            m = this.m_20184_().m_82490_(0.8);
        } else if (this.m_20077_()) {
            this.m_19920_(0.02f, v);
            this.m_6478_(MoverType.SELF, m);
            m = this.m_20184_().m_82490_(0.5);
        } else {
            BlockPos ground = new BlockPos((int)this.m_20185_(), (int)(this.m_20186_() - 1.0), (int)this.m_20189_());
            float f = 0.91f;
            if (this.m_20096_()) {
                f = this.m_9236_().m_8055_(ground).getFriction((LevelReader)this.m_9236_(), ground, (Entity)this) * 0.91f;
            }
            float f1 = 0.16277137f / (f * f * f);
            f = 0.91f;
            if (this.m_20096_()) {
                f = this.m_9236_().m_8055_(ground).getFriction((LevelReader)this.m_9236_(), ground, (Entity)this) * 0.91f;
            }
            this.m_19920_(this.m_20096_() ? 0.1f * f1 : 0.02f, v);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            m = this.m_20184_().m_82490_((double)f);
        }
        this.m_20256_(m);
        this.m_267651_(false);
    }

    public boolean m_6147_() {
        return false;
    }
}

