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
 *  net.minecraft.world.entity.player.Player
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerNPCTrader;
import noppes.npcs.roles.RoleTrader;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;

public class GuiNPCTrader
extends GuiContainerNPCInterface<ContainerNPCTrader> {
    private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/trader.png");
    private final ResourceLocation slot = new ResourceLocation("customnpcs", "textures/gui/slot.png");
    private RoleTrader role;
    private ContainerNPCTrader container;

    public GuiNPCTrader(ContainerNPCTrader container, Inventory inv, Component titleIn) {
        super(NoppesUtil.getLastNpc(), container, inv, titleIn);
        this.container = container;
        this.role = (RoleTrader)this.npc.role;
        this.f_97727_ = 224;
        this.f_97726_ = 223;
        this.title = "role.trader";
    }

    @Override
    protected void m_7286_(GuiGraphics graphics, float partialTicks, int x, int y) {
        super.m_280273_(graphics);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.resource);
        graphics.m_280218_(this.resource, this.guiLeft, this.guiTop, 0, 0, this.f_97726_, this.f_97727_);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.slot);
        for (int slot = 0; slot < 18; ++slot) {
            int i = this.guiLeft + slot % 3 * 72 + 10;
            int j = this.guiTop + slot / 3 * 21 + 6;
            ItemStack item = (ItemStack)this.role.inventoryCurrency.items.get(slot);
            ItemStack item2 = (ItemStack)this.role.inventoryCurrency.items.get(slot + 18);
            if (NoppesUtilServer.IsItemStackNull(item)) {
                item = item2;
                item2 = ItemStack.f_41583_;
            }
            if (NoppesUtilPlayer.compareItems(item, item2, false, false)) {
                item = item.m_41777_();
                item.m_41764_(item.m_41613_() + item2.m_41613_());
                item2 = ItemStack.f_41583_;
            }
            ItemStack sold = (ItemStack)this.role.inventorySold.items.get(slot);
            RenderSystem.setShader(GameRenderer::m_172817_);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.slot);
            graphics.m_280218_(this.slot, i + 42, j, 0, 0, 18, 18);
            if (NoppesUtilServer.IsItemStackNull(item) || NoppesUtilServer.IsItemStackNull(sold)) continue;
            if (!NoppesUtilServer.IsItemStackNull(item2)) {
                graphics.m_280480_(item2, i, j + 1);
                graphics.m_280370_(this.f_96547_, item2, i, j + 1);
            }
            graphics.m_280480_(item, i + 18, j + 1);
            graphics.m_280370_(this.f_96547_, item, i + 18, j + 1);
            graphics.m_280056_(this.f_96547_, "=", i + 36, j + 5, CustomNpcResourceListener.getDefaultTextColor(), false);
        }
    }

    @Override
    protected void m_280003_(GuiGraphics graphics, int x, int y) {
        for (int slot = 0; slot < 18; ++slot) {
            ItemStack sold;
            int i = slot % 3 * 72 + 10;
            int j = slot / 3 * 21 + 6;
            ItemStack item = (ItemStack)this.role.inventoryCurrency.items.get(slot);
            ItemStack item2 = (ItemStack)this.role.inventoryCurrency.items.get(slot + 18);
            if (NoppesUtilServer.IsItemStackNull(item)) {
                item = item2;
                item2 = ItemStack.f_41583_;
            }
            if (NoppesUtilPlayer.compareItems(item, item2, this.role.ignoreDamage, this.role.ignoreNBT)) {
                item = item.m_41777_();
                item.m_41764_(item.m_41613_() + item2.m_41613_());
                item2 = ItemStack.f_41583_;
            }
            if (NoppesUtilServer.IsItemStackNull(sold = (ItemStack)this.role.inventorySold.items.get(slot))) continue;
            if (this.m_6774_(i + 43, j + 1, 16, 16, x, y)) {
                if (!this.container.canBuy(item, item2, (Player)this.player)) {
                    graphics.m_280168_().m_252880_(0.0f, 0.0f, 300.0f);
                    if (!item.m_41619_() && !NoppesUtilPlayer.compareItems((Player)this.player, item, this.role.ignoreDamage, this.role.ignoreNBT)) {
                        graphics.m_280024_(i + 17, j, i + 35, j + 18, 0x70771010, 0x70771010);
                    }
                    if (!item2.m_41619_() && !NoppesUtilPlayer.compareItems((Player)this.player, item2, this.role.ignoreDamage, this.role.ignoreNBT)) {
                        graphics.m_280024_(i - 1, j, i + 17, j + 18, 0x70771010, 0x70771010);
                    }
                    title = I18n.m_118938_((String)"trader.insufficient", (Object[])new Object[0]);
                    graphics.m_280488_(this.f_96547_, title, (this.f_97726_ - this.f_96547_.m_92895_(title)) / 2, 131, 0xDD0000);
                    graphics.m_280168_().m_252880_(0.0f, 0.0f, -300.0f);
                } else {
                    title = I18n.m_118938_((String)"trader.sufficient", (Object[])new Object[0]);
                    graphics.m_280488_(this.f_96547_, title, (this.f_97726_ - this.f_96547_.m_92895_(title)) / 2, 131, 56576);
                }
            }
            if (this.m_6774_(i, j, 16, 16, x, y) && !NoppesUtilServer.IsItemStackNull(item2)) {
                graphics.m_280153_(this.f_96547_, item2, x - this.guiLeft, y - this.guiTop);
            }
            if (!this.m_6774_(i + 18, j, 16, 16, x, y)) continue;
            graphics.m_280153_(this.f_96547_, item, x - this.guiLeft, y - this.guiTop);
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop button) {
    }

    @Override
    public void save() {
    }
}

