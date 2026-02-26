/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.GameProfile
 *  com.mojang.brigadier.CommandDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
 *  net.minecraft.network.protocol.game.ClientboundSetScorePacket
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.ServerScoreboard
 *  net.minecraft.server.ServerScoreboard$Method
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.attributes.RangedAttribute
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.storage.LevelResource
 *  net.minecraft.world.scores.Objective
 *  net.minecraft.world.scores.Score
 *  net.minecraft.world.scores.Scoreboard
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.util.FakePlayer
 *  net.minecraftforge.event.RegisterCommandsEvent
 *  net.minecraftforge.event.server.ServerAboutToStartEvent
 *  net.minecraftforge.event.server.ServerStartedEvent
 *  net.minecraftforge.event.server.ServerStartingEvent
 *  net.minecraftforge.event.server.ServerStoppedEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.DistExecutor
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  net.minecraftforge.fml.loading.FMLPaths
 */
package noppes.npcs;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import nikedemos.markovnames.generators.MarkovGenerator;
import noppes.npcs.AbilityEventHandler;
import noppes.npcs.CommonProxy;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.CustomTabs;
import noppes.npcs.ScriptItemEventHandler;
import noppes.npcs.ScriptPlayerEventHandler;
import noppes.npcs.ServerEventsHandler;
import noppes.npcs.ServerTickHandler;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.wrapper.WrapperNpcAPI;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.command.CmdNoppes;
import noppes.npcs.command.CmdSchematics;
import noppes.npcs.config.ConfigLoader;
import noppes.npcs.config.ConfigProp;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.ChunkController;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.GlobalDataController;
import noppes.npcs.controllers.LinkedNpcController;
import noppes.npcs.controllers.MassBlockController;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.SchematicController;
import noppes.npcs.controllers.ScriptController;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.controllers.SpawnController;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.VisibilityController;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.ScoreBoardMixin;
import noppes.npcs.packets.Packets;
import noppes.npcs.shared.common.util.LogWriter;

@Mod(value="customnpcs")
public class CustomNpcs {
    public static final String MODID = "customnpcs";
    public static final String VERSION = "1.20.1";
    @ConfigProp(info="Whether scripting is enabled or not")
    public static boolean EnableScripting = true;
    @ConfigProp(info="Arguments given to the Nashorn scripting library")
    public static String NashorArguments = "-strict";
    @ConfigProp(info="Disable Chat Bubbles")
    public static boolean EnableChatBubbles = true;
    @ConfigProp(info="Navigation search range for NPCs. Not recommended to increase if you have a slow pc or on a server")
    public static int NpcNavRange = 32;
    @ConfigProp(info="Limit too how many npcs can be in one chunk for natural spawning")
    public static int NpcNaturalSpawningChunkLimit = 4;
    @ConfigProp(info="Set to true if you want the dialog command option to be able to use op commands like tp etc")
    public static boolean NpcUseOpCommands = false;
    @ConfigProp(info="If set to true only opped people can use the /noppes command")
    public static boolean NoppesCommandOpOnly = false;
    @ConfigProp
    public static boolean InventoryGuiEnabled = true;
    public static boolean FixUpdateFromPre_1_12 = false;
    @ConfigProp(info="If you are running sponge and you want to disable the permissions set this to true")
    public static boolean DisablePermissions = false;
    @ConfigProp
    public static boolean SceneButtonsEnabled = true;
    public static long ticks;
    public static CommonProxy proxy;
    @ConfigProp(info="Enables CustomNpcs startup update message")
    public static boolean EnableUpdateChecker;
    public static CustomNpcs instance;
    public static boolean FreezeNPCs;
    @ConfigProp(info="Only ops can create and edit npcs")
    public static boolean OpsOnly;
    @ConfigProp(info="Default interact line. Leave empty to not have one")
    public static String DefaultInteractLine;
    @ConfigProp(info="Number of chunk loading npcs that can be active at the same time")
    public static int ChuckLoaders;
    public static File Dir;
    @ConfigProp(info="Enables leaves decay")
    public static boolean LeavesDecayEnabled;
    @ConfigProp(info="Enables Vine Growth")
    public static boolean VineGrowthEnabled;
    @ConfigProp(info="Enables Ice Melting")
    public static boolean IceMeltsEnabled;
    @ConfigProp(info="Normal players can use soulstone on animals")
    public static boolean SoulStoneAnimals;
    @ConfigProp(info="Normal players can use soulstone on all npcs")
    public static boolean SoulStoneNPCs;
    @ConfigProp(info="Type 0 = Normal, Type 1 = Solid")
    public static int HeadWearType;
    @ConfigProp(info="When set to Minecraft it will use minecrafts font, when Default it will use OpenSans. Can only use fonts installed on your PC")
    public static String FontType;
    @ConfigProp(info="Font size for custom fonts (doesn't work with minecrafts font)")
    public static int FontSize;
    @ConfigProp(info="On some servers or with certain plugins, it doesnt work, so you can disable it here")
    public static boolean EnableInvisibleNpcs;
    @ConfigProp(info="Type 0 = standard CNPC (client side despawn), type 1 = old CNPC (disable renderer only), type 2 = modern (disable rendering, hitboxes and interactions)")
    public static int InvisibilityAlgorithm;
    @ConfigProp
    public static boolean NpcSpeachTriggersChatEvent;
    @ConfigProp(info="Enable it if you want all your global data (quests and dialogs) to be saved externally (not per world, but per .minecraft folder)")
    public static boolean EnableExternalSaving;
    @ConfigProp(info="The lifetime for the projectiles, after which they get despawned (used for removing stuck projectiles)")
    public static int ProjectileLifespan;
    public static ConfigLoader Config;
    public static boolean VerboseDebug;
    public static MinecraftServer Server;

    public CustomNpcs() {
        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        CustomTabs.CREATIVE_TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
        File dir = new File(FMLPaths.CONFIGDIR.get().toFile(), "..");
        Config = new ConfigLoader(this.getClass(), new File(dir, "config"), "CustomNpcs");
        Config.loadConfig();
    }

    private void postLoad(FMLLoadCompleteEvent event) {
        proxy.postload();
        CustomItems.registerDispenser();
    }

    private void setup(FMLCommonSetupEvent event) {
        if (NpcNavRange < 16) {
            NpcNavRange = 16;
        }
        Packets.register();
        MinecraftForge.EVENT_BUS.register((Object)new ServerEventsHandler());
        MinecraftForge.EVENT_BUS.register((Object)new ServerTickHandler());
        MinecraftForge.EVENT_BUS.register((Object)proxy);
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)new CustomNpcsPermissions());
        NpcAPI.Instance().events().register((Object)new AbilityEventHandler());
        proxy.load();
        PixelmonHelper.load();
        ScriptController controller = new ScriptController();
        if (EnableScripting && controller.languages.size() > 0) {
            MinecraftForge.EVENT_BUS.register((Object)controller);
            MinecraftForge.EVENT_BUS.register((Object)new ScriptPlayerEventHandler().registerForgeEvents());
            MinecraftForge.EVENT_BUS.register((Object)new ScriptItemEventHandler());
        }
        CustomNpcs.setPrivateValue(RangedAttribute.class, (RangedAttribute)Attributes.f_22276_, Double.MAX_VALUE, 1);
        new RecipeController();
    }

    @SubscribeEvent
    public void setAboutToStart(ServerAboutToStartEvent event) {
        Availability.scores.clear();
        Server = event.getServer();
        MarkovGenerator.load();
        ChunkController.instance.clear();
        FactionController.instance.load();
        new PlayerDataController();
        new TransportController();
        new GlobalDataController();
        new SpawnController();
        new LinkedNpcController();
        new MassBlockController();
        VisibilityController.instance = new VisibilityController();
        ScriptController.Instance.loadCategories();
        ScriptController.Instance.loadStoredData();
        ScriptController.Instance.loadPlayerScripts();
        ScriptController.Instance.loadForgeScripts();
        ScriptController.HasStart = false;
        WrapperNpcAPI.clearCache();
        CmdSchematics.names.clear();
        CmdSchematics.names.addAll(SchematicController.Instance.list());
    }

    @SubscribeEvent
    public void started(ServerStartedEvent event) {
        RecipeController.instance.load();
        new BankController();
        DialogController.instance.load();
        QuestController.instance.load();
        ScriptController.HasStart = true;
        ServerCloneController.Instance = new ServerCloneController();
    }

    @SubscribeEvent
    public void stopped(ServerStoppedEvent event) {
        ServerCloneController.Instance = null;
        Server = null;
    }

    @SubscribeEvent
    public void serverstart(ServerStartingEvent event) {
        EntityNPCInterface.ChatEventPlayer = new FakePlayer(event.getServer().m_129880_(Level.f_46428_), (GameProfile)EntityNPCInterface.ChatEventProfile);
        EntityNPCInterface.CommandPlayer = new FakePlayer(event.getServer().m_129880_(Level.f_46428_), (GameProfile)EntityNPCInterface.CommandProfile);
        EntityNPCInterface.GenericPlayer = new FakePlayer(event.getServer().m_129880_(Level.f_46428_), (GameProfile)EntityNPCInterface.GenericProfile);
        for (ServerLevel level : Server.m_129785_()) {
            ServerScoreboard board = level.m_6188_();
            board.m_136207_(() -> {
                for (String objective : Availability.scores) {
                    Objective so = board.m_83477_(objective);
                    if (so == null) continue;
                    for (ServerPlayer player : Server.m_6846_().m_11314_()) {
                        if (!board.m_83461_(player.m_6302_(), so) && board.m_136237_(so) == 0) {
                            player.f_8906_.m_9829_((Packet)new ClientboundSetObjectivePacket(so, 0));
                        }
                        ScoreBoardMixin mixin = (ScoreBoardMixin)board;
                        Map map = mixin.getScores().computeIfAbsent(player.m_6302_(), p_197898_0_ -> Maps.newHashMap());
                        Score sco = map.computeIfAbsent(so, ob -> new Score((Scoreboard)board, ob, player.m_6302_()));
                        player.f_8906_.m_9829_((Packet)new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, so.m_83320_(), sco.m_83405_(), sco.m_83400_()));
                    }
                }
            });
            board.m_136207_(() -> {
                List players = Server.m_6846_().m_11314_();
                for (ServerPlayer playerMP : players) {
                    VisibilityController.instance.onUpdate(playerMP);
                }
            });
        }
    }

    @SubscribeEvent
    public void registerCommand(RegisterCommandsEvent e) {
        CmdNoppes.register((CommandDispatcher<CommandSourceStack>)e.getDispatcher());
    }

    public static File getLevelSaveDirectory() {
        return CustomNpcs.getLevelSaveDirectory(null, false);
    }

    public static File getLevelSaveDirectory(String s) {
        return CustomNpcs.getLevelSaveDirectory(s, true);
    }

    public static File getLevelSaveDirectory(String s, boolean local) {
        try {
            File dir = new File(".");
            if (EnableExternalSaving && !local) {
                dir = Dir;
            } else if (Server != null) {
                if (!Server.m_6982_()) {
                    dir = new File(Minecraft.m_91087_().f_91069_, "saves");
                }
                dir = Server.m_129843_(new LevelResource(MODID)).toFile();
            }
            if (s != null) {
                dir = new File(dir, s);
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir;
        }
        catch (Exception e) {
            LogWriter.error("Error getting worldsave", e);
            return null;
        }
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, T instance, E value, int fieldIndex) {
        try {
            Field f = classToAccess.getDeclaredFields()[fieldIndex];
            f.setAccessible(true);
            f.set(instance, value);
        }
        catch (IllegalAccessException e) {
            LogWriter.error("setPrivateValue error", e);
        }
    }

    static {
        proxy = (CommonProxy)DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        EnableUpdateChecker = true;
        FreezeNPCs = false;
        OpsOnly = false;
        DefaultInteractLine = "Hello @p";
        ChuckLoaders = 20;
        LeavesDecayEnabled = true;
        VineGrowthEnabled = true;
        IceMeltsEnabled = true;
        SoulStoneAnimals = true;
        SoulStoneNPCs = false;
        HeadWearType = 1;
        FontType = "Default";
        FontSize = 18;
        EnableInvisibleNpcs = true;
        InvisibilityAlgorithm = 2;
        NpcSpeachTriggersChatEvent = false;
        ProjectileLifespan = 1200;
        VerboseDebug = false;
        File dir = new File(FMLPaths.CONFIGDIR.get().toFile(), "..");
        Dir = new File(dir, MODID);
        if (!Dir.exists()) {
            Dir.mkdir();
        }
    }
}

