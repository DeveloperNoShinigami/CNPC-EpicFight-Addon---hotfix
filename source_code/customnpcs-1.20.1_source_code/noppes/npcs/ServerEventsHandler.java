/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ListenableFutureTask
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.arguments.EntityArgument
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.event.AttachCapabilitiesEvent
 *  net.minecraftforge.event.CommandEvent
 *  net.minecraftforge.event.entity.EntityJoinLevelEvent
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$SaveToFile
 *  net.minecraftforge.event.entity.player.PlayerEvent$StartTracking
 *  net.minecraftforge.event.entity.player.PlayerEvent$StopTracking
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$EntityInteract
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs;

import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.EventHooks;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.api.wrapper.WrapperEntityData;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.controllers.VisibilityController;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.controllers.data.MarkData;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.items.ItemSoulstoneEmpty;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiCloneOpen;
import noppes.npcs.packets.client.PacketGuiOpen;
import noppes.npcs.packets.client.PacketMarkData;
import noppes.npcs.quests.QuestKill;

public class ServerEventsHandler {
    public static Villager Merchant;

    @SubscribeEvent
    public void invoke(PlayerInteractEvent.EntityInteract event) {
        ItemStack item = event.getEntity().m_21205_();
        if (item.m_41619_() || event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        boolean isClientSide = event.getEntity().m_9236_().f_46443_;
        boolean npcInteracted = event.getTarget() instanceof EntityNPCInterface;
        if (!isClientSide && CustomNpcs.OpsOnly && !event.getEntity().m_20194_().m_6846_().m_11303_(event.getEntity().m_36316_())) {
            return;
        }
        if (!isClientSide && item.m_41720_() == CustomItems.soulstoneEmpty && event.getTarget() instanceof LivingEntity) {
            ((ItemSoulstoneEmpty)item.m_41720_()).store((LivingEntity)event.getTarget(), item, event.getEntity());
        }
        if (item.m_41720_() == CustomItems.wand && npcInteracted && !isClientSide) {
            if (!CustomNpcsPermissions.hasPermission((ServerPlayer)event.getEntity(), CustomNpcsPermissions.NPC_GUI)) {
                return;
            }
            event.setCanceled(true);
            NoppesUtilServer.sendOpenGui(event.getEntity(), EnumGuiType.MainMenuDisplay, (EntityNPCInterface)event.getTarget());
        } else if (item.m_41720_() == CustomItems.cloner && !isClientSide && !(event.getTarget() instanceof Player)) {
            CompoundTag compound = new CompoundTag();
            if (!event.getTarget().m_20086_(compound)) {
                return;
            }
            PlayerData data = PlayerData.get(event.getEntity());
            ServerCloneController.Instance.cleanTags(compound);
            Packets.send((ServerPlayer)event.getEntity(), new PacketGuiCloneOpen(compound));
            data.cloned = compound;
            event.setCanceled(true);
        } else if (item.m_41720_() == CustomItems.scripter && !isClientSide && npcInteracted) {
            if (!CustomNpcsPermissions.hasPermission((ServerPlayer)event.getEntity(), CustomNpcsPermissions.NPC_GUI)) {
                return;
            }
            NoppesUtilServer.setEditingNpc(event.getEntity(), (EntityNPCInterface)event.getTarget());
            event.setCanceled(true);
            Packets.send((ServerPlayer)event.getEntity(), new PacketGuiOpen(EnumGuiType.Script, BlockPos.f_121853_));
        } else if (item.m_41720_() == CustomItems.mount && !isClientSide) {
            if (!CustomNpcsPermissions.hasPermission((ServerPlayer)event.getEntity(), CustomNpcsPermissions.TOOL_MOUNTER)) {
                return;
            }
            PlayerData data = PlayerData.get(event.getEntity());
            event.setCanceled(true);
            data.mounted = event.getTarget();
            Packets.send((ServerPlayer)event.getEntity(), new PacketGuiOpen(EnumGuiType.MobSpawnerMounter, BlockPos.f_121853_));
        }
    }

    @SubscribeEvent
    public void invoke(LivingDeathEvent event) {
        if (event.getEntity().m_9236_().f_46443_) {
            return;
        }
        Entity source = NoppesUtilServer.GetDamageSourcee(event.getSource());
        if (source != null) {
            if (source instanceof EntityNPCInterface && event.getEntity() != null) {
                EntityNPCInterface npc = (EntityNPCInterface)source;
                Line line = npc.advanced.getKillLine();
                if (line != null) {
                    npc.saySurrounding(Line.formatTarget(line, event.getEntity()));
                }
                EventHooks.onNPCKills(npc, event.getEntity());
            }
            Player player = null;
            if (source instanceof Player) {
                player = (Player)source;
            } else if (source instanceof EntityNPCInterface && ((EntityNPCInterface)source).getOwner() instanceof Player) {
                player = (Player)((EntityNPCInterface)source).getOwner();
            } else if (source instanceof TamableAnimal && ((TamableAnimal)source).m_269323_() instanceof Player) {
                player = (Player)((TamableAnimal)source).m_269323_();
            }
            if (player != null) {
                this.doQuest(player, event.getEntity(), true);
                if (event.getEntity() instanceof EntityNPCInterface) {
                    this.doFactionPoints(player, (EntityNPCInterface)event.getEntity());
                }
            }
        }
        if (event.getEntity() instanceof Player) {
            PlayerData data = PlayerData.get((Player)event.getEntity());
            data.save(false);
        }
    }

    private void doFactionPoints(Player player, EntityNPCInterface npc) {
        npc.advanced.factions.addPoints(player);
    }

    private void doQuest(Player player, LivingEntity entity, boolean all) {
        PlayerData pdata = PlayerData.get(player);
        PlayerQuestData playerdata = pdata.questData;
        String entityName = ForgeRegistries.ENTITY_TYPES.getKey((Object)entity.m_6095_()).toString();
        if (entity instanceof Player) {
            entityName = "Player";
        }
        for (QuestData data : playerdata.activeQuests.values()) {
            if (data.quest.type != 2 && data.quest.type != 4) continue;
            if (data.quest.type == 4 && all) {
                List list = player.m_9236_().m_45976_(Player.class, entity.m_20191_().m_82377_(10.0, 10.0, 10.0));
                for (Player pl : list) {
                    if (pl == player) continue;
                    this.doQuest(pl, entity, false);
                }
            }
            String name = entityName;
            QuestKill quest = (QuestKill)data.quest.questInterface;
            if (quest.targets.containsKey(entity.m_7755_().getString())) {
                name = entity.m_7755_().getString();
            } else if (!quest.targets.containsKey(name)) continue;
            HashMap<String, Integer> killed = quest.getKilled(data);
            if (killed.containsKey(name) && killed.get(name) >= quest.targets.get(name)) continue;
            int amount = 0;
            if (killed.containsKey(name)) {
                amount = killed.get(name);
            }
            killed.put(name, amount + 1);
            quest.setKilled(data, killed);
            pdata.updateClient = true;
        }
        playerdata.checkQuestCompletion(player, 2);
        playerdata.checkQuestCompletion(player, 4);
    }

    @SubscribeEvent
    public void world(EntityJoinLevelEvent event) {
        if (event.getLevel().f_46443_ || !(event.getEntity() instanceof Player)) {
            return;
        }
        PlayerData data = PlayerData.get((Player)event.getEntity());
        data.updateCompanion(event.getLevel());
    }

    @SubscribeEvent(priority=EventPriority.LOW)
    public void attachEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            PlayerData.register(event);
        }
        if (event.getObject() instanceof LivingEntity) {
            MarkData.register(event);
        }
        if (((Entity)event.getObject()).m_9236_() != null && !((Entity)event.getObject()).m_9236_().f_46443_ && ((Entity)event.getObject()).m_9236_() instanceof ServerLevel) {
            WrapperEntityData.register(event);
        }
    }

    @SubscribeEvent
    public void attachItem(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStackWrapper.register(event);
    }

    @SubscribeEvent
    public void savePlayer(PlayerEvent.SaveToFile event) {
        PlayerData.get(event.getEntity()).save(false);
    }

    @SubscribeEvent
    public void playerTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)event.getTarget();
            npc.tracking.add(event.getEntity().m_19879_());
            VisibilityController.checkIsVisible(npc, (ServerPlayer)event.getEntity());
        }
        if (!(event.getTarget() instanceof LivingEntity) || event.getTarget().m_9236_().f_46443_) {
            return;
        }
        MarkData data = MarkData.get((LivingEntity)event.getTarget());
        if (data.marks.isEmpty()) {
            return;
        }
        Packets.send((ServerPlayer)event.getEntity(), new PacketMarkData(event.getTarget().m_19879_(), data.getNBT()));
    }

    @SubscribeEvent
    public void playerStopTracking(PlayerEvent.StopTracking event) {
        if (event.getTarget() instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)event.getTarget();
            npc.tracking.remove(event.getEntity().m_19879_());
        }
    }

    @SubscribeEvent
    public void commandGive(CommandEvent event) {
        String command = event.getParseResults().getReader().getString();
        if (!command.startsWith("/give ")) {
            return;
        }
        try {
            CommandContext context = event.getParseResults().getContext().build(event.getParseResults().getReader().getString());
            Collection players = EntityArgument.m_91477_((CommandContext)context, (String)"targets");
            for (ServerPlayer player : players) {
                player.m_20194_().execute((Runnable)ListenableFutureTask.create(Executors.callable(() -> {
                    PlayerQuestData playerdata = PlayerData.get((Player)player).questData;
                    playerdata.checkQuestCompletion((Player)player, 0);
                })));
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void commandTime(CommandEvent event) {
        String command = event.getParseResults().getReader().getString();
        if (!command.startsWith("time ")) {
            return;
        }
        try {
            CustomNpcs.Server.m_18707_(() -> {
                List players = CustomNpcs.Server.m_6846_().m_11314_();
                for (ServerPlayer playerMP : players) {
                    VisibilityController.instance.onUpdate(playerMP);
                }
            });
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }
}

