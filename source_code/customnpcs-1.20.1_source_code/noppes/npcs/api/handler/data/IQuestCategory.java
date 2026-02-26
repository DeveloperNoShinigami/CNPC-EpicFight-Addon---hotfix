/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.handler.data;

import java.util.List;
import noppes.npcs.api.handler.data.IQuest;

public interface IQuestCategory {
    public List<IQuest> quests();

    public String getName();

    public IQuest create();
}

