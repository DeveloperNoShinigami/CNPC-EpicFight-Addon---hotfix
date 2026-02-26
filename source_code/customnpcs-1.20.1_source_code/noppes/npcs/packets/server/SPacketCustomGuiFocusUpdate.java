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
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketCustomGuiFocusUpdate
extends PacketServerBasic {
    private final UUID id;
    private final boolean focus;

    public SPacketCustomGuiFocusUpdate(UUID id, boolean focus) {
        this.id = id;
        this.focus = focus;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketCustomGuiFocusUpdate msg, FriendlyByteBuf buf) {
        buf.m_130077_(msg.id);
        buf.writeBoolean(msg.focus);
    }

    public static SPacketCustomGuiFocusUpdate decode(FriendlyByteBuf buf) {
        return new SPacketCustomGuiFocusUpdate(buf.m_130259_(), buf.readBoolean());
    }

    @Override
    protected void handle() {
        AbstractContainerMenu abstractContainerMenu = this.player.f_36096_;
        if (abstractContainerMenu instanceof ContainerCustomGui) {
            ContainerCustomGui container = (ContainerCustomGui)abstractContainerMenu;
            ICustomGuiComponent comp = container.activeGui.getComponentUuid(this.id);
            if (comp instanceof CustomGuiTextFieldWrapper) {
                CustomGuiTextFieldWrapper tf = (CustomGuiTextFieldWrapper)comp;
                PlayerWrapper p = (PlayerWrapper)NpcAPI.Instance().getIEntity((Entity)this.player);
                tf.setFocused(this.focus);
                if (!this.focus) {
                    tf.onFocusLost(container.activeGui);
                }
            }
        }
    }
}

