package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import com.goodbird.cnpcefaddon.mixin.IMixinCapabilityDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.data.DataDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.provider.EntityPatchProvider;

@Mixin(value = DataDisplay.class, priority = 1001)
public class MixinDataDisplay implements IDataDisplay {
    @Shadow(remap = false)
    EntityNPCInterface npc;
    @Unique
    private ResourceLocation cNPC_EpicFight_Addon$efModelResLoc = null;

    @Inject(method = "save", at = @At("HEAD"), remap = false)
    public void writeToNBT(CompoundTag nbttagcompound, CallbackInfoReturnable<CompoundTag> cir) {
        if (hasEFModel())
            nbttagcompound.putString("efModel", cNPC_EpicFight_Addon$efModelResLoc.toString());
    }

    @Inject(method = "readToNBT", at = @At("HEAD"), remap = false)
    public void readFromNBT(CompoundTag nbttagcompound, CallbackInfo ci) {
        if (nbttagcompound.contains("efModel")) {
            cNPC_EpicFight_Addon$efModelResLoc = new ResourceLocation(nbttagcompound.getString("efModel"));
            // On the client during readSpawnData: EF's AttachCapabilitiesEvent has already
            // fired without the efModel set, so the entity has a NullPatch. We must replace
            // it with the real patch, but we can't call updateModelCap() synchronously here
            // because the entity's position in the level may not be finalised yet.
            // Defer via execute() so it runs after the current tick completes and the entity
            // is fully in the world. NpcHumanoidPatch.initAnimator seeds zombie fallback
            // animations so ClientAnimator.postInit() never NPEs even on first construction.
            if (cNPC_EpicFight_Addon$isClientReadingSpawnData()) {
                Minecraft.getInstance().execute(() -> {
                    if (!npc.isRemoved()) {
                        cNPC_EpicFight_Addon$updateModelCap();
                    }
                });
            } else {
                cNPC_EpicFight_Addon$updateModelCap();
                // GUI-save / direct-sync path: entity is already live on the server.
                // Push the updated EF patch to clients so living/weapon motions apply immediately.
                if (!npc.level().isClientSide() && npc.isAddedToWorld()) {
                    npc.updateClient();
                }
            }
            if (npc.isKilled()) {
                LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(npc, LivingEntityPatch.class);
                if (patch != null) {
                    patch.onDeath(new LivingDeathEvent(npc, npc.level().damageSources().generic()));
                }
            }
        }
    }

    @Override
    public void setEFModel(ResourceLocation modelPath, boolean server) {
        cNPC_EpicFight_Addon$efModelResLoc = modelPath;
        if (server) {
            cNPC_EpicFight_Addon$updateModelCap();
            npc.updateClient();
        }
    }

    @Unique
    public ResourceLocation getEFModel() {
        return cNPC_EpicFight_Addon$efModelResLoc;
    }

    @Unique
    public boolean hasEFModel() {
        return cNPC_EpicFight_Addon$efModelResLoc != null;
    }

    @Unique
    private void cNPC_EpicFight_Addon$updateModelCap() {
        ICapabilityProvider[] caps = ((IMixinCapabilityDispatcher) (Object) ((com.goodbird.cnpcefaddon.mixin.impl.MixinCapabilityProvider) (Object) npc)
                .invokeGetCapabilities()).getCaps();
        EntityPatchProvider newProvider = new EntityPatchProvider(npc);
        if (newProvider.get() == null)
            return;
        ((EntityPatch) newProvider.get()).onConstructed(npc);
        ((EntityPatch) newProvider.get()).onJoinWorld(npc, new EntityJoinLevelEvent(npc, npc.level()));
        if (newProvider.hasCapability()) {
            boolean hasFoundAny = false;
            for (int i = 0; i < caps.length; i++) {
                if (caps[i] instanceof EntityPatchProvider) {
                    caps[i] = newProvider;
                    hasFoundAny = true;
                    break;
                }
            }
            if (!hasFoundAny) {
                ICapabilityProvider[] newCaps = new ICapabilityProvider[caps.length + 1];
                System.arraycopy(caps, 0, newCaps, 0, caps.length);
                newCaps[caps.length] = newProvider;
                ((IMixinCapabilityDispatcher) (Object) ((MixinCapabilityProvider) npc).invokeGetCapabilities())
                        .setCaps(newCaps);
            }
        } // TODO remove one
    }

    @Unique
    private boolean cNPC_EpicFight_Addon$isClientReadingSpawnData() {
        if (!npc.level().isClientSide()) {
            return false;
        }
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if ("noppes.npcs.entity.EntityNPCInterface".equals(element.getClassName())
                    && "readSpawnData".equals(element.getMethodName())) {
                return true;
            }
        }
        return false;
    }
}
