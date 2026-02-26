/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package noppes.npcs.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class VersionChecker
extends Thread {
    @Override
    public void run() {
        LocalPlayer player;
        String name = "\u00a72CustomNpcs\u00a7f";
        String link = "\u00a79\u00a7nClick here";
        String text = name + " installed. For more info " + link;
        try {
            player = Minecraft.m_91087_().f_91074_;
        }
        catch (NoSuchMethodError e) {
            return;
        }
        while ((player = Minecraft.m_91087_().f_91074_) == null) {
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MutableComponent message = Component.m_237115_((String)text);
        message.m_6270_(message.m_7383_().m_131142_(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://www.kodevelopment.nl/minecraft/customnpcs/")));
        player.m_213846_((Component)message);
    }
}

