/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.api.wrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.EventHooks;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.ITimers;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IEntityLiving;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.entity.IProjectile;
import noppes.npcs.api.entity.data.INPCAdvanced;
import noppes.npcs.api.entity.data.INPCAi;
import noppes.npcs.api.entity.data.INPCDisplay;
import noppes.npcs.api.entity.data.INPCInventory;
import noppes.npcs.api.entity.data.INPCJob;
import noppes.npcs.api.entity.data.INPCRole;
import noppes.npcs.api.entity.data.INPCStats;
import noppes.npcs.api.handler.data.IDialog;
import noppes.npcs.api.handler.data.IFaction;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.EntityLivingWrapper;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketNpcRotationUpdate;
import noppes.npcs.util.ValueUtil;

public class NPCWrapper<T extends EntityNPCInterface>
extends EntityLivingWrapper<T>
implements ICustomNpc {
    public NPCWrapper(T npc) {
        super(npc);
    }

    @Override
    public void setMaxHealth(float health) {
        if ((int)health == ((EntityNPCInterface)this.entity).stats.maxHealth) {
            return;
        }
        super.setMaxHealth(health);
        ((EntityNPCInterface)this.entity).stats.maxHealth = (int)health;
        ((EntityNPCInterface)this.entity).updateClient = true;
    }

    @Override
    public INPCDisplay getDisplay() {
        return ((EntityNPCInterface)this.entity).display;
    }

    @Override
    public INPCInventory getInventory() {
        return ((EntityNPCInterface)this.entity).inventory;
    }

    @Override
    public INPCAi getAi() {
        return ((EntityNPCInterface)this.entity).ais;
    }

    @Override
    public INPCAdvanced getAdvanced() {
        return ((EntityNPCInterface)this.entity).advanced;
    }

    @Override
    public INPCStats getStats() {
        return ((EntityNPCInterface)this.entity).stats;
    }

    @Override
    public IFaction getFaction() {
        return ((EntityNPCInterface)this.entity).faction;
    }

    @Override
    public ITimers getTimers() {
        return ((EntityNPCInterface)this.entity).timers;
    }

    @Override
    public void setFaction(int id) {
        Faction faction = FactionController.instance.getFaction(id);
        if (faction == null) {
            throw new CustomNPCsException("Unknown faction id: " + id, new Object[0]);
        }
        ((EntityNPCInterface)this.entity).setFaction(id);
    }

    @Override
    public INPCRole getRole() {
        return ((EntityNPCInterface)this.entity).role;
    }

    @Override
    public INPCJob getJob() {
        return ((EntityNPCInterface)this.entity).job;
    }

    @Override
    public int getHomeX() {
        return ((EntityNPCInterface)this.entity).ais.startPos().m_123341_();
    }

    @Override
    public int getHomeY() {
        return ((EntityNPCInterface)this.entity).ais.startPos().m_123342_();
    }

    @Override
    public int getHomeZ() {
        return ((EntityNPCInterface)this.entity).ais.startPos().m_123343_();
    }

    @Override
    public void setHome(int x, int y, int z) {
        ((EntityNPCInterface)this.entity).ais.setStartPos(new BlockPos(x, y, z));
    }

    public int getOffsetX() {
        return (int)((EntityNPCInterface)this.entity).ais.bodyOffsetX;
    }

    public int getOffsetY() {
        return (int)((EntityNPCInterface)this.entity).ais.bodyOffsetY;
    }

    public int getOffsetZ() {
        return (int)((EntityNPCInterface)this.entity).ais.bodyOffsetZ;
    }

    public void setOffset(int x, int y, int z) {
        ((EntityNPCInterface)this.entity).ais.bodyOffsetX = ValueUtil.correctFloat(x, 0.0f, 10.0f);
        ((EntityNPCInterface)this.entity).ais.bodyOffsetY = ValueUtil.correctFloat(y, 0.0f, 100.0f);
        ((EntityNPCInterface)this.entity).ais.bodyOffsetZ = ValueUtil.correctFloat(z, 0.0f, 10.0f);
        ((EntityNPCInterface)this.entity).updateClient = true;
    }

    @Override
    public void say(String message) {
        ((EntityNPCInterface)this.entity).saySurrounding(new Line(message));
    }

    @Override
    public void sayTo(IPlayer player, String message) {
        ((EntityNPCInterface)this.entity).say((Player)player.getMCEntity(), new Line(message));
    }

    @Override
    public void reset() {
        ((EntityNPCInterface)this.entity).reset();
    }

    @Override
    public long getAge() {
        return ((EntityNPCInterface)this.entity).totalTicksAlive;
    }

    @Override
    public IProjectile shootItem(IEntityLiving target, IItemStack item, int accuracy) {
        if (item == null) {
            throw new CustomNPCsException("No item was given", new Object[0]);
        }
        if (target == null) {
            throw new CustomNPCsException("No target was given", new Object[0]);
        }
        accuracy = ValueUtil.CorrectInt(accuracy, 1, 100);
        return (IProjectile)NpcAPI.Instance().getIEntity((Entity)((EntityNPCInterface)this.entity).shoot((LivingEntity)target.getMCEntity(), accuracy, item.getMCItemStack(), false));
    }

    @Override
    public IProjectile shootItem(double x, double y, double z, IItemStack item, int accuracy) {
        if (item == null) {
            throw new CustomNPCsException("No item was given", new Object[0]);
        }
        accuracy = ValueUtil.CorrectInt(accuracy, 1, 100);
        return (IProjectile)NpcAPI.Instance().getIEntity((Entity)((EntityNPCInterface)this.entity).shoot(x, y, z, accuracy, item.getMCItemStack(), false));
    }

    @Override
    public void giveItem(IPlayer player, IItemStack item) {
        ((EntityNPCInterface)this.entity).givePlayerItem((Player)player.getMCEntity(), item.getMCItemStack());
    }

    @Override
    public String executeCommand(String command) {
        if (!((EntityNPCInterface)this.entity).m_20194_().m_6993_()) {
            throw new CustomNPCsException("Command blocks need to be enabled to executeCommands", new Object[0]);
        }
        return NoppesUtilServer.runCommand(this.entity, ((EntityNPCInterface)this.entity).m_7755_().getString(), command, null);
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String getName() {
        return ((EntityNPCInterface)this.entity).display.getName();
    }

    @Override
    public void setName(String name) {
        ((EntityNPCInterface)this.entity).display.setName(name);
    }

    @Override
    public void setRotation(float rotation) {
        super.setRotation(rotation);
        int r = (int)rotation;
        if (((EntityNPCInterface)this.entity).ais.orientation != r) {
            ((EntityNPCInterface)this.entity).ais.orientation = r;
            Packets.sendNearby(this.entity, new PacketNpcRotationUpdate(((EntityNPCInterface)this.entity).m_19879_(), ((EntityNPCInterface)this.entity).ais.orientation));
        }
    }

    @Override
    public boolean typeOf(int type) {
        return type == 2 ? true : super.typeOf(type);
    }

    @Override
    public void setDialog(int slot, IDialog dialog) {
        if (slot < 0 || slot > 11) {
            throw new CustomNPCsException("Slot needs to be between 0 and 11", new Object[0]);
        }
        if (dialog == null) {
            ((EntityNPCInterface)this.entity).dialogs.remove(slot);
        } else {
            DialogOption option = new DialogOption();
            option.dialogId = dialog.getId();
            option.title = dialog.getName();
            ((EntityNPCInterface)this.entity).dialogs.put(slot, option);
        }
    }

    @Override
    public IDialog getDialog(int slot) {
        if (slot < 0 || slot > 11) {
            throw new CustomNPCsException("Slot needs to be between 0 and 11", new Object[0]);
        }
        DialogOption option = ((EntityNPCInterface)this.entity).dialogs.get(slot);
        if (option == null || !option.hasDialog()) {
            return null;
        }
        return option.getDialog();
    }

    @Override
    public void updateClient() {
        ((EntityNPCInterface)this.entity).updateClient();
    }

    @Override
    public IEntityLiving getOwner() {
        LivingEntity owner = ((EntityNPCInterface)this.entity).getOwner();
        if (owner != null) {
            return (IEntityLiving)NpcAPI.Instance().getIEntity((Entity)owner);
        }
        return null;
    }

    @Override
    public void trigger(int id, Object ... arguments) {
        EventHooks.onScriptTriggerEvent(((EntityNPCInterface)this.entity).script, id, this.getWorld(), this.getPos(), null, arguments);
    }
}

