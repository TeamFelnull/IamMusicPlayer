package dev.felnull.imp.blockentity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.imp.block.IMPBlocks;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class IMPBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_REGISTER = DeferredRegister.create(OtyacraftEngine.MODID, Registries.BLOCK_ENTITY_TYPE);
    public static final RegistrySupplier<BlockEntityType<MusicManagerBlockEntity>> MUSIC_MANAGER = register("music_manager", MusicManagerBlockEntity::new, IMPBlocks.MUSIC_MANAGER);
    public static final RegistrySupplier<BlockEntityType<CassetteDeckBlockEntity>> CASSETTE_DECK = register("cassette_deck", CassetteDeckBlockEntity::new, IMPBlocks.CASSETTE_DECK);
    public static final RegistrySupplier<BlockEntityType<BoomboxBlockEntity>> BOOMBOX = register("boombox", BoomboxBlockEntity::new, IMPBlocks.BOOMBOX);

    private static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> constructor, RegistrySupplier<Block> blocks) {
        return BLOCK_ENTITY_TYPES_REGISTER.register(name, () -> BlockEntityType.Builder.of(constructor, blocks.get()).build(null));
    }

    public static void init() {
        BLOCK_ENTITY_TYPES_REGISTER.register();
    }
}
