/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.packs.resources.Resource
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomNpcs;
import noppes.npcs.schematics.Blueprint;
import noppes.npcs.schematics.BlueprintUtil;
import noppes.npcs.schematics.ISchematic;
import noppes.npcs.schematics.Schematic;
import noppes.npcs.schematics.SchematicWrapper;
import noppes.npcs.schematics.SpongeSchem;
import noppes.npcs.shared.common.CommonUtil;
import noppes.npcs.shared.common.util.LogWriter;
import noppes.npcs.util.ValueUtil;

public class SchematicController {
    public static SchematicController Instance = new SchematicController();
    private SchematicWrapper building = null;
    private CommandSourceStack buildStarter = null;
    private int buildingPercentage = 0;
    public List<String> included = Arrays.asList("archery_range.schematic", "bakery.schematic", "barn.schematic", "building_site.schematic", "chapel.schematic", "church.schematic", "gate.schematic", "glassworks.schematic", "guard_tower.schematic", "guild_house.schematic", "house.schematic", "house_small.schematic", "inn.schematic", "library.schematic", "lighthouse.schematic", "mill.schematic", "observatory.schematic", "ship.schematic", "shop.schematic", "stall.schematic", "stall2.schematic", "stall3.schematic", "tier_house1.schematic", "tier_house2.schematic", "tier_house3.schematic", "tower.schematic", "wall.schematic", "wall_corner.schematic");

    public List<String> list() {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(this.included);
        for (File file : this.getDir().listFiles()) {
            String name = file.getName();
            if (!ValueUtil.isValidPath(name) || !name.toLowerCase().endsWith(".schematic") && !name.toLowerCase().endsWith(".schem") && !name.toLowerCase().endsWith(".blueprint")) continue;
            list.add(name);
        }
        Collections.sort(list);
        return list;
    }

    public File getDir() {
        File dir = new File(CustomNpcs.getLevelSaveDirectory(), "schematics");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    public void info(CommandSourceStack sender) {
        if (this.building == null) {
            this.sendMessage(sender, "Nothing is being build");
        } else {
            this.sendMessage(sender, "Already building: " + this.building.schema.getName() + " - " + this.building.getPercentage() + "%");
            if (this.buildStarter != null) {
                this.sendMessage(sender, "Build started by: " + this.buildStarter.m_81357_().getString());
            }
        }
    }

    private void sendMessage(CommandSourceStack sender, String message) {
        if (sender == null) {
            return;
        }
        sender.m_288197_(() -> Component.m_237113_((String)message), false);
    }

    public void stop(CommandSourceStack sender) {
        if (this.building == null || !this.building.isBuilding) {
            this.sendMessage(sender, "Not building");
        } else {
            this.sendMessage(sender, "Stopped building: " + this.building.schema.getName());
            this.building = null;
        }
    }

    public void build(SchematicWrapper schem, CommandSourceStack sender) {
        if (this.building != null && this.building.isBuilding) {
            this.info(sender);
            return;
        }
        this.buildingPercentage = 0;
        this.building = schem;
        this.building.isBuilding = true;
        this.buildStarter = sender;
    }

    public void updateBuilding() {
        if (this.building == null) {
            return;
        }
        this.building.build();
        if (this.buildStarter != null && this.building.getPercentage() - this.buildingPercentage >= 10) {
            this.sendMessage(this.buildStarter, "Building at " + this.building.getPercentage() + "%");
            this.buildingPercentage = this.building.getPercentage();
        }
        if (!this.building.isBuilding) {
            if (this.buildStarter != null) {
                this.sendMessage(this.buildStarter, "Building finished");
            }
            this.building = null;
        }
    }

    public SchematicWrapper load(String name) {
        InputStream stream = null;
        if (this.included.contains(name)) {
            ResourceLocation resource = new ResourceLocation("customnpcs", "schematics/" + name);
            Resource ir = CustomNpcs.Server.getServerResources().f_206584_().m_213713_(resource).orElse(null);
            if (ir != null) {
                try {
                    stream = ir.m_215507_();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        if (stream == null) {
            File file = new File(this.getDir(), name);
            if (!file.exists()) {
                return null;
            }
            try {
                stream = new FileInputStream(file);
            }
            catch (FileNotFoundException e) {
                return null;
            }
        }
        try {
            ISchematic bp;
            CompoundTag compound = NbtIo.m_128939_(stream);
            stream.close();
            if (name.toLowerCase().endsWith(".schem")) {
                bp = new SpongeSchem(name);
                ((SpongeSchem)bp).load(compound);
                return new SchematicWrapper(bp);
            }
            if (name.toLowerCase().endsWith(".blueprint")) {
                bp = BlueprintUtil.readBlueprintFromNBT(compound);
                ((Blueprint)bp).setName(name);
                return new SchematicWrapper(bp);
            }
            Schematic schema = new Schematic(name);
            schema.load(compound);
            return new SchematicWrapper(schema);
        }
        catch (IOException e) {
            LogWriter.except(e);
            return null;
        }
    }

    public void save(CommandSourceStack sender, String name, BlockPos pos, short height, short width, short length) {
        if (this.included.contains(name = name.replace(" ", "_"))) {
            return;
        }
        ServerLevel level = sender.m_81372_();
        File file = new File(this.getDir(), name + ".schem");
        SpongeSchem schema = SpongeSchem.Create((Level)level, name, pos, height, width, length);
        CommonUtil.NotifyOPs(sender.m_81377_(), "Schematic " + name + " succesfully created", new Object[0]);
        try {
            NbtIo.m_128947_((CompoundTag)schema.getNBT(), (OutputStream)new FileOutputStream(file));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

