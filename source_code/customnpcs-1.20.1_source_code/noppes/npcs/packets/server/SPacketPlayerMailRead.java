/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerMail;
import noppes.npcs.controllers.data.PlayerMailData;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketPlayerMailRead
extends PacketServerBasic {
    private final long time;
    private final String username;

    public SPacketPlayerMailRead(long time, String username) {
        this.time = time;
        this.username = username;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketPlayerMailRead msg, FriendlyByteBuf buf) {
        buf.writeLong(msg.time);
        buf.m_130070_(msg.username);
    }

    public static SPacketPlayerMailRead decode(FriendlyByteBuf buf) {
        return new SPacketPlayerMailRead(buf.readLong(), buf.m_130136_(Short.MAX_VALUE));
    }

    @Override
    protected void handle() {
        PlayerMailData data = PlayerData.get((Player)this.player).mailData;
        for (PlayerMail mail : data.playermail) {
            if (mail.beenRead || mail.time != this.time || !mail.sender.equals(this.username)) continue;
            if (mail.hasQuest()) {
                PlayerQuestController.addActiveQuest(mail.getQuest(), (Player)this.player);
            }
            mail.beenRead = true;
        }
    }
}

