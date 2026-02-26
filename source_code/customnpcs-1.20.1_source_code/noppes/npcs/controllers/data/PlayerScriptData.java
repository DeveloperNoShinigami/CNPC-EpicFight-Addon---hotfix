/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.eventbus.api.Event
 */
package noppes.npcs.controllers.data;

import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import noppes.npcs.EventHooks;
import noppes.npcs.NBTTags;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.IScriptHandler;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.controllers.ScriptController;

public class PlayerScriptData
implements IScriptHandler {
    private List<ScriptContainer> scripts = new ArrayList<ScriptContainer>();
    private String scriptLanguage = "ECMAScript";
    private Player player;
    private IPlayer playerAPI;
    private long lastPlayerUpdate = 0L;
    public long lastInited = -1L;
    public boolean hadInteract = true;
    private boolean enabled = false;
    private static Map<Long, String> console = new TreeMap<Long, String>();
    private static List<Integer> errored = new ArrayList<Integer>();

    public PlayerScriptData(Player player) {
        this.player = player;
    }

    public void clear() {
        console = new TreeMap<Long, String>();
        errored = new ArrayList<Integer>();
        this.scripts = new ArrayList<ScriptContainer>();
    }

    public void load(CompoundTag compound) {
        this.scripts = NBTTags.GetScript(compound.m_128437_("Scripts", 10), this);
        this.scriptLanguage = compound.m_128461_("ScriptLanguage");
        this.enabled = compound.m_128471_("ScriptEnabled");
        console = NBTTags.GetLongStringMap(compound.m_128437_("ScriptConsole", 10));
    }

    public CompoundTag save(CompoundTag compound) {
        compound.m_128365_("Scripts", (Tag)NBTTags.NBTScript(this.scripts));
        compound.m_128359_("ScriptLanguage", this.scriptLanguage);
        compound.m_128379_("ScriptEnabled", this.enabled);
        compound.m_128365_("ScriptConsole", (Tag)NBTTags.NBTLongStringMap(console));
        return compound;
    }

    @Override
    public void runScript(EnumScriptType type, Event event) {
        if (!this.isEnabled()) {
            return;
        }
        if (ScriptController.Instance.lastLoaded > this.lastInited || ScriptController.Instance.lastPlayerUpdate > this.lastPlayerUpdate) {
            this.lastInited = ScriptController.Instance.lastLoaded;
            errored.clear();
            if (this.player != null) {
                this.scripts.clear();
                for (ScriptContainer script : ScriptController.Instance.playerScripts.scripts) {
                    ScriptContainer s = new ScriptContainer(this);
                    s.load(script.save(new CompoundTag()));
                    this.scripts.add(s);
                }
            }
            this.lastPlayerUpdate = ScriptController.Instance.lastPlayerUpdate;
            if (type != EnumScriptType.INIT) {
                EventHooks.onPlayerInit(this);
            }
        }
        for (int i = 0; i < this.scripts.size(); ++i) {
            ScriptContainer script;
            script = this.scripts.get(i);
            if (errored.contains(i)) continue;
            script.run(type, event);
            if (script.isErrored()) {
                errored.add(i);
            }
            for (Map.Entry<Long, String> entry : script.console.entrySet()) {
                if (console.containsKey(entry.getKey())) continue;
                console.put(entry.getKey(), " tab " + (i + 1) + ":\n" + entry.getValue());
            }
            script.console.clear();
        }
    }

    public boolean isEnabled() {
        return ScriptController.Instance.playerScripts.enabled && ScriptController.HasStart && (this.player == null || !this.player.m_9236_().f_46443_);
    }

    @Override
    public boolean isClient() {
        return this.player.m_9236_().m_5776_();
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
        return ScriptController.Instance.playerScripts.scriptLanguage;
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
        if (this.player == null) {
            return "Global script";
        }
        BlockPos pos = this.player.m_20183_();
        return MoreObjects.toStringHelper((Object)this.player).add("x", pos.m_123341_()).add("y", pos.m_123342_()).add("z", pos.m_123343_()).toString();
    }

    public IPlayer getPlayer() {
        if (this.playerAPI == null) {
            this.playerAPI = (IPlayer)NpcAPI.Instance().getIEntity((Entity)this.player);
        }
        return this.playerAPI;
    }

    @Override
    public Map<Long, String> getConsoleText() {
        return console;
    }

    @Override
    public void clearConsole() {
        console.clear();
    }
}

