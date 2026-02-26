/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.fml.ModList
 */
package noppes.npcs.controllers;

import java.util.Set;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

public class PhysicsHelper {
    public static boolean Enabled = ModList.get().isLoaded("physicsmod");

    public static void resetEntityPhysics(Level level, int id) {
        try {
            Class<?> physModClass = Class.forName("net.diebuddies.physics.PhysicsMod");
            Object modInstance = physModClass.getMethod("getInstance", Level.class).invoke(null, level);
            Set blockified = (Set)physModClass.getField("alreadyBlockified").get(modInstance);
            blockified.remove(id);
        }
        catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}

