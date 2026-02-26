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
import noppes.npcs.api.wrapper.gui.CustomGuiAssetsSelectorWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketCustomGuiTextUpdate
extends PacketServerBasic {
    private final UUID id;
    private final String text;

    public SPacketCustomGuiTextUpdate(UUID id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketCustomGuiTextUpdate msg, FriendlyByteBuf buf) {
        buf.m_130077_(msg.id);
        buf.m_130072_(msg.text, 131068);
    }

    public static SPacketCustomGuiTextUpdate decode(FriendlyByteBuf buf) {
        return new SPacketCustomGuiTextUpdate(buf.m_130259_(), buf.m_130136_(131068));
    }

    @Override
    protected void handle() {
        AbstractContainerMenu abstractContainerMenu = this.player.f_36096_;
        if (abstractContainerMenu instanceof ContainerCustomGui) {
            ContainerCustomGui container = (ContainerCustomGui)abstractContainerMenu;
            ICustomGuiComponent comp = container.activeGui.getComponentUuid(this.id);
            if (comp instanceof CustomGuiTextFieldWrapper) {
                CustomGuiTextFieldWrapper tf = (CustomGuiTextFieldWrapper)comp;
                tf.setText(this.text);
                tf.onChange(container.activeGui);
            }
            if (comp instanceof CustomGuiAssetsSelectorWrapper) {
                CustomGuiAssetsSelectorWrapper as = (CustomGuiAssetsSelectorWrapper)comp;
                as.setSelected(this.text);
                as.onChange(container.activeGui);
            }
        }
    }
}

