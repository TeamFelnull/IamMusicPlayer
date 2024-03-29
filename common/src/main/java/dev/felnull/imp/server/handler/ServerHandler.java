package dev.felnull.imp.server.handler;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LootEvent;
import dev.felnull.imp.block.IMPBlocks;
import dev.felnull.imp.item.IMPItems;
import dev.felnull.imp.server.commands.MusicCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ServerHandler {
    private static final List<String> LOOT_NORMAL = List.of("minecraft:chests/simple_dungeon", "minecraft:chests/nether_bridge", "minecraft:chests/desert_pyramid", "minecraft:chests/abandoned_mineshaft", "minecraft:chests/bastion_treasure", "minecraft:chests/jungle_temple", "minecraft:chests/underwater_ruin_big");
    private static final List<String> LOOT_RARE = List.of("minecraft:chests/buried_treasure", "minecraft:chests/end_city_treasure", "minecraft:chests/woodland_mansion");

    public static void init() {
        CommandRegistrationEvent.EVENT.register(ServerHandler::registerCommand);
        LootEvent.MODIFY_LOOT_TABLE.register(ServerHandler::modifyLootTable);
    }

    public static void modifyLootTable(@Nullable LootDataManager lootDataManager, ResourceLocation id, LootEvent.LootTableModificationContext context, boolean builtin) {
        boolean normal = LOOT_NORMAL.contains(id.toString());
        boolean rare = LOOT_RARE.contains(id.toString());

        if (normal || rare) {
            var antennaPoolB = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(rare ? 0.364364f : 0.1919810f))
                    .add(LootItem.lootTableItem(IMPItems.PARABOLIC_ANTENNA.get()).setWeight(1))
                    .add(LootItem.lootTableItem(IMPItems.RADIO_ANTENNA.get()).setWeight(rare ? 1 : 4));
            context.addPool(antennaPoolB);

            var djKitPoolB = LootPool.lootPool().setRolls(UniformGenerator.between(1, 3))
                    .when(LootItemRandomChanceCondition.randomChance(0.114514f))
                    .add(LootItem.lootTableItem(IMPBlocks.BOOMBOX.get()).setWeight(1))
                    .add(LootItem.lootTableItem(IMPItems.CASSETTE_TAPE.get()).setWeight(rare ? 3 : 6));
            context.addPool(djKitPoolB);
        }
    }

    private static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection selectioncommandSelection) {
        MusicCommand.register(dispatcher);
    }
}
