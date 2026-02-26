/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.controllers.data;

import java.util.HashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NBTTags;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.handler.data.IFaction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerFactionData;
import noppes.npcs.entity.EntityNPCInterface;

public class Faction
implements IFaction {
    public String name = "";
    public int color = Integer.parseInt("FF00", 16);
    public HashSet<Integer> attackFactions;
    public int id = -1;
    public int neutralPoints = 500;
    public int friendlyPoints = 1500;
    public int defaultPoints = 1000;
    public boolean hideFaction = false;
    public boolean getsAttacked = false;

    public Faction() {
        this.attackFactions = new HashSet();
    }

    public Faction(int id, String name, int color, int defaultPoints) {
        this.name = name;
        this.color = color;
        this.defaultPoints = defaultPoints;
        this.id = id;
        this.attackFactions = new HashSet();
    }

    public static String formatName(String name) {
        name = name.toLowerCase().trim();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public void readNBT(CompoundTag compound) {
        this.name = compound.m_128461_("Name");
        this.color = compound.m_128451_("Color");
        this.id = compound.m_128451_("Slot");
        this.neutralPoints = compound.m_128451_("NeutralPoints");
        this.friendlyPoints = compound.m_128451_("FriendlyPoints");
        this.defaultPoints = compound.m_128451_("DefaultPoints");
        this.hideFaction = compound.m_128471_("HideFaction");
        this.getsAttacked = compound.m_128471_("GetsAttacked");
        this.attackFactions = NBTTags.getIntegerSet(compound.m_128437_("AttackFactions", 10));
    }

    public CompoundTag writeNBT(CompoundTag compound) {
        compound.m_128405_("Slot", this.id);
        compound.m_128359_("Name", this.name);
        compound.m_128405_("Color", this.color);
        compound.m_128405_("NeutralPoints", this.neutralPoints);
        compound.m_128405_("FriendlyPoints", this.friendlyPoints);
        compound.m_128405_("DefaultPoints", this.defaultPoints);
        compound.m_128379_("HideFaction", this.hideFaction);
        compound.m_128379_("GetsAttacked", this.getsAttacked);
        compound.m_128365_("AttackFactions", (Tag)NBTTags.nbtIntegerCollection(this.attackFactions));
        return compound;
    }

    public boolean isFriendlyToPlayer(Player player) {
        PlayerFactionData data = PlayerData.get((Player)player).factionData;
        return data.getFactionPoints(player, this.id) >= this.friendlyPoints;
    }

    public boolean isAggressiveToPlayer(Player player) {
        if (player.m_150110_().f_35937_) {
            return false;
        }
        PlayerFactionData data = PlayerData.get((Player)player).factionData;
        return data.getFactionPoints(player, this.id) < this.neutralPoints;
    }

    public boolean isNeutralToPlayer(Player player) {
        PlayerFactionData data = PlayerData.get((Player)player).factionData;
        int points = data.getFactionPoints(player, this.id);
        return points >= this.neutralPoints && points < this.friendlyPoints;
    }

    public boolean isAggressiveToNpc(EntityNPCInterface entity) {
        return this.attackFactions.contains(entity.faction.id);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getDefaultPoints() {
        return this.defaultPoints;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public int playerStatus(IPlayer player) {
        PlayerFactionData data = PlayerData.get(player.getMCEntity()).factionData;
        int points = data.getFactionPoints((Player)player.getMCEntity(), this.id);
        if (points >= this.friendlyPoints) {
            return 1;
        }
        if (points < this.neutralPoints) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean hostileToNpc(ICustomNpc npc) {
        return this.attackFactions.contains(npc.getFaction().getId());
    }

    @Override
    public void setDefaultPoints(int points) {
        this.defaultPoints = points;
    }

    @Override
    public boolean hostileToFaction(int factionId) {
        return this.attackFactions.contains(factionId);
    }

    @Override
    public int[] getHostileList() {
        int[] a = new int[this.attackFactions.size()];
        int i = 0;
        for (Integer val : this.attackFactions) {
            a[i++] = val;
        }
        return a;
    }

    @Override
    public void addHostile(int id) {
        if (this.attackFactions.contains(id)) {
            throw new CustomNPCsException("Faction " + this.id + " is already hostile to " + id, new Object[0]);
        }
        this.attackFactions.add(id);
    }

    @Override
    public void removeHostile(int id) {
        this.attackFactions.remove(id);
    }

    @Override
    public boolean hasHostile(int id) {
        return this.attackFactions.contains(id);
    }

    @Override
    public boolean getIsHidden() {
        return this.hideFaction;
    }

    @Override
    public void setIsHidden(boolean bo) {
        this.hideFaction = bo;
    }

    @Override
    public boolean getAttackedByMobs() {
        return this.getsAttacked;
    }

    @Override
    public void setAttackedByMobs(boolean bo) {
        this.getsAttacked = bo;
    }

    @Override
    public void save() {
        FactionController.instance.saveFaction(this);
    }
}

