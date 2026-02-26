/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.eventbus.api.Event
 */
package noppes.npcs.blocks.tiles;

import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;
import noppes.npcs.CustomBlocks;
import noppes.npcs.EventHooks;
import noppes.npcs.NBTTags;
import noppes.npcs.api.block.IBlock;
import noppes.npcs.api.wrapper.BlockScriptedDoorWrapper;
import noppes.npcs.blocks.tiles.TileDoor;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.IScriptBlockHandler;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.entity.data.DataTimers;

public class TileScriptedDoor
extends TileDoor
implements IScriptBlockHandler {
    public List<ScriptContainer> scripts = new ArrayList<ScriptContainer>();
    public boolean shouldRefreshData = false;
    public String scriptLanguage = "ECMAScript";
    public boolean enabled = false;
    private IBlock blockDummy = null;
    public DataTimers timers = new DataTimers(this);
    public long lastInited = -1L;
    private short tickCount = 0;
    public int newPower = 0;
    public int prevPower = 0;
    public float blockHardness = 5.0f;
    public float blockResistance = 10.0f;

    public TileScriptedDoor(BlockPos pos, BlockState state) {
        super(CustomBlocks.tile_scripteddoor, pos, state);
    }

    @Override
    public IBlock getBlock() {
        if (this.blockDummy == null) {
            this.blockDummy = new BlockScriptedDoorWrapper(this.m_58904_(), CustomBlocks.scripted_door, this.m_58899_());
        }
        return this.blockDummy;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.setNBT(compound);
        this.timers.load(compound);
    }

    public void setNBT(CompoundTag compound) {
        this.scripts = NBTTags.GetScript(compound.m_128437_("Scripts", 10), this);
        this.scriptLanguage = compound.m_128461_("ScriptLanguage");
        this.enabled = compound.m_128471_("ScriptEnabled");
        this.prevPower = compound.m_128451_("BlockPrevPower");
        if (compound.m_128441_("BlockHardness")) {
            this.blockHardness = compound.m_128457_("BlockHardness");
            this.blockResistance = compound.m_128457_("BlockResistance");
        }
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        this.getNBT(compound);
        this.timers.save(compound);
        super.m_183515_(compound);
    }

    public CompoundTag getNBT(CompoundTag compound) {
        compound.m_128365_("Scripts", (Tag)NBTTags.NBTScript(this.scripts));
        compound.m_128359_("ScriptLanguage", this.scriptLanguage);
        compound.m_128379_("ScriptEnabled", this.enabled);
        compound.m_128405_("BlockPrevPower", this.prevPower);
        compound.m_128350_("BlockHardness", this.blockHardness);
        compound.m_128350_("BlockResistance", this.blockResistance);
        return compound;
    }

    @Override
    public void runScript(EnumScriptType type, Event event) {
        if (!this.isEnabled()) {
            return;
        }
        if (ScriptController.Instance.lastLoaded > this.lastInited) {
            this.lastInited = ScriptController.Instance.lastLoaded;
            if (type != EnumScriptType.INIT) {
                EventHooks.onScriptBlockInit(this);
            }
        }
        for (ScriptContainer script : this.scripts) {
            script.run(type, event);
        }
    }

    private boolean isEnabled() {
        return this.enabled && ScriptController.HasStart && !this.f_58857_.f_46443_;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileScriptedDoor tile) {
        tile.tickCount = (short)(tile.tickCount + 1);
        if (tile.prevPower != tile.newPower) {
            EventHooks.onScriptBlockRedstonePower(tile, tile.prevPower, tile.newPower);
            tile.prevPower = tile.newPower;
        }
        tile.timers.update();
        if (tile.tickCount >= 10) {
            EventHooks.onScriptBlockUpdate(tile);
            tile.tickCount = 0;
        }
    }

    @Override
    public boolean isClient() {
        return this.m_58904_().f_46443_;
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
        BlockPos pos = this.m_58899_();
        return MoreObjects.toStringHelper((Object)this).add("x", pos.m_123341_()).add("y", pos.m_123342_()).add("z", pos.m_123343_()).toString();
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

