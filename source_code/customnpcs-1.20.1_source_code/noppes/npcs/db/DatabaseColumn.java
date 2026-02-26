/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.db;

public @interface DatabaseColumn {
    public String name();

    public String default_value() default "";

    public Type type();

    public boolean isVirtual() default false;

    public static enum Type {
        INT,
        TEXT,
        VARCHAR,
        ENUM,
        UUID,
        SMALLINT,
        JSON,
        BOOLEAN;

    }
}

