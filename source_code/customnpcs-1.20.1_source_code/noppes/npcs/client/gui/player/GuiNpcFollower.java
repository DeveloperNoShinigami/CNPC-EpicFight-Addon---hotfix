/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.client.gui.player;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerNPCFollower;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketFollowerExtend;
import noppes.npcs.packets.server.SPacketFollowerState;
import noppes.npcs.packets.server.SPacketNpcRoleGet;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.listeners.IGuiData;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiNpcFollower
extends GuiContainerNPCInterface<ContainerNPCFollower>
implements IGuiData {
    private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/follower.png");
    private RoleFollower role;

    public GuiNpcFollower(ContainerNPCFollower container, Inventory inv, Component titleIn) {
        super(NoppesUtil.getLastNpc(), container, inv, titleIn);
        this.role = (RoleFollower)this.npc.role;
        Packets.sendServer(new SPacketNpcRoleGet());
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.f_169369_.clear();
        this.addButton(new GuiButtonNop((IGuiInterface)this, 4, this.guiLeft + 100, this.guiTop + 110, 50, 20, new String[]{I18n.m_118938_((String)"follower.waiting", (Object[])new Object[0]), I18n.m_118938_((String)"follower.following", (Object[])new Object[0])}, this.role.isFollowing ? 1 : 0));
        if (!this.role.infiniteDays) {
            this.addButton(new GuiButtonNop(this, 5, this.guiLeft + 8, this.guiTop + 30, 50, 20, I18n.m_118938_((String)"follower.hire", (Object[])new Object[0])));
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 4) {
            Packets.sendServer(new SPacketFollowerState());
        }
        if (id == 5) {
            Packets.sendServer(new SPacketFollowerExtend());
        }
    }

    @Override
    protected void m_280003_(GuiGraphics graphics, int x, int y) {
        graphics.m_280488_(this.f_96547_, I18n.m_118938_((String)"follower.health", (Object[])new Object[0]) + ": " + this.npc.m_21223_() + "/" + this.npc.m_21233_(), 62, 70, CustomNpcResourceListener.getDefaultTextColor());
        if (!this.role.infiniteDays) {
            if (this.role.getDays() <= 1) {
                graphics.m_280488_(this.f_96547_, I18n.m_118938_((String)"follower.daysleft", (Object[])new Object[0]) + ": " + I18n.m_118938_((String)"follower.lastday", (Object[])new Object[0]), 62, 94, CustomNpcResourceListener.getDefaultTextColor());
            } else {
                graphics.m_280488_(this.f_96547_, I18n.m_118938_((String)"follower.daysleft", (Object[])new Object[0]) + ": " + (this.role.getDays() - 1), 62, 94, CustomNpcResourceListener.getDefaultTextColor());
            }
        }
    }

    @Override
    protected void m_7286_(GuiGraphics graphics, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.resource);
        int l = this.guiLeft;
        int i1 = this.guiTop;
        graphics.m_280218_(this.resource, l, i1, 0, 0, this.f_97726_, this.f_97727_);
        int index = 0;
        if (!this.role.infiniteDays) {
            for (int slot = 0; slot < this.role.inventory.items.size(); ++slot) {
                ItemStack itemstack = (ItemStack)this.role.inventory.items.get(slot);
                if (NoppesUtilServer.IsItemStackNull(itemstack)) continue;
                int days = 1;
                if (this.role.rates.containsKey(slot)) {
                    days = this.role.rates.get(slot);
                }
                int yOffset = index * 20;
                int i = this.guiLeft + 68;
                int j = this.guiTop + yOffset + 4;
                graphics.m_280480_(itemstack, x + 11, y);
                graphics.m_280370_(this.f_96547_, itemstack, x + 11, y);
                String daysS = days + " " + (days == 1 ? I18n.m_118938_((String)"follower.day", (Object[])new Object[0]) : I18n.m_118938_((String)"follower.days", (Object[])new Object[0]));
                graphics.m_280488_(this.f_96547_, " = " + daysS, i + 27, j + 4, CustomNpcResourceListener.getDefaultTextColor());
                if (this.m_6774_(i - this.guiLeft + 11, j - this.guiTop, 16, 16, this.mouseX, this.mouseY)) {
                    graphics.m_280153_(this.f_96547_, itemstack, this.mouseX, this.mouseY);
                }
                ++index;
            }
        }
        this.drawNpc(graphics, 33, 131);
    }

    @Override
    public void save() {
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.npc.role.load(compound);
        this.m_7856_();
    }
}

