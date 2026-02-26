/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui.script;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.blocks.tiles.TileScripted;
import noppes.npcs.client.gui.script.GuiScriptInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketScriptGet;
import noppes.npcs.packets.server.SPacketScriptSave;

public class GuiScriptBlock
extends GuiScriptInterface {
    private TileScripted script;

    public GuiScriptBlock(BlockPos pos) {
        this.script = (TileScripted)this.player.m_9236_().m_7702_(pos);
        this.handler = this.script;
        Packets.sendServer(new SPacketScriptGet(1));
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.script.setNBT(compound);
        super.setGuiData(compound);
    }

    @Override
    public void save() {
        super.save();
        Packets.sendServer(new SPacketScriptSave(1, this.script.getNBT(new CompoundTag())));
    }
}

