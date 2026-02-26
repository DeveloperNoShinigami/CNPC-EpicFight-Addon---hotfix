/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.arguments.selector.EntitySelector
 *  net.minecraft.commands.arguments.selector.EntitySelectorParser
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec2
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.entity.data;

import com.mojang.brigadier.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.constants.AnimationType;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.shared.common.CommonUtil;
import noppes.npcs.util.ValueUtil;

public class DataScenes {
    private EntityNPCInterface npc;
    public List<SceneContainer> scenes = new ArrayList<SceneContainer>();
    public static Map<String, SceneState> StartedScenes = new HashMap<String, SceneState>();
    public static List<SceneContainer> ScenesToRun = new ArrayList<SceneContainer>();
    private LivingEntity owner = null;
    private String ownerScene = null;

    public DataScenes(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public CompoundTag save(CompoundTag compound) {
        ListTag list = new ListTag();
        for (SceneContainer scene : this.scenes) {
            list.add((Object)scene.save(new CompoundTag()));
        }
        compound.m_128365_("Scenes", (Tag)list);
        return compound;
    }

    public void load(CompoundTag compound) {
        ListTag list = compound.m_128437_("Scenes", 10);
        ArrayList<SceneContainer> scenes = new ArrayList<SceneContainer>();
        for (int i = 0; i < list.size(); ++i) {
            SceneContainer scene = new SceneContainer();
            scene.load(list.m_128728_(i));
            scenes.add(scene);
        }
        this.scenes = scenes;
    }

    public LivingEntity getOwner() {
        return this.owner;
    }

    public static void Toggle(MinecraftServer server, String id) {
        SceneState state = StartedScenes.get(id.toLowerCase());
        if (state == null || state.paused) {
            DataScenes.Start(server, id);
        } else {
            state.paused = true;
            CommonUtil.NotifyOPs(server, "Paused scene %s at %s", id, state.ticks);
        }
    }

    public static void Start(MinecraftServer server, String id) {
        SceneState state = StartedScenes.get(id.toLowerCase());
        if (state == null) {
            CommonUtil.NotifyOPs(server, "Started scene %s", id);
            StartedScenes.put(id.toLowerCase(), new SceneState());
        } else if (state.paused) {
            state.paused = false;
            CommonUtil.NotifyOPs(server, "Started scene %s from %s", id, state.ticks);
        }
    }

    public static void Pause(CommandSourceStack sender, String id) {
        if (id == null) {
            for (SceneState state : StartedScenes.values()) {
                state.paused = true;
            }
            CommonUtil.NotifyOPs(sender.m_81377_(), "Paused all scenes", new Object[0]);
        } else {
            SceneState state = StartedScenes.get(id.toLowerCase());
            if (state == null) {
                sender.m_288197_(() -> Component.m_237110_((String)"Unknown scene %s ", (Object[])new Object[]{id}), false);
            } else {
                state.paused = true;
                CommonUtil.NotifyOPs(sender.m_81377_(), "Paused scene %s at %s", id, state.ticks);
            }
        }
    }

    public static void Reset(CommandSourceStack sender, String id) {
        if (id == null) {
            if (StartedScenes.isEmpty()) {
                return;
            }
            StartedScenes = new HashMap<String, SceneState>();
            CommonUtil.NotifyOPs(sender.m_81377_(), "Reset all scene", new Object[0]);
        } else if (StartedScenes.remove(id.toLowerCase()) == null) {
            sender.m_288197_(() -> Component.m_237110_((String)"Unknown scene %s ", (Object[])new Object[]{id}), false);
        } else {
            CommonUtil.NotifyOPs(sender.m_81377_(), "Reset scene %s", id);
        }
    }

    public void update() {
        for (SceneContainer scene : this.scenes) {
            if (!scene.validState()) continue;
            ScenesToRun.add(scene);
        }
        if (this.owner != null && !StartedScenes.containsKey(this.ownerScene.toLowerCase())) {
            this.owner = null;
            this.ownerScene = null;
        }
    }

    public void addScene(String name) {
        if (name.isEmpty()) {
            return;
        }
        SceneContainer scene = new SceneContainer();
        scene.name = name;
        this.scenes.add(scene);
    }

    public class SceneContainer {
        public int btn = 0;
        public String name = "";
        public String lines = "";
        public boolean enabled = false;
        public int ticks = -1;
        private SceneState state = null;
        private List<SceneEvent> events = new ArrayList<SceneEvent>();

        public CompoundTag save(CompoundTag compound) {
            compound.m_128379_("Enabled", this.enabled);
            compound.m_128359_("Name", this.name);
            compound.m_128359_("Lines", this.lines);
            compound.m_128405_("Button", this.btn);
            compound.m_128405_("Ticks", this.ticks);
            return compound;
        }

        public boolean validState() {
            if (!this.enabled) {
                return false;
            }
            if (this.state != null) {
                if (StartedScenes.containsValue(this.state)) {
                    return !this.state.paused;
                }
                this.state = null;
            }
            this.state = StartedScenes.get(this.name.toLowerCase());
            if (this.state == null) {
                this.state = StartedScenes.get(this.btn + "btn");
            }
            if (this.state != null) {
                return !this.state.paused;
            }
            return false;
        }

        public void load(CompoundTag compound) {
            this.enabled = compound.m_128471_("Enabled");
            this.name = compound.m_128461_("Name");
            this.lines = compound.m_128461_("Lines");
            this.btn = compound.m_128451_("Button");
            this.ticks = compound.m_128451_("Ticks");
            ArrayList<SceneEvent> events = new ArrayList<SceneEvent>();
            for (String line : this.lines.split("\r\n|\r|\n")) {
                SceneEvent event = SceneEvent.parse(line);
                if (event == null) continue;
                events.add(event);
            }
            Collections.sort(events);
            this.events = events;
        }

        public void update() {
            if (!this.enabled || this.events.isEmpty() || this.state == null) {
                return;
            }
            for (SceneEvent event : this.events) {
                if (event.ticks > this.state.ticks) break;
                if (event.ticks != this.state.ticks) continue;
                try {
                    this.handle(event);
                }
                catch (Exception exception) {}
            }
            this.ticks = this.state.ticks;
        }

        private LivingEntity getEntity(String name) {
            try {
                EntitySelector selector = new EntitySelectorParser(new StringReader(name)).m_121377_();
                final Level level = DataScenes.this.npc.m_9236_();
                Vec3 point = new Vec3(DataScenes.this.npc.m_20185_() + 0.5, DataScenes.this.npc.m_20186_() + 0.5, DataScenes.this.npc.m_20189_() + 0.5);
                CommandSourceStack commandSource = new CommandSourceStack((CommandSource)DataScenes.this.npc.getFakeChatPlayer(), point, Vec2.f_82462_, (ServerLevel)level, CustomNpcs.NpcUseOpCommands ? 4 : 2, "@CustomNPCs-" + name, (Component)Component.m_237113_((String)("@CustomNPCs-" + name)), level.m_7654_(), (Entity)DataScenes.this.npc){

                    public void m_81352_(Component text) {
                        super.m_81352_(text);
                        CommonUtil.NotifyOPs(level.m_7654_(), text);
                    }
                };
                Entity entity = selector.m_121139_(commandSource);
                if (entity instanceof LivingEntity) {
                    return (LivingEntity)entity;
                }
            }
            catch (Exception selector) {
                // empty catch block
            }
            UUID uuid = null;
            try {
                uuid = UUID.fromString(name);
            }
            catch (Exception exception) {
                // empty catch block
            }
            for (Entity entity : ((ServerLevel)DataScenes.this.npc.m_20193_()).m_142646_().m_142273_()) {
                if (!(entity instanceof LivingEntity)) continue;
                if (uuid != null && entity.m_20148_() == uuid) {
                    return (LivingEntity)entity;
                }
                if (!name.equalsIgnoreCase(entity.m_7755_().getString())) continue;
                return (LivingEntity)entity;
            }
            return null;
        }

        private BlockPos parseBlockPos(BlockPos blockpos, String[] args, int startIndex, boolean centerBlock) throws Exception {
            return new BlockPos((int)this.parseDouble(blockpos.m_123341_(), args[startIndex], -30000000, 30000000, centerBlock), (int)this.parseDouble(blockpos.m_123342_(), args[startIndex + 1], -64, 319, false), (int)this.parseDouble(blockpos.m_123343_(), args[startIndex + 2], -30000000, 30000000, centerBlock));
        }

        private double parseDouble(double base, String input, int min, int max, boolean centerBlock) throws Exception {
            double d0;
            boolean flag = input.startsWith("~");
            if (flag && Double.isNaN(base)) {
                throw new Exception("invalid number");
            }
            double d = d0 = flag ? base : 0.0;
            if (!flag || input.length() > 1) {
                boolean flag1 = input.contains(".");
                if (flag) {
                    input = input.substring(1);
                }
                d0 += Double.parseDouble(input);
                if (!flag1 && !flag && centerBlock) {
                    d0 += 0.5;
                }
            }
            if (min != 0 || max != 0) {
                if (d0 < (double)min) {
                    throw new Exception("number too small");
                }
                if (d0 > (double)max) {
                    throw new Exception("number too big");
                }
            }
            return d0;
        }

        private void handle(SceneEvent event) throws Exception {
            block64: {
                if (event.type == SceneType.MOVE) {
                    String[] param = event.param.split(" ");
                    while (param.length > 1) {
                        boolean move = false;
                        if (param[0].startsWith("to")) {
                            move = true;
                        } else if (!param[0].startsWith("tp")) break;
                        BlockPos pos = null;
                        if (param[0].startsWith("@")) {
                            LivingEntity entitylivingbase = this.getEntity(param[0]);
                            if (entitylivingbase != null) {
                                pos = entitylivingbase.m_20183_();
                            }
                            param = Arrays.copyOfRange(param, 2, param.length);
                        } else {
                            if (param.length < 4) {
                                return;
                            }
                            pos = this.parseBlockPos(DataScenes.this.npc.m_20183_(), param, 1, false);
                            param = Arrays.copyOfRange(param, 4, param.length);
                        }
                        if (pos == null) continue;
                        DataScenes.this.npc.ais.setStartPos(pos);
                        DataScenes.this.npc.m_21573_().m_26573_();
                        if (move) {
                            Path pathentity = DataScenes.this.npc.m_21573_().m_7864_(pos, 0);
                            DataScenes.this.npc.m_21573_().m_26536_(pathentity, 1.0);
                            continue;
                        }
                        if (DataScenes.this.npc.isInRange((double)pos.m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5, 2.0)) continue;
                        DataScenes.this.npc.m_6034_((double)pos.m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5);
                    }
                } else if (event.type == SceneType.SAY) {
                    DataScenes.this.npc.saySurrounding(new Line(event.param));
                } else if (event.type == SceneType.ROTATE) {
                    if (event.param.startsWith("@")) {
                        LivingEntity entitylivingbase = this.getEntity(event.param);
                        DataScenes.this.npc.lookAi.rotate((Entity)DataScenes.this.npc.m_9236_().m_45930_((Entity)entitylivingbase, 30.0));
                    } else if (event.param.equals("clear")) {
                        DataScenes.this.npc.lookAi.m_8041_();
                    } else {
                        DataScenes.this.npc.lookAi.rotate(Integer.parseInt(event.param));
                    }
                } else if (event.type == SceneType.EQUIP) {
                    String[] args = event.param.split(" ");
                    if (args.length < 2) {
                        return;
                    }
                    IItemStack itemstack = null;
                    if (!args[1].equalsIgnoreCase("none")) {
                        ResourceLocation resourcelocation = new ResourceLocation(args[1]);
                        Item item = (Item)ForgeRegistries.ITEMS.getValue(resourcelocation);
                        int i = args.length >= 3 ? ValueUtil.CorrectInt(Integer.parseInt(args[2]), 1, 64) : 1;
                        itemstack = NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)item, i));
                    }
                    if (args[0].equalsIgnoreCase("main")) {
                        DataScenes.this.npc.inventory.weapons.put(0, itemstack);
                    } else if (args[0].equalsIgnoreCase("off")) {
                        DataScenes.this.npc.inventory.weapons.put(2, itemstack);
                    } else if (args[0].equalsIgnoreCase("proj")) {
                        DataScenes.this.npc.inventory.weapons.put(1, itemstack);
                    } else if (args[0].equalsIgnoreCase("head")) {
                        DataScenes.this.npc.inventory.armor.put(0, itemstack);
                    } else if (args[0].equalsIgnoreCase("body")) {
                        DataScenes.this.npc.inventory.armor.put(1, itemstack);
                    } else if (args[0].equalsIgnoreCase("legs")) {
                        DataScenes.this.npc.inventory.armor.put(2, itemstack);
                    } else if (args[0].equalsIgnoreCase("boots")) {
                        DataScenes.this.npc.inventory.armor.put(3, itemstack);
                    }
                } else if (event.type == SceneType.ATTACK) {
                    if (event.param.equals("none")) {
                        DataScenes.this.npc.m_6710_(null);
                    } else {
                        LivingEntity entity = this.getEntity(event.param);
                        if (entity != null) {
                            DataScenes.this.npc.m_6710_(entity);
                        }
                    }
                } else if (event.type == SceneType.THROW) {
                    String[] args = event.param.split(" ");
                    LivingEntity entity = this.getEntity(args[0]);
                    if (entity == null) {
                        return;
                    }
                    float damage = Float.parseFloat(args[1]);
                    if (damage <= 0.0f) {
                        damage = 0.01f;
                    }
                    ItemStack stack = ItemStackWrapper.MCItem(DataScenes.this.npc.inventory.getProjectile());
                    if (args.length > 2) {
                        ResourceLocation resourcelocation = new ResourceLocation(args[2]);
                        Item item = (Item)ForgeRegistries.ITEMS.getValue(resourcelocation);
                        stack = new ItemStack((ItemLike)item, 1);
                    }
                    EntityProjectile projectile = DataScenes.this.npc.shoot(entity, 100, stack, false);
                    projectile.damage = damage;
                } else if (event.type == SceneType.ANIMATE) {
                    DataScenes.this.npc.animateAi.temp = AnimationType.valueOf(event.param);
                } else if (event.type == SceneType.COMMAND) {
                    NoppesUtilServer.runCommand((Entity)DataScenes.this.npc, DataScenes.this.npc.m_7755_().getString(), event.param, null);
                } else if (event.type == SceneType.STATS) {
                    int i = event.param.indexOf(" ");
                    if (i <= 0) {
                        return;
                    }
                    String type = event.param.substring(0, i).toLowerCase();
                    String value = event.param.substring(i).trim();
                    try {
                        if (type.equals("walking_speed")) {
                            DataScenes.this.npc.ais.setWalkingSpeed(ValueUtil.CorrectInt(Integer.parseInt(value), 0, 10));
                            break block64;
                        }
                        if (type.equals("size")) {
                            DataScenes.this.npc.display.setSize(ValueUtil.CorrectInt(Integer.parseInt(value), 1, 30));
                            break block64;
                        }
                        CommonUtil.NotifyOPs(DataScenes.this.npc.m_9236_().m_7654_(), "Unknown scene stat: " + type, new Object[0]);
                    }
                    catch (NumberFormatException e) {
                        CommonUtil.NotifyOPs(DataScenes.this.npc.m_9236_().m_7654_(), "Unknown scene stat " + type + " value: " + value, new Object[0]);
                    }
                } else if (event.type == SceneType.FACTION) {
                    DataScenes.this.npc.setFaction(Integer.parseInt(event.param));
                } else if (event.type == SceneType.FOLLOW) {
                    if (event.param.equalsIgnoreCase("none")) {
                        DataScenes.this.owner = null;
                        DataScenes.this.ownerScene = null;
                    } else {
                        LivingEntity entity = this.getEntity(event.param);
                        if (entity == null) {
                            return;
                        }
                        DataScenes.this.owner = entity;
                        DataScenes.this.ownerScene = this.name;
                    }
                }
            }
        }
    }

    public static class SceneState {
        public boolean paused = false;
        public int ticks = -1;
    }

    public static enum SceneType {
        ANIMATE,
        MOVE,
        FACTION,
        COMMAND,
        EQUIP,
        THROW,
        ATTACK,
        FOLLOW,
        SAY,
        ROTATE,
        STATS;

    }

    public static class SceneEvent
    implements Comparable<SceneEvent> {
        public int ticks = 0;
        public SceneType type;
        public String param = "";

        public String toString() {
            return this.ticks + " " + this.type.name() + " " + this.param;
        }

        public static SceneEvent parse(String str) {
            SceneEvent event = new SceneEvent();
            int i = str.indexOf(" ");
            if (i <= 0) {
                return null;
            }
            try {
                event.ticks = Integer.parseInt(str.substring(0, i));
                str = str.substring(i + 1);
            }
            catch (NumberFormatException ex) {
                return null;
            }
            i = str.indexOf(" ");
            if (i <= 0) {
                return null;
            }
            String name = str.substring(0, i);
            for (SceneType type : SceneType.values()) {
                if (!name.equalsIgnoreCase(type.name())) continue;
                event.type = type;
            }
            if (event.type == null) {
                return null;
            }
            event.param = str.substring(i + 1);
            return event;
        }

        @Override
        public int compareTo(SceneEvent o) {
            return this.ticks - o.ticks;
        }
    }
}

