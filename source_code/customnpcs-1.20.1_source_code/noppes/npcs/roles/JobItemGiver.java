/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.controllers.GlobalDataController;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerItemGiverData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobItemGiver
extends JobInterface {
    public int cooldownType = 0;
    public int givingMethod = 0;
    public int cooldown = 10;
    public NpcMiscInventory inventory;
    public int itemGiverId = 0;
    public List<String> lines = new ArrayList<String>();
    private int ticks = 10;
    private List<Player> recentlyChecked = new ArrayList<Player>();
    private List<Player> toCheck;
    public Availability availability = new Availability();

    public JobItemGiver(EntityNPCInterface npc) {
        super(npc);
        this.inventory = new NpcMiscInventory(9);
        this.lines.add("Have these items {player}");
    }

    @Override
    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128405_("igCooldownType", this.cooldownType);
        nbttagcompound.m_128405_("igGivingMethod", this.givingMethod);
        nbttagcompound.m_128405_("igCooldown", this.cooldown);
        nbttagcompound.m_128405_("ItemGiverId", this.itemGiverId);
        nbttagcompound.m_128365_("igLines", (Tag)NBTTags.nbtStringList(this.lines));
        nbttagcompound.m_128365_("igJobInventory", (Tag)this.inventory.getToNBT());
        nbttagcompound.m_128365_("igAvailability", (Tag)this.availability.save(new CompoundTag()));
        return nbttagcompound;
    }

    @Override
    public void load(CompoundTag nbttagcompound) {
        this.itemGiverId = nbttagcompound.m_128451_("ItemGiverId");
        this.cooldownType = nbttagcompound.m_128451_("igCooldownType");
        this.givingMethod = nbttagcompound.m_128451_("igGivingMethod");
        this.cooldown = nbttagcompound.m_128451_("igCooldown");
        this.lines = NBTTags.getStringList(nbttagcompound.m_128437_("igLines", 10));
        this.inventory.setFromNBT(nbttagcompound.m_128469_("igJobInventory"));
        if (this.itemGiverId == 0 && GlobalDataController.instance != null) {
            this.itemGiverId = GlobalDataController.instance.incrementItemGiverId();
        }
        this.availability.load(nbttagcompound.m_128469_("igAvailability"));
    }

    public ListTag newHashMapNBTList(HashMap<String, Long> lines) {
        ListTag nbttaglist = new ListTag();
        HashMap<String, Long> lines2 = lines;
        for (String s : lines2.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128359_("Line", s);
            nbttagcompound.m_128356_("Time", lines.get(s).longValue());
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public HashMap<String, Long> getNBTLines(ListTag tagList) {
        HashMap<String, Long> map = new HashMap<String, Long>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            String line = nbttagcompound.m_128461_("Line");
            long time = nbttagcompound.m_128454_("Time");
            map.put(line, time);
        }
        return map;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean giveItems(Player player) {
        PlayerItemGiverData data = PlayerData.get((Player)player).itemgiverData;
        if (!this.canPlayerInteract(data)) {
            return false;
        }
        Vector<ItemStack> items = new Vector<ItemStack>();
        Vector<Object> toGive = new Vector<ItemStack>();
        for (Object is : this.inventory.items) {
            if (is.m_41619_()) continue;
            items.add(is.m_41777_());
        }
        if (items.isEmpty()) {
            return false;
        }
        if (this.isAllGiver()) {
            toGive = items;
        } else if (this.isRemainingGiver()) {
            for (Object is : items) {
                if (this.playerHasItem(player, is.m_41720_())) continue;
                toGive.add((ItemStack)is);
            }
        } else if (this.isRandomGiver()) {
            toGive.add(((ItemStack)items.get(this.npc.m_9236_().f_46441_.m_188503_(items.size()))).m_41777_());
        } else if (this.isGiverWhenNotOwnedAny()) {
            boolean ownsItems = false;
            for (ItemStack is : items) {
                if (!this.playerHasItem(player, is.m_41720_())) continue;
                return false;
            }
            if (ownsItems) return false;
            toGive = items;
        } else if (this.isChainedGiver()) {
            int itemIndex = data.getItemIndex(this);
            int i = 0;
            for (ItemStack item : this.inventory.items) {
                if (i == itemIndex) {
                    toGive.add(item);
                    break;
                }
                ++i;
            }
        }
        if (toGive.isEmpty()) {
            return false;
        }
        if (!this.givePlayerItems(player, toGive)) return false;
        if (!this.lines.isEmpty()) {
            this.npc.say(player, new Line(this.lines.get(this.npc.m_217043_().m_188503_(this.lines.size()))));
        }
        if (this.isDaily()) {
            data.setTime(this, this.getDay());
        } else {
            data.setTime(this, System.currentTimeMillis());
        }
        if (!this.isChainedGiver()) return true;
        data.setItemIndex(this, (data.getItemIndex(this) + 1) % this.inventory.items.size());
        return true;
    }

    private int getDay() {
        return (int)(this.npc.m_9236_().m_46467_() / 24000L);
    }

    private boolean canPlayerInteract(PlayerItemGiverData data) {
        if (this.inventory.items.isEmpty()) {
            return false;
        }
        if (this.isOnTimer()) {
            if (!data.hasInteractedBefore(this)) {
                return true;
            }
            return data.getTime(this) + (long)(this.cooldown * 1000) < System.currentTimeMillis();
        }
        if (this.isGiveOnce()) {
            return !data.hasInteractedBefore(this);
        }
        if (this.isDaily()) {
            if (!data.hasInteractedBefore(this)) {
                return true;
            }
            return (long)this.getDay() > data.getTime(this);
        }
        return false;
    }

    private boolean givePlayerItems(Player player, Vector<ItemStack> toGive) {
        if (toGive.isEmpty()) {
            return false;
        }
        if (this.freeInventorySlots(player) < toGive.size()) {
            return false;
        }
        for (ItemStack is : toGive) {
            this.npc.givePlayerItem(player, is);
        }
        return true;
    }

    private boolean playerHasItem(Player player, Item item) {
        for (ItemStack is : player.m_150109_().f_35974_) {
            if (is.m_41619_() || is.m_41720_() != item) continue;
            return true;
        }
        for (ItemStack is : player.m_150109_().f_35975_) {
            if (is.m_41619_() || is.m_41720_() != item) continue;
            return true;
        }
        return false;
    }

    private int freeInventorySlots(Player player) {
        int i = 0;
        for (ItemStack is : player.m_150109_().f_35974_) {
            if (!NoppesUtilServer.IsItemStackNull(is)) continue;
            ++i;
        }
        return i;
    }

    private boolean isRandomGiver() {
        return this.givingMethod == 0;
    }

    private boolean isAllGiver() {
        return this.givingMethod == 1;
    }

    private boolean isRemainingGiver() {
        return this.givingMethod == 2;
    }

    private boolean isGiverWhenNotOwnedAny() {
        return this.givingMethod == 3;
    }

    private boolean isChainedGiver() {
        return this.givingMethod == 4;
    }

    public boolean isOnTimer() {
        return this.cooldownType == 0;
    }

    private boolean isGiveOnce() {
        return this.cooldownType == 1;
    }

    private boolean isDaily() {
        return this.cooldownType == 2;
    }

    @Override
    public boolean aiShouldExecute() {
        if (this.npc.isAttacking()) {
            return false;
        }
        --this.ticks;
        if (this.ticks > 0) {
            return false;
        }
        this.ticks = 10;
        this.toCheck = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_(3.0, 3.0, 3.0));
        this.toCheck.removeAll(this.recentlyChecked);
        List listMax = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_(10.0, 10.0, 10.0));
        this.recentlyChecked.retainAll(listMax);
        this.recentlyChecked.addAll(this.toCheck);
        return this.toCheck.size() > 0;
    }

    @Override
    public boolean aiContinueExecute() {
        return false;
    }

    @Override
    public void aiStartExecuting() {
        for (Player player : this.toCheck) {
            if (!this.npc.canNpcSee((Entity)player) || !this.availability.isAvailable(player)) continue;
            this.recentlyChecked.add(player);
            this.interact(player);
        }
    }

    @Override
    public void killed() {
    }

    private boolean interact(Player player) {
        if (!this.giveItems(player)) {
            this.npc.say(player, this.npc.advanced.getInteractLine());
        }
        return true;
    }

    @Override
    public void delete() {
    }

    @Override
    public int getType() {
        return 4;
    }
}

