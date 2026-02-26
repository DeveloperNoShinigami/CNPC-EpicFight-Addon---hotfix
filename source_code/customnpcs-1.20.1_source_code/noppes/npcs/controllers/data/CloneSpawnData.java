/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 */
package noppes.npcs.controllers.data;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import noppes.npcs.controllers.ServerCloneController;

public class CloneSpawnData {
    public int tab;
    public String name;
    private long lastLoaded;
    private CompoundTag compound;

    public CloneSpawnData(int tab, String name) {
        this.name = name;
        this.tab = tab;
    }

    public CompoundTag getCompound() {
        if (this.lastLoaded < ServerCloneController.Instance.lastLoaded) {
            this.compound = ServerCloneController.Instance.getCloneData(null, this.name, this.tab);
            this.lastLoaded = ServerCloneController.Instance.lastLoaded;
        }
        return this.compound;
    }

    public static Map<Integer, CloneSpawnData> load(ListTag list) {
        HashMap<Integer, CloneSpawnData> data = new HashMap<Integer, CloneSpawnData>();
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag c = list.m_128728_(i);
            int tab = c.m_128451_("tab");
            String name = c.m_128461_("name");
            if (ServerCloneController.Instance != null && !ServerCloneController.Instance.hasClone(tab, name)) continue;
            data.put(c.m_128451_("slot"), new CloneSpawnData(tab, name));
        }
        return data;
    }

    public static ListTag save(Map<Integer, CloneSpawnData> data) {
        ListTag list = new ListTag();
        for (Map.Entry<Integer, CloneSpawnData> entry : data.entrySet()) {
            if (ServerCloneController.Instance != null && !ServerCloneController.Instance.hasClone(entry.getValue().tab, entry.getValue().name)) continue;
            CompoundTag c = new CompoundTag();
            c.m_128405_("slot", entry.getKey().intValue());
            c.m_128405_("tab", entry.getValue().tab);
            c.m_128359_("name", entry.getValue().name);
            list.add((Object)c);
        }
        return list;
    }
}

