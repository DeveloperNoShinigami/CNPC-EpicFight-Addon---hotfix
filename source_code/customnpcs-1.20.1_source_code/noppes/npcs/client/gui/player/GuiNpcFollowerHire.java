/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.resources.language.I18n
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerNPCFollowerHire;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketFollowerHire;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;

public class GuiNpcFollowerHire
extends GuiContainerNPCInterface<ContainerNPCFollowerHire> {
    private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/followerhire.png");
    private ContainerNPCFollowerHire container;
    private RoleFollower role;

    public GuiNpcFollowerHire(ContainerNPCFollowerHire container, Inventory inv, Component titleIn) {
        super(NoppesUtil.getLastNpc(), container, inv, titleIn);
        this.container = container;
        this.role = (RoleFollower)this.npc.role;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addButton(new GuiButtonNop(this, 5, this.guiLeft + 26, this.guiTop + 60, 50, 20, I18n.m_118938_((String)"follower.hire", (Object[])new Object[0])));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        if (guibutton.id == 5) {
            Packets.sendServer(new SPacketFollowerHire());
            this.close();
        }
    }

    @Override
    protected void m_280003_(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        super.m_280003_(p_281635_, p_282681_, p_283686_);
    }

    @Override
    protected void m_7286_(GuiGraphics graphics, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.resource);
        int l = (this.f_96543_ - this.f_97726_) / 2;
        int i1 = (this.f_96544_ - this.f_97727_) / 2;
        graphics.m_280218_(this.resource, l, i1, 0, 0, this.f_97726_, this.f_97727_);
        int index = 0;
        for (int slot = 0; slot < this.role.inventory.items.size(); ++slot) {
            ItemStack itemstack = (ItemStack)this.role.inventory.items.get(slot);
            if (NoppesUtilServer.IsItemStackNull(itemstack)) continue;
            int days = 1;
            if (this.role.rates.containsKey(slot)) {
                days = this.role.rates.get(slot);
            }
            int yOffset = index * 26;
            int x = this.guiLeft + 78;
            int y = this.guiTop + yOffset + 10;
            graphics.m_280480_(itemstack, x + 11, y);
            graphics.m_280370_(this.f_96547_, itemstack, x + 11, y);
            String daysS = days + " " + (days == 1 ? I18n.m_118938_((String)"follower.day", (Object[])new Object[0]) : I18n.m_118938_((String)"follower.days", (Object[])new Object[0]));
            graphics.m_280488_(this.f_96547_, " = " + daysS, x + 27, y + 4, CustomNpcResourceListener.getDefaultTextColor());
            if (this.m_6774_(x - this.guiLeft + 11, y - this.guiTop, 16, 16, this.mouseX, this.mouseY)) {
                graphics.m_280153_(this.f_96547_, itemstack, this.mouseX, this.mouseY);
            }
            ++index;
        }
    }

    @Override
    public void save() {
    }
}

