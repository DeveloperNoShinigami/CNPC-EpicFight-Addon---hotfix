/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.Files
 *  net.minecraft.nbt.ByteArrayTag
 *  net.minecraft.nbt.ByteTag
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.DoubleTag
 *  net.minecraft.nbt.FloatTag
 *  net.minecraft.nbt.IntArrayTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.LongArrayTag
 *  net.minecraft.nbt.LongTag
 *  net.minecraft.nbt.ShortTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  org.apache.commons.io.Charsets
 */
package noppes.npcs.util;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.mixin.ListNBTMixin;
import org.apache.commons.io.Charsets;

public class NBTJsonUtil {
    public static String Convert(CompoundTag compound) {
        ArrayList<JsonLine> list = new ArrayList<JsonLine>();
        JsonLine line = NBTJsonUtil.ReadTag("", (Tag)compound, list);
        line.removeComma();
        return NBTJsonUtil.ConvertList(list);
    }

    public static CompoundTag Convert(String json) throws JsonException {
        json = json.trim();
        JsonFile file = new JsonFile(json);
        if (!json.startsWith("{") || !json.endsWith("}")) {
            throw new JsonException("Not properly incapsulated between { }", file);
        }
        CompoundTag compound = new CompoundTag();
        NBTJsonUtil.FillCompound(compound, file);
        return compound;
    }

    public static void FillCompound(CompoundTag compound, JsonFile json) throws JsonException {
        if (json.startsWith("{") || json.startsWith(",")) {
            json.cut(1);
        }
        if (json.startsWith("}")) {
            return;
        }
        int index = json.keyIndex();
        if (index < 1) {
            throw new JsonException("Expected key after ,", json);
        }
        String key = json.substring(0, index);
        json.cut(index + 1);
        Tag base = NBTJsonUtil.ReadValue(json);
        if (base == null) {
            base = StringTag.m_129297_((String)"");
        }
        if (key.startsWith("\"")) {
            key = key.substring(1);
        }
        if (key.endsWith("\"")) {
            key = key.substring(0, key.length() - 1);
        }
        compound.m_128365_(key, base);
        if (json.startsWith(",")) {
            NBTJsonUtil.FillCompound(compound, json);
        }
    }

    public static Tag ReadValue(JsonFile json) throws JsonException {
        if (json.startsWith("{")) {
            CompoundTag compound = new CompoundTag();
            NBTJsonUtil.FillCompound(compound, json);
            if (!json.startsWith("}")) {
                throw new JsonException("Expected }", json);
            }
            json.cut(1);
            return compound;
        }
        if (json.startsWith("[")) {
            json.cut(1);
            ListTag list = new ListTag();
            if (json.startsWith("B;") || json.startsWith("I;") || json.startsWith("L;")) {
                json.cut(2);
            }
            Tag value = NBTJsonUtil.ReadValue(json);
            while (value != null) {
                list.add((Object)value);
                if (!json.startsWith(",")) break;
                json.cut(1);
                value = NBTJsonUtil.ReadValue(json);
            }
            if (!json.startsWith("]")) {
                throw new JsonException("Expected ]", json);
            }
            json.cut(1);
            if (list.m_7264_() == 3) {
                int[] arr = new int[list.size()];
                int i = 0;
                while (list.size() > 0) {
                    arr[i] = ((IntTag)list.remove(0)).m_7047_();
                    ++i;
                }
                return new IntArrayTag(arr);
            }
            if (list.m_7264_() == 1) {
                byte[] arr = new byte[list.size()];
                int i = 0;
                while (list.size() > 0) {
                    arr[i] = ((ByteTag)list.remove(0)).m_7063_();
                    ++i;
                }
                return new ByteArrayTag(arr);
            }
            if (list.m_7264_() == 4) {
                long[] arr = new long[list.size()];
                int i = 0;
                while (list.size() > 0) {
                    arr[i] = ((LongTag)list.remove(0)).m_7063_();
                    ++i;
                }
                return new LongArrayTag(arr);
            }
            return list;
        }
        if (json.startsWith("\"")) {
            json.cut(1);
            Object s = "";
            boolean ignore = false;
            while (!json.startsWith("\"") || ignore) {
                String cut = json.cutDirty(1);
                ignore = cut.equals("\\");
                s = (String)s + cut;
            }
            json.cut(1);
            return StringTag.m_129297_((String)((String)s).replace("\\\\", "\\").replace("\\\"", "\""));
        }
        Object s = "";
        while (!json.startsWith(",", "]", "}")) {
            s = (String)s + json.cut(1);
        }
        if (((String)(s = ((String)s).trim().toLowerCase())).isEmpty()) {
            return null;
        }
        try {
            if (((String)s).endsWith("d")) {
                if (((String)s).equals("nand")) {
                    return DoubleTag.m_128500_((double)Double.NaN);
                }
                return DoubleTag.m_128500_((double)Double.parseDouble(((String)s).substring(0, ((String)s).length() - 1)));
            }
            if (((String)s).endsWith("f")) {
                return FloatTag.m_128566_((float)Float.parseFloat(((String)s).substring(0, ((String)s).length() - 1)));
            }
            if (((String)s).endsWith("b")) {
                return ByteTag.m_128266_((byte)Byte.parseByte(((String)s).substring(0, ((String)s).length() - 1)));
            }
            if (((String)s).endsWith("s")) {
                return ShortTag.m_129258_((short)Short.parseShort(((String)s).substring(0, ((String)s).length() - 1)));
            }
            if (((String)s).endsWith("l")) {
                return LongTag.m_128882_((long)Long.parseLong(((String)s).substring(0, ((String)s).length() - 1)));
            }
            if (((String)s).contains(".")) {
                return DoubleTag.m_128500_((double)Double.parseDouble((String)s));
            }
            return IntTag.m_128679_((int)Integer.parseInt((String)s));
        }
        catch (NumberFormatException ex) {
            throw new JsonException("Unable to convert: " + (String)s + " to a number", json);
        }
    }

    private static JsonLine ReadTag(String name, Tag base, List<JsonLine> list) {
        if (!((String)name).isEmpty()) {
            name = "\"" + (String)name + "\": ";
        }
        if (base.m_7060_() == 9) {
            list.add(new JsonLine((String)name + "["));
            ListTag tags = (ListTag)base;
            JsonLine line = null;
            List<Tag> data = ((ListNBTMixin)tags).getList();
            for (Tag b : data) {
                line = NBTJsonUtil.ReadTag("", b, list);
            }
            if (line != null) {
                line.removeComma();
            }
            list.add(new JsonLine("]"));
        } else if (base.m_7060_() == 10) {
            list.add(new JsonLine((String)name + "{"));
            CompoundTag compound = (CompoundTag)base;
            JsonLine line = null;
            for (Object key : compound.m_128431_()) {
                line = NBTJsonUtil.ReadTag(key.toString(), compound.m_128423_(key.toString()), list);
            }
            if (line != null) {
                line.removeComma();
            }
            list.add(new JsonLine("}"));
        } else if (base.m_7060_() == 11) {
            list.add(new JsonLine((String)name + base.toString().replaceFirst(",]", "]")));
        } else if (base.m_7060_() == 8) {
            list.add(new JsonLine((String)name + NBTJsonUtil.quoteAndEscape(base.m_7916_())));
        } else {
            list.add(new JsonLine((String)name + base));
        }
        JsonLine line = list.get(list.size() - 1);
        line.line = line.line + ",";
        return line;
    }

    private static String ConvertList(List<JsonLine> list) {
        Object json = "";
        int tab = 0;
        for (JsonLine tag : list) {
            if (tag.reduceTab()) {
                --tab;
            }
            for (int i = 0; i < tab; ++i) {
                json = (String)json + "    ";
            }
            json = (String)json + tag + "\n";
            if (!tag.increaseTab()) continue;
            ++tab;
        }
        return json;
    }

    public static CompoundTag LoadFile(File file) throws IOException, JsonException {
        return NBTJsonUtil.Convert(Files.toString((File)file, (Charset)Charsets.UTF_8));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void SaveFile(File file, CompoundTag compound) throws IOException, JsonException {
        String json = NBTJsonUtil.Convert(compound);
        try (OutputStreamWriter writer = null;){
            writer = new OutputStreamWriter((OutputStream)new FileOutputStream(file), Charsets.UTF_8);
            writer.write(json);
        }
    }

    public static void main(String[] args) {
        CompoundTag comp = new CompoundTag();
        CompoundTag comp2 = new CompoundTag();
        comp2.m_128382_("test", new byte[]{0, 0, 1, 1, 0});
        comp.m_128365_("comp", (Tag)comp2);
        System.out.println(NBTJsonUtil.Convert(comp));
    }

    public static String quoteAndEscape(String p_193588_0_) {
        StringBuilder stringbuilder = new StringBuilder("\"");
        for (int i = 0; i < p_193588_0_.length(); ++i) {
            char c0 = p_193588_0_.charAt(i);
            if (c0 == '\\' || c0 == '\"') {
                stringbuilder.append('\\');
            }
            stringbuilder.append(c0);
        }
        return stringbuilder.append('\"').toString();
    }

    static class JsonLine {
        private String line;

        public JsonLine(String line) {
            this.line = line;
        }

        public void removeComma() {
            if (this.line.endsWith(",")) {
                this.line = this.line.substring(0, this.line.length() - 1);
            }
        }

        public boolean reduceTab() {
            int length = this.line.length();
            return length == 1 && (this.line.endsWith("}") || this.line.endsWith("]")) || length == 2 && (this.line.endsWith("},") || this.line.endsWith("],"));
        }

        public boolean increaseTab() {
            return this.line.endsWith("{") || this.line.endsWith("[");
        }

        public String toString() {
            return this.line;
        }
    }

    static class JsonFile {
        private String original;
        private String text;

        public JsonFile(String text) {
            this.text = text;
            this.original = text;
        }

        public int keyIndex() {
            boolean hasQuote = false;
            for (int i = 0; i < this.text.length(); ++i) {
                char c = this.text.charAt(i);
                if (i == 0 && c == '\"') {
                    hasQuote = true;
                } else if (hasQuote && c == '\"') {
                    hasQuote = false;
                }
                if (hasQuote || c != ':') continue;
                return i;
            }
            return -1;
        }

        public String cutDirty(int i) {
            String s = this.text.substring(0, i);
            this.text = this.text.substring(i);
            return s;
        }

        public String cut(int i) {
            String s = this.text.substring(0, i);
            this.text = this.text.substring(i).trim();
            return s;
        }

        public String substring(int beginIndex, int endIndex) {
            return this.text.substring(beginIndex, endIndex);
        }

        public int indexOf(String s) {
            return this.text.indexOf(s);
        }

        public String getCurrentPos() {
            int lengthOr = this.original.length();
            int lengthCur = this.text.length();
            int currentPos = lengthOr - lengthCur;
            String done = this.original.substring(0, currentPos);
            String[] lines = done.split("\r\n|\r|\n");
            int pos = 0;
            String line = "";
            if (lines.length > 0) {
                pos = lines[lines.length - 1].length();
                line = this.original.split("\r\n|\r|\n")[lines.length - 1].trim();
            }
            return "Line: " + lines.length + ", Pos: " + pos + ", Text: " + line;
        }

        public boolean startsWith(String ... ss) {
            for (String s : ss) {
                if (!this.text.startsWith(s)) continue;
                return true;
            }
            return false;
        }

        public boolean endsWith(String s) {
            return this.text.endsWith(s);
        }
    }

    public static class JsonException
    extends Exception {
        public JsonException(String message, JsonFile json) {
            super(message + ": " + json.getCurrentPos());
        }
    }
}

