/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 */
package noppes.npcs.packets.server;

import java.util.HashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketRecipesGet
extends PacketServerBasic {
    private int width;

    public SPacketRecipesGet(int width) {
        this.width = width;
    }

    public static void encode(SPacketRecipesGet msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.width);
    }

    public static SPacketRecipesGet decode(FriendlyByteBuf buf) {
        return new SPacketRecipesGet(buf.readInt());
    }

    @Override
    protected void handle() {
        SPacketRecipesGet.sendRecipeData(this.player, this.width);
    }

    public static void sendRecipeData(ServerPlayer player, int size) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        if (size == 3) {
            for (RecipeCarpentry recipe : RecipeController.instance.globalRecipes.values()) {
                map.put(recipe.name + "|" + recipe.m_6423_(), 0);
            }
        } else {
            for (RecipeCarpentry recipe : RecipeController.instance.anvilRecipes.values()) {
                map.put(recipe.name + "|" + recipe.m_6423_(), 0);
            }
        }
        NoppesUtilServer.sendScrollData(player, map);
    }
}

