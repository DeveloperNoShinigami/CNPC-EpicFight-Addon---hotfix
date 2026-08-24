package com.goodbird.cnpcefaddon.common.compatibility;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.entity.EntityNPCInterface;
import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;

import java.util.Map;
import java.util.WeakHashMap;

/** Tracks CNPC equipment so Epic Fight never becomes the equipment authority. */
public final class CnpcEquipmentBridge {
    private static final Map<EntityNPCInterface, Snapshot> LAST = new WeakHashMap<>();

    private CnpcEquipmentBridge() {}

    public static boolean observe(EntityNPCInterface npc) {
        Snapshot now = Snapshot.capture(npc);
        Snapshot previous = LAST.put(npc, now);
        if (previous == null) {
            return true;
        }
        if (!previous.sameHands(now)) {
            return true;
        }
        return false;
    }

    public static void forget(EntityNPCInterface npc) {
        LAST.remove(npc);
    }

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
