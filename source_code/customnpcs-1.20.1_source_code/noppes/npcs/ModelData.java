/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs;

import java.lang.reflect.Method;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.ModelDataShared;
import noppes.npcs.controllers.CobblemonHelper;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.common.util.LogWriter;

public class ModelData
extends ModelDataShared {
    public boolean simpleRender = false;
    public float elytraRotX;
    public float elytraRotY;
    public float elytraRotZ;
    public EntityCustomNpc npc;

    public ModelData(EntityCustomNpc npc) {
        this.npc = npc;
    }

    public LivingEntity getEntity(EntityNPCInterface npc) {
        if (!this.hasEntity()) {
            return null;
        }
        if (this.entity == null) {
            try {
                this.entity = (LivingEntity)((EntityType)ForgeRegistries.ENTITY_TYPES.getValue(this.getEntityName())).m_20615_(npc.m_9236_());
                CompoundTag comp = new CompoundTag();
                this.entity.m_7380_(comp);
                if (PixelmonHelper.isPixelmon((Entity)this.entity) && !this.extra.m_128441_("Name")) {
                    this.extra.m_128359_("Name", "abra");
                }
                comp = comp.m_128391_(this.extra);
                try {
                    this.entity.m_7378_(comp);
                    if (PixelmonHelper.isPixelmon((Entity)this.entity)) {
                        PixelmonHelper.initEntity(this.entity, this.extra.m_128461_("Name"));
                    }
                    if (CobblemonHelper.isPokemon((Entity)this.entity)) {
                        CobblemonHelper.setType((Entity)this.entity, new ResourceLocation(this.extra.m_128461_("CobblemonModel")));
                    }
                }
                catch (Exception e) {
                    LogWriter.except(e);
                }
                this.entity.m_20331_(true);
                this.entity.m_21051_(Attributes.f_22276_).m_22100_((double)npc.m_21233_());
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    this.entity.m_8061_(slot, npc.m_6844_(slot));
                }
            }
            catch (Exception e) {
                LogWriter.except(e);
            }
        }
        return this.entity;
    }

    public ModelData copy() {
        ModelData data = new ModelData(this.npc);
        data.load(this.save());
        return data;
    }

    @Override
    public CompoundTag save() {
        CompoundTag compound = super.save();
        compound.m_128379_("SimpleRender", this.simpleRender);
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.simpleRender = compound.m_128471_("SimpleRender");
    }

    public void setExtra(LivingEntity entity, String key, String value) {
        if ((key = key.toLowerCase()).equals("breed") && entity.m_20078_().equals("tgvstyle.Dog")) {
            try {
                Method method = entity.getClass().getMethod("getBreedID", new Class[0]);
                Enum breed = (Enum)method.invoke(entity, new Object[0]);
                method = entity.getClass().getMethod("setBreedID", breed.getClass());
                method.invoke(entity, ((Enum[])breed.getClass().getEnumConstants())[Integer.parseInt(value)]);
                CompoundTag comp = new CompoundTag();
                entity.m_7378_(comp);
                this.extra.m_128359_("EntityData21", comp.m_128461_("EntityData21"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (key.equalsIgnoreCase("name") && PixelmonHelper.isPixelmon((Entity)entity)) {
            this.extra.m_128359_("Name", value);
        }
        if (key.equalsIgnoreCase("cobblemonmodel") && CobblemonHelper.isPokemon((Entity)entity)) {
            this.extra.m_128359_("CobblemonModel", value);
        }
        this.clearEntity();
    }

    @Override
    public LivingEntity getOwner() {
        return this.npc;
    }

    public static ModelData get(EntityCustomNpc npc) {
        return npc.modelData;
    }
}

