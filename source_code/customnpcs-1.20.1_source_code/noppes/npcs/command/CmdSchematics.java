/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.commands.SharedSuggestionProvider
 *  net.minecraft.commands.arguments.coordinates.BlockPosArgument
 *  net.minecraft.commands.synchronization.SuggestionProviders
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.SchematicController;
import noppes.npcs.schematics.SchematicWrapper;

public class CmdSchematics {
    public static final List<String> names = new ArrayList<String>();
    public static final SuggestionProvider<CommandSourceStack> SCHEMAS = SuggestionProviders.m_121658_((ResourceLocation)new ResourceLocation("schemas"), (context, builder) -> SharedSuggestionProvider.m_82981_(names.stream(), (SuggestionsBuilder)builder));
    public static final SuggestionProvider<CommandSourceStack> ROTATION = SuggestionProviders.m_121658_((ResourceLocation)new ResourceLocation("rotation"), (context, builder) -> SharedSuggestionProvider.m_82967_((String[])new String[]{"0", "90", "180", "270"}, (SuggestionsBuilder)builder));

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder command = (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_((String)"schema").requires(source -> source.m_6761_(CustomNpcs.NoppesCommandOpOnly ? 4 : 2))).then(Commands.m_82127_((String)"build").then(Commands.m_82129_((String)"name", (ArgumentType)StringArgumentType.word()).suggests(SCHEMAS).then(Commands.m_82129_((String)"pos", (ArgumentType)BlockPosArgument.m_118239_()).then(Commands.m_82129_((String)"rotation", (ArgumentType)StringArgumentType.word()).suggests(ROTATION).executes(context -> {
            String name = StringArgumentType.getString((CommandContext)context, (String)"name");
            BlockPos pos = BlockPosArgument.m_118242_((CommandContext)context, (String)"pos");
            int rotation = Integer.parseInt(StringArgumentType.getString((CommandContext)context, (String)"rotation"));
            SchematicWrapper schem = SchematicController.Instance.load(name);
            schem.init(pos, (Level)((CommandSourceStack)context.getSource()).m_81372_(), rotation);
            SchematicController.Instance.build(schem, (CommandSourceStack)context.getSource());
            return 1;
        })))))).then(Commands.m_82127_((String)"stop").executes(context -> {
            SchematicController.Instance.stop((CommandSourceStack)context.getSource());
            return 1;
        }))).then(Commands.m_82127_((String)"info").executes(context -> {
            SchematicController.Instance.info((CommandSourceStack)context.getSource());
            return 1;
        }))).then(Commands.m_82127_((String)"list").executes(context -> {
            List<String> list = SchematicController.Instance.list();
            if (list.isEmpty()) {
                ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237115_((String)"No schemas available"), false);
                return 1;
            }
            Object s = "";
            for (String file : list) {
                s = (String)s + file + ", ";
            }
            String finalS = s;
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237115_((String)finalS), false);
            return 1;
        }));
        return command;
    }
}

