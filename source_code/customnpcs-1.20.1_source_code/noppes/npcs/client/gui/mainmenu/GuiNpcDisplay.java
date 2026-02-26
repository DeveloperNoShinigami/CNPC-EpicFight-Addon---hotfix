/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui.mainmenu;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.SubGuiNpcAvailability;
import noppes.npcs.client.gui.SubGuiNpcName;
import noppes.npcs.client.gui.model.GuiCreationEntities;
import noppes.npcs.client.gui.select.GuiTextureSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.constants.EnumMenuType;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.data.DataDisplay;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketMenuGet;
import noppes.npcs.packets.server.SPacketMenuSave;
import noppes.npcs.packets.server.SPacketNpRandomNameSet;
import noppes.npcs.shared.client.gui.components.GuiButtonBiDirectional;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiButtonYesNo;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiData;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiNpcDisplay
extends GuiNPCInterface2
implements ITextfieldListener,
IGuiData {
    private DataDisplay display;

    public GuiNpcDisplay(EntityNPCInterface npc) {
        super(npc, 1);
        this.display = npc.display;
        Packets.sendServer(new SPacketMenuGet(EnumMenuType.DISPLAY));
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        int y = this.guiTop + 4;
        this.addLabel(new GuiLabel(0, "gui.name", this.guiLeft + 5, y + 5, "guihint.npcname"));
        this.addTextField(new GuiTextFieldNop(0, (Screen)this, this.guiLeft + 50, y, 206, 20, this.display.getName()));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 0, this.guiLeft + 253 + 52, y, 110, 20, new String[]{"display.show", "display.hide", "display.showAttacking"}, this.display.getShowName()));
        this.addButton(new GuiButtonNop(this, 14, this.guiLeft + 259, y, 20, 20, Character.toString('\u21bb')));
        this.addButton(new GuiButtonNop(this, 15, this.guiLeft + 259 + 22, y, 20, 20, Character.toString('\u22ee')));
        this.addLabel(new GuiLabel(11, "gui.title", this.guiLeft + 5, (y += 23) + 5, "guihint.npctitle"));
        this.addTextField(new GuiTextFieldNop(11, (Screen)this, this.guiLeft + 50, y, 186, 20, this.display.getTitle()));
        this.addLabel(new GuiLabel(1, "display.model", this.guiLeft + 5, (y += 23) + 5, "guihint.npcmodel"));
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 50, y, 110, 20, "selectServer.edit"));
        this.addLabel(new GuiLabel(2, "display.size", this.guiLeft + 175, y + 5, "guihint.npcsize"));
        this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 203, y, 40, 20, "" + this.display.getSize()));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(1, 30, 5);
        this.addLabel(new GuiLabel(3, "(1-30)", this.guiLeft + 246, y + 5));
        this.addLabel(new GuiLabel(4, "display.texture", this.guiLeft + 5, (y += 23) + 5, "guihint.npctexture"));
        this.addTextField(new GuiTextFieldNop(3, (Screen)this, this.guiLeft + 80, y, 200, 20, this.display.skinType == 0 ? this.display.getSkinTexture() : this.display.getSkinUrl()));
        this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 325, y, 38, 20, "mco.template.button.select"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 2, this.guiLeft + 283, y, 40, 20, new String[]{"display.texture", "display.player", "display.url"}, this.display.skinType));
        this.getButton(3).setEnabled(this.display.skinType == 0);
        if (this.display.skinType == 1 && !this.display.getSkinPlayer().isEmpty()) {
            this.getTextField(3).m_94144_(this.display.getSkinPlayer());
        }
        this.addLabel(new GuiLabel(8, "display.cape", this.guiLeft + 5, (y += 23) + 5, "guihint.npccape"));
        this.addTextField(new GuiTextFieldNop(8, (Screen)this, this.guiLeft + 80, y, 200, 20, this.display.getCapeTexture()));
        this.addButton(new GuiButtonNop(this, 8, this.guiLeft + 283, y, 80, 20, "display.selectTexture"));
        this.addLabel(new GuiLabel(9, "display.overlay", this.guiLeft + 5, (y += 23) + 5, "guihint.npcoverlay"));
        this.addTextField(new GuiTextFieldNop(9, (Screen)this, this.guiLeft + 80, y, 200, 20, this.display.getOverlayTexture()));
        this.addButton(new GuiButtonNop(this, 9, this.guiLeft + 283, y, 38, 20, "mco.template.button.select"));
        this.addLabel(new GuiLabel(14, "display.isglowing", this.guiLeft + 325, y + 5));
        this.addButton(new GuiButtonYesNo((IGuiInterface)this, 18, this.guiLeft + 365, y, 40, 20, this.display.isOverlayGlowing()));
        this.addLabel(new GuiLabel(5, "display.livingAnimation", this.guiLeft + 5, (y += 23) + 5, "guihint.npchasliving"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 5, this.guiLeft + 120, y, 50, 20, new String[]{"gui.yes", "gui.no"}, this.display.getHasLivingAnimation() ? 0 : 1));
        this.addLabel(new GuiLabel(6, "display.tint", this.guiLeft + 180, y + 5, "guihint.npctint"));
        Object color = Integer.toHexString(this.display.getTint());
        while (((String)color).length() < 6) {
            color = "0" + (String)color;
        }
        this.addTextField(new GuiTextFieldNop(6, (Screen)this, this.guiLeft + 220, y, 60, 20, (String)color));
        this.getTextField(6).m_94202_(this.display.getTint());
        this.addLabel(new GuiLabel(15, "display.showlayers", this.guiLeft + 285, y + 5));
        this.addButton(new GuiButtonYesNo((IGuiInterface)this, 19, this.guiLeft + 365, y, 40, 20, this.display.isShowingLayers()));
        this.addLabel(new GuiLabel(7, "display.visible", this.guiLeft + 5, (y += 23) + 5, "guihint.npcvisible"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 7, this.guiLeft + 40, y, 50, 20, new String[]{"gui.yes", "gui.no", "gui.partly"}, this.display.getVisible()));
        this.addButton(new GuiButtonNop(this, 16, this.guiLeft + 92, y, 78, 20, "availability.name"));
        this.addLabel(new GuiLabel(13, "display.hitbox", this.guiLeft + 180, y + 5, "guihint.npchitbox"));
        this.addButton(new GuiButtonBiDirectional((IGuiInterface)this, 13, this.guiLeft + 230, y, 100, 20, this.display.getHitboxState(), "stats.normal", "gui.none", "hair.solid"));
        this.addLabel(new GuiLabel(10, "display.bossbar", this.guiLeft + 5, (y += 23) + 5, "guihint.npcbossbar"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 10, this.guiLeft + 60, y, 110, 20, new String[]{"display.hide", "display.show", "display.showAttacking"}, this.display.getBossbar()));
        this.addLabel(new GuiLabel(12, "gui.color", this.guiLeft + 180, y + 5, "guihint.npcbossbarcolor"));
        this.addButton(new GuiButtonBiDirectional((IGuiInterface)this, 12, this.guiLeft + 230, y, 100, 20, this.display.getBossColor(), "color.pink", "color.blue", "color.red", "color.green", "color.yellow", "color.purple", "color.white"));
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 0) {
            if (!textfield.isEmpty()) {
                this.display.setName(textfield.m_94155_());
            } else {
                textfield.m_94144_(this.display.getName());
            }
        } else if (textfield.id == 2) {
            this.display.setSize(textfield.getInteger());
        } else if (textfield.id == 3) {
            if (this.display.skinType == 2) {
                this.display.setSkinUrl(textfield.m_94155_());
            } else if (this.display.skinType == 1) {
                this.display.setSkinPlayer(textfield.m_94155_());
            } else {
                this.display.setSkinTexture(textfield.m_94155_());
            }
        } else if (textfield.id == 6) {
            int color = 0;
            try {
                color = Integer.parseInt(textfield.m_94155_(), 16);
            }
            catch (NumberFormatException e) {
                color = 0xFFFFFF;
            }
            this.display.setTint(color);
            textfield.m_94202_(this.display.getTint());
        } else if (textfield.id == 8) {
            this.display.setCapeTexture(textfield.m_94155_());
        } else if (textfield.id == 9) {
            this.display.setOverlayTexture(textfield.m_94155_());
        } else if (textfield.id == 11) {
            this.display.setTitle(textfield.m_94155_());
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 0) {
            this.display.setShowName(button.getValue());
        }
        if (button.id == 1) {
            NoppesUtil.openGUI((Player)this.player, new GuiCreationEntities((EntityCustomNpc)this.npc));
        }
        if (button.id == 2) {
            this.display.setSkinUrl("");
            this.display.setSkinPlayer(null);
            this.display.skinType = (byte)button.getValue();
            this.m_7856_();
        } else if (button.id == 3) {
            this.setSubGui(new GuiTextureSelection(this.npc, this.npc.display.getSkinTexture(), 0));
        } else if (button.id == 5) {
            this.display.setHasLivingAnimation(button.getValue() == 0);
        } else if (button.id == 7) {
            this.display.setVisible(button.getValue());
        } else if (button.id == 8) {
            this.setSubGui(new GuiTextureSelection(this.npc, this.npc.display.getCapeTexture(), 1));
        } else if (button.id == 9) {
            this.setSubGui(new GuiTextureSelection(this.npc, this.npc.display.getOverlayTexture(), 2));
        } else if (button.id == 10) {
            this.display.setBossbar(button.getValue());
        } else if (button.id == 12) {
            this.display.setBossColor(button.getValue());
        } else if (button.id == 13) {
            this.display.setHitboxState((byte)button.getValue());
        } else if (button.id == 14) {
            Packets.sendServer(new SPacketNpRandomNameSet(this.display.getMarkovGeneratorId(), this.display.getMarkovGender()));
        } else if (button.id == 15) {
            this.setSubGui(new SubGuiNpcName(this.display));
        } else if (button.id == 16) {
            this.setSubGui(new SubGuiNpcAvailability(this.display.availability));
        } else if (button.id == 18) {
            this.display.setOverlayGlowing(((GuiButtonYesNo)button).getBoolean());
        } else if (button.id == 19) {
            this.display.setShowingLayers(((GuiButtonYesNo)button).getBoolean());
        }
    }

    @Override
    public void subGuiClosed(Screen subgui) {
        this.m_7856_();
    }

    @Override
    public void save() {
        if (this.display.skinType == 1) {
            this.display.loadProfile();
        }
        this.npc.textureLocation = null;
        Packets.sendServer(new SPacketMenuSave(EnumMenuType.DISPLAY, this.display.save(new CompoundTag())));
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.display.readToNBT(compound);
        this.m_7856_();
    }
}

