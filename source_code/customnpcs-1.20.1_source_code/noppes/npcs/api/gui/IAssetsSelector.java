/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.function.gui.GuiComponentClicked;
import noppes.npcs.api.function.gui.GuiComponentUpdate;
import noppes.npcs.api.gui.ICustomGuiComponent;

public interface IAssetsSelector
extends ICustomGuiComponent {
    public String getSelected();

    public IAssetsSelector setSelected(String var1);

    public String getRoot();

    public IAssetsSelector setRoot(String var1);

    public String getFileType();

    public IAssetsSelector setFileType(String var1);

    public IAssetsSelector setOnChange(GuiComponentUpdate<IAssetsSelector> var1);

    public IAssetsSelector setOnPress(GuiComponentClicked<IAssetsSelector> var1);
}

