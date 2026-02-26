/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.shared.common.util;

public class EasingFunctions {
    public static float easeInCubic(float x) {
        return x * x * x;
    }

    public static float easeOutCubic(float x) {
        return 1.0f - (float)Math.pow(1.0f - x, 3.0);
    }

    public static float easeInOutCubic(float x) {
        return (double)x < 0.5 ? 4.0f * x * x * x : 1.0f - (float)Math.pow(-2.0f * x + 2.0f, 3.0) / 2.0f;
    }

    public static float easeInOutQuad(float x) {
        return (double)x < 0.5 ? 2.0f * x * x : 1.0f - (float)Math.pow(-2.0f * x + 2.0f, 2.0) / 2.0f;
    }

    public static float easeInOutQuart(float x) {
        return (double)x < 0.5 ? 8.0f * x * x * x * x : 1.0f - (float)Math.pow(-2.0f * x + 2.0f, 4.0) / 2.0f;
    }
}

