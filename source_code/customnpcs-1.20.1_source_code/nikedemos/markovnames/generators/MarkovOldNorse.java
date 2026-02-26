/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package nikedemos.markovnames.generators;

import net.minecraft.network.chat.Component;
import nikedemos.markovnames.MarkovDictionary;
import nikedemos.markovnames.generators.MarkovGenerator;

public class MarkovOldNorse
extends MarkovGenerator {
    public MarkovDictionary markov2;

    public MarkovOldNorse(int seqlen) {
        this.markov = new MarkovDictionary("old_norse_bothgenders.txt", seqlen);
        this.name = Component.m_237115_((String)"markov.oldNorse").toString();
    }

    public MarkovOldNorse() {
        this(4);
    }

    @Override
    public String fetch(int gender) {
        return this.markov.generateWord();
    }
}

