/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.eventbus.EventBus
 *  net.minecraftforge.fml.ModList
 *  org.apache.logging.log4j.LogManager
 */
package noppes.npcs.controllers;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.fml.ModList;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.shared.client.util.NoppesStringUtils;
import noppes.npcs.shared.common.util.LogWriter;
import org.apache.logging.log4j.LogManager;

public class PixelmonHelper {
    public static boolean Enabled = ModList.get().isLoaded("pixelmon");
    public static EventBus EVENT_BUS;
    public static Field storageManager;
    private static Object partyStorage;
    private static Method getPartyStorage;
    private static Object pcStorage;
    private static Method getPcStorage;
    private static Method getPokemonData;
    private static Method getPixelmonModel;
    private static Class modelSetupClass;
    private static Method modelSetupMethod;
    private static Class pixelmonClass;

    public static void load() {
        if (!Enabled) {
            return;
        }
        try {
            if (PixelmonHelper.isReforged()) {
                Class<?> c = Class.forName("com.pixelmonmod.pixelmon.Pixelmon");
                EVENT_BUS = (EventBus)c.getDeclaredField("EVENT_BUS").get(null);
                c = Class.forName("com.pixelmonmod.pixelmon.api.storage.StorageProxy");
                storageManager = c.getDeclaredField("storageManager");
                storageManager.setAccessible(true);
                c = Class.forName("com.pixelmonmod.pixelmon.api.storage.StorageManager");
                getPartyStorage = c.getMethod("getParty", UUID.class);
                getPcStorage = c.getMethod("getPCForPlayer", UUID.class);
                pixelmonClass = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.AbstractBaseEntity");
                getPokemonData = pixelmonClass.getMethod("getPokemon", new Class[0]);
            } else {
                Class<?> c = Class.forName("com.pixelmongenerations.core.storage.PixelmonStorage");
                partyStorage = c.getDeclaredField("pokeBallManager").get(null);
                pcStorage = c.getDeclaredField("computerManager").get(null);
                c = Class.forName("com.pixelmongenerations.core.storage.PokeballManager");
                getPartyStorage = c.getMethod("getPlayerStorage", Player.class);
                c = Class.forName("com.pixelmongenerations.core.storage.ComputerManager");
                getPcStorage = c.getMethod("getPlayerStorage", Player.class);
                pixelmonClass = Class.forName("com.pixelmongenerations.common.entity.pixelmon.Entity1Base");
            }
        }
        catch (Exception e) {
            LogWriter.except(e);
            Enabled = false;
        }
    }

    public static boolean isReforged() {
        if (!Enabled) {
            throw new CustomNPCsException("No pixelmon installed", new Object[0]);
        }
        try {
            Class.forName("com.pixelmonmod.pixelmon.Pixelmon");
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static void loadClient() {
        if (!Enabled) {
            return;
        }
        try {
            if (PixelmonHelper.isReforged()) {
                Class<?> c = Class.forName("com.pixelmonmod.pixelmon.entities.pixelmon.AbstractClientEntity");
                getPixelmonModel = c.getMethod("getModel", new Class[0]);
                modelSetupClass = Class.forName("com.pixelmonmod.pixelmon.client.models.PixelmonModelSmd");
                modelSetupMethod = modelSetupClass.getMethod("setupForRender", c);
            } else {
                Class<?> c = Class.forName("com.pixelmongenerations.common.entity.pixelmon.Entity3HasStats");
                getPixelmonModel = c.getMethod("getModel", new Class[0]);
                modelSetupClass = Class.forName("com.pixelmongenerations.client.models.PixelmonModelSmd");
                modelSetupMethod = modelSetupClass.getMethod("setupForRender", c);
            }
        }
        catch (Exception e) {
            LogWriter.except(e);
            Enabled = false;
        }
    }

    public static List<String> getPixelmonList() {
        ArrayList<String> list = new ArrayList<String>();
        if (!Enabled) {
            return list;
        }
        try {
            if (PixelmonHelper.isReforged()) {
                Class<?> c = Class.forName("com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies");
                Field getAll = c.getDeclaredField("ENGLISH_NAMES");
                getAll.setAccessible(true);
                Object2IntOpenHashMap names = (Object2IntOpenHashMap)getAll.get(null);
                list = new ArrayList(names.keySet());
            } else {
                ?[] array;
                Class<?> c = Class.forName("com.pixelmongenerations.core.enums.EnumSpecies");
                for (Object ob : array = c.getEnumConstants()) {
                    list.add(ob.toString());
                }
            }
        }
        catch (Exception e) {
            LogWriter.error("getPixelmonList", e);
        }
        return list;
    }

    public static boolean isPixelmon(Entity entity) {
        if (!Enabled) {
            return false;
        }
        String s = entity.m_20078_();
        if (s == null) {
            return false;
        }
        return s.equals("pixelmon:pixelmon");
    }

    public static String getName(LivingEntity entity) {
        if (!Enabled || !PixelmonHelper.isPixelmon((Entity)entity)) {
            return "";
        }
        try {
            if (PixelmonHelper.isReforged()) {
                Object species = pixelmonClass.getMethod("getSpecies", new Class[0]).invoke(entity, new Object[0]);
                return NoppesStringUtils.stripSpecialCharacters(((String)species.getClass().getMethod("getName", new Class[0]).invoke(species, new Object[0])).toLowerCase());
            }
            Method m = entity.getClass().getMethod("getName", new Class[0]);
            return m.invoke(entity, new Object[0]).toString();
        }
        catch (Exception e) {
            LogManager.getLogger().error("getName", (Throwable)e);
            return "";
        }
    }

    public static Object getModel(LivingEntity entity) {
        try {
            return getPixelmonModel.invoke(entity, new Object[0]);
        }
        catch (Exception e) {
            LogManager.getLogger().error("getModel", (Throwable)e);
            return null;
        }
    }

    public static void setupModel(LivingEntity entity, Object model) {
        try {
            if (modelSetupClass.isAssignableFrom(model.getClass())) {
                modelSetupMethod.invoke(model, entity);
            }
        }
        catch (Exception e) {
            LogManager.getLogger().error("setupModel", (Throwable)e);
        }
    }

    public static Object getPokemonData(Entity entity) {
        try {
            return getPokemonData.invoke(entity, new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getParty(Player player) {
        try {
            if (PixelmonHelper.isReforged()) {
                return getPartyStorage.invoke(storageManager.get(null), player.m_20148_());
            }
            return ((Optional)getPartyStorage.invoke(partyStorage, player)).get();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getPc(Player player) {
        try {
            if (PixelmonHelper.isReforged()) {
                return getPcStorage.invoke(storageManager.get(null), player.m_20148_());
            }
            return getPcStorage.invoke(pcStorage, player);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class getPixelmonClass() {
        return pixelmonClass;
    }

    public static void initEntity(LivingEntity entity, String name) {
        try {
            if (PixelmonHelper.isReforged()) {
                Class<?> c = Class.forName("com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies");
                Field f = c.getDeclaredField("ENGLISH_NAMES");
                f.setAccessible(true);
                Object2IntOpenHashMap names = (Object2IntOpenHashMap)f.get(null);
                f = c.getDeclaredField("REGISTERED_SPECIES");
                f.setAccessible(true);
                Int2ObjectOpenHashMap species = (Int2ObjectOpenHashMap)f.get(null);
                Object specie = species.get(names.getInt((Object)name));
                c = Class.forName("com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory");
                Object pokemon = c.getMethod("create", specie.getClass()).invoke(null, specie);
                pixelmonClass.getMethod("setPokemon", pokemon.getClass()).invoke(entity, pokemon);
            }
        }
        catch (Exception e) {
            LogManager.getLogger().error("initEntity", (Throwable)e);
        }
    }

    static {
        getPixelmonModel = null;
    }
}

