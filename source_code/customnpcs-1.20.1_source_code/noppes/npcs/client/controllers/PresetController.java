/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.client.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import noppes.npcs.client.controllers.Preset;
import noppes.npcs.shared.common.util.LogWriter;

public class PresetController {
    public HashMap<String, Preset> presets = new HashMap();
    private File dir;
    public static PresetController instance;

    public PresetController(File dir) {
        instance = this;
        this.dir = dir;
        this.load();
    }

    public Preset getPreset(String username) {
        if (this.presets.isEmpty()) {
            this.load();
        }
        return this.presets.get(username.toLowerCase());
    }

    public void load() {
        CompoundTag compound = this.loadPreset();
        HashMap<String, Preset> presets = new HashMap<String, Preset>();
        if (compound != null) {
            ListTag list = compound.m_128437_("Presets", 10);
            for (int i = 0; i < list.size(); ++i) {
                CompoundTag comp = list.m_128728_(i);
                Preset preset = new Preset();
                preset.load(comp);
                presets.put(preset.name.toLowerCase(), preset);
            }
        }
        Preset.FillDefault(presets);
        this.presets = presets;
    }

    private CompoundTag loadPreset() {
        String filename = "presets.dat";
        try {
            File file = new File(this.dir, filename);
            if (!file.exists()) {
                return null;
            }
            return NbtIo.m_128939_((InputStream)new FileInputStream(file));
        }
        catch (Exception e) {
            LogWriter.except(e);
            try {
                File file = new File(this.dir, filename + "_old");
                if (!file.exists()) {
                    return null;
                }
                return NbtIo.m_128939_((InputStream)new FileInputStream(file));
            }
            catch (Exception e2) {
                LogWriter.except(e2);
                return null;
            }
        }
    }

    public void save() {
        CompoundTag compound = new CompoundTag();
        ListTag list = new ListTag();
        for (Preset preset : this.presets.values()) {
            list.add((Object)preset.save());
        }
        compound.m_128365_("Presets", (Tag)list);
        this.savePreset(compound);
    }

    private void savePreset(CompoundTag compound) {
        String filename = "presets.dat";
        try {
            File file = new File(this.dir, filename + "_new");
            File file1 = new File(this.dir, filename + "_old");
            File file2 = new File(this.dir, filename);
            NbtIo.m_128947_((CompoundTag)compound, (OutputStream)new FileOutputStream(file));
            if (file1.exists()) {
                file1.delete();
            }
            file2.renameTo(file1);
            if (file2.exists()) {
                file2.delete();
            }
            file.renameTo(file2);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception e) {
            LogWriter.except(e);
        }
    }

    public void addPreset(Preset preset) {
        while (this.presets.containsKey(preset.name.toLowerCase())) {
            preset.name = preset.name + "_";
        }
        this.presets.put(preset.name.toLowerCase(), preset);
        this.save();
    }

    public void removePreset(String preset) {
        if (preset == null) {
            return;
        }
        this.presets.remove(preset.toLowerCase());
        this.save();
    }
}

