package com.goodbird.cnpcefaddon.common.patch;

import com.goodbird.cnpcefaddon.common.provider.NpcHumanoidPatchProvider;
import net.minecraft.world.entity.PathfinderMob;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
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
    public void initAnimator(Animator animator) {
        // Seed guaranteed-valid fallback animations BEFORE adding provider animations.
        // ClientAnimator.addLivingAnimation silently skips null/empty accessors, so if
        // a custom animation (e.g. from epicfightx) isn't registered yet when this patch
        // is constructed, IDLE would be absent and ClientAnimator.postInit() would NPE.
        // The fallbacks are always valid; provider animations override them if loaded.
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.ZOMBIE_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.ZOMBIE_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.ZOMBIE_CHASE);
        // Now add the real animations from the datapack provider (overrides fallbacks if valid)
        super.initAnimator(animator);
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