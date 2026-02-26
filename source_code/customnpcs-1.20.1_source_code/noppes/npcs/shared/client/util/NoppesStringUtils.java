/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.shared.client.util;

import java.util.Arrays;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.entity.EntityNPCInterface;

public class NoppesStringUtils {
    static final int[] illegalChars = new int[]{34, 60, 62, 124, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 58, 42, 63, 92, 47};
    private static final Pattern NON_ALPHANUMERIC;

    public static String cleanFileName(String badFileName) {
        StringBuilder cleanName = new StringBuilder();
        for (int i = 0; i < badFileName.length(); ++i) {
            char c = badFileName.charAt(i);
            if (Arrays.binarySearch(illegalChars, (int)c) >= 0) continue;
            cleanName.append(c);
        }
        return cleanName.toString();
    }

    public static String removeHidden(String text) {
        int codePoint;
        StringBuilder newString = new StringBuilder(text.length());
        block3: for (int offset = 0; offset < text.length(); offset += Character.charCount(codePoint)) {
            codePoint = text.codePointAt(offset);
            switch (Character.getType(codePoint)) {
                case 0: 
                case 16: 
                case 18: 
                case 19: {
                    continue block3;
                }
            }
            newString.append(Character.toChars(codePoint));
        }
        return newString.toString();
    }

    public static String formatText(Component text, Object ... obs) {
        return NoppesStringUtils.formatText(text.getString(), obs);
    }

    public static String formatText(String text, Object ... obs) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        text = NoppesStringUtils.translate(text);
        for (Object ob : obs) {
            if (ob instanceof Player) {
                String username = ((Player)ob).m_5446_().getString();
                text = text.replace("{player}", username);
                text = text.replace("@p", username);
                continue;
            }
            if (!(ob instanceof EntityNPCInterface)) continue;
            text = text.replace("@npc", ((EntityNPCInterface)((Object)ob)).m_7755_().getString());
        }
        return text.replace("&", "" + Character.toChars(167)[0]);
    }

    public static void setClipboardContents(String aString) {
        Minecraft.m_91087_().f_91068_.m_90911_(aString);
    }

    public static String getClipboardContents() {
        return Minecraft.m_91087_().f_91068_.m_90876_();
    }

    public static String stripSpecialCharacters(String in) {
        return NON_ALPHANUMERIC.matcher(in).replaceAll("");
    }

    public static String cleanResource(String s) {
        return s.toLowerCase().replaceAll("[^a-z0-9_.\\-/:]", "");
    }

    public static String translate(Object ... arr) {
        Language languagemap = Language.m_128107_();
        Object s = "";
        for (Object str : arr) {
            s = (String)s + languagemap.m_6834_(str.toString());
        }
        return s;
    }

    public static String[] splitLines(String s) {
        return s.split("\r\n|\r|\n");
    }

    public static String newLine() {
        return System.getProperty("line.separator");
    }

    public static int parseInt(String s, int i) {
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException numberFormatException) {
            return i;
        }
    }

    public static boolean areEqual(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equalsIgnoreCase(s2);
    }

    public static boolean areEqual(ResourceLocation s1, ResourceLocation s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.toString().equalsIgnoreCase(s2.toString());
    }

    static {
        Arrays.sort(illegalChars);
        NON_ALPHANUMERIC = Pattern.compile("[^a-zA-Z0-9]");
    }
}

