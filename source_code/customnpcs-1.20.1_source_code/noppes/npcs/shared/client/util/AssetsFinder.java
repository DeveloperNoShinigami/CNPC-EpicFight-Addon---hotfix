/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ResourceLocationException
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.PackType
 */
package noppes.npcs.shared.client.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import noppes.npcs.shared.common.util.LogWriter;

public class AssetsFinder {
    private static List<ResourceLocation> list = new ArrayList<ResourceLocation>();
    private static String root;
    private static String type;

    public static List<ResourceLocation> find(String root, String type) {
        AssetsFinder.root = root;
        AssetsFinder.type = type;
        list.clear();
        ArrayList<ResourceLocation> resources = new ArrayList<ResourceLocation>();
        Minecraft.m_91087_().m_91098_().m_7536_().forEach(p -> {
            for (String s : p.m_5698_(PackType.CLIENT_RESOURCES)) {
                try {
                    p.m_8031_(PackType.CLIENT_RESOURCES, s, root, (r, streamIoSupplier) -> {
                        if (r.toString().endsWith(type)) {
                            resources.add((ResourceLocation)r);
                        }
                    });
                }
                catch (ResourceLocationException e) {
                    LogWriter.except(e);
                }
            }
        });
        return resources;
    }
}

