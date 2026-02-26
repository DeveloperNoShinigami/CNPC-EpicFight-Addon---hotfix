/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderDispatcher
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.gui.custom;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import noppes.npcs.ModelData;
import noppes.npcs.ModelEyeData;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.gui.subgui.AssetsGui;
import noppes.npcs.api.wrapper.gui.CustomGuiAssetsSelectorWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonListWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiEntityDisplayWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiScrollWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiSliderWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.api.wrapper.gui.GuiComponentsScrollableWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.components.CustomGuiEntityDisplay;
import noppes.npcs.client.gui.custom.components.CustomGuiScroll;
import noppes.npcs.client.gui.custom.components.CustomGuiSlider;
import noppes.npcs.client.gui.custom.components.CustomGuiTexturedRect;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.client.gui.model.GuiModelColor;
import noppes.npcs.client.layer.LayerParts;
import noppes.npcs.client.parts.ModelPartWrapper;
import noppes.npcs.client.parts.MpmPart;
import noppes.npcs.client.parts.MpmPartAbstractClient;
import noppes.npcs.client.parts.MpmPartData;
import noppes.npcs.client.parts.MpmPartEyes;
import noppes.npcs.client.parts.MpmPartReader;
import noppes.npcs.client.parts.PartBehaviorType;
import noppes.npcs.client.parts.PartRenderType;
import noppes.npcs.constants.BodyPart;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketCustomGuiParts;
import noppes.npcs.shared.client.gui.components.GuiColorButton;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.common.util.ColorUtil;
import noppes.npcs.shared.common.util.NaturalOrderComparator;
import noppes.npcs.shared.common.util.NopVector2i;
import noppes.npcs.shared.common.util.NopVector3f;

public class GuiCreationNewParts
extends AbstractWidget
implements IGuiComponent {
    private CustomGuiScroll scroll;
    private CustomGuiSlider slider;
    private CustomGuiEntityDisplay entity;
    private ModelData data;
    private ModelData renderData = new ModelData(null);
    private static String active = "";
    private static PlayerModel biped;
    private GuiCustom parent;
    private EntityCustomNpc npc;
    private Minecraft minecraft;
    private List<GuiMpmPart> guiParts = new ArrayList<GuiMpmPart>();
    public static final ResourceLocation buttonsResource;
    private static final ResourceLocation colorWheel;

    public GuiCreationNewParts(final GuiCustom parent, EntityCustomNpc npc) {
        super(0, 0, 420, 200, (Component)Component.m_237119_());
        this.npc = npc;
        this.parent = parent;
        this.data = npc.modelData;
        this.minecraft = Minecraft.m_91087_();
        String[] menus = (String[])MpmPartReader.PARTS.values().stream().map(p -> p.menu).sorted(new NaturalOrderComparator()).distinct().toArray(String[]::new);
        if (active.isEmpty()) {
            active = menus[0];
        }
        this.scroll = new CustomGuiScroll(parent, new CustomGuiScrollWrapper(10, 4, 24, 100, 210, menus));
        this.scroll.disabledSearch();
        this.scroll.listener = new ICustomScrollListener(){

            @Override
            public void scrollClicked(double i, double j, int k, GuiCustomScrollNop scroll) {
                if (scroll.getSelectedIndex() >= 0) {
                    active = scroll.getSelected();
                    for (GuiMpmPart part : GuiCreationNewParts.this.guiParts) {
                        parent.scrollingPanel.comps.removeComponent(part.getID());
                    }
                    GuiCreationNewParts.this.guiParts.clear();
                    parent.m_7856_();
                }
            }

            @Override
            public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
            }
        };
        CustomGuiEntityDisplayWrapper wrapper = new CustomGuiEntityDisplayWrapper(-2, npc.wrappedNPC, 106, 90);
        wrapper.setSize(68, 90);
        this.entity = new CustomGuiEntityDisplay(parent, wrapper);
        this.slider = new CustomGuiSlider(parent, (CustomGuiSliderWrapper)new CustomGuiSliderWrapper(-3, "", 106, 186, 68, 20).setMax(360.0f).setDecimals(0).setValue(180.0f).setOnChange((gui, slider) -> {
            this.entity.component.setRotation((int)slider.getValue() - 180);
            this.entity.init();
        })).disablePackets();
        biped = new PlayerModel(this.minecraft.m_167973_().m_171103_(ModelLayers.f_171162_), true);
    }

    public void openSubgui(GuiCustom parent, GuiCustom subgui) {
        subgui.m_6575_(this.minecraft, parent.f_96543_, parent.f_96544_);
        subgui.parent = parent;
        parent.subgui = subgui;
        if (subgui.guiWrapper != null) {
            subgui.background = new CustomGuiTexturedRect(subgui, (CustomGuiTexturedRectWrapper)subgui.guiWrapper.getBackgroundRect());
        }
        if (subgui.scrollingPanel.comps == null) {
            subgui.scrollingPanel.comps = new GuiComponentsScrollableWrapper(subgui.guiWrapper, null);
        }
    }

    @Override
    public int getID() {
        return -10;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.entity.f_93624_ = this.parent.subgui == null;
        this.m_88315_(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void m_7856_() {
        this.parent.add(this.scroll);
        this.parent.add(this.entity);
        this.parent.add(this.slider);
        ArrayList list = new ArrayList(MpmPartReader.PARTS.values().stream().sorted(Comparator.comparing(t -> t.id)).filter(t -> t.menu.equals(active) && t.parentId == null).collect(Collectors.toList()));
        this.scroll.setSelected(active);
        this.entity.setEntity((Entity)this.npc);
        if (this.guiParts.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                int column = i % 3;
                MpmPart part = (MpmPart)list.get(i);
                GuiMpmPart gui = new GuiMpmPart(this.parent, 80 + i, column * 70 + column, i / 3 * 70, part);
                this.guiParts.add(gui);
                this.parent.addPanel(gui);
                this.parent.scrollingPanel.comps.addComponent(new PartsWrapper(gui));
            }
        } else {
            for (int i = 0; i < this.guiParts.size(); ++i) {
                int column = i % 3;
                GuiMpmPart gui = this.guiParts.get(i);
                gui.m_252865_(column * 70 + column);
                gui.m_253211_(i / 3 * 70);
                this.parent.addPanel(gui);
            }
        }
        this.parent.scrollingPanel.setMaxSize(this.guiParts.stream().mapToInt(v -> v.m_252907_() + v.m_93694_()).max().orElse(0));
    }

    @Override
    public ICustomGuiComponent component() {
        return null;
    }

    protected void m_87963_(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {
    }

    public boolean m_6375_(double mouseX, double mouseY, int mouseButton) {
        return false;
    }

    public void m_88315_(GuiGraphics graphics, int i, int j, float f) {
    }

    protected void m_168797_(NarrationElementOutput p_259858_) {
    }

    public void save() {
        Packets.sendServer(new SPacketCustomGuiParts(this.data.save()));
    }

    public void openTextureSubgui(GuiCustom parent, MpmPartData data, MpmPart part) {
        TexturePart screen = new TexturePart(data, part);
        CustomGuiWrapper gui = screen.guiWrapper;
        gui.setBackgroundTexture("customnpcs:textures/gui/components.png");
        gui.setSize(310, 200);
        gui.getBackgroundRect().setTextureOffset(0, 0);
        gui.getBackgroundRect().setRepeatingTexture(64, 64, 4);
        ((CustomGuiComponentWrapper)((Object)gui.addButton(66, "x", 276, 4, 20, 20).setOnPress((gui2, button) -> screen.m_7379_()))).setDisablePackets();
        if (!part.disableCustomTextures) {
            gui.addLabel(21, "gui.playerskin", 4, 110, 10, 100);
            gui.addButtonList(22, 76, 105, 50, 20).setValues("gui.no", "gui.yes").setSelected(data.usePlayerSkin ? 1 : 0).setOnPress((gui2, button) -> {
                data.usePlayerSkin = ((CustomGuiButtonListWrapper)button).getSelected() == 1;
                gui2.getComponent(23).setVisible(!data.usePlayerSkin);
                gui2.getComponent(24).setVisible(!data.usePlayerSkin);
                gui2.getComponent(25).setVisible(!data.usePlayerSkin);
                gui2.getComponent(26).setVisible(!data.usePlayerSkin);
                gui2.getComponent(27).setVisible(!data.usePlayerSkin);
                this.data.refreshParts();
                this.save();
                screen.m_7856_();
            }).setDisablePackets();
            gui.addLabel(23, "gui.texture", 4, 130, 10, 100).setVisible(!data.usePlayerSkin);
            ResourceLocation loc = data.getDefaultTexture();
            CustomGuiTextFieldWrapper tf = (CustomGuiTextFieldWrapper)((CustomGuiComponentWrapper)((Object)gui.addTextField(24, 4, 140, 220, 20).setText(loc == null ? "" : loc.toString()).setOnFocusLost((gui2, text) -> data.setTexture(text.getText())))).setVisible(!data.usePlayerSkin).setDisablePackets();
            ((CustomGuiComponentWrapper)((Object)gui.addButton(25, "gui.select", 226, 140, 80, 20).setOnPress((gui2, button) -> this.openSubgui(screen, this.openTextureBasic(data.getDefaultTexture() == null ? "" : data.getDefaultTexture().toString(), resource -> {
                data.setTexture(resource);
                tf.setText(resource);
                this.data.refreshParts();
                this.save();
                screen.m_7856_();
            }))))).setVisible(!data.usePlayerSkin).setDisablePackets();
            gui.addLabel(26, "config.skinurl", 4, 168, 10, 100).setVisible(!data.usePlayerSkin);
            ((CustomGuiComponentWrapper)((Object)gui.addTextField(27, 4, 178, 220, 20).setText(data.url).setOnFocusLost((gui2, text) -> {
                data.setUrl(text.getText());
                this.data.refreshParts();
                this.save();
                screen.m_7856_();
            }))).setVisible(!data.usePlayerSkin).setDisablePackets();
        }
        screen.setGuiWrapper(gui);
        this.openSubgui(parent, screen);
    }

    public GuiCustom openTextureBasic(String resource, AssetsGui.SelectionCallback callback) {
        GuiCustom screen = new GuiCustom((ContainerCustomGui)this.parent.m_6262_(), this.parent.inv, (Component)Component.m_237119_());
        CustomGuiWrapper gui = screen.guiWrapper = new CustomGuiWrapper(null);
        gui.setBackgroundTexture("customnpcs:textures/gui/components.png");
        gui.setSize(308, 214);
        gui.getBackgroundRect().setTextureOffset(0, 0);
        gui.getBackgroundRect().setRepeatingTexture(64, 64, 4);
        CustomGuiButtonWrapper b = gui.addTexturedButton(666, "X", 290, -4, 14, 14, "customnpcs:textures/gui/components.png", 0, 64);
        b.getTextureRect().setRepeatingTexture(64, 22, 3).setHoverText("gui.close");
        b.setTextureHoverOffset(22).setOnPress((guii, bb) -> screen.m_7379_());
        b.setDisablePackets();
        ((CustomGuiComponentWrapper)((Object)((CustomGuiAssetsSelectorWrapper)gui.addAssetsSelector(11, 4, 4, 300, 204).setSelected(resource).setOnPress((gui2, assets) -> screen.m_7379_())).setOnChange((gui2, assets) -> callback.call(assets.getSelected())))).setDisablePackets();
        screen.setGuiWrapper(gui);
        return screen;
    }

    public void openEyesSubgui(GuiCustom parent, ModelEyeData data, MpmPartEyes part) {
        EyesPart screen = new EyesPart(data, part);
        CustomGuiWrapper gui = screen.guiWrapper = new CustomGuiWrapper(null);
        gui.setBackgroundTexture("customnpcs:textures/gui/components.png");
        gui.setSize(310, 200);
        gui.getBackgroundRect().setTextureOffset(0, 0);
        gui.getBackgroundRect().setRepeatingTexture(64, 64, 4);
        int y = 8;
        gui.addLabel(21, "part.eyes", 56, y + 5, 10, 100);
        gui.addButtonList(22, 110, y, 110, 20).setValues("gui.playerskin", "gui.normal", "gui.texture").setSelected(data.skinType).setOnPress((gui2, button) -> {
            data.skinType = ((CustomGuiButtonListWrapper)button).getSelected();
            gui2.getComponent(23).setVisible(data.skinType == 1);
            gui2.getComponent(24).setVisible(data.skinType == 2);
            gui2.getComponent(25).setVisible(data.skinType == 2);
            gui2.getComponent(27).setVisible(data.glint || data.skinType == 1 || data.skinType == 2);
            screen.m_7856_();
        }).setDisablePackets();
        ((CustomGuiComponentWrapper)((Object)gui.addButton(23, "", 230, y, 50, 20).setOnPress((gui2, button) -> this.openSubgui(screen, new GuiModelColor(screen, ColorUtil.rgbToColor(data.color), color -> {
            data.color = ColorUtil.colorToRgb(color);
            screen.m_7856_();
        }))))).setVisible(data.skinType == 1).setDisablePackets();
        gui.addLabel(24, "config.skinurl", 56, (y += 25) + 5, 10, 100).setVisible(data.skinType == 2);
        ((CustomGuiComponentWrapper)((Object)gui.addTextField(25, 110, y, 195, 20).setText(data.url).setOnFocusLost((gui2, text) -> data.setUrl(text.getText())))).setVisible(data.skinType == 2).setDisablePackets();
        gui.addButtonList(26, 54, y += 25, 100, 20).setValues("gui.normal", "gui.big").setSelected(data.eyeSize).setOnPress((gui2, button) -> {
            data.eyeSize = ((CustomGuiButtonListWrapper)button).getSelected();
            screen.m_7856_();
        }).setDisablePackets();
        gui.addButtonList(27, 156, y, 100, 20).setValues("gui.normal", "gui.mirror").setSelected(data.mirror ? 1 : 0).setOnPress((gui2, button) -> {
            data.mirror = ((CustomGuiButtonListWrapper)button).getSelected() == 1;
            screen.m_7856_();
        }).setVisible(data.glint || data.skinType == 1 || data.skinType == 2).setDisablePackets();
        gui.addLabel(28, "eye.pupil", 4, y + 5, 10, 100);
        gui.addButtonList(29, 54, y += 25, 100, 20).setValues(I18n.m_118938_((String)"gui.down", (Object[])new Object[0]) + "x2", "gui.down", "gui.normal", "gui.up", I18n.m_118938_((String)"gui.up", (Object[])new Object[0]) + "x2").setSelected(data.eyePos.y + 2).setOnPress((gui2, button) -> {
            data.eyePos = new NopVector2i(data.eyePos.x, ((CustomGuiButtonListWrapper)button).getSelected() - 2);
        }).setDisablePackets();
        gui.addButtonList(30, 156, y, 100, 20).setValues("gui.inward", "gui.normal", "gui.outward").setSelected(data.eyePos.x + 1).setOnPress((gui2, button) -> {
            data.eyePos = new NopVector2i(((CustomGuiButtonListWrapper)button).getSelected() - 1, data.eyePos.y);
        }).setDisablePackets();
        gui.addLabel(31, "gui.position", 4, y + 5, 10, 100);
        gui.addButtonList(32, 54, y += 25, 50, 20).setValues("gui.no", "gui.yes").setSelected(data.glint ? 1 : 0).setOnPress((gui2, button) -> {
            data.glint = ((CustomGuiButtonListWrapper)button).getSelected() == 1;
            gui2.getComponent(27).setVisible(data.glint || data.skinType == 1 || data.skinType == 2);
        }).setDisablePackets();
        gui.addLabel(33, "eye.glint", 4, y + 5, 10, 100);
        ((CustomGuiComponentWrapper)((Object)gui.addButton(34, "", 162, y, 50, 20).setOnPress((gui2, button) -> this.openSubgui(screen, new GuiModelColor(screen, ColorUtil.rgbToColor(data.browColor), color -> {
            data.browColor = ColorUtil.colorToRgb(color);
            screen.m_7856_();
        }))))).setDisablePackets();
        gui.addButtonList(35, 214, y, 70, 20).setValues("gui.disabled", "1", "2", "3", "4", "5", "6", "7", "8").setSelected((int)(data.browThickness.y * 10.0f)).setOnPress((gui2, button) -> {
            data.browThickness = new NopVector3f(1.0f, (float)((CustomGuiButtonListWrapper)button).getSelected() / 10.0f, 1.0f);
        }).setDisablePackets();
        gui.addLabel(36, "eye.lash", 112, y + 5, 10, 100);
        gui.addButtonList(37, 54, y += 25, 50, 20).setValues("gui.no", "gui.yes").setSelected(data.disableBlink ? 0 : 1).setOnPress((gui2, button) -> {
            data.disableBlink = ((CustomGuiButtonListWrapper)button).getSelected() == 0;
            gui2.getComponent(39).setVisible(!data.disableBlink);
            gui2.getComponent(40).setVisible(!data.disableBlink);
            screen.m_7856_();
        }).setDisablePackets();
        gui.addLabel(38, "eye.blink", 4, y + 5, 10, 100);
        gui.addLabel(39, "eye.lid", 112, y + 5, 10, 100).setVisible(!data.disableBlink);
        ((CustomGuiComponentWrapper)((Object)gui.addButton(40, "", 162, y, 50, 20).setOnPress((gui2, button) -> this.openSubgui(screen, new GuiModelColor(screen, ColorUtil.rgbToColor(data.lidColor), color -> {
            data.lidColor = ColorUtil.colorToRgb(color);
            screen.m_7856_();
        }))))).setVisible(!data.disableBlink).setDisablePackets();
        ((CustomGuiComponentWrapper)((Object)gui.addButton(66, "x", 288, 4, 20, 20).setOnPress((gui2, button) -> screen.m_7379_()))).setDisablePackets();
        screen.setGuiWrapper(gui);
        this.openSubgui(parent, screen);
    }

    static {
        buttonsResource = new ResourceLocation("moreplayermodels", "textures/gui/arrowbuttons.png");
        colorWheel = new ResourceLocation("moreplayermodels", "textures/gui/colorwheel.png");
    }

    class GuiMpmPart
    extends AbstractWidget
    implements IGuiComponent {
        public static final int SIZE = 70;
        public boolean basic;
        private List<MpmPart> all;
        private MpmPart part;
        private MpmPartData data;
        private boolean selected;
        private GuiCustom parent;
        boolean colorPickerHovered;
        boolean infoHovered;
        boolean settingsHovered;
        boolean hoverL;
        boolean hoverR;
        int zPos;
        int id;

        public GuiMpmPart(GuiCustom parent, int id, int x, int y, MpmPart part) {
            super(x, y, 70, 70, (Component)Component.m_237119_());
            this.basic = false;
            this.all = new ArrayList<MpmPart>();
            this.selected = true;
            this.colorPickerHovered = false;
            this.infoHovered = false;
            this.settingsHovered = false;
            this.hoverL = false;
            this.hoverR = false;
            this.zPos = 0;
            this.parent = parent;
            this.id = id;
            this.part = part;
            this.all.add(part);
            for (Map.Entry<ResourceLocation, MpmPart> entry : MpmPartReader.PARTS.entrySet()) {
                if (entry.getValue().parentId == null || !entry.getValue().parentId.equals((Object)part.id)) continue;
                this.all.add(entry.getValue());
            }
            for (MpmPart p : this.all) {
                this.data = GuiCreationNewParts.this.data.mpmParts.stream().filter(t -> t.partId.equals((Object)p.id)).findFirst().orElse(null);
                if (this.data == null) continue;
                this.part = p;
                break;
            }
            this.all = this.all.stream().sorted(Comparator.comparing(t -> t.id)).collect(Collectors.toList());
            if (this.data == null) {
                this.data = part.id.equals((Object)ModelEyeData.RESOURCE) || part.id.equals((Object)ModelEyeData.RESOURCE_RIGHT) || part.id.equals((Object)ModelEyeData.RESOURCE_LEFT) ? new ModelEyeData() : new MpmPartData();
                this.data.partId = part.id;
                this.data.usePlayerSkin = part.defaultUsePlayerSkins;
                this.selected = false;
            }
        }

        public void renderModel(GuiGraphics graphics, int xMouse, int yMouse, float tick) {
            int x1 = this.m_252754_();
            int x2 = this.m_252754_() + 70;
            int y1 = this.m_252907_();
            int y2 = this.m_252907_() + 70 - 1;
            graphics.m_280509_(x1, y1, x2, y2, -3750202);
            GuiCreationNewParts.this.renderData.mpmParts = GuiCreationNewParts.this.data.mpmParts;
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.m_85836_();
            posestack.m_85837_(0.0, 0.0, 100.0 + (double)this.zPos);
            posestack.m_85841_(1.0f, 1.0f, -1.0f);
            RenderSystem.applyModelViewMatrix();
            PoseStack matrixstack = new PoseStack();
            matrixstack.m_252880_((float)this.m_252754_(), (float)(this.m_252907_() - this.parent.scrollingPanel.comps.scrollAmount), 1.0f);
            matrixstack.m_85836_();
            EntityRenderDispatcher entityrenderermanager = GuiCreationNewParts.this.minecraft.m_91290_();
            entityrenderermanager.m_114468_(false);
            MultiBufferSource.BufferSource irendertypebuffer$impl = GuiCreationNewParts.this.minecraft.m_91269_().m_110104_();
            VertexConsumer ivertex = irendertypebuffer$impl.m_6299_(RenderType.m_110458_((ResourceLocation)GuiCreationNewParts.this.npc.textureLocation));
            Lighting.m_166384_();
            RenderSystem.runAsFancy(() -> {
                ModelPartWrapper modelPart;
                GuiCreationNewParts.biped.f_102814_.f_104207_ = !this.part.hiddenParts.contains((Object)BodyPart.LEFT_LEG) && !this.part.hiddenParts.contains((Object)BodyPart.LEGS);
                GuiCreationNewParts.biped.f_103376_.f_104207_ = GuiCreationNewParts.biped.f_103376_.f_104207_ && GuiCreationNewParts.biped.f_102814_.f_104207_;
                GuiCreationNewParts.biped.f_102813_.f_104207_ = !this.part.hiddenParts.contains((Object)BodyPart.RIGHT_LEG) && !this.part.hiddenParts.contains((Object)BodyPart.LEGS);
                GuiCreationNewParts.biped.f_103377_.f_104207_ = GuiCreationNewParts.biped.f_103377_.f_104207_ && GuiCreationNewParts.biped.f_102813_.f_104207_;
                GuiCreationNewParts.biped.f_102812_.f_104207_ = !this.part.hiddenParts.contains((Object)BodyPart.LEFT_ARM) && !this.part.hiddenParts.contains((Object)BodyPart.ARMS);
                GuiCreationNewParts.biped.f_103374_.f_104207_ = GuiCreationNewParts.biped.f_103374_.f_104207_ && GuiCreationNewParts.biped.f_102812_.f_104207_;
                GuiCreationNewParts.biped.f_102811_.f_104207_ = !this.part.hiddenParts.contains((Object)BodyPart.RIGHT_ARM) && !this.part.hiddenParts.contains((Object)BodyPart.ARMS);
                GuiCreationNewParts.biped.f_103375_.f_104207_ = GuiCreationNewParts.biped.f_103375_.f_104207_ && GuiCreationNewParts.biped.f_102811_.f_104207_;
                GuiCreationNewParts.biped.f_102810_.f_104207_ = !this.part.hiddenParts.contains((Object)BodyPart.BODY);
                GuiCreationNewParts.biped.f_103378_.f_104207_ = GuiCreationNewParts.biped.f_103378_.f_104207_ && GuiCreationNewParts.biped.f_102810_.f_104207_;
                GuiCreationNewParts.biped.f_102808_.f_104207_ = !this.part.hiddenParts.contains((Object)BodyPart.HEAD);
                boolean bl = GuiCreationNewParts.biped.f_102809_.f_104207_ = GuiCreationNewParts.biped.f_102809_.f_104207_ && GuiCreationNewParts.biped.f_102808_.f_104207_;
                if (this.part.bodyPart == BodyPart.HEAD) {
                    matrixstack.m_252880_(32.0f, 46.0f, 25.0f);
                    matrixstack.m_85841_(36.0f, 36.0f, 36.0f);
                    matrixstack.m_252781_(Axis.f_252529_.m_252961_(0.3926991f));
                    matrixstack.m_252781_(Axis.f_252436_.m_252961_((float)this.part.previewRotation * ((float)Math.PI / 180)));
                    GuiCreationNewParts.biped.f_102808_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                }
                if (this.part.bodyPart == BodyPart.LEGS) {
                    matrixstack.m_252880_(18.0f, 12.0f, 25.0f);
                    matrixstack.m_85841_(36.0f, 36.0f, 36.0f);
                    matrixstack.m_252781_(Axis.f_252529_.m_252961_(0.3926991f));
                    matrixstack.m_252781_(Axis.f_252436_.m_252961_((float)this.part.previewRotation * ((float)Math.PI / 180)));
                    GuiCreationNewParts.biped.f_102810_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                    if (this.part.animationType == PartBehaviorType.LEGS) {
                        modelPart = this.part.getPart("right_leg");
                        if (modelPart != null) {
                            modelPart.setRot(new NopVector3f(GuiCreationNewParts.biped.f_102813_.f_104203_, GuiCreationNewParts.biped.f_102813_.f_104204_, GuiCreationNewParts.biped.f_102813_.f_104205_));
                            modelPart.setPos(new NopVector3f(GuiCreationNewParts.biped.f_102813_.f_104200_, GuiCreationNewParts.biped.f_102813_.f_104201_, GuiCreationNewParts.biped.f_102813_.f_104202_));
                        }
                        if ((modelPart = this.part.getPart("left_leg")) != null) {
                            modelPart.setRot(new NopVector3f(GuiCreationNewParts.biped.f_102814_.f_104203_, GuiCreationNewParts.biped.f_102814_.f_104204_, GuiCreationNewParts.biped.f_102814_.f_104205_));
                            modelPart.setPos(new NopVector3f(GuiCreationNewParts.biped.f_102814_.f_104200_, GuiCreationNewParts.biped.f_102814_.f_104201_, GuiCreationNewParts.biped.f_102814_.f_104202_));
                        }
                    }
                    GuiCreationNewParts.biped.f_102813_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                    GuiCreationNewParts.biped.f_102814_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                }
                if (this.part.bodyPart == BodyPart.ARMS) {
                    matrixstack.m_252880_(18.0f, 12.0f, 25.0f);
                    matrixstack.m_85841_(36.0f, 36.0f, 36.0f);
                    matrixstack.m_252781_(Axis.f_252529_.m_252961_(0.3926991f));
                    matrixstack.m_252781_(Axis.f_252436_.m_252961_((float)this.part.previewRotation * ((float)Math.PI / 180)));
                    GuiCreationNewParts.biped.f_102810_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                    if (this.part.animationType == PartBehaviorType.ARMS) {
                        modelPart = this.part.getPart("right_arm");
                        if (modelPart != null) {
                            modelPart.setRot(new NopVector3f(GuiCreationNewParts.biped.f_102811_.f_104203_, GuiCreationNewParts.biped.f_102811_.f_104204_, GuiCreationNewParts.biped.f_102811_.f_104205_));
                            modelPart.setPos(new NopVector3f(GuiCreationNewParts.biped.f_102811_.f_104200_, GuiCreationNewParts.biped.f_102811_.f_104201_, GuiCreationNewParts.biped.f_102811_.f_104202_));
                        }
                        if ((modelPart = this.part.getPart("left_arm")) != null) {
                            modelPart.setRot(new NopVector3f(GuiCreationNewParts.biped.f_102812_.f_104203_, GuiCreationNewParts.biped.f_102812_.f_104204_, GuiCreationNewParts.biped.f_102812_.f_104205_));
                            modelPart.setPos(new NopVector3f(GuiCreationNewParts.biped.f_102812_.f_104200_, GuiCreationNewParts.biped.f_102812_.f_104201_, GuiCreationNewParts.biped.f_102812_.f_104202_));
                        }
                    }
                    GuiCreationNewParts.biped.f_102812_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                    GuiCreationNewParts.biped.f_102811_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                }
                if (this.part.bodyPart == BodyPart.BODY) {
                    matrixstack.m_252880_(18.0f, 18.0f, 25.0f);
                    matrixstack.m_85841_(36.0f, 36.0f, 36.0f);
                    matrixstack.m_252781_(Axis.f_252529_.m_252961_(0.3926991f));
                    matrixstack.m_252781_(Axis.f_252436_.m_252961_((float)this.part.previewRotation * ((float)Math.PI / 180)));
                    GuiCreationNewParts.biped.f_102810_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                }
                if (this.part.renderType != PartRenderType.NONE) {
                    MpmPartAbstractClient partc = (MpmPartAbstractClient)this.part;
                    partc.pos = NopVector3f.ZERO;
                    partc.rot = NopVector3f.ZERO;
                    LayerParts.renderPart(this.data, partc, matrixstack, (MultiBufferSource)irendertypebuffer$impl, 0xF000F0, GuiCreationNewParts.this.npc, (HumanoidModel)biped, GuiCreationNewParts.this.renderData);
                }
            });
            irendertypebuffer$impl.m_109911_();
            matrixstack.m_85849_();
            posestack.m_85849_();
            entityrenderermanager.m_114468_(true);
            RenderSystem.applyModelViewMatrix();
        }

        public void m_87963_(GuiGraphics graphics, int xMouse, int yMouse, float tick) {
            if (this.parent.subgui == null) {
                // empty if block
            }
        }

        public void renderIcons(GuiGraphics graphics, int xMouse, int yMouse, float tick) {
            int color = -1;
            if (!this.basic) {
                if (this.f_93622_) {
                    color = -65536;
                }
                int x1 = this.m_252754_();
                int x2 = this.m_252754_() + 70;
                int y1 = this.m_252907_();
                int y2 = this.m_252907_() + 70 - 1;
                graphics.m_280656_(x1, x2, y1, color);
                graphics.m_280656_(x1, x2, y2, color);
                graphics.m_280656_(x1, y1, y2, color);
                graphics.m_280656_(x2, y1, y2, color);
                x1 = this.m_252754_() + 70 - 16;
                x2 = this.m_252754_() + 70;
                y1 = this.m_252907_() + 1;
                y2 = this.m_252907_() + 70 - 1;
                graphics.m_280509_(x1, y1, x2, y2, -3750202);
                color = -1;
                x1 = this.m_252754_() + 70 - 14;
                x2 = this.m_252754_() + 70 - 2;
                y1 = this.m_252907_() + 2;
                y2 = this.m_252907_() + 14;
                graphics.m_280509_(x1, y1, x2, y2, -16777216);
                graphics.m_280656_(x1, x2, y1, color);
                graphics.m_280656_(x1, x2, y2, color);
                graphics.m_280656_(x1, y1, y2, color);
                graphics.m_280656_(x2, y1, y2, color);
                if (!this.part.isEnabled) {
                    graphics.m_280430_(GuiCreationNewParts.this.minecraft.f_91062_, (Component)Component.m_237113_((String)"X").m_130940_(ChatFormatting.BOLD), x1 + 4, y1 + 3, 0xFF0000);
                } else if (this.selected) {
                    char c = (char)Integer.parseInt("2713", 16);
                    graphics.m_280430_(GuiCreationNewParts.this.minecraft.f_91062_, (Component)Component.m_237113_((String)("" + c)).m_130940_(ChatFormatting.BOLD), x1 + 3, y1 + 2, 65280);
                }
            }
            int guiY = this.m_252907_() + 16;
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)colorWheel);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int size = 14;
            int x1 = this.m_252754_() + 70 - 15;
            int x2 = x1 + size;
            int y1 = guiY;
            int y2 = y1 + size;
            boolean bl = this.colorPickerHovered = xMouse >= x1 && yMouse >= y1 && xMouse < x2 && yMouse < y2;
            if (this.colorPickerHovered) {
                --x1;
                --y1;
                size = 16;
            }
            graphics.m_280398_(colorWheel, x1, y1, 0, 0.0f, 0.0f, size, size, size, size);
            guiY += 15;
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)buttonsResource);
            RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (this.all.size() > 1) {
                x1 = this.m_252754_() + 70 - 17;
                x2 = x1 + 6;
                y1 = guiY;
                y2 = y1 + 8;
                RenderSystem.setShaderTexture((int)0, (ResourceLocation)buttonsResource);
                RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                this.hoverL = xMouse >= x1 && yMouse >= y1 && xMouse < x2 && yMouse < y2;
                graphics.m_280218_(buttonsResource, x1, y1, 0, this.hoverL ? 76 : 60, 6, 8);
                String s = "" + this.all.indexOf(this.part);
                graphics.m_280488_(GuiCreationNewParts.this.minecraft.f_91062_, s, (int)((float)x1 + 9.5f - (float)GuiCreationNewParts.this.minecraft.f_91062_.m_92895_(s) / 2.0f), (int)((float)y1 + 0.5f), 0);
                RenderSystem.setShaderTexture((int)0, (ResourceLocation)buttonsResource);
                RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                x1 = this.m_252754_() + 70 - 5;
                x2 = x1 + 6;
                y1 = guiY;
                y2 = y1 + 8;
                this.hoverR = xMouse >= x1 && yMouse >= y1 && xMouse < x2 && yMouse < y2;
                graphics.m_280218_(buttonsResource, x1, y1, 6, this.hoverR ? 76 : 60, 6, 8);
                guiY += 11;
            }
            if (!this.basic) {
                if (this.selected) {
                    x1 = this.m_252754_() + 70 - 15;
                    x2 = x1 + 14;
                    y1 = guiY;
                    y2 = y1 + 14;
                    this.settingsHovered = xMouse >= x1 && yMouse >= y1 && xMouse < x2 && yMouse < y2;
                    graphics.m_280218_(buttonsResource, x1, y1, 0, this.settingsHovered ? 140 : 126, 14, 14);
                }
                size = 8;
                x1 = this.m_252754_() + 70 - 10;
                x2 = x1 + size;
                y1 = this.m_252907_() + 70 - 12;
                y2 = y1 + size;
                this.infoHovered = xMouse >= x1 && yMouse >= y1 && xMouse < x2 && yMouse < y2;
                MutableComponent text = Component.m_237113_((String)"i").m_130940_(ChatFormatting.BOLD);
                if (this.infoHovered) {
                    text = text.m_130940_(ChatFormatting.UNDERLINE);
                }
                graphics.m_280430_(GuiCreationNewParts.this.minecraft.f_91062_, (Component)text, x1 + 3, y1 + 2, 0);
            }
        }

        public boolean m_6375_(double mouseX, double mouseY, int mouseButton) {
            if (!super.m_93680_(mouseX, mouseY)) {
                return false;
            }
            if (this.colorPickerHovered) {
                GuiCreationNewParts.this.openSubgui(this.parent, new GuiModelColor(this.parent, this.data.getColor(), color -> {
                    this.data.setColor(color);
                    GuiCreationNewParts.this.save();
                }));
            } else if (this.hoverL) {
                int index = (this.all.indexOf(this.part) + this.all.size() - 1) % this.all.size();
                this.part = this.all.get(index);
                this.data.partId = this.part.id;
            } else if (this.hoverR) {
                int index = (this.all.indexOf(this.part) + 1) % this.all.size();
                this.part = this.all.get(index);
                this.data.partId = this.part.id;
            } else if (this.settingsHovered) {
                if (this.data instanceof ModelEyeData) {
                    GuiCreationNewParts.this.openEyesSubgui(this.parent, (ModelEyeData)this.data, (MpmPartEyes)this.part);
                } else {
                    GuiCreationNewParts.this.openTextureSubgui(this.parent, this.data, this.part);
                }
            } else if (this.part.isEnabled && !this.basic) {
                boolean bl = this.selected = !this.selected;
                if (this.selected) {
                    GuiCreationNewParts.this.data.mpmParts.add(this.data);
                } else {
                    GuiCreationNewParts.this.data.mpmParts.removeIf(t -> t.partId.equals((Object)this.data.partId));
                }
            }
            GuiCreationNewParts.this.data.refreshParts();
            GuiCreationNewParts.this.save();
            return true;
        }

        protected void m_168797_(NarrationElementOutput p_259858_) {
        }

        @Override
        public int getID() {
            return this.id;
        }

        @Override
        public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            if (this.parent.subgui == null) {
                this.renderModel(graphics, mouseX, mouseY, partialTicks);
            }
        }

        @Override
        public void onRenderPost(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            if (this.parent.subgui == null) {
                this.renderIcons(graphics, mouseX, mouseY, partialTicks);
                if (this.infoHovered) {
                    List<Component> text = Arrays.asList(Component.m_237115_((String)this.part.name), Component.m_237110_((String)"message.madeby", (Object[])new Object[]{this.part.author}));
                    if (!this.part.isEnabled) {
                        text = new ArrayList<Component>();
                        text.add((Component)Component.m_237110_((String)"gui.disabled", (Object[])new Object[]{this.part.author}));
                    }
                    this.parent.hoverText = text;
                }
            }
        }

        @Override
        public void m_7856_() {
        }

        @Override
        public ICustomGuiComponent component() {
            return null;
        }
    }

    public class PartsWrapper
    implements ICustomGuiComponent {
        private GuiMpmPart part;

        public PartsWrapper(GuiMpmPart part) {
            this.part = part;
        }

        @Override
        public int getID() {
            return this.part.getID();
        }

        @Override
        public ICustomGuiComponent setID(int id) {
            return this;
        }

        @Override
        public UUID getUniqueID() {
            return null;
        }

        @Override
        public int getPosX() {
            return this.part.m_252754_();
        }

        @Override
        public int getPosY() {
            return this.part.m_252907_();
        }

        @Override
        public ICustomGuiComponent setPos(int x, int y) {
            return this;
        }

        @Override
        public int getWidth() {
            return this.part.m_5711_();
        }

        @Override
        public int getHeight() {
            return this.part.m_93694_();
        }

        @Override
        public ICustomGuiComponent setSize(int width, int height) {
            return null;
        }

        @Override
        public boolean hasHoverText() {
            return false;
        }

        @Override
        public String[] getHoverText() {
            return new String[0];
        }

        @Override
        public ICustomGuiComponent setHoverText(String text) {
            return null;
        }

        @Override
        public ICustomGuiComponent setHoverText(String[] text) {
            return null;
        }

        @Override
        public ICustomGuiComponent setEnabled(boolean bo) {
            return this;
        }

        @Override
        public boolean getVisible() {
            return true;
        }

        @Override
        public ICustomGuiComponent setVisible(boolean bo) {
            return null;
        }

        @Override
        public boolean getEnabled() {
            return true;
        }

        @Override
        public int getType() {
            return -1;
        }
    }

    class TexturePart
    extends GuiCustom {
        private MpmPart part;
        private MpmPartData data;
        private GuiMpmPart partGui;

        public TexturePart(MpmPartData data, MpmPart part) {
            super((ContainerCustomGui)GuiCreationNewParts.this.parent.m_6262_(), GuiCreationNewParts.this.parent.inv, (Component)Component.m_237119_());
            this.data = data;
            this.part = part;
            this.partGui = new GuiMpmPart(this, 70, 2, 2, part);
            this.partGui.zPos = 250;
            this.partGui.basic = true;
            this.guiWrapper = new CustomGuiWrapper(null);
            this.guiWrapper.addComponent(new PartsWrapper(this.partGui));
        }

        @Override
        public void m_7856_() {
            super.m_7856_();
            this.add(this.partGui);
        }

        @Override
        public void m_7379_() {
            super.m_7379_();
            GuiCreationNewParts.this.save();
        }
    }

    class EyesPart
    extends GuiCustom {
        private MpmPartEyes part;
        private ModelEyeData data;

        public EyesPart(ModelEyeData data, MpmPartEyes part) {
            super((ContainerCustomGui)GuiCreationNewParts.this.parent.m_6262_(), GuiCreationNewParts.this.parent.inv, (Component)Component.m_237119_());
            this.data = data;
            this.part = part;
        }

        @Override
        public void m_7856_() {
            super.m_7856_();
            this.components.components.put(23, new GuiColorButton(this, (CustomGuiButtonWrapper)this.guiWrapper.getComponent(23), ColorUtil.rgbToColor(this.data.color)));
            this.components.components.put(34, new GuiColorButton(this, (CustomGuiButtonWrapper)this.guiWrapper.getComponent(34), ColorUtil.rgbToColor(this.data.browColor)));
            this.components.components.put(40, new GuiColorButton(this, (CustomGuiButtonWrapper)this.guiWrapper.getComponent(40), ColorUtil.rgbToColor(this.data.lidColor)));
        }

        public void m_280273_(GuiGraphics graphics) {
            super.m_280273_(graphics);
        }

        @Override
        public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            super.m_88315_(graphics, mouseX, mouseY, partialTicks);
            PoseStack posestack = RenderSystem.getModelViewStack();
            posestack.m_85836_();
            posestack.m_85837_((double)(this.f_97735_ + 10), (double)(this.f_97736_ + 10), 150.0);
            posestack.m_85841_(1.0f, 1.0f, -1.0f);
            RenderSystem.applyModelViewMatrix();
            PoseStack matrixstack = new PoseStack();
            matrixstack.m_85836_();
            EntityRenderDispatcher entityrenderermanager = this.f_96541_.m_91290_();
            entityrenderermanager.m_114468_(false);
            MultiBufferSource.BufferSource irendertypebuffer$impl = this.f_96541_.m_91269_().m_110104_();
            VertexConsumer ivertex = irendertypebuffer$impl.m_6299_(RenderType.m_110458_((ResourceLocation)GuiCreationNewParts.this.npc.textureLocation));
            Lighting.m_166384_();
            RenderSystem.runAsFancy(() -> {
                GuiCreationNewParts.biped.f_102810_.f_104207_ = !this.part.hiddenParts.contains((Object)BodyPart.BODY);
                GuiCreationNewParts.biped.f_103378_.f_104207_ = GuiCreationNewParts.biped.f_103378_.f_104207_ && GuiCreationNewParts.biped.f_102810_.f_104207_;
                GuiCreationNewParts.biped.f_102808_.f_104207_ = !this.part.hiddenParts.contains((Object)BodyPart.HEAD);
                GuiCreationNewParts.biped.f_102809_.f_104207_ = GuiCreationNewParts.biped.f_102809_.f_104207_ && GuiCreationNewParts.biped.f_102808_.f_104207_;
                matrixstack.m_252880_(19.0f, 43.0f, 25.0f);
                matrixstack.m_85841_(100.0f, 100.0f, 100.0f);
                GuiCreationNewParts.biped.f_102808_.m_104301_(matrixstack, ivertex, 0xF000F0, OverlayTexture.f_118083_);
                this.part.pos = NopVector3f.ZERO;
                this.part.rot = NopVector3f.ZERO;
                LayerParts.renderPart(this.data, this.part, matrixstack, (MultiBufferSource)irendertypebuffer$impl, 0xF000F0, GuiCreationNewParts.this.npc, (HumanoidModel)biped, GuiCreationNewParts.this.renderData);
            });
            irendertypebuffer$impl.m_109911_();
            matrixstack.m_85849_();
            posestack.m_85849_();
            entityrenderermanager.m_114468_(true);
            RenderSystem.applyModelViewMatrix();
        }

        @Override
        public void m_7379_() {
            super.m_7379_();
            GuiCreationNewParts.this.save();
        }
    }
}

