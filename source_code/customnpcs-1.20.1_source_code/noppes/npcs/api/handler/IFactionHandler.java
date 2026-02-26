/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.handler;

import java.util.List;
import noppes.npcs.api.handler.data.IFaction;

public interface IFactionHandler {
    public List<IFaction> list();

    public IFaction delete(int var1);

    public IFaction create(String var1, int var2);

    public IFaction get(int var1);
}

