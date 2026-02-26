/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ShaderInstance
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.server.packs.resources.ResourceManagerReloadListener
 *  net.minecraft.server.packs.resources.ResourceProvider
 */
package noppes.npcs.client;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.server.packs.resources.ResourceProvider;
import noppes.npcs.client.gui.select.GuiTextureSelection;
import noppes.npcs.client.parts.MpmPartReader;
import noppes.npcs.shared.client.model.util.CustomRenderStates;
import noppes.npcs.shared.client.util.TextureCache;

public class CustomNpcResourceListener
implements ResourceManagerReloadListener {
    private static int DefaultTextColor = 0x404040;
    private static boolean init = false;

    public static int getDefaultTextColor() {
        if (!init) {
            try {
                CustomNpcResourceListener.setDefaultTextColor(Integer.parseInt(I18n.m_118938_((String)"customnpcs.defaultTextColor", (Object[])new Object[0]), 16));
            }
            catch (NumberFormatException e) {
                CustomNpcResourceListener.setDefaultTextColor(0x404040);
            }
            init = true;
        }
        return DefaultTextColor;
    }

    public static void setDefaultTextColor(int defaultTextColor) {
        DefaultTextColor = defaultTextColor;
    }

    public void m_6213_(ResourceManager manager) {
        GuiTextureSelection.clear();
        MpmPartReader.reload();
        RenderSystem.recordRenderCall(() -> {
            try {
                CustomRenderStates.posTexNormalShader = new ShaderInstance((ResourceProvider)manager, "moreplayermodels:position_tex_normal", CustomRenderStates.POS_TEX_NORMAL);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void createTextureCache() {
        this.enlargeTexture("acacia_planks");
        this.enlargeTexture("birch_planks");
        this.enlargeTexture("crimson_planks");
        this.enlargeTexture("dark_oak_planks");
        this.enlargeTexture("jungle_planks");
        this.enlargeTexture("oak_planks");
        this.enlargeTexture("spruce_planks");
        this.enlargeTexture("warped_planks");
        this.enlargeTexture("iron_block");
        this.enlargeTexture("diamond_block");
        this.enlargeTexture("stone");
        this.enlargeTexture("gold_block");
        this.enlargeTexture("white_wool");
    }

    private void enlargeTexture(String texture) {
        ResourceLocation location;
        TextureManager manager = Minecraft.m_91087_().m_91097_();
        Object ob = manager.m_118506_(location = new ResourceLocation("customnpcs:textures/cache/" + texture + ".png"));
        if (!(ob instanceof TextureCache)) {
            ob = new TextureCache(location, new ResourceLocation("textures/block/" + texture + ".png"));
            manager.m_118495_(location, ob);
        }
    }
}

