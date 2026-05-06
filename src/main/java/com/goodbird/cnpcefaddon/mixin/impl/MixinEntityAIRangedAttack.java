package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.common.patch.AdvNpcHumanoidPatch;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.ai.EntityAIRangedAttack;
import noppes.npcs.entity.EntityNPCInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = EntityAIRangedAttack.class, remap = false)
public class MixinEntityAIRangedAttack {
    
    @Shadow @Final
    private EntityNPCInterface npc;
    
    private boolean isCurrentlyUsingItem = false;
    
    /**
     * Emulate vanilla bow mechanics by starting item use when CNPC has a target.
     * Vanilla skeletons call startUsingItem() which sets isUsingItem() = true,
     * allowing Epic Fight to detect aiming and show AIM/RELOAD animations.
     * 
     * CNPCs normally fire bows instantly without entering item-use state.
     * This inject makes them behave like vanilla ranged mobs for Epic Fight.
     * 
     * Keeps aiming active throughout the entire attack cycle including delays
     * between shots, so if "Aim While Shooting" is enabled, the CNPC always aims
     * while a target is present.
     */
    @Inject(method = "m_8037_", at = @At("HEAD"), cancellable = true)
    private void onTickStart(CallbackInfo ci) {
        if (AdvNpcHumanoidPatch.cNPC_EpicFight_Addon$shouldSuppressNativeRangedAi(this.npc)) {
            if (isCurrentlyUsingItem) {
                this.npc.stopUsingItem();
                isCurrentlyUsingItem = false;
            }
            ci.cancel();
            return;
        }

        // Only apply to CNPCs with "Aim While Shooting" enabled
        if (!this.npc.stats.ranged.getHasAimAnimation()) {
            return;
        }
        
        // Keep aiming whenever there's a target (regardless of hasFired state)
        // This ensures continuous aiming during the entire attack sequence
        boolean hasTarget = this.npc.getTarget() != null;
        
        if (hasTarget && !isCurrentlyUsingItem) {
            // Start using bow/crossbow (emulates vanilla skeleton behavior)
            ItemStack mainHand = this.npc.getMainHandItem();
            if (mainHand.getItem() instanceof BowItem || mainHand.getItem() instanceof CrossbowItem) {
                this.npc.startUsingItem(InteractionHand.MAIN_HAND);
                isCurrentlyUsingItem = true;
            }
        } else if (!hasTarget && isCurrentlyUsingItem) {
            // Stop using item when target is lost
            this.npc.stopUsingItem();
            isCurrentlyUsingItem = false;
        }
    }
    
    /**
     * Trigger SHOT animation right before the CNPC fires its ranged attack.
     * Also stops item use after firing (emulates vanilla bow release).
     */
    @Inject(method = "m_8037_", 
            at = @At(value = "INVOKE", 
                     target = "Lnoppes/npcs/entity/EntityNPCInterface;m_6504_(Lnet/minecraft/world/entity/LivingEntity;F)V"))
    private void onFireRangedAttack(CallbackInfo ci) {
        if (AdvNpcHumanoidPatch.cNPC_EpicFight_Addon$shouldSuppressNativeRangedAi(this.npc)) {
            return;
        }

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(this.npc, LivingEntityPatch.class);
        
        if (patch != null) {
            // Play SHOT animation (bow firing)
            patch.playShootingAnimation();
        }
        
        // Stop using item after firing (bow is released)
        if (isCurrentlyUsingItem) {
            this.npc.stopUsingItem();
            isCurrentlyUsingItem = false;
        }
    }
    
    /**
     * Shadow method to check if CNPC has completed attack cycle
     */
    @Shadow
    public boolean hasFired() {
        throw new AssertionError("Mixin failed to shadow hasFired()");
    }
}
