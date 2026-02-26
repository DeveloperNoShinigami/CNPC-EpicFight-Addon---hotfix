/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.quests;

import java.util.ArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.quests.QuestInterface;

public class QuestLocation
extends QuestInterface {
    public String location = "";
    public String location2 = "";
    public String location3 = "";

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.location = compound.m_128461_("QuestLocation");
        this.location2 = compound.m_128461_("QuestLocation2");
        this.location3 = compound.m_128461_("QuestLocation3");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.m_128359_("QuestLocation", this.location);
        compound.m_128359_("QuestLocation2", this.location2);
        compound.m_128359_("QuestLocation3", this.location3);
    }

    @Override
    public boolean isCompleted(Player player) {
        PlayerQuestData playerdata = PlayerData.get((Player)player).questData;
        QuestData data = playerdata.activeQuests.get(this.questId);
        if (data == null) {
            return false;
        }
        return this.getFound(data, 0);
    }

    @Override
    public void handleComplete(Player player) {
    }

    public boolean getFound(QuestData data, int i) {
        if (i == 1) {
            return data.extraData.m_128471_("LocationFound");
        }
        if (i == 2) {
            return data.extraData.m_128471_("Location2Found");
        }
        if (i == 3) {
            return data.extraData.m_128471_("Location3Found");
        }
        if (!this.location.isEmpty() && !data.extraData.m_128471_("LocationFound")) {
            return false;
        }
        if (!this.location2.isEmpty() && !data.extraData.m_128471_("Location2Found")) {
            return false;
        }
        return this.location3.isEmpty() || data.extraData.m_128471_("Location3Found");
    }

    public boolean setFound(QuestData data, String location) {
        if (location.equalsIgnoreCase(this.location) && !data.extraData.m_128471_("LocationFound")) {
            data.extraData.m_128379_("LocationFound", true);
            return true;
        }
        if (location.equalsIgnoreCase(this.location2) && !data.extraData.m_128471_("LocationFound2")) {
            data.extraData.m_128379_("Location2Found", true);
            return true;
        }
        if (location.equalsIgnoreCase(this.location3) && !data.extraData.m_128471_("LocationFound3")) {
            data.extraData.m_128379_("Location3Found", true);
            return true;
        }
        return false;
    }

    @Override
    public IQuestObjective[] getObjectives(Player player) {
        ArrayList<QuestLocationObjective> list = new ArrayList<QuestLocationObjective>();
        if (!this.location.isEmpty()) {
            list.add(new QuestLocationObjective(player, this.location, "LocationFound"));
        }
        if (!this.location2.isEmpty()) {
            list.add(new QuestLocationObjective(player, this.location2, "Location2Found"));
        }
        if (!this.location3.isEmpty()) {
            list.add(new QuestLocationObjective(player, this.location3, "Location3Found"));
        }
        return list.toArray(new IQuestObjective[list.size()]);
    }

    class QuestLocationObjective
    implements IQuestObjective {
        private final Player player;
        private final String location;
        private final String nbtName;

        public QuestLocationObjective(Player player, String location, String nbtName) {
            this.player = player;
            this.location = location;
            this.nbtName = nbtName;
        }

        @Override
        public int getProgress() {
            return this.isCompleted() ? 1 : 0;
        }

        @Override
        public void setProgress(int progress) {
            if (progress < 0 || progress > 1) {
                throw new CustomNPCsException("Progress has to be 0 or 1", new Object[0]);
            }
            PlayerData data = PlayerData.get(this.player);
            QuestData questData = data.questData.activeQuests.get(QuestLocation.this.questId);
            boolean completed = questData.extraData.m_128471_(this.nbtName);
            if (completed && progress == 1 || !completed && progress == 0) {
                return;
            }
            questData.extraData.m_128379_(this.nbtName, progress == 1);
            data.questData.checkQuestCompletion(this.player, 3);
            data.updateClient = true;
        }

        @Override
        public int getMaxProgress() {
            return 1;
        }

        @Override
        public boolean isCompleted() {
            PlayerData data = PlayerData.get(this.player);
            QuestData questData = data.questData.activeQuests.get(QuestLocation.this.questId);
            return questData.extraData.m_128471_(this.nbtName);
        }

        @Override
        public String getText() {
            return this.getMCText().getString();
        }

        @Override
        public Component getMCText() {
            return Component.m_237115_((String)this.location).m_7220_((Component)Component.m_237115_((String)(this.isCompleted() ? "quest.found" : "quest.notfound")));
        }
    }
}

