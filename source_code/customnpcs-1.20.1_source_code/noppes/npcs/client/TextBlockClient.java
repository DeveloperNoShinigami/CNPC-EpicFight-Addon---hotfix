/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package noppes.npcs.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import noppes.npcs.TextBlock;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.shared.client.util.NoppesStringUtils;

public class TextBlockClient
extends TextBlock {
    public int color = 0xE0E0E0;
    private String name;
    private CommandSourceStack sender;

    public TextBlockClient(String name, String text, int lineWidth, int color, Object ... obs) {
        this(text, lineWidth, false, obs);
        this.color = color;
        this.name = name;
    }

    public TextBlockClient(CommandSourceStack sender, String text, int lineWidth, int color, Object ... obs) {
        this(text, lineWidth, false, obs);
        this.color = color;
        this.sender = sender;
    }

    public String getName() {
        if (this.sender != null) {
            return this.sender.m_81368_();
        }
        return this.name;
    }

    public TextBlockClient(String text, int lineWidth, boolean mcFont, Object ... obs) {
        text = NoppesStringUtils.formatText(text, obs);
        Object line = "";
        Font font = Minecraft.m_91087_().f_91062_;
        String language = Minecraft.m_91087_().m_91102_().m_264236_();
        if (language.startsWith("zh")) {
            for (int i = 0; i < text.length(); ++i) {
                line = (String)line + text.charAt(i);
                if ((mcFont ? font.m_92895_((String)line) : ClientProxy.Font.width((String)line)) <= lineWidth) continue;
                this.addLine((String)line);
                line = "";
            }
        } else {
            String[] words;
            text = text.replace("\n", " \n ");
            text = text.replace("\r", " \r ");
            for (String word : words = text.split(" ")) {
                char c;
                if (word.isEmpty()) continue;
                if (word.length() == 1 && ((c = word.charAt(0)) == '\r' || c == '\n')) {
                    this.addLine((String)line);
                    line = "";
                    continue;
                }
                Object newLine = ((String)line).isEmpty() ? word : (String)line + " " + word;
                if ((mcFont ? font.m_92895_((String)newLine) : ClientProxy.Font.width((String)newLine)) > lineWidth) {
                    this.addLine((String)line);
                    line = word.trim();
                    continue;
                }
                line = newLine;
            }
        }
        if (!((String)line).isEmpty()) {
            this.addLine((String)line);
        }
    }

    private void addLine(String text) {
        MutableComponent line = Component.m_237113_((String)text);
        this.lines.add(line);
    }
}

