/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.TamableAnimal
 */
package noppes.npcs.api.entity;

import net.minecraft.world.entity.TamableAnimal;
import noppes.npcs.api.entity.IAnimal;

public interface IPixelmon<T extends TamableAnimal>
extends IAnimal<T> {
    public Object getPokemonData();
}

