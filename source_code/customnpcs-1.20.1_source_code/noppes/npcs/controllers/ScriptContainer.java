/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraftforge.eventbus.api.Event
 */
package noppes.npcs.controllers;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.eventbus.api.Event;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NBTTags;
import noppes.npcs.api.constants.AnimationType;
import noppes.npcs.api.constants.EntitiesType;
import noppes.npcs.api.constants.JobType;
import noppes.npcs.api.constants.ParticleType;
import noppes.npcs.api.constants.PotionEffectType;
import noppes.npcs.api.constants.RoleType;
import noppes.npcs.api.constants.SideType;
import noppes.npcs.api.wrapper.BlockPosWrapper;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.IScriptExecutor;
import noppes.npcs.controllers.IScriptHandler;
import noppes.npcs.controllers.Jsr223Executor;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.shared.client.util.NoppesStringUtils;
import noppes.npcs.shared.common.CommonUtil;
import noppes.npcs.shared.common.util.LogWriter;

public class ScriptContainer {
    private static final String lock = "cnpcslock";
    public static ScriptContainer Current;
    private static String CurrentType;
    public static final HashMap<String, Object> Data;
    public String fullscript = "";
    public String script = "";
    public TreeMap<Long, String> console = new TreeMap();
    public List<String> scripts = new ArrayList<String>();
    public long lastCreated = 0L;
    private String currentScriptLanguage = null;
    private IScriptExecutor executor = null;
    private IScriptHandler handler = null;

    private static void FillMap(Class c) {
        Field[] declaredFields;
        try {
            Data.put(c.getSimpleName(), c.newInstance());
        }
        catch (Exception exception) {
            // empty catch block
        }
        for (Field field : declaredFields = c.getDeclaredFields()) {
            try {
                if (!Modifier.isStatic(field.getModifiers()) || field.getType() != Integer.TYPE) continue;
                Data.put(c.getSimpleName() + "_" + field.getName(), field.getInt(null));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public ScriptContainer(IScriptHandler handler) {
        this.handler = handler;
    }

    public void load(CompoundTag compound) {
        this.script = compound.m_128461_("Script");
        this.console = NBTTags.GetLongStringMap(compound.m_128437_("Console", 10));
        this.scripts = NBTTags.getStringList(compound.m_128437_("ScriptList", 10));
        this.lastCreated = 0L;
    }

    public CompoundTag save(CompoundTag compound) {
        compound.m_128359_("Script", this.script);
        compound.m_128365_("Console", (Tag)NBTTags.NBTLongStringMap(this.console));
        compound.m_128365_("ScriptList", (Tag)NBTTags.nbtStringList(this.scripts));
        return compound;
    }

    public boolean isErrored() {
        return this.executor != null && this.executor.isErrored();
    }

    private String getFullCode() {
        if (this.executor == null || !this.executor.isInit()) {
            this.fullscript = this.script;
            if (!this.fullscript.isEmpty()) {
                this.fullscript = this.fullscript + "\n";
            }
            for (String loc : this.scripts) {
                String code = ScriptController.Instance.scripts.get(loc);
                if (code == null || code.isEmpty()) continue;
                this.fullscript = this.fullscript + code + "\n";
            }
        }
        return this.fullscript;
    }

    public void run(EnumScriptType type, Event event) {
        this.run(type.function, (Object)event);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run(String type, Object event) {
        if (this.executor != null && this.executor.isErrored() || !this.hasCode() || this.executor != null && this.executor.isUnknownFunction(type) || !CustomNpcs.EnableScripting) {
            return;
        }
        this.getOrCreateExecutor(this.handler.getLanguage());
        if (this.executor.isErrored()) {
            return;
        }
        if (ScriptController.Instance.lastLoaded > this.lastCreated) {
            this.lastCreated = ScriptController.Instance.lastLoaded;
            this.executor.setScript(this.getFullCode());
        }
        String string = lock;
        synchronized (lock) {
            Current = this;
            CurrentType = type;
            String consoleOutput = "";
            try {
                consoleOutput = this.executor.run(type, event);
            }
            finally {
                this.appandConsole(consoleOutput.trim());
                if (this.executor.isErrored()) {
                    CommonUtil.NotifyOPs(CustomNpcs.Server, this.handler.noticeString() + " script errored", new Object[0]);
                }
                Current = null;
            }
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return;
        }
    }

    public void appandConsole(String message) {
        if (message == null || ((String)message).isEmpty()) {
            return;
        }
        long time = System.currentTimeMillis();
        if (this.console.containsKey(time)) {
            message = this.console.get(time) + "\n" + (String)message;
        }
        this.console.put(time, (String)message);
        while (this.console.size() > 40) {
            this.console.remove(this.console.firstKey());
        }
    }

    public boolean hasCode() {
        return !this.getFullCode().isEmpty();
    }

    private void getOrCreateExecutor(String language) {
        if (this.executor != null && this.currentScriptLanguage.equals(language)) {
            return;
        }
        if (this.executor != null) {
            this.executor.close();
        }
        Supplier provider = ScriptController.Instance.executorProviders.computeIfAbsent(language.toLowerCase(), l -> Jsr223Executor::new);
        this.executor = (IScriptExecutor)provider.get();
        this.currentScriptLanguage = language;
        HashMap<String, Object> globals = new HashMap<String, Object>(Data);
        globals.put("dump", new Dump());
        globals.put("log", new Log());
        this.executor.initialize(language, globals);
        this.executor.setScript(this.getFullCode());
    }

    public boolean isValid() {
        return !this.executor.isErrored();
    }

    static {
        Data = new HashMap();
        ScriptContainer.FillMap(AnimationType.class);
        ScriptContainer.FillMap(EntitiesType.class);
        ScriptContainer.FillMap(RoleType.class);
        ScriptContainer.FillMap(JobType.class);
        ScriptContainer.FillMap(SideType.class);
        ScriptContainer.FillMap(PotionEffectType.class);
        ScriptContainer.FillMap(ParticleType.class);
        Data.put("PosZero", new BlockPosWrapper(BlockPos.f_121853_));
    }

    private class Dump
    implements Function<Object, String> {
        private Dump() {
        }

        @Override
        public String apply(Object o) {
            if (o == null) {
                return "null";
            }
            StringBuilder builder = new StringBuilder();
            builder.append(o + ":" + NoppesStringUtils.newLine());
            for (Field field : o.getClass().getFields()) {
                try {
                    builder.append(field.getName() + " - " + field.getType().getSimpleName() + ", ");
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
            }
            for (AccessibleObject accessibleObject : o.getClass().getMethods()) {
                try {
                    Object s = ((Method)accessibleObject).getName() + "(";
                    for (Class<?> c : ((Method)accessibleObject).getParameterTypes()) {
                        s = (String)s + c.getSimpleName() + ", ";
                    }
                    if (((String)s).endsWith(", ")) {
                        s = ((String)s).substring(0, ((String)s).length() - 2);
                    }
                    builder.append((String)s + "), ");
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
            }
            return builder.toString();
        }
    }

    private class Log
    implements Function<Object, Void> {
        private Log() {
        }

        @Override
        public Void apply(Object o) {
            ScriptContainer.this.appandConsole("" + o);
            LogWriter.info("" + o);
            return null;
        }
    }
}

