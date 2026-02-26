/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.entity;

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

public class EntityNPCGolem
extends EntityNPCInterface {
    public EntityNPCGolem(EntityType<? extends EntityNPCInterface> type, Level world) {
        super(type, world);
        this.display.setSkinTexture("customnpcs:textures/entity/golem/irongolem.png");
        this.baseSize = new EntityDimensions(1.4f, 2.5f, false);
    }

    @Override
    public EntityDimensions m_6972_(Pose pos) {
        this.currentAnimation = (Integer)this.f_19804_.m_135370_(Animation);
        if (this.currentAnimation == 2) {
            return new EntityDimensions(0.5f, 0.5f, false);
        }
        if (this.currentAnimation == 1) {
            return new EntityDimensions(1.4f, 2.0f, false);
        }
        return new EntityDimensions(1.4f, 2.5f, false);
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
            data.setEntity(ForgeRegistries.ENTITY_TYPES.getKey(CustomEntities.entityNPCGolem));
            this.m_9236_().m_7967_((Entity)npc);
        }
        super.m_8119_();
    }
}

