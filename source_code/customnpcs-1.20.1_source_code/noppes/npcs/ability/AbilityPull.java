/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.ability;

import noppes.npcs.ability.AbstractAbility;
import noppes.npcs.ability.IAbilityUpdate;
import noppes.npcs.constants.EnumAbilityType;
import noppes.npcs.entity.EntityNPCInterface;

public class AbilityPull
extends AbstractAbility
implements IAbilityUpdate {
    public AbilityPull(EntityNPCInterface entity) {
        super(entity);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isType(EnumAbilityType type) {
        return type == EnumAbilityType.UPDATE;
    }
}

