/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.client.model;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class ModelClassicPlayer<T extends LivingEntity>
extends PlayerModel<T> {
    public ModelClassicPlayer(ModelPart p_170821_, float scale) {
        super(p_170821_, false);
    }

    public void m_6973_(T entity, float par1, float limbSwingAmount, float par3, float par4, float par5) {
        super.m_6973_(entity, par1, limbSwingAmount, par3, par4, par5);
        float j = 2.0f;
        if (entity.m_20142_()) {
            j = 1.0f;
        }
        this.f_102811_.f_104203_ += Mth.m_14089_((float)(par1 * 0.6662f + (float)Math.PI)) * j * limbSwingAmount;
        this.f_102812_.f_104203_ += Mth.m_14089_((float)(par1 * 0.6662f)) * j * limbSwingAmount;
        this.f_102812_.f_104205_ += (Mth.m_14089_((float)(par1 * 0.2812f)) - 1.0f) * limbSwingAmount;
        this.f_102811_.f_104205_ += (Mth.m_14089_((float)(par1 * 0.2312f)) + 1.0f) * limbSwingAmount;
        this.f_103374_.f_104203_ = this.f_102812_.f_104203_;
        this.f_103374_.f_104204_ = this.f_102812_.f_104204_;
        this.f_103374_.f_104205_ = this.f_102812_.f_104205_;
        this.f_103375_.f_104203_ = this.f_102811_.f_104203_;
        this.f_103375_.f_104204_ = this.f_102811_.f_104204_;
        this.f_103375_.f_104205_ = this.f_102811_.f_104205_;
    }
}

