/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.blocks.tiles.TileWaypoint;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketTileEntityGet;
import noppes.npcs.packets.server.SPacketTileEntitySave;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiData;

public class GuiNpcWaypoint
extends GuiNPCInterface
implements IGuiData {
    private TileWaypoint tile;

    public GuiNpcWaypoint(BlockPos pos) {
        this.tile = (TileWaypoint)this.player.m_9236_().m_7702_(pos);
        Packets.sendServer(new SPacketTileEntityGet(pos));
        this.imageWidth = 265;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (this.tile == null) {
            this.close();
        }
        this.addLabel(new GuiLabel(0, "gui.name", this.guiLeft + 1, this.guiTop + 76, 0xFFFFFF));
        this.addTextField(new GuiTextFieldNop(0, (Screen)this, this.guiLeft + 60, this.guiTop + 71, 200, 20, this.tile.name));
        this.addLabel(new GuiLabel(1, "gui.range", this.guiLeft + 1, this.guiTop + 97, 0xFFFFFF));
        this.addTextField(new GuiTextFieldNop(1, (Screen)this, this.guiLeft + 60, this.guiTop + 92, 200, 20, "" + this.tile.range));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(2, 60, 10);
        this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 40, this.guiTop + 190, 120, 20, "Done"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 0) {
            this.close();
        }
    }

    @Override
    public void save() {
        this.tile.name = this.getTextField(0).m_94155_();
        this.tile.range = this.getTextField(1).getInteger();
        Packets.sendServer(new SPacketTileEntitySave(this.tile.m_187480_()));
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.tile.m_142466_(compound);
        this.m_7856_();
    }
}

