/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui.player;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.player.tabs.InventoryTabFactions;
import noppes.npcs.client.gui.player.tabs.InventoryTabQuests;
import noppes.npcs.client.gui.player.tabs.InventoryTabVanilla;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerFactionData;
import noppes.npcs.shared.client.gui.components.GuiButtonNextPage;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiFaction
extends GuiNPCInterface {
    private ArrayList<Faction> playerFactions = new ArrayList();
    private PlayerFactionData data;
    private int page = 0;
    private int pages = 1;
    private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private ResourceLocation indicator;

    public GuiFaction() {
        this.imageWidth = 200;
        this.imageHeight = 195;
        this.drawDefaultBackground = false;
        this.title = "";
        this.indicator = this.getResource("standardbg.png");
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.data = PlayerData.get((Player)this.player).factionData;
        this.playerFactions = new ArrayList();
        for (int id : this.data.factionData.keySet()) {
            Faction faction = FactionController.instance.getFaction(id);
            if (faction == null || faction.hideFaction) continue;
            this.playerFactions.add(faction);
        }
        this.pages = (this.playerFactions.size() - 1) / 5;
        ++this.pages;
        this.page = 1;
        this.guiLeft = (this.f_96543_ - this.imageWidth) / 2;
        this.guiTop += 12;
        this.m_142416_((GuiEventListener)new InventoryTabVanilla().init(this));
        this.m_142416_((GuiEventListener)new InventoryTabFactions().init(this));
        this.m_142416_((GuiEventListener)new InventoryTabQuests().init(this));
        this.buttonNextPage = new GuiButtonNextPage((IGuiInterface)this, 1, this.guiLeft + this.imageWidth - 43, this.guiTop + 180, true, button -> {
            ++this.page;
            this.updateButtons();
        });
        this.addButton(this.buttonNextPage);
        this.buttonPreviousPage = new GuiButtonNextPage((IGuiInterface)this, 2, this.guiLeft + 20, this.guiTop + 180, false, button -> {
            --this.page;
            this.updateButtons();
        });
        this.addButton(this.buttonPreviousPage);
        this.updateButtons();
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.m_280273_(graphics);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.indicator);
        graphics.m_280218_(this.indicator, this.guiLeft, this.guiTop + 8, 0, 0, this.imageWidth, this.imageHeight);
        graphics.m_280218_(this.indicator, this.guiLeft + 4, this.guiTop + 8, 56, 0, 200, this.imageHeight);
        if (this.playerFactions.isEmpty()) {
            MutableComponent noFaction = Component.m_237115_((String)"faction.nostanding");
            Font font = Minecraft.m_91087_().f_91062_;
            graphics.m_280614_(font, (Component)noFaction, this.guiLeft + (this.imageWidth - font.m_92852_((FormattedText)noFaction)) / 2, this.guiTop + 80, CustomNpcResourceListener.getDefaultTextColor(), false);
        } else {
            this.renderScreen(graphics);
        }
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
    }

    private void renderScreen(GuiGraphics graphics) {
        int size = 5;
        if (this.playerFactions.size() % 5 != 0 && this.page == this.pages) {
            size = this.playerFactions.size() % 5;
        }
        for (int id = 0; id < size; ++id) {
            graphics.m_280656_(this.guiLeft + 2, this.guiLeft + this.imageWidth, this.guiTop + 14 + id * 30, -16777216 + CustomNpcResourceListener.getDefaultTextColor());
            Faction faction = this.playerFactions.get((this.page - 1) * 5 + id);
            MutableComponent name = Component.m_237115_((String)faction.name);
            int current = this.data.factionData.get(faction.id);
            String points = " : " + current;
            MutableComponent standing = Component.m_237115_((String)"faction.friendly");
            int color = 65280;
            if (current < faction.neutralPoints) {
                standing = Component.m_237115_((String)"faction.unfriendly");
                color = 0xFF0000;
                points = points + "/" + faction.neutralPoints;
            } else if (current < faction.friendlyPoints) {
                standing = Component.m_237115_((String)"faction.neutral");
                color = 0xF2FF00;
                points = points + "/" + faction.friendlyPoints;
            } else {
                points = points + "/-";
            }
            graphics.m_280430_(this.f_96547_, (Component)name, this.guiLeft + (this.imageWidth - this.f_96547_.m_92852_((FormattedText)name)) / 2, this.guiTop + 19 + id * 30, faction.color);
            graphics.m_280430_(this.f_96547_, (Component)standing, this.f_96543_ / 2 - this.f_96547_.m_92852_((FormattedText)standing) - 1, this.guiTop + 33 + id * 30, color);
            graphics.m_280488_(this.f_96547_, points, this.f_96543_ / 2, this.guiTop + 33 + id * 30, CustomNpcResourceListener.getDefaultTextColor());
        }
        graphics.m_280656_(this.guiLeft + 2, this.guiLeft + this.imageWidth, this.guiTop + 14 + size * 30, -16777216 + CustomNpcResourceListener.getDefaultTextColor());
        if (this.pages > 1) {
            String s = this.page + "/" + this.pages;
            graphics.m_280488_(this.f_96547_, s, this.guiLeft + (this.imageWidth - this.f_96547_.m_92895_(s)) / 2, this.guiTop + 203, CustomNpcResourceListener.getDefaultTextColor());
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        if (!(guibutton instanceof GuiButtonNextPage)) {
            return;
        }
        int id = guibutton.id;
        if (id == 1) {
            ++this.page;
        }
        if (id == 2) {
            --this.page;
        }
        this.updateButtons();
    }

    private void updateButtons() {
        this.buttonNextPage.f_93624_ = this.page < this.pages;
        this.buttonPreviousPage.f_93624_ = this.page > 1;
    }

    @Override
    public void save() {
    }
}

