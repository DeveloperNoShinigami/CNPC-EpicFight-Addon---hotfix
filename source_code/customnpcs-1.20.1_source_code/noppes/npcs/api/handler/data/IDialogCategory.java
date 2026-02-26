/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.handler.data;

import java.util.List;
import noppes.npcs.api.handler.data.IDialog;

public interface IDialogCategory {
    public List<IDialog> dialogs();

    public String getName();

    public IDialog create();
}

