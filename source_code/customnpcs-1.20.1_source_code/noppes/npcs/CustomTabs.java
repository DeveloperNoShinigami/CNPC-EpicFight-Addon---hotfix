/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.CreativeModeTabs
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.RegistryObject
 */
package noppes.npcs;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import noppes.npcs.CustomBlocks;
import noppes.npcs.CustomItems;

public class CustomTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create((ResourceKey)Registries.f_279569_, (String)"customnpcs");
    public static final RegistryObject<CreativeModeTab> CNPCS = CREATIVE_TABS.register("cnpcs", () -> CreativeModeTab.builder().m_257941_((Component)Component.m_237113_((String)"cnpcs")).m_257737_(() -> CustomItems.wand.m_7968_()).m_257501_((params, output) -> {
        output.m_246342_(CustomItems.wand.m_7968_());
        output.m_246342_(CustomItems.cloner.m_7968_());
        output.m_246342_(CustomItems.scripter.m_7968_());
        output.m_246342_(CustomItems.moving.m_7968_());
        output.m_246342_(CustomItems.mount.m_7968_());
        output.m_246342_(CustomItems.teleporter.m_7968_());
        output.m_246342_(CustomItems.scripted_item.m_7968_());
        output.m_246342_(CustomItems.nbt_book.m_7968_());
        output.m_246342_(CustomItems.soulstoneEmpty.m_7968_());
        output.m_246342_(CustomBlocks.redstone_item.m_7968_());
        output.m_246342_(CustomBlocks.waypoint_item.m_7968_());
        output.m_246342_(CustomBlocks.border_item.m_7968_());
        output.m_246342_(CustomBlocks.scripted_item.m_7968_());
        output.m_246342_(CustomBlocks.scripted_door_item.m_7968_());
        output.m_246342_(CustomBlocks.builder_item.m_7968_());
        output.m_246342_(CustomBlocks.copy_item.m_7968_());
        output.m_246342_(CustomBlocks.carpentry_item.m_7968_());
        output.m_246342_(CustomBlocks.mailbox_item.m_7968_());
        output.m_246342_(CustomBlocks.mailbox2_item.m_7968_());
        output.m_246342_(CustomBlocks.mailbox3_item.m_7968_());
    }).withTabsBefore(new ResourceKey[]{CreativeModeTabs.f_256731_}).m_257652_());
    public static final CreativeModeTab tab = new CreativeModeTab(CreativeModeTab.builder().m_257941_((Component)Component.m_237113_((String)"cnpcs"))){

        @OnlyIn(value=Dist.CLIENT)
        public ItemStack m_40787_() {
            return new ItemStack((ItemLike)CustomItems.wand);
        }
    };
}

