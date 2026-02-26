/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.shared.common.util;

import java.util.Comparator;

public class NaturalOrderComparator
implements Comparator<String> {
    int compareRight(String a, String b) {
        int bias = 0;
        int ia = 0;
        int ib = 0;
        while (true) {
            char ca = NaturalOrderComparator.charAt(a, ia);
            char cb = NaturalOrderComparator.charAt(b, ib);
            if (!Character.isDigit(ca) && !Character.isDigit(cb)) {
                return bias;
            }
            if (!Character.isDigit(ca)) {
                return -1;
            }
            if (!Character.isDigit(cb)) {
                return 1;
            }
            if (ca < cb) {
                if (bias == 0) {
                    bias = -1;
                }
            } else if (ca > cb) {
                if (bias == 0) {
                    bias = 1;
                }
            } else if (ca == '\u0000' && cb == '\u0000') {
                return bias;
            }
            ++ia;
            ++ib;
        }
    }

    @Override
    public int compare(String o1, String o2) {
        String a = o1.toLowerCase();
        String b = o2.toLowerCase();
        int ia = 0;
        int ib = 0;
        int nza = 0;
        int nzb = 0;
        while (true) {
            int result;
            nzb = 0;
            nza = 0;
            char ca = NaturalOrderComparator.charAt(a, ia);
            char cb = NaturalOrderComparator.charAt(b, ib);
            while (Character.isSpaceChar(ca) || ca == '0') {
                nza = ca == '0' ? ++nza : 0;
                ca = NaturalOrderComparator.charAt(a, ++ia);
            }
            while (Character.isSpaceChar(cb) || cb == '0') {
                nzb = cb == '0' ? ++nzb : 0;
                cb = NaturalOrderComparator.charAt(b, ++ib);
            }
            if (Character.isDigit(ca) && Character.isDigit(cb) && (result = this.compareRight(a.substring(ia), b.substring(ib))) != 0) {
                return result;
            }
            if (ca == '\u0000' && cb == '\u0000') {
                return nza - nzb;
            }
            if (ca < cb) {
                return -1;
            }
            if (ca > cb) {
                return 1;
            }
            ++ia;
            ++ib;
        }
    }

    static char charAt(String s, int i) {
        if (i >= s.length()) {
            return '\u0000';
        }
        return s.charAt(i);
    }
}

