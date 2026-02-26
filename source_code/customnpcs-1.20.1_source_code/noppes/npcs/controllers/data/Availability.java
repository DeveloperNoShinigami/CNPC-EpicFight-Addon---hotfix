/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.ServerScoreboard
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.scores.Objective
 */
package noppes.npcs.controllers.data;

import java.util.HashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Objective;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ICompatibilty;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.handler.data.IAvailability;
import noppes.npcs.constants.EnumAvailabilityDialog;
import noppes.npcs.constants.EnumAvailabilityFaction;
import noppes.npcs.constants.EnumAvailabilityFactionType;
import noppes.npcs.constants.EnumAvailabilityQuest;
import noppes.npcs.constants.EnumAvailabilityScoreboard;
import noppes.npcs.constants.EnumDayTime;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerFactionData;

public class Availability
implements ICompatibilty,
IAvailability {
    public static HashSet<String> scores = new HashSet();
    public int version = VersionCompatibility.ModRev;
    public EnumAvailabilityDialog dialogAvailable = EnumAvailabilityDialog.Always;
    public EnumAvailabilityDialog dialog2Available = EnumAvailabilityDialog.Always;
    public EnumAvailabilityDialog dialog3Available = EnumAvailabilityDialog.Always;
    public EnumAvailabilityDialog dialog4Available = EnumAvailabilityDialog.Always;
    public int dialogId = -1;
    public int dialog2Id = -1;
    public int dialog3Id = -1;
    public int dialog4Id = -1;
    public EnumAvailabilityQuest questAvailable = EnumAvailabilityQuest.Always;
    public EnumAvailabilityQuest quest2Available = EnumAvailabilityQuest.Always;
    public EnumAvailabilityQuest quest3Available = EnumAvailabilityQuest.Always;
    public EnumAvailabilityQuest quest4Available = EnumAvailabilityQuest.Always;
    public int questId = -1;
    public int quest2Id = -1;
    public int quest3Id = -1;
    public int quest4Id = -1;
    public EnumDayTime daytime = EnumDayTime.Always;
    public int factionId = -1;
    public int faction2Id = -1;
    public EnumAvailabilityFactionType factionAvailable = EnumAvailabilityFactionType.Always;
    public EnumAvailabilityFactionType faction2Available = EnumAvailabilityFactionType.Always;
    public EnumAvailabilityFaction factionStance = EnumAvailabilityFaction.Friendly;
    public EnumAvailabilityFaction faction2Stance = EnumAvailabilityFaction.Friendly;
    public EnumAvailabilityScoreboard scoreboardType = EnumAvailabilityScoreboard.EQUAL;
    public EnumAvailabilityScoreboard scoreboard2Type = EnumAvailabilityScoreboard.EQUAL;
    public String scoreboardObjective = "";
    public String scoreboard2Objective = "";
    public int scoreboardValue = 1;
    public int scoreboard2Value = 1;
    public int minPlayerLevel = 0;
    private boolean hasOptions = false;

    public void load(CompoundTag compound) {
        this.version = compound.m_128451_("ModRev");
        VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
        this.dialogAvailable = EnumAvailabilityDialog.values()[compound.m_128451_("AvailabilityDialog")];
        this.dialog2Available = EnumAvailabilityDialog.values()[compound.m_128451_("AvailabilityDialog2")];
        this.dialog3Available = EnumAvailabilityDialog.values()[compound.m_128451_("AvailabilityDialog3")];
        this.dialog4Available = EnumAvailabilityDialog.values()[compound.m_128451_("AvailabilityDialog4")];
        this.dialogId = compound.m_128451_("AvailabilityDialogId");
        this.dialog2Id = compound.m_128451_("AvailabilityDialog2Id");
        this.dialog3Id = compound.m_128451_("AvailabilityDialog3Id");
        this.dialog4Id = compound.m_128451_("AvailabilityDialog4Id");
        this.questAvailable = EnumAvailabilityQuest.values()[compound.m_128451_("AvailabilityQuest")];
        this.quest2Available = EnumAvailabilityQuest.values()[compound.m_128451_("AvailabilityQuest2")];
        this.quest3Available = EnumAvailabilityQuest.values()[compound.m_128451_("AvailabilityQuest3")];
        this.quest4Available = EnumAvailabilityQuest.values()[compound.m_128451_("AvailabilityQuest4")];
        this.questId = compound.m_128451_("AvailabilityQuestId");
        this.quest2Id = compound.m_128451_("AvailabilityQuest2Id");
        this.quest3Id = compound.m_128451_("AvailabilityQuest3Id");
        this.quest4Id = compound.m_128451_("AvailabilityQuest4Id");
        this.setFactionAvailability(compound.m_128451_("AvailabilityFaction"));
        this.setFactionAvailabilityStance(compound.m_128451_("AvailabilityFactionStance"));
        this.setFaction2Availability(compound.m_128451_("AvailabilityFaction2"));
        this.setFaction2AvailabilityStance(compound.m_128451_("AvailabilityFaction2Stance"));
        this.factionId = compound.m_128451_("AvailabilityFactionId");
        this.faction2Id = compound.m_128451_("AvailabilityFaction2Id");
        this.scoreboardObjective = compound.m_128461_("AvailabilityScoreboardObjective");
        this.scoreboard2Objective = compound.m_128461_("AvailabilityScoreboard2Objective");
        this.initScore(this.scoreboardObjective);
        this.initScore(this.scoreboard2Objective);
        this.scoreboardType = EnumAvailabilityScoreboard.values()[compound.m_128451_("AvailabilityScoreboardType")];
        this.scoreboard2Type = EnumAvailabilityScoreboard.values()[compound.m_128451_("AvailabilityScoreboard2Type")];
        this.scoreboardValue = compound.m_128451_("AvailabilityScoreboardValue");
        this.scoreboard2Value = compound.m_128451_("AvailabilityScoreboard2Value");
        this.daytime = EnumDayTime.values()[compound.m_128451_("AvailabilityDayTime")];
        this.minPlayerLevel = compound.m_128451_("AvailabilityMinPlayerLevel");
        this.hasOptions = this.checkHasOptions();
    }

    private void initScore(String objective) {
        if (objective.isEmpty() || scores.contains(objective)) {
            return;
        }
        scores.add(objective);
        if (CustomNpcs.Server == null) {
            return;
        }
        for (ServerLevel level : CustomNpcs.Server.m_129785_()) {
            ServerScoreboard board = level.m_6188_();
            Objective so = board.m_83477_(objective);
            if (so == null) continue;
            board.m_136231_(so);
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.m_128405_("ModRev", this.version);
        compound.m_128405_("AvailabilityDialog", this.dialogAvailable.ordinal());
        compound.m_128405_("AvailabilityDialog2", this.dialog2Available.ordinal());
        compound.m_128405_("AvailabilityDialog3", this.dialog3Available.ordinal());
        compound.m_128405_("AvailabilityDialog4", this.dialog4Available.ordinal());
        compound.m_128405_("AvailabilityDialogId", this.dialogId);
        compound.m_128405_("AvailabilityDialog2Id", this.dialog2Id);
        compound.m_128405_("AvailabilityDialog3Id", this.dialog3Id);
        compound.m_128405_("AvailabilityDialog4Id", this.dialog4Id);
        compound.m_128405_("AvailabilityQuest", this.questAvailable.ordinal());
        compound.m_128405_("AvailabilityQuest2", this.quest2Available.ordinal());
        compound.m_128405_("AvailabilityQuest3", this.quest3Available.ordinal());
        compound.m_128405_("AvailabilityQuest4", this.quest4Available.ordinal());
        compound.m_128405_("AvailabilityQuestId", this.questId);
        compound.m_128405_("AvailabilityQuest2Id", this.quest2Id);
        compound.m_128405_("AvailabilityQuest3Id", this.quest3Id);
        compound.m_128405_("AvailabilityQuest4Id", this.quest4Id);
        compound.m_128405_("AvailabilityFaction", this.factionAvailable.ordinal());
        compound.m_128405_("AvailabilityFaction2", this.faction2Available.ordinal());
        compound.m_128405_("AvailabilityFactionStance", this.factionStance.ordinal());
        compound.m_128405_("AvailabilityFaction2Stance", this.faction2Stance.ordinal());
        compound.m_128405_("AvailabilityFactionId", this.factionId);
        compound.m_128405_("AvailabilityFaction2Id", this.faction2Id);
        compound.m_128359_("AvailabilityScoreboardObjective", this.scoreboardObjective);
        compound.m_128359_("AvailabilityScoreboard2Objective", this.scoreboard2Objective);
        compound.m_128405_("AvailabilityScoreboardType", this.scoreboardType.ordinal());
        compound.m_128405_("AvailabilityScoreboard2Type", this.scoreboard2Type.ordinal());
        compound.m_128405_("AvailabilityScoreboardValue", this.scoreboardValue);
        compound.m_128405_("AvailabilityScoreboard2Value", this.scoreboard2Value);
        compound.m_128405_("AvailabilityDayTime", this.daytime.ordinal());
        compound.m_128405_("AvailabilityMinPlayerLevel", this.minPlayerLevel);
        return compound;
    }

    public void setFactionAvailability(int value) {
        this.factionAvailable = EnumAvailabilityFactionType.values()[value];
        this.hasOptions = this.checkHasOptions();
    }

    public void setFaction2Availability(int value) {
        this.faction2Available = EnumAvailabilityFactionType.values()[value];
        this.hasOptions = this.checkHasOptions();
    }

    public void setFactionAvailabilityStance(int integer) {
        this.factionStance = EnumAvailabilityFaction.values()[integer];
    }

    public void setFaction2AvailabilityStance(int integer) {
        this.faction2Stance = EnumAvailabilityFaction.values()[integer];
    }

    public boolean isAvailable(Player player) {
        long time;
        if (this.daytime == EnumDayTime.Day && (time = player.m_9236_().m_46468_() % 24000L) > 12000L) {
            return false;
        }
        if (this.daytime == EnumDayTime.Night && (time = player.m_9236_().m_46468_() % 24000L) < 12000L) {
            return false;
        }
        if (!this.dialogAvailable(this.dialogId, this.dialogAvailable, player)) {
            return false;
        }
        if (!this.dialogAvailable(this.dialog2Id, this.dialog2Available, player)) {
            return false;
        }
        if (!this.dialogAvailable(this.dialog3Id, this.dialog3Available, player)) {
            return false;
        }
        if (!this.dialogAvailable(this.dialog4Id, this.dialog4Available, player)) {
            return false;
        }
        if (!this.questAvailable(this.questId, this.questAvailable, player)) {
            return false;
        }
        if (!this.questAvailable(this.quest2Id, this.quest2Available, player)) {
            return false;
        }
        if (!this.questAvailable(this.quest3Id, this.quest3Available, player)) {
            return false;
        }
        if (!this.questAvailable(this.quest4Id, this.quest4Available, player)) {
            return false;
        }
        if (!this.factionAvailable(this.factionId, this.factionStance, this.factionAvailable, player)) {
            return false;
        }
        if (!this.factionAvailable(this.faction2Id, this.faction2Stance, this.faction2Available, player)) {
            return false;
        }
        if (!this.scoreboardAvailable(player, this.scoreboardObjective, this.scoreboardType, this.scoreboardValue)) {
            return false;
        }
        if (!this.scoreboardAvailable(player, this.scoreboard2Objective, this.scoreboard2Type, this.scoreboard2Value)) {
            return false;
        }
        return player.f_36078_ >= this.minPlayerLevel;
    }

    private boolean scoreboardAvailable(Player player, String objective, EnumAvailabilityScoreboard type, int value) {
        if (objective.isEmpty()) {
            return true;
        }
        Objective sbObjective = player.m_36329_().m_83477_(objective);
        if (sbObjective == null) {
            return false;
        }
        if (!player.m_36329_().m_83461_(player.m_7755_().getString(), sbObjective)) {
            return false;
        }
        int i = player.m_36329_().m_83471_(player.m_7755_().getString(), sbObjective).m_83400_();
        if (type == EnumAvailabilityScoreboard.EQUAL) {
            return i == value;
        }
        if (type == EnumAvailabilityScoreboard.BIGGER) {
            return i > value;
        }
        return i < value;
    }

    private boolean factionAvailable(int id, EnumAvailabilityFaction stance, EnumAvailabilityFactionType available, Player player) {
        if (available == EnumAvailabilityFactionType.Always) {
            return true;
        }
        Faction faction = FactionController.instance.getFaction(id);
        if (faction == null) {
            return true;
        }
        PlayerFactionData data = PlayerData.get((Player)player).factionData;
        int points = data.getFactionPoints(player, id);
        EnumAvailabilityFaction current = EnumAvailabilityFaction.Neutral;
        if (points < faction.neutralPoints) {
            current = EnumAvailabilityFaction.Hostile;
        }
        if (points >= faction.friendlyPoints) {
            current = EnumAvailabilityFaction.Friendly;
        }
        if (available == EnumAvailabilityFactionType.Is && stance == current) {
            return true;
        }
        return available == EnumAvailabilityFactionType.IsNot && stance != current;
    }

    public boolean dialogAvailable(int id, EnumAvailabilityDialog en, Player player) {
        if (en == EnumAvailabilityDialog.Always) {
            return true;
        }
        boolean hasRead = PlayerData.get((Player)player).dialogData.dialogsRead.contains(id);
        if (hasRead && en == EnumAvailabilityDialog.After) {
            return true;
        }
        return !hasRead && en == EnumAvailabilityDialog.Before;
    }

    public boolean questAvailable(int id, EnumAvailabilityQuest en, Player player) {
        if (en == EnumAvailabilityQuest.Always) {
            return true;
        }
        if (en == EnumAvailabilityQuest.After && PlayerQuestController.isQuestFinished(player, id)) {
            return true;
        }
        if (en == EnumAvailabilityQuest.Before && !PlayerQuestController.isQuestFinished(player, id)) {
            return true;
        }
        if (en == EnumAvailabilityQuest.Active && PlayerQuestController.isQuestActive(player, id)) {
            return true;
        }
        if (en == EnumAvailabilityQuest.NotActive && !PlayerQuestController.isQuestActive(player, id)) {
            return true;
        }
        if (en == EnumAvailabilityQuest.Completed && PlayerQuestController.isQuestCompleted(player, id)) {
            return true;
        }
        return en == EnumAvailabilityQuest.CanStart && PlayerQuestController.canQuestBeAccepted(player, id);
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean isAvailable(IPlayer player) {
        return this.isAvailable((Player)player.getMCEntity());
    }

    @Override
    public int getDaytime() {
        return this.daytime.ordinal();
    }

    @Override
    public void setDaytime(int type) {
        this.daytime = EnumDayTime.values()[Mth.m_14045_((int)type, (int)0, (int)2)];
        this.hasOptions = this.checkHasOptions();
    }

    @Override
    public int getMinPlayerLevel() {
        return this.minPlayerLevel;
    }

    @Override
    public void setMinPlayerLevel(int level) {
        this.minPlayerLevel = level;
        this.hasOptions = this.checkHasOptions();
    }

    @Override
    public int getDialog(int i) {
        if (i < 0 && i > 3) {
            throw new CustomNPCsException(i + " isnt between 0 and 3", new Object[0]);
        }
        if (i == 0) {
            return this.dialogId;
        }
        if (i == 1) {
            return this.dialog2Id;
        }
        if (i == 2) {
            return this.dialog3Id;
        }
        return this.dialog4Id;
    }

    @Override
    public void setDialog(int i, int id, int type) {
        if (i < 0 && i > 3) {
            throw new CustomNPCsException(i + " isnt between 0 and 3", new Object[0]);
        }
        EnumAvailabilityDialog e = EnumAvailabilityDialog.values()[Mth.m_14045_((int)type, (int)0, (int)2)];
        if (i == 0) {
            this.dialogId = id;
            this.dialogAvailable = e;
        } else if (i == 1) {
            this.dialog2Id = id;
            this.dialog2Available = e;
        } else if (i == 2) {
            this.dialog3Id = id;
            this.dialog3Available = e;
        } else if (i == 3) {
            this.dialog4Id = id;
            this.dialog4Available = e;
        }
        this.hasOptions = this.checkHasOptions();
    }

    @Override
    public void removeDialog(int i) {
        if (i < 0 && i > 3) {
            throw new CustomNPCsException(i + " isnt between 0 and 3", new Object[0]);
        }
        if (i == 0) {
            this.dialogId = -1;
            this.dialogAvailable = EnumAvailabilityDialog.Always;
        } else if (i == 1) {
            this.dialog2Id = -1;
            this.dialog2Available = EnumAvailabilityDialog.Always;
        } else if (i == 2) {
            this.dialog3Id = -1;
            this.dialog3Available = EnumAvailabilityDialog.Always;
        } else if (i == 3) {
            this.dialog4Id = -1;
            this.dialog4Available = EnumAvailabilityDialog.Always;
        }
        this.hasOptions = this.checkHasOptions();
    }

    @Override
    public int getQuest(int i) {
        if (i < 0 && i > 3) {
            throw new CustomNPCsException(i + " isnt between 0 and 3", new Object[0]);
        }
        if (i == 0) {
            return this.questId;
        }
        if (i == 1) {
            return this.quest2Id;
        }
        if (i == 2) {
            return this.quest3Id;
        }
        return this.quest4Id;
    }

    @Override
    public void setQuest(int i, int id, int type) {
        if (i < 0 && i > 3) {
            throw new CustomNPCsException(i + " isnt between 0 and 3", new Object[0]);
        }
        EnumAvailabilityQuest e = EnumAvailabilityQuest.values()[Mth.m_14045_((int)type, (int)0, (int)5)];
        if (i == 0) {
            this.questId = id;
            this.questAvailable = e;
        } else if (i == 1) {
            this.quest2Id = id;
            this.quest2Available = e;
        } else if (i == 2) {
            this.quest3Id = id;
            this.quest3Available = e;
        } else if (i == 3) {
            this.quest4Id = id;
            this.quest4Available = e;
        }
        this.hasOptions = this.checkHasOptions();
    }

    @Override
    public void removeQuest(int i) {
        if (i < 0 && i > 3) {
            throw new CustomNPCsException(i + " isnt between 0 and 3", new Object[0]);
        }
        if (i == 0) {
            this.questId = -1;
            this.questAvailable = EnumAvailabilityQuest.Always;
        } else if (i == 1) {
            this.quest2Id = -1;
            this.quest2Available = EnumAvailabilityQuest.Always;
        } else if (i == 2) {
            this.quest3Id = -1;
            this.quest3Available = EnumAvailabilityQuest.Always;
        } else if (i == 3) {
            this.quest4Id = -1;
            this.quest4Available = EnumAvailabilityQuest.Always;
        }
        this.hasOptions = this.checkHasOptions();
    }

    @Override
    public void setFaction(int i, int id, int type, int stance) {
        if (i < 0 && i > 1) {
            throw new CustomNPCsException(i + " isnt between 0 and 1", new Object[0]);
        }
        EnumAvailabilityFactionType e = EnumAvailabilityFactionType.values()[Mth.m_14045_((int)type, (int)0, (int)2)];
        EnumAvailabilityFaction ee = EnumAvailabilityFaction.values()[Mth.m_14045_((int)stance, (int)0, (int)2)];
        if (i == 0) {
            this.factionId = id;
            this.factionAvailable = e;
            this.factionStance = ee;
        } else if (i == 1) {
            this.faction2Id = id;
            this.faction2Available = e;
            this.faction2Stance = ee;
        }
        this.hasOptions = this.checkHasOptions();
    }

    @Override
    public void setScoreboard(int i, String objective, int type, int value) {
        if (i < 0 && i > 1) {
            throw new CustomNPCsException(i + " isnt between 0 and 1", new Object[0]);
        }
        if (objective == null) {
            objective = "";
        }
        EnumAvailabilityScoreboard e = EnumAvailabilityScoreboard.values()[Mth.m_14045_((int)type, (int)0, (int)2)];
        if (i == 0) {
            this.scoreboardObjective = objective;
            this.scoreboardType = e;
            this.scoreboardValue = value;
        } else if (i == 1) {
            this.scoreboard2Objective = objective;
            this.scoreboard2Type = e;
            this.scoreboard2Value = value;
        }
        this.hasOptions = this.checkHasOptions();
    }

    @Override
    public void removeFaction(int i) {
        if (i < 0 && i > 1) {
            throw new CustomNPCsException(i + " isnt between 0 and 1", new Object[0]);
        }
        if (i == 0) {
            this.factionId = -1;
            this.factionAvailable = EnumAvailabilityFactionType.Always;
            this.factionStance = EnumAvailabilityFaction.Friendly;
        } else if (i == 1) {
            this.faction2Id = -1;
            this.faction2Available = EnumAvailabilityFactionType.Always;
            this.faction2Stance = EnumAvailabilityFaction.Friendly;
        }
        this.hasOptions = this.checkHasOptions();
    }

    private boolean checkHasOptions() {
        if (this.dialogAvailable != EnumAvailabilityDialog.Always || this.dialog2Available != EnumAvailabilityDialog.Always || this.dialog3Available != EnumAvailabilityDialog.Always || this.dialog4Available != EnumAvailabilityDialog.Always) {
            return true;
        }
        if (this.questAvailable != EnumAvailabilityQuest.Always || this.quest2Available != EnumAvailabilityQuest.Always || this.quest3Available != EnumAvailabilityQuest.Always || this.quest4Available != EnumAvailabilityQuest.Always) {
            return true;
        }
        if (this.daytime != EnumDayTime.Always || this.minPlayerLevel > 0) {
            return true;
        }
        if (this.factionAvailable != EnumAvailabilityFactionType.Always || this.faction2Available != EnumAvailabilityFactionType.Always) {
            return true;
        }
        return !this.scoreboardObjective.isEmpty() || !this.scoreboard2Objective.isEmpty();
    }

    public boolean hasOptions() {
        return this.hasOptions;
    }
}

