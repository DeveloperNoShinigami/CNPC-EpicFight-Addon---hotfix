/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerboundContainerClosePacket
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 */
package noppes.npcs.client.gui.player.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import noppes.npcs.client.gui.player.tabs.AbstractTab;

public class InventoryTabVanilla
extends AbstractTab {
    public InventoryTabVanilla() {
        super(0, 0, 0, new ItemStack((ItemLike)Blocks.f_50091_));
    }

    @Override
    public void onTabClicked() {
        Minecraft mc = Minecraft.m_91087_();
        mc.f_91074_.f_108617_.m_104955_((Packet)new ServerboundContainerClosePacket(mc.f_91074_.f_36096_.f_38840_));
        InventoryScreen inventory = new InventoryScreen((Player)mc.f_91074_);
        mc.m_91152_((Screen)inventory);
    }

    @Override
    public boolean shouldAddToList() {
        return true;
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
    }
}

