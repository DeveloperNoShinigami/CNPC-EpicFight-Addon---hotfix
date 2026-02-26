/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip
 *  net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
 *  net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner
 *  net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner
 *  net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.world.inventory.tooltip.TooltipComponent
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.client.ForgeHooksClient
 *  net.minecraftforge.client.event.RenderTooltipEvent$Color
 *  net.minecraftforge.client.event.RenderTooltipEvent$Pre
 *  org.joml.Vector2ic
 */
package noppes.npcs.client.gui.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderTooltipEvent;
import noppes.npcs.mixin.ClientTextTooltipMixin;
import org.joml.Vector2ic;

public class GuiTooltipUtils {
    private static ItemStack tooltipStack = ItemStack.f_41583_;

    public static void renderTooltip(GuiGraphics graphics, Font p_282308_, ItemStack p_282781_, int p_282687_, int p_282292_) {
        tooltipStack = p_282781_;
        GuiTooltipUtils.renderTooltip(graphics, p_282308_, Screen.m_280152_((Minecraft)Minecraft.m_91087_(), (ItemStack)p_282781_), p_282781_.m_150921_(), p_282687_, p_282292_);
        tooltipStack = ItemStack.f_41583_;
    }

    public static void renderTooltip(GuiGraphics graphics, Font font, List<Component> textComponents, Optional<TooltipComponent> tooltipComponent, ItemStack stack, int mouseX, int mouseY) {
        tooltipStack = stack;
        GuiTooltipUtils.renderTooltip(graphics, font, textComponents, tooltipComponent, mouseX, mouseY);
        tooltipStack = ItemStack.f_41583_;
    }

    public static void renderTooltip(GuiGraphics graphics, Font p_283128_, List<Component> p_282716_, Optional<TooltipComponent> p_281682_, int p_283678_, int p_281696_) {
        List list = ForgeHooksClient.gatherTooltipComponents((ItemStack)tooltipStack, p_282716_, p_281682_, (int)p_283678_, (int)graphics.m_280182_(), (int)graphics.m_280206_(), (Font)p_283128_);
        GuiTooltipUtils.renderTooltipInternal(graphics, p_283128_, list, p_283678_, p_281696_, DefaultTooltipPositioner.f_262752_);
    }

    public static void renderTooltip(GuiGraphics graphics, Font p_282269_, Component p_282572_, int p_282044_, int p_282545_) {
        GuiTooltipUtils.renderTooltip(graphics, p_282269_, List.of(p_282572_.m_7532_()), p_282044_, p_282545_);
    }

    public static void renderComponentTooltip(GuiGraphics graphics, Font p_282739_, List<Component> p_281832_, int p_282191_, int p_282446_) {
        List components = ForgeHooksClient.gatherTooltipComponents((ItemStack)tooltipStack, p_281832_, (int)p_282191_, (int)graphics.m_280182_(), (int)graphics.m_280206_(), (Font)p_282739_);
        GuiTooltipUtils.renderTooltipInternal(graphics, p_282739_, components, p_282191_, p_282446_, DefaultTooltipPositioner.f_262752_);
    }

    public static void renderComponentTooltip(GuiGraphics graphics, Font font, List<? extends FormattedText> tooltips, int mouseX, int mouseY, ItemStack stack) {
        tooltipStack = stack;
        List components = ForgeHooksClient.gatherTooltipComponents((ItemStack)stack, tooltips, (int)mouseX, (int)graphics.m_280182_(), (int)graphics.m_280206_(), (Font)font);
        GuiTooltipUtils.renderTooltipInternal(graphics, font, components, mouseX, mouseY, DefaultTooltipPositioner.f_262752_);
        tooltipStack = ItemStack.f_41583_;
    }

    public static void renderTooltip(GuiGraphics graphics, Font p_282192_, List<? extends FormattedCharSequence> p_282297_, int p_281680_, int p_283325_) {
        GuiTooltipUtils.renderTooltipInternal(graphics, p_282192_, p_282297_.stream().map(ClientTooltipComponent::m_169948_).collect(Collectors.toList()), p_281680_, p_283325_, DefaultTooltipPositioner.f_262752_);
    }

    public static void renderTooltip(GuiGraphics graphics, Font p_281627_, List<FormattedCharSequence> p_283313_, ClientTooltipPositioner p_283571_, int p_282367_, int p_282806_) {
        GuiTooltipUtils.renderTooltipInternal(graphics, p_281627_, p_283313_.stream().map(ClientTooltipComponent::m_169948_).collect(Collectors.toList()), p_282367_, p_282806_, p_283571_);
    }

    private static void renderTooltipInternal(GuiGraphics graphics, Font p_282675_, List<ClientTooltipComponent> p_282615_, int p_283230_, int p_283417_, ClientTooltipPositioner p_282442_) {
        if (!p_282615_.isEmpty()) {
            RenderTooltipEvent.Pre preEvent = ForgeHooksClient.onRenderTooltipPre((ItemStack)tooltipStack, (GuiGraphics)graphics, (int)p_283230_, (int)p_283417_, (int)graphics.m_280182_(), (int)graphics.m_280206_(), p_282615_, (Font)p_282675_, (ClientTooltipPositioner)p_282442_);
            if (preEvent.isCanceled()) {
                return;
            }
            int i = 0;
            int j = p_282615_.size() == 1 ? -2 : 0;
            for (ClientTooltipComponent clienttooltipcomponent : p_282615_) {
                int k = clienttooltipcomponent.m_142069_(preEvent.getFont());
                if (k > i) {
                    i = k;
                }
                j += clienttooltipcomponent.m_142103_();
            }
            int i2 = i;
            int j2 = j;
            Vector2ic vector2ic = p_282442_.m_262814_(graphics.m_280182_(), graphics.m_280206_(), preEvent.getX(), preEvent.getY(), i2, j2);
            int l = vector2ic.x();
            int i1 = vector2ic.y();
            graphics.m_280168_().m_85836_();
            int j1 = 400;
            graphics.m_280168_().m_252880_(0.0f, 0.0f, 3600.0f);
            Runnable runa = () -> {
                RenderTooltipEvent.Color colorEvent = ForgeHooksClient.onRenderTooltipColor((ItemStack)tooltipStack, (GuiGraphics)graphics, (int)l, (int)i1, (Font)preEvent.getFont(), (List)p_282615_);
                TooltipRenderUtil.renderTooltipBackground((GuiGraphics)graphics, (int)l, (int)i1, (int)i2, (int)j2, (int)400, (int)colorEvent.getBackgroundStart(), (int)colorEvent.getBackgroundEnd(), (int)colorEvent.getBorderStart(), (int)colorEvent.getBorderEnd());
            };
            graphics.m_286007_(runa);
            graphics.m_280168_().m_252880_(0.0f, 0.0f, 400.0f);
            int k1 = i1;
            for (int l1 = 0; l1 < p_282615_.size(); ++l1) {
                ClientTooltipComponent clienttooltipcomponent1 = p_282615_.get(l1);
                if (clienttooltipcomponent1 instanceof ClientTextTooltip) {
                    graphics.m_280649_(preEvent.getFont(), ((ClientTextTooltipMixin)clienttooltipcomponent1).getText(), l, k1, 0xFFFFFF, false);
                } else {
                    clienttooltipcomponent1.m_142440_(preEvent.getFont(), l, k1, graphics.m_280168_().m_85850_().m_252922_(), graphics.m_280091_());
                }
                k1 += clienttooltipcomponent1.m_142103_() + (l1 == 0 ? 2 : 0);
            }
            k1 = i1;
            for (int k2 = 0; k2 < p_282615_.size(); ++k2) {
                ClientTooltipComponent clienttooltipcomponent2 = p_282615_.get(k2);
                clienttooltipcomponent2.m_183452_(preEvent.getFont(), l, k1, graphics);
                k1 += clienttooltipcomponent2.m_142103_() + (k2 == 0 ? 2 : 0);
            }
            graphics.m_280168_().m_85849_();
        }
    }
}

