/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package noppes.npcs.controllers.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import noppes.npcs.CustomItems;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.RecipeCarpentry;

public class RecipesDefault {
    public static void addRecipe(String name, Object ob, boolean isGlobal, Object ... recipe) {
        ItemStack item = ob instanceof Item ? new ItemStack((ItemLike)((Item)ob)) : (ob instanceof Block ? new ItemStack((ItemLike)((Block)ob)) : (ItemStack)ob);
        RecipeCarpentry recipeAnvil = new RecipeCarpentry(new ResourceLocation("customnpcs", name), name);
        recipeAnvil.isGlobal = isGlobal;
        recipeAnvil = RecipeCarpentry.createRecipe(new ResourceLocation("customnpcs", name), recipeAnvil, item, recipe);
        RecipeController.instance.saveRecipe(recipeAnvil);
    }

    public static void loadDefaultRecipes(int i) {
        if (i < 0) {
            RecipesDefault.addRecipe("npc_wand", CustomItems.wand, true, "XX", " Y", " Y", Character.valueOf('X'), Items.f_42406_, Character.valueOf('Y'), Items.f_42398_);
            RecipesDefault.addRecipe("mob_cloner", CustomItems.cloner, true, "XX", "XY", " Y", Character.valueOf('X'), Items.f_42406_, Character.valueOf('Y'), Items.f_42398_);
        }
    }
}

