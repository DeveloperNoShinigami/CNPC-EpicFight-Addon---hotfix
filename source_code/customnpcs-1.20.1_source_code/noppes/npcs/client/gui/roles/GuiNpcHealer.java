/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffect
 */
package noppes.npcs.client.gui.roles;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketNpcJobSave;
import noppes.npcs.roles.JobHealer;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiNpcHealer
extends GuiNPCInterface2 {
    private JobHealer job;
    private GuiCustomScrollNop scroll1;
    private GuiCustomScrollNop scroll2;
    private HashMap<String, Integer> potions;
    private HashMap<String, String> displays;
    private int potency = 0;

    public GuiNpcHealer(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobHealer)npc.job;
        this.potions = new HashMap();
        this.displays = new HashMap();
        Registry r = BuiltInRegistries.f_256974_;
        for (ResourceLocation rl : r.m_6566_()) {
            this.potions.put(rl.toString(), r.m_7447_((Object)((MobEffect)r.m_7745_(rl))));
        }
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(1, "beacon.range", this.guiLeft + 10, this.guiTop + 9));
        this.addTextField(new GuiTextFieldNop(1, (Screen)this, this.guiLeft + 80, this.guiTop + 4, 40, 20, "" + this.job.range));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(1, 64, 16);
        this.addLabel(new GuiLabel(4, "stats.speed", this.guiLeft + 140, this.guiTop + 9));
        this.addTextField(new GuiTextFieldNop(4, (Screen)this, this.guiLeft + 220, this.guiTop + 4, 40, 20, "" + this.job.speed));
        this.getTextField((int)4).numbersOnly = true;
        this.getTextField(4).setMinMaxDefault(10, Integer.MAX_VALUE, 20);
        this.addLabel(new GuiLabel(3, "beacon.affect", this.guiLeft + 10, this.guiTop + 31));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 3, this.guiLeft + 56, this.guiTop + 26, 80, 20, new String[]{"faction.friendly", "faction.unfriendly", "spawner.all"}, this.job.type));
        this.addLabel(new GuiLabel(2, "beacon.potency", this.guiLeft + 140, this.guiTop + 31));
        this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 220, this.guiTop + 26, 40, 20, "" + this.potency));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(0, 3, 0);
        if (this.scroll1 == null) {
            this.scroll1 = new GuiCustomScrollNop(this, 0);
            this.scroll1.setSize(175, 154);
        }
        this.scroll1.guiLeft = this.guiLeft + 4;
        this.scroll1.guiTop = this.guiTop + 58;
        this.addScroll(this.scroll1);
        this.addLabel(new GuiLabel(11, "beacon.availableEffects", this.guiLeft + 4, this.guiTop + 48));
        if (this.scroll2 == null) {
            this.scroll2 = new GuiCustomScrollNop(this, 1);
            this.scroll2.setSize(175, 154);
        }
        this.scroll2.guiLeft = this.guiLeft + 235;
        this.scroll2.guiTop = this.guiTop + 58;
        this.addScroll(this.scroll2);
        this.addLabel(new GuiLabel(12, "beacon.currentEffects", this.guiLeft + 235, this.guiTop + 48));
        ArrayList<String> all = new ArrayList<String>();
        for (String names : this.potions.keySet()) {
            if (!this.job.effects.containsKey(this.potions.get(names))) {
                all.add(names);
                continue;
            }
            this.displays.put(I18n.m_118938_((String)names, (Object[])new Object[0]) + " " + I18n.m_118938_((String)("enchantment.level." + (this.job.effects.get(this.potions.get(names)) + 1)), (Object[])new Object[0]), names);
        }
        this.scroll1.setList(all);
        ArrayList<String> applied = new ArrayList<String>(this.displays.keySet());
        this.scroll2.setList(applied);
        this.addButton(new GuiButtonNop(this, 11, this.guiLeft + 180, this.guiTop + 80, 55, 20, ">"));
        this.addButton(new GuiButtonNop(this, 12, this.guiLeft + 180, this.guiTop + 102, 55, 20, "<"));
        this.addButton(new GuiButtonNop(this, 13, this.guiLeft + 180, this.guiTop + 130, 55, 20, ">>"));
        this.addButton(new GuiButtonNop(this, 14, this.guiLeft + 180, this.guiTop + 152, 55, 20, "<<"));
    }

    @Override
    public void elementClicked() {
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 3) {
            this.job.type = (byte)button.getValue();
        }
        if (button.id == 11 && this.scroll1.hasSelected()) {
            this.job.effects.put(this.potions.get(this.scroll1.getSelected()), this.getTextField(2).getInteger());
            this.scroll1.clearSelection();
            this.scroll2.clearSelection();
            this.m_7856_();
        }
        if (button.id == 12 && this.scroll2.hasSelected()) {
            this.job.effects.remove(this.potions.get(this.displays.remove(this.scroll2.getSelected())));
            this.scroll1.clearSelection();
            this.scroll2.clearSelection();
            this.m_7856_();
        }
        if (button.id == 13) {
            this.job.effects.clear();
            ArrayList all = new ArrayList();
            Registry r = BuiltInRegistries.f_256974_;
            for (ResourceLocation rl : r.m_6566_()) {
                this.job.effects.put(r.m_7447_((Object)((MobEffect)r.m_7745_(rl))), this.potency);
            }
            this.scroll1.clearSelection();
            this.scroll2.clearSelection();
            this.m_7856_();
        }
        if (button.id == 14) {
            this.job.effects.clear();
            this.displays.clear();
            this.scroll1.clearSelection();
            this.scroll2.clearSelection();
            this.m_7856_();
        }
    }

    @Override
    public void save() {
        this.job.range = this.getTextField(1).getInteger();
        this.potency = this.getTextField(2).getInteger();
        this.job.speed = this.getTextField(4).getInteger();
        Packets.sendServer(new SPacketNpcJobSave(this.job.save(new CompoundTag())));
    }
}

