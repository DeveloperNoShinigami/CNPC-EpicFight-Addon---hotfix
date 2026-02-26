package com.goodbird.cnpcefaddon.common.patch;

import com.goodbird.cnpcefaddon.common.provider.NpcPatchProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.entity.EntityNPCInterface;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.main.EpicFightSharedConstants;
import yesman.epicfight.world.capabilities.entitypatch.CustomMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.Faction;

public class NpcPatch<T extends PathfinderMob> extends CustomMobPatch<T> implements INpcPatch {
    NpcPatchProvider provider;

    public NpcPatch(Faction faction, NpcPatchProvider provider) {
        super(faction, provider);
        this.provider = provider;
    }

    public void onConstructed(T entityIn) {
        super.onConstructed(entityIn);
        // Override armature with CNPC's unique armature instead of default
        this.armature = provider.armature.deepCopy();
    }

    public OpenMatrix4f getModelMatrix(float partialTicks) {
        float scale = ((EntityNPCInterface) original).display.getSize() / 5f;
        return super.getModelMatrix(partialTicks).scale(scale, scale, scale);
    }

    @Override
    public Armature getArmature() {
        return this.armature;
    }
}