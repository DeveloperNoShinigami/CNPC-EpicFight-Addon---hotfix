/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import noppes.npcs.entity.EntityCustomNpc;

public class EntityNPC64x32
extends EntityCustomNpc {
    public EntityNPC64x32(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
        this.display.setSkinTexture("customnpcs:textures/entity/humanmale/steve64x32.png");
    }
}

