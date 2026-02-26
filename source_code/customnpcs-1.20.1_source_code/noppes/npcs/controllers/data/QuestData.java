/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.controllers.data.Quest;

public class QuestData {
    public Quest quest;
    public boolean isCompleted;
    public CompoundTag extraData = new CompoundTag();

    public QuestData(Quest quest) {
        this.quest = quest;
    }

    public void addAdditionalSaveData(CompoundTag nbttagcompound) {
        nbttagcompound.m_128379_("QuestCompleted", this.isCompleted);
        nbttagcompound.m_128365_("ExtraData", (Tag)this.extraData);
    }

    public void readAdditionalSaveData(CompoundTag nbttagcompound) {
        this.isCompleted = nbttagcompound.m_128471_("QuestCompleted");
        this.extraData = nbttagcompound.m_128469_("ExtraData");
    }
}

