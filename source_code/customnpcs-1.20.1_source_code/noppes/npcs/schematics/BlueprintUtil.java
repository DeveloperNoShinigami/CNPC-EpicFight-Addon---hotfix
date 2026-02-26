/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.HolderGetter
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.fml.ModList
 */
package noppes.npcs.schematics;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import noppes.npcs.schematics.Blueprint;

public class BlueprintUtil {
    public static CompoundTag writeBlueprintToNBT(Blueprint schem) {
        CompoundTag compound = new CompoundTag();
        compound.m_128344_("version", (byte)1);
        compound.m_128376_("size_x", schem.getSizeX());
        compound.m_128376_("size_y", schem.getSizeY());
        compound.m_128376_("size_z", schem.getSizeZ());
        BlockState[] palette = schem.getPallete();
        ListTag paletteTag = new ListTag();
        for (short i = 0; i < schem.getPalleteSize(); i = (short)(i + 1)) {
            paletteTag.add((Object)NbtUtils.m_129202_((BlockState)palette[i]));
        }
        compound.m_128365_("palette", (Tag)paletteTag);
        int[] blockInt = BlueprintUtil.convertBlocksToSaveData(schem.getStructure(), schem.getSizeX(), schem.getSizeY(), schem.getSizeZ());
        compound.m_128385_("blocks", blockInt);
        ListTag finishedTes = new ListTag();
        CompoundTag[] tes = schem.getTileEntities();
        for (int i = 0; i < tes.length; ++i) {
            finishedTes.add((Object)tes[i]);
        }
        compound.m_128365_("tile_entities", (Tag)finishedTes);
        List<String> requiredMods = schem.getRequiredMods();
        ListTag modsList = new ListTag();
        for (int i = 0; i < requiredMods.size(); ++i) {
            modsList.add((Object)StringTag.m_129297_((String)requiredMods.get(i)));
        }
        compound.m_128365_("required_mods", (Tag)modsList);
        String name = schem.getName();
        String[] architects = schem.getArchitects();
        if (name != null) {
            compound.m_128359_("name", name);
        }
        if (architects != null) {
            ListTag architectsTag = new ListTag();
            for (String architect : architects) {
                architectsTag.add((Object)StringTag.m_129297_((String)architect));
            }
            compound.m_128365_("architects", (Tag)architectsTag);
        }
        return compound;
    }

    public static Blueprint readBlueprintFromNBT(CompoundTag tag) {
        byte version = tag.m_128445_("version");
        if (version == 1) {
            short sizeX = tag.m_128448_("size_x");
            short sizeY = tag.m_128448_("size_y");
            short sizeZ = tag.m_128448_("size_z");
            ArrayList<String> requiredMods = new ArrayList<String>();
            ListTag modsList = tag.m_128437_("required_mods", 8);
            int modListSize = modsList.size();
            for (int i = 0; i < modListSize; ++i) {
                requiredMods.add(((StringTag)modsList.get(i)).m_7916_());
                if (ModList.get().isLoaded((String)requiredMods.get(i))) continue;
                Logger.getGlobal().log(Level.WARNING, "Couldn't load Blueprint, the following mod is missing: " + (String)requiredMods.get(i));
                return null;
            }
            ListTag paletteTag = tag.m_128437_("palette", 10);
            short paletteSize = (short)paletteTag.size();
            BlockState[] palette = new BlockState[paletteSize];
            for (int i = 0; i < palette.length; i = (int)((short)(i + 1))) {
                palette[i] = NbtUtils.m_247651_((HolderGetter)BuiltInRegistries.f_256975_.m_255303_(), (CompoundTag)paletteTag.m_128728_(i));
            }
            short[][][] blocks = BlueprintUtil.convertSaveDataToBlocks(tag.m_128465_("blocks"), sizeX, sizeY, sizeZ);
            ListTag teTag = tag.m_128437_("tile_entities", 10);
            CompoundTag[] tileEntities = new CompoundTag[teTag.size()];
            for (int i = 0; i < tileEntities.length; i = (int)((short)(i + 1))) {
                tileEntities[i] = teTag.m_128728_(i);
            }
            Blueprint schem = new Blueprint(sizeX, sizeY, sizeZ, paletteSize, palette, blocks, tileEntities, requiredMods);
            if (tag.m_128441_("name")) {
                schem.setName(tag.m_128461_("name"));
            }
            if (tag.m_128441_("architects")) {
                ListTag architectsTag = tag.m_128437_("architects", 8);
                String[] architects = new String[architectsTag.size()];
                for (int i = 0; i < architectsTag.size(); ++i) {
                    architects[i] = architectsTag.m_128778_(i);
                }
                schem.setArchitects(architects);
            }
            return schem;
        }
        return null;
    }

    private static int[] convertBlocksToSaveData(short[][][] multDimArray, short sizeX, short sizeY, short sizeZ) {
        short[] oneDimArray = new short[sizeX * sizeY * sizeZ];
        int j = 0;
        for (short y = 0; y < sizeY; y = (short)(y + 1)) {
            for (short z = 0; z < sizeZ; z = (short)(z + 1)) {
                for (short x = 0; x < sizeX; x = (short)(x + 1)) {
                    oneDimArray[j++] = multDimArray[y][z][x];
                }
            }
        }
        int[] ints = new int[(int)Math.ceil((float)oneDimArray.length / 2.0f)];
        int currentInt = 0;
        for (int i = 1; i < oneDimArray.length; i += 2) {
            currentInt = oneDimArray[i - 1];
            ints[(int)Math.ceil((double)((double)((float)i / 2.0f))) - 1] = currentInt = currentInt << 16 | oneDimArray[i];
            currentInt = 0;
        }
        if (oneDimArray.length % 2 == 1) {
            ints[ints.length - 1] = currentInt = oneDimArray[oneDimArray.length - 1] << 16;
        }
        return ints;
    }

    public static short[][][] convertSaveDataToBlocks(int[] ints, short sizeX, short sizeY, short sizeZ) {
        short[] oneDimArray = new short[ints.length * 2];
        for (int i = 0; i < ints.length; ++i) {
            oneDimArray[i * 2] = (short)(ints[i] >> 16);
            oneDimArray[i * 2 + 1] = (short)ints[i];
        }
        short[][][] multDimArray = new short[sizeY][sizeZ][sizeX];
        int i = 0;
        for (short y = 0; y < sizeY; y = (short)(y + 1)) {
            for (short z = 0; z < sizeZ; z = (short)(z + 1)) {
                for (short x = 0; x < sizeX; x = (short)(x + 1)) {
                    multDimArray[y][z][x] = oneDimArray[i++];
                }
            }
        }
        return multDimArray;
    }
}

