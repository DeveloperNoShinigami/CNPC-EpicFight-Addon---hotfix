/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.client.gui.player;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.data.RecipeCarpentry;
import noppes.npcs.shared.client.gui.components.GuiButtonNextPage;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

@OnlyIn(value=Dist.CLIENT)
public class GuiRecipes
extends GuiNPCInterface {
    private static final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/slot.png");
    private int page = 0;
    private boolean npcRecipes = true;
    private GuiLabel label;
    private GuiButtonNop left;
    private GuiButtonNop right;
    private List<Recipe> recipes = new ArrayList<Recipe>();

    public GuiRecipes() {
        this.imageHeight = 182;
        this.imageWidth = 256;
        this.setBackground("recipes.png");
        this.recipes.addAll(RecipeController.instance.anvilRecipes.values());
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(0, "Recipe List", this.guiLeft + 5, this.guiTop + 5));
        this.label = new GuiLabel(1, "", this.guiLeft + 5, this.guiTop + 168);
        this.addLabel(this.label);
        this.left = new GuiButtonNextPage((IGuiInterface)this, 1, this.guiLeft + 150, this.guiTop + 164, true, b -> {
            ++this.page;
            this.updateButton();
        });
        this.addButton(this.left);
        this.right = new GuiButtonNextPage((IGuiInterface)this, 2, this.guiLeft + 80, this.guiTop + 164, false, b -> {
            --this.page;
            this.updateButton();
        });
        this.addButton(this.right);
        this.updateButton();
    }

    private void updateButton() {
        this.right.f_93623_ = this.page > 0;
        this.right.f_93624_ = this.right.f_93623_;
        this.left.f_93623_ = this.page + 1 < Mth.m_14167_((float)((float)this.recipes.size() / 4.0f));
        this.left.f_93624_ = this.left.f_93623_;
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int xMouse, int yMouse, float f) {
        ItemStack item;
        int k;
        int j;
        Recipe irecipe;
        int index;
        int i;
        super.m_88315_(graphics, xMouse, yMouse, f);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)resource);
        this.label.m_93666_((Component)Component.m_237113_((String)(this.page + 1 + "/" + Mth.m_14167_((float)((float)this.recipes.size() / 4.0f)))));
        this.label.m_252865_(this.guiLeft + (256 - Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)this.label.m_6035_())) / 2);
        for (i = 0; i < 4 && (index = i + this.page * 4) < this.recipes.size(); ++i) {
            irecipe = this.recipes.get(index);
            if (irecipe.m_8043_(this.player.m_9236_().m_9598_()).m_41619_()) continue;
            int x = this.guiLeft + 5 + i / 2 * 126;
            int y = this.guiTop + 15 + i % 2 * 76;
            this.drawItem(graphics, irecipe.m_8043_(this.player.m_9236_().m_9598_()), x + 98, y + 28, xMouse, yMouse);
            if (!(irecipe instanceof RecipeCarpentry)) continue;
            RecipeCarpentry recipe = (RecipeCarpentry)irecipe;
            x += (72 - recipe.getRecipeWidth() * 18) / 2;
            y += (72 - recipe.getRecipeHeight() * 18) / 2;
            for (j = 0; j < recipe.getRecipeWidth(); ++j) {
                for (k = 0; k < recipe.getRecipeHeight(); ++k) {
                    RenderSystem.setShader(GameRenderer::m_172817_);
                    RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    RenderSystem.setShaderTexture((int)0, (ResourceLocation)resource);
                    graphics.m_280218_(resource, x + j * 18, y + k * 18, 0, 0, 18, 18);
                    item = recipe.getCraftingItem(j + k * recipe.getRecipeWidth());
                    if (item.m_41619_()) continue;
                    this.drawItem(graphics, item, x + j * 18 + 1, y + k * 18 + 1, xMouse, yMouse);
                }
            }
        }
        for (i = 0; i < 4 && (index = i + this.page * 4) < this.recipes.size(); ++i) {
            RecipeCarpentry recipe;
            irecipe = this.recipes.get(index);
            if (!(irecipe instanceof RecipeCarpentry) || (recipe = (RecipeCarpentry)irecipe).m_8043_(this.player.m_9236_().m_9598_()).m_41619_()) continue;
            int x = this.guiLeft + 5 + i / 2 * 126;
            int y = this.guiTop + 15 + i % 2 * 76;
            this.drawOverlay(graphics, recipe.m_8043_(this.player.m_9236_().m_9598_()), x + 98, y + 22, xMouse, yMouse);
            x += (72 - recipe.getRecipeWidth() * 18) / 2;
            y += (72 - recipe.getRecipeHeight() * 18) / 2;
            for (j = 0; j < recipe.getRecipeWidth(); ++j) {
                for (k = 0; k < recipe.getRecipeHeight(); ++k) {
                    item = recipe.getCraftingItem(j + k * recipe.getRecipeWidth());
                    if (item.m_41619_()) continue;
                    this.drawOverlay(graphics, item, x + j * 18 + 1, y + k * 18 + 1, xMouse, yMouse);
                }
            }
        }
    }

    private void drawItem(GuiGraphics graphics, ItemStack item, int x, int y, int xMouse, int yMouse) {
        graphics.m_280168_().m_85836_();
        graphics.m_280168_().m_85837_(0.0, 0.0, 100.0);
        graphics.m_280480_(item, x, y);
        graphics.m_280370_(this.f_96547_, item, x, y);
        graphics.m_280168_().m_85849_();
    }

    private void drawOverlay(GuiGraphics graphics, ItemStack item, int x, int y, int xMouse, int yMouse) {
        if (this.func_146978_c(x - this.guiLeft, y - this.guiTop, 16, 16, xMouse, yMouse)) {
            graphics.m_280153_(this.f_96547_, item, xMouse, yMouse);
        }
    }

    protected boolean func_146978_c(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_) {
        int k1 = this.guiLeft;
        int l1 = this.guiTop;
        return (p_146978_5_ -= k1) >= p_146978_1_ - 1 && p_146978_5_ < p_146978_1_ + p_146978_3_ + 1 && (p_146978_6_ -= l1) >= p_146978_2_ - 1 && p_146978_6_ < p_146978_2_ + p_146978_4_ + 1;
    }

    @Override
    public void save() {
    }
}

