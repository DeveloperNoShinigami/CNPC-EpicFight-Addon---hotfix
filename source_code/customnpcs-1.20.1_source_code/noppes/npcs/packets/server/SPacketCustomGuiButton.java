/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.EventHooks;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiAssetsSelectorWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketCustomGuiButton
extends PacketServerBasic {
    private final UUID buttonId;

    public SPacketCustomGuiButton(UUID id) {
        this.buttonId = id;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketCustomGuiButton msg, FriendlyByteBuf buf) {
        buf.m_130077_(msg.buttonId);
    }

    public static SPacketCustomGuiButton decode(FriendlyByteBuf buf) {
        return new SPacketCustomGuiButton(buf.m_130259_());
    }

    @Override
    protected void handle() {
        AbstractContainerMenu abstractContainerMenu = this.player.f_36096_;
        if (abstractContainerMenu instanceof ContainerCustomGui) {
            PlayerWrapper p;
            ContainerCustomGui container = (ContainerCustomGui)abstractContainerMenu;
            ICustomGuiComponent comp = container.activeGui.getComponentUuid(this.buttonId);
            if (comp instanceof CustomGuiButtonWrapper) {
                CustomGuiButtonWrapper button = (CustomGuiButtonWrapper)comp;
                p = (PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)this.player);
                button.onPress(container.activeGui);
                EventHooks.onCustomGuiButton(p, container.activeGui, button);
            }
            if (comp instanceof CustomGuiAssetsSelectorWrapper) {
                CustomGuiAssetsSelectorWrapper assets = (CustomGuiAssetsSelectorWrapper)comp;
                p = (PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)this.player);
                assets.onPress(container.activeGui);
            }
        }
    }
}

