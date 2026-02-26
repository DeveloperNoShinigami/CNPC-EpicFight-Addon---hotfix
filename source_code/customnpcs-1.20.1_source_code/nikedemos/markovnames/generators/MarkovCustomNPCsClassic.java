/*
 * Decompiled with CFR 0.152.
 */
package nikedemos.markovnames.generators;

import nikedemos.markovnames.MarkovDictionary;
import nikedemos.markovnames.generators.MarkovGenerator;

public class MarkovCustomNPCsClassic
extends MarkovGenerator {
    public MarkovCustomNPCsClassic(int seqlen) {
        this.markov = new MarkovDictionary("customnpcs_classic.txt", seqlen);
    }

    public MarkovCustomNPCsClassic() {
        this(3);
    }

    @Override
    public String fetch(int gender) {
        return this.markov.generateWord();
    }
}

