/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import noppes.npcs.api.function.gui.GuiComponentClicked;
import noppes.npcs.api.gui.ICustomGuiComponent;

public interface IScroll
extends ICustomGuiComponent {
    public String[] getList();

    public IScroll setList(String[] var1);

    @Deprecated
    public int getDefaultSelection();

    @Deprecated
    public IScroll setDefaultSelection(int var1);

    public int[] getSelection();

    public IScroll setSelection(int ... var1);

    public String[] getSelectionList();

    public IScroll setSelectionList(String ... var1);

    public boolean isMultiSelect();

    public IScroll setMultiSelect(boolean var1);

    public IScroll setOnClick(GuiComponentClicked<IScroll> var1);

    public IScroll setOnDoubleClick(GuiComponentClicked<IScroll> var1);

    public boolean getHasSearch();

    public IScroll setHasSearch(boolean var1);
}

