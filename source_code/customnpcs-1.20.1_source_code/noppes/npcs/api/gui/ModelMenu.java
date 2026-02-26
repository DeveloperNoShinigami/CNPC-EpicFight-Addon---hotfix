/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.api.gui;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.gui.MainMenuGui;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiParts;

public class ModelMenu
extends MainMenuGui {
    public ModelMenu(EntityCustomNpc npc, IPlayer player) {
        super(1, npc, player, false);
        this.gui.getScrollingPanel().init(180, 26, 230, this.gui.getHeight() - 32);
    }

    public static void open(Player player, EntityCustomNpc npc) {
        IPlayer p = (IPlayer)NpcAPI.Instance().getIEntity((Entity)player);
        CustomGuiWrapper menu = new ModelMenu((EntityCustomNpc)npc, (IPlayer)p).gui;
        p.showCustomGui(menu);
        Packets.send(p.getMCEntity(), new PacketGuiParts(npc.m_19879_(), menu.toNBT()));
    }
}

