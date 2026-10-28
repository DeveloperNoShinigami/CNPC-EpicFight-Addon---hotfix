package com.goodbird.cnpcefaddon.common;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
import com.goodbird.cnpcefaddon.common.provider.NpcPatchProvider;
import com.goodbird.cnpcefaddon.common.compatibility.IndestructibleCompat;
import com.goodbird.cnpcefaddon.client.render.RenderStorage;
import com.goodbird.cnpcefaddon.common.network.SPDatapackSync;
import com.goodbird.cnpcefaddon.common.patch.INpcPatch;
import com.goodbird.cnpcefaddon.common.provider.INpcPatchProvider;
import com.goodbird.cnpcefaddon.common.provider.NpcBranchPatchProvider;
import com.goodbird.cnpcefaddon.common.provider.NpcHumanoidPatchProvider;
import com.goodbird.cnpcefaddon.mixin.impl.ICustomHumanoidMobPatchProvider;
import com.goodbird.cnpcefaddon.mixin.impl.ICustomMobPatchProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.CustomEntities;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.main.EpicFightSharedConstants;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.provider.EntityPatchProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class NpcPatchReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).create();

    public static NpcBranchPatchProvider branchPatchProvider = new NpcBranchPatchProvider();
    private static volatile NpcBranchPatchProvider clientBranchPatchProvider = new NpcBranchPatchProvider();
    public static Set<ResourceLocation> AVAILABLE_MODELS = new HashSet<>();
    public static Map<ResourceLocation, CompoundTag> TAGMAP = Maps.newHashMap();
    private static volatile boolean CLIENT_DATA_READY;
    /**
     * Selection packets may arrive before the client has deserialized the
     * synced tables. Keep this field in the common class on both physical
     * sides: Forge's dist cleaner can remove an {@code @OnlyIn} field while
     * the common reload listener is being initialized on the server, which
     * leaves the client-only packet methods with a stale field reference and
     * causes {@code NoSuchFieldError} during datapack reload.
     */
    private static final Set<Integer> CLIENT_REFRESH_PENDING = new HashSet<>();
    /**
     * Server-authoritative selections must survive replacement of the client
     * provider during datapack synchronization.  The provider itself is
     * intentionally replaceable, so keeping this state on the provider made
     * an otherwise valid selection disappear during reload/login ordering.
     */
    private static final Map<Integer, ResourceLocation> CLIENT_AUTHORITATIVE_SELECTIONS = Maps.newHashMap();
    private static final Set<Integer> CLIENT_AUTHORITATIVE_SELECTION_KNOWN = new HashSet<>();

    public NpcPatchReloadListener() {
        super(GSON, "npc_epicfight_mobpatch");
    }

    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn,
            ProfilerFiller profilerIn) {
        // This listener is registered before AdvNpcPatchReloader. Start one
        // fresh shared table here; the advanced listener appends its providers
        // and TAGMAP entries immediately afterward. The resulting table is
        // what the server sends to clients during datapack synchronization.
        branchPatchProvider = new NpcBranchPatchProvider();
        AVAILABLE_MODELS = new HashSet<>();
        TAGMAP = Maps.newHashMap();
        // Register HumanoidArmature as default for CustomNPCs (each NPC can override via unique armature)
        Armatures.registerEntityTypeArmature(CustomEntities.entityCustomNpc, Armatures.BIPED);
        for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            CompoundTag tag = null;
            try {
                tag = TagParser.parseTag((entry.getValue()).toString());
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
            branchPatchProvider.addProvider(entry.getKey(), deserializeMobPatchProvider(tag, false));
            if (tag.contains("nbt_tag")) {
                try {
                    branchPatchProvider.addNbtProvider(entry.getKey(),
                            TagParser.parseTag(tag.getString("nbt_tag")),
                            deserializeMobPatchProvider(tag, false));
                } catch (CommandSyntaxException e) {
                    CNPCEpicFightAddon.LOGGER.error("Invalid CNPC nbt_tag for {}", entry.getKey(), e);
                }
            }
            AVAILABLE_MODELS.add(entry.getKey());
            CompoundTag filteredTag = MobPatchReloadListener.filterClientData(tag);
            filteredTag.putString("patchType", "NORMAL");
            TAGMAP.put(entry.getKey(), filteredTag);
            if (EpicFightSharedConstants.isPhysicalClient())
                RenderStorage.registerRenderer(entry.getKey(),
                        tag.contains("preset") ? tag.getString("preset") : tag.getString("renderer"));
        }
        CNPCEpicFightAddon.LOGGER.info("Loaded CNPC Epic Fight mobpatch JSON files: {}", objectIn.size());
    }

    public static MobPatchReloadListener.AbstractMobPatchProvider deserializeMobPatchProvider(CompoundTag tag,
            boolean clientSide) {
        boolean disabled = (tag.contains("disabled") && tag.getBoolean("disabled"));
        if (disabled)
            return new MobPatchReloadListener.NullPatchProvider();
        if (tag.contains("preset")) {
            String presetName = tag.getString("preset");
            Function<Entity, Supplier<EntityPatch<?>>> preset = EntityPatchProvider.get(presetName);
            Armatures.registerEntityTypeArmature(CustomEntities.entityCustomNpc, Armatures.BIPED);
            MobPatchReloadListener.MobPatchPresetProvider mobPatchPresetProvider = new MobPatchReloadListener.MobPatchPresetProvider(
                    preset);
            return mobPatchPresetProvider;
        }
        boolean humanoid = tag.getBoolean("isHumanoid");

        MobPatchReloadListener.AbstractMobPatchProvider provider = humanoid ? new NpcHumanoidPatchProvider()
                : new NpcPatchProvider();
        final ICustomMobPatchProvider npcPatchProvider = (ICustomMobPatchProvider) provider;
        npcPatchProvider
                .setAttributeValues(MobPatchReloadListener.deserializeAttributes(tag.getCompound("attributes")));
        ResourceLocation modelLocation = new ResourceLocation(tag.getString("model"));
        ResourceLocation armatureLocation = new ResourceLocation(tag.getString("armature"));
        modelLocation = new ResourceLocation(modelLocation.getNamespace(),
                "animmodels/" + modelLocation.getPath() + ".json");
        armatureLocation = new ResourceLocation(armatureLocation.getNamespace(),
                "animmodels/" + armatureLocation.getPath() + ".json");
        if (EpicFightSharedConstants.isPhysicalClient()) {
            Minecraft mc = Minecraft.getInstance();
            boolean isHumanoid = humanoid;
            Meshes.getOrCreate(modelLocation, (jsonModelLoader) -> {
                if (isHumanoid) {
                    return jsonModelLoader.loadSkinnedMesh(yesman.epicfight.client.mesh.HumanoidMesh::new);
                } else {
                    return jsonModelLoader.loadSkinnedMesh(SkinnedMesh::new);
                }
            }).get();
            Armature armature = Armatures.getOrCreate(armatureLocation,
                    humanoid ? yesman.epicfight.model.armature.HumanoidArmature::new : Armature::new).get();
            ((INpcPatchProvider) provider).setArmature(armature);
        } else {
            Armature armature = Armatures.getOrCreate(armatureLocation,
                    humanoid ? yesman.epicfight.model.armature.HumanoidArmature::new : Armature::new).get();
            ((INpcPatchProvider) provider).setArmature(armature);
        }
        // Note: CustomNPCs have individual armatures set via setArmature(), no global registration needed
        List<Pair<LivingMotion, AnimationAccessor<? extends yesman.epicfight.api.animation.types.StaticAnimation>>> defaultAnimations =
                MobPatchReloadListener.deserializeDefaultAnimations(tag.getCompound("default_livingmotions"));
        npcPatchProvider.setDefaultAnimations(defaultAnimations);
        npcPatchProvider.setFaction(Faction.ENUM_MANAGER.getOrThrow(tag.getString("faction").toUpperCase(Locale.ROOT)));
        npcPatchProvider.setScale(tag.getCompound("attributes").contains("scale")
                ? (float) tag.getCompound("attributes").getDouble("scale")
                : 1.0F);
        if (!clientSide) {
            npcPatchProvider.setStunAnimations(
                    MobPatchReloadListener.deserializeStunAnimations(tag.getCompound("stun_animations")));
            npcPatchProvider.setChasingSpeed(tag.getCompound("attributes").getDouble("chasing_speed"));
            if (humanoid) {
                MobPatchReloadListener.CustomHumanoidMobPatchProvider humanoidProvider = (MobPatchReloadListener.CustomHumanoidMobPatchProvider) npcPatchProvider;
                ((ICustomHumanoidMobPatchProvider) humanoidProvider).setHumanoidCombatBehaviors(
                        MobPatchReloadListener.deserializeHumanoidCombatBehaviors(tag.getList("combat_behavior", 10)));
                ((ICustomHumanoidMobPatchProvider) humanoidProvider).setHumanoidWeaponMotions(MobPatchReloadListener
                        .deserializeHumanoidWeaponMotions(tag.getList("humanoid_weapon_motions", 10)));
            } else {
                npcPatchProvider.setCombatBehaviorsBuilder(
                        MobPatchReloadListener.deserializeCombatBehaviorsBuilder(tag.getList("combat_behavior", 10)));
            }
        }
        return provider;
    }

    public static Stream<CompoundTag> getDataStream() {
        Stream<CompoundTag> tagStream = TAGMAP.entrySet().stream().map((entry) -> {
            entry.getValue().putString("id", entry.getKey().toString());
            return entry.getValue();
        });
        return tagStream;
    }

    /**
     * CNPCs have their own one-to-many selector and must win over EFI's
     * entity-type fallback registration for the CustomNPC entity type.
     * Generic Minecraft mobs continue using EFI's universal dispatcher.
     */
    public static void bindEntityPatchProvider() {
        bindEntityPatchProvider(branchPatchProvider);
    }

    public static void bindEntityPatchProvider(NpcBranchPatchProvider provider) {
        EntityPatchProvider.putCustomEntityPatch(CustomEntities.entityCustomNpc,
                entity -> () -> provider.get(entity));
    }

    public static NpcBranchPatchProvider providerFor(Entity entity) {
        return entity.level().isClientSide() ? clientBranchPatchProvider : branchPatchProvider;
    }

    @OnlyIn(Dist.CLIENT)
    public static void applyClientSelection(int entityId, ResourceLocation selectedKey) {
        CLIENT_AUTHORITATIVE_SELECTION_KNOWN.add(entityId);
        if (selectedKey == null) {
            CLIENT_AUTHORITATIVE_SELECTIONS.remove(entityId);
        } else {
            CLIENT_AUTHORITATIVE_SELECTIONS.put(entityId, selectedKey);
        }
        clientBranchPatchProvider.applyClientSelection(entityId, selectedKey);
        if (!CLIENT_DATA_READY) {
            CLIENT_REFRESH_PENDING.add(entityId);
            CNPCEpicFightAddon.LOGGER.info(
                    "[CNPC EFI diagnostic] client selection queued until datapack sync: entityId={} selectedKey={}",
                    entityId, selectedKey);
            return;
        }
        if (Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity instanceof noppes.npcs.entity.EntityNPCInterface npc
                    && npc.display instanceof com.goodbird.cnpcefaddon.mixin.IDataDisplay display) {
                display.refreshEFPatch();
            }
        }
    }

    public static boolean isClientDataReady() {
        return CLIENT_DATA_READY;
    }

    @OnlyIn(Dist.CLIENT)
    public static void processServerPacket(SPDatapackSync packet) {
        CLIENT_DATA_READY = false;
        NpcBranchPatchProvider synchronizedProvider = new NpcBranchPatchProvider();
        clientBranchPatchProvider = synchronizedProvider;
        AVAILABLE_MODELS = new HashSet<>();
        TAGMAP = Maps.newHashMap();
        int normalEntries = 0;
        int advancedEntries = 0;
        // Register HumanoidArmature as default for CustomNPCs (each NPC can override via unique armature)
        Armatures.registerEntityTypeArmature(CustomEntities.entityCustomNpc, Armatures.BIPED);
        for (CompoundTag tag : packet.getTags()) {
            boolean disabled = false;
            if (tag.contains("disabled"))
                disabled = tag.getBoolean("disabled");
            ResourceLocation key = new ResourceLocation(tag.getString("id"));
            MobPatchReloadListener.AbstractMobPatchProvider provider = null;
            if ("ADVANCED".equals(tag.getString("patchType")) && IndestructibleCompat.isAvailable()) {
                advancedEntries++;
                provider = IndestructibleCompat.deserializeAdvancedNpcPatchProvider(tag, false,
                        Minecraft.getInstance().getResourceManager());
            } else {
                normalEntries++;
                provider = deserializeMobPatchProvider(tag, false);
            }

            synchronizedProvider.addProvider(key, provider);
            if (tag.contains("nbt_tag")) {
                try {
                    synchronizedProvider.addNbtProvider(key, TagParser.parseTag(tag.getString("nbt_tag")), provider);
                } catch (CommandSyntaxException e) {
                    CNPCEpicFightAddon.LOGGER.error("Invalid synced CNPC nbt_tag for {}", key, e);
                }
            }
            AVAILABLE_MODELS.add(key);
            bindEntityPatchProvider(synchronizedProvider);
            if (!disabled) {
                if (tag.contains("preset")) {
                    // Armatures.registerEntityTypeArmature(entityType, tag.getString("preset"));

                } else {
                    Minecraft mc = Minecraft.getInstance();
                    ResourceLocation armatureLocation = new ResourceLocation(tag.getString("armature"));
                    armatureLocation = new ResourceLocation(armatureLocation.getNamespace(),
                            "animmodels/" + armatureLocation.getPath() + ".json");
                    boolean humanoid = tag.getBoolean("isHumanoid");
                    Armature armature = Armatures
                            .getOrCreate(armatureLocation,
                                humanoid ? yesman.epicfight.model.armature.HumanoidArmature::new : Armature::new)
                            .get();
                    ((INpcPatchProvider) provider).setArmature(armature);
                }
                // Note: CustomNPCs have individual armatures set via setArmature(), no global registration needed
                RenderStorage.registerRenderer(key,
                        tag.contains("preset") ? tag.getString("preset") : tag.getString("renderer"));
            }
        }

        // All normal and advanced providers are now complete. Existing
        // entities can safely be rebuilt from either category.
        CLIENT_DATA_READY = true;
        // Re-apply selections received before or during the datapack sync.
        // processServerPacket replaces the provider instance, so selections
        // stored only on the old instance would otherwise be lost.
        for (Integer entityId : CLIENT_AUTHORITATIVE_SELECTION_KNOWN) {
            synchronizedProvider.applyClientSelection(entityId,
                    CLIENT_AUTHORITATIVE_SELECTIONS.get(entityId));
        }
        Set<Integer> pendingRefreshes = new HashSet<>(CLIENT_REFRESH_PENDING);
        CLIENT_REFRESH_PENDING.clear();

        // The server may send the entity selection packet before this
        // datapack payload. Refresh already-spawned CNPCs after all providers
        // and armatures exist so a standard customnpc.json patch cannot remain
        // on the old client capability/animation state.
        if (Minecraft.getInstance().level != null) {
            for (Entity entity : Minecraft.getInstance().level.entitiesForRendering()) {
                if (entity instanceof noppes.npcs.entity.EntityNPCInterface npc
                        && npc.display instanceof com.goodbird.cnpcefaddon.mixin.IDataDisplay display) {
                    display.refreshEFPatch();
                }
            }
            for (Integer entityId : pendingRefreshes) {
                Entity entity = Minecraft.getInstance().level.getEntity(entityId);
                if (entity instanceof noppes.npcs.entity.EntityNPCInterface npc
                        && npc.display instanceof com.goodbird.cnpcefaddon.mixin.IDataDisplay display) {
                    display.refreshEFPatch();
                }
            }
        }
        CNPCEpicFightAddon.LOGGER.info(
                "Synchronized CNPC Epic Fight client patch data: {} entries (normal={}, advanced={})",
                packet.getTags().length, normalEntries, advancedEntries);
    }

    /** Server-side selection lookup used when a player begins tracking a CNPC. */
    public static ResourceLocation resolveServerSelection(Entity entity) {
        if (entity.level().isClientSide() || !(entity instanceof noppes.npcs.entity.EntityNPCInterface)) {
            return null;
        }
        return branchPatchProvider.resolveSelectedKey(entity);
    }
}

/*
 * function interact(e){
 * var npc = e.npc.getMCEntity()
 * var RL = Java.type("net.minecraft.resources.ResourceLocation")
 * npc.display.setEFModel(new RL("customnpcs:skeleton"))
 * }
 */
