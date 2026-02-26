/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.ability;

import noppes.npcs.ability.IAbility;

public interface IAbilityUpdate
extends IAbility {
    public boolean isActive();

    public void update();
}

