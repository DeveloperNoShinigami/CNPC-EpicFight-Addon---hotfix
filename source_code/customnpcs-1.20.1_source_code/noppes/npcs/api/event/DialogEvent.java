/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.eventbus.api.Cancelable
 */
package noppes.npcs.api.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.api.handler.data.IDialog;
import noppes.npcs.api.handler.data.IDialogOption;

public class DialogEvent
extends NpcEvent {
    public final IDialog dialog;
    public final IPlayer player;

    public DialogEvent(ICustomNpc npc, Player player, IDialog dialog) {
        super(npc);
        this.dialog = dialog;
        this.player = (IPlayer)NpcAPI.Instance().getIEntity((Entity)player);
    }

    @Cancelable
    public static class OptionEvent
    extends DialogEvent {
        public final IDialogOption option;

        public OptionEvent(ICustomNpc npc, Player player, IDialog dialog, IDialogOption option) {
            super(npc, player, dialog);
            this.option = option;
        }
    }

    public static class CloseEvent
    extends DialogEvent {
        public CloseEvent(ICustomNpc npc, Player player, IDialog dialog) {
            super(npc, player, dialog);
        }
    }

    @Cancelable
    public static class OpenEvent
    extends DialogEvent {
        public OpenEvent(ICustomNpc npc, Player player, IDialog dialog) {
            super(npc, player, dialog);
        }
    }
}

