/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 */
package noppes.npcs.client.gui.model;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.components.CustomGuiButton;
import noppes.npcs.client.gui.custom.components.CustomGuiTextField;
import noppes.npcs.client.gui.custom.components.CustomGuiTexturedRect;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiModelColor
extends GuiCustom
implements ITextfieldListener {
    private GuiCustom parent;
    private static final ResourceLocation colorPicker = new ResourceLocation("moreplayermodels:textures/gui/color.png");
    private static final ResourceLocation colorgui = new ResourceLocation("moreplayermodels:textures/gui/color_gui.png");
    private int colorX;
    private int colorY;
    public int color;
    private CustomGuiTextField textfield;
    private CustomGuiButton button;
    private ColorCallback callback;

    public GuiModelColor(GuiCustom parent, int c, ColorCallback callback) {
        super((ContainerCustomGui)parent.m_6262_(), parent.inv, (Component)Component.m_237119_());
        this.parent = parent;
        this.callback = callback;
        this.f_97727_ = 170;
        this.f_97726_ = 130;
        this.color = c;
        CustomGuiTexturedRectWrapper bg = new CustomGuiTexturedRectWrapper();
        bg.setTexture("customnpcs:textures/gui/components.png").setSize(this.f_97726_, this.f_97727_);
        bg.setTextureOffset(0, 0).setRepeatingTexture(64, 64, 4);
        this.background = new CustomGuiTexturedRect(this, bg);
        this.textfield = new CustomGuiTextField(this, (CustomGuiTextFieldWrapper)new CustomGuiTextFieldWrapper(24, 35, 25, 60, 20).setCharacterType(2).setColor(this.color).setText(this.getColor()).setOnChange((gui, text) -> {
            this.color = Integer.parseInt(text.getText(), 16);
            callback.color(this.color);
            this.textfield.m_94202_(this.color);
        }));
        this.button = new CustomGuiButton(this, (CustomGuiButtonWrapper)((CustomGuiComponentWrapper)((Object)new CustomGuiButtonWrapper(66, "x", 107, 8, 20, 20).setOnPress((gui, button) -> {
            parent.subgui = null;
        }))).setDisablePackets());
        this.f_96541_ = Minecraft.m_91087_();
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.add(this.textfield);
        this.add(this.button);
        this.background.setTexture(colorgui);
        this.colorX = this.f_97735_ + 4;
        this.colorY = this.f_97736_ + 50;
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int par1, int limbSwingAmount, float par3) {
        super.m_88315_(graphics, par1, limbSwingAmount, par3);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)colorPicker);
        graphics.m_280218_(colorPicker, this.colorX, this.colorY, 0, 0, 120, 120);
    }

    @Override
    public boolean m_6375_(double i, double j, int k) {
        super.m_6375_(i, j, k);
        if (i < (double)this.colorX || i > (double)(this.colorX + 120) || j < (double)this.colorY || j > (double)(this.colorY + 120)) {
            return false;
        }
        Resource resource = this.f_96541_.m_91098_().m_213713_(colorPicker).orElse(null);
        if (resource != null) {
            try (InputStream stream = resource.m_215507_();){
                BufferedImage bufferedimage = ImageIO.read(stream);
                int color = bufferedimage.getRGB((int)(i - (double)this.f_97735_ - 4.0) * 4, (int)(j - (double)this.f_97736_ - 50.0) * 4) & 0xFFFFFF;
                if (color != 0) {
                    this.color = color;
                    this.callback.color(color);
                    this.textfield.m_94202_(color);
                    this.textfield.m_94144_(this.getColor());
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return true;
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        try {
            this.color = Integer.parseInt(textfield.m_94155_(), 16);
        }
        catch (NumberFormatException e) {
            this.color = 0;
        }
        this.callback.color(this.color);
        textfield.m_94202_(this.color);
    }

    public String getColor() {
        Object str = Integer.toHexString(this.color);
        while (((String)str).length() < 6) {
            str = "0" + (String)str;
        }
        return str;
    }

    public static interface ColorCallback {
        public void color(int var1);
    }
}

