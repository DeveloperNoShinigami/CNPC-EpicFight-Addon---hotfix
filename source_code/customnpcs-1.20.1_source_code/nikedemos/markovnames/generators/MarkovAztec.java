/*
 * Decompiled with CFR 0.152.
 */
package nikedemos.markovnames.generators;

import nikedemos.markovnames.MarkovDictionary;
import nikedemos.markovnames.generators.MarkovGenerator;

public class MarkovAztec
extends MarkovGenerator {
    public MarkovAztec(int seqlen) {
        this.markov = new MarkovDictionary("aztec_given.txt", seqlen);
    }

    public MarkovAztec() {
        this(3);
    }

    @Override
    public String fetch(int gender) {
        return this.markov.generateWord();
    }
}

