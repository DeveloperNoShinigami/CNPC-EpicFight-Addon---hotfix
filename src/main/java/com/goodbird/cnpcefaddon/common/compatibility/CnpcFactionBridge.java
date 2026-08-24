package com.goodbird.cnpcefaddon.common.compatibility;

import noppes.npcs.entity.EntityNPCInterface;
import net.minecraft.world.entity.Entity;

/** CNPC faction relationships take priority when both sides are CNPCs. */
public final class CnpcFactionBridge {
    public enum Relation { ALLIED, HOSTILE, UNKNOWN }

    private CnpcFactionBridge() {}

    public static Relation relation(Entity attacker, Entity target) {
        if (!(attacker instanceof EntityNPCInterface source)
                || !(target instanceof EntityNPCInterface victim)) {
            return Relation.UNKNOWN;
        }
        try {
            if (source.isAlliedTo(victim) || victim.isAlliedTo(source)) {
                return Relation.ALLIED;
            }
            if (source.getFaction() != null && source.getFaction().isAggressiveToNpc(victim)) {
                return Relation.HOSTILE;
            }
        } catch (RuntimeException ignored) {
            // Preserve Epic Fight's normal faction behavior if CNPC faction data is incomplete.
        }
        return Relation.UNKNOWN;
    }
}
