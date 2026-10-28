package com.goodbird.cnpcefaddon.common.patch;

import com.goodbird.cnpcefaddon.common.provider.NpcHumanoidPatchProvider;
import com.goodbird.cnpcefaddon.common.compatibility.CnpcFactionBridge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import com.nameless.indestructible.world.capability.TaczCompat;
import com.nameless.indestructible.world.capability.EpicArsenalMobMotionBridge;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.CustomHumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.RangedWeaponCapability;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;
import yesman.epicfight.world.damagesource.StunType;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;

public class NpcHumanoidPatch<T extends PathfinderMob> extends CustomHumanoidMobPatch<T> implements INpcPatch {
    NpcHumanoidPatchProvider provider;
    private boolean cNPC_EpicFight_Addon$nativeMeleeDamage;
    private boolean cNPC_EpicFight_Addon$combatGoalsInitialized;
    private CombatBehaviors<HumanoidMobPatch<?>> cNPC_EpicFight_Addon$combatBehaviors;

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
    public void onJoinWorld(T entityIn, EntityJoinLevelEvent event) {
        super.onJoinWorld(entityIn, event);

        // MobPatch.onJoinWorld only removes incompatible goals. For a normal
        // CustomNPC patch, the weapon is often already equipped before EFI
        // constructs the patch, so HumanoidMobPatch.updateHeldItem is never
        // called and Epic Fight's AnimatedAttackGoal is never installed.
        // CNPC can then attack through its native path while the client stays
        // in ordinary living motion. Install the same EFI combat goal that
        // vanilla Epic Fight humanoid mobs receive.
        if (!entityIn.level().isClientSide() && !entityIn.isNoAi()) {
            // Build the held-item capability/motion map first.  The combat
            // builder is derived from that capability; asking for it before
            // the CNPC equipment has been published permanently skipped goal
            // installation for this patch instance.
            this.modifyLivingMotionByCurrentItem(false);
            this.cNPC_EpicFight_Addon$ensureCombatGoals(entityIn);
        }
    }

    public void cNPC_EpicFight_Addon$ensureEpicFightCombatGoal() {
        this.cNPC_EpicFight_Addon$ensureCombatGoals(this.original);
    }

    private void cNPC_EpicFight_Addon$ensureCombatGoals(T entity) {
        if (this.cNPC_EpicFight_Addon$combatGoalsInitialized || entity.level().isClientSide()
                || entity.isNoAi()) {
            return;
        }

        // CNPC can publish equipment after the first EntityJoinLevelEvent.
        // Build the capability map before resolving the combat builder so a
        // transiently empty held-item map cannot permanently skip the EFI goal.
        CombatBehaviors.Builder<HumanoidMobPatch<?>> builder = this.getHoldingItemWeaponMotionBuilder();
        if (builder == null) {
            this.modifyLivingMotionByCurrentItem(false);
            builder = this.getHoldingItemWeaponMotionBuilder();
        }
        if (builder == null) {
            return;
        }

        this.setAIAsInfantry(entity.getMainHandItem().getItem() instanceof ProjectileWeaponItem
                || TaczCompat.isTaczGun(entity.getMainHandItem()));
        // CNPC may register its own attack goal from addRegularEntries after
        // EFI constructs the patch. Give the EFI animated goal a stronger
        // priority so the NPC cannot deal a native hit while the Epic Fight
        // behavior animation is skipped.
        this.original.goalSelector.getAvailableGoals().stream()
                .map(net.minecraft.world.entity.ai.goal.WrappedGoal::getGoal)
                .filter(goal -> goal instanceof AnimatedAttackGoal)
                .toList()
                .forEach(this.original.goalSelector::removeGoal);
        // Epic Fight's normal humanoid path always pairs the animated attack
        // goal with its target-chasing goal. The previous CNPC bridge installed
        // only AnimatedAttackGoal, leaving the native movement/attack lifecycle
        // in control and skipping the synchronized attack animation.
        this.cNPC_EpicFight_Addon$combatBehaviors = builder.build(this);
        this.original.goalSelector.addGoal(0, new AnimatedAttackGoal<>(this, this.cNPC_EpicFight_Addon$combatBehaviors));
        this.original.goalSelector.addGoal(1,
                new TargetChasingGoal(this, this.getOriginal(), this.provider.getChasingSpeed(), true));
        this.cNPC_EpicFight_Addon$combatGoalsInitialized = true;
        CNPCEpicFightAddon.LOGGER.info(
                "[CNPC EFI diagnostic] normal Epic Fight combat goal installed: entity={} item={}",
                entity.getId(), entity.getMainHandItem().getItem());
    }

    /**
     * CNPC's native melee callback can bypass AnimatedAttackGoal. Run the same
     * configured EFI behavior once before the damage bridge so its animation
     * action sends SPAnimatorControl to tracking clients.
     */
    public boolean cNPC_EpicFight_Addon$triggerConfiguredAttackAnimation() {
        if (this.cNPC_EpicFight_Addon$combatBehaviors == null
                || this.original.getMainHandItem().isEmpty()
                || this.original.getMainHandItem().getItem() instanceof ProjectileWeaponItem
                || this.original.getTarget() == null
                || !this.original.getTarget().isAlive()) {
            CNPCEpicFightAddon.LOGGER.debug(
                    "[CNPC EFI diagnostic] skipped melee behavior selection: entity={} target={} alive={}",
                    this.original.getId(), this.original.getTarget(),
                    this.original.getTarget() != null && this.original.getTarget().isAlive());
            return false;
        }

        CombatBehaviors.Behavior<HumanoidMobPatch<?>> behavior = this.cNPC_EpicFight_Addon$combatBehaviors.hasActivatedMove()
                ? this.cNPC_EpicFight_Addon$combatBehaviors.tryProceed()
                : this.cNPC_EpicFight_Addon$combatBehaviors.selectRandomBehaviorSeries();
        if (behavior == null) {
            return false;
        }
        behavior.execute(this);
        return true;
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
        InteractionHand gunHand = TaczCompat.findGunHand(this.original, this.original.getUsedItemHand());
        if (gunHand != null) {
            if (TaczCompat.isReloading(this.original)) {
                this.currentCompositeMotion = LivingMotions.RELOAD;
            } else if (TaczCompat.isShooting(this.original)) {
                this.currentCompositeMotion = LivingMotions.SHOT;
            } else if (TaczCompat.isAiming(this.original)) {
                this.currentCompositeMotion = LivingMotions.AIM;
            }
        }
    }

    @Override
    public void serverTick(LivingEvent.LivingTickEvent event) {
        super.serverTick(event);

        // CNPC may publish its equipment capability after the EFI patch is
        // constructed. Retry until the selected weapon behavior is available;
        // this is one-time and does not rebuild the goal every tick.
        this.cNPC_EpicFight_Addon$ensureCombatGoals(this.original);

        InteractionHand gunHand = TaczCompat.findGunHand(this.original, null);
        net.minecraft.world.entity.LivingEntity target = this.original.getTarget();
        if (gunHand == null || target == null || !target.isAlive()) {
            if (gunHand != null && (target == null || !target.isAlive())) {
                TaczCompat.stopAiming(this.original);
            }
            return;
        }

        // CNPC's ordinary humanoid patch has no advanced behavior loop to
        // re-enter every tick. Keep the native TACZ operator aimed at its
        // current target so Epic Arsenal can select AIM/SHOT naturally.
        if (!TaczCompat.isReloading(this.original)) {
            this.original.lookAt(target, 30.0F, 30.0F);
            if (!TaczCompat.isAiming(this.original)) {
                TaczCompat.tryAim(this.original, gunHand);
            }
        }
    }

    public void cNPC_EpicFight_Addon$resyncHeldItemFromCnpc() {
        // Equipment/provider refresh must not rebuild the combat goal or force a
        // tracking reset. Those operations interrupt the active client animator.
        // Rebuild the held-item map only when its contents actually changed.
        this.modifyLivingMotionByCurrentItem(false);
        EpicArsenalMobMotionBridge.resyncNativeGunMotions(this);
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
        CnpcFactionBridge.Relation relation = CnpcFactionBridge.relation(this.original, target);
        if (relation == CnpcFactionBridge.Relation.ALLIED) {
            return true;
        }
        if (relation == CnpcFactionBridge.Relation.HOSTILE) {
            return false;
        }
        return super.isTargetInvulnerable(target);
    }
}
