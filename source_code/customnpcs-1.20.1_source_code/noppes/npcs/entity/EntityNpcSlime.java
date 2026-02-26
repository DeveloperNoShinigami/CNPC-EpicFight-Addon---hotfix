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

public class EntityNpcSlime
extends EntityNPCInterface {
    public EntityNpcSlime(EntityType<? extends EntityNPCInterface> type, Level world) {
        super(type, world);
        this.scaleX = 2.0f;
        this.scaleY = 2.0f;
        this.scaleZ = 2.0f;
        this.display.setSkinTexture("customnpcs:textures/entity/slime/slime.png");
        this.baseSize = new EntityDimensions(0.8f, 0.8f, false);
    }

    @Override
    public EntityDimensions m_6972_(Pose pos) {
        return new EntityDimensions(0.8f, 0.8f, false);
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
            data.setEntity(ForgeRegistries.ENTITY_TYPES.getKey(CustomEntities.entityNpcSlime));
            this.m_9236_().m_7967_((Entity)npc);
        }
        super.m_8119_();
    }
}

