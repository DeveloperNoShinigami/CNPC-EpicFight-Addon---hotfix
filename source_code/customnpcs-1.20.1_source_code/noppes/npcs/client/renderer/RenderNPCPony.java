/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.client.model.ModelPony;
import noppes.npcs.client.model.ModelPonyArmor;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.EntityNpcPony;

public class RenderNPCPony<T extends EntityNpcPony, M extends ModelPony<T>>
extends RenderNPCInterface<T, M> {
    private ModelPony modelBipedMain;
    private ModelPonyArmor modelArmorChestplate;
    private ModelPonyArmor modelArmor;

    public RenderNPCPony(EntityRendererProvider.Context manager, M model) {
        super(manager, model, 0.5f);
        this.modelBipedMain = model;
        this.modelArmorChestplate = new ModelPonyArmor(1.0f);
        this.modelArmor = new ModelPonyArmor(0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(T pony) {
        Resource resource;
        boolean check = ((EntityNpcPony)((Object)pony)).textureLocation == null || ((EntityNpcPony)((Object)pony)).textureLocation != ((EntityNpcPony)((Object)pony)).checked;
        ResourceLocation loc = super.getTextureLocation(pony);
        if (check && (resource = (Resource)Minecraft.m_91087_().m_91098_().m_213713_(loc).orElse(null)) != null) {
            try {
                BufferedImage bufferedimage = ImageIO.read(resource.m_215507_());
                ((EntityNpcPony)((Object)pony)).isPegasus = false;
                ((EntityNpcPony)((Object)pony)).isUnicorn = false;
                Color color = new Color(bufferedimage.getRGB(0, 0), true);
                Color color1 = new Color(249, 177, 49, 255);
                Color color2 = new Color(136, 202, 240, 255);
                Color color3 = new Color(209, 159, 228, 255);
                Color color4 = new Color(254, 249, 252, 255);
                if (color.equals(color1)) {
                    // empty if block
                }
                if (color.equals(color2)) {
                    ((EntityNpcPony)((Object)pony)).isPegasus = true;
                }
                if (color.equals(color3)) {
                    ((EntityNpcPony)((Object)pony)).isUnicorn = true;
                }
                if (color.equals(color4)) {
                    ((EntityNpcPony)((Object)pony)).isPegasus = true;
                    ((EntityNpcPony)((Object)pony)).isUnicorn = true;
                }
                ((EntityNpcPony)((Object)pony)).checked = loc;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return loc;
    }

    @Override
    public void render(T pony, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        ItemStack itemstack = ((EntityNPCInterface)((Object)pony)).m_21205_();
        this.modelBipedMain.heldItemRight = itemstack == null ? 0 : 1;
        this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight;
        this.modelArmorChestplate.heldItemRight = this.modelBipedMain.heldItemRight;
        this.modelArmor.isSneak = this.modelBipedMain.isSneak = ((EntityNPCInterface)((Object)pony)).m_6047_();
        this.modelArmorChestplate.isSneak = this.modelBipedMain.isSneak;
        this.modelBipedMain.f_102609_ = false;
        this.modelArmor.f_102609_ = false;
        this.modelArmorChestplate.f_102609_ = false;
        this.modelArmor.isSleeping = this.modelBipedMain.isSleeping = ((EntityNPCInterface)((Object)pony)).m_5803_();
        this.modelArmorChestplate.isSleeping = this.modelBipedMain.isSleeping;
        this.modelArmor.isUnicorn = this.modelBipedMain.isUnicorn = ((EntityNpcPony)((Object)pony)).isUnicorn;
        this.modelArmorChestplate.isUnicorn = this.modelBipedMain.isUnicorn;
        this.modelArmor.isPegasus = this.modelBipedMain.isPegasus = ((EntityNpcPony)((Object)pony)).isPegasus;
        this.modelArmorChestplate.isPegasus = this.modelBipedMain.isPegasus;
        super.render(pony, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        this.modelBipedMain.aimedBow = false;
        this.modelArmor.aimedBow = false;
        this.modelArmorChestplate.aimedBow = false;
        this.modelBipedMain.f_102609_ = false;
        this.modelArmor.f_102609_ = false;
        this.modelArmorChestplate.f_102609_ = false;
        this.modelBipedMain.isSneak = false;
        this.modelArmor.isSneak = false;
        this.modelArmorChestplate.isSneak = false;
        this.modelBipedMain.heldItemRight = 0;
        this.modelArmor.heldItemRight = 0;
        this.modelArmorChestplate.heldItemRight = 0;
    }
}

