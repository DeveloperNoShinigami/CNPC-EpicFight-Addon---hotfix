/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 */
package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EventHooks;
import noppes.npcs.api.handler.IRecipeHandler;
import noppes.npcs.api.handler.data.IRecipe;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.controllers.data.RecipesDefault;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketSyncRecipeRemove;
import noppes.npcs.packets.client.PacketSyncRecipeUpdate;

public class RecipeController
implements IRecipeHandler {
    public HashMap<ResourceLocation, RecipeCarpentry> globalRecipes = new HashMap();
    public HashMap<ResourceLocation, RecipeCarpentry> anvilRecipes = new HashMap();
    public static RecipeController instance;
    public static final int version = 1;
    public int nextId = 1;
    public static HashMap<ResourceLocation, RecipeCarpentry> syncRecipes;

    public RecipeController() {
        instance = this;
    }

    public void load() {
        this.loadCategories();
        this.reloadGlobalRecipes();
        EventHooks.onGlobalRecipesLoaded(this);
    }

    public void reloadGlobalRecipes() {
    }

    private void loadCategories() {
        File saveDir = CustomNpcs.getLevelSaveDirectory();
        try {
            File file = new File(saveDir, "recipes.dat");
            if (file.exists()) {
                this.loadCategories(file);
            } else {
                this.globalRecipes.clear();
                this.anvilRecipes.clear();
                this.loadDefaultRecipes(-1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                File file = new File(saveDir, "recipes.dat_old");
                if (file.exists()) {
                    this.loadCategories(file);
                }
            }
            catch (Exception ee) {
                e.printStackTrace();
            }
        }
    }

    private void loadDefaultRecipes(int i) {
        if (i == 1) {
            return;
        }
        RecipesDefault.loadDefaultRecipes(i);
        this.saveCategories();
    }

    private void loadCategories(File file) throws Exception {
        CompoundTag nbttagcompound1 = NbtIo.m_128939_((InputStream)new FileInputStream(file));
        this.nextId = nbttagcompound1.m_128451_("LastId");
        ListTag list = nbttagcompound1.m_128437_("Data", 10);
        HashMap<ResourceLocation, RecipeCarpentry> globalRecipes = new HashMap<ResourceLocation, RecipeCarpentry>();
        HashMap<ResourceLocation, RecipeCarpentry> anvilRecipes = new HashMap<ResourceLocation, RecipeCarpentry>();
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                RecipeCarpentry recipe = RecipeCarpentry.load(list.m_128728_(i));
                if (recipe.isGlobal) {
                    globalRecipes.put(recipe.m_6423_(), recipe);
                    continue;
                }
                anvilRecipes.put(recipe.m_6423_(), recipe);
            }
        }
        this.anvilRecipes = anvilRecipes;
        this.globalRecipes = globalRecipes;
        this.loadDefaultRecipes(nbttagcompound1.m_128451_("Version"));
    }

    private void saveCategories() {
        try {
            File saveDir = CustomNpcs.getLevelSaveDirectory();
            ListTag list = new ListTag();
            for (RecipeCarpentry recipe : this.globalRecipes.values()) {
                if (!recipe.savesRecipe) continue;
                list.add((Object)recipe.writeNBT());
            }
            for (RecipeCarpentry recipe : this.anvilRecipes.values()) {
                if (!recipe.savesRecipe) continue;
                list.add((Object)recipe.writeNBT());
            }
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128365_("Data", (Tag)list);
            nbttagcompound.m_128405_("LastId", this.nextId);
            nbttagcompound.m_128405_("Version", 1);
            File file = new File(saveDir, "recipes.dat_new");
            File file1 = new File(saveDir, "recipes.dat_old");
            File file2 = new File(saveDir, "recipes.dat");
            NbtIo.m_128947_((CompoundTag)nbttagcompound, (OutputStream)new FileOutputStream(file));
            if (file1.exists()) {
                file1.delete();
            }
            file2.renameTo(file1);
            if (file2.exists()) {
                file2.delete();
            }
            file.renameTo(file2);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RecipeCarpentry findMatchingRecipe(CraftingContainer inventoryCrafting) {
        for (RecipeCarpentry recipe : this.anvilRecipes.values()) {
            if (!recipe.isValid() || !recipe.m_5818_(inventoryCrafting, null)) continue;
            return recipe;
        }
        return null;
    }

    public RecipeCarpentry getRecipe(ResourceLocation id) {
        if (this.globalRecipes.containsKey(id)) {
            return this.globalRecipes.get(id);
        }
        if (this.anvilRecipes.containsKey(id)) {
            return this.anvilRecipes.get(id);
        }
        return null;
    }

    public RecipeCarpentry saveRecipe(RecipeCarpentry recipe) {
        RecipeCarpentry current = this.getRecipe(recipe.m_6423_());
        if (current != null && !current.name.equals(recipe.name)) {
            while (this.containsRecipeName(recipe.name)) {
                recipe.name = recipe.name + "_";
            }
        }
        if (recipe.isGlobal) {
            this.globalRecipes.remove(recipe.m_6423_());
            this.globalRecipes.put(recipe.m_6423_(), recipe);
            Packets.sendAll(new PacketSyncRecipeUpdate(recipe.m_6423_(), 6, recipe.writeNBT()));
        } else {
            this.anvilRecipes.remove(recipe.m_6423_());
            this.anvilRecipes.put(recipe.m_6423_(), recipe);
            Packets.sendAll(new PacketSyncRecipeUpdate(recipe.m_6423_(), 7, recipe.writeNBT()));
        }
        this.saveCategories();
        this.reloadGlobalRecipes();
        return recipe;
    }

    private int getUniqueId() {
        ++this.nextId;
        return this.nextId;
    }

    private boolean containsRecipeName(String name) {
        name = name.toLowerCase();
        for (RecipeCarpentry recipe : this.globalRecipes.values()) {
            if (!recipe.name.toLowerCase().equals(name)) continue;
            return true;
        }
        for (RecipeCarpentry recipe : this.anvilRecipes.values()) {
            if (!recipe.name.toLowerCase().equals(name)) continue;
            return true;
        }
        return false;
    }

    @Override
    public RecipeCarpentry delete(ResourceLocation id) {
        RecipeCarpentry recipe = this.getRecipe(id);
        if (recipe == null) {
            return null;
        }
        this.globalRecipes.remove(recipe.m_6423_());
        this.anvilRecipes.remove(recipe.m_6423_());
        if (recipe.isGlobal) {
            Packets.sendAll(new PacketSyncRecipeRemove(id, 6));
        } else {
            Packets.sendAll(new PacketSyncRecipeRemove(id, 7));
        }
        this.saveCategories();
        this.reloadGlobalRecipes();
        return recipe;
    }

    @Override
    public List<IRecipe> getGlobalList() {
        return new ArrayList<IRecipe>(this.globalRecipes.values());
    }

    @Override
    public List<IRecipe> getCarpentryList() {
        return new ArrayList<IRecipe>(this.anvilRecipes.values());
    }

    @Override
    public IRecipe addRecipe(String name, boolean global, ItemStack result, Object ... objects) {
        RecipeCarpentry recipe = new RecipeCarpentry(new ResourceLocation("customnpcs", name), name);
        recipe.isGlobal = global;
        recipe = RecipeCarpentry.createRecipe(new ResourceLocation("customnpcs", name), recipe, result, objects);
        return this.saveRecipe(recipe);
    }

    @Override
    public IRecipe addRecipe(String name, boolean global, ItemStack result, int width, int height, ItemStack ... objects) {
        NonNullList list = NonNullList.m_122779_();
        for (ItemStack item : objects) {
            if (item.m_41619_()) continue;
            list.add((Object)Ingredient.m_43927_((ItemStack[])new ItemStack[]{item}));
        }
        RecipeCarpentry recipe = new RecipeCarpentry(new ResourceLocation("customnpcs", name), width, height, (NonNullList<Ingredient>)list, result);
        recipe.isGlobal = global;
        recipe.name = name;
        return this.saveRecipe(recipe);
    }

    static {
        syncRecipes = new HashMap();
    }
}

