package com.goodbird.cnpcefaddon.common.compatibility;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.entity.EntityNPCInterface;
import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Tracks CNPC equipment so Epic Fight never becomes the equipment authority. */
public final class CnpcEquipmentBridge {
    // CNPC can rebuild/wrap its entity state during display synchronization.
    // Entity-object keyed caches can therefore report a false equipment change
    // and repeatedly re-enter TACZ draw state. UUID is stable for the entity.
    private static final Map<UUID, Snapshot> LAST = new ConcurrentHashMap<>();
    /** CNPC/TACZ can expose a transient empty or rebuilt stack for one tick. */
    private static final Map<UUID, Candidate> PENDING = new ConcurrentHashMap<>();

    private CnpcEquipmentBridge() {}

    public static boolean observe(EntityNPCInterface npc) {
        Snapshot now = Snapshot.capture(npc);
        UUID uuid = npc.getUUID();
        Snapshot previous = LAST.get(uuid);
        if (previous == null) {
            LAST.put(uuid, now);
            PENDING.remove(uuid);
            return true;
        }
        if (!previous.sameHands(now)) {
            Candidate candidate = PENDING.get(uuid);
            if (candidate == null || !candidate.snapshot.sameHands(now)) {
                PENDING.put(uuid, new Candidate(now));
                return false;
            }

            // Require the new identity to be observed on two consecutive
            // server ticks.  This filters CNPC's one-tick wrapper/hand-state
            // rebuilds without delaying deliberate equipment changes more
            // than a single tick.
            LAST.put(uuid, now);
            PENDING.remove(uuid);
            return true;
        }
        PENDING.remove(uuid);
        return false;
    }

    /**
     * Commits an equipment change reported by the entity equipment mutation
     * hook. This is deliberately separate from {@link #observe(EntityNPCInterface)}:
     * the latter is only a passive legacy observer and must never turn a
     * combat tick into a patch refresh.
     */
    public static boolean observeEquipmentEvent(EntityNPCInterface npc) {
        Snapshot now = Snapshot.capture(npc);
        UUID uuid = npc.getUUID();
        Snapshot previous = LAST.put(uuid, now);
        PENDING.remove(uuid);
        return previous == null || !previous.sameHands(now);
    }

    public static void forget(EntityNPCInterface npc) {
        UUID uuid = npc.getUUID();
        LAST.remove(uuid);
        PENDING.remove(uuid);
    }

    private record Candidate(Snapshot snapshot) {}

    private record Snapshot(ItemStack mainHand, ItemStack offHand) {
        static Snapshot capture(EntityNPCInterface npc) {
            return new Snapshot(npc.getItemBySlot(EquipmentSlot.MAINHAND).copy(),
                    npc.getItemBySlot(EquipmentSlot.OFFHAND).copy());
        }

        boolean sameHands(Snapshot other) {
            return same(mainHand, other.mainHand) && same(offHand, other.offHand);
        }

        private static boolean same(ItemStack left, ItemStack right) {
            if (left.isEmpty() || right.isEmpty()) {
                return left.isEmpty() && right.isEmpty();
            }

            // TACZ mutates the stack tag for ammo, cooldown, bolt and other
            // runtime state after every shot. Those changes do not represent
            // a CNPC weapon swap and must not rebuild the Epic Fight patch or
            // clear the sustained AIM animation. GunId still distinguishes
            // TACZ weapon variants that share the same registered item.
            if (left.getItem() != right.getItem()) {
                return false;
            }

            String leftGunId = left.getTag() == null ? "" : left.getTag().getString("GunId");
            String rightGunId = right.getTag() == null ? "" : right.getTag().getString("GunId");
            return leftGunId.equals(rightGunId);
        }
    }
}
