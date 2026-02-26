/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityChairMount
extends Entity {
    public EntityChairMount(EntityType type, Level world) {
        super(type, world);
    }

    public double m_6048_() {
        return 0.5;
    }

    protected void m_8097_() {
    }

    public void m_6075_() {
        super.m_6075_();
        if (this.m_9236_() != null && !this.m_9236_().f_46443_ && this.m_20197_().isEmpty()) {
            this.m_146870_();
        }
    }

    public boolean m_6673_(DamageSource source) {
        return true;
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        return new ClientboundAddEntityPacket((Entity)this);
    }

    public boolean m_20145_() {
        return true;
    }

    public void m_6478_(MoverType type, Vec3 vec) {
    }

    public void m_20258_(CompoundTag tagCompound) {
    }

    protected void m_7378_(CompoundTag compound) {
    }

    protected void m_7380_(CompoundTag compound) {
    }

    public CompoundTag m_20240_(CompoundTag tagCompound) {
        return tagCompound;
    }

    public boolean m_5829_() {
        return false;
    }

    public boolean m_6094_() {
        return false;
    }

    public boolean m_142535_(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_6453_(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_, boolean bo) {
        this.m_6034_(p_70056_1_, p_70056_3_, p_70056_5_);
        this.m_19915_(p_70056_7_, p_70056_8_);
    }
}

