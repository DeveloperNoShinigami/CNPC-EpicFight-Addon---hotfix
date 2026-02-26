package com.goodbird.cnpcefaddon.mixin.impl;

import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

import java.util.Map;
import java.util.Set;

@Mixin(MobPatchReloadListener.CustomHumanoidMobPatchProvider.class)
public interface ICustomHumanoidMobPatchProvider {
    @Accessor(remap = false)
    void setHumanoidCombatBehaviors(Map<WeaponCategory, Map<Style, CombatBehaviors.Builder<HumanoidMobPatch<?>>>> map);

    @Accessor(remap = false)
    void setHumanoidWeaponMotions(
            Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, AnimationAccessor<? extends StaticAnimation>>>>> map);
}
