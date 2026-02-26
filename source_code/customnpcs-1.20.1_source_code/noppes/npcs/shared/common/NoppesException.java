/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.shared.common;

public class NoppesException
extends RuntimeException {
    public NoppesException(String message, Object ... obs) {
        super(String.format(message, obs));
    }

    public NoppesException(Exception ex, String message, Object ... obs) {
        super(String.format(message, obs), ex);
    }
}

