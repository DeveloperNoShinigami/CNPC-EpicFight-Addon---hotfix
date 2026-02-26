/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui.global;

import com.google.common.collect.Lists;
import java.util.HashMap;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.SubGuiEditText;
import noppes.npcs.client.gui.global.GuiQuestEdit;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestCategory;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketQuestCategoryRemove;
import noppes.npcs.packets.server.SPacketQuestCategorySave;
import noppes.npcs.packets.server.SPacketQuestRemove;
import noppes.npcs.packets.server.SPacketQuestSave;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiNPCManageQuest
extends GuiNPCInterface2
implements ICustomScrollListener {
    private HashMap<String, QuestCategory> categoryData = new HashMap();
    private HashMap<String, Quest> questData = new HashMap();
    private GuiCustomScrollNop scrollCategories;
    private GuiCustomScrollNop scrollQuests;
    public static Screen Instance;
    private QuestCategory selectedCategory;
    private Quest selectedQuest;

    public GuiNPCManageQuest(EntityNPCInterface npc) {
        super(npc);
        Instance = this;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(0, "gui.categories", this.guiLeft + 8, this.guiTop + 4));
        this.addLabel(new GuiLabel(1, "quest.quests", this.guiLeft + 175, this.guiTop + 4));
        this.addLabel(new GuiLabel(3, "quest.quests", this.guiLeft + 356, this.guiTop + 8));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 13, this.guiLeft + 356, this.guiTop + 18, 58, 20, "selectServer.edit", this.selectedQuest != null));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 12, this.guiLeft + 356, this.guiTop + 41, 58, 20, "gui.remove", this.selectedQuest != null));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 11, this.guiLeft + 356, this.guiTop + 64, 58, 20, "gui.add", this.selectedCategory != null));
        this.addLabel(new GuiLabel(2, "gui.categories", this.guiLeft + 356, this.guiTop + 110));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 3, this.guiLeft + 356, this.guiTop + 120, 58, 20, "selectServer.edit", this.selectedCategory != null));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 2, this.guiLeft + 356, this.guiTop + 143, 58, 20, "gui.remove", this.selectedCategory != null));
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 356, this.guiTop + 166, 58, 20, "gui.add"));
        HashMap<String, QuestCategory> categoryData = new HashMap<String, QuestCategory>();
        HashMap<String, Quest> questData = new HashMap<String, Quest>();
        for (QuestCategory category : QuestController.instance.categories.values()) {
            categoryData.put(category.title, category);
        }
        this.categoryData = categoryData;
        if (this.selectedCategory != null) {
            for (Quest quest : this.selectedCategory.quests.values()) {
                questData.put(quest.title, quest);
            }
        }
        this.questData = questData;
        if (this.scrollCategories == null) {
            this.scrollCategories = new GuiCustomScrollNop(this, 0);
            this.scrollCategories.setSize(170, 200);
        }
        this.scrollCategories.setList(Lists.newArrayList(categoryData.keySet()));
        this.scrollCategories.guiLeft = this.guiLeft + 4;
        this.scrollCategories.guiTop = this.guiTop + 14;
        this.addScroll(this.scrollCategories);
        if (this.scrollQuests == null) {
            this.scrollQuests = new GuiCustomScrollNop(this, 1);
            this.scrollQuests.setSize(170, 200);
        }
        this.scrollQuests.setList(Lists.newArrayList(questData.keySet()));
        this.scrollQuests.guiLeft = this.guiLeft + 175;
        this.scrollQuests.guiTop = this.guiTop + 14;
        this.addScroll(this.scrollQuests);
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        ConfirmScreen guiyesno;
        GuiButtonNop button = guibutton;
        if (button.id == 1) {
            this.setSubGui(new SubGuiEditText(1, I18n.m_118938_((String)"gui.new", (Object[])new Object[0])));
        }
        if (button.id == 2) {
            guiyesno = new ConfirmScreen(bo -> {
                if (bo) {
                    Packets.sendServer(new SPacketQuestCategoryRemove(this.selectedCategory.id));
                }
                NoppesUtil.openGUI((Player)this.player, this);
            }, (Component)Component.m_237115_((String)this.selectedCategory.title), (Component)Component.m_237115_((String)"gui.deleteMessage"));
            this.setScreen((Screen)guiyesno);
        }
        if (button.id == 3) {
            this.setSubGui(new SubGuiEditText(3, this.selectedCategory.title));
        }
        if (button.id == 11) {
            this.setSubGui(new SubGuiEditText(11, I18n.m_118938_((String)"gui.new", (Object[])new Object[0])));
        }
        if (button.id == 12) {
            guiyesno = new ConfirmScreen(bo -> {
                if (bo) {
                    Packets.sendServer(new SPacketQuestRemove(this.selectedQuest.id));
                }
                NoppesUtil.openGUI((Player)this.player, this);
            }, (Component)Component.m_237115_((String)this.selectedQuest.title), (Component)Component.m_237115_((String)"gui.deleteMessage"));
            this.setScreen((Screen)guiyesno);
        }
        if (button.id == 13) {
            this.setSubGui(new GuiQuestEdit(this.selectedQuest));
        }
    }

    @Override
    public void subGuiClosed(Screen subgui) {
        if (subgui instanceof SubGuiEditText && ((SubGuiEditText)subgui).cancelled) {
            return;
        }
        if (subgui instanceof SubGuiEditText) {
            SubGuiEditText editText = (SubGuiEditText)subgui;
            if (editText.id == 1) {
                QuestCategory category = new QuestCategory();
                category.title = editText.text;
                while (QuestController.instance.containsCategoryName(category)) {
                    category.title = category.title + "_";
                }
                Packets.sendServer(new SPacketQuestCategorySave(category.writeNBT(new CompoundTag())));
            }
            if (editText.id == 3) {
                this.selectedCategory.title = editText.text;
                while (QuestController.instance.containsCategoryName(this.selectedCategory)) {
                    this.selectedCategory.title = this.selectedCategory.title + "_";
                }
                Packets.sendServer(new SPacketQuestCategorySave(this.selectedCategory.writeNBT(new CompoundTag())));
            }
            if (editText.id == 11) {
                Quest quest = new Quest(this.selectedCategory);
                quest.title = editText.text;
                while (QuestController.instance.containsQuestName(this.selectedCategory, quest)) {
                    quest.title = quest.title + "_";
                }
                Packets.sendServer(new SPacketQuestSave(this.selectedCategory.id, quest.save(new CompoundTag())));
            }
        }
        if (subgui instanceof GuiQuestEdit) {
            this.m_7856_();
        }
    }

    @Override
    public void scrollClicked(double i, double j, int k, GuiCustomScrollNop guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.selectedCategory = this.categoryData.get(this.scrollCategories.getSelected());
            this.selectedQuest = null;
            this.scrollQuests.clearSelection();
        }
        if (guiCustomScroll.id == 1) {
            this.selectedQuest = this.questData.get(this.scrollQuests.getSelected());
        }
        this.m_7856_();
    }

    @Override
    public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
        if (this.selectedQuest != null && scroll.id == 1) {
            this.setSubGui(new GuiQuestEdit(this.selectedQuest));
        }
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void save() {
        GuiTextFieldNop.unfocus();
    }
}

