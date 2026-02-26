package com.goodbird.cnpcefaddon.common;

/*
 * import com.goodbird.cnpcefaddon.client.render.RenderStorage;
 * import com.goodbird.cnpcefaddon.common.patch.INpcPatch;
 * import com.goodbird.cnpcefaddon.common.provider.AdvNpcPatchProvider;
 * import com.goodbird.cnpcefaddon.common.provider.INpcPatchProvider;
 * import com.google.common.collect.Lists;
 * import com.google.gson.Gson;
 * import com.google.gson.GsonBuilder;
 * import com.google.gson.JsonElement;
 * import com.mojang.brigadier.exceptions.CommandSyntaxException;
 * import com.nameless.indestructible.api.animation.types.AnimationEvent;
 * import com.nameless.indestructible.data.AdvancedMobpatchReloader;
 * import net.minecraft.client.Minecraft;
 * import net.minecraft.nbt.CompoundTag;
 * import net.minecraft.nbt.ListTag;
 * import net.minecraft.nbt.TagParser;
 * import net.minecraft.resources.ResourceLocation;
 * import net.minecraft.server.packs.resources.ResourceManager;
 * import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
 * import net.minecraft.util.profiling.ProfilerFiller;
 * import noppes.npcs.CustomEntities;
 * import yesman.epicfight.api.client.model.Meshes;
 * import yesman.epicfight.api.data.reloader.MobPatchReloadListener;
 * import yesman.epicfight.api.model.Armature;
 * import yesman.epicfight.client.mesh.HumanoidMesh;
 * import yesman.epicfight.gameasset.Armatures;
 * import yesman.epicfight.main.EpicFightMod;
 * import yesman.epicfight.model.armature.HumanoidArmature;
 * import yesman.epicfight.world.capabilities.entitypatch.Faction;
 * import yesman.epicfight.world.capabilities.provider.EntityPatchProvider;
 * import yesman.epicfight.world.damagesource.StunType;
 * 
 * import java.util.List;
 * import java.util.Locale;
 * import java.util.Map;
 * 
 * public class AdvNpcPatchReloader extends SimpleJsonResourceReloadListener {
 * private static final Gson GSON = (new GsonBuilder()).create();
 * 
 * public AdvNpcPatchReloader() {
 * super(GSON, "adv_npc_epicfight_mobpatch");
 * }
 * 
 * protected void apply(Map<ResourceLocation, JsonElement> objectIn,
 * ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
 * for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
 * CompoundTag tag = null;
 * try {
 * tag = TagParser.parseTag((entry.getValue()).toString());
 * } catch (CommandSyntaxException e) {
 * e.printStackTrace();
 * }
 * NpcPatchReloadListener.branchPatchProvider.addProvider(entry.getKey(),
 * deserializeMobPatchProvider(tag, false));
 * NpcPatchReloadListener.AVAILABLE_MODELS.add(entry.getKey());
 * CompoundTag filteredTag = MobPatchReloadListener.filterClientData(tag);
 * filteredTag.putString("patchType", "ADVANCED");
 * NpcPatchReloadListener.TAGMAP.put(entry.getKey(), filteredTag);
 * EntityPatchProvider.putCustomEntityPatch(CustomEntities.entityCustomNpc,
 * entity -> ()->NpcPatchReloadListener.branchPatchProvider.get(entity));
 * if (EpicFightMod.isPhysicalClient())
 * RenderStorage.registerRenderer(entry.getKey(), tag.contains("preset") ?
 * tag.getString("preset") : tag.getString("renderer"));
 * }
 * }
 * 
 * public static AdvNpcPatchProvider deserializeMobPatchProvider(CompoundTag
 * tag, boolean clientSide) {
 * AdvNpcPatchProvider provider = new AdvNpcPatchProvider();
 * provider.setAttributeValues(AdvancedMobpatchReloader.
 * deserializeAdvancedAttributes(tag.getCompound("attributes")));
 * ResourceLocation modelLocation = new
 * ResourceLocation(tag.getString("model"));
 * ResourceLocation armatureLocation = new
 * ResourceLocation(tag.getString("armature"));
 * modelLocation = new ResourceLocation(modelLocation.getNamespace(),
 * "animmodels/" + modelLocation.getPath() + ".json");
 * armatureLocation = new ResourceLocation(armatureLocation.getNamespace(),
 * "animmodels/" + armatureLocation.getPath() + ".json");
 * if (EpicFightMod.isPhysicalClient()) {
 * Minecraft mc = Minecraft.getInstance();
 * Meshes.getOrCreateAnimatedMesh(mc.getResourceManager(), modelLocation,
 * HumanoidMesh::new);
 * Armature armature = Armatures.getOrCreateArmature(mc.getResourceManager(),
 * armatureLocation, HumanoidArmature::new);
 * ((INpcPatchProvider)provider).setArmature(armature);
 * } else {
 * Armature armature = Armatures.getOrCreateArmature(null, armatureLocation,
 * HumanoidArmature::new);
 * ((INpcPatchProvider)provider).setArmature(armature);
 * }
 * Armatures.registerEntityTypeArmature(CustomEntities.entityCustomNpc, patch ->
 * {
 * if(patch instanceof INpcPatch) {
 * return ((INpcPatch) patch).getArmature().deepCopy();
 * }
 * return
 * Armatures.getRegistry(patch.getOriginal().getType()).apply(patch).deepCopy();
 * });
 * 
 * provider.setDefaultAnimations(MobPatchReloadListener.
 * deserializeDefaultAnimations(tag.getCompound("default_livingmotions")));
 * provider.setFaction(Faction.valueOf(tag.getString("faction").toUpperCase(
 * Locale.ROOT)));
 * provider.setScale(tag.getCompound("attributes").contains("scale") ?
 * (float)tag.getCompound("attributes").getDouble("scale") : 1.0F);
 * provider.setMaxStamina(tag.getCompound("attributes").contains("max_stamina")
 * ? (float)tag.getCompound("attributes").getDouble("max_stamina") : 15.0F);
 * provider.setMaxStunShield(tag.getCompound("attributes").contains(
 * "max_stun_shield") ?
 * (float)tag.getCompound("attributes").getDouble("max_stun_shield") : 0.0F);
 * if (!clientSide) {
 * provider.setStunAnimations(MobPatchReloadListener.deserializeStunAnimations(
 * tag.getCompound("stun_animations")));
 * provider.setChasingSpeed(tag.getCompound("attributes").getDouble(
 * "chasing_speed"));
 * provider.setAHCombatBehaviors(AdvancedMobpatchReloader.
 * deserializeAdvancedCombatBehaviors(tag.getList("combat_behavior", 10)));
 * provider.setAHWeaponMotions(MobPatchReloadListener.
 * deserializeHumanoidWeaponMotions(tag.getList("humanoid_weapon_motions",
 * 10)));
 * provider.setGuardMotions(AdvancedMobpatchReloader.deserializeGuardMotions(tag
 * .getList("custom_guard_motion", 10)));
 * provider.setRegenStaminaStandbyTime(tag.getCompound("attributes").contains(
 * "stamina_regan_delay") ?
 * tag.getCompound("attributes").getInt("stamina_regan_delay") : 30);
 * provider.setRegenStaminaMultiply(tag.getCompound("attributes").contains(
 * "stamina_regan_multiply") ?
 * (float)tag.getCompound("attributes").getDouble("stamina_regan_multiply") :
 * 1.0F);
 * provider.setHasStunReduction(!tag.getCompound("attributes").contains(
 * "has_stun_reduction") ||
 * tag.getCompound("attributes").getBoolean("has_stun_reduction"));
 * provider.setReganShieldStandbyTime(tag.getCompound("attributes").contains(
 * "stun_shield_regan_delay") ?
 * tag.getCompound("attributes").getInt("stun_shield_regan_delay") : 30);
 * provider.setRegenStaminaMultiply(tag.getCompound("attributes").contains(
 * "stun_shield_regan_multiply") ?
 * (float)tag.getCompound("attributes").getDouble("stun_shield_multiply") :
 * 1.0F);
 * provider.setStaminaLoseMultiply(tag.getCompound("attributes").contains(
 * "stamina_lose_multiply") ?
 * (float)tag.getCompound("attributes").getDouble("stamina_lose_multiply") :
 * 0.0F);
 * provider.setGuardRadius(tag.getCompound("attributes").contains(
 * "guard_radius") ?
 * (float)tag.getCompound("attributes").getDouble("guard_radius") : 3.0F);
 * provider.setStunEvent(deserializeStunCommandList(tag.getList(
 * "stun_command_list", 10)));
 * }
 * 
 * return provider;
 * }
 * 
 * private static List<AnimationEvent.ConditionalEvent>
 * deserializeStunCommandList(ListTag args) {
 * List<AnimationEvent.ConditionalEvent> list = Lists.newArrayList();
 * 
 * for(int k = 0; k < args.size(); ++k) {
 * CompoundTag command = args.getCompound(k);
 * AnimationEvent.ConditionalEvent event =
 * AnimationEvent.ConditionalEvent.CreateStunCommandEvent(command.getString(
 * "command"),
 * StunType.valueOf(command.getString("stun_type").toUpperCase(Locale.ROOT)));
 * list.add(event);
 * }
 * 
 * return list;
 * }
 * 
 * }
 */
