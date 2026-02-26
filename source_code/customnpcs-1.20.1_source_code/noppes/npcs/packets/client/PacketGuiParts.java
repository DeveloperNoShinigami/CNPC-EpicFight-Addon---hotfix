/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtAccounter
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.gui.custom.GuiCreationNewParts;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.shared.common.PacketBasic;

public class PacketGuiParts
extends PacketBasic {
    private final int id;
    private final CompoundTag data;

    public PacketGuiParts(int id, CompoundTag data) {
        this.id = id;
        this.data = data;
    }

    public static void encode(PacketGuiParts msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        buf.m_130079_(msg.data);
    }

    public static PacketGuiParts decode(FriendlyByteBuf buf) {
        return new PacketGuiParts(buf.readInt(), buf.m_130081_(new NbtAccounter(Long.MAX_VALUE)));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Entity entity = this.player.m_9236_().m_6815_(this.id);
        Screen screen = Minecraft.m_91087_().f_91080_;
        if (screen instanceof GuiCustom) {
            GuiCustom gui = (GuiCustom)screen;
            if (entity instanceof EntityCustomNpc) {
                EntityCustomNpc npc = (EntityCustomNpc)entity;
                GuiCreationNewParts parts = new GuiCreationNewParts(gui, npc);
                gui.initCallback = () -> {
                    gui.add(parts);
                    parts.m_7856_();
                };
                gui.setGuiData(this.data);
            }
        }
    }
}

