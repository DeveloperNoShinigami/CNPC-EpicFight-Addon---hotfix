/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 */
package noppes.npcs.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class SubGuiColorSelector
extends GuiBasic
implements ITextfieldListener {
    private static final ResourceLocation resource = new ResourceLocation("customnpcs:textures/gui/color.png");
    private int colorX;
    private int colorY;
    private GuiTextFieldNop textfield;
    public int color;

    public SubGuiColorSelector(int color) {
        this.imageWidth = 176;
        this.imageHeight = 222;
        this.color = color;
        this.setBackground("smallbg.png");
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.colorX = this.guiLeft + 30;
        this.colorY = this.guiTop + 50;
        this.textfield = new GuiTextFieldNop(0, (Screen)this, this.guiLeft + 53, this.guiTop + 20, 70, 20, this.getColor());
        this.addTextField(this.textfield);
        this.textfield.m_94202_(this.color);
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 112, this.guiTop + 198, 60, 20, "gui.done"));
    }

    public String getColor() {
        Object str = Integer.toHexString(this.color);
        while (((String)str).length() < 6) {
            str = "0" + (String)str;
        }
        return str;
    }

    @Override
    public boolean m_5534_(char c, int i) {
        String prev = this.textfield.m_94155_();
        super.m_5534_(c, i);
        String newText = this.textfield.m_94155_();
        if (newText.equals(prev)) {
            return false;
        }
        try {
            this.color = Integer.parseInt(this.textfield.m_94155_(), 16);
            this.textfield.m_94202_(this.color);
        }
        catch (NumberFormatException e) {
            this.textfield.m_94144_(prev);
        }
        return true;
    }

    @Override
    public void buttonEvent(GuiButtonNop btn) {
        if (btn.id == 66) {
            this.close();
        }
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)resource);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        graphics.m_280218_(resource, this.colorX, this.colorY, 0, 0, 120, 120);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean m_6375_(double i, double j, int k) {
        super.m_6375_(i, j, k);
        if (i < (double)this.colorX || i > (double)(this.colorX + 117) || j < (double)this.colorY || j > (double)(this.colorY + 117)) {
            return false;
        }
        InputStream stream = null;
        Resource iresource = this.f_96541_.m_91098_().m_213713_(resource).orElse(null);
        if (iresource != null) {
            try {
                stream = iresource.m_215507_();
                BufferedImage bufferedimage = ImageIO.read(stream);
                this.color = bufferedimage.getRGB((int)(i - (double)this.guiLeft - 30.0) * 4, (int)(j - (double)this.guiTop - 50.0) * 4) & 0xFFFFFF;
                this.textfield.m_94202_(this.color);
                this.textfield.m_94144_(this.getColor());
            }
            catch (IOException iOException) {
            }
            finally {
                if (stream != null) {
                    try {
                        stream.close();
                    }
                    catch (IOException iOException) {}
                }
            }
        }
        return true;
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        int color = 0;
        try {
            color = Integer.parseInt(textfield.m_94155_(), 16);
        }
        catch (NumberFormatException e) {
            color = 0;
        }
        this.color = color;
        textfield.m_94202_(color);
    }
}

