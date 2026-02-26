/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.handler.data;

import java.util.List;
import noppes.npcs.api.handler.data.IAvailability;
import noppes.npcs.api.handler.data.IDialogCategory;
import noppes.npcs.api.handler.data.IDialogOption;
import noppes.npcs.api.handler.data.IQuest;

public interface IDialog {
    public int getId();

    public String getName();

    public void setName(String var1);

    public String getText();

    public void setText(String var1);

    public IQuest getQuest();

    public void setQuest(IQuest var1);

    public String getCommand();

    public void setCommand(String var1);

    public List<IDialogOption> getOptions();

    public IDialogOption getOption(int var1);

    public IAvailability getAvailability();

    public IDialogCategory getCategory();

    public void save();
}

