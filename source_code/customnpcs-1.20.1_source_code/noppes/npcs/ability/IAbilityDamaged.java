/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.ability;

import noppes.npcs.ability.IAbility;
import noppes.npcs.api.event.NpcEvent;

public interface IAbilityDamaged
extends IAbility {
    public void handleEvent(NpcEvent.DamagedEvent var1);
}

