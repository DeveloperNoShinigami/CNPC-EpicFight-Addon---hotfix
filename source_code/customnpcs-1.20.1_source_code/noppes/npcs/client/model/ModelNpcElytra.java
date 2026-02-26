/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  net.minecraft.client.model.AgeableListModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.ModelData;
import noppes.npcs.entity.EntityCustomNpc;

public class ModelNpcElytra<T extends LivingEntity>
extends AgeableListModel<T> {
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public ModelNpcElytra(ModelPart p_170538_) {
        this.leftWing = p_170538_.m_171324_("left_wing");
        this.rightWing = p_170538_.m_171324_("right_wing");
    }

    protected Iterable<ModelPart> m_5607_() {
        return ImmutableList.of();
    }

    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.leftWing, (Object)this.rightWing);
    }

    public void setupAnim(T p_102544_, float p_102545_, float p_102546_, float p_102547_, float p_102548_, float p_102549_) {
        float $$6 = 0.2617994f;
        float $$7 = -0.2617994f;
        float $$8 = 0.0f;
        float $$9 = 0.0f;
        if (p_102544_.m_21255_()) {
            float $$10 = 1.0f;
            Vec3 $$11 = p_102544_.m_20184_();
            if ($$11.f_82480_ < 0.0) {
                Vec3 $$12 = $$11.m_82541_();
                $$10 = 1.0f - (float)Math.pow(-$$12.f_82480_, 1.5);
            }
            $$6 = $$10 * 0.34906584f + (1.0f - $$10) * $$6;
            $$7 = $$10 * -1.5707964f + (1.0f - $$10) * $$7;
        } else if (p_102544_.m_6047_()) {
            $$6 = 0.6981317f;
            $$7 = -0.7853982f;
            $$8 = 3.0f;
            $$9 = 0.08726646f;
        }
        this.leftWing.f_104201_ = $$8;
        if (p_102544_ instanceof EntityCustomNpc) {
            ModelData $$13 = ((EntityCustomNpc)((Object)p_102544_)).modelData;
            $$13.elytraRotX += ($$6 - $$13.elytraRotX) * 0.1f;
            $$13.elytraRotY += ($$9 - $$13.elytraRotY) * 0.1f;
            $$13.elytraRotZ += ($$7 - $$13.elytraRotZ) * 0.1f;
            this.leftWing.f_104203_ = $$13.elytraRotX;
            this.leftWing.f_104204_ = $$13.elytraRotY;
            this.leftWing.f_104205_ = $$13.elytraRotZ;
        } else {
            this.leftWing.f_104203_ = $$6;
            this.leftWing.f_104205_ = $$7;
            this.leftWing.f_104204_ = $$9;
        }
        this.rightWing.f_104204_ = -this.leftWing.f_104204_;
        this.rightWing.f_104201_ = this.leftWing.f_104201_;
        this.rightWing.f_104203_ = this.leftWing.f_104203_;
        this.rightWing.f_104205_ = -this.leftWing.f_104205_;
    }
}

