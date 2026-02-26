/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ModelData;
import noppes.npcs.ModelEyeData;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.client.parts.MpmPartData;
import noppes.npcs.constants.EnumParts;
import noppes.npcs.entity.EntityNPCFlying;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityCustomNpc
extends EntityNPCFlying {
    public ModelData modelData = new ModelData(this);

    public EntityCustomNpc(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
    }

    @Override
    public void m_7378_(CompoundTag compound) {
        if (compound.m_128441_("NpcModelData")) {
            this.modelData.load(compound.m_128469_("NpcModelData"));
        }
        super.m_7378_(compound);
    }

    @Override
    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        compound.m_128365_("NpcModelData", (Tag)this.modelData.save());
    }

    public boolean m_20086_(CompoundTag compound) {
        String s;
        boolean bo = super.m_20086_(compound);
        if (bo && (s = this.m_20078_()).equals("minecraft:customnpcs.customnpc")) {
            compound.m_128359_("id", "customnpcs:customnpc");
        }
        return bo;
    }

    @Override
    public void m_8119_() {
        LivingEntity entity;
        super.m_8119_();
        if (this.isClientSide() && (entity = this.modelData.getEntity(this)) != null) {
            try {
                entity.m_8119_();
            }
            catch (Exception exception) {
                // empty catch block
            }
            EntityUtil.Copy((LivingEntity)this, entity);
        }
        for (MpmPartData pd : this.modelData.mpmParts) {
            if (!(pd instanceof ModelEyeData)) continue;
            ((ModelEyeData)pd).update((LivingEntity)this);
        }
    }

    public boolean m_7998_(Entity par1Entity, boolean force) {
        boolean b = super.m_7998_(par1Entity, force);
        this.m_6210_();
        return b;
    }

    public void m_6210_() {
        LivingEntity entity = this.modelData.getEntity(this);
        if (entity != null) {
            entity.m_6210_();
        }
        super.m_6210_();
    }

    @Override
    public EntityDimensions m_6972_(Pose pos) {
        if (this.modelData == null) {
            return new EntityDimensions(0.6f, 1.8f, false);
        }
        LivingEntity entity = this.modelData.getEntity(this);
        if (entity == null) {
            float height = 1.9f - this.modelData.getBodyY() + (this.modelData.getPartConfig((EnumParts)EnumParts.HEAD).scaleY - 1.0f) / 2.0f;
            if (this.baseSize.f_20378_ != height) {
                this.baseSize = new EntityDimensions(this.baseSize.f_20377_, height, false);
            }
            return super.m_6972_(pos);
        }
        EntityDimensions size = entity.m_6972_(pos);
        if (entity instanceof EntityNPCInterface) {
            return size.m_20388_((float)this.display.getSize() * 0.2f);
        }
        float width = size.f_20377_ / 5.0f * (float)this.display.getSize();
        float height = size.f_20378_ / 5.0f * (float)this.display.getSize();
        if (width < 0.1f) {
            width = 0.1f;
        }
        if (height < 0.1f) {
            height = 0.1f;
        }
        if (this.display.getHitboxState() == 1 || this.isKilled() && this.stats.hideKilledBody) {
            width = 1.0E-5f;
        }
        if ((double)(width / 2.0f) > this.m_9236_().getMaxEntityRadius()) {
            this.m_9236_().increaseMaxEntityRadius((double)(width / 2.0f));
        }
        return new EntityDimensions(width, height, false);
    }

    public double m_6048_() {
        LivingEntity entity = this.modelData.getEntity(this);
        if (entity != null) {
            return entity.m_6048_() / 5.0 * (double)this.display.getSize();
        }
        return super.m_6048_();
    }

    @Override
    protected void m_6138_() {
        Player playerMP;
        if (this.display.getHitboxState() != 0) {
            return;
        }
        if (this.m_9236_().m_5776_() && CustomNpcs.EnableInvisibleNpcs && CustomNpcs.InvisibilityAlgorithm == 2 && !this.display.isVisibleTo(playerMP = CustomNpcs.proxy.getPlayer()) && !playerMP.m_5833_() && playerMP.m_21205_().m_41720_() != CustomItems.wand) {
            return;
        }
        super.m_6138_();
    }
}

