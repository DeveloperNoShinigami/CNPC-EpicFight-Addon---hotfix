/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.MenuScreens
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.SimpleTexture
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.server.packs.resources.ReloadableResourceManager
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.thread.BlockableEventLoop
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.util.LogicalSidedProvider
 *  net.minecraftforge.fml.LogicalSide
 */
package noppes.npcs.client;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import noppes.npcs.CommonProxy;
import noppes.npcs.CustomContainer;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartData;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.item.IItemScripted;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.client.ClientEventHandler;
import noppes.npcs.client.ClientTickHandler;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.VersionChecker;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.controllers.PresetController;
import noppes.npcs.client.gui.GuiBlockBuilder;
import noppes.npcs.client.gui.GuiBlockCopy;
import noppes.npcs.client.gui.GuiBorderBlock;
import noppes.npcs.client.gui.GuiMerchantAdd;
import noppes.npcs.client.gui.GuiNbtBook;
import noppes.npcs.client.gui.GuiNpcDimension;
import noppes.npcs.client.gui.GuiNpcMobSpawner;
import noppes.npcs.client.gui.GuiNpcMobSpawnerMounter;
import noppes.npcs.client.gui.GuiNpcPather;
import noppes.npcs.client.gui.GuiNpcRedstoneBlock;
import noppes.npcs.client.gui.GuiNpcRemoteEditor;
import noppes.npcs.client.gui.GuiNpcWaypoint;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.global.GuiNPCManageBanks;
import noppes.npcs.client.gui.global.GuiNPCManageDialogs;
import noppes.npcs.client.gui.global.GuiNPCManageFactions;
import noppes.npcs.client.gui.global.GuiNPCManageLinkedNpc;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.global.GuiNPCManageTransporters;
import noppes.npcs.client.gui.global.GuiNpcManageRecipes;
import noppes.npcs.client.gui.global.GuiNpcQuestReward;
import noppes.npcs.client.gui.mainmenu.GuiNPCGlobalMainMenu;
import noppes.npcs.client.gui.mainmenu.GuiNPCInv;
import noppes.npcs.client.gui.mainmenu.GuiNpcAI;
import noppes.npcs.client.gui.mainmenu.GuiNpcAdvanced;
import noppes.npcs.client.gui.mainmenu.GuiNpcDisplay;
import noppes.npcs.client.gui.mainmenu.GuiNpcStats;
import noppes.npcs.client.gui.player.GuiMailbox;
import noppes.npcs.client.gui.player.GuiMailmanWrite;
import noppes.npcs.client.gui.player.GuiNPCBankChest;
import noppes.npcs.client.gui.player.GuiNPCTrader;
import noppes.npcs.client.gui.player.GuiNpcCarpentryBench;
import noppes.npcs.client.gui.player.GuiNpcFollower;
import noppes.npcs.client.gui.player.GuiNpcFollowerHire;
import noppes.npcs.client.gui.player.GuiTransportSelection;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionInv;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionStats;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionTalents;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeItem;
import noppes.npcs.client.gui.roles.GuiNpcBankSetup;
import noppes.npcs.client.gui.roles.GuiNpcFollowerSetup;
import noppes.npcs.client.gui.roles.GuiNpcItemGiver;
import noppes.npcs.client.gui.roles.GuiNpcTraderSetup;
import noppes.npcs.client.gui.roles.GuiNpcTransporter;
import noppes.npcs.client.gui.script.GuiScript;
import noppes.npcs.client.gui.script.GuiScriptBlock;
import noppes.npcs.client.gui.script.GuiScriptDoor;
import noppes.npcs.client.gui.script.GuiScriptGlobal;
import noppes.npcs.client.gui.script.GuiScriptItem;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.containers.ContainerCustomGui;
import noppes.npcs.controllers.ArmorersWorkshopHelper;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.ArmorLayerMixin;
import noppes.npcs.shared.client.util.TrueTypeFont;
import noppes.npcs.shared.common.util.LogWriter;

public class ClientProxy
extends CommonProxy {
    public static PlayerData playerData = new PlayerData();
    public static KeyMapping QuestLog;
    public static KeyMapping Scene1;
    public static KeyMapping SceneReset;
    public static KeyMapping Scene2;
    public static KeyMapping Scene3;
    public static FontContainer Font;
    public static ModelData data;
    public static PlayerModel playerModel;
    public static ArmorLayerMixin armorLayer;

    public CompletableFuture<Void> enqueueWork(Runnable runnable) {
        BlockableEventLoop executor = (BlockableEventLoop)LogicalSidedProvider.WORKQUEUE.get(LogicalSide.CLIENT);
        if (!executor.m_18695_()) {
            return executor.m_18689_(runnable);
        }
        runnable.run();
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void load() {
        this.enqueueWork(() -> {
            Font = new FontContainer(CustomNpcs.FontType, CustomNpcs.FontSize);
            this.createFolders();
            CustomNpcResourceListener listener = new CustomNpcResourceListener();
            ((ReloadableResourceManager)Minecraft.m_91087_().m_91098_()).m_7217_((PreparableReloadListener)listener);
            listener.m_6213_(Minecraft.m_91087_().m_91098_());
            MenuScreens.m_96206_(CustomContainer.container_carpentrybench, GuiNpcCarpentryBench::new);
            MenuScreens.m_96206_(CustomContainer.container_customgui, (container, inv, comp) -> {
                GuiCustom gui = new GuiCustom((ContainerCustomGui)container, inv, comp);
                gui.setGuiData(container.data);
                return gui;
            });
            MenuScreens.m_96206_(CustomContainer.container_mail, GuiMailmanWrite::new);
            MenuScreens.m_96206_(CustomContainer.container_managebanks, GuiNPCManageBanks::new);
            MenuScreens.m_96206_(CustomContainer.container_managerecipes, GuiNpcManageRecipes::new);
            MenuScreens.m_96206_(CustomContainer.container_merchantadd, GuiMerchantAdd::new);
            MenuScreens.m_96206_(CustomContainer.container_banklarge, GuiNPCBankChest::new);
            MenuScreens.m_96206_(CustomContainer.container_banksmall, GuiNPCBankChest::new);
            MenuScreens.m_96206_(CustomContainer.container_bankunlock, GuiNPCBankChest::new);
            MenuScreens.m_96206_(CustomContainer.container_bankupgrade, GuiNPCBankChest::new);
            MenuScreens.m_96206_(CustomContainer.container_companion, GuiNpcCompanionInv::new);
            MenuScreens.m_96206_(CustomContainer.container_follower, GuiNpcFollower::new);
            MenuScreens.m_96206_(CustomContainer.container_followerhire, GuiNpcFollowerHire::new);
            MenuScreens.m_96206_(CustomContainer.container_followersetup, GuiNpcFollowerSetup::new);
            MenuScreens.m_96206_(CustomContainer.container_inv, GuiNPCInv::new);
            MenuScreens.m_96206_(CustomContainer.container_itemgiver, GuiNpcItemGiver::new);
            MenuScreens.m_96206_(CustomContainer.container_questreward, GuiNpcQuestReward::new);
            MenuScreens.m_96206_(CustomContainer.container_questtypeitem, GuiNpcQuestTypeItem::new);
            MenuScreens.m_96206_(CustomContainer.container_trader, GuiNPCTrader::new);
            MenuScreens.m_96206_(CustomContainer.container_tradersetup, GuiNpcTraderSetup::new);
            new MusicController();
            MinecraftForge.EVENT_BUS.register((Object)new ClientTickHandler());
            Minecraft mc = Minecraft.m_91087_();
            new PresetController(CustomNpcs.Dir);
            if (CustomNpcs.EnableUpdateChecker) {
                VersionChecker checker = new VersionChecker();
                checker.start();
            }
            PixelmonHelper.loadClient();
        });
    }

    @Override
    public PlayerData getPlayerData(Player player) {
        if (player.m_20148_() == Minecraft.m_91087_().f_91074_.m_20148_()) {
            if (ClientProxy.playerData.player != player) {
                ClientProxy.playerData.player = player;
            }
            return playerData;
        }
        return null;
    }

    @Override
    public void postload() {
        MinecraftForge.EVENT_BUS.register((Object)new ClientEventHandler());
        Minecraft.m_91087_().getItemColors().m_92689_((stack, tintIndex) -> 9127187, new ItemLike[]{CustomItems.mount, CustomItems.cloner, CustomItems.moving, CustomItems.scripter, CustomItems.wand, CustomItems.teleporter});
        Minecraft.m_91087_().getItemColors().m_92689_((stack, tintIndex) -> {
            IItemStack item;
            if (stack.m_41720_() == CustomItems.scripted_item && !(item = NpcAPI.Instance().getIItemStack(stack)).isEmpty()) {
                return ((IItemScripted)item).getColor();
            }
            return -1;
        }, new ItemLike[]{CustomItems.scripted_item});
        ArmorersWorkshopHelper.register();
    }

    private void createFolders() {
        File meta;
        File json;
        File check;
        File file = new File(CustomNpcs.Dir, "assets/customnpcs");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!(check = new File(file, "sounds")).exists()) {
            check.mkdir();
        }
        if (!(json = new File(file, "sounds.json")).exists()) {
            try {
                json.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(json));
                writer.write("{\n\n}");
                writer.close();
            }
            catch (IOException writer) {
                // empty catch block
            }
        }
        if (!(meta = new File(CustomNpcs.Dir, "pack.mcmeta")).exists()) {
            try {
                meta.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(meta));
                writer.write("{\n    \"pack\": {\n        \"description\": \"customnpcs map resource pack\",\n        \"pack_format\": 6\n    }\n}");
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!(check = new File(file, "textures")).exists()) {
            check.mkdir();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Screen getGui(EnumGuiType gui, EntityNPCInterface npc, FriendlyByteBuf buf) {
        try {
            if (gui == EnumGuiType.MainMenuDisplay) {
                if (npc != null) {
                    GuiNpcDisplay guiNpcDisplay = new GuiNpcDisplay(npc);
                    return guiNpcDisplay;
                }
                Minecraft.m_91087_().f_91074_.m_213846_((Component)Component.m_237113_((String)"Unable to find npc"));
            } else {
                if (gui == EnumGuiType.MainMenuStats) {
                    GuiNpcStats guiNpcStats = new GuiNpcStats(npc);
                    return guiNpcStats;
                }
                if (gui == EnumGuiType.MainMenuAdvanced) {
                    GuiNpcAdvanced guiNpcAdvanced = new GuiNpcAdvanced(npc);
                    return guiNpcAdvanced;
                }
                if (gui == EnumGuiType.MovingPath) {
                    GuiNpcPather guiNpcPather = new GuiNpcPather(npc);
                    return guiNpcPather;
                }
                if (gui == EnumGuiType.ManageFactions) {
                    GuiNPCManageFactions guiNPCManageFactions = new GuiNPCManageFactions(npc);
                    return guiNPCManageFactions;
                }
                if (gui == EnumGuiType.ManageLinked) {
                    GuiNPCManageLinkedNpc guiNPCManageLinkedNpc = new GuiNPCManageLinkedNpc(npc);
                    return guiNPCManageLinkedNpc;
                }
                if (gui == EnumGuiType.BuilderBlock) {
                    GuiBlockBuilder guiBlockBuilder = new GuiBlockBuilder(buf.m_130135_());
                    return guiBlockBuilder;
                }
                if (gui == EnumGuiType.ManageTransport) {
                    GuiNPCManageTransporters guiNPCManageTransporters = new GuiNPCManageTransporters(npc);
                    return guiNPCManageTransporters;
                }
                if (gui == EnumGuiType.ManageDialogs) {
                    GuiNPCManageDialogs guiNPCManageDialogs = new GuiNPCManageDialogs(npc);
                    return guiNPCManageDialogs;
                }
                if (gui == EnumGuiType.ManageQuests) {
                    GuiNPCManageQuest guiNPCManageQuest = new GuiNPCManageQuest(npc);
                    return guiNPCManageQuest;
                }
                if (gui == EnumGuiType.Companion) {
                    GuiNpcCompanionStats guiNpcCompanionStats = new GuiNpcCompanionStats(npc);
                    return guiNpcCompanionStats;
                }
                if (gui == EnumGuiType.CompanionTalent) {
                    GuiNpcCompanionTalents guiNpcCompanionTalents = new GuiNpcCompanionTalents(npc);
                    return guiNpcCompanionTalents;
                }
                if (gui == EnumGuiType.MainMenuGlobal) {
                    GuiNPCGlobalMainMenu guiNPCGlobalMainMenu = new GuiNPCGlobalMainMenu(npc);
                    return guiNPCGlobalMainMenu;
                }
                if (gui == EnumGuiType.MainMenuAI) {
                    GuiNpcAI guiNpcAI = new GuiNpcAI(npc);
                    return guiNpcAI;
                }
                if (gui == EnumGuiType.PlayerTransporter) {
                    GuiTransportSelection guiTransportSelection = new GuiTransportSelection(npc);
                    return guiTransportSelection;
                }
                if (gui == EnumGuiType.Script) {
                    GuiScript guiScript = new GuiScript(npc);
                    return guiScript;
                }
                if (gui == EnumGuiType.ScriptBlock) {
                    GuiScriptBlock guiScriptBlock = new GuiScriptBlock(buf.m_130135_());
                    return guiScriptBlock;
                }
                if (gui == EnumGuiType.ScriptItem) {
                    GuiScriptItem guiScriptItem = new GuiScriptItem((Player)Minecraft.m_91087_().f_91074_);
                    return guiScriptItem;
                }
                if (gui == EnumGuiType.ScriptDoor) {
                    GuiScriptDoor guiScriptDoor = new GuiScriptDoor(buf.m_130135_());
                    return guiScriptDoor;
                }
                if (gui == EnumGuiType.ScriptPlayers) {
                    GuiScriptGlobal guiScriptGlobal = new GuiScriptGlobal();
                    return guiScriptGlobal;
                }
                if (gui == EnumGuiType.SetupTransporter) {
                    GuiNpcTransporter guiNpcTransporter = new GuiNpcTransporter(npc);
                    return guiNpcTransporter;
                }
                if (gui == EnumGuiType.SetupBank) {
                    GuiNpcBankSetup guiNpcBankSetup = new GuiNpcBankSetup(npc);
                    return guiNpcBankSetup;
                }
                if (gui == EnumGuiType.NpcRemote && Minecraft.m_91087_().f_91080_ == null) {
                    GuiNpcRemoteEditor guiNpcRemoteEditor = new GuiNpcRemoteEditor();
                    return guiNpcRemoteEditor;
                }
                if (gui == EnumGuiType.PlayerMailbox) {
                    GuiMailbox guiMailbox = new GuiMailbox();
                    return guiMailbox;
                }
                if (gui == EnumGuiType.NpcDimensions) {
                    GuiNpcDimension guiNpcDimension = new GuiNpcDimension();
                    return guiNpcDimension;
                }
                if (gui == EnumGuiType.Border) {
                    GuiBorderBlock guiBorderBlock = new GuiBorderBlock(buf.m_130135_());
                    return guiBorderBlock;
                }
                if (gui == EnumGuiType.RedstoneBlock) {
                    GuiNpcRedstoneBlock guiNpcRedstoneBlock = new GuiNpcRedstoneBlock(buf.m_130135_());
                    return guiNpcRedstoneBlock;
                }
                if (gui == EnumGuiType.MobSpawner) {
                    GuiNpcMobSpawner guiNpcMobSpawner = new GuiNpcMobSpawner(buf.m_130135_());
                    return guiNpcMobSpawner;
                }
                if (gui == EnumGuiType.CopyBlock) {
                    GuiBlockCopy guiBlockCopy = new GuiBlockCopy(buf.m_130135_());
                    return guiBlockCopy;
                }
                if (gui == EnumGuiType.MobSpawnerMounter) {
                    GuiNpcMobSpawnerMounter guiNpcMobSpawnerMounter = new GuiNpcMobSpawnerMounter();
                    return guiNpcMobSpawnerMounter;
                }
                if (gui == EnumGuiType.Waypoint) {
                    GuiNpcWaypoint guiNpcWaypoint = new GuiNpcWaypoint(buf.m_130135_());
                    return guiNpcWaypoint;
                }
                if (gui == EnumGuiType.NbtBook) {
                    GuiNbtBook guiNbtBook = new GuiNbtBook(buf.m_130135_());
                    return guiNbtBook;
                }
            }
            Screen screen = null;
            return screen;
        }
        finally {
            if (buf != null) {
                buf.release();
            }
        }
    }

    @Override
    public void openGui(Player player, EnumGuiType gui) {
        Minecraft minecraft = Minecraft.m_91087_();
        if (minecraft.f_91074_ != player) {
            return;
        }
        Screen screen = ClientProxy.getGui(gui, null, null);
        if (screen != null) {
            minecraft.m_91152_(screen);
        }
    }

    @Override
    public void openGui(EntityNPCInterface npc, EnumGuiType gui) {
        Minecraft minecraft = Minecraft.m_91087_();
        minecraft.m_91152_(ClientProxy.getGui(gui, npc, null));
    }

    @Override
    public void openGui(Player player, Object guiscreen) {
        Minecraft minecraft = Minecraft.m_91087_();
        if (!player.m_9236_().f_46443_ || !(guiscreen instanceof Screen)) {
            return;
        }
        if (guiscreen != null) {
            minecraft.m_91152_((Screen)guiscreen);
        }
    }

    @Override
    public void spawnParticle(LivingEntity player, String string, Object ... ob) {
        if (string.equals("Block")) {
            BlockPos pos = (BlockPos)ob[0];
            BlockState state = (BlockState)ob[1];
            Minecraft.m_91087_().f_91061_.m_107355_(pos, state);
        } else if (string.equals("ModelData")) {
            ModelData data = (ModelData)ob[0];
            ModelPartData particles = (ModelPartData)ob[1];
            EntityCustomNpc npc = (EntityCustomNpc)player;
            Minecraft minecraft = Minecraft.m_91087_();
            double height = npc.m_6049_() + (double)data.getBodyY();
            RandomSource randomSource = npc.m_217043_();
        }
    }

    @Override
    public boolean hasClient() {
        return true;
    }

    @Override
    public Player getPlayer() {
        return Minecraft.m_91087_().f_91074_;
    }

    public static void bind(ResourceLocation location) {
        try {
            if (location == null) {
                return;
            }
            TextureManager manager = Minecraft.m_91087_().m_91097_();
            AbstractTexture ob = manager.m_118506_(location);
            if (ob == null) {
                ob = new SimpleTexture(location);
                manager.m_118495_(location, ob);
            }
            RenderSystem.bindTexture((int)ob.m_117963_());
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
    }

    @Override
    public void spawnParticle(ParticleOptions particle, double x, double y, double z, double motionX, double motionY, double motionZ, float scale) {
        double zz;
        double yy;
        Minecraft mc = Minecraft.m_91087_();
        double xx = mc.m_91288_().m_20185_() - x;
        if (xx * xx + (yy = mc.m_91288_().m_20186_() - y) * yy + (zz = mc.m_91288_().m_20189_() - z) * zz > 256.0) {
            return;
        }
        Particle fx = mc.f_91061_.m_107370_(particle, x, y, z, motionX, motionY, motionZ);
        if (fx == null) {
            return;
        }
        if (particle == ParticleTypes.f_123744_) {
            fx.m_6569_(1.0E-5f);
        } else if (particle == ParticleTypes.f_123762_) {
            fx.m_6569_(1.0E-5f);
        }
    }

    public static class FontContainer {
        private TrueTypeFont textFont = null;
        public boolean useCustomFont = true;

        private FontContainer() {
        }

        public FontContainer(String fontType, int fontSize) {
            try {
                this.textFont = new TrueTypeFont(new Font(fontType, 0, fontSize), 1.0f);
                boolean bl = this.useCustomFont = !fontType.equalsIgnoreCase("minecraft");
                if (!this.useCustomFont || fontType.isEmpty() || fontType.equalsIgnoreCase("default")) {
                    this.textFont = new TrueTypeFont(new ResourceLocation("customnpcs", "opensans.ttf"), fontSize, 1.0f);
                }
            }
            catch (Throwable e) {
                LogWriter.except(e);
                this.useCustomFont = false;
            }
        }

        public int height(String text) {
            if (this.useCustomFont) {
                return this.textFont.height(text);
            }
            Objects.requireNonNull(Minecraft.m_91087_().f_91062_);
            return 9;
        }

        public int width(String text) {
            if (this.useCustomFont) {
                return this.textFont.width(text);
            }
            return Minecraft.m_91087_().f_91062_.m_92895_(text);
        }

        public FontContainer copy() {
            FontContainer font = new FontContainer();
            font.textFont = this.textFont;
            font.useCustomFont = this.useCustomFont;
            return font;
        }

        public void draw(GuiGraphics graphics, String text, int x, int y, int color) {
            if (this.useCustomFont) {
                this.textFont.draw(graphics.m_280168_(), text, x, y, color);
            } else {
                graphics.m_280488_(Minecraft.m_91087_().f_91062_, text, x, y, color);
            }
        }

        public String getName() {
            if (!this.useCustomFont) {
                return "Minecraft";
            }
            return this.textFont.getFontName();
        }

        public void clear() {
            if (this.textFont != null) {
                this.textFont.dispose();
            }
        }
    }
}

