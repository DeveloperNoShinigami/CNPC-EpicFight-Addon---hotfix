/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.client.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.client.controllers.ClientCloneController;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketCloneList;
import noppes.npcs.packets.server.SPacketCloneRemove;
import noppes.npcs.packets.server.SPacketToolMobSpawner;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiMenuSideButton;
import noppes.npcs.shared.client.gui.components.GuiMenuTopButton;
import noppes.npcs.shared.client.gui.listeners.IGuiData;

public class GuiNpcMobSpawner
extends GuiNPCInterface
implements IGuiData {
    private GuiCustomScrollNop scroll;
    private BlockPos pos;
    private List<String> list;
    private static int showingClones = 0;
    private int activeTab = 1;

    public GuiNpcMobSpawner(BlockPos pos) {
        this.imageWidth = 256;
        this.pos = pos;
        this.setBackground("menubg.png");
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.guiTop += 10;
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
            this.scroll.setSize(165, 210);
        } else {
            this.scroll.clear();
        }
        this.scroll.guiLeft = this.guiLeft + 4;
        this.scroll.guiTop = this.guiTop + 4;
        this.addScroll(this.scroll);
        GuiMenuTopButton button = new GuiMenuTopButton(this, 3, this.guiLeft + 4, this.guiTop - 17, "spawner.clones");
        this.addTopButton(button);
        button.active = showingClones == 0;
        button = new GuiMenuTopButton(this, 4, button, "spawner.entities");
        this.addTopButton(button);
        button.active = showingClones == 1;
        button = new GuiMenuTopButton(this, 5, button, "gui.server");
        this.addTopButton(button);
        button.active = showingClones == 2;
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 170, this.guiTop + 6, 82, 20, "gui.spawn"));
        this.addButton(new GuiButtonNop(this, 2, this.guiLeft + 170, this.guiTop + 100, 82, 20, "spawner.mobspawner"));
        if (showingClones == 0 || showingClones == 2) {
            this.addSideButton(new GuiMenuSideButton(this, 21, this.guiLeft - 69, this.guiTop + 2, 70, 22, "Tab 1"));
            this.addSideButton(new GuiMenuSideButton(this, 22, this.guiLeft - 69, this.guiTop + 23, 70, 22, "Tab 2"));
            this.addSideButton(new GuiMenuSideButton(this, 23, this.guiLeft - 69, this.guiTop + 44, 70, 22, "Tab 3"));
            this.addSideButton(new GuiMenuSideButton(this, 24, this.guiLeft - 69, this.guiTop + 65, 70, 22, "Tab 4"));
            this.addSideButton(new GuiMenuSideButton(this, 25, this.guiLeft - 69, this.guiTop + 86, 70, 22, "Tab 5"));
            this.addSideButton(new GuiMenuSideButton(this, 26, this.guiLeft - 69, this.guiTop + 107, 70, 22, "Tab 6"));
            this.addSideButton(new GuiMenuSideButton(this, 27, this.guiLeft - 69, this.guiTop + 128, 70, 22, "Tab 7"));
            this.addSideButton(new GuiMenuSideButton(this, 28, this.guiLeft - 69, this.guiTop + 149, 70, 22, "Tab 8"));
            this.addSideButton(new GuiMenuSideButton(this, 29, this.guiLeft - 69, this.guiTop + 170, 70, 22, "Tab 9"));
            this.addButton(new GuiButtonNop(this, 6, this.guiLeft + 170, this.guiTop + 30, 82, 20, "gui.remove"));
            this.getSideButton((int)(20 + this.activeTab)).active = true;
            this.showClones();
        } else {
            this.showEntities();
        }
    }

    private void showEntities() {
        this.list = new ArrayList<String>(EntityUtil.getAllEntities((Level)Minecraft.m_91087_().f_91073_, false).keySet());
        this.scroll.setList(this.list);
    }

    private void showClones() {
        if (showingClones == 2) {
            Packets.sendServer(new SPacketCloneList(this.activeTab));
            return;
        }
        ArrayList list = new ArrayList();
        this.list = ClientCloneController.Instance.getClones(this.activeTab);
        this.scroll.setList(this.list);
    }

    private CompoundTag getCompound() {
        String sel = this.scroll.getSelected();
        if (sel == null) {
            return null;
        }
        if (showingClones == 0) {
            return ClientCloneController.Instance.getCloneData(this.player.m_20203_(), sel, this.activeTab);
        }
        ResourceLocation loc = EntityUtil.getAllEntities((Level)Minecraft.m_91087_().f_91073_, false).get(sel);
        EntityType type = (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(loc);
        Entity entity = type.m_20615_((Level)Minecraft.m_91087_().f_91073_);
        if (entity == null) {
            return null;
        }
        CompoundTag compound = new CompoundTag();
        entity.m_20086_(compound);
        return compound;
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        CompoundTag compound;
        String sel;
        int id = guibutton.id;
        if (id == 0) {
            this.close();
        }
        if (id == 1) {
            if (showingClones == 2) {
                sel = this.scroll.getSelected();
                if (sel == null) {
                    return;
                }
                Packets.sendServer(new SPacketToolMobSpawner(false, this.pos, sel, this.activeTab));
                this.close();
            } else {
                compound = this.getCompound();
                if (compound == null) {
                    return;
                }
                Packets.sendServer(new SPacketToolMobSpawner(false, this.pos, compound));
                this.close();
            }
        }
        if (id == 2) {
            if (showingClones == 2) {
                sel = this.scroll.getSelected();
                if (sel == null) {
                    return;
                }
                Packets.sendServer(new SPacketToolMobSpawner(true, this.pos, sel, this.activeTab));
                this.close();
            } else {
                compound = this.getCompound();
                if (compound == null) {
                    return;
                }
                Packets.sendServer(new SPacketToolMobSpawner(true, this.pos, compound));
                this.close();
            }
        }
        if (id == 3) {
            showingClones = 0;
            this.m_7856_();
        }
        if (id == 4) {
            showingClones = 1;
            this.m_7856_();
        }
        if (id == 5) {
            showingClones = 2;
            this.m_7856_();
        }
        if (id == 6 && this.scroll.getSelected() != null) {
            if (showingClones == 2) {
                Packets.sendServer(new SPacketCloneRemove(this.scroll.getSelected(), this.activeTab));
                return;
            }
            ClientCloneController.Instance.removeClone(this.scroll.getSelected(), this.activeTab);
            this.scroll.clearSelection();
            this.m_7856_();
        }
        if (id > 20) {
            this.activeTab = id - 20;
            this.m_7856_();
        }
    }

    @Override
    public void save() {
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        ListTag nbtlist = compound.m_128437_("List", 8);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < nbtlist.size(); ++i) {
            list.add(nbtlist.m_128778_(i));
        }
        this.list = list;
        this.scroll.setList(this.list);
    }
}

