package com.goodbird.cnpcefaddon.common.patch;

import com.goodbird.cnpcefaddon.common.provider.AdvNpcPatchProvider;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.Faction;

import java.util.Map;

public class AdvNpcHumanoidPatch<T extends PathfinderMob> extends AdvancedCustomHumanoidMobPatch<T> implements INpcPatch {
    private final AdvNpcPatchProvider provider;
    private boolean cNPC_EpicFight_Addon$pendingServerJoinInitialization;

    public AdvNpcHumanoidPatch(Faction faction, AdvNpcPatchProvider provider) {
        super(faction, provider);
        this.provider = provider;
    }

    @Override
    public void onConstructed(T entityIn) {
        super.onConstructed(entityIn);

        if (this.provider.armature != null) {
            this.armature = this.provider.armature.deepCopy();
        }
    }

    @Override
    public void onJoinWorld(T entityIn, EntityJoinLevelEvent event) {
        if (this.cNPC_EpicFight_Addon$shouldDeferServerJoinInitialization(entityIn)) {
            this.cNPC_EpicFight_Addon$pendingServerJoinInitialization = true;
            return;
        }

        this.cNPC_EpicFight_Addon$pendingServerJoinInitialization = false;
        super.onJoinWorld(entityIn, event);
    }

    public void cNPC_EpicFight_Addon$completeDeferredServerJoinInitialization() {
        if (!this.cNPC_EpicFight_Addon$pendingServerJoinInitialization || this.original == null || this.original.level().isClientSide()) {
            return;
        }

        this.cNPC_EpicFight_Addon$pendingServerJoinInitialization = false;
        super.onJoinWorld(this.original, new EntityJoinLevelEvent(this.original, this.original.level()));
    }

    private boolean cNPC_EpicFight_Addon$shouldDeferServerJoinInitialization(T entityIn) {
        if (entityIn.level().isClientSide()) {
            return false;
        }

        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if ("noppes.npcs.entity.EntityNPCInterface".equals(element.getClassName()) && "m_7378_".equals(element.getMethodName())) {
                return true;
            }

            if ("noppes.npcs.entity.data.DataDisplay".equals(element.getClassName()) && "readToNBT".equals(element.getMethodName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        float scale = ((EntityNPCInterface) this.original).display.getSize() / 5.0F;
        return super.getModelMatrix(partialTicks).scale(scale, scale, scale);
    }

    @Override
    public HumanoidArmature getArmature() {
        return (HumanoidArmature) this.armature;
    }

    public static boolean cNPC_EpicFight_Addon$hasNativeRangedWeaponForEpicFight(LivingEntity entity) {
        if (cNPC_EpicFight_Addon$isNativeRangedWeaponForEpicFight(entity.getMainHandItem())) {
            return true;
        }

        if (cNPC_EpicFight_Addon$isNativeRangedWeaponForEpicFight(entity.getOffhandItem())) {
            return true;
        }

        return false;
    }

    private static boolean cNPC_EpicFight_Addon$isNativeRangedWeaponForEpicFight(ItemStack stack) {
        return stack.getItem() instanceof ProjectileWeaponItem;
    }

    public static boolean cNPC_EpicFight_Addon$shouldSuppressNativeRangedAi(LivingEntity entity) {
        if (!(entity instanceof PathfinderMob pathfinderMob)) {
            return false;
        }

        if (!(yesman.epicfight.world.capabilities.EpicFightCapabilities.getEntityPatch(pathfinderMob, yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch.class) instanceof AdvNpcHumanoidPatch<?> patch)) {
            return false;
        }

        return patch.cNPC_EpicFight_Addon$usesAdvancedRangedCombatContract();
    }

    public boolean cNPC_EpicFight_Addon$usesAdvancedRangedCombatContract() {
        return this.cNPC_EpicFight_Addon$hasAdvancedRangedContractForHand(net.minecraft.world.InteractionHand.MAIN_HAND)
                || this.cNPC_EpicFight_Addon$hasAdvancedRangedContractForHand(net.minecraft.world.InteractionHand.OFF_HAND);
    }

    private boolean cNPC_EpicFight_Addon$hasAdvancedRangedContractForHand(net.minecraft.world.InteractionHand hand) {
        if (this.original == null || this.provider.getHumanoidCombatBehaviors() == null) {
            return false;
        }

        ItemStack heldItem = this.original.getItemInHand(hand);
        if (heldItem.isEmpty()) {
            return false;
        }

        WeaponCategory weaponCategory = this.getResolvedWeaponCategory(hand);
        if (weaponCategory != CapabilityItem.WeaponCategories.RANGED) {
            return false;
        }

        CapabilityItem itemCapability = hand == net.minecraft.world.InteractionHand.OFF_HAND
                ? this.getAdvancedHoldingItemCapability(hand)
                : this.getHoldingItemCapability(hand);
        Style style = itemCapability.getStyle(this);
        Map<Style, yesman.epicfight.world.entity.ai.goal.CombatBehaviors.Builder<yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch<?>>> combatByStyle =
                this.provider.getHumanoidCombatBehaviors().get(weaponCategory);

        return combatByStyle != null
                && (combatByStyle.containsKey(style) || combatByStyle.containsKey(CapabilityItem.Styles.COMMON));
    }
}