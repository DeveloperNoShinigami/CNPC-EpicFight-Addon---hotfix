/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.ChatFormatting
 *  net.minecraft.SharedConstants
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.font.TextFieldHelper
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.client.gui.player;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerMail;
import noppes.npcs.controllers.data.PlayerMail;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketPlayerMailDelete;
import noppes.npcs.packets.server.SPacketPlayerMailSend;
import noppes.npcs.shared.client.gui.components.GuiButtonNextPage;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiClose;
import noppes.npcs.shared.client.gui.listeners.IGuiError;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

@OnlyIn(value=Dist.CLIENT)
public class GuiMailmanWrite
extends GuiContainerNPCInterface<ContainerMail>
implements ITextfieldListener,
IGuiError,
IGuiClose {
    private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
    private static final ResourceLocation bookWidgets = new ResourceLocation("textures/gui/widgets.png");
    private static final ResourceLocation bookInventory = new ResourceLocation("textures/gui/container/inventory.png");
    private final TextFieldHelper pageEdit = new TextFieldHelper(this::getText, this::setText, this::getClipboard, this::setClipboard, p_238774_1_ -> p_238774_1_.length() < 1024 && this.f_96547_.m_92920_(p_238774_1_, 114) <= 128);
    private int updateCount;
    private int bookImageWidth = 192;
    private int bookImageHeight = 192;
    private int bookTotalPages = 1;
    private int currPage;
    private ListTag bookPages;
    private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private final boolean canEdit;
    private final boolean canSend;
    private boolean hasSend = false;
    public static Screen parent;
    public static PlayerMail mail;
    private Minecraft mc = Minecraft.m_91087_();
    private String username = "";
    private GuiLabel error;

    public GuiMailmanWrite(ContainerMail container, Inventory inv, Component titleIn) {
        super(null, container, inv, titleIn);
        this.title = "";
        this.canEdit = container.canEdit;
        this.canSend = container.canSend;
        if (GuiMailmanWrite.mail.message.m_128441_("pages")) {
            this.bookPages = GuiMailmanWrite.mail.message.m_128437_("pages", 8);
        }
        if (this.bookPages != null) {
            this.bookPages = this.bookPages.m_6426_();
            this.bookTotalPages = this.bookPages.size();
            if (this.bookTotalPages < 1) {
                this.bookTotalPages = 1;
            }
        } else {
            this.bookPages = new ListTag();
            this.bookPages.add((Object)StringTag.m_129297_((String)""));
            this.bookTotalPages = 1;
        }
        this.f_97726_ = 360;
        this.f_97727_ = 260;
        this.drawDefaultBackground = false;
    }

    @Override
    public void m_181908_() {
        ++this.updateCount;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.f_169369_.clear();
        if (this.canEdit && !this.canSend) {
            this.addLabel(new GuiLabel(0, "mailbox.sender", this.guiLeft + 170, this.guiTop + 32, 0));
        } else {
            this.addLabel(new GuiLabel(0, "mailbox.username", this.guiLeft + 170, this.guiTop + 32, 0));
        }
        if (this.canEdit && !this.canSend) {
            this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 170, this.guiTop + 42, 114, 20, GuiMailmanWrite.mail.sender));
        } else if (this.canEdit) {
            this.addTextField(new GuiTextFieldNop(0, (Screen)this, this.guiLeft + 170, this.guiTop + 42, 114, 20, this.username));
        } else {
            this.addLabel(new GuiLabel(10, GuiMailmanWrite.mail.sender, this.guiLeft + 170, this.guiTop + 42, 0));
        }
        this.addLabel(new GuiLabel(1, "mailbox.subject", this.guiLeft + 170, this.guiTop + 72, 0));
        if (this.canEdit) {
            this.addTextField(new GuiTextFieldNop(1, (Screen)this, this.guiLeft + 170, this.guiTop + 82, 114, 20, GuiMailmanWrite.mail.subject));
        } else {
            this.addLabel(new GuiLabel(11, GuiMailmanWrite.mail.subject, this.guiLeft + 170, this.guiTop + 82, 0));
        }
        this.error = new GuiLabel(2, "", this.guiLeft + 170, this.guiTop + 114, 0xFF0000);
        this.addLabel(this.error);
        if (this.canEdit && !this.canSend) {
            this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 200, this.guiTop + 171, 60, 20, "gui.done"));
        } else if (this.canEdit) {
            this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 200, this.guiTop + 171, 60, 20, "mailbox.send"));
        }
        if (!this.canEdit && !this.canSend) {
            this.addButton(new GuiButtonNop(this, 4, this.guiLeft + 200, this.guiTop + 171, 60, 20, "selectServer.delete"));
        }
        if (!this.canEdit || this.canSend) {
            this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 200, this.guiTop + 194, 60, 20, "gui.cancel"));
        }
        this.buttonNextPage = new GuiButtonNextPage((IGuiInterface)this, 1, this.guiLeft + 120, this.guiTop + 156, true, b -> {
            if (this.currPage < this.bookTotalPages - 1) {
                ++this.currPage;
            } else if (this.canEdit) {
                this.addNewPage();
                if (this.currPage < this.bookTotalPages - 1) {
                    ++this.currPage;
                }
            }
            this.updateButtons();
        });
        this.addButton(this.buttonNextPage);
        this.buttonPreviousPage = new GuiButtonNextPage((IGuiInterface)this, 2, this.guiLeft + 38, this.guiTop + 156, false, b -> {
            if (this.currPage > 0) {
                --this.currPage;
            }
            this.updateButtons();
        });
        this.addButton(this.buttonPreviousPage);
        this.updateButtons();
    }

    @Override
    public void m_7379_() {
    }

    private void updateButtons() {
        this.buttonNextPage.f_93624_ = this.currPage < this.bookTotalPages - 1 || this.canEdit;
        this.buttonPreviousPage.f_93624_ = this.currPage > 0;
    }

    @Override
    public void buttonEvent(GuiButtonNop par1GuiButton) {
        if (par1GuiButton.f_93623_) {
            int id = par1GuiButton.id;
            if (id == 0) {
                GuiMailmanWrite.mail.message.m_128365_("pages", (Tag)this.bookPages);
                if (this.canSend) {
                    if (!this.hasSend) {
                        this.hasSend = true;
                        Packets.sendServer(new SPacketPlayerMailSend(this.username, mail.writeNBT()));
                    }
                } else {
                    this.close();
                }
            }
            if (id == 3) {
                this.close();
            }
            if (id == 4) {
                ConfirmScreen guiyesno = new ConfirmScreen(flag -> {
                    if (flag) {
                        Packets.sendServer(new SPacketPlayerMailDelete(GuiMailmanWrite.mail.time, GuiMailmanWrite.mail.sender));
                        this.close();
                    } else {
                        NoppesUtil.openGUI((Player)this.player, this);
                    }
                }, (Component)Component.m_237113_((String)""), (Component)Component.m_237115_((String)I18n.m_118938_((String)"gui.deleteMessage", (Object[])new Object[0])));
                this.setScreen((Screen)guiyesno);
            }
            this.updateButtons();
        }
    }

    private void addNewPage() {
        if (this.bookPages != null && this.bookPages.size() < 50) {
            this.bookPages.add((Object)StringTag.m_129297_((String)""));
            ++this.bookTotalPages;
        }
    }

    @Override
    public boolean m_5534_(char par1, int limbSwingAmount) {
        if (!GuiTextFieldNop.isAnyActive() && this.canEdit) {
            if (SharedConstants.m_136188_((char)par1)) {
                this.pageEdit.m_95158_(Character.toString(par1));
                return true;
            }
        } else {
            super.m_5534_(par1, limbSwingAmount);
        }
        return true;
    }

    @Override
    public boolean m_7933_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        if (super.m_7933_(p_231046_1_, p_231046_2_, p_231046_3_)) {
            return true;
        }
        boolean flag = this.bookKeyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
        return flag;
    }

    private boolean bookKeyPressed(int p_214230_1_, int p_214230_2_, int p_214230_3_) {
        if (Screen.m_96634_((int)p_214230_1_)) {
            this.pageEdit.m_95188_();
            return true;
        }
        if (Screen.m_96632_((int)p_214230_1_)) {
            this.pageEdit.m_95178_();
            return true;
        }
        if (Screen.m_96630_((int)p_214230_1_)) {
            this.pageEdit.m_95165_();
            return true;
        }
        if (Screen.m_96628_((int)p_214230_1_)) {
            this.pageEdit.m_95142_();
            return true;
        }
        switch (p_214230_1_) {
            case 257: 
            case 335: {
                this.pageEdit.m_95158_("\n");
                return true;
            }
            case 259: {
                this.pageEdit.m_95189_(-1);
                return true;
            }
            case 261: {
                this.pageEdit.m_95189_(1);
                return true;
            }
            case 262: {
                this.pageEdit.m_95150_(1, Screen.m_96638_());
                return true;
            }
            case 263: {
                this.pageEdit.m_95150_(-1, Screen.m_96638_());
                return true;
            }
            case 266: {
                this.buttonPreviousPage.m_5691_();
                return true;
            }
            case 267: {
                this.buttonNextPage.m_5691_();
                return true;
            }
        }
        return false;
    }

    private String getText() {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.size()) {
            return this.bookPages.m_128778_(this.currPage);
        }
        return "";
    }

    private void setText(String par1Str) {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.size()) {
            this.bookPages.set(this.currPage, (Tag)StringTag.m_129297_((String)par1Str));
        }
    }

    private void setClipboard(String p_238760_1_) {
        if (this.f_96541_ != null) {
            TextFieldHelper.m_95155_((Minecraft)this.f_96541_, (String)p_238760_1_);
        }
    }

    private String getClipboard() {
        return this.f_96541_ != null ? TextFieldHelper.m_95169_((Minecraft)this.f_96541_) : "";
    }

    private void append(String par1Str) {
        String s1 = this.getText();
        String s2 = s1 + par1Str;
        int i = this.mc.f_91062_.m_92920_(s2 + ChatFormatting.BLACK + "_", 118);
        if (i <= 118 && s2.length() < 256) {
            this.setText(s2);
        }
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float par3) {
        super.m_280273_(graphics);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)bookGuiTextures);
        graphics.m_280218_(bookGuiTextures, this.guiLeft + 130, this.guiTop + 22, 0, 0, this.bookImageWidth, this.bookImageHeight / 3);
        graphics.m_280218_(bookGuiTextures, this.guiLeft + 130, this.guiTop + 22 + this.bookImageHeight / 3, 0, this.bookImageHeight / 2, this.bookImageWidth, this.bookImageHeight / 2);
        graphics.m_280218_(bookGuiTextures, this.guiLeft, this.guiTop + 2, 0, 0, this.bookImageWidth, this.bookImageHeight);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)bookInventory);
        graphics.m_280218_(bookInventory, this.guiLeft + 20, this.guiTop + 173, 0, 82, 180, 55);
        graphics.m_280218_(bookInventory, this.guiLeft + 20, this.guiTop + 228, 0, 140, 180, 28);
        String s = I18n.m_118938_((String)"book.pageIndicator", (Object[])new Object[]{this.currPage + 1, this.bookTotalPages});
        Object s1 = "";
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.size()) {
            s1 = this.bookPages.m_128778_(this.currPage);
        }
        if (this.canEdit) {
            s1 = this.updateCount / 6 % 2 == 0 ? (String)s1 + "_" : (this.updateCount / 6 % 2 == 0 ? (String)s1 + ChatFormatting.BLACK + "_" : (String)s1 + ChatFormatting.GRAY + "_");
        }
        int l = this.mc.f_91062_.m_92895_(s);
        graphics.m_280488_(this.mc.f_91062_, s, this.guiLeft - l + this.bookImageWidth - 44, this.guiTop + 18, 0);
        graphics.m_280554_(this.mc.f_91062_, (FormattedText)Component.m_237115_((String)s1), this.guiLeft + 36, this.guiTop + 18 + 16, 116, 0);
        graphics.m_280024_(this.guiLeft + 175, this.guiTop + 136, this.guiLeft + 269, this.guiTop + 154, -1072689136, -804253680);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)bookWidgets);
        for (int i = 0; i < 4; ++i) {
            graphics.m_280218_(bookWidgets, this.guiLeft + 175 + i * 24, this.guiTop + 134, 0, 22, 24, 24);
        }
        super.m_88315_(graphics, mouseX, mouseY, par3);
    }

    @Override
    public void close() {
        this.mc.m_91152_(parent);
        parent = null;
        mail = new PlayerMail();
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 0) {
            this.username = textfield.m_94155_();
        }
        if (textfield.id == 1) {
            GuiMailmanWrite.mail.subject = textfield.m_94155_();
        }
        if (textfield.id == 2) {
            GuiMailmanWrite.mail.sender = textfield.m_94155_();
        }
    }

    @Override
    public void setError(int i, CompoundTag data) {
        if (i == 0) {
            this.error.m_93666_((Component)Component.m_237115_((String)"mailbox.errorUsername"));
        }
        if (i == 1) {
            this.error.m_93666_((Component)Component.m_237115_((String)"mailbox.errorSubject"));
        }
        this.hasSend = false;
    }

    @Override
    public void setClose(CompoundTag data) {
        this.player.m_213846_((Component)Component.m_237110_((String)"mailbox.succes", (Object[])new Object[]{data.m_128461_("username")}));
    }

    @Override
    public void save() {
    }

    static {
        mail = new PlayerMail();
    }
}

