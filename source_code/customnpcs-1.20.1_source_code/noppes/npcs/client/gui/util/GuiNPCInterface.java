/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs.client.gui.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.gui.components.GuiBasic;

public abstract class GuiNPCInterface
extends GuiBasic {
    public EntityNPCInterface npc;

    public GuiNPCInterface(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public GuiNPCInterface() {
    }

    @Override
    public void setSubGui(Screen gui) {
        if (gui instanceof GuiNPCInterface) {
            ((GuiNPCInterface)gui).npc = this.npc;
        }
        if (gui instanceof GuiContainerNPCInterface) {
            ((GuiContainerNPCInterface)gui).npc = this.npc;
        }
        super.setSubGui(gui);
    }

    public void drawNpc(GuiGraphics graphics, int x, int y) {
        this.drawNpc(graphics, (LivingEntity)this.npc, x, y, 1.0f, 0);
    }
}

