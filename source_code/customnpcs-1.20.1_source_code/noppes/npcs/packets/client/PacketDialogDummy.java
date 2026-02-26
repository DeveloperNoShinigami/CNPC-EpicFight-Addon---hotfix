/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.entity.EntityDialogNpc;
import noppes.npcs.packets.client.PacketDialog;
import noppes.npcs.shared.common.PacketBasic;

public class PacketDialogDummy
extends PacketBasic {
    private final String name;
    private final CompoundTag data;

    public PacketDialogDummy(String name, CompoundTag data) {
        this.name = name;
        this.data = data;
    }

    public static void encode(PacketDialogDummy msg, FriendlyByteBuf buf) {
        buf.m_130070_(msg.name);
        buf.m_130079_(msg.data);
    }

    public static PacketDialogDummy decode(FriendlyByteBuf buf) {
        return new PacketDialogDummy(buf.m_130136_(Short.MAX_VALUE), buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        EntityDialogNpc npc = new EntityDialogNpc(this.player.m_9236_());
        npc.display.setName(I18n.m_118938_((String)this.name, (Object[])new Object[0]));
        EntityUtil.Copy((LivingEntity)this.player, (LivingEntity)npc);
        Dialog dialog = new Dialog(null);
        dialog.readNBT(this.data);
        PacketDialog.openDialog(dialog, npc, this.player);
    }
}

