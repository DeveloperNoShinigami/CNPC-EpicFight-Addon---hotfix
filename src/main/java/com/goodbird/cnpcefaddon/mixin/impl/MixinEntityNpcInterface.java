package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.common.patch.AdvNpcHumanoidPatch;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import noppes.npcs.entity.EntityNPCInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(EntityNPCInterface.class)
public class MixinEntityNpcInterface extends PathfinderMob {

    protected MixinEntityNpcInterface(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Inject(method = "addRegularEntries", at = @At("TAIL"), remap = false)
    public void addRegularEntries(CallbackInfo ci) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
        if (patch instanceof HumanoidMobPatch) {
            Object humanoidPatch = patch;
            if (humanoidPatch instanceof AdvNpcHumanoidPatch<?>) {
                ((AdvNpcHumanoidPatch<?>) humanoidPatch).cNPC_EpicFight_Addon$completeDeferredServerJoinInitialization();
            }
            ((HumanoidMobPatch<?>) patch)
                    .setAIAsInfantry(AdvNpcHumanoidPatch.cNPC_EpicFight_Addon$hasNativeRangedWeaponForEpicFight(this));
            // World-load path: after deferred onJoinWorld completes, sync the finalized
            // EF patch to the client so living/weapon motions are applied correctly.
            if (!this.level().isClientSide()) {
                ((EntityNPCInterface)(Object)this).updateClient();
            }
        }
    }
}
