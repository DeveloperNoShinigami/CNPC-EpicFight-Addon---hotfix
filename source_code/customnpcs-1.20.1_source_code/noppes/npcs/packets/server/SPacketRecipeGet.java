/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiData;

public class SPacketRecipeGet
extends PacketServerBasic {
    private ResourceLocation recipe;

    public SPacketRecipeGet(ResourceLocation recipe) {
        this.recipe = recipe;
    }

    public static void encode(SPacketRecipeGet msg, FriendlyByteBuf buf) {
        buf.m_130085_(msg.recipe);
    }

    public static SPacketRecipeGet decode(FriendlyByteBuf buf) {
        return new SPacketRecipeGet(buf.m_130281_());
    }

    @Override
    protected void handle() {
        RecipeCarpentry r = RecipeController.instance.getRecipe(this.recipe);
        SPacketRecipeGet.setRecipeGui(this.player, r);
    }

    public static void setRecipeGui(ServerPlayer player, RecipeCarpentry recipe) {
        if (recipe == null) {
            return;
        }
        if (!(player.f_36096_ instanceof ContainerManageRecipes)) {
            return;
        }
        ContainerManageRecipes container = (ContainerManageRecipes)player.f_36096_;
        container.setRecipe(recipe, player.m_9236_().m_9598_());
        Packets.send(player, new PacketGuiData(recipe.writeNBT()));
    }
}

