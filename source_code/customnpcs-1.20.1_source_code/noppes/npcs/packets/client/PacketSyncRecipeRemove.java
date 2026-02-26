/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.shared.common.PacketBasic;

public class PacketSyncRecipeRemove
extends PacketBasic {
    private final ResourceLocation id;
    private final int type;

    public PacketSyncRecipeRemove(ResourceLocation id, int type) {
        this.id = id;
        this.type = type;
    }

    public static void encode(PacketSyncRecipeRemove msg, FriendlyByteBuf buf) {
        buf.m_130085_(msg.id);
        buf.writeInt(msg.type);
    }

    public static PacketSyncRecipeRemove decode(FriendlyByteBuf buf) {
        return new PacketSyncRecipeRemove(buf.m_130281_(), buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        if (this.type == 6) {
            RecipeController.instance.globalRecipes.remove(this.id);
            RecipeController.instance.reloadGlobalRecipes();
        } else if (this.type == 7) {
            RecipeController.instance.anvilRecipes.remove(this.id);
        }
    }

    public void clientSync(boolean syncEnd) {
    }
}

