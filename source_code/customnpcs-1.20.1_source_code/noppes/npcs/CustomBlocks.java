/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL$TypeReference
 *  net.minecraft.Util
 *  net.minecraft.util.datafix.fixes.References
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier
 *  net.minecraft.world.level.block.entity.BlockEntityType$Builder
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 *  net.minecraftforge.registries.ObjectHolder
 *  net.minecraftforge.registries.RegisterEvent
 */
package noppes.npcs;

import com.mojang.datafixers.DSL;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegisterEvent;
import noppes.npcs.blocks.BlockBorder;
import noppes.npcs.blocks.BlockBuilder;
import noppes.npcs.blocks.BlockCarpentryBench;
import noppes.npcs.blocks.BlockCopy;
import noppes.npcs.blocks.BlockMailbox;
import noppes.npcs.blocks.BlockNpcRedstone;
import noppes.npcs.blocks.BlockScripted;
import noppes.npcs.blocks.BlockScriptedDoor;
import noppes.npcs.blocks.BlockWaypoint;
import noppes.npcs.blocks.tiles.TileBlockAnvil;
import noppes.npcs.blocks.tiles.TileBorder;
import noppes.npcs.blocks.tiles.TileBuilder;
import noppes.npcs.blocks.tiles.TileCopy;
import noppes.npcs.blocks.tiles.TileMailbox;
import noppes.npcs.blocks.tiles.TileRedstoneBlock;
import noppes.npcs.blocks.tiles.TileScripted;
import noppes.npcs.blocks.tiles.TileScriptedDoor;
import noppes.npcs.blocks.tiles.TileWaypoint;
import noppes.npcs.items.ItemNpcBlock;
import noppes.npcs.items.ItemScriptedDoor;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, modid="customnpcs")
public class CustomBlocks {
    @ObjectHolder(registryName="block", value="customnpcs:npcredstoneblock")
    public static Block redstone;
    @ObjectHolder(registryName="item", value="customnpcs:npcredstoneblock")
    public static Item redstone_item;
    @ObjectHolder(registryName="block", value="customnpcs:npcmailbox")
    public static Block mailbox;
    @ObjectHolder(registryName="item", value="customnpcs:npcmailbox")
    public static Item mailbox_item;
    @ObjectHolder(registryName="block", value="customnpcs:npcmailbox2")
    public static Block mailbox2;
    @ObjectHolder(registryName="item", value="customnpcs:npcmailbox2")
    public static Item mailbox2_item;
    @ObjectHolder(registryName="block", value="customnpcs:npcmailbox3")
    public static Block mailbox3;
    @ObjectHolder(registryName="item", value="customnpcs:npcmailbox3")
    public static Item mailbox3_item;
    @ObjectHolder(registryName="block", value="customnpcs:npcwaypoint")
    public static Block waypoint;
    @ObjectHolder(registryName="item", value="customnpcs:npcwaypoint")
    public static Item waypoint_item;
    @ObjectHolder(registryName="block", value="customnpcs:npcborder")
    public static Block border;
    @ObjectHolder(registryName="item", value="customnpcs:npcborder")
    public static Item border_item;
    @ObjectHolder(registryName="block", value="customnpcs:npcscripted")
    public static Block scripted;
    @ObjectHolder(registryName="item", value="customnpcs:npcscripted")
    public static Item scripted_item;
    @ObjectHolder(registryName="block", value="customnpcs:npcscripteddoor")
    public static Block scripted_door;
    @ObjectHolder(registryName="item", value="customnpcs:npcscripteddoortool")
    public static Item scripted_door_item;
    @ObjectHolder(registryName="block", value="customnpcs:npcbuilderblock")
    public static Block builder;
    @ObjectHolder(registryName="item", value="customnpcs:npcbuilderblock")
    public static Item builder_item;
    @ObjectHolder(registryName="block", value="customnpcs:npccopyblock")
    public static Block copy;
    @ObjectHolder(registryName="item", value="customnpcs:npccopyblock")
    public static Item copy_item;
    @ObjectHolder(registryName="block", value="customnpcs:npccarpentybench")
    public static Block carpenty;
    @ObjectHolder(registryName="item", value="customnpcs:npccarpentybench")
    public static Item carpentry_item;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tileblockanvil")
    public static BlockEntityType<TileBlockAnvil> tile_anvil;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tilenpcborder")
    public static BlockEntityType<TileBorder> tile_border;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tilenpcbuilder")
    public static BlockEntityType<TileBuilder> tile_builder;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tilenpccopy")
    public static BlockEntityType<TileCopy> tile_copy;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tilemailbox")
    public static BlockEntityType<TileMailbox> tile_mailbox;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tileredstoneblock")
    public static BlockEntityType<TileRedstoneBlock> tile_redstoneblock;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tilenpcscripted")
    public static BlockEntityType<TileScripted> tile_scripted;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tilenpcscripteddoor")
    public static BlockEntityType<TileScriptedDoor> tile_scripteddoor;
    @ObjectHolder(registryName="block_entity_type", value="customnpcs:tilewaypoint")
    public static BlockEntityType<TileWaypoint> tile_waypoint;

    @SubscribeEvent
    public static void registerBlocks(RegisterEvent event) {
        if (event.getRegistryKey() == ForgeRegistries.Keys.BLOCKS) {
            event.getForgeRegistry().register("customnpcs:npcredstoneblock", (Object)new BlockNpcRedstone());
            event.getForgeRegistry().register("customnpcs:npcmailbox", (Object)new BlockMailbox(0));
            event.getForgeRegistry().register("customnpcs:npcmailbox2", (Object)new BlockMailbox(1));
            event.getForgeRegistry().register("customnpcs:npcmailbox3", (Object)new BlockMailbox(2));
            event.getForgeRegistry().register("customnpcs:npcwaypoint", (Object)new BlockWaypoint());
            event.getForgeRegistry().register("customnpcs:npcborder", (Object)new BlockBorder());
            event.getForgeRegistry().register("customnpcs:npcscripted", (Object)new BlockScripted());
            event.getForgeRegistry().register("customnpcs:npcscripteddoor", (Object)new BlockScriptedDoor());
            event.getForgeRegistry().register("customnpcs:npcbuilderblock", (Object)new BlockBuilder());
            event.getForgeRegistry().register("customnpcs:npccopyblock", (Object)new BlockCopy());
            event.getForgeRegistry().register("customnpcs:npccarpentybench", (Object)new BlockCarpentryBench());
        }
        if (event.getRegistryKey() == ForgeRegistries.Keys.ITEMS) {
            event.getForgeRegistry().register("customnpcs:npcredstoneblock", (Object)CustomBlocks.createItem(redstone));
            event.getForgeRegistry().register("customnpcs:npcmailbox", (Object)CustomBlocks.createItem(mailbox));
            event.getForgeRegistry().register("customnpcs:npcmailbox2", (Object)CustomBlocks.createItem(mailbox2));
            event.getForgeRegistry().register("customnpcs:npcmailbox3", (Object)CustomBlocks.createItem(mailbox3));
            event.getForgeRegistry().register("customnpcs:npcwaypoint", (Object)CustomBlocks.createItem(waypoint));
            event.getForgeRegistry().register("customnpcs:npcborder", (Object)CustomBlocks.createItem(border));
            event.getForgeRegistry().register("customnpcs:npcscripted", (Object)CustomBlocks.createItem(scripted));
            event.getForgeRegistry().register("customnpcs:npcscripteddoortool", (Object)new ItemScriptedDoor(scripted_door));
            event.getForgeRegistry().register("customnpcs:npcbuilderblock", (Object)CustomBlocks.createItem(builder));
            event.getForgeRegistry().register("customnpcs:npccopyblock", (Object)CustomBlocks.createItem(copy));
            event.getForgeRegistry().register("customnpcs:npccarpentybench", (Object)CustomBlocks.createItem(carpenty));
        }
        if (event.getRegistryKey() == ForgeRegistries.Keys.BLOCK_ENTITY_TYPES) {
            event.getForgeRegistry().register("customnpcs:tileblockanvil", CustomBlocks.createTile("tileblockanvil", TileBlockAnvil::new, carpenty));
            event.getForgeRegistry().register("customnpcs:tilenpcborder", CustomBlocks.createTile("tilenpcborder", TileBorder::new, border));
            event.getForgeRegistry().register("customnpcs:tilenpcbuilder", CustomBlocks.createTile("tilenpcbuilder", TileBuilder::new, builder));
            event.getForgeRegistry().register("customnpcs:tilenpccopy", CustomBlocks.createTile("tilenpccopy", TileCopy::new, copy));
            event.getForgeRegistry().register("customnpcs:tilemailbox", CustomBlocks.createTile("tilemailbox", TileMailbox::new, mailbox, mailbox2, mailbox3));
            event.getForgeRegistry().register("customnpcs:tileredstoneblock", CustomBlocks.createTile("tileredstoneblock", TileRedstoneBlock::new, redstone));
            event.getForgeRegistry().register("customnpcs:tilenpcscripted", CustomBlocks.createTile("tilenpcscripted", TileScripted::new, scripted));
            event.getForgeRegistry().register("customnpcs:tilenpcscripteddoor", CustomBlocks.createTile("tilenpcscripteddoor", TileScriptedDoor::new, scripted_door));
            event.getForgeRegistry().register("customnpcs:tilewaypoint", CustomBlocks.createTile("tilewaypoint", TileWaypoint::new, waypoint));
        }
    }

    private static BlockEntityType<?> createTile(String key, BlockEntityType.BlockEntitySupplier factoryIn, Block ... blocks) {
        BlockEntityType.Builder builder = BlockEntityType.Builder.m_155273_((BlockEntityType.BlockEntitySupplier)factoryIn, (Block[])blocks);
        return builder.m_58966_(Util.m_137456_((DSL.TypeReference)References.f_16781_, (String)key));
    }

    public static Item createItem(Block block) {
        ItemNpcBlock item = new ItemNpcBlock(block, new Item.Properties());
        return item;
    }
}

