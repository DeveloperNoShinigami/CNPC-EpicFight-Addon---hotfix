/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.monster.Monster
 */
package noppes.npcs.api.wrapper;

import net.minecraft.world.entity.monster.Monster;
import noppes.npcs.api.entity.IMonster;
import noppes.npcs.api.wrapper.EntityLivingWrapper;

public class MonsterWrapper<T extends Monster>
extends EntityLivingWrapper<T>
implements IMonster {
    public MonsterWrapper(T entity) {
        super(entity);
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public boolean typeOf(int type) {
        return type == 3 ? true : super.typeOf(type);
    }
}

