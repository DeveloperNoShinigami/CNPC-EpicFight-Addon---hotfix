/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs.client.gui.custom.components;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiAssetsSelectorWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiLabelWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.components.CustomGuiLabel;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketCustomGuiButton;
import noppes.npcs.packets.server.SPacketCustomGuiTextUpdate;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.client.util.AssetsFinder;

public class CustomGuiAssetsSelector
extends AbstractWidget
implements IGuiComponent {
    private String up = "..<" + I18n.m_118938_((String)"gui.up", (Object[])new Object[0]) + ">..";
    private GuiCustom parent;
    private CustomGuiAssetsSelectorWrapper component;
    private GuiCustomScrollNop folders;
    private GuiCustomScrollNop items;
    private CustomGuiLabel label;
    public int id;
    private static final HashMap<String, List<ResourceLocation>> domains = new HashMap();
    private static final HashMap<String, ResourceLocation> textures = new HashMap();
    private String location = "";
    private String path = "";
    private String selectedDomain;
    public ResourceLocation prevResource = null;
    public ResourceLocation selectedResource = null;

    public CustomGuiAssetsSelector(GuiCustom parent, final CustomGuiAssetsSelectorWrapper component) {
        super(component.getPosX(), component.getPosY(), component.getWidth(), component.getHeight(), (Component)Component.m_237119_());
        this.parent = parent;
        this.component = component;
        this.folders = new GuiCustomScrollNop((Screen)parent, 101);
        this.items = new GuiCustomScrollNop((Screen)parent, 102);
        this.label = new CustomGuiLabel(parent, (CustomGuiLabelWrapper)new CustomGuiLabelWrapper().setCentered(true));
        this.init();
        if (!component.getSelected().isEmpty()) {
            this.selectedResource = this.prevResource = new ResourceLocation(component.getSelected());
        }
        List<ResourceLocation> resources = AssetsFinder.find(component.getRoot(), "." + component.getFileType());
        for (ResourceLocation loc : resources) {
            domains.computeIfAbsent(loc.m_135827_(), k -> new ArrayList()).add(loc);
        }
        if (this.selectedResource != null && !this.selectedResource.m_135815_().isEmpty()) {
            this.selectedDomain = this.selectedResource.m_135827_();
            if (!domains.containsKey(this.selectedDomain)) {
                this.selectedDomain = null;
            }
            int i = this.selectedResource.m_135815_().lastIndexOf(47);
            this.location = this.path = this.selectedResource.m_135815_().substring(0, i + 1);
            i = this.path.lastIndexOf(47, this.path.length() - 2);
            if (i > 0) {
                this.location = this.path.substring(0, i + 1);
            }
            this.label.setText(this.selectedDomain + ":" + this.location);
        }
        this.setFolders();
        this.setItems();
        this.folders.listener = new ICustomScrollListener(){

            @Override
            public void scrollClicked(double x, double y, int k, GuiCustomScrollNop scroll) {
                if (scroll.getSelected().equals(CustomGuiAssetsSelector.this.up)) {
                    return;
                }
                CustomGuiAssetsSelector.this.path = CustomGuiAssetsSelector.this.location + scroll.getSelected() + "/";
                CustomGuiAssetsSelector.this.setItems();
            }

            @Override
            public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
                if (CustomGuiAssetsSelector.this.selectedDomain == null) {
                    CustomGuiAssetsSelector.this.selectedDomain = scroll.getSelected();
                    if (!component.getRoot().isEmpty()) {
                        CustomGuiAssetsSelector.this.path = CustomGuiAssetsSelector.this.location = component.getRoot() + "/";
                    }
                } else if (scroll.getSelected().equals(CustomGuiAssetsSelector.this.up)) {
                    int i = CustomGuiAssetsSelector.this.location.lastIndexOf(47, CustomGuiAssetsSelector.this.location.length() - 2);
                    if (i > 0) {
                        CustomGuiAssetsSelector.this.path = CustomGuiAssetsSelector.this.location;
                        CustomGuiAssetsSelector.this.location = CustomGuiAssetsSelector.this.location.substring(0, i + 1);
                    } else {
                        CustomGuiAssetsSelector.this.location = "";
                        CustomGuiAssetsSelector.this.path = "";
                    }
                    if (CustomGuiAssetsSelector.this.location.isEmpty()) {
                        CustomGuiAssetsSelector.this.selectedDomain = null;
                    }
                } else {
                    CustomGuiAssetsSelector.this.path = CustomGuiAssetsSelector.this.location = CustomGuiAssetsSelector.this.location + scroll.getSelected() + "/";
                }
                CustomGuiAssetsSelector.this.setFolders();
                CustomGuiAssetsSelector.this.setItems();
                CustomGuiAssetsSelector.this.label.setText(CustomGuiAssetsSelector.this.selectedDomain + ":" + CustomGuiAssetsSelector.this.location);
            }
        };
        this.items.listener = new ICustomScrollListener(){

            @Override
            public void scrollClicked(double i, double j, int k, GuiCustomScrollNop scroll) {
                CustomGuiAssetsSelector.this.selectedResource = textures.get(scroll.getSelected());
                component.setSelected(textures.get(scroll.getSelected()).toString());
                if (!component.disablePackets) {
                    Packets.sendServer(new SPacketCustomGuiTextUpdate(component.getUniqueID(), component.getSelected()));
                } else {
                    component.onChange(null);
                }
            }

            @Override
            public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
                if (!component.disablePackets) {
                    Packets.sendServer(new SPacketCustomGuiButton(component.getUniqueID()));
                } else {
                    component.onPress(null);
                }
            }
        };
    }

    public void init() {
        this.id = this.component.getID();
        this.m_252865_(this.component.getPosX());
        this.m_253211_(this.component.getPosY());
        this.folders.guiTop = this.items.guiTop = this.m_252907_() + 10;
        this.m_93674_(this.component.getWidth());
        this.setHeight(this.component.getHeight());
        this.folders.setSize(this.component.getWidth() / 2 - 1, this.component.getHeight() - 10);
        this.items.setSize(this.component.getWidth() / 2 - 1, this.component.getHeight() - 10);
        this.folders.guiLeft = this.m_252754_();
        this.items.guiLeft = this.m_252754_() + this.component.getWidth() / 2 + 1;
        this.label.m_93674_(this.component.getWidth());
        this.label.m_252865_(this.m_252754_());
        this.label.m_253211_(this.m_252907_());
        this.label.setHeight(10);
        if (!this.component.getSelected().isEmpty()) {
            this.selectedResource = new ResourceLocation(this.component.getSelected());
        }
    }

    private void setFolders() {
        if (this.selectedDomain == null) {
            this.folders.setList(Lists.newArrayList(domains.keySet()));
            if (this.selectedResource != null) {
                this.selectedDomain = this.selectedResource.m_135827_();
                this.folders.setSelected(this.selectedDomain);
            }
            return;
        }
        ArrayList<String> list = new ArrayList<String>();
        list.add(this.up);
        for (ResourceLocation td : domains.get(this.selectedDomain)) {
            String path;
            int i;
            String fullPath = td.m_135815_();
            if (fullPath.indexOf(47) >= 0) {
                fullPath = fullPath.substring(0, fullPath.lastIndexOf(47) + 1);
            }
            if (!this.location.isEmpty() && (!fullPath.startsWith(this.location) || fullPath.equals(this.location)) || (i = (path = fullPath.substring(this.location.length())).indexOf(47)) < 0 || (path = path.substring(0, i)).isEmpty() || list.contains(path)) continue;
            list.add(path);
        }
        this.folders.clearSelection();
        this.folders.setList(list);
        if (this.selectedResource != null && this.selectedResource.m_135815_().startsWith(this.location) && !this.location.equals(this.path)) {
            this.folders.setSelected(this.path.substring(this.location.length(), this.path.length() - 1));
            this.folders.scrollTo(this.folders.getSelected());
        }
    }

    private void setItems() {
        if (this.selectedDomain == null) {
            return;
        }
        textures.clear();
        List<ResourceLocation> data = domains.get(this.selectedDomain);
        ArrayList<String> list = new ArrayList<String>();
        for (ResourceLocation td : data) {
            String name = td.m_135815_();
            String path = td.m_135815_();
            if (name.indexOf(47) >= 0) {
                name = name.substring(name.lastIndexOf(47) + 1);
                path = path.substring(0, path.lastIndexOf(47) + 1);
            }
            if (!path.equals(this.path) || list.contains(name)) continue;
            list.add(name);
            textures.put(name, td);
        }
        this.items.clearSelection();
        this.items.setList(list);
        if (this.selectedResource != null) {
            int i = this.selectedResource.m_135815_().lastIndexOf(47);
            String name = this.selectedResource.m_135815_().substring(i + 1);
            String path = this.selectedResource.m_135815_().substring(0, i + 1);
            if (path.equals(this.path)) {
                this.items.setSelected(name);
            }
        }
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    }

    @Override
    public void onRenderPost(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        boolean hovered;
        if (!this.f_93624_) {
            return;
        }
        this.label.onRender(graphics, mouseX, mouseY, partialTicks);
        this.folders.m_88315_(graphics, mouseX, mouseY, partialTicks);
        this.items.m_88315_(graphics, mouseX, mouseY, partialTicks);
        boolean bl = hovered = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        if (hovered && this.component.hasHoverText()) {
            this.parent.hoverText = this.component.getHoverTextList();
        }
    }

    public boolean m_6050_(double mouseX, double mouseY, double mouseScrolled) {
        return this.folders.m_6050_(mouseX, mouseY, mouseScrolled) || this.items.m_6050_(mouseX, mouseY, mouseScrolled);
    }

    protected void m_87963_(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        return this.folders.m_6375_(mouseX, mouseY, button) || this.items.m_6375_(mouseX, mouseY, button);
    }

    public boolean m_7933_(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        return this.folders.m_7933_(p_231046_1_, p_231046_2_, p_231046_3_) || this.items.m_7933_(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    public boolean m_5534_(char p_231042_1_, int p_231042_2_) {
        return this.folders.m_5534_(p_231042_1_, p_231042_2_) || this.items.m_5534_(p_231042_1_, p_231042_2_);
    }

    public void m_168797_(NarrationElementOutput p_169152_) {
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }
}

