package com.goodbird.cnpcefaddon.common;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
import com.goodbird.cnpcefaddon.client.render.RenderStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import noppes.npcs.CustomEntities;
import yesman.epicfight.main.EpicFightSharedConstants;
import yesman.epicfight.world.capabilities.provider.EntityPatchProvider;

import java.util.Map;

public class AdvNpcPatchReloader extends SimpleJsonResourceReloadListener {
	private static final Gson GSON = new GsonBuilder().create();

	public AdvNpcPatchReloader() {
		super(GSON, "adv_npc_epicfight_mobpatch");
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn,
			ProfilerFiller profilerIn) {
		for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
			CompoundTag tag = null;
			try {
				tag = TagParser.parseTag(entry.getValue().toString());
			} catch (CommandSyntaxException e) {
				e.printStackTrace();
			}
			if (tag == null) {
				continue;
			}

			NpcPatchReloadListener.branchPatchProvider.addProvider(entry.getKey(),
					com.goodbird.cnpcefaddon.common.compatibility.IndestructibleCompat.deserializeAdvancedNpcPatchProvider(tag, false, resourceManagerIn));
			if (tag.contains("nbt_tag")) {
				try {
					CompoundTag matcher = TagParser.parseTag(tag.getString("nbt_tag"));
					NpcPatchReloadListener.branchPatchProvider.addNbtProvider(entry.getKey(), matcher,
							com.goodbird.cnpcefaddon.common.compatibility.IndestructibleCompat.deserializeAdvancedNpcPatchProvider(tag, false, resourceManagerIn));
				} catch (CommandSyntaxException e) {
					CNPCEpicFightAddon.LOGGER.error("Invalid CNPC nbt_tag for {}", entry.getKey(), e);
				}
			}
			NpcPatchReloadListener.AVAILABLE_MODELS.add(entry.getKey());

			CompoundTag filteredTag = com.goodbird.cnpcefaddon.common.compatibility.IndestructibleCompat.filterClientData(tag);
			filteredTag.putString("patchType", "ADVANCED");
			NpcPatchReloadListener.TAGMAP.put(entry.getKey(), filteredTag);

			NpcPatchReloadListener.bindEntityPatchProvider();

			if (EpicFightSharedConstants.isPhysicalClient()) {
				RenderStorage.registerRenderer(entry.getKey(),
						tag.contains("preset") ? tag.getString("preset") : tag.getString("renderer"));
			}
		}
		CNPCEpicFightAddon.LOGGER.info("Loaded advanced CNPC Epic Fight mobpatch JSON files: {}", objectIn.size());
	}
}
