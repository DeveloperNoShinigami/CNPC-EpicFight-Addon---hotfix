/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.Container
 *  net.minecraft.world.ContainerHelper
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.controllers.data;

import java.util.ArrayList;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.IContainer;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.data.IPlayerMail;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Quest;

public class PlayerMail
implements IPlayerMail,
Container {
    public String subject = "";
    public String sender = "";
    public CompoundTag message = new CompoundTag();
    public long time = 0L;
    public boolean beenRead = false;
    public int questId = -1;
    public NonNullList<ItemStack> items = NonNullList.m_122780_((int)4, (Object)ItemStack.f_41583_);
    public long timePast;

    public void readNBT(CompoundTag compound) {
        this.subject = compound.m_128461_("Subject");
        this.sender = compound.m_128461_("Sender");
        this.time = compound.m_128454_("Time");
        this.beenRead = compound.m_128471_("BeenRead");
        this.message = compound.m_128469_("Message");
        this.timePast = compound.m_128454_("TimePast");
        if (compound.m_128441_("MailQuest")) {
            this.questId = compound.m_128451_("MailQuest");
        }
        this.items.clear();
        ListTag nbttaglist = compound.m_128437_("MailItems", 10);
        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound1 = nbttaglist.m_128728_(i);
            int j = nbttagcompound1.m_128445_("Slot") & 0xFF;
            if (j < 0 || j >= this.items.size()) continue;
            this.items.set(j, (Object)ItemStack.m_41712_((CompoundTag)nbttagcompound1));
        }
    }

    public CompoundTag writeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.m_128359_("Subject", this.subject);
        compound.m_128359_("Sender", this.sender);
        compound.m_128356_("Time", this.time);
        compound.m_128379_("BeenRead", this.beenRead);
        compound.m_128365_("Message", (Tag)this.message);
        compound.m_128356_("TimePast", System.currentTimeMillis() - this.time);
        compound.m_128405_("MailQuest", this.questId);
        if (this.hasQuest()) {
            compound.m_128359_("MailQuestTitle", this.getQuest().title);
        }
        ListTag nbttaglist = new ListTag();
        for (int i = 0; i < this.items.size(); ++i) {
            if (((ItemStack)this.items.get(i)).m_41619_()) continue;
            CompoundTag nbttagcompound1 = new CompoundTag();
            nbttagcompound1.m_128344_("Slot", (byte)i);
            ((ItemStack)this.items.get(i)).m_41739_(nbttagcompound1);
            nbttaglist.add((Object)nbttagcompound1);
        }
        compound.m_128365_("MailItems", (Tag)nbttaglist);
        return compound;
    }

    public boolean isValid() {
        return !this.subject.isEmpty() && !this.message.m_128456_() && !this.sender.isEmpty();
    }

    public boolean hasQuest() {
        return this.getQuest() != null;
    }

    @Override
    public Quest getQuest() {
        return QuestController.instance != null ? QuestController.instance.quests.get(this.questId) : null;
    }

    public int m_6643_() {
        return 4;
    }

    public int m_6893_() {
        return 64;
    }

    public ItemStack m_8020_(int i) {
        return (ItemStack)this.items.get(i);
    }

    public ItemStack m_7407_(int index, int count) {
        ItemStack itemstack = ContainerHelper.m_18969_(this.items, (int)index, (int)count);
        if (!itemstack.m_41619_()) {
            this.m_6596_();
        }
        return itemstack;
    }

    public ItemStack m_8016_(int var1) {
        return (ItemStack)this.items.set(var1, (Object)ItemStack.f_41583_);
    }

    public void m_6836_(int index, ItemStack stack) {
        this.items.set(index, (Object)stack);
        if (stack.m_41613_() > this.m_6893_()) {
            stack.m_41764_(this.m_6893_());
        }
        this.m_6596_();
    }

    public void m_6596_() {
    }

    public boolean m_6542_(Player var1) {
        return true;
    }

    public void m_5856_(Player player) {
    }

    public void m_5785_(Player player) {
    }

    public boolean m_7013_(int var1, ItemStack var2) {
        return true;
    }

    public PlayerMail copy() {
        PlayerMail mail = new PlayerMail();
        mail.readNBT(this.writeNBT());
        return mail;
    }

    public boolean m_7983_() {
        for (int slot = 0; slot < this.m_6643_(); ++slot) {
            ItemStack item = this.m_8020_(slot);
            if (NoppesUtilServer.IsItemStackNull(item) || item.m_41619_()) continue;
            return false;
        }
        return true;
    }

    @Override
    public String getSender() {
        return this.sender;
    }

    @Override
    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String[] getText() {
        ArrayList<String> list = new ArrayList<String>();
        ListTag pages = this.message.m_128437_("pages", 8);
        for (int i = 0; i < pages.size(); ++i) {
            list.add(pages.m_128778_(i));
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public void setText(String[] pages) {
        ListTag list = new ListTag();
        if (pages != null && pages.length > 0) {
            for (String page : pages) {
                list.add((Object)StringTag.m_129297_((String)page));
            }
        }
        this.message.m_128365_("pages", (Tag)list);
    }

    @Override
    public void setQuest(int id) {
        this.questId = id;
    }

    @Override
    public IContainer getContainer() {
        return NpcAPI.Instance().getIContainer(this);
    }

    public void m_6211_() {
    }
}

