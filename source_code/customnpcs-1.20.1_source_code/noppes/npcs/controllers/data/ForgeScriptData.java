/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.fml.LogicalSide
 *  net.minecraftforge.fml.util.thread.EffectiveSide
 */
package noppes.npcs.controllers.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EventHooks;
import noppes.npcs.NBTTags;
import noppes.npcs.api.event.ForgeEvent;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.IScriptHandler;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.event.SlotItemChangeEvent;

public class ForgeScriptData
implements IScriptHandler {
    private List<ScriptContainer> scripts = new ArrayList<ScriptContainer>();
    private String scriptLanguage = "ECMAScript";
    public long lastInited = -1L;
    public boolean hadInteract = true;
    private boolean enabled = false;

    public void clear() {
        this.scripts = new ArrayList<ScriptContainer>();
    }

    public void load(CompoundTag compound) {
        this.scripts = NBTTags.GetScript(compound.m_128437_("Scripts", 10), this);
        this.scriptLanguage = compound.m_128461_("ScriptLanguage");
        this.enabled = compound.m_128471_("ScriptEnabled");
    }

    public CompoundTag save(CompoundTag compound) {
        compound.m_128365_("Scripts", (Tag)NBTTags.NBTScript(this.scripts));
        compound.m_128359_("ScriptLanguage", this.scriptLanguage);
        compound.m_128379_("ScriptEnabled", this.enabled);
        return compound;
    }

    @Override
    public void runScript(EnumScriptType type, Event event) {
    }

    public void runScript(String type, Event event) {
        if (!this.isEnabled()) {
            return;
        }
        if (EffectiveSide.get() != LogicalSide.SERVER) {
            return;
        }
        if (event instanceof ForgeEvent) {
            ForgeEvent fe = (ForgeEvent)event;
            if (fe.event instanceof SlotItemChangeEvent) {
                for (ScriptContainer script : this.scripts) {
                    script.run(type, (Object)event);
                }
                return;
            }
        }
        CustomNpcs.Server.m_18707_(() -> {
            if (ScriptController.Instance.lastLoaded > this.lastInited) {
                this.lastInited = ScriptController.Instance.lastLoaded;
                if (!type.equals("init")) {
                    EventHooks.onForgeInit(this);
                }
            }
            for (ScriptContainer script : this.scripts) {
                script.run(type, (Object)event);
            }
        });
    }

    public boolean isEnabled() {
        return this.enabled && ScriptController.HasStart && this.scripts.size() > 0;
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
        return "ForgeScript";
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
}

