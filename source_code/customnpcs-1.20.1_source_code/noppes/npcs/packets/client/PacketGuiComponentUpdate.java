/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiComponentUpdate
extends PacketBasic {
    private UUID id;
    private CompoundTag data;

    public PacketGuiComponentUpdate(UUID id, CompoundTag data) {
        this.id = id;
        this.data = data;
    }

    public static void encode(PacketGuiComponentUpdate msg, FriendlyByteBuf buf) {
        buf.m_130077_(msg.id);
        buf.m_130079_(msg.data);
    }

    public static PacketGuiComponentUpdate decode(FriendlyByteBuf buf) {
        return new PacketGuiComponentUpdate(buf.m_130259_(), buf.m_130261_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui == null) {
            return;
        }
        if (gui instanceof GuiCustom) {
            GuiCustom cgui = (GuiCustom)gui;
            CustomGuiComponentWrapper component = (CustomGuiComponentWrapper)cgui.guiWrapper.getComponentUuid(this.id);
            component.fromNBT(this.data);
            IGuiComponent guic = cgui.getComponent(this.id);
            guic.m_7856_();
        }
    }
}

