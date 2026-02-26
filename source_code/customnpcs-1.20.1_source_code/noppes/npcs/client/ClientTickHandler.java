/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.client.event.InputEvent$Key
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickEmpty
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package noppes.npcs.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import noppes.npcs.CustomNpcs;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.player.GuiQuestLog;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketPlayerKeyPressed;
import noppes.npcs.packets.server.SPacketPlayerLeftClicked;
import noppes.npcs.packets.server.SPacketQuestCompletionCheckAll;
import noppes.npcs.packets.server.SPacketSceneReset;
import noppes.npcs.packets.server.SPacketSceneStart;
import noppes.npcs.packets.server.SPacketScreenSize;

public class ClientTickHandler {
    private Level prevLevel;
    private int prevWidth = 0;
    private int prevHeight = 0;
    private boolean otherContainer = false;
    private final int[] ignoreKeys = new int[]{341, 340, 342, 343, 345, 344, 346, 347};

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91074_ != null && mc.f_91074_.f_36096_ instanceof InventoryMenu) {
            if (this.otherContainer) {
                Packets.sendServer(new SPacketQuestCompletionCheckAll());
                this.otherContainer = false;
            }
        } else {
            this.otherContainer = true;
        }
        ++CustomNpcs.ticks;
        ++RenderNPCInterface.LastTextureTick;
        if (this.prevLevel != mc.f_91073_) {
            this.prevLevel = mc.f_91073_;
            MusicController.Instance.stopMusic();
        }
        if (Minecraft.m_91087_().f_91074_ != null && (this.prevWidth != mc.m_91268_().m_85441_() || this.prevHeight != mc.m_91268_().m_85442_())) {
            this.prevWidth = mc.m_91268_().m_85441_();
            this.prevHeight = mc.m_91268_().m_85442_();
            Packets.sendServer(new SPacketScreenSize(mc.m_91268_().m_85441_(), mc.m_91268_().m_85442_()));
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.Key event) {
        Minecraft mc = Minecraft.m_91087_();
        if (mc == null || mc.f_91073_ == null || mc.m_91403_() == null) {
            return;
        }
        if (CustomNpcs.SceneButtonsEnabled) {
            if (ClientProxy.Scene1.m_90857_()) {
                Packets.sendServer(new SPacketSceneStart(1));
            }
            if (ClientProxy.Scene2.m_90857_()) {
                Packets.sendServer(new SPacketSceneStart(2));
            }
            if (ClientProxy.Scene3.m_90857_()) {
                Packets.sendServer(new SPacketSceneStart(3));
            }
            if (ClientProxy.SceneReset.m_90857_()) {
                Packets.sendServer(new SPacketSceneReset());
            }
        }
        if (ClientProxy.QuestLog.m_90857_()) {
            if (mc.f_91080_ == null) {
                NoppesUtil.openGUI((Player)mc.f_91074_, new GuiQuestLog((Player)mc.f_91074_));
            } else if (mc.f_91080_ instanceof GuiQuestLog) {
                mc.f_91067_.m_91601_();
            }
        }
        if (event.getAction() == 1 || event.getAction() == 0) {
            boolean isCtrlPressed = InputConstants.m_84830_((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)341) || InputConstants.m_84830_((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)345);
            boolean isShiftPressed = InputConstants.m_84830_((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)340) || InputConstants.m_84830_((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)344);
            boolean isAltPressed = InputConstants.m_84830_((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)342) || InputConstants.m_84830_((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)346);
            boolean isMetaPressed = InputConstants.m_84830_((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)343) || InputConstants.m_84830_((long)Minecraft.m_91087_().m_91268_().m_85439_(), (int)347);
            String openGui = mc.f_91080_ == null ? "" : mc.f_91080_.getClass().getName();
            Packets.sendServer(new SPacketPlayerKeyPressed(event.getKey(), isCtrlPressed, isShiftPressed, isAltPressed, isMetaPressed, event.getAction() == 0, openGui));
        }
    }

    @SubscribeEvent
    public void invoke(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        Packets.sendServer(new SPacketPlayerLeftClicked());
    }

    private boolean isIgnoredKey(int key) {
        for (int i : this.ignoreKeys) {
            if (i != key) continue;
            return true;
        }
        return false;
    }
}

