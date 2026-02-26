/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.shared.common.PacketBasic;
import noppes.npcs.shared.common.util.LogWriter;

public class PacketGuiOpen
extends PacketBasic {
    private final EnumGuiType gui;
    private final BlockPos pos;

    public PacketGuiOpen(EnumGuiType gui, BlockPos pos) {
        this.gui = gui;
        this.pos = pos;
    }

    public static void encode(PacketGuiOpen msg, FriendlyByteBuf buf) {
        buf.m_130068_((Enum)msg.gui);
        buf.m_130064_(msg.pos);
    }

    public static PacketGuiOpen decode(FriendlyByteBuf buf) {
        return new PacketGuiOpen((EnumGuiType)buf.m_130066_(EnumGuiType.class), buf.m_130135_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        try {
            FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
            buffer.m_130064_(this.pos);
            Minecraft minecraft = Minecraft.m_91087_();
            minecraft.m_91152_(ClientProxy.getGui(this.gui, NoppesUtil.getLastNpc(), buffer));
        }
        catch (Exception e) {
            LogWriter.error("Error in gui: " + this.gui, e);
        }
    }
}

