/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockSource
 *  net.minecraft.core.Direction
 *  net.minecraft.core.dispenser.DefaultDispenseItemBehavior
 *  net.minecraft.core.dispenser.DispenseItemBehavior
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.DispenserBlock
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.ObjectHolder
 *  net.minecraftforge.registries.RegisterEvent
 */
package noppes.npcs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegisterEvent;
import noppes.npcs.items.ItemMounter;
import noppes.npcs.items.ItemNbtBook;
import noppes.npcs.items.ItemNpcCloner;
import noppes.npcs.items.ItemNpcMovingPath;
import noppes.npcs.items.ItemNpcScripter;
import noppes.npcs.items.ItemNpcWand;
import noppes.npcs.items.ItemScripted;
import noppes.npcs.items.ItemSoulstoneEmpty;
import noppes.npcs.items.ItemSoulstoneFilled;
import noppes.npcs.items.ItemTeleporter;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid="customnpcs")
public class CustomItems {
    @ObjectHolder(registryName="item", value="customnpcs:npcwand")
    public static Item wand = null;
    @ObjectHolder(registryName="item", value="customnpcs:npcmobcloner")
    public static Item cloner = null;
    @ObjectHolder(registryName="item", value="customnpcs:npcscripter")
    public static Item scripter = null;
    @ObjectHolder(registryName="item", value="customnpcs:npcmovingpath")
    public static Item moving = null;
    @ObjectHolder(registryName="item", value="customnpcs:npcmounter")
    public static Item mount = null;
    @ObjectHolder(registryName="item", value="customnpcs:npcteleporter")
    public static Item teleporter = null;
    @ObjectHolder(registryName="item", value="customnpcs:scripted_item")
    public static ItemScripted scripted_item = null;
    @ObjectHolder(registryName="item", value="customnpcs:nbt_book")
    public static ItemNbtBook nbt_book = null;
    @ObjectHolder(registryName="item", value="customnpcs:npcsoulstoneempty")
    public static final Item soulstoneEmpty = null;
    @ObjectHolder(registryName="item", value="customnpcs:npcsoulstonefilled")
    public static final Item soulstoneFull = null;

    @SubscribeEvent
    public static void registerBlocks(RegisterEvent event) {
        if (event.getRegistryKey() == ForgeRegistries.Keys.ITEMS) {
            event.getForgeRegistry().register("customnpcs:npcwand", (Object)new ItemNpcWand());
            event.getForgeRegistry().register("customnpcs:npcmobcloner", (Object)new ItemNpcCloner());
            event.getForgeRegistry().register("customnpcs:npcscripter", (Object)new ItemNpcScripter());
            event.getForgeRegistry().register("customnpcs:npcmovingpath", (Object)new ItemNpcMovingPath());
            event.getForgeRegistry().register("customnpcs:npcmounter", (Object)new ItemMounter());
            event.getForgeRegistry().register("customnpcs:npcteleporter", (Object)new ItemTeleporter());
            event.getForgeRegistry().register("customnpcs:npcsoulstoneempty", (Object)new ItemSoulstoneEmpty());
            event.getForgeRegistry().register("customnpcs:npcsoulstonefilled", (Object)new ItemSoulstoneFilled());
            event.getForgeRegistry().register("customnpcs:scripted_item", (Object)new ItemScripted(new Item.Properties().m_41487_(1)));
            event.getForgeRegistry().register("customnpcs:nbt_book", (Object)new ItemNbtBook());
        }
    }

    public static void registerDispenser() {
        DispenserBlock.m_52672_((ItemLike)soulstoneFull, (DispenseItemBehavior)new DefaultDispenseItemBehavior(){

            public ItemStack m_7498_(BlockSource source, ItemStack item) {
                Direction enumfacing = (Direction)source.m_6414_().m_61143_((Property)DispenserBlock.f_52659_);
                double x = source.m_7096_() + (double)enumfacing.m_122429_();
                double z = source.m_7094_() + (double)enumfacing.m_122431_();
                ItemSoulstoneFilled.Spawn(null, item, (Level)source.m_7727_(), new BlockPos((int)x, (int)source.m_7098_(), (int)z));
                item.m_41620_(1);
                return item;
            }
        });
    }
}

