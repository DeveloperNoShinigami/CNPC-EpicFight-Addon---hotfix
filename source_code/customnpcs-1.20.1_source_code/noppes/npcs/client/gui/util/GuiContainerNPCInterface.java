/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 */
package noppes.npcs.client.gui.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.gui.components.GuiBasicContainer;

public abstract class GuiContainerNPCInterface<T extends AbstractContainerMenu>
extends GuiBasicContainer<T> {
    public EntityNPCInterface npc;

    public GuiContainerNPCInterface(EntityNPCInterface npc, T cont, Inventory inv, Component titleIn) {
        super(cont, inv, titleIn);
        this.npc = npc;
    }

    public void drawNpc(GuiGraphics graphics, int x, int y) {
        this.wrapper.drawNpc(graphics, (LivingEntity)this.npc, x, y, 1.0f, 0, this.guiLeft, this.guiTop);
    }
}

