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
import noppes.npcs.api.wrapper.gui.CustomGuiButtonListWrapper;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketCustomGuiButtonList
extends PacketServerBasic {
    private final UUID buttonId;
    private final boolean isRightClick;

    public SPacketCustomGuiButtonList(UUID id, boolean isRightClick) {
        this.buttonId = id;
        this.isRightClick = isRightClick;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketCustomGuiButtonList msg, FriendlyByteBuf buf) {
        buf.m_130077_(msg.buttonId);
        buf.writeBoolean(msg.isRightClick);
    }

    public static SPacketCustomGuiButtonList decode(FriendlyByteBuf buf) {
        return new SPacketCustomGuiButtonList(buf.m_130259_(), buf.readBoolean());
    }

    @Override
    protected void handle() {
        AbstractContainerMenu abstractContainerMenu = this.player.f_36096_;
        if (abstractContainerMenu instanceof ContainerCustomGui) {
            ContainerCustomGui container = (ContainerCustomGui)abstractContainerMenu;
            ICustomGuiComponent comp = container.activeGui.getComponentUuid(this.buttonId);
            if (comp instanceof CustomGuiButtonListWrapper) {
                CustomGuiButtonListWrapper button = (CustomGuiButtonListWrapper)comp;
                PlayerWrapper p = (PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)this.player);
                button.setSelected(button.getSelected() + (this.isRightClick ? 1 : -1));
                button.onPress(container.activeGui);
                EventHooks.onCustomGuiButton(p, container.activeGui, button);
            }
        }
    }
}

