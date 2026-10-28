package com.goodbird.cnpcefaddon.common.compatibility;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
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
    public enum AttackHandling {
        NOT_HANDLED,
        HANDLED_NO_DAMAGE,
        HANDLED_DAMAGE
    }

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

    /**
     * CNPC's display update rebuilds the client patch and animator. Send the
     * final held-item motion map after that update instead of immediately
     * before it, or the client discards the map it just received.
     */
    public static void resyncAfterClientUpdate(EntityNPCInterface npc) {
        if (npc.level().isClientSide() || npc.getServer() == null) {
            return;
        }
        npc.getServer().execute(() -> {
            if (!npc.isRemoved() && npc.isAlive()) {
                resync(npc);
            }
        });
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
        return tryAttackWithEpicFight(attacker, target) != AttackHandling.NOT_HANDLED;
    }

    /**
     * Returns whether Epic Fight owned the swing separately from whether the
     * hit dealt damage. CNPC must not fall back to its vanilla attack after an
     * Epic Fight miss, block, or invulnerability result.
     */
    public static AttackHandling tryAttackWithEpicFight(EntityNPCInterface attacker, Entity target) {
        if (ROUTING_NATIVE_ATTACK.get()) {
            return AttackHandling.NOT_HANDLED;
        }
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
        if (!(patch instanceof AdvNpcHumanoidPatch<?>) && !(patch instanceof NpcHumanoidPatch<?>)) {
            return AttackHandling.NOT_HANDLED;
        }

        EpicFightDamageSource source = EpicFightDamageSources.mobAttack(attacker)
                .setUsedItem(attacker.getMainHandItem())
                .setBaseImpact(patch.getImpact(InteractionHand.MAIN_HAND))
                .setStunType(StunType.SHORT);
        boolean animationDispatched = false;
        if (patch instanceof AdvNpcHumanoidPatch<?> advanced) {
            animationDispatched = advanced.triggerConfiguredAttackAnimation();
        } else if (patch instanceof NpcHumanoidPatch<?> normal) {
            animationDispatched = normal.cNPC_EpicFight_Addon$triggerConfiguredAttackAnimation();
        }
        CNPCEpicFightAddon.LOGGER.debug(
                "[CNPC EFI diagnostic] native melee animation dispatch: entity={} patch={} dispatched={} item={}",
                attacker.getId(), patch.getClass().getSimpleName(), animationDispatched,
                attacker.getMainHandItem().getItem());
        AttackResult result;
        ROUTING_NATIVE_ATTACK.set(true);
        try {
            result = patch.attack(source, target, InteractionHand.MAIN_HAND);
        } finally {
            ROUTING_NATIVE_ATTACK.set(false);
        }
        AttackHandling handling = result.resultType.dealtDamage()
                ? AttackHandling.HANDLED_DAMAGE : AttackHandling.HANDLED_NO_DAMAGE;
        com.goodbird.cnpcefaddon.CNPCEpicFightAddon.LOGGER.info(
                "[CNPC EFI] melee ownership entity={} target={} handling={} source=EpicFightDamageSource stun={} item={}",
                attacker.getId(), target.getId(), handling, source.getStunType(), attacker.getMainHandItem());
        return handling;
    }

    public static boolean isRoutingNativeAttack() {
        return ROUTING_NATIVE_ATTACK.get();
    }

    public static DamageSource replaceActiveEpicFightSource(Entity victim, DamageSource incoming) {
        if (incoming instanceof EpicFightDamageSource) return incoming;
        Entity attacker = incoming.getDirectEntity() != null ? incoming.getDirectEntity() : incoming.getEntity();
        if (attacker instanceof EntityNPCInterface) {
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
