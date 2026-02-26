/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.shared.common;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import noppes.npcs.shared.common.util.LogWriter;

public class CommonUtil {
    public static void NotifyOPs(MinecraftServer server, String message, Object ... obs) {
        CommonUtil.NotifyOPs(server, (Component)Component.m_237110_((String)message, (Object[])obs));
    }

    public static void NotifyOPs(MinecraftServer server, Component message) {
        MutableComponent chatcomponenttranslation = Component.m_237113_((String)"").m_7220_(message).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC});
        for (Player entityplayer : server.m_6846_().m_11314_()) {
            if (!entityplayer.m_6102_() || !CommonUtil.isOp(entityplayer)) continue;
            entityplayer.m_213846_((Component)chatcomponenttranslation);
        }
        if (server.m_129880_(Level.f_46428_).m_46469_().m_46207_(GameRules.f_46141_)) {
            LogWriter.info(chatcomponenttranslation.getString());
        }
    }

    public static boolean isOp(Player player) {
        return player.m_20194_().m_6846_().m_11303_(player.m_36316_());
    }
}

