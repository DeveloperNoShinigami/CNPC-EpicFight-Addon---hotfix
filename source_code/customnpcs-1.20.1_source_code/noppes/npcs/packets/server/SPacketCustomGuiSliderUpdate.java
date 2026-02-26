/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiSliderWrapper;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketCustomGuiSliderUpdate
extends PacketServerBasic {
    private final UUID id;
    private final float value;

    public SPacketCustomGuiSliderUpdate(UUID id, float value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketCustomGuiSliderUpdate msg, FriendlyByteBuf buf) {
        buf.m_130077_(msg.id);
        buf.writeFloat(msg.value);
    }

    public static SPacketCustomGuiSliderUpdate decode(FriendlyByteBuf buf) {
        return new SPacketCustomGuiSliderUpdate(buf.m_130259_(), buf.readFloat());
    }

    @Override
    protected void handle() {
        AbstractContainerMenu abstractContainerMenu = this.player.f_36096_;
        if (abstractContainerMenu instanceof ContainerCustomGui) {
            ContainerCustomGui container = (ContainerCustomGui)abstractContainerMenu;
            ICustomGuiComponent comp = container.activeGui.getComponentUuid(this.id);
            if (comp instanceof CustomGuiSliderWrapper) {
                CustomGuiSliderWrapper slider = (CustomGuiSliderWrapper)comp;
                slider.setValue(this.value);
                slider.onChange(container.activeGui);
            }
        }
    }
}

