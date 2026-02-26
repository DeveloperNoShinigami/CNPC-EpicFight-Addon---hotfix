/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data.role;

import noppes.npcs.api.entity.data.INPCRole;

public interface IRoleTransporter
extends INPCRole {
    public ITransportLocation getLocation();

    public static interface ITransportLocation {
        public int getId();

        public String getDimension();

        public int getX();

        public int getY();

        public int getZ();

        public String getName();

        public int getType();
    }
}

