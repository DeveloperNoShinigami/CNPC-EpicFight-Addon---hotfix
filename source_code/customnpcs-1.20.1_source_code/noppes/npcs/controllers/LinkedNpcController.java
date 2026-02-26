/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NBTTags;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.common.util.LogWriter;
import noppes.npcs.util.NBTJsonUtil;

public class LinkedNpcController {
    public static LinkedNpcController Instance;
    public List<LinkedData> list = new ArrayList<LinkedData>();

    public LinkedNpcController() {
        Instance = this;
        this.load();
    }

    private void load() {
        try {
            this.loadNpcs();
        }
        catch (Exception e) {
            LogWriter.except(e);
        }
    }

    public File getDir() {
        File dir = new File(CustomNpcs.getLevelSaveDirectory(), "linkednpcs");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    private void loadNpcs() {
        LogWriter.info("Loading Linked Npcs");
        File dir = this.getDir();
        if (dir.exists()) {
            ArrayList<LinkedData> list = new ArrayList<LinkedData>();
            for (File file : dir.listFiles()) {
                if (!file.getName().endsWith(".json")) continue;
                try {
                    CompoundTag compound = NBTJsonUtil.LoadFile(file);
                    LinkedData linked = new LinkedData();
                    linked.setNBT(compound);
                    list.add(linked);
                }
                catch (Exception e) {
                    LogWriter.error("Error loading: " + file.getAbsolutePath(), e);
                }
            }
            this.list = list;
        }
        LogWriter.info("Done loading Linked Npcs");
    }

    public void save() {
        for (LinkedData npc : this.list) {
            try {
                this.saveNpc(npc);
            }
            catch (IOException e) {
                LogWriter.except(e);
            }
        }
    }

    private void saveNpc(LinkedData npc) throws IOException {
        File file = new File(this.getDir(), npc.name + ".json_new");
        File file1 = new File(this.getDir(), npc.name + ".json");
        try {
            NBTJsonUtil.SaveFile(file, npc.getNBT());
            if (file1.exists()) {
                file1.delete();
            }
            file.renameTo(file1);
        }
        catch (NBTJsonUtil.JsonException e) {
            LogWriter.except(e);
        }
    }

    public void loadNpcData(EntityNPCInterface npc) {
        if (npc.linkedName.isEmpty()) {
            return;
        }
        LinkedData data = this.getData(npc.linkedName);
        if (data == null) {
            npc.linkedLast = 0L;
            npc.linkedName = "";
            npc.linkedData = null;
        } else {
            npc.linkedData = data;
            if (npc.m_20185_() == 0.0 && npc.m_20186_() == 0.0 && npc.m_20189_() == 0.0) {
                return;
            }
            npc.linkedLast = data.time;
            List<int[]> points = npc.ais.getMovingPath();
            CompoundTag compound = NBTTags.NBTMerge(this.readNpcData(npc), data.data);
            npc.display.readToNBT(compound);
            npc.stats.readToNBT(compound);
            npc.advanced.readToNBT(compound);
            npc.inventory.load(compound);
            if (compound.m_128441_("ModelData")) {
                ((EntityCustomNpc)npc).modelData.load(compound.m_128469_("ModelData"));
            }
            npc.ais.readToNBT(compound);
            npc.transform.readToNBT(compound);
            npc.ais.setMovingPath(points);
            npc.updateClient = true;
        }
    }

    private void cleanTags(CompoundTag compound) {
        compound.m_128473_("MovingPathNew");
    }

    public LinkedData getData(String name) {
        for (LinkedData data : this.list) {
            if (!data.name.equalsIgnoreCase(name)) continue;
            return data;
        }
        return null;
    }

    private CompoundTag readNpcData(EntityNPCInterface npc) {
        CompoundTag compound = new CompoundTag();
        npc.display.save(compound);
        npc.inventory.save(compound);
        npc.stats.save(compound);
        npc.ais.save(compound);
        npc.advanced.save(compound);
        npc.transform.save(compound);
        compound.m_128365_("ModelData", (Tag)((EntityCustomNpc)npc).modelData.save());
        return compound;
    }

    public void saveNpcData(EntityNPCInterface npc) {
        CompoundTag compound = this.readNpcData(npc);
        this.cleanTags(compound);
        if (npc.linkedData.data.equals((Object)compound)) {
            return;
        }
        npc.linkedData.data = compound;
        npc.linkedData.time = System.currentTimeMillis();
        this.save();
    }

    public void removeData(String name) {
        Iterator<LinkedData> ita = this.list.iterator();
        while (ita.hasNext()) {
            if (!ita.next().name.equalsIgnoreCase(name)) continue;
            ita.remove();
        }
        this.save();
    }

    public void addData(String name) {
        if (this.getData(name) != null || name.isEmpty()) {
            return;
        }
        LinkedData data = new LinkedData();
        data.name = name;
        this.list.add(data);
        this.save();
    }

    public static class LinkedData {
        public String name = "LinkedNpc";
        public long time = 0L;
        public CompoundTag data = new CompoundTag();

        public LinkedData() {
            this.time = System.currentTimeMillis();
        }

        public void setNBT(CompoundTag compound) {
            this.name = compound.m_128461_("LinkedName");
            this.data = compound.m_128469_("NPCData");
        }

        public CompoundTag getNBT() {
            CompoundTag compound = new CompoundTag();
            compound.m_128359_("LinkedName", this.name);
            compound.m_128365_("NPCData", (Tag)this.data);
            return compound;
        }
    }
}

