/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.eventbus.api.Event
 */
package noppes.npcs.controllers;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import noppes.npcs.api.event.CustomGuiEvent;
import noppes.npcs.api.wrapper.WrapperNpcAPI;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.constants.EnumScriptType;
import noppes.npcs.containers.ContainerCustomGui;

public class CustomGuiController {
    static boolean checkGui(CustomGuiEvent event) {
        Object player = event.player.getMCEntity();
        if (!(((Player)player).f_36096_ instanceof ContainerCustomGui)) {
            return false;
        }
        return ((ContainerCustomGui)((Player)player).f_36096_).customGui.getID() == event.gui.getID();
    }

    public static void onButton(CustomGuiEvent.ButtonEvent event) {
        Object player = event.player.getMCEntity();
        if (CustomGuiController.checkGui(event) && CustomGuiController.getOpenGui(player).getScriptHandler() != null) {
            ((CustomGuiWrapper)event.gui).getScriptHandler().run(EnumScriptType.CUSTOM_GUI_BUTTON, event);
        }
        WrapperNpcAPI.EVENT_BUS.post((Event)event);
    }

    public static void onQuickCraft(CustomGuiEvent.SlotEvent event) {
        Object player = event.player.getMCEntity();
        if (CustomGuiController.checkGui(event) && CustomGuiController.getOpenGui(player).getScriptHandler() != null) {
            ((CustomGuiWrapper)event.gui).getScriptHandler().run(EnumScriptType.CUSTOM_GUI_SLOT, event);
        }
        WrapperNpcAPI.EVENT_BUS.post((Event)event);
    }

    public static void onScrollClick(CustomGuiEvent.ScrollEvent event) {
        Object player = event.player.getMCEntity();
        if (CustomGuiController.checkGui(event) && CustomGuiController.getOpenGui(player).getScriptHandler() != null) {
            ((CustomGuiWrapper)event.gui).getScriptHandler().run(EnumScriptType.CUSTOM_GUI_SCROLL, event);
        }
        WrapperNpcAPI.EVENT_BUS.post((Event)event);
    }

    public static boolean onSlotClick(CustomGuiEvent.SlotClickEvent event) {
        Object player = event.player.getMCEntity();
        if (CustomGuiController.checkGui(event) && CustomGuiController.getOpenGui(player).getScriptHandler() != null) {
            ((CustomGuiWrapper)event.gui).getScriptHandler().run(EnumScriptType.CUSTOM_GUI_SLOT_CLICKED, event);
        }
        return WrapperNpcAPI.EVENT_BUS.post((Event)event);
    }

    public static void onClose(CustomGuiEvent.CloseEvent event) {
        Object player = event.player.getMCEntity();
        if (CustomGuiController.checkGui(event) && CustomGuiController.getOpenGui(player).getScriptHandler() != null) {
            ((CustomGuiWrapper)event.gui).getScriptHandler().run(EnumScriptType.CUSTOM_GUI_CLOSED, event);
        }
        WrapperNpcAPI.EVENT_BUS.post((Event)event);
    }

    public static CustomGuiWrapper getOpenGui(Player player) {
        if (player.f_36096_ instanceof ContainerCustomGui) {
            return ((ContainerCustomGui)player.f_36096_).customGui;
        }
        return null;
    }
}

