/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.contents.TranslatableContents
 */
package noppes.npcs.api.wrapper.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiAssetsSelectorWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonListWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiColoredLineWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiEntityDisplayWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiItemRendererWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiItemSlotWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiLabelWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiScrollWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiSliderWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextAreaWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;

public abstract class CustomGuiComponentWrapper
implements ICustomGuiComponent {
    public UUID uniqueId = UUID.randomUUID();
    private int id;
    private int posX;
    private int posY;
    private int width;
    private int height;
    private List<Component> hoverText = new ArrayList<Component>();
    private boolean enabled = true;
    private boolean visible = true;
    public boolean disablePackets = false;

    public CustomGuiComponentWrapper setDisablePackets() {
        this.disablePackets = true;
        return this;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public CustomGuiComponentWrapper setID(int id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean getEnabled() {
        return this.enabled;
    }

    @Override
    public CustomGuiComponentWrapper setEnabled(boolean bo) {
        this.enabled = bo;
        return this;
    }

    @Override
    public boolean getVisible() {
        return this.visible;
    }

    @Override
    public CustomGuiComponentWrapper setVisible(boolean bo) {
        this.visible = bo;
        return this;
    }

    @Override
    public UUID getUniqueID() {
        return this.uniqueId;
    }

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public CustomGuiComponentWrapper setPos(int x, int y) {
        this.posX = x;
        this.posY = y;
        return this;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public CustomGuiComponentWrapper setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public boolean hasHoverText() {
        return this.hoverText.size() > 0;
    }

    @Override
    public String[] getHoverText() {
        String[] ht = new String[this.hoverText.size()];
        for (int i = 0; i < this.hoverText.size(); ++i) {
            ht[i] = ((TranslatableContents)this.hoverText.get(i).m_214077_()).m_237508_();
        }
        return ht;
    }

    public List<Component> getHoverTextList() {
        return this.hoverText;
    }

    @Override
    public CustomGuiComponentWrapper setHoverText(String text) {
        this.hoverText = new ArrayList<Component>();
        this.hoverText.add((Component)Component.m_237115_((String)text));
        return this;
    }

    @Override
    public CustomGuiComponentWrapper setHoverText(String[] text) {
        this.hoverText = new ArrayList<Component>();
        ArrayList<Component> list = new ArrayList<Component>();
        for (String s : text) {
            list.add((Component)Component.m_237115_((String)s));
        }
        this.hoverText = list;
        return this;
    }

    public CompoundTag toNBT(CompoundTag nbt) {
        nbt.m_128405_("id", this.id);
        nbt.m_128379_("enabled", this.enabled);
        nbt.m_128379_("visible", this.visible);
        nbt.m_128362_("uniqueId", this.uniqueId);
        nbt.m_128385_("pos", new int[]{this.posX, this.posY});
        nbt.m_128385_("size", new int[]{this.width, this.height});
        if (this.hoverText != null) {
            ListTag list = new ListTag();
            for (Component s : this.hoverText) {
                list.add((Object)StringTag.m_129297_((String)((TranslatableContents)s.m_214077_()).m_237508_()));
            }
            if (list.size() > 0) {
                nbt.m_128365_("hover", (Tag)list);
            }
        }
        nbt.m_128405_("type", this.getType());
        return nbt;
    }

    public CustomGuiComponentWrapper fromNBT(CompoundTag nbt) {
        this.setID(nbt.m_128451_("id"));
        this.setEnabled(nbt.m_128471_("enabled"));
        this.setVisible(nbt.m_128471_("visible"));
        this.uniqueId = nbt.m_128342_("uniqueId");
        this.setPos(nbt.m_128465_("pos")[0], nbt.m_128465_("pos")[1]);
        this.setSize(nbt.m_128465_("size")[0], nbt.m_128465_("size")[1]);
        if (nbt.m_128441_("hover")) {
            ListTag list = nbt.m_128437_("hover", 8);
            String[] hoverText = new String[list.size()];
            for (int i = 0; i < list.size(); ++i) {
                hoverText[i] = list.get(i).m_7916_();
            }
            this.setHoverText(hoverText);
        }
        return this;
    }

    public static CustomGuiComponentWrapper createFromNBT(CompoundTag nbt) {
        switch (nbt.m_128451_("type")) {
            case 0: {
                return new CustomGuiButtonWrapper().fromNBT(nbt);
            }
            case 7: {
                return new CustomGuiButtonListWrapper().fromNBT(nbt);
            }
            case 1: {
                return new CustomGuiLabelWrapper().fromNBT(nbt);
            }
            case 2: {
                return new CustomGuiTexturedRectWrapper().fromNBT(nbt);
            }
            case 3: {
                return new CustomGuiTextFieldWrapper().fromNBT(nbt);
            }
            case 4: {
                return new CustomGuiScrollWrapper().fromNBT(nbt);
            }
            case 5: {
                return new CustomGuiItemSlotWrapper().fromNBT(nbt);
            }
            case 6: {
                return new CustomGuiTextAreaWrapper().fromNBT(nbt);
            }
            case 8: {
                return new CustomGuiSliderWrapper().fromNBT(nbt);
            }
            case 9: {
                return new CustomGuiEntityDisplayWrapper().fromNBT(nbt);
            }
            case 10: {
                return new CustomGuiAssetsSelectorWrapper().fromNBT(nbt);
            }
            case 11: {
                return new CustomGuiColoredLineWrapper().fromNBT(nbt);
            }
            case 12: {
                return new CustomGuiItemRendererWrapper().fromNBT(nbt);
            }
        }
        return null;
    }
}

