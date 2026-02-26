/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package noppes.npcs.client.gui.script;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import noppes.npcs.CustomItems;
import noppes.npcs.api.wrapper.ItemScriptedWrapper;
import noppes.npcs.client.gui.script.GuiScriptInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketScriptGet;
import noppes.npcs.packets.server.SPacketScriptSave;

public class GuiScriptItem
extends GuiScriptInterface {
    private ItemScriptedWrapper item = new ItemScriptedWrapper(new ItemStack((ItemLike)CustomItems.scripted_item));

    public GuiScriptItem(Player player) {
        this.handler = this.item;
        Packets.sendServer(new SPacketScriptGet(2));
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.item.setMCNbt(compound);
        super.setGuiData(compound);
    }

    @Override
    public void save() {
        super.save();
        Packets.sendServer(new SPacketScriptSave(2, this.item.getMCNbt()));
    }
}

