/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.inventory.TransientCraftingContainer
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingBookCategory
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.ShapedRecipe
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.common.ForgeHooks
 */
package noppes.npcs.controllers.data;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeHooks;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.api.handler.data.IRecipe;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.Availability;

public class RecipeCarpentry
extends ShapedRecipe
implements IRecipe {
    public Availability availability = new Availability();
    public boolean isGlobal = false;
    public boolean ignoreDamage = false;
    public boolean ignoreNBT = false;
    public boolean savesRecipe = true;
    public String name;

    public RecipeCarpentry(ResourceLocation location, int width, int height, NonNullList<Ingredient> recipe, ItemStack result) {
        super(location, "customnpcs", CraftingBookCategory.MISC, width, height, recipe, result);
        this.name = location.m_135815_();
    }

    public RecipeCarpentry(ResourceLocation location, String name) {
        super(location, "customnpcs", CraftingBookCategory.MISC, 4, 4, NonNullList.m_122779_(), ItemStack.f_41583_);
        this.name = name;
    }

    public static RecipeCarpentry load(CompoundTag compound) {
        ResourceLocation location = null;
        location = compound.m_128441_("ID") ? new ResourceLocation("customnpcs", compound.m_128461_("ID")) : new ResourceLocation(compound.m_128461_("Id"));
        RecipeCarpentry recipe = new RecipeCarpentry(location, compound.m_128451_("Width"), compound.m_128451_("Height"), NBTTags.getIngredientList(compound.m_128437_("Materials", 10)), ItemStack.m_41712_((CompoundTag)compound.m_128469_("Item")));
        recipe.availability.load(compound.m_128469_("Availability"));
        recipe.ignoreDamage = compound.m_128471_("IgnoreDamage");
        recipe.ignoreNBT = compound.m_128471_("IgnoreNBT");
        recipe.isGlobal = compound.m_128471_("Global");
        recipe.name = compound.m_128461_("Name");
        return recipe;
    }

    public CompoundTag writeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.m_128405_("Width", this.getRecipeWidth());
        compound.m_128405_("Height", this.getRecipeHeight());
        if (this.getResult() != null) {
            compound.m_128365_("Item", (Tag)this.getResult().m_41739_(new CompoundTag()));
        }
        compound.m_128365_("Materials", (Tag)NBTTags.nbtIngredientList((NonNullList<Ingredient>)this.m_7527_()));
        compound.m_128365_("Availability", (Tag)this.availability.save(new CompoundTag()));
        compound.m_128359_("Name", this.name);
        compound.m_128359_("Id", this.m_6423_().toString());
        compound.m_128379_("Global", this.isGlobal);
        compound.m_128379_("IgnoreDamage", this.ignoreDamage);
        compound.m_128379_("IgnoreNBT", this.ignoreNBT);
        return compound;
    }

    public static RecipeCarpentry createRecipe(ResourceLocation location, RecipeCarpentry recipe, ItemStack par1ItemStack, Object ... limbSwingAmountArrayOfObj) {
        int var9;
        Object var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        if (limbSwingAmountArrayOfObj[var4] instanceof String[]) {
            String[] var7;
            String[] var8 = var7 = (String[])limbSwingAmountArrayOfObj[var4++];
            var9 = var7.length;
            for (int var10 = 0; var10 < var9; ++var10) {
                String var11 = var8[var10];
                ++var6;
                var5 = var11.length();
                var3 = (String)var3 + var11;
            }
        } else {
            while (limbSwingAmountArrayOfObj[var4] instanceof String) {
                String var13 = (String)limbSwingAmountArrayOfObj[var4++];
                ++var6;
                var5 = var13.length();
                var3 = (String)var3 + var13;
            }
        }
        HashMap<Character, ItemStack> var14 = new HashMap<Character, ItemStack>();
        while (var4 < limbSwingAmountArrayOfObj.length) {
            Character var16 = (Character)limbSwingAmountArrayOfObj[var4];
            ItemStack var17 = ItemStack.f_41583_;
            if (limbSwingAmountArrayOfObj[var4 + 1] instanceof Item) {
                var17 = new ItemStack((ItemLike)((Item)limbSwingAmountArrayOfObj[var4 + 1]));
            } else if (limbSwingAmountArrayOfObj[var4 + 1] instanceof Block) {
                var17 = new ItemStack((ItemLike)((Block)limbSwingAmountArrayOfObj[var4 + 1]), 1);
            } else if (limbSwingAmountArrayOfObj[var4 + 1] instanceof ItemStack) {
                var17 = (ItemStack)limbSwingAmountArrayOfObj[var4 + 1];
            }
            var14.put(var16, var17);
            var4 += 2;
        }
        NonNullList ingredients = NonNullList.m_122779_();
        for (var9 = 0; var9 < var5 * var6; ++var9) {
            char var18 = ((String)var3).charAt(var9);
            if (var14.containsKey(Character.valueOf(var18))) {
                ingredients.add(var9, (Object)Ingredient.m_43927_((ItemStack[])new ItemStack[]{((ItemStack)var14.get(Character.valueOf(var18))).m_41777_()}));
                continue;
            }
            ingredients.add(var9, (Object)Ingredient.f_43901_);
        }
        RecipeCarpentry newrecipe = new RecipeCarpentry(location, var5, var6, (NonNullList<Ingredient>)ingredients, par1ItemStack);
        newrecipe.copy(recipe);
        if (var5 == 4 || var6 == 4) {
            newrecipe.isGlobal = false;
        }
        return newrecipe;
    }

    public boolean m_5818_(CraftingContainer inventoryCrafting, Level world) {
        for (int i = 0; i <= 4 - this.getRecipeWidth(); ++i) {
            for (int j = 0; j <= 4 - this.getRecipeHeight(); ++j) {
                if (this.checkMatch((Container)inventoryCrafting, i, j, true)) {
                    return true;
                }
                if (!this.checkMatch((Container)inventoryCrafting, i, j, false)) continue;
                return true;
            }
        }
        return false;
    }

    public ItemStack m_8043_(RegistryAccess p_266881_) {
        if (super.m_8043_(p_266881_).m_41619_()) {
            return ItemStack.f_41583_;
        }
        return super.m_8043_(p_266881_).m_41777_();
    }

    private boolean checkMatch(Container inventoryCrafting, int par2, int par3, boolean par4) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                int var7 = i - par2;
                int var8 = j - par3;
                Ingredient ingredient = Ingredient.f_43901_;
                if (var7 >= 0 && var8 >= 0 && var7 < this.getRecipeWidth() && var8 < this.getRecipeHeight()) {
                    ingredient = par4 ? (Ingredient)this.m_7527_().get(this.getRecipeWidth() - var7 - 1 + var8 * this.getRecipeWidth()) : (Ingredient)this.m_7527_().get(var7 + var8 * this.getRecipeWidth());
                }
                ItemStack var10 = ItemStack.f_41583_;
                if (inventoryCrafting instanceof TransientCraftingContainer) {
                    TransientCraftingContainer tcc = (TransientCraftingContainer)inventoryCrafting;
                    var10 = tcc.m_8020_(i + j * tcc.m_39347_());
                }
                if (!var10.m_41619_() && ingredient.m_43908_().length == 0) {
                    return false;
                }
                if (var10.m_41619_() && ingredient.m_43908_().length == 0) continue;
                ItemStack var9 = ingredient.m_43908_()[0];
                if (var10.m_41619_() && var9.m_41619_() || NoppesUtilPlayer.compareItems(var9, var10, this.ignoreDamage, this.ignoreNBT)) continue;
                return false;
            }
        }
        return true;
    }

    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inventoryCrafting) {
        NonNullList list = NonNullList.m_122780_((int)inventoryCrafting.m_6643_(), (Object)ItemStack.f_41583_);
        for (int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = inventoryCrafting.m_8020_(i);
            list.set(i, (Object)ForgeHooks.getCraftingRemainingItem((ItemStack)itemstack));
        }
        return list;
    }

    public boolean m_5598_() {
        return false;
    }

    public void copy(RecipeCarpentry recipe) {
        this.availability = recipe.availability;
        this.isGlobal = recipe.isGlobal;
        this.ignoreDamage = recipe.ignoreDamage;
        this.ignoreNBT = recipe.ignoreNBT;
    }

    public ItemStack getCraftingItem(int i) {
        if (i >= this.m_7527_().size()) {
            return ItemStack.f_41583_;
        }
        Ingredient ingredients = (Ingredient)this.m_7527_().get(i);
        if (ingredients.m_43908_().length == 0) {
            return ItemStack.f_41583_;
        }
        return ingredients.m_43908_()[0];
    }

    public boolean isValid() {
        if (this.m_7527_().size() == 0 || this.getResult().m_41619_()) {
            return false;
        }
        for (Ingredient ingredient : this.m_7527_()) {
            if (ingredient.m_43908_().length <= 0) continue;
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ItemStack getResult() {
        return this.m_8043_(null);
    }

    @Override
    public boolean isGlobal() {
        return this.isGlobal;
    }

    @Override
    public void setIsGlobal(boolean bo) {
        this.isGlobal = bo;
    }

    @Override
    public boolean getIgnoreNBT() {
        return this.ignoreNBT;
    }

    @Override
    public void setIgnoreNBT(boolean bo) {
        this.ignoreNBT = bo;
    }

    @Override
    public boolean getIgnoreDamage() {
        return this.ignoreDamage;
    }

    @Override
    public void setIgnoreDamage(boolean bo) {
        this.ignoreDamage = bo;
    }

    @Override
    public void save() {
        RecipeController.instance.saveRecipe(this);
    }

    @Override
    public void delete() {
    }

    @Override
    public ItemStack[] getRecipe() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (Ingredient ingredient : this.m_7527_()) {
            if (ingredient.m_43908_().length <= 0) continue;
            list.add(ingredient.m_43908_()[0]);
        }
        return list.toArray(new ItemStack[list.size()]);
    }

    @Override
    public void saves(boolean bo) {
        this.savesRecipe = bo;
    }

    @Override
    public boolean saves() {
        return this.savesRecipe;
    }
}

