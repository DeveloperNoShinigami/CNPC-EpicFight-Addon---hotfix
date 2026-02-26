/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.eventbus.api.Event
 */
package noppes.npcs.api.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import noppes.npcs.EventHooks;
import noppes.npcs.NBTTags;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.item.IItemScripted;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.IScriptHandler;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.controllers.ScriptController;

public class ItemScriptedWrapper
extends ItemStackWrapper
implements IItemScripted,
IScriptHandler {
    public List<ScriptContainer> scripts = new ArrayList<ScriptContainer>();
    public String scriptLanguage = "ECMAScript";
    public boolean enabled = false;
    public long lastInited = -1L;
    public boolean updateClient = false;
    public boolean durabilityShow = true;
    public float durabilityValue = 1.0f;
    public int durabilityColor = -1;
    public int itemColor = -1;
    public int stackSize = 64;
    public boolean loaded = false;
    public ResourceLocation texture = null;

    public ItemScriptedWrapper(ItemStack item) {
        super(item);
    }

    @Override
    public boolean hasTexture(int damage) {
        return this.texture != null;
    }

    @Override
    public String getTexture(int damage) {
        return this.getTexture();
    }

    @Override
    public String getTexture() {
        if (this.texture == null) {
            return null;
        }
        return this.texture.toString();
    }

    @Override
    public void setTexture(int damage, String texture) {
        this.setTexture(texture);
    }

    @Override
    public void setTexture(String texture) {
        this.texture = texture == null ? null : new ResourceLocation(texture);
    }

    public CompoundTag getScriptNBT(CompoundTag compound) {
        compound.m_128365_("Scripts", (Tag)NBTTags.NBTScript(this.scripts));
        compound.m_128359_("ScriptLanguage", this.scriptLanguage);
        compound.m_128379_("ScriptEnabled", this.enabled);
        if (this.texture != null) {
            compound.m_128359_("ScriptTexture", this.texture.toString());
        }
        return compound;
    }

    @Override
    public CompoundTag getMCNbt() {
        CompoundTag compound = super.getMCNbt();
        this.getScriptNBT(compound);
        compound.m_128379_("DurabilityShow", this.durabilityShow);
        compound.m_128350_("DurabilityValue", this.durabilityValue);
        compound.m_128405_("DurabilityColor", this.durabilityColor);
        compound.m_128405_("ItemColor", this.itemColor);
        compound.m_128405_("MaxStackSize", this.stackSize);
        return compound;
    }

    public void setScriptNBT(CompoundTag compound) {
        if (!compound.m_128441_("Scripts")) {
            return;
        }
        this.scripts = NBTTags.GetScript(compound.m_128437_("Scripts", 10), this);
        this.scriptLanguage = compound.m_128461_("ScriptLanguage");
        this.enabled = compound.m_128471_("ScriptEnabled");
        if (compound.m_128441_("ScriptTexture")) {
            this.texture = new ResourceLocation(compound.m_128461_("ScriptTexture"));
        }
    }

    @Override
    public void setMCNbt(CompoundTag compound) {
        super.setMCNbt(compound);
        this.setScriptNBT(compound);
        this.durabilityShow = compound.m_128471_("DurabilityShow");
        this.durabilityValue = compound.m_128457_("DurabilityValue");
        if (compound.m_128441_("DurabilityColor")) {
            this.durabilityColor = compound.m_128451_("DurabilityColor");
        }
        this.itemColor = compound.m_128451_("ItemColor");
        this.stackSize = compound.m_128451_("MaxStackSize");
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public void runScript(EnumScriptType type, Event event) {
        if (!this.loaded) {
            this.loadScriptData();
            this.loaded = true;
        }
        if (!this.isEnabled()) {
            return;
        }
        if (ScriptController.Instance.lastLoaded > this.lastInited) {
            this.lastInited = ScriptController.Instance.lastLoaded;
            if (type != EnumScriptType.INIT) {
                EventHooks.onScriptItemInit(this);
            }
        }
        for (ScriptContainer script : this.scripts) {
            script.run(type, event);
        }
    }

    private boolean isEnabled() {
        return this.enabled && ScriptController.HasStart;
    }

    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public boolean getEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean bo) {
        this.enabled = bo;
    }

    @Override
    public String getLanguage() {
        return this.scriptLanguage;
    }

    @Override
    public void setLanguage(String lang) {
        this.scriptLanguage = lang;
    }

    @Override
    public List<ScriptContainer> getScripts() {
        return this.scripts;
    }

    @Override
    public String noticeString() {
        return "ScriptedItem";
    }

    @Override
    public Map<Long, String> getConsoleText() {
        TreeMap<Long, String> map = new TreeMap<Long, String>();
        int tab = 0;
        for (ScriptContainer script : this.getScripts()) {
            ++tab;
            for (Map.Entry<Long, String> entry : script.console.entrySet()) {
                map.put(entry.getKey(), " tab " + tab + ":\n" + entry.getValue());
            }
        }
        return map;
    }

    @Override
    public void clearConsole() {
        for (ScriptContainer script : this.getScripts()) {
            script.console.clear();
        }
    }

    @Override
    public int getMaxStackSize() {
        return this.stackSize;
    }

    @Override
    public void setMaxStackSize(int size) {
        if (size < 1 || size > 64) {
            throw new CustomNPCsException("Stacksize has to be between 1 and 64", new Object[0]);
        }
        this.stackSize = size;
    }

    @Override
    public double getDurabilityValue() {
        return this.durabilityValue;
    }

    @Override
    public void setDurabilityValue(float value) {
        if (value != this.durabilityValue) {
            this.updateClient = true;
        }
        this.durabilityValue = value;
    }

    @Override
    public boolean getDurabilityShow() {
        return this.durabilityShow;
    }

    @Override
    public void setDurabilityShow(boolean bo) {
        if (bo != this.durabilityShow) {
            this.updateClient = true;
        }
        this.durabilityShow = bo;
    }

    @Override
    public int getDurabilityColor() {
        return this.durabilityColor;
    }

    @Override
    public void setDurabilityColor(int color) {
        if (color != this.durabilityColor) {
            this.updateClient = true;
        }
        this.durabilityColor = color;
    }

    @Override
    public int getColor() {
        return this.itemColor;
    }

    @Override
    public void setColor(int color) {
        if (color != this.itemColor) {
            this.updateClient = true;
        }
        this.itemColor = color;
    }

    public void saveScriptData() {
        CompoundTag c = this.item.m_41783_();
        if (c == null) {
            c = new CompoundTag();
            this.item.m_41751_(c);
        }
        c.m_128365_("ScriptedData", (Tag)this.getScriptNBT(new CompoundTag()));
    }

    public void loadScriptData() {
        CompoundTag c = this.item.m_41783_();
        if (c == null) {
            return;
        }
        this.setScriptNBT(c.m_128469_("ScriptedData"));
    }
}

