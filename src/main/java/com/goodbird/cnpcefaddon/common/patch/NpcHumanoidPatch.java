package com.goodbird.cnpcefaddon.common.patch;

import com.goodbird.cnpcefaddon.common.provider.NpcHumanoidPatchProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.main.EpicFightSharedConstants;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.CustomHumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.Faction;

public class NpcHumanoidPatch<T extends PathfinderMob> extends CustomHumanoidMobPatch<T> implements INpcPatch {
    NpcHumanoidPatchProvider provider;

    public NpcHumanoidPatch(Faction faction, NpcHumanoidPatchProvider provider) {
        super(faction, provider);
        this.provider = provider;
    }

    public void onConstructed(T entityIn) {
        this.original = entityIn;
        this.armature = provider.armature.deepCopy();
        this.animator = EpicFightSharedConstants.getAnimator(this);
        if (!this.original.getEntityData().hasItem(STUN_SHIELD)) {
            this.original.getEntityData().define(STUN_SHIELD, Float.valueOf(0.0F));
            this.original.getEntityData().define(MAX_STUN_SHIELD, Float.valueOf(0.0F));
            this.original.getEntityData().define(EXECUTION_RESISTANCE, Integer.valueOf(1));
            this.original.getEntityData().define(AIRBORNE, Boolean.valueOf(false));
        }
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