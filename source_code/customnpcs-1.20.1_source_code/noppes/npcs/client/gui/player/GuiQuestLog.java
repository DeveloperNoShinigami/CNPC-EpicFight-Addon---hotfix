/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui.player;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.api.handler.data.IQuestObjective;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.gui.player.tabs.InventoryTabFactions;
import noppes.npcs.client.gui.player.tabs.InventoryTabQuests;
import noppes.npcs.client.gui.player.tabs.InventoryTabVanilla;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.shared.client.gui.components.GuiButtonNextPage;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiMenuSideButton;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITopButtonListener;
import noppes.npcs.shared.common.util.NaturalOrderComparator;

public class GuiQuestLog
extends GuiNPCInterface
implements ITopButtonListener,
ICustomScrollListener {
    private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/standardbg.png");
    public HashMap<String, List<Quest>> activeQuests = new HashMap();
    private HashMap<String, Quest> categoryQuests = new HashMap();
    public Quest selectedQuest = null;
    public Component selectedCategory = Component.m_237119_();
    private Player player;
    private GuiCustomScrollNop scroll;
    private HashMap<Integer, GuiMenuSideButton> sideButtons = new HashMap();
    private boolean noQuests = false;
    private final int maxLines = 10;
    private int currentPage = 0;
    private int maxPages = 1;
    TextBlockClient textblock = null;
    private Minecraft mc = Minecraft.m_91087_();

    public GuiQuestLog(Player player) {
        this.player = player;
        this.imageWidth = 280;
        this.imageHeight = 180;
        this.drawDefaultBackground = false;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        for (Quest quest : PlayerQuestController.getActiveQuests(this.player)) {
            String category = quest.category.title;
            if (!this.activeQuests.containsKey(category)) {
                this.activeQuests.put(category, new ArrayList());
            }
            List<Quest> list = this.activeQuests.get(category);
            list.add(quest);
        }
        this.sideButtons.clear();
        this.guiTop += 10;
        this.m_142416_((GuiEventListener)new InventoryTabVanilla().init(this));
        this.m_142416_((GuiEventListener)new InventoryTabFactions().init(this));
        this.m_142416_((GuiEventListener)new InventoryTabQuests().init(this));
        this.noQuests = false;
        if (this.activeQuests.isEmpty()) {
            this.noQuests = true;
            return;
        }
        ArrayList<String> categories = new ArrayList<String>();
        categories.addAll(this.activeQuests.keySet());
        Collections.sort(categories, new NaturalOrderComparator());
        int i = 0;
        for (String category : categories) {
            if (Objects.equals(this.selectedCategory, Component.m_237119_())) {
                this.selectedCategory = Component.m_237115_((String)category);
            }
            this.sideButtons.put(i, new GuiMenuSideButton(this, i, this.guiLeft - 69, this.guiTop + 2 + i * 21, 70, 22, category));
            ++i;
        }
        this.sideButtons.get((Object)Integer.valueOf((int)categories.indexOf((Object)this.selectedCategory.getString()))).active = true;
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
        }
        HashMap<String, Quest> categoryQuests = new HashMap<String, Quest>();
        for (Quest q : this.activeQuests.get(this.selectedCategory.getString())) {
            categoryQuests.put(q.title, q);
        }
        this.categoryQuests = categoryQuests;
        this.scroll.setList(new ArrayList<String>(categoryQuests.keySet()));
        this.scroll.setSize(134, 174);
        this.scroll.guiLeft = this.guiLeft + 5;
        this.scroll.guiTop = this.guiTop + 15;
        this.addScroll(this.scroll);
        this.addButton(new GuiButtonNextPage((IGuiInterface)this, 1, this.guiLeft + 286, this.guiTop + 114, true, b -> {
            ++this.currentPage;
            this.m_7856_();
        }));
        this.addButton(new GuiButtonNextPage((IGuiInterface)this, 2, this.guiLeft + 144, this.guiTop + 114, false, b -> {
            --this.currentPage;
            this.m_7856_();
        }));
        this.getButton((int)1).f_93624_ = this.selectedQuest != null && this.currentPage < this.maxPages - 1;
        this.getButton((int)2).f_93624_ = this.selectedQuest != null && this.currentPage > 0;
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.scroll != null) {
            this.scroll.visible = !this.noQuests;
        }
        PoseStack matrixStack = graphics.m_280168_();
        this.m_280273_(graphics);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.resource);
        graphics.m_280218_(this.resource, this.guiLeft, this.guiTop, 0, 0, 252, 195);
        graphics.m_280218_(this.resource, this.guiLeft + 252, this.guiTop, 188, 0, 67, 195);
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        if (this.noQuests) {
            graphics.m_280614_(this.mc.f_91062_, (Component)Component.m_237115_((String)"quest.noquests"), this.guiLeft + 84, this.guiTop + 80, CustomNpcResourceListener.getDefaultTextColor(), false);
            return;
        }
        for (GuiMenuSideButton button : this.sideButtons.values().toArray(new GuiMenuSideButton[this.sideButtons.size()])) {
            button.m_88315_(graphics, mouseX, mouseY, partialTicks);
        }
        graphics.m_280614_(this.mc.f_91062_, this.selectedCategory, this.guiLeft + 5, this.guiTop + 5, CustomNpcResourceListener.getDefaultTextColor(), false);
        if (this.selectedQuest == null) {
            return;
        }
        this.drawProgress(graphics);
        this.drawQuestText(graphics);
        matrixStack.m_85836_();
        matrixStack.m_252880_((float)(this.guiLeft + 148), (float)this.guiTop, 0.0f);
        matrixStack.m_85841_(1.24f, 1.24f, 1.24f);
        MutableComponent title = Component.m_237115_((String)this.selectedQuest.title);
        graphics.m_280614_(this.mc.f_91062_, (Component)title, (130 - this.f_96547_.m_92852_((FormattedText)title)) / 2, 4, CustomNpcResourceListener.getDefaultTextColor(), false);
        matrixStack.m_85849_();
        graphics.m_280656_(this.guiLeft + 142, this.guiLeft + 312, this.guiTop + 17, -16777216 + CustomNpcResourceListener.getDefaultTextColor());
    }

    private void drawQuestText(GuiGraphics graphics) {
        if (this.textblock == null) {
            return;
        }
        int yoffset = this.guiTop + 5;
        for (int i = 0; i < 10; ++i) {
            int index = i + this.currentPage * 10;
            if (index >= this.textblock.lines.size()) continue;
            Component text = (Component)this.textblock.lines.get(index);
            Objects.requireNonNull(this.f_96547_);
            graphics.m_280614_(this.f_96547_, text, this.guiLeft + 142, this.guiTop + 20 + i * 9, CustomNpcResourceListener.getDefaultTextColor(), false);
        }
    }

    private void drawProgress(GuiGraphics graphics) {
        MutableComponent title = Component.m_237115_((String)"quest.objectives").m_130946_(":");
        graphics.m_280614_(this.mc.f_91062_, (Component)title, this.guiLeft + 142, this.guiTop + 130, CustomNpcResourceListener.getDefaultTextColor(), false);
        graphics.m_280656_(this.guiLeft + 142, this.guiLeft + 312, this.guiTop + 140, -16777216 + CustomNpcResourceListener.getDefaultTextColor());
        int yoffset = this.guiTop + 144;
        for (IQuestObjective objective : this.selectedQuest.questInterface.getObjectives(this.player)) {
            graphics.m_280614_(this.mc.f_91062_, (Component)Component.m_237113_((String)"- ").m_7220_(objective.getMCText()), this.guiLeft + 142, yoffset, CustomNpcResourceListener.getDefaultTextColor(), false);
            yoffset += 10;
        }
        graphics.m_280656_(this.guiLeft + 142, this.guiLeft + 312, this.guiTop + 178, -16777216 + CustomNpcResourceListener.getDefaultTextColor());
        String complete = this.selectedQuest.getNpcName();
        if (complete != null && !complete.isEmpty()) {
            graphics.m_280614_(this.mc.f_91062_, (Component)Component.m_237110_((String)"quest.completewith", (Object[])new Object[]{Component.m_237115_((String)complete)}), this.guiLeft + 142, this.guiTop + 182, CustomNpcResourceListener.getDefaultTextColor(), false);
        }
    }

    @Override
    public boolean m_6375_(double i, double j, int k) {
        super.m_6375_(i, j, k);
        if (k == 0) {
            if (this.scroll != null) {
                this.scroll.m_6375_(i, j, k);
            }
            for (GuiMenuSideButton button : new ArrayList<GuiMenuSideButton>(this.sideButtons.values())) {
                if (!button.m_6375_(i, j, k)) continue;
                this.sideButtonPressed(button);
                return true;
            }
        }
        return false;
    }

    private void sideButtonPressed(GuiMenuSideButton button) {
        if (button.active) {
            return;
        }
        NoppesUtil.clickSound();
        this.selectedCategory = button.m_6035_();
        this.selectedQuest = null;
        this.m_7856_();
    }

    @Override
    public void scrollClicked(double i, double j, int k, GuiCustomScrollNop scroll) {
        if (!scroll.hasSelected()) {
            return;
        }
        this.selectedQuest = this.categoryQuests.get(scroll.getSelected());
        this.textblock = new TextBlockClient(this.selectedQuest.getLogText(), 172, true, this.player);
        if (this.textblock.lines.size() > 10) {
            this.maxPages = Mth.m_14167_((float)(1.0f * (float)this.textblock.lines.size() / 10.0f));
        }
        this.currentPage = 0;
        this.m_7856_();
    }

    @Override
    public boolean m_7043_() {
        return false;
    }

    @Override
    public void save() {
    }

    @Override
    public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
    }
}

