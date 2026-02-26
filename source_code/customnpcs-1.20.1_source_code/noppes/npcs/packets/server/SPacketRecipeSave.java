/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.server.SPacketRecipeGet;
import noppes.npcs.packets.server.SPacketRecipesGet;

public class SPacketRecipeSave
extends PacketServerBasic {
    private CompoundTag data;

    public SPacketRecipeSave(CompoundTag data) {
        this.data = data;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.GLOBAL_RECIPE;
    }

    public static void encode(SPacketRecipeSave msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static SPacketRecipeSave decode(FriendlyByteBuf buf) {
        return new SPacketRecipeSave(buf.m_130260_());
    }

    @Override
    protected void handle() {
        RecipeCarpentry recipe = RecipeCarpentry.load(this.data);
        RecipeController.instance.saveRecipe(recipe);
        SPacketRecipesGet.sendRecipeData(this.player, recipe.isGlobal ? 3 : 4);
        SPacketRecipeGet.setRecipeGui(this.player, recipe);
    }
}

