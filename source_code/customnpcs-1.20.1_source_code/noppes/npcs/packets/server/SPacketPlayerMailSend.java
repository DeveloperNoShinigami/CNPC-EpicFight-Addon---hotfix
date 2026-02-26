/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.EventHooks;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.event.RoleEvent;
import noppes.npcs.containers.ContainerMail;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.data.PlayerMail;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketPlayerMailSend
extends PacketServerBasic {
    private final CompoundTag data;
    private final String username;

    public SPacketPlayerMailSend(String username, CompoundTag data) {
        this.username = username;
        this.data = data;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketPlayerMailSend msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.username);
        buf.m_130079_(msg.data);
    }

    public static SPacketPlayerMailSend decode(FriendlyByteBuf buf) {
        return new SPacketPlayerMailSend(buf.m_130136_(Short.MAX_VALUE), buf.m_130260_());
    }

    @Override
    protected void handle() {
        String username = PlayerDataController.instance.hasPlayer(this.username);
        if (username.isEmpty()) {
            NoppesUtilServer.sendGuiError((Player)this.player, 0);
            return;
        }
        PlayerMail mail = new PlayerMail();
        Object s = this.player.m_5446_().getString();
        if (!((String)s).equals(this.player.m_7755_().getString())) {
            s = (String)s + "(" + this.player.m_7755_().getString() + ")";
        }
        mail.readNBT(this.data);
        mail.sender = s;
        if (mail.subject.isEmpty()) {
            NoppesUtilServer.sendGuiError((Player)this.player, 1);
            return;
        }
        mail.items = ((ContainerMail)this.player.f_36096_).mail.items;
        CompoundTag comp = new CompoundTag();
        comp.m_128359_("username", username);
        NoppesUtilServer.sendGuiClose(this.player, 1, comp);
        this.player.m_6915_();
        EntityNPCInterface npc = NoppesUtilServer.getEditingNpc((Player)this.player);
        if (npc != null && EventHooks.onNPCRole(npc, new RoleEvent.MailmanEvent((Player)this.player, npc.wrappedNPC, mail))) {
            return;
        }
        PlayerDataController.instance.addPlayerMessage(this.player.m_20194_(), username, mail);
    }
}

