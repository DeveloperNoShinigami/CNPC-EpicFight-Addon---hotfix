/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraftforge.event.TickEvent
 *  net.minecraftforge.event.entity.EntityEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent
 *  net.minecraftforge.event.level.LevelEvent
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.LogicalSide
 *  org.apache.commons.lang3.StringUtils
 */
package noppes.npcs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EventHooks;
import noppes.npcs.api.event.ForgeEvent;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.shared.common.util.LogWriter;
import org.apache.commons.lang3.StringUtils;

public class ForgeEventHandler {
    public static List<String> eventNames = new ArrayList<String>();
    private Event lastSeenEvent;

    @SubscribeEvent
    public void forgeEntity(Event event) {
        if (CustomNpcs.Server == null || !ScriptController.Instance.forgeScripts.isEnabled()) {
            return;
        }
        if (this.lastSeenEvent == event) {
            return;
        }
        this.lastSeenEvent = event;
        try {
            PlayerEvent ev;
            if (event instanceof PlayerEvent && ((ev = (PlayerEvent)event).getEntity() == null || !(ev.getEntity().m_9236_() instanceof ServerLevel))) {
                return;
            }
            if (event instanceof EntityEvent) {
                ev = (EntityEvent)event;
                if (ev.getEntity() == null || !(ev.getEntity().m_9236_() instanceof ServerLevel)) {
                    return;
                }
                EventHooks.onForgeEntityEvent((EntityEvent)ev);
                return;
            }
            if (event instanceof LevelEvent) {
                ev = (LevelEvent)event;
                if (!(ev.getLevel() instanceof ServerLevel)) {
                    return;
                }
                EventHooks.onForgeLevelEvent((LevelEvent)ev);
                return;
            }
            if (event instanceof TickEvent && ((TickEvent)event).side == LogicalSide.CLIENT) {
                return;
            }
            EventHooks.onForgeEvent(new ForgeEvent(event), event);
        }
        catch (Throwable t) {
            LogWriter.error("Error in " + event.getClass().getName(), t);
        }
    }

    public static String getEventName(Class c) {
        String eventName = c.getName();
        int i = eventName.lastIndexOf(".");
        return StringUtils.uncapitalize((String)eventName.substring(i + 1).replace("$", ""));
    }
}

