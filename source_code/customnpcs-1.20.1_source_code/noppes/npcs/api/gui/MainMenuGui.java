/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package noppes.npcs.api.gui;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.gui.DeathMenu;
import noppes.npcs.api.gui.DisplayMenu;
import noppes.npcs.api.gui.HealthMenu;
import noppes.npcs.api.gui.IButton;
import noppes.npcs.api.gui.ITexturedRect;
import noppes.npcs.api.gui.InventoryMenu;
import noppes.npcs.api.gui.LogicMenu;
import noppes.npcs.api.gui.MeleeMenu;
import noppes.npcs.api.gui.ModelMenu;
import noppes.npcs.api.gui.MovementMenu;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiParts;

public abstract class MainMenuGui {
    protected CustomGuiWrapper gui;

    public MainMenuGui(int active, EntityCustomNpc npc, IPlayer player) {
        this(active, npc, player, true);
    }

    public MainMenuGui(int active, EntityCustomNpc npc, IPlayer player, boolean renderHeader) {
        this.gui = new CustomGuiWrapper(player);
        this.gui.setBackgroundTexture("customnpcs:textures/gui/components.png");
        this.gui.setSize(420, 220);
        this.gui.getBackgroundRect().setPos(0, 20);
        this.gui.getBackgroundRect().setSize(420, 200);
        this.gui.getBackgroundRect().setTextureOffset(0, 0);
        this.gui.getBackgroundRect().setRepeatingTexture(64, 64, 4);
        this.gui.npc = npc;
        if (renderHeader) {
            int buttonId = 0;
            CustomGuiButtonWrapper button = new CustomGuiButtonWrapper().setSize(22, 20);
            ITexturedRect rect = button.getTextureRect().setTexture("customnpcs:textures/gui/components.png").setRepeatingTexture(64, 20, 3).setTextureOffset(0, 64);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42545_)));
            button.setHoverText("menu.display");
            button.setOnPress((gui, b) -> player.showCustomGui(new DisplayMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42680_)));
            button.setHoverText("menu.model");
            button.setOnPress((gui, b) -> {
                CustomGuiWrapper wrapper = new ModelMenu((EntityCustomNpc)npc, (IPlayer)player).gui;
                ((ContainerCustomGui)((ServerPlayer)player.getMCEntity()).f_36096_).setGui(wrapper, (Player)player.getMCEntity());
                Packets.send(player.getMCEntity(), new PacketGuiParts(npc.m_19879_(), wrapper.toNBT()));
            });
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42009_)));
            button.setHoverText("menu.inventory");
            button.setOnPress((gui, b) -> player.showCustomGui(new InventoryMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42451_)));
            button.setHoverText("menu.logic");
            button.setOnPress((gui, b) -> player.showCustomGui(new LogicMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42473_)));
            button.setHoverText("menu.health");
            button.setOnPress((gui, b) -> player.showCustomGui(new HealthMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42747_)));
            button.setHoverText("menu.death");
            button.setOnPress((gui, b) -> player.showCustomGui(new DeathMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42471_)));
            button.setHoverText("menu.movement");
            button.setOnPress((gui, b) -> player.showCustomGui(new MovementMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42388_)));
            button.setHoverText("stats.meleeproperties");
            button.setOnPress((gui, b) -> player.showCustomGui(new MeleeMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42412_)));
            button.setOnPress((gui, b) -> player.showCustomGui(new DisplayMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            button = new CustomGuiButtonWrapper().setSize(22, 20);
            button.setTextureRect(rect);
            button.setTextureHoverOffset(22).setPos(buttonId * 22 + 4, 0).setID(buttonId++);
            button.setDisplayItem(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42451_)));
            button.setOnPress((gui, b) -> player.showCustomGui(new DisplayMenu((EntityCustomNpc)npc, (IPlayer)player).gui));
            this.gui.addComponent(button);
            IButton b2 = (IButton)this.gui.getComponent(active);
            b2.setEnabled(false);
            b2.setPos(b2.getPosX(), 3);
        }
    }

    public static void open(Player player, EntityCustomNpc npc) {
        IPlayer p = (IPlayer)NpcAPI.Instance().getIEntity((Entity)player);
        DisplayMenu menu = new DisplayMenu(npc, p);
        p.showCustomGui(menu.gui);
    }
}

