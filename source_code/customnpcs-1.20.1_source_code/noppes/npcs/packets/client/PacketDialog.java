/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.CustomNpcs;
import noppes.npcs.client.gui.player.GuiDialogInteract;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.common.PacketBasic;

public class PacketDialog
extends PacketBasic {
    private final int entityId;
    private final int dialogId;

    public PacketDialog(int entityId, int dialogId) {
        this.entityId = entityId;
        this.dialogId = dialogId;
    }

    public static void encode(PacketDialog msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.dialogId);
    }

    public static PacketDialog decode(FriendlyByteBuf buf) {
        return new PacketDialog(buf.readInt(), buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(this.entityId);
        if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
        }
        Dialog dialog = DialogController.instance.dialogs.get(this.dialogId);
        PacketDialog.openDialog(dialog, (EntityNPCInterface)entity, this.player);
    }

    public static void openDialog(Dialog dialog, EntityNPCInterface npc, Player player) {
        Screen gui = Minecraft.m_91087_().f_91080_;
        if (gui == null || !(gui instanceof GuiDialogInteract)) {
            CustomNpcs.proxy.openGui(player, new GuiDialogInteract(npc, dialog));
        } else {
            GuiDialogInteract dia = (GuiDialogInteract)gui;
            dia.appendDialog(dialog);
        }
    }
}

