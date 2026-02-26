/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.animal.horse.AbstractHorse
 */
package noppes.npcs.api.wrapper;

import net.minecraft.world.entity.animal.horse.AbstractHorse;
import noppes.npcs.api.entity.IPixelmon;
import noppes.npcs.api.wrapper.AnimalWrapper;
import noppes.npcs.controllers.PixelmonHelper;

public class PixelmonWrapper<T extends AbstractHorse>
extends AnimalWrapper<T>
implements IPixelmon {
    public PixelmonWrapper(T entity) {
        super(entity);
    }

    @Override
    public Object getPokemonData() {
        return PixelmonHelper.getPokemonData(this.entity);
    }

    @Override
    public int getType() {
        return 8;
    }

    @Override
    public boolean typeOf(int type) {
        return type == 8 ? true : super.typeOf(type);
    }
}

