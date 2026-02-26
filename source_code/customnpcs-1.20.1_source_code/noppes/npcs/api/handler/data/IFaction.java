/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.handler.data;

import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IPlayer;

public interface IFaction {
    public int getId();

    public String getName();

    public int getDefaultPoints();

    public void setDefaultPoints(int var1);

    public int getColor();

    public int playerStatus(IPlayer var1);

    public boolean hostileToNpc(ICustomNpc var1);

    public boolean hostileToFaction(int var1);

    public int[] getHostileList();

    public void addHostile(int var1);

    public void removeHostile(int var1);

    public boolean hasHostile(int var1);

    public boolean getIsHidden();

    public void setIsHidden(boolean var1);

    public boolean getAttackedByMobs();

    public void setAttackedByMobs(boolean var1);

    public void save();
}

