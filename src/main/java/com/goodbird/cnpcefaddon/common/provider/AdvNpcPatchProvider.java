package com.goodbird.cnpcefaddon.common.provider;

import com.goodbird.cnpcefaddon.common.patch.AdvNpcHumanoidPatch;
import com.mojang.datafixers.util.Pair;
import com.nameless.indestructible.api.animation.types.CommandEvent;
import com.nameless.indestructible.data.AdvancedMobpatchReloader;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch.GuardMotion;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdvNpcPatchProvider extends AdvancedMobpatchReloader.AdvancedCustomHumanoidMobPatchProvider
		implements INpcPatchProvider {
	public HumanoidArmature armature;

	public void setAHCombatBehaviors(
			Map<WeaponCategory, Map<Style, yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Builder<HumanoidMobPatch<?>>>> value) {
		this.AHCombatBehaviors = value;
	}

	public void setAHWeaponMotions(
			Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, AnimationAccessor<? extends StaticAnimation>>>>> value) {
		this.AHWeaponMotions = value;
	}

	public void setGuardMotions(Map<WeaponCategory, Map<Style, GuardMotion>> value) {
		this.guardMotions = value;
	}

	public void setRegenStaminaStandbyTime(int value) {
		this.regenStaminaStandbyTime = value;
	}

	public void setHasStunReduction(boolean value) {
		this.hasStunReduction = value;
	}

	public void setMaxStunShield(float value) {
		this.maxStunShield = value;
	}

	public void setReganShieldStandbyTime(int value) {
		this.reganShieldStandbyTime = value;
	}

	public void setReganShieldMultiply(float value) {
		this.reganShieldMultiply = value;
	}

	public void setStaminaLoseMultiply(float value) {
		this.staminaLoseMultiply = value;
	}

	public void setGuardRadius(float value) {
		this.guardRadius = value;
	}

	public void setAttackRadius(float value) {
		this.attackRadius = value;
	}

	public void setDefaultAnimations(List<Pair<LivingMotion, AnimationAccessor<? extends StaticAnimation>>> value) {
		this.defaultAnimations = value;
	}

	public void setStunAnimations(Map<StunType, AnimationAccessor<? extends StaticAnimation>> value) {
		this.stunAnimations = value;
	}

	public void setAttributeValues(Map<Attribute, Double> value) {
		this.attributeValues = value;
	}

	public void setFaction(Faction value) {
		this.faction = value;
	}

	public void setChasingSpeed(double value) {
		this.chasingSpeed = value;
	}

	public void setScale(float value) {
		this.scale = value;
	}

	public void setHasBossBar(boolean value) {
		this.hasBossBar = value;
	}

	public void setBossBar(ResourceLocation value) {
		this.bossBar = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setStunEvent(List<CommandEvent.StunEvent> value) {
		this.stunEvent = value;
	}

	@Override
	public EntityPatch<?> get(Entity entity) {
		return new AdvNpcHumanoidPatch<>(this.faction, this);
	}

	@Override
	public void setArmature(Armature armature) {
		this.armature = (HumanoidArmature) armature;
	}
}
