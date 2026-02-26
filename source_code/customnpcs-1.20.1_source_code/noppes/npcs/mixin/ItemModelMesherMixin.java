/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ItemModelShaper
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.registries.ForgeRegistries
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package noppes.npcs.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomItems;
import noppes.npcs.api.wrapper.ItemScriptedWrapper;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.items.ItemScripted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ItemModelShaper.class})
public class ItemModelMesherMixin {
    @Inject(at={@At(value="HEAD")}, method={"getItemModel*"}, cancellable=true)
    public void getModel(ItemStack item, CallbackInfoReturnable<BakedModel> cir) {
        if (item.m_41720_() == CustomItems.scripted_item) {
            BakedModel model;
            ItemScriptedWrapper si = (ItemScriptedWrapper)item.getCapability(ItemStackWrapper.ITEMSCRIPTEDDATA_CAPABILITY, null).orElse(null);
            if (si == null) {
                return;
            }
            ItemScripted i = null;
            if (si.texture != null) {
                i = (Item)ForgeRegistries.ITEMS.getValue(si.texture);
            }
            if (i == null) {
                i = CustomItems.scripted_item;
            }
            if ((model = Minecraft.m_91087_().m_91291_().m_115103_().m_109394_((Item)i)) != null) {
                cir.setReturnValue((Object)model);
                cir.cancel();
            }
        }
    }
}

