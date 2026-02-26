/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.horse.Horse
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragon
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomNpcs;
import noppes.npcs.entity.EntityNPCInterface;

public class CmdSlay {
    static Map<String, Class<?>> slayMap = new LinkedHashMap();

    private static Map<String, Class<?>> getSlay(Level level) {
        if (!slayMap.isEmpty()) {
            return slayMap;
        }
        slayMap.put("all", LivingEntity.class);
        slayMap.put("mobs", Monster.class);
        slayMap.put("animals", Animal.class);
        slayMap.put("items", ItemEntity.class);
        slayMap.put("xporbs", ExperienceOrb.class);
        slayMap.put("npcs", EntityNPCInterface.class);
        for (ResourceLocation resource : ForgeRegistries.ENTITY_TYPES.getKeys()) {
            EntityType ent = (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(resource);
            if (ent.m_20674_() == MobCategory.MISC) continue;
            String name = ent.m_20675_();
            try {
                Entity e = ent.m_20615_(level);
                e.m_142687_(Entity.RemovalReason.DISCARDED);
                Class<?> cls = e.getClass();
                if (EntityNPCInterface.class.isAssignableFrom(cls) || !LivingEntity.class.isAssignableFrom(cls)) continue;
                slayMap.put(name.toLowerCase(), cls);
            }
            catch (Throwable throwable) {}
        }
        slayMap.remove("monster");
        slayMap.remove("mob");
        return slayMap;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"slay").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82129_((String)"type", (ArgumentType)StringArgumentType.word()).then(Commands.m_82129_((String)"range", (ArgumentType)IntegerArgumentType.integer((int)1)).executes(context -> {
            ArrayList toDelete = new ArrayList();
            boolean deleteNPCs = false;
            String delete = StringArgumentType.getString((CommandContext)context, (String)"type");
            Class<?> cls = CmdSlay.getSlay((Level)((CommandSourceStack)context.getSource()).m_81372_()).get(delete);
            if (cls != null) {
                toDelete.add(cls);
            }
            if (delete.equals("mobs")) {
                toDelete.add(Ghast.class);
                toDelete.add(EnderDragon.class);
            }
            if (delete.equals("npcs")) {
                deleteNPCs = true;
            }
            int count = 0;
            int range = IntegerArgumentType.getInteger((CommandContext)context, (String)"range");
            AABB box = new AABB(((CommandSourceStack)context.getSource()).m_81371_(), ((CommandSourceStack)context.getSource()).m_81371_().m_82520_(1.0, 1.0, 1.0)).m_82377_((double)range, (double)range, (double)range);
            List list = ((CommandSourceStack)context.getSource()).m_81372_().m_45976_(LivingEntity.class, box);
            for (Entity entity : list) {
                if (entity instanceof Player || entity instanceof TamableAnimal && ((TamableAnimal)entity).m_21824_() || entity instanceof EntityNPCInterface && !deleteNPCs || !CmdSlay.delete(entity, toDelete)) continue;
                ++count;
            }
            if (toDelete.contains(ExperienceOrb.class)) {
                list = ((CommandSourceStack)context.getSource()).m_81372_().m_45976_(ExperienceOrb.class, box);
                for (Entity entity : list) {
                    entity.m_142467_(Entity.RemovalReason.DISCARDED);
                    ++count;
                }
            }
            if (toDelete.contains(ItemEntity.class)) {
                list = ((CommandSourceStack)context.getSource()).m_81372_().m_45976_(ItemEntity.class, box);
                for (Entity entity : list) {
                    entity.m_142467_(Entity.RemovalReason.DISCARDED);
                    ++count;
                }
            }
            int finalCount = count;
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237115_((String)(finalCount + " entities deleted")), false);
            return 1;
        })));
        return command;
    }

    private static boolean delete(Entity entity, ArrayList<Class<?>> toDelete) {
        for (Class<?> delete : toDelete) {
            if (delete == Animal.class && entity instanceof Horse || !delete.isAssignableFrom(entity.getClass())) continue;
            entity.m_142467_(Entity.RemovalReason.DISCARDED);
            return true;
        }
        return false;
    }
}

