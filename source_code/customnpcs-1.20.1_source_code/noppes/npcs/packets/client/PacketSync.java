/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.DialogCategory;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestCategory;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.shared.common.PacketBasic;

public class PacketSync
extends PacketBasic {
    private final int type;
    private final CompoundTag data;
    private final boolean syncEnd;

    public PacketSync(int type, CompoundTag data, boolean syncEnd) {
        this.type = type;
        this.data = data;
        this.syncEnd = syncEnd;
    }

    public static void encode(PacketSync msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.type);
        buf.m_130079_(msg.data);
        buf.writeBoolean(msg.syncEnd);
    }

    public static PacketSync decode(FriendlyByteBuf buf) {
        return new PacketSync(buf.readInt(), buf.m_130260_(), buf.readBoolean());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        if (this.type == 1) {
            ListTag list = this.data.m_128437_("Data", 10);
            for (int i = 0; i < list.size(); ++i) {
                Faction faction = new Faction();
                faction.readNBT(list.m_128728_(i));
                FactionController.instance.factionsSync.put(faction.id, faction);
            }
            if (this.syncEnd) {
                FactionController.instance.factions = FactionController.instance.factionsSync;
                FactionController.instance.factionsSync = new HashMap();
            }
        } else if (this.type == 3) {
            if (!this.data.m_128456_()) {
                QuestCategory category = new QuestCategory();
                category.readNBT(this.data);
                QuestController.instance.categoriesSync.put(category.id, category);
            }
            if (this.syncEnd) {
                HashMap<Integer, Quest> quests = new HashMap<Integer, Quest>();
                for (QuestCategory category : QuestController.instance.categoriesSync.values()) {
                    for (Quest quest : category.quests.values()) {
                        quests.put(quest.id, quest);
                    }
                }
                QuestController.instance.categories = QuestController.instance.categoriesSync;
                QuestController.instance.quests = quests;
                QuestController.instance.categoriesSync = new HashMap();
            }
        } else if (this.type == 5) {
            if (!this.data.m_128456_()) {
                DialogCategory category = new DialogCategory();
                category.readNBT(this.data);
                DialogController.instance.categoriesSync.put(category.id, category);
            }
            if (this.syncEnd) {
                HashMap<Integer, Dialog> dialogs = new HashMap<Integer, Dialog>();
                for (DialogCategory category : DialogController.instance.categoriesSync.values()) {
                    for (Dialog dialog : category.dialogs.values()) {
                        dialogs.put(dialog.id, dialog);
                    }
                }
                DialogController.instance.categories = DialogController.instance.categoriesSync;
                DialogController.instance.dialogs = dialogs;
                DialogController.instance.categoriesSync = new HashMap();
            }
        } else if (this.type == 6) {
            ListTag list = this.data.m_128437_("Data", 10);
            for (int i = 0; i < list.size(); ++i) {
                RecipeCarpentry recipe = RecipeCarpentry.load(list.m_128728_(i));
                RecipeController.syncRecipes.put(recipe.m_6423_(), recipe);
            }
            if (this.syncEnd) {
                RecipeController.instance.globalRecipes = RecipeController.syncRecipes;
                RecipeController.instance.reloadGlobalRecipes();
                RecipeController.syncRecipes = new HashMap();
            }
        } else if (this.type == 7) {
            ListTag list = this.data.m_128437_("Data", 10);
            for (int i = 0; i < list.size(); ++i) {
                RecipeCarpentry recipe = RecipeCarpentry.load(list.m_128728_(i));
                RecipeController.syncRecipes.put(recipe.m_6423_(), recipe);
            }
            if (this.syncEnd) {
                RecipeController.instance.anvilRecipes = RecipeController.syncRecipes;
                RecipeController.syncRecipes = new HashMap();
            }
        } else if (this.type == 8) {
            ClientProxy.playerData.setNBT(this.data);
        }
    }

    public void clientSync(boolean syncEnd) {
    }
}

