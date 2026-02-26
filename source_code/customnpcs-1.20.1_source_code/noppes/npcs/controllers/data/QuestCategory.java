/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.api.handler.data.IQuest;
import noppes.npcs.api.handler.data.IQuestCategory;
import noppes.npcs.controllers.data.Quest;

public class QuestCategory
implements IQuestCategory {
    public HashMap<Integer, Quest> quests = new HashMap();
    public int id = -1;
    public String title = "";

    public void readNBT(CompoundTag nbttagcompound) {
        this.id = nbttagcompound.m_128451_("Slot");
        this.title = nbttagcompound.m_128461_("Title");
        ListTag dialogsList = nbttagcompound.m_128437_("Dialogs", 10);
        if (dialogsList != null) {
            for (int ii = 0; ii < dialogsList.size(); ++ii) {
                CompoundTag nbttagcompound2 = dialogsList.m_128728_(ii);
                Quest quest = new Quest(this);
                quest.readNBT(nbttagcompound2);
                this.quests.put(quest.id, quest);
            }
        }
    }

    public CompoundTag writeNBT(CompoundTag nbttagcompound) {
        nbttagcompound.m_128405_("Slot", this.id);
        nbttagcompound.m_128359_("Title", this.title);
        ListTag dialogs = new ListTag();
        for (int dialogId : this.quests.keySet()) {
            Quest quest = this.quests.get(dialogId);
            dialogs.add((Object)quest.save(new CompoundTag()));
        }
        nbttagcompound.m_128365_("Dialogs", (Tag)dialogs);
        return nbttagcompound;
    }

    @Override
    public List<IQuest> quests() {
        return new ArrayList<IQuest>(this.quests.values());
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public IQuest create() {
        return new Quest(this);
    }
}

