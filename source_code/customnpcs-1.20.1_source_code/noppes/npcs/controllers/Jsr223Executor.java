/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import noppes.npcs.controllers.IScriptExecutor;
import noppes.npcs.controllers.ScriptController;

public class Jsr223Executor
implements IScriptExecutor {
    private ScriptEngine engine;
    private boolean init = false;
    private boolean errored = false;
    private HashSet<String> unknownFunctions = new HashSet();
    private String scriptCode;
    private static Method luaCoerce;
    private static Method luaCall;

    @Override
    public void initialize(String language, Map<String, Object> globals) {
        this.engine = ScriptController.Instance.getEngineByName(language);
        if (this.engine == null) {
            this.errored = true;
            return;
        }
        for (Map.Entry<String, Object> entry : globals.entrySet()) {
            this.engine.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void setScript(String fullScriptCode) {
        this.scriptCode = fullScriptCode;
        this.init = false;
        this.errored = false;
        this.unknownFunctions.clear();
    }

    @Override
    public String run(String functionName, Object event) {
        StringWriter sw;
        block14: {
            if (this.errored || this.engine == null || this.unknownFunctions.contains(functionName)) {
                return "";
            }
            sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw);){
                this.engine.getContext().setWriter(pw);
                this.engine.getContext().setErrorWriter(pw);
                try {
                    if (!this.init) {
                        this.engine.eval(this.scriptCode);
                        this.init = true;
                    }
                    if (this.engine.getFactory().getLanguageName().equals("lua")) {
                        Object ob = this.engine.get(functionName);
                        if (ob != null) {
                            if (luaCoerce == null) {
                                luaCoerce = Class.forName("org.luaj.vm2.lib.jse.CoerceJavaToLua").getMethod("coerce", Object.class);
                                luaCall = ob.getClass().getMethod("call", Class.forName("org.luaj.vm2.LuaValue"));
                            }
                            luaCall.invoke(ob, luaCoerce.invoke(null, event));
                        } else {
                            this.unknownFunctions.add(functionName);
                        }
                        break block14;
                    }
                    ((Invocable)((Object)this.engine)).invokeFunction(functionName, event);
                }
                catch (NoSuchMethodException e) {
                    this.unknownFunctions.add(functionName);
                }
                catch (Throwable e) {
                    this.errored = true;
                    e.printStackTrace(pw);
                }
            }
        }
        return sw.toString();
    }

    @Override
    public boolean isErrored() {
        return this.errored;
    }

    @Override
    public boolean isInit() {
        return this.init;
    }

    @Override
    public boolean isUnknownFunction(String functionName) {
        return this.unknownFunctions.contains(functionName);
    }

    @Override
    public void close() {
        this.engine = null;
    }
}

