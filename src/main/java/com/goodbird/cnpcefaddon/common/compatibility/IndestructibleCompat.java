package com.goodbird.cnpcefaddon.common.compatibility;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
import com.goodbird.cnpcefaddon.common.provider.AdvNpcPatchProvider;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.nameless.indestructible.data.AdvancedMobpatchReloader;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener.AbstractMobPatchProvider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.main.EpicFightSharedConstants;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class IndestructibleCompat {
    private IndestructibleCompat() {
    }

    public static boolean isAvailable() {
        return CNPCEpicFightAddon.isIndestructibleCompatLoaded();
    }

    public static CompoundTag filterClientData(CompoundTag tag) {
        return AdvancedMobpatchReloader.filterClientData(tag);
    }

    public static AbstractMobPatchProvider deserializeAdvancedNpcPatchProvider(CompoundTag tag, boolean clientSide,
            ResourceManager resourceManager) {
        boolean disabled = tag.contains("disabled") && tag.getBoolean("disabled");
        if (disabled) {
            return new MobPatchReloadListener.NullPatchProvider();
        }

        AdvNpcPatchProvider provider = new AdvNpcPatchProvider();
        provider.setAttributeValues(AdvancedMobpatchReloader.deserializeAdvancedAttributes(tag.getCompound("attributes")));

        ResourceLocation modelLocation = withAnimModelSuffix(ResourceLocation.parse(tag.getString("model")));
        ResourceLocation armatureLocation = withAnimModelSuffix(ResourceLocation.parse(tag.getString("armature")));

        if (EpicFightSharedConstants.isPhysicalClient()) {
            Meshes.getOrCreate(modelLocation, jsonModelLoader -> jsonModelLoader.loadSkinnedMesh(
                    yesman.epicfight.client.mesh.HumanoidMesh::new)).get();
        }

        Armature armature = Armatures.getOrCreate(armatureLocation, yesman.epicfight.model.armature.HumanoidArmature::new)
                .get();
        provider.setArmature(armature);
        provider.setDefaultAnimations(resolveDefaultAnimations(tag.getCompound("default_livingmotions")));
        provider.setFaction(Faction.ENUM_MANAGER.getOrThrow(tag.getString("faction").toUpperCase(Locale.ROOT)));
        provider.setScale(tag.getCompound("attributes").contains("scale")
                ? (float) tag.getCompound("attributes").getDouble("scale")
                : 1.0F);

        if (tag.contains("boss_bar")) {
            provider.setHasBossBar(tag.getBoolean("boss_bar"));
            if (tag.contains("custom_texture")) {
                provider.setBossBar(ResourceLocation.tryParse(tag.getString("custom_texture")));
            }
        }
        if (tag.contains("custom_name")) {
            provider.setName(tag.getString("custom_name"));
        }

        if (!clientSide) {
            CompoundTag attributes = tag.getCompound("attributes");
            provider.setStunAnimations(resolveStunAnimations(tag.getCompound("stun_animations")));
            provider.setChasingSpeed(attributes.getDouble("chasing_speed"));
            provider.setAHCombatBehaviors(
                    AdvancedMobpatchReloader.deserializeAdvancedCombatBehaviors(tag.getList("combat_behavior", 10)));
            provider.setAHWeaponMotions(
                    resolveHumanoidWeaponMotions(MobPatchReloadListener.deserializeHumanoidWeaponMotions(tag.getList("humanoid_weapon_motions", 10))));
            provider.setGuardMotions(AdvancedMobpatchReloader.deserializeGuardMotions(tag.getList("custom_guard_motion", 10)));
            provider.setRegenStaminaStandbyTime(attributes.contains("stamina_regan_delay")
                    ? attributes.getInt("stamina_regan_delay")
                    : 30);
            provider.setHasStunReduction(!attributes.contains("has_stun_reduction")
                    || attributes.getBoolean("has_stun_reduction"));
            provider.setMaxStunShield(attributes.contains("max_stun_shield")
                    ? (float) attributes.getDouble("max_stun_shield")
                    : 0.0F);
            provider.setReganShieldStandbyTime(attributes.contains("stun_shield_regan_delay")
                    ? attributes.getInt("stun_shield_regan_delay")
                    : 30);
            provider.setReganShieldMultiply(attributes.contains("stun_shield_regan_multiply")
                    ? (float) attributes.getDouble("stun_shield_regan_multiply")
                    : 1.0F);
            provider.setStaminaLoseMultiply(attributes.contains("stamina_lose_multiply")
                    ? (float) attributes.getDouble("stamina_lose_multiply")
                    : 0.0F);
            provider.setGuardRadius(attributes.contains("guard_radius")
                    ? (float) attributes.getDouble("guard_radius")
                    : 3.0F);
            provider.setAttackRadius(attributes.contains("attack_radius")
                    ? (float) attributes.getDouble("attack_radius")
                    : 1.5F);
            provider.setStunEvent(AdvancedMobpatchReloader.deserializeStunCommandList(tag.getList("stun_command_list", 10)));
        }

        return provider;
    }

    private static ResourceLocation withAnimModelSuffix(ResourceLocation location) {
        return ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "animmodels/" + location.getPath() + ".json");
    }

    private static List<Pair<LivingMotion, AnimationAccessor<? extends StaticAnimation>>> resolveDefaultAnimations(CompoundTag tag) {
        return MobPatchReloadListener.deserializeDefaultAnimations(tag);
    }

    private static Map<StunType, AnimationAccessor<? extends StaticAnimation>> resolveStunAnimations(CompoundTag tag) {
        return MobPatchReloadListener.deserializeStunAnimations(tag);
    }

    private static Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, AnimationAccessor<? extends StaticAnimation>>>>> resolveHumanoidWeaponMotions(
            Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, AnimationAccessor<? extends StaticAnimation>>>>> input) {
        return input;
    }
}