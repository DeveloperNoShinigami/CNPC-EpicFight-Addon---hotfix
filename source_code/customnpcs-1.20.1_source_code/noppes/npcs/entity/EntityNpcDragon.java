/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomEntities;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityNpcDragon
extends EntityNPCInterface {
    private EntityDimensions size = new EntityDimensions(1.8f, 1.4f, false);
    public double[][] field_40162_d = new double[64][3];
    public int field_40164_e = -1;
    public float prevAnimTime = 0.0f;
    public float animTime = 0.0f;
    public int field_40178_aA = 0;
    public boolean isFlying = false;
    private boolean exploded = false;

    public EntityNpcDragon(EntityType<? extends EntityNPCInterface> type, Level world) {
        super(type, world);
        this.scaleX = 0.4f;
        this.scaleY = 0.4f;
        this.scaleZ = 0.4f;
        this.display.setSkinTexture("customnpcs:textures/entity/dragon/blackdragon.png");
    }

    public double m_6048_() {
        return 1.1;
    }

    public double[] getMovementOffsets(int i, float f) {
        double d1;
        f = 1.0f - f;
        int j = this.field_40164_e - i * 1 & 0x3F;
        int k = this.field_40164_e - i * 1 - 1 & 0x3F;
        double[] ad = new double[3];
        double d = this.field_40162_d[j][0];
        for (d1 = this.field_40162_d[k][0] - d; d1 < -180.0; d1 += 360.0) {
        }
        while (d1 >= 180.0) {
            d1 -= 360.0;
        }
        ad[0] = d + d1 * (double)f;
        d = this.field_40162_d[j][1];
        d1 = this.field_40162_d[k][1] - d;
        ad[1] = d + d1 * (double)f;
        ad[2] = this.field_40162_d[j][2] + (this.field_40162_d[k][2] - this.field_40162_d[j][2]) * (double)f;
        return ad;
    }

    @Override
    public void m_8119_() {
        this.m_146870_();
        this.m_21557_(true);
        if (!this.m_9236_().f_46443_) {
            CompoundTag compound = new CompoundTag();
            this.m_7380_(compound);
            EntityCustomNpc npc = new EntityCustomNpc(CustomEntities.entityCustomNpc, this.m_9236_());
            npc.m_7378_(compound);
            ModelData data = npc.modelData;
            data.setEntity(ForgeRegistries.ENTITY_TYPES.getKey(CustomEntities.entityNpcDragon));
            this.m_9236_().m_7967_((Entity)npc);
        }
        this.prevAnimTime = this.animTime;
        this.exploded = false;
        float f1 = 0.045f;
        this.animTime += (f1 *= (float)Math.pow(2.0, this.m_20184_().f_82480_)) * 0.5f;
        super.m_8119_();
    }

    @Override
    public void m_8107_() {
        this.prevAnimTime = this.animTime;
        if (this.m_9236_().f_46443_ && this.m_21223_() <= 0.0f) {
            if (!this.exploded) {
                this.exploded = true;
                float f = (this.f_19796_.m_188501_() - 0.5f) * 8.0f;
                float f2 = (this.f_19796_.m_188501_() - 0.5f) * 4.0f;
                float f4 = (this.f_19796_.m_188501_() - 0.5f) * 8.0f;
                this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123813_, this.m_20185_() + (double)f, this.m_20186_() + 2.0 + (double)f2, this.m_20189_() + (double)f4, 0.0, 0.0, 0.0);
            }
        } else {
            this.exploded = false;
            float f1 = 0.045f;
            this.animTime += (f1 *= (float)Math.pow(2.0, this.m_20184_().f_82480_)) * 0.5f;
        }
        super.m_8107_();
    }

    @Override
    public EntityDimensions m_6972_(Pose pos) {
        return this.size;
    }
}

