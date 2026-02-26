/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.server.SPacketRecipeGet;
import noppes.npcs.packets.server.SPacketRecipesGet;

public class SPacketRecipeRemove
extends PacketServerBasic {
    private ResourceLocation recipe;

    public SPacketRecipeRemove(ResourceLocation recipe) {
        this.recipe = recipe;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.GLOBAL_RECIPE;
    }

    public static void encode(SPacketRecipeRemove msg, FriendlyByteBuf buf) {
        buf.m_130085_(msg.recipe);
    }

    public static SPacketRecipeRemove decode(FriendlyByteBuf buf) {
        return new SPacketRecipeRemove(buf.m_130281_());
    }

    @Override
    protected void handle() {
        RecipeCarpentry r = RecipeController.instance.delete(this.recipe);
        SPacketRecipesGet.sendRecipeData(this.player, r.isGlobal ? 3 : 4);
        SPacketRecipeGet.setRecipeGui(this.player, new RecipeCarpentry(new ResourceLocation("customnpcs", ""), ""));
    }
}

