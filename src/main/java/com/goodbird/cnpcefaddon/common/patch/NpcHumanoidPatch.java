package com.goodbird.cnpcefaddon.common.patch;

import com.goodbird.cnpcefaddon.common.provider.NpcHumanoidPatchProvider;
import com.goodbird.cnpcefaddon.common.compatibility.CnpcEpicFightCombatBridge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.CustomHumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.RangedWeaponCapability;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;
import yesman.epicfight.world.damagesource.StunType;
import net.minecraft.world.InteractionHand;

public class NpcHumanoidPatch<T extends PathfinderMob> extends CustomHumanoidMobPatch<T> implements INpcPatch {
    NpcHumanoidPatchProvider provider;
    private boolean cNPC_EpicFight_Addon$nativeMeleeDamage;

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
        // Epic Fight's 20.14.x ranged path is stateful: it derives AIM, SHOT, and
        // RELOAD from item-use/charge state. CNPC's native ranged AI does not enter
        // that path consistently, so select it whenever the active Epic Fight item
        // capability is ranged. Melee NPCs retain the normal humanoid path.
        CapabilityItem mainHand = this.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        if (mainHand instanceof RangedWeaponCapability
                || this.original.isUsingItem() && this.getHoldingItemCapability(this.original.getUsedItemHand()) instanceof RangedWeaponCapability) {
            super.commonAggressiveRangedMobUpdateMotion(considerInaction);
        } else {
            super.updateMotion(considerInaction);
        }
    }

    public void cNPC_EpicFight_Addon$resyncHeldItemFromCnpc() {
        this.initAI();
        this.modifyLivingMotionByCurrentItem(true);
    }

    public void cNPC_EpicFight_Addon$beginNativeMeleeDamage() {
        if (this.epicFightDamageSource == null) {
            this.epicFightDamageSource = EpicFightDamageSources.mobAttack(this.original)
                    .setUsedItem(this.original.getMainHandItem())
                    .setBaseImpact(this.getImpact(InteractionHand.MAIN_HAND))
                    .setStunType(StunType.SHORT);
            this.cNPC_EpicFight_Addon$nativeMeleeDamage = true;
        }
    }

    public void cNPC_EpicFight_Addon$endNativeMeleeDamage() {
        if (this.cNPC_EpicFight_Addon$nativeMeleeDamage) {
            this.epicFightDamageSource = null;
            this.cNPC_EpicFight_Addon$nativeMeleeDamage = false;
        }
    }

    @Override
    public boolean isTargetInvulnerable(Entity target) {
        if (CnpcEpicFightCombatBridge.cnpcFactionBlocks(this.original, target)) {
            return true;
        }
        if (CnpcEpicFightCombatBridge.cnpcFactionAllows(this.original, target)) {
            return false;
        }
        return super.isTargetInvulnerable(target);
    }
}
