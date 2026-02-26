/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.entity.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.EventHooks;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.ITimers;
import noppes.npcs.controllers.IScriptBlockHandler;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityNPCInterface;

public class DataTimers
implements ITimers {
    private Object parent;
    private Map<Integer, Timer> timers = new HashMap<Integer, Timer>();

    public DataTimers(Object parent) {
        this.parent = parent;
    }

    @Override
    public void start(int id, int ticks, boolean repeat) {
        if (this.timers.containsKey(id)) {
            throw new CustomNPCsException("There is already a timer with id: " + id, new Object[0]);
        }
        this.timers.put(id, new Timer(id, ticks, repeat));
    }

    @Override
    public void forceStart(int id, int ticks, boolean repeat) {
        this.timers.put(id, new Timer(id, ticks, repeat));
    }

    @Override
    public boolean has(int id) {
        return this.timers.containsKey(id);
    }

    @Override
    public boolean stop(int id) {
        return this.timers.remove(id) != null;
    }

    @Override
    public void reset(int id) {
        Timer timer = this.timers.get(id);
        if (timer == null) {
            throw new CustomNPCsException("There is no timer with id: " + id, new Object[0]);
        }
        timer.ticks = 0;
    }

    public void save(CompoundTag compound) {
        ListTag list = new ListTag();
        for (Timer timer : this.timers.values()) {
            CompoundTag c = new CompoundTag();
            c.m_128405_("ID", timer.id);
            c.m_128405_("TimerTicks", timer.id);
            c.m_128379_("Repeat", timer.repeat);
            c.m_128405_("Ticks", timer.ticks);
            list.add((Object)c);
        }
        compound.m_128365_("NpcsTimers", (Tag)list);
    }

    public void load(CompoundTag compound) {
        HashMap<Integer, Timer> timers = new HashMap<Integer, Timer>();
        ListTag list = compound.m_128437_("NpcsTimers", 10);
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag c = list.m_128728_(i);
            Timer t = new Timer(c.m_128451_("ID"), c.m_128451_("TimerTicks"), c.m_128471_("Repeat"));
            t.ticks = c.m_128451_("Ticks");
            timers.put(t.id, t);
        }
        this.timers = timers;
    }

    public void update() {
        for (Timer timer : new ArrayList<Timer>(this.timers.values())) {
            timer.update();
        }
    }

    @Override
    public void clear() {
        this.timers = new HashMap<Integer, Timer>();
    }

    class Timer {
        public int id;
        private boolean repeat;
        private int timerTicks;
        private int ticks = 0;

        public Timer(int id, int ticks, boolean repeat) {
            this.id = id;
            this.repeat = repeat;
            this.timerTicks = ticks;
            this.ticks = ticks;
        }

        public void update() {
            if (this.ticks-- > 0) {
                return;
            }
            if (this.repeat) {
                this.ticks = this.timerTicks;
            } else {
                DataTimers.this.stop(this.id);
            }
            Object ob = DataTimers.this.parent;
            if (ob instanceof EntityNPCInterface) {
                EventHooks.onNPCTimer((EntityNPCInterface)((Object)ob), this.id);
            } else if (ob instanceof PlayerData) {
                EventHooks.onPlayerTimer((PlayerData)ob, this.id);
            } else {
                EventHooks.onScriptBlockTimer((IScriptBlockHandler)ob, this.id);
            }
        }
    }
}

