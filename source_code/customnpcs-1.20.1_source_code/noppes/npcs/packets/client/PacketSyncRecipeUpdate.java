/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.shared.common.PacketBasic;

public class PacketSyncRecipeUpdate
extends PacketBasic {
    private final ResourceLocation id;
    private final int type;
    private final CompoundTag data;

    public PacketSyncRecipeUpdate(ResourceLocation id, int type, CompoundTag data) {
        this.id = id;
        this.type = type;
        this.data = data;
    }

    public static void encode(PacketSyncRecipeUpdate msg, FriendlyByteBuf buf) {
        buf.m_130085_(msg.id);
        buf.writeInt(msg.type);
        buf.m_130079_(msg.data);
    }

    public static PacketSyncRecipeUpdate decode(FriendlyByteBuf buf) {
        return new PacketSyncRecipeUpdate(buf.m_130281_(), buf.readInt(), buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        if (this.type == 6) {
            RecipeCarpentry recipe = RecipeCarpentry.load(this.data);
            RecipeController.instance.globalRecipes.put(recipe.m_6423_(), recipe);
            RecipeController.instance.reloadGlobalRecipes();
        } else if (this.type == 7) {
            RecipeCarpentry recipe = RecipeCarpentry.load(this.data);
            RecipeController.instance.anvilRecipes.put(recipe.m_6423_(), recipe);
        }
    }

    public void clientSync(boolean syncEnd) {
    }
}

