/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.Util
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client;

import java.io.File;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketGuiOpen;

public class NoppesUtil {
    private static EntityNPCInterface lastNpc;

    public static void requestOpenGUI(EnumGuiType gui) {
        NoppesUtil.requestOpenGUI(gui, BlockPos.f_121853_);
    }

    public static void requestOpenGUI(EnumGuiType gui, BlockPos pos) {
        Packets.sendServer(new SPacketGuiOpen(gui, pos));
    }

    public static EntityNPCInterface getLastNpc() {
        return lastNpc;
    }

    public static void setLastNpc(EntityNPCInterface npc) {
        lastNpc = npc;
    }

    public static void openGUI(Player player, Object guiscreen) {
        CustomNpcs.proxy.openGui(player, guiscreen);
    }

    public static void openFolder(File dir) {
        Util.m_137581_().m_137644_(dir);
    }

    public static void clickSound() {
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_263171_((Holder)SoundEvents.f_12490_, (float)1.0f));
    }
}

