/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.biome.Biome
 */
package noppes.npcs.client.gui;

import java.util.ArrayList;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import noppes.npcs.controllers.data.SpawnData;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;

public class SubGuiNpcBiomes
extends GuiBasic {
    private SpawnData data;
    private GuiCustomScrollNop scroll1;
    private GuiCustomScrollNop scroll2;

    public SubGuiNpcBiomes(SpawnData data) {
        this.data = data;
        this.setBackground("menubg.png");
        this.imageWidth = 346;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (this.scroll1 == null) {
            this.scroll1 = new GuiCustomScrollNop(this, 0);
            this.scroll1.setSize(140, 180);
        }
        this.scroll1.guiLeft = this.guiLeft + 4;
        this.scroll1.guiTop = this.guiTop + 14;
        this.addScroll(this.scroll1);
        this.addLabel(new GuiLabel(1, "spawning.availableBiomes", this.guiLeft + 4, this.guiTop + 4));
        if (this.scroll2 == null) {
            this.scroll2 = new GuiCustomScrollNop(this, 1);
            this.scroll2.setSize(140, 180);
        }
        this.scroll2.guiLeft = this.guiLeft + 200;
        this.scroll2.guiTop = this.guiTop + 14;
        this.addScroll(this.scroll2);
        this.addLabel(new GuiLabel(2, "spawning.spawningBiomes", this.guiLeft + 200, this.guiTop + 4));
        ArrayList<String> biomes = new ArrayList<String>();
        Registry biomeRegistry = this.player.m_9236_().m_9598_().m_175515_(Registries.f_256952_);
        for (Biome base : biomeRegistry) {
            if (base == null || biomeRegistry.m_7981_((Object)base) == null || this.data.biomes.contains(biomeRegistry.m_7981_((Object)base).toString())) continue;
            biomes.add(biomeRegistry.m_7981_((Object)base).toString());
        }
        this.scroll1.setList(biomes);
        this.scroll2.setList(this.data.biomes.stream().map(Object::toString).collect(Collectors.toList()));
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 145, this.guiTop + 40, 55, 20, ">"));
        this.addButton(new GuiButtonNop(this, 2, this.guiLeft + 145, this.guiTop + 62, 55, 20, "<"));
        this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 145, this.guiTop + 90, 55, 20, ">>"));
        this.addButton(new GuiButtonNop(this, 4, this.guiLeft + 145, this.guiTop + 112, 55, 20, "<<"));
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 260, this.guiTop + 194, 60, 20, "gui.done"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 1 && this.scroll1.hasSelected()) {
            this.data.biomes.add(new ResourceLocation(this.scroll1.getSelected()));
            this.scroll1.clearSelection();
            this.scroll2.clearSelection();
            this.m_7856_();
        }
        if (button.id == 2 && this.scroll2.hasSelected()) {
            this.data.biomes.remove(new ResourceLocation(this.scroll2.getSelected()));
            this.scroll2.clearSelection();
            this.m_7856_();
        }
        if (button.id == 3) {
            this.data.biomes.clear();
            Registry biomeRegistry = this.player.m_9236_().m_9598_().m_175515_(Registries.f_256952_);
            for (Biome base : biomeRegistry) {
                if (base == null) continue;
                this.data.biomes.add(biomeRegistry.m_7981_((Object)base));
            }
            this.scroll1.clearSelection();
            this.scroll2.clearSelection();
            this.m_7856_();
        }
        if (button.id == 4) {
            this.data.biomes.clear();
            this.scroll1.clearSelection();
            this.scroll2.clearSelection();
            this.m_7856_();
        }
        if (button.id == 66) {
            this.close();
        }
    }
}

