/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.controllers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.DialogCategory;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.QuestCategory;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketSync;

public class SyncController {
    public static void syncPlayer(ServerPlayer player) {
        ListTag list = new ListTag();
        for (Faction faction : FactionController.instance.factions.values()) {
            list.add((Object)faction.writeNBT(new CompoundTag()));
        }
        CompoundTag compound = new CompoundTag();
        compound.m_128365_("Data", (Tag)list);
        Packets.send(player, new PacketSync(1, compound, true));
        for (QuestCategory questCategory : QuestController.instance.categories.values()) {
            Packets.send(player, new PacketSync(3, questCategory.writeNBT(new CompoundTag()), false));
        }
        Packets.send(player, new PacketSync(3, new CompoundTag(), true));
        for (DialogCategory dialogCategory : DialogController.instance.categories.values()) {
            Packets.send(player, new PacketSync(5, dialogCategory.writeNBT(new CompoundTag()), false));
        }
        Packets.send(player, new PacketSync(5, new CompoundTag(), true));
        list = new ListTag();
        for (RecipeCarpentry recipeCarpentry : RecipeController.instance.globalRecipes.values()) {
            list.add((Object)recipeCarpentry.writeNBT());
            if (list.size() <= 10) continue;
            compound = new CompoundTag();
            compound.m_128365_("Data", (Tag)list);
            Packets.send(player, new PacketSync(6, compound, false));
            list = new ListTag();
        }
        compound = new CompoundTag();
        compound.m_128365_("Data", (Tag)list);
        Packets.send(player, new PacketSync(6, compound, true));
        list = new ListTag();
        for (RecipeCarpentry recipeCarpentry : RecipeController.instance.anvilRecipes.values()) {
            list.add((Object)recipeCarpentry.writeNBT());
            if (list.size() <= 10) continue;
            compound = new CompoundTag();
            compound.m_128365_("Data", (Tag)list);
            Packets.send(player, new PacketSync(7, compound, false));
            list = new ListTag();
        }
        compound = new CompoundTag();
        compound.m_128365_("Data", (Tag)list);
        Packets.send(player, new PacketSync(7, compound, true));
        PlayerData playerData = PlayerData.get((Player)player);
        Packets.send(player, new PacketSync(8, playerData.getNBT(), true));
    }

    public static void syncAllDialogs() {
        for (DialogCategory category : DialogController.instance.categories.values()) {
            Packets.sendAll(new PacketSync(5, category.writeNBT(new CompoundTag()), false));
        }
        Packets.sendAll(new PacketSync(5, new CompoundTag(), true));
    }

    public static void syncAllQuests() {
        for (QuestCategory category : QuestController.instance.categories.values()) {
            Packets.sendAll(new PacketSync(3, category.writeNBT(new CompoundTag()), false));
        }
        Packets.sendAll(new PacketSync(3, new CompoundTag(), true));
    }
}

