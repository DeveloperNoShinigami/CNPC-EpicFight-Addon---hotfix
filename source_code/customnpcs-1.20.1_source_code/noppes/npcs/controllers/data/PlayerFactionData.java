/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.controllers.data;

import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.EventHooks;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.event.PlayerEvent;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerScriptData;

public class PlayerFactionData {
    public HashMap<Integer, Integer> factionData = new HashMap();

    public void loadNBTData(CompoundTag compound) {
        HashMap<Integer, Integer> factionData = new HashMap<Integer, Integer>();
        if (compound == null) {
            return;
        }
        ListTag list = compound.m_128437_("FactionData", 10);
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag nbttagcompound = list.m_128728_(i);
            factionData.put(nbttagcompound.m_128451_("Faction"), nbttagcompound.m_128451_("Points"));
        }
        this.factionData = factionData;
    }

    public void saveNBTData(CompoundTag compound) {
        ListTag list = new ListTag();
        for (int faction : this.factionData.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Faction", faction);
            nbttagcompound.m_128405_("Points", this.factionData.get(faction).intValue());
            list.add((Object)nbttagcompound);
        }
        compound.m_128365_("FactionData", (Tag)list);
    }

    public int getFactionPoints(Player player, int factionId) {
        Faction faction = FactionController.instance.getFaction(factionId);
        if (faction == null) {
            return 0;
        }
        if (!this.factionData.containsKey(factionId)) {
            if (player.m_9236_().f_46443_) {
                this.factionData.put(factionId, faction.defaultPoints);
                return faction.defaultPoints;
            }
            PlayerScriptData handler = PlayerData.get((Player)player).scriptData;
            PlayerWrapper wrapper = (PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)player);
            PlayerEvent.FactionUpdateEvent event = new PlayerEvent.FactionUpdateEvent(wrapper, faction, faction.defaultPoints, true);
            EventHooks.OnPlayerFactionChange(handler, event);
            this.factionData.put(factionId, event.points);
            PlayerData data = PlayerData.get(player);
            data.updateClient = true;
        }
        return this.factionData.get(factionId);
    }

    public void increasePoints(Player player, int factionId, int points) {
        PlayerEvent.FactionUpdateEvent event;
        Faction faction = FactionController.instance.getFaction(factionId);
        if (faction == null || player == null || player.m_9236_().f_46443_) {
            return;
        }
        PlayerScriptData handler = PlayerData.get((Player)player).scriptData;
        PlayerWrapper wrapper = (PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)player);
        if (!this.factionData.containsKey(factionId)) {
            event = new PlayerEvent.FactionUpdateEvent(wrapper, faction, faction.defaultPoints, true);
            EventHooks.OnPlayerFactionChange(handler, event);
            this.factionData.put(factionId, event.points);
        }
        event = new PlayerEvent.FactionUpdateEvent(wrapper, faction, points, false);
        EventHooks.OnPlayerFactionChange(handler, event);
        this.factionData.put(factionId, this.factionData.get(factionId) + points);
    }

    public CompoundTag getPlayerGuiData() {
        CompoundTag compound = new CompoundTag();
        this.saveNBTData(compound);
        ListTag list = new ListTag();
        for (int id : this.factionData.keySet()) {
            Faction faction = FactionController.instance.getFaction(id);
            if (faction == null || faction.hideFaction) continue;
            CompoundTag com = new CompoundTag();
            faction.writeNBT(com);
            list.add((Object)com);
        }
        compound.m_128365_("FactionList", (Tag)list);
        return compound;
    }
}

