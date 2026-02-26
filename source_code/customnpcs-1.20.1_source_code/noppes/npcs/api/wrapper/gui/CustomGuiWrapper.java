/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.api.wrapper.gui;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.gui.ICustomGui;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.gui.ITexturedRect;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;
import noppes.npcs.api.wrapper.gui.GuiComponentsScrollableWrapper;
import noppes.npcs.api.wrapper.gui.GuiComponentsWrapper;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiComponentUpdate;
import noppes.npcs.packets.client.PacketGuiData;

public class CustomGuiWrapper
extends GuiComponentsWrapper
implements ICustomGui {
    private int id;
    private int width;
    private int height;
    private boolean pauseGame;
    private boolean closesOnEsc;
    private final CustomGuiTexturedRectWrapper background = new CustomGuiTexturedRectWrapper();
    private final GuiComponentsScrollableWrapper scrollingPanel;
    private ScriptContainer scriptHandler;
    private CustomGuiWrapper parent;
    private CustomGuiWrapper subgui;
    public EntityCustomNpc npc;

    public CustomGuiWrapper(IPlayer player) {
        super(player);
        this.scrollingPanel = new GuiComponentsScrollableWrapper(this, player);
    }

    public CustomGuiWrapper(IPlayer player, int id, int width, int height, boolean pauseGame) {
        this(player);
        this.id = id;
        this.setSize(width, height);
        this.pauseGame = pauseGame;
        this.closesOnEsc = true;
        this.scriptHandler = ScriptContainer.Current;
        this.background.setID(-1);
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public ScriptContainer getScriptHandler() {
        return this.scriptHandler;
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        if (this.background.getWidth() <= 0 || this.background.getHeight() <= 0) {
            this.background.setSize(width, height);
        }
    }

    @Override
    public void setDoesPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
    }

    @Override
    public void setClosesOnEsc(boolean closesOnEsc) {
        this.closesOnEsc = closesOnEsc;
    }

    public boolean getClosesOnEsc() {
        return this.closesOnEsc;
    }

    public boolean getDoesPauseGame() {
        return this.pauseGame;
    }

    @Override
    public void setBackgroundTexture(String resourceLocation) {
        this.background.texture = resourceLocation;
    }

    public String getBackgroundTexture() {
        return this.background.texture;
    }

    public ITexturedRect getBackgroundRect() {
        return this.background;
    }

    @Override
    public GuiComponentsScrollableWrapper getScrollingPanel() {
        return this.scrollingPanel;
    }

    @Override
    public void openSubGui(ICustomGui gui) {
        this.subgui = (CustomGuiWrapper)gui;
        this.subgui.parent = this;
        this.subgui.npc = this.npc;
        this.getRootGui().update();
    }

    @Override
    public CustomGuiWrapper getSubGui() {
        return this.subgui;
    }

    @Override
    public boolean hasSubGui() {
        return this.subgui != null;
    }

    @Override
    public CustomGuiWrapper closeSubGui() {
        if (this.subgui == null) {
            throw new CustomNPCsException("Current gui has no subgui", new Object[0]);
        }
        CustomGuiWrapper gui = this.subgui;
        this.subgui = null;
        this.player.showCustomGui(this.getRootGui());
        return gui;
    }

    @Override
    public void close() {
        if (this.parent == null) {
            this.player.closeGui();
        } else {
            this.parent.subgui = null;
            this.getRootGui().update();
        }
    }

    @Override
    public CustomGuiWrapper getParentGui() {
        return this.parent;
    }

    @Override
    public CustomGuiWrapper getRootGui() {
        if (this.parent == null) {
            return this;
        }
        return this.parent.getRootGui();
    }

    @Override
    public CustomGuiWrapper getActiveGui() {
        if (this.subgui == null) {
            return this;
        }
        return this.subgui.getActiveGui();
    }

    @Override
    public IPlayer getPlayer() {
        return this.player;
    }

    @Override
    public void update() {
        if (((ServerPlayer)this.player.getMCEntity()).f_36096_ instanceof ContainerCustomGui) {
            Packets.send(this.player.getMCEntity(), new PacketGuiData(this.getRootGui().toNBT()));
        }
        ((ContainerCustomGui)((ServerPlayer)this.player.getMCEntity()).f_36096_).setGui(this.getActiveGui(), (Player)this.player.getMCEntity());
    }

    @Override
    public void update(ICustomGuiComponent component) {
        if (((ServerPlayer)this.player.getMCEntity()).f_36096_ instanceof ContainerCustomGui) {
            Packets.send(this.player.getMCEntity(), new PacketGuiComponentUpdate(component.getUniqueID(), ((CustomGuiComponentWrapper)component).toNBT(new CompoundTag())));
        }
    }

    public ICustomGui fromNBT(CompoundTag tag) {
        this.id = tag.m_128451_("id");
        this.width = tag.m_128465_("size")[0];
        this.height = tag.m_128465_("size")[1];
        this.pauseGame = tag.m_128471_("pause");
        this.closesOnEsc = tag.m_128471_("closesOnEsc");
        this.background.fromNBT(tag.m_128469_("backgroundRect"));
        this.setComponentNbt(tag.m_128469_("components"));
        this.scrollingPanel.setComponentNbt(tag.m_128469_("scrolling_components"));
        if (tag.m_128441_("subgui")) {
            if (this.subgui == null) {
                this.subgui = new CustomGuiWrapper(this.player);
                this.subgui.fromNBT(tag.m_128469_("subgui"));
            }
        } else {
            this.subgui = null;
        }
        return this;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.m_128405_("id", this.id);
        tag.m_128385_("size", new int[]{this.width, this.height});
        tag.m_128379_("pause", this.pauseGame);
        tag.m_128379_("closesOnEsc", this.closesOnEsc);
        tag.m_128365_("backgroundRect", (Tag)this.background.toNBT(new CompoundTag()));
        tag.m_128365_("components", (Tag)this.getComponentNbt());
        tag.m_128365_("scrolling_components", (Tag)this.scrollingPanel.getComponentNbt());
        if (this.parent == null) {
            tag.m_128405_("slotSize", this.getActiveGui().getSlots().size());
        }
        if (this.subgui != null) {
            tag.m_128365_("subgui", (Tag)this.subgui.toNBT());
        }
        return tag;
    }

    @Override
    public ICustomGuiComponent getComponentUuid(UUID id) {
        ICustomGuiComponent comp;
        if (this.subgui != null && (comp = this.subgui.getComponentUuid(id)) != null) {
            return comp;
        }
        comp = super.getComponentUuid(id);
        if (comp != null) {
            return comp;
        }
        return this.scrollingPanel.getComponentUuid(id);
    }
}

