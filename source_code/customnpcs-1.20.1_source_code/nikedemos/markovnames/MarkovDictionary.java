/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 */
package nikedemos.markovnames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import nikedemos.markovnames.HashMap2D;
import noppes.npcs.CustomNpcs;

public class MarkovDictionary {
    public static final Random rng = new Random();
    private int sequenceLen = 3;
    private HashMap2D<String, String, Integer> occurrences = new HashMap2D();

    public MarkovDictionary(String dictionary, int seqlen) {
        try {
            this.applyDictionary(dictionary, seqlen);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MarkovDictionary(String dictionary) {
        this(dictionary, 3);
    }

    public String getCapitalized(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    public static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path, new String[0]));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public void incrementSafe(String str1, String str2) {
        if (this.occurrences.containsKeys(str1, str2)) {
            int curr = this.occurrences.get(str1, str2);
            this.occurrences.put(str1, str2, curr + 1);
        } else {
            this.occurrences.put(str1, str2, 1);
        }
    }

    public String generateWord() {
        int allEntries = 0;
        for (Map.Entry pair : this.occurrences.mMap.entrySet()) {
            String k = (String)pair.getKey();
            if (!k.startsWith("_[") || !k.endsWith("_")) continue;
            allEntries += this.occurrences.get(k, "_TOTAL_").intValue();
        }
        int randomNumber = rng.nextInt(allEntries);
        Iterator it = this.occurrences.mMap.entrySet().iterator();
        StringBuilder sequence = new StringBuilder("");
        while (it.hasNext()) {
            Map.Entry pair = it.next();
            String k = (String)pair.getKey();
            if (!k.startsWith("_[") || !k.endsWith("_")) continue;
            int topLevelEntries = this.occurrences.get(k, "_TOTAL_");
            if (randomNumber < topLevelEntries) {
                sequence.append(k.substring(1, this.sequenceLen + 1));
                break;
            }
            randomNumber -= topLevelEntries;
        }
        StringBuilder word = new StringBuilder("");
        word.append((CharSequence)sequence);
        while (sequence.charAt(sequence.length() - 1) != ']') {
            int subSize = 0;
            for (Map.Entry entry : this.occurrences.mMap.get(sequence.toString()).entrySet()) {
                subSize += ((Integer)entry.getValue()).intValue();
            }
            randomNumber = rng.nextInt(subSize);
            Iterator k = this.occurrences.mMap.get(sequence.toString()).entrySet().iterator();
            String chosen = "";
            while (k.hasNext()) {
                Map.Entry entry = k.next();
                int occu = this.occurrences.get(sequence.toString(), (String)entry.getKey());
                if (randomNumber < occu) {
                    chosen = (String)entry.getKey();
                    break;
                }
                randomNumber -= occu;
            }
            word.append(chosen);
            sequence.delete(0, 1);
            sequence.append(chosen);
        }
        return this.getPost(word.substring(1, word.length() - 1));
    }

    public String getPost(String str) {
        return this.getCapitalized(str);
    }

    public void applyDictionary(String dictionaryFile, int seqLen) throws IOException {
        StringBuilder input = new StringBuilder();
        ResourceLocation resource = new ResourceLocation("customnpcs", "markovnames/" + dictionaryFile);
        Resource ir = CustomNpcs.Server.getServerResources().f_206584_().m_213713_(resource).orElse(null);
        try (InputStream stream = ir.m_215507_();){
            BufferedReader readIn = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = readIn.readLine();
            while (line != null) {
                input.append(line).append(" ");
                line = readIn.readLine();
            }
            readIn.close();
        }
        if (input.length() == 0) {
            throw new RuntimeException("Resource was empty: + " + resource);
        }
        if (this.sequenceLen != seqLen) {
            this.sequenceLen = seqLen;
            this.occurrences.clear();
        }
        String input_str = "[" + input.toString().toLowerCase().replaceAll("[\\t\\n\\r\\s]+", "][") + "]";
        int maxCursorPos = input_str.length() - 1 - this.sequenceLen;
        for (int i = 0; i <= maxCursorPos; ++i) {
            String seqCurr = input_str.substring(i, i + this.sequenceLen);
            String seqNext = input_str.substring(i + this.sequenceLen, i + this.sequenceLen + 1);
            this.incrementSafe(seqCurr, seqNext);
            StringBuilder meta = new StringBuilder("_").append(seqCurr).append("_");
            this.incrementSafe(meta.toString(), "_TOTAL_");
        }
    }
}

