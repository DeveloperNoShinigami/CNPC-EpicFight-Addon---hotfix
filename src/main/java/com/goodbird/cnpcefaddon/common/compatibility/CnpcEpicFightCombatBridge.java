package com.goodbird.cnpcefaddon.common.compatibility;

import com.goodbird.cnpcefaddon.common.patch.AdvNpcHumanoidPatch;
import com.goodbird.cnpcefaddon.common.patch.NpcHumanoidPatch;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

/** Shared bridge for CNPC lifecycle, faction, and native melee integration. */
public final class CnpcEpicFightCombatBridge {
    private static final ThreadLocal<Boolean> ROUTING_NATIVE_ATTACK = ThreadLocal.withInitial(() -> false);

    private CnpcEpicFightCombatBridge() {}

    public static void resync(EntityNPCInterface npc) {
        // forceWeaponMotionResync synchronizes through Epic Fight's tracking
        // packet distributor. It is a server operation; invoking it while a
        // client receives the NPC NBT causes ClientChunkCache to be cast to
        // ServerChunkCache.
        if (npc.level().isClientSide()) {
            return;
        }
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(npc, LivingEntityPatch.class);
        if (patch instanceof AdvNpcHumanoidPatch<?> advanced) {
            advanced.cNPC_EpicFight_Addon$resyncHeldItemFromCnpc();
        } else if (patch instanceof NpcHumanoidPatch<?> humanoid) {
            humanoid.cNPC_EpicFight_Addon$resyncHeldItemFromCnpc();
        }
    }

    public static void beginNativeMelee(EntityNPCInterface npc) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(npc, LivingEntityPatch.class);
        if (patch instanceof AdvNpcHumanoidPatch<?> advanced) {
            advanced.cNPC_EpicFight_Addon$beginNativeMeleeDamage();
        } else if (patch instanceof NpcHumanoidPatch<?> humanoid) {
            humanoid.cNPC_EpicFight_Addon$beginNativeMeleeDamage();
        }
    }

    public static void endNativeMelee(EntityNPCInterface npc) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(npc, LivingEntityPatch.class);
        if (patch instanceof AdvNpcHumanoidPatch<?> advanced) {
            advanced.cNPC_EpicFight_Addon$endNativeMeleeDamage();
        } else if (patch instanceof NpcHumanoidPatch<?> humanoid) {
            humanoid.cNPC_EpicFight_Addon$endNativeMeleeDamage();
        }
    }

    /**
     * Routes CNPC's native melee entrypoint through Epic Fight exactly once.
     * CNPC's original implementation creates a vanilla DamageSource, so merely
     * surrounding it with a cached source cannot carry stun metadata.
     */
    public static boolean attackWithEpicFight(EntityNPCInterface attacker, Entity target) {
        if (ROUTING_NATIVE_ATTACK.get()) {
            return false;
        }
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
        if (!(patch instanceof AdvNpcHumanoidPatch<?>) && !(patch instanceof NpcHumanoidPatch<?>)) {
            return false;
        }

        EpicFightDamageSource source = EpicFightDamageSources.mobAttack(attacker)
                .setUsedItem(attacker.getMainHandItem())
                .setBaseImpact(patch.getImpact(InteractionHand.MAIN_HAND))
                .setStunType(StunType.SHORT);
        AttackResult result;
        ROUTING_NATIVE_ATTACK.set(true);
        try {
            result = patch.attack(source, target, InteractionHand.MAIN_HAND);
        } finally {
            ROUTING_NATIVE_ATTACK.set(false);
        }
        return result.resultType.dealtDamage();
    }

    public static boolean isRoutingNativeAttack() {
        return ROUTING_NATIVE_ATTACK.get();
    }

    public static DamageSource replaceActiveEpicFightSource(Entity victim, DamageSource incoming) {
        if (incoming instanceof EpicFightDamageSource) return incoming;
        Entity attacker = incoming.getDirectEntity() != null ? incoming.getDirectEntity() : incoming.getEntity();
        if (attacker != null) {
            LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
            EpicFightDamageSource active = patch == null ? null : patch.getEpicFightDamageSource();
            if (active != null && active.getDirectEntity() == attacker) {
                return active;
            }
        }
        return incoming;
    }

    public static boolean cnpcFactionAllows(Entity attacker, Entity target) {
        return CnpcFactionBridge.relation(attacker, target) == CnpcFactionBridge.Relation.HOSTILE;
    }

    public static boolean cnpcFactionBlocks(Entity attacker, Entity target) {
        return CnpcFactionBridge.relation(attacker, target) == CnpcFactionBridge.Relation.ALLIED;
    }
}
