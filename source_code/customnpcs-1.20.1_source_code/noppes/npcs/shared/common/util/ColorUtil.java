/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.shared.common.util;

import noppes.npcs.shared.common.util.NopVector3f;

public class ColorUtil {
    public static NopVector3f colorToRgb(int color) {
        return new NopVector3f(new float[]{(float)(color >> 16 & 0xFF) / 255.0f, (float)(color >> 8 & 0xFF) / 255.0f, (float)(color & 0xFF) / 255.0f});
    }

    public static int rgbToColor(NopVector3f color) {
        int r = (int)(color.x * 255.0f) << 16;
        int g = (int)(color.y * 255.0f) << 8;
        int b = (int)(color.z * 255.0f);
        return r + g + b;
    }

    public static String colorToHex(int color) {
        Object str = Integer.toHexString(color);
        while (((String)str).length() < 6) {
            str = "0" + (String)str;
        }
        return str;
    }

    public static int hexToColor(String hex) {
        try {
            return Integer.parseInt(hex, 16);
        }
        catch (NumberFormatException numberFormatException) {
            return 0xFFFFFF;
        }
    }
}

