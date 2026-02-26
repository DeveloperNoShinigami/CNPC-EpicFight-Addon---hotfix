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

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.shared.client.gui.listeners.IScrollData;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiScrollData
extends PacketBasic {
    private final Map<String, Integer> data;

    public PacketGuiScrollData(Map<String, Integer> data) {
        this.data = data;
    }

    public static void encode(PacketGuiScrollData msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.data.size());
        for (Map.Entry<String, Integer> e : msg.data.entrySet()) {
            buf.m_130070_(e.getKey());
            buf.writeInt(e.getValue().intValue());
        }
    }

    public static PacketGuiScrollData decode(FriendlyByteBuf buf) {
        HashMap<String, Integer> data = new HashMap<String, Integer>();
        int size = buf.readInt();
        for (int i = 0; i < size; ++i) {
            data.put(buf.m_130136_(Short.MAX_VALUE), buf.readInt());
        }
        return new PacketGuiScrollData(data);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui == null) {
            return;
        }
        if (gui instanceof GuiNPCInterface && ((GuiNPCInterface)gui).hasSubGui()) {
            gui = ((GuiNPCInterface)gui).getSubGui();
        }
        if (gui instanceof GuiContainerNPCInterface && ((GuiContainerNPCInterface)gui).hasSubGui()) {
            gui = ((GuiContainerNPCInterface)gui).getSubGui();
        }
        if (gui instanceof IScrollData) {
            ((IScrollData)gui).setData(new Vector<String>(this.data.keySet()), this.data);
        }
    }
}

