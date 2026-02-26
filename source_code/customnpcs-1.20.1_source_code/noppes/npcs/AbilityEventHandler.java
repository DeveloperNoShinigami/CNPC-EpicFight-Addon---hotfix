/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package noppes.npcs;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import noppes.npcs.ability.IAbilityDamaged;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.constants.EnumAbilityType;
import noppes.npcs.entity.EntityNPCInterface;

public class AbilityEventHandler {
    @SubscribeEvent
    public void invoke(NpcEvent.DamagedEvent event) {
        IAbilityDamaged ab = (IAbilityDamaged)((Object)((EntityNPCInterface)((Object)event.npc.getMCEntity())).abilities.getAbility(EnumAbilityType.ATTACKED));
        if (ab != null) {
            ab.handleEvent(event);
        }
    }
}

