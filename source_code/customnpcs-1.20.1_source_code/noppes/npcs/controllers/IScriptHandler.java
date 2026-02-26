/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.eventbus.api.Event
 */
package noppes.npcs.controllers;

import java.util.List;
import java.util.Map;
import net.minecraftforge.eventbus.api.Event;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.controllers.ScriptContainer;

public interface IScriptHandler {
    public void runScript(EnumScriptType var1, Event var2);

    public boolean isClient();

    public boolean getEnabled();

    public void setEnabled(boolean var1);

    public String getLanguage();

    public void setLanguage(String var1);

    public List<ScriptContainer> getScripts();

    public String noticeString();

    public Map<Long, String> getConsoleText();

    public void clearConsole();
}

