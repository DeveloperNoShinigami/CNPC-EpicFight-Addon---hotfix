package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.common.patch.AdvNpcHumanoidPatch;
import com.goodbird.cnpcefaddon.common.compatibility.CnpcEquipmentBridge;
import com.goodbird.cnpcefaddon.common.compatibility.CnpcEpicFightCombatBridge;
import com.goodbird.cnpcefaddon.common.NpcPatchReloadListener;
import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import com.goodbird.cnpcefaddon.common.network.NetworkHandler;
import com.goodbird.cnpcefaddon.common.network.SPNpcPatchSelection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import noppes.npcs.entity.EntityNPCInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
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
            if (humanoidPatch instanceof AdvNpcHumanoidPatch<?> advancedPatch) {
                // Never mutate GoalSelector from the CNPC entity callback. EFI
                // drains this request at the next server-tick START boundary.
                advancedPatch.requestAiRebuild();
            } else if (!this.level().isClientSide()) {
                HumanoidMobPatch<?> humanoid = (HumanoidMobPatch<?>) patch;
                this.level().getServer().execute(() -> {
                    if (!this.isRemoved() && this.isAlive()) {
                        humanoid.setAIAsInfantry(
                                AdvNpcHumanoidPatch.cNPC_EpicFight_Addon$hasNativeRangedWeaponForEpicFight(this));
                    }
                });
            }
            // World-load path: after deferred onJoinWorld completes, sync the finalized
            // EF patch to the client so living/weapon motions are applied correctly.
            if (!this.level().isClientSide()) {
                ((EntityNPCInterface)(Object)this).updateClient();
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    private void cnpcefaddon$syncAuthoritativeEquipment(CallbackInfo ci) {
        if (!this.level().isClientSide()) {
            EntityNPCInterface npc = (EntityNPCInterface) (Object) this;
            boolean selectionChanged = NpcPatchReloadListener.branchPatchProvider != null
                    && NpcPatchReloadListener.branchPatchProvider.observeSelection(npc);
            if (selectionChanged && npc.display instanceof IDataDisplay display) {
                display.refreshEFPatch();
                ResourceLocation selectedKey = NpcPatchReloadListener.branchPatchProvider.resolveSelectedKey(npc);
                NetworkHandler.sendNearby(npc, new SPNpcPatchSelection(npc.getId(), selectedKey, selectedKey != null));
            }
            if (CnpcEquipmentBridge.observe(npc)) {
                CnpcEpicFightCombatBridge.resync(npc);
                npc.updateClient();
            }
        }
    }

    @Inject(method = "doHurtTarget(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), remap = false, cancellable = true)
    private void cnpcefaddon$beginNativeEpicFightMelee(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (!this.level().isClientSide() && !CnpcEpicFightCombatBridge.isRoutingNativeAttack()) {
            if (CnpcEpicFightCombatBridge.attackWithEpicFight((EntityNPCInterface) (Object) this, target)) {
                cir.setReturnValue(true);
            }
        }
    }

    @ModifyVariable(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"), argsOnly = true, ordinal = 0, remap = false)
    private DamageSource cnpcefaddon$useActiveEpicFightSource(DamageSource incoming) {
        return CnpcEpicFightCombatBridge.replaceActiveEpicFightSource((Entity) (Object) this, incoming);
    }

    @Inject(method = "doHurtTarget(Lnet/minecraft/world/entity/Entity;)Z", at = @At("RETURN"), remap = false)
    private void cnpcefaddon$endNativeEpicFightMelee(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (!this.level().isClientSide()) {
            CnpcEpicFightCombatBridge.endNativeMelee((EntityNPCInterface) (Object) this);
        }
    }

    /**
     * CNPC movement can reach Entity.travel after the Epic Fight living-tick
     * callback. Enforce the vanilla one-block step height at the actual
     * movement boundary so navigation, strafing, and dodge/backstep movement
     * all see the same value.
     */
    @Inject(method = "travel", at = @At("HEAD"))
    private void cnpcefaddon$enforceOneBlockStepHeight(Vec3 travelVector, CallbackInfo ci) {
        this.setMaxUpStep(1.5F);
    }

}
