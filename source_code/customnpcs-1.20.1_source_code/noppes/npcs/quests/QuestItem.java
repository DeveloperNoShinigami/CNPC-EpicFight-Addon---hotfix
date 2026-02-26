/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.quests.QuestInterface;
import noppes.npcs.util.ValueUtil;

public class QuestItem
extends QuestInterface {
    public NpcMiscInventory items = new NpcMiscInventory(3);
    public boolean leaveItems = false;
    public boolean ignoreDamage = false;
    public boolean ignoreNBT = false;

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.items.setFromNBT(compound.m_128469_("Items"));
        this.leaveItems = compound.m_128471_("LeaveItems");
        this.ignoreDamage = compound.m_128471_("IgnoreDamage");
        this.ignoreNBT = compound.m_128471_("IgnoreNBT");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.m_128365_("Items", (Tag)this.items.getToNBT());
        compound.m_128379_("LeaveItems", this.leaveItems);
        compound.m_128379_("IgnoreDamage", this.ignoreDamage);
        compound.m_128379_("IgnoreNBT", this.ignoreNBT);
    }

    @Override
    public boolean isCompleted(Player player) {
        List<ItemStack> questItems = NoppesUtilPlayer.countStacks((Container)this.items, this.ignoreDamage, this.ignoreNBT);
        for (ItemStack reqItem : questItems) {
            if (NoppesUtilPlayer.compareItems(player, reqItem, this.ignoreDamage, this.ignoreNBT)) continue;
            return false;
        }
        return true;
    }

    public Map<ItemStack, Integer> getProgressSet(Player player) {
        HashMap<ItemStack, Integer> map = new HashMap<ItemStack, Integer>();
        List<ItemStack> questItems = NoppesUtilPlayer.countStacks((Container)this.items, this.ignoreDamage, this.ignoreNBT);
        for (ItemStack item : questItems) {
            if (NoppesUtilServer.IsItemStackNull(item)) continue;
            map.put(item, 0);
        }
        for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack item;
            item = player.m_150109_().m_8020_(i);
            if (NoppesUtilServer.IsItemStackNull(item)) continue;
            for (Map.Entry<ItemStack, Integer> questItem : map.entrySet()) {
                if (!NoppesUtilPlayer.compareItems(questItem.getKey(), item, this.ignoreDamage, this.ignoreNBT)) continue;
                map.put(questItem.getKey(), questItem.getValue() + item.m_41613_());
            }
        }
        return map;
    }

    @Override
    public void handleComplete(Player player) {
        if (this.leaveItems) {
            return;
        }
        block0: for (ItemStack questitem : this.items.items) {
            if (questitem.m_41619_()) continue;
            int stacksize = questitem.m_41613_();
            for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
                ItemStack item = player.m_150109_().m_8020_(i);
                if (NoppesUtilServer.IsItemStackNull(item) || !NoppesUtilPlayer.compareItems(item, questitem, this.ignoreDamage, this.ignoreNBT)) continue;
                int size = item.m_41613_();
                if (stacksize - size >= 0) {
                    player.m_150109_().m_6836_(i, ItemStack.f_41583_);
                    item.m_41620_(size);
                } else {
                    item.m_41620_(stacksize);
                }
                if ((stacksize -= size) <= 0) continue block0;
            }
        }
    }

    @Override
    public IQuestObjective[] getObjectives(Player player) {
        ArrayList<QuestItemObjective> list = new ArrayList<QuestItemObjective>();
        List<ItemStack> questItems = NoppesUtilPlayer.countStacks((Container)this.items, this.ignoreDamage, this.ignoreNBT);
        for (ItemStack stack : questItems) {
            if (stack.m_41619_()) continue;
            list.add(new QuestItemObjective(player, stack));
        }
        return list.toArray(new IQuestObjective[list.size()]);
    }

    class QuestItemObjective
    implements IQuestObjective {
        private final Player player;
        private final ItemStack questItem;

        public QuestItemObjective(Player player, ItemStack item) {
            this.player = player;
            this.questItem = item;
        }

        @Override
        public int getProgress() {
            int count = 0;
            for (int i = 0; i < this.player.m_150109_().m_6643_(); ++i) {
                ItemStack item = this.player.m_150109_().m_8020_(i);
                if (NoppesUtilServer.IsItemStackNull(item) || !NoppesUtilPlayer.compareItems(this.questItem, item, QuestItem.this.ignoreDamage, QuestItem.this.ignoreNBT)) continue;
                count += item.m_41613_();
            }
            return ValueUtil.CorrectInt(count, 0, this.questItem.m_41613_());
        }

        @Override
        public void setProgress(int progress) {
            throw new CustomNPCsException("Cant set the progress of ItemQuests", new Object[0]);
        }

        @Override
        public int getMaxProgress() {
            return this.questItem.m_41613_();
        }

        @Override
        public boolean isCompleted() {
            return NoppesUtilPlayer.compareItems(this.player, this.questItem, QuestItem.this.ignoreDamage, QuestItem.this.ignoreNBT);
        }

        @Override
        public String getText() {
            return this.getMCText().getString();
        }

        @Override
        public Component getMCText() {
            return Component.m_237113_((String)"").m_7220_(this.questItem.m_41786_()).m_130946_(": " + this.getProgress() + "/" + this.getMaxProgress());
        }
    }
}

