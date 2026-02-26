/*
 * Decompiled with CFR 0.152.
 */
package nikedemos.markovnames.generators;

import nikedemos.markovnames.MarkovDictionary;
import nikedemos.markovnames.MarkovDictionarySPA;
import nikedemos.markovnames.generators.MarkovGenerator;

public class MarkovSpanish
extends MarkovGenerator {
    public MarkovDictionary markov2;
    public MarkovDictionary markov3;

    public MarkovSpanish(int seqlen) {
        this.markov = new MarkovDictionary("spanish_given_male.txt", seqlen);
        this.markov2 = new MarkovDictionary("spanish_given_female.txt", seqlen);
        this.markov3 = new MarkovDictionarySPA("spanish_surnames.txt", seqlen);
    }

    public MarkovSpanish() {
        this(3);
    }

    @Override
    public String fetch(int gender) {
        String giv = "";
        String sur = this.markov3.generateWord();
        if (gender == 0) {
            gender = MarkovDictionary.rng.nextBoolean() ? 1 : 2;
        }
        giv = gender == 1 ? this.markov.generateWord() : this.markov2.generateWord();
        return giv + " " + sur;
    }
}

