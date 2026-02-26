/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.control.MoveControl$Operation
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.entity.EntityNPCInterface;

public class FlyingMoveHelper
extends MoveControl {
    private EntityNPCInterface entity;
    private int courseChangeCooldown;

    public FlyingMoveHelper(EntityNPCInterface entity) {
        super((Mob)entity);
        this.entity = entity;
    }

    public void m_8126_() {
        if (this.f_24981_ == MoveControl.Operation.MOVE_TO && this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown = 4;
            double d0 = this.m_25000_() - this.entity.m_20185_();
            double d1 = this.m_25001_() - this.entity.m_20186_();
            double d2 = this.m_25002_() - this.entity.m_20189_();
            Vec3 vector3d = new Vec3(this.m_25000_() - this.entity.m_20185_(), this.m_25001_() - this.entity.m_20186_(), this.m_25002_() - this.entity.m_20189_());
            double length = vector3d.m_82553_();
            vector3d = vector3d.m_82541_();
            if (length > 0.5 && this.isNotColliding(vector3d, Mth.m_14165_((double)length))) {
                double speed = this.entity.m_21051_(Attributes.f_22279_).m_22135_() / 2.5;
                if (length < 3.0 && speed > (double)0.1f) {
                    speed = 0.1f;
                }
                Vec3 m = this.entity.m_20184_().m_82549_(vector3d.m_82490_(speed));
                this.entity.m_20256_(m);
                this.entity.m_146922_(-((float)Math.atan2(m.f_82479_, m.f_82481_)) * 180.0f / (float)Math.PI);
                this.entity.f_20883_ = this.entity.m_146908_();
            } else {
                this.f_24981_ = MoveControl.Operation.WAIT;
            }
        }
    }

    private boolean isNotColliding(Vec3 vec, int length) {
        AABB axisalignedbb = this.entity.m_20191_();
        for (int i = 1; i < length; ++i) {
            axisalignedbb = axisalignedbb.m_82383_(vec);
            if (this.entity.m_9236_().m_45756_((Entity)this.entity, axisalignedbb)) continue;
            return false;
        }
        return true;
    }
}

