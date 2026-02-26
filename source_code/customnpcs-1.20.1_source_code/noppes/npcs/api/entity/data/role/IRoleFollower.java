/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data.role;

import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.entity.data.INPCRole;

public interface IRoleFollower
extends INPCRole {
    public int getDays();

    public void addDays(int var1);

    public boolean getInfinite();

    public void setInfinite(boolean var1);

    public boolean getGuiDisabled();

    public void setGuiDisabled(boolean var1);

    public IPlayer getFollowing();

    public void setFollowing(IPlayer var1);

    public boolean isFollowing();

    public void reset();

    public void setRefuseSoulstone(boolean var1);

    public boolean getRefuseSoulstone();
}

