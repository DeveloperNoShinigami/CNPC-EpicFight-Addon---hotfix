/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.controllers.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerFactionData;

public class FactionOptions {
    public boolean decreaseFactionPoints = false;
    public boolean decreaseFaction2Points = false;
    public int factionId = -1;
    public int faction2Id = -1;
    public int factionPoints = 100;
    public int faction2Points = 100;

    public void load(CompoundTag compound) {
        this.factionId = compound.m_128451_("OptionFactions1");
        this.faction2Id = compound.m_128451_("OptionFactions2");
        this.decreaseFactionPoints = compound.m_128471_("DecreaseFaction1Points");
        this.decreaseFaction2Points = compound.m_128471_("DecreaseFaction2Points");
        this.factionPoints = compound.m_128451_("OptionFaction1Points");
        this.faction2Points = compound.m_128451_("OptionFaction2Points");
    }

    public CompoundTag save(CompoundTag par1CompoundTag) {
        par1CompoundTag.m_128405_("OptionFactions1", this.factionId);
        par1CompoundTag.m_128405_("OptionFactions2", this.faction2Id);
        par1CompoundTag.m_128405_("OptionFaction1Points", this.factionPoints);
        par1CompoundTag.m_128405_("OptionFaction2Points", this.faction2Points);
        par1CompoundTag.m_128379_("DecreaseFaction1Points", this.decreaseFactionPoints);
        par1CompoundTag.m_128379_("DecreaseFaction2Points", this.decreaseFaction2Points);
        return par1CompoundTag;
    }

    public boolean hasFaction(int id) {
        return this.factionId == id || this.faction2Id == id;
    }

    public void addPoints(Player player) {
        if (this.factionId < 0 && this.faction2Id < 0) {
            return;
        }
        PlayerData playerdata = PlayerData.get(player);
        PlayerFactionData data = playerdata.factionData;
        if (this.factionId >= 0 && this.factionPoints > 0) {
            this.addPoints(player, data, this.factionId, this.decreaseFactionPoints, this.factionPoints);
        }
        if (this.faction2Id >= 0 && this.faction2Points > 0) {
            this.addPoints(player, data, this.faction2Id, this.decreaseFaction2Points, this.faction2Points);
        }
        playerdata.updateClient = true;
    }

    private void addPoints(Player player, PlayerFactionData data, int factionId, boolean decrease, int points) {
        Faction faction = FactionController.instance.getFaction(factionId);
        if (faction == null) {
            return;
        }
        if (!faction.hideFaction) {
            String message = decrease ? "faction.decreasepoints" : "faction.increasepoints";
            player.m_213846_((Component)Component.m_237110_((String)message, (Object[])new Object[]{faction.name, points}));
        }
        data.increasePoints(player, factionId, decrease ? -points : points);
    }
}

