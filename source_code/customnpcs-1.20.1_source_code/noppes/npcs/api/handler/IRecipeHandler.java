/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.handler;

import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.handler.data.IRecipe;

public interface IRecipeHandler {
    public List<IRecipe> getGlobalList();

    public List<IRecipe> getCarpentryList();

    public IRecipe addRecipe(String var1, boolean var2, ItemStack var3, Object ... var4);

    public IRecipe addRecipe(String var1, boolean var2, ItemStack var3, int var4, int var5, ItemStack ... var6);

    public IRecipe delete(ResourceLocation var1);
}

