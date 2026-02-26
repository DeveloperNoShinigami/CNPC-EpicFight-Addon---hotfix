/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import java.util.Vector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.shared.client.gui.listeners.IScrollData;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiScrollList
extends PacketBasic {
    private final Vector<String> data;

    public PacketGuiScrollList(Vector<String> data) {
        this.data = data;
    }

    public static void encode(PacketGuiScrollList msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.data.size());
        for (String s : msg.data) {
            buf.m_130070_(s);
        }
    }

    public static PacketGuiScrollList decode(FriendlyByteBuf buf) {
        Vector<String> data = new Vector<String>();
        int size = buf.readInt();
        for (int i = 0; i < size; ++i) {
            data.add(buf.m_130136_(Short.MAX_VALUE));
        }
        return new PacketGuiScrollList(data);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui instanceof GuiNPCInterface && ((GuiNPCInterface)gui).hasSubGui()) {
            gui = ((GuiNPCInterface)gui).getSubGui();
        }
        if (gui == null || !(gui instanceof IScrollData)) {
            return;
        }
        ((IScrollData)gui).setData(this.data, null);
    }
}

