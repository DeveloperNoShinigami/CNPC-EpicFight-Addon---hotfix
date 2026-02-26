/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.controllers;

import java.util.Map;

public interface IScriptExecutor {
    public void initialize(String var1, Map<String, Object> var2);

    public void setScript(String var1);

    public String run(String var1, Object var2);

    public boolean isErrored();

    public boolean isInit();

    public boolean isUnknownFunction(String var1);

    public void close();
}

