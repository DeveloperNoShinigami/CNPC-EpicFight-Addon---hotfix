/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.client.controllers;

import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.ModelData;
import noppes.npcs.constants.EnumParts;

public class Preset {
    public ModelData data = new ModelData(null);
    public String name;

    public CompoundTag save() {
        CompoundTag compound = new CompoundTag();
        compound.m_128359_("PresetName", this.name);
        compound.m_128365_("PresetData", (Tag)this.data.save());
        return compound;
    }

    public void load(CompoundTag compound) {
        this.name = compound.m_128461_("PresetName");
        this.data.load(compound.m_128469_("PresetData"));
    }

    public static void FillDefault(HashMap<String, Preset> presets) {
        ModelData data = new ModelData(null);
        Preset preset = new Preset();
        preset.name = "Elf Male";
        preset.data = data;
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(0.85f, 1.15f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(0.85f, 1.15f);
        data.getPartConfig(EnumParts.BODY).setScale(0.85f, 1.15f);
        data.getPartConfig(EnumParts.HEAD).setScale(0.85f, 0.95f);
        presets.put("elf male", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Elf Female";
        preset.data = data;
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(0.8f, 1.05f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(0.8f, 1.05f);
        data.getPartConfig(EnumParts.BODY).setScale(0.8f, 1.05f);
        data.getPartConfig(EnumParts.HEAD).setScale(0.8f, 0.85f);
        presets.put("elf female", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Dwarf Male";
        preset.data = data;
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(1.1f, 0.7f, 0.9f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(0.9f, 0.7f);
        data.getPartConfig(EnumParts.BODY).setScale(1.2f, 0.7f, 1.5f);
        data.getPartConfig(EnumParts.HEAD).setScale(0.85f, 0.85f);
        presets.put("dwarf male", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Dwarf Female";
        preset.data = data;
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(0.9f, 0.65f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(0.9f, 0.65f);
        data.getPartConfig(EnumParts.BODY).setScale(1.0f, 0.65f, 1.1f);
        data.getPartConfig(EnumParts.HEAD).setScale(0.85f, 0.85f);
        presets.put("dwarf female", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Orc Male";
        preset.data = data;
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(1.2f, 1.05f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(1.2f, 1.05f);
        data.getPartConfig(EnumParts.BODY).setScale(1.4f, 1.1f, 1.5f);
        data.getPartConfig(EnumParts.HEAD).setScale(1.2f, 1.1f);
        presets.put("orc male", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Orc Female";
        preset.data = data;
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(1.1f, 1.0f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(1.1f, 1.0f);
        data.getPartConfig(EnumParts.BODY).setScale(1.1f, 1.0f, 1.25f);
        presets.put("orc female", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Human Male";
        preset.data = data;
        presets.put("human male", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Human Female";
        preset.data = data;
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(0.92f, 0.92f);
        data.getPartConfig(EnumParts.HEAD).setScale(0.95f, 0.95f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(0.8f, 0.92f);
        data.getPartConfig(EnumParts.BODY).setScale(0.92f, 0.92f);
        presets.put("human female", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Cat Male";
        preset.data = data;
        presets.put("cat male", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Cat Female";
        preset.data = data;
        data.getPartConfig(EnumParts.HEAD).setScale(0.95f, 0.95f);
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(0.92f, 0.92f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(0.8f, 0.92f);
        data.getPartConfig(EnumParts.BODY).setScale(0.92f, 0.92f);
        presets.put("cat female", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Wolf Male";
        preset.data = data;
        presets.put("wolf male", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Wolf Female";
        preset.data = data;
        data.getPartConfig(EnumParts.HEAD).setScale(0.95f, 0.95f);
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(0.92f, 0.92f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(0.8f, 0.92f);
        data.getPartConfig(EnumParts.BODY).setScale(0.92f, 0.92f);
        presets.put("wolf female", preset);
        data = new ModelData(null);
        preset = new Preset();
        preset.name = "Enderchibi";
        preset.data = data;
        data.getPartConfig(EnumParts.LEG_LEFT).setScale(0.65f, 0.75f);
        data.getPartConfig(EnumParts.ARM_LEFT).setScale(0.5f, 1.45f);
        presets.put("enderchibi", preset);
    }
}

