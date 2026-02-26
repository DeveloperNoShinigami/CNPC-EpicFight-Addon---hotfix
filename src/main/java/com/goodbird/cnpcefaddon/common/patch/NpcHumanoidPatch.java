package com.goodbird.cnpcefaddon.common.patch;

import com.goodbird.cnpcefaddon.common.provider.NpcHumanoidPatchProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.api.model.Armature;
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
        super.onConstructed(entityIn);
        // Override armature with CNPC's unique armature instead of default
        this.armature = provider.armature.deepCopy();
    }

    public OpenMatrix4f getModelMatrix(float partialTicks) {
        float scale = ((EntityNPCInterface) original).display.getSize() / 5f;
        return super.getModelMatrix(partialTicks).scale(scale, scale, scale);
    }

    @Override
    public HumanoidArmature getArmature() {
        return (HumanoidArmature) this.armature;
    }
}