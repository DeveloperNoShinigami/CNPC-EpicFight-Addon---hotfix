/*
 * Decompiled with CFR 0.152.
 */
package nikedemos.markovnames.generators;

import nikedemos.markovnames.MarkovDictionary;
import nikedemos.markovnames.generators.MarkovAncientGreek;
import nikedemos.markovnames.generators.MarkovAztec;
import nikedemos.markovnames.generators.MarkovCustomNPCsClassic;
import nikedemos.markovnames.generators.MarkovJapanese;
import nikedemos.markovnames.generators.MarkovOldNorse;
import nikedemos.markovnames.generators.MarkovRoman;
import nikedemos.markovnames.generators.MarkovSaami;
import nikedemos.markovnames.generators.MarkovSlavic;
import nikedemos.markovnames.generators.MarkovSpanish;
import nikedemos.markovnames.generators.MarkovWelsh;

public class MarkovGenerator {
    private static final MarkovGenerator[] GENERATOR = new MarkovGenerator[10];
    public MarkovDictionary markov;
    public String name;
    public String symbol;

    public MarkovGenerator(int seqlen) {
    }

    public MarkovGenerator() {
        this(3);
    }

    public String fetch(int gender) {
        return this.stylize(this.markov.generateWord());
    }

    public String fetch() {
        return this.fetch(0);
    }

    public static String fetch(int dictionary, int gender) {
        return GENERATOR[dictionary].fetch(gender);
    }

    public String stylize(String str) {
        return str;
    }

    public String feminize(String element, boolean flag) {
        return element;
    }

    public static void load() {
        MarkovGenerator.GENERATOR[0] = new MarkovRoman(3);
        MarkovGenerator.GENERATOR[1] = new MarkovJapanese(4);
        MarkovGenerator.GENERATOR[2] = new MarkovSlavic(3);
        MarkovGenerator.GENERATOR[3] = new MarkovWelsh(3);
        MarkovGenerator.GENERATOR[4] = new MarkovSaami(3);
        MarkovGenerator.GENERATOR[5] = new MarkovOldNorse(4);
        MarkovGenerator.GENERATOR[6] = new MarkovAncientGreek(3);
        MarkovGenerator.GENERATOR[7] = new MarkovAztec(3);
        MarkovGenerator.GENERATOR[8] = new MarkovCustomNPCsClassic(3);
        MarkovGenerator.GENERATOR[9] = new MarkovSpanish(3);
    }
}

