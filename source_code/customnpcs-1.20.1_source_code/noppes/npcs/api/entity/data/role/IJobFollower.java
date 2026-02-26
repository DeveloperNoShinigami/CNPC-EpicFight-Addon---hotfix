/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data.role;

import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.data.INPCJob;

public interface IJobFollower
extends INPCJob {
    public String getFollowing();

    public void setFollowing(String var1);

    public boolean isFollowing();

    public ICustomNpc getFollowingNpc();
}

