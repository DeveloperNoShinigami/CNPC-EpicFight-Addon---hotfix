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
import noppes.npcs.containers.ContainerNPCBankInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketBankUnlock;
import noppes.npcs.packets.server.SPacketBankUpgrade;
import noppes.npcs.packets.server.SPacketBanksSlotOpen;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.listeners.IGuiData;

public class GuiNPCBankChest
extends GuiContainerNPCInterface<ContainerNPCBankInterface>
implements IGuiData {
    private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/bankchest.png");
    private ContainerNPCBankInterface container;
    private int availableSlots = 0;
    private int maxSlots = 1;
    private int unlockedSlots = 1;
    private ItemStack currency;

    public GuiNPCBankChest(ContainerNPCBankInterface container, Inventory inv, Component titleIn) {
        super(NoppesUtil.getLastNpc(), container, inv, titleIn);
        this.container = container;
        this.title = "";
        this.f_97727_ = 235;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.availableSlots = 0;
        if (this.maxSlots > 1) {
            for (int i = 0; i < this.maxSlots; ++i) {
                GuiButtonNop button = new GuiButtonNop(this, i, this.guiLeft - 50, this.guiTop + 10 + i * 24, 50, 20, I18n.m_118938_((String)"gui.tab", (Object[])new Object[0]) + " " + (i + 1));
                if (i > this.unlockedSlots) {
                    button.setEnabled(false);
                }
                this.addButton(button);
                ++this.availableSlots;
            }
            if (this.availableSlots == 1) {
                this.f_169369_.clear();
            }
        }
        if (!this.container.isAvailable()) {
            this.addButton(new GuiButtonNop(this, 8, this.guiLeft + 48, this.guiTop + 48, 80, 20, I18n.m_118938_((String)"bank.unlock", (Object[])new Object[0])));
        } else if (this.container.canBeUpgraded()) {
            this.addButton(new GuiButtonNop(this, 9, this.guiLeft + 48, this.guiTop + 48, 80, 20, I18n.m_118938_((String)"bank.upgrade", (Object[])new Object[0])));
        }
        if (this.maxSlots > 1) {
            this.getButton((int)this.container.slot).f_93624_ = false;
            this.getButton(this.container.slot).setEnabled(false);
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id < 6) {
            Packets.sendServer(new SPacketBanksSlotOpen(id, this.container.bankid));
        }
        if (id == 8) {
            Packets.sendServer(new SPacketBankUnlock());
        }
        if (id == 9) {
            Packets.sendServer(new SPacketBankUpgrade());
        }
    }

    @Override
    protected void m_7286_(GuiGraphics graphics, float partialTicks, int x, int y) {
        super.m_7286_(graphics, partialTicks, x, y);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.resource);
        int l = (this.f_96543_ - this.f_97726_) / 2;
        int i1 = (this.f_96544_ - this.f_97727_) / 2;
        graphics.m_280218_(this.resource, l, i1, 0, 0, this.f_97726_, 6);
        if (!this.container.isAvailable()) {
            graphics.m_280218_(this.resource, l, i1 + 6, 0, 6, this.f_97726_, 64);
            graphics.m_280218_(this.resource, l, i1 + 70, 0, 124, this.f_97726_, 98);
            i = this.guiLeft + 30;
            j = this.guiTop + 8;
            graphics.m_280488_(this.f_96547_, I18n.m_118938_((String)"bank.unlockCosts", (Object[])new Object[0]) + ":", i, j + 4, CustomNpcResourceListener.getDefaultTextColor());
            this.drawItem(graphics, i + 90, j, this.currency, x, y);
        } else if (this.container.isUpgraded()) {
            graphics.m_280218_(this.resource, l, i1 + 60, 0, 60, this.f_97726_, 162);
            graphics.m_280218_(this.resource, l, i1 + 6, 0, 60, this.f_97726_, 64);
        } else if (this.container.canBeUpgraded()) {
            graphics.m_280218_(this.resource, l, i1 + 6, 0, 6, this.f_97726_, 216);
            i = this.guiLeft + 30;
            j = this.guiTop + 8;
            graphics.m_280488_(this.f_96547_, I18n.m_118938_((String)"bank.upgradeCosts", (Object[])new Object[0]) + ":", i, j + 4, CustomNpcResourceListener.getDefaultTextColor());
            this.drawItem(graphics, i + 90, j, this.currency, x, y);
        } else {
            graphics.m_280218_(this.resource, l, i1 + 6, 0, 60, this.f_97726_, 162);
        }
        if (this.maxSlots > 1) {
            for (int ii = 0; ii < this.maxSlots && this.availableSlots != ii; ++ii) {
                if (ii != this.container.slot) continue;
                graphics.m_280488_(this.f_96547_, "Tab " + (ii + 1), this.guiLeft - 40, this.guiTop + 16 + ii * 24, 0xFFFFFF);
            }
        }
    }

    private void drawItem(GuiGraphics graphics, int x, int y, ItemStack item, int mouseX, int mouseY) {
        if (NoppesUtilServer.IsItemStackNull(item)) {
            return;
        }
        graphics.m_280480_(item, x, y);
        graphics.m_280370_(this.f_96547_, item, x, y);
        if (this.m_6774_(x - this.guiLeft, y - this.guiTop, 16, 16, mouseX, mouseY)) {
            graphics.m_280153_(this.f_96547_, item, mouseX, mouseY);
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.maxSlots = compound.m_128451_("MaxSlots");
        this.unlockedSlots = compound.m_128451_("UnlockedSlots");
        this.currency = compound.m_128441_("Currency") ? ItemStack.m_41712_((CompoundTag)compound.m_128469_("Currency")) : ItemStack.f_41583_;
        if (this.container.currency != null) {
            this.container.currency.item = this.currency;
        }
        this.m_7856_();
    }
}

