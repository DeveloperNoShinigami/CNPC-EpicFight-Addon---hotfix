/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.NBTTags;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.quests.QuestInterface;

public class QuestKill
extends QuestInterface {
    public TreeMap<String, Integer> targets = new TreeMap();

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.targets = new TreeMap<String, Integer>(NBTTags.getStringIntegerMap(compound.m_128437_("QuestDialogs", 10)));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.m_128365_("QuestDialogs", (Tag)NBTTags.nbtStringIntegerMap(this.targets));
    }

    @Override
    public boolean isCompleted(Player player) {
        PlayerQuestData playerdata = PlayerData.get((Player)player).questData;
        QuestData data = playerdata.activeQuests.get(this.questId);
        if (data == null) {
            return false;
        }
        HashMap<String, Integer> killed = this.getKilled(data);
        if (killed.size() != this.targets.size()) {
            return false;
        }
        for (String entity : killed.keySet()) {
            if (this.targets.containsKey(entity) && this.targets.get(entity) <= killed.get(entity)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void handleComplete(Player player) {
    }

    public HashMap<String, Integer> getKilled(QuestData data) {
        return NBTTags.getStringIntegerMap(data.extraData.m_128437_("Killed", 10));
    }

    public void setKilled(QuestData data, HashMap<String, Integer> killed) {
        data.extraData.m_128365_("Killed", (Tag)NBTTags.nbtStringIntegerMap(killed));
    }

    @Override
    public IQuestObjective[] getObjectives(Player player) {
        ArrayList<QuestKillObjective> list = new ArrayList<QuestKillObjective>();
        for (Map.Entry<String, Integer> entry : this.targets.entrySet()) {
            list.add(new QuestKillObjective(player, entry.getKey(), entry.getValue()));
        }
        return list.toArray(new IQuestObjective[list.size()]);
    }

    class QuestKillObjective
    implements IQuestObjective {
        private final Player player;
        private final String entity;
        private String translationKey = null;
        private final int amount;

        public QuestKillObjective(Player player, String entity, int amount) {
            this.player = player;
            this.entity = entity;
            this.amount = amount;
            try {
                if (ForgeRegistries.ENTITY_TYPES.containsKey(new ResourceLocation(entity))) {
                    this.translationKey = ((EntityType)ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(entity))).m_20675_();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        @Override
        public int getProgress() {
            PlayerData data = PlayerData.get(this.player);
            PlayerQuestData playerdata = data.questData;
            QuestData questdata = playerdata.activeQuests.get(QuestKill.this.questId);
            HashMap<String, Integer> killed = QuestKill.this.getKilled(questdata);
            if (!killed.containsKey(this.entity)) {
                return 0;
            }
            return killed.get(this.entity);
        }

        @Override
        public void setProgress(int progress) {
            if (progress < 0 || progress > this.amount) {
                throw new CustomNPCsException("Progress has to be between 0 and " + this.amount, new Object[0]);
            }
            PlayerData data = PlayerData.get(this.player);
            PlayerQuestData playerdata = data.questData;
            QuestData questdata = playerdata.activeQuests.get(QuestKill.this.questId);
            HashMap<String, Integer> killed = QuestKill.this.getKilled(questdata);
            if (killed.containsKey(this.entity) && killed.get(this.entity) == progress) {
                return;
            }
            killed.put(this.entity, progress);
            QuestKill.this.setKilled(questdata, killed);
            data.questData.checkQuestCompletion(this.player, 2);
            data.questData.checkQuestCompletion(this.player, 4);
            data.updateClient = true;
        }

        @Override
        public int getMaxProgress() {
            return this.amount;
        }

        @Override
        public boolean isCompleted() {
            return this.getProgress() >= this.amount;
        }

        @Override
        public String getText() {
            return this.getMCText().getString();
        }

        @Override
        public Component getMCText() {
            if (this.translationKey == null) {
                return Component.m_237115_((String)this.entity).m_130946_(": " + this.getProgress() + "/" + this.getMaxProgress());
            }
            return Component.m_237115_((String)this.translationKey).m_130946_(": " + this.getProgress() + "/" + this.getMaxProgress());
        }
    }
}

