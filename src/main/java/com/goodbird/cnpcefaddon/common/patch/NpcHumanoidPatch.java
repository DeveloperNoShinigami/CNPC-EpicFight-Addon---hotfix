package com.goodbird.cnpcefaddon.common.patch;

import com.goodbird.cnpcefaddon.common.provider.NpcHumanoidPatchProvider;
import net.minecraft.world.entity.PathfinderMob;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
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

    @Override
    public HumanoidArmature getArmature() {
        return (HumanoidArmature) this.armature;
    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        float scale = ((EntityNPCInterface) this.original).display.getSize() / 5.0F;
        return super.getModelMatrix(partialTicks).scale(scale, scale, scale);
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        // Let the base humanoid patch resolve living motions from held-item capability
        // and humanoid_weapon_motions instead of forcing ranged-only logic.
        super.updateMotion(considerInaction);
    }
}