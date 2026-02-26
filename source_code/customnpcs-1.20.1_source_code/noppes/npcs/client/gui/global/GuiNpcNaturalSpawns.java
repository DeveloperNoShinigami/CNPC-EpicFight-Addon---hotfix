/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import noppes.npcs.client.gui.GuiNpcMobSpawnerSelector;
import noppes.npcs.client.gui.SubGuiNpcBiomes;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.controllers.data.SpawnData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketNaturalSpawnGet;
import noppes.npcs.packets.server.SPacketNaturalSpawnGetAll;
import noppes.npcs.packets.server.SPacketNaturalSpawnRemove;
import noppes.npcs.packets.server.SPacketNaturalSpawnSave;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiSliderNop;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.client.gui.listeners.IGuiData;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.IScrollData;
import noppes.npcs.shared.client.gui.listeners.ISliderListener;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiNpcNaturalSpawns
extends GuiNPCInterface2
implements IGuiData,
IScrollData,
ITextfieldListener,
ICustomScrollListener,
ISliderListener {
    private GuiCustomScrollNop scroll;
    private Map<String, Integer> data = new HashMap<String, Integer>();
    private SpawnData spawn = new SpawnData();

    public GuiNpcNaturalSpawns(EntityNPCInterface npc) {
        super(npc);
        Packets.sendServer(new SPacketNaturalSpawnGetAll());
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
            this.scroll.setSize(143, 208);
        }
        this.scroll.guiLeft = this.guiLeft + 214;
        this.scroll.guiTop = this.guiTop + 4;
        this.addScroll(this.scroll);
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 358, this.guiTop + 38, 58, 20, "gui.add"));
        this.addButton(new GuiButtonNop(this, 2, this.guiLeft + 358, this.guiTop + 61, 58, 20, "gui.remove"));
        if (this.spawn.id >= 0) {
            this.showSpawn();
        }
    }

    private void showSpawn() {
        this.addLabel(new GuiLabel(1, "gui.title", this.guiLeft + 4, this.guiTop + 8));
        this.addTextField(new GuiTextFieldNop(1, (Screen)this, this.guiLeft + 60, this.guiTop + 3, 140, 20, this.spawn.name));
        this.addLabel(new GuiLabel(3, "spawning.biomes", this.guiLeft + 4, this.guiTop + 30));
        this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 120, this.guiTop + 25, 50, 20, "selectServer.edit"));
        this.addSlider(new GuiSliderNop(this, 4, this.guiLeft + 4, this.guiTop + 47, 180, 20, (float)this.spawn.m_142631_().m_146281_() / 100.0f));
        int y = this.guiTop + 70;
        this.addButton(new GuiButtonNop(this, 25, this.guiLeft + 14, y, 20, 20, "X"));
        this.addLabel(new GuiLabel(5, "1:", this.guiLeft + 4, y + 5));
        this.addButton(new GuiButtonNop(this, 5, this.guiLeft + 36, y, 170, 20, this.getTitle(1)));
        this.addLabel(new GuiLabel(26, "gui.type", this.guiLeft + 4, this.guiTop + 100));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 27, this.guiLeft + 70, this.guiTop + 93, 120, 20, new String[]{"spawner.any", "spawner.dark", "spawner.light"}, this.spawn.type));
    }

    private String getTitle(int slot) {
        if (this.spawn.data.containsKey(slot)) {
            return this.spawn.data.get((Object)Integer.valueOf((int)slot)).name;
        }
        return "gui.selectnpc";
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 1) {
            this.save();
            Object name = I18n.m_118938_((String)"gui.new", (Object[])new Object[0]);
            while (this.data.containsKey(name)) {
                name = (String)name + "_";
            }
            SpawnData spawn = new SpawnData();
            spawn.name = name;
            Packets.sendServer(new SPacketNaturalSpawnSave(spawn.writeNBT(new CompoundTag())));
        }
        if (id == 2 && this.data.containsKey(this.scroll.getSelected())) {
            Packets.sendServer(new SPacketNaturalSpawnRemove(this.spawn.id));
            this.spawn = new SpawnData();
            this.scroll.clear();
        }
        if (id == 3) {
            this.setSubGui(new SubGuiNpcBiomes(this.spawn));
        }
        if (id == 5) {
            this.setSubGui(new GuiNpcMobSpawnerSelector());
        }
        if (id == 25) {
            this.spawn.data.remove(1);
            this.m_7856_();
        }
        if (id == 27) {
            this.spawn.type = guibutton.getValue();
        }
    }

    @Override
    public void unFocused(GuiTextFieldNop guiNpcTextField) {
        String name = guiNpcTextField.m_94155_();
        if (name.isEmpty() || this.data.containsKey(name)) {
            guiNpcTextField.m_94144_(this.spawn.name);
        } else {
            String old = this.spawn.name;
            this.data.remove(old);
            this.spawn.name = name;
            this.data.put(this.spawn.name, this.spawn.id);
            this.scroll.replace(old, this.spawn.name);
        }
    }

    @Override
    public void setData(Vector<String> list, Map<String, Integer> data) {
        String name = this.scroll.getSelected();
        this.data = data;
        this.scroll.setList(list);
        if (name != null) {
            this.scroll.setSelected(name);
        }
        this.m_7856_();
    }

    @Override
    public void scrollClicked(double i, double j, int k, GuiCustomScrollNop guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.save();
            String selected = this.scroll.getSelected();
            this.spawn = new SpawnData();
            Packets.sendServer(new SPacketNaturalSpawnGet(this.data.get(selected)));
        }
    }

    @Override
    public void save() {
        GuiTextFieldNop.unfocus();
        if (this.spawn.id >= 0) {
            Packets.sendServer(new SPacketNaturalSpawnSave(this.spawn.writeNBT(new CompoundTag())));
        }
    }

    @Override
    public void setSelected(String selected) {
    }

    @Override
    public void subGuiClosed(Screen gui) {
        if (gui instanceof GuiNpcMobSpawnerSelector) {
            GuiNpcMobSpawnerSelector selector = (GuiNpcMobSpawnerSelector)gui;
            String selected = selector.getSelected();
            if (selected != null) {
                this.spawn.setClone(1, selector.activeTab, selected);
            }
            this.m_7856_();
        }
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.spawn.readNBT(compound);
        this.setSelected(this.spawn.name);
        this.m_7856_();
    }

    @Override
    public void mouseDragged(GuiSliderNop guiNpcSlider) {
        guiNpcSlider.m_93666_((Component)Component.m_237115_((String)"spawning.weightedChance").m_130946_(": " + (int)(guiNpcSlider.sliderValue * 100.0f)));
    }

    @Override
    public void mousePressed(GuiSliderNop guiNpcSlider) {
    }

    @Override
    public void mouseReleased(GuiSliderNop guiNpcSlider) {
        this.spawn.setWeight((int)(guiNpcSlider.sliderValue * 100.0f));
    }

    @Override
    public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
    }
}

