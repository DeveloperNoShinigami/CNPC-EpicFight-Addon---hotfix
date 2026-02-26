/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.fml.ModList
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class CobblemonHelper {
    public static boolean Enabled = ModList.get().isLoaded("cobblemon");

    public static boolean isPokemon(Entity entity) {
        if (entity == null) {
            return false;
        }
        ResourceLocation typeResLoc = ForgeRegistries.ENTITY_TYPES.getKey((Object)entity.m_6095_());
        return typeResLoc.equals((Object)new ResourceLocation("cobblemon", "pokemon"));
    }

    public static ResourceLocation getType(Entity entity) {
        if (!CobblemonHelper.isPokemon(entity)) {
            return null;
        }
        try {
            Object pokemon = entity.getClass().getMethod("getPokemon", new Class[0]).invoke(entity, new Object[0]);
            Object species = pokemon.getClass().getMethod("getSpecies", new Class[0]).invoke(pokemon, new Object[0]);
            return (ResourceLocation)species.getClass().getField("resourceIdentifier").get(species);
        }
        catch (Exception ignored) {
            return null;
        }
    }

    public static void setType(Entity entity, ResourceLocation resourceLocation) {
        if (!CobblemonHelper.isPokemon(entity)) {
            return;
        }
        try {
            Object instance = Class.forName("com.cobblemon.mod.common.api.pokemon.PokemonSpecies").getField("INSTANCE").get(null);
            Object species = instance.getClass().getMethod("getByIdentifier", ResourceLocation.class).invoke(instance, resourceLocation);
            Object pokemon = entity.getClass().getMethod("getPokemon", new Class[0]).invoke(entity, new Object[0]);
            pokemon.getClass().getMethod("setSpecies", species.getClass()).invoke(pokemon, species);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static EntityModel getPokemonModel(Entity entity) {
        ResourceLocation species = CobblemonHelper.getType(entity);
        EntityModel model = null;
        try {
            Object instance = Class.forName("com.cobblemon.mod.common.client.render.models.blockbench.repository.PokemonModelRepository").getField("INSTANCE").get(null);
            model = (EntityModel)instance.getClass().getMethod("getPoser", ResourceLocation.class, Set.class).invoke(instance, species, new HashSet());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return model;
    }

    public static List<String> getTypes() {
        HashSet<String> res = new HashSet<String>();
        try {
            Object instance = Class.forName("com.cobblemon.mod.common.api.pokemon.PokemonSpecies").getField("INSTANCE").get(null);
            List implementedSpecies = (List)instance.getClass().getMethod("getImplemented", new Class[0]).invoke(instance, new Object[0]);
            for (Object obj : implementedSpecies) {
                res.add(obj.getClass().getMethod("getResourceIdentifier", new Class[0]).invoke(obj, new Object[0]).toString());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return new ArrayList<String>(res);
    }
}

