package dev.felnull.imp.item;

import dev.felnull.imp.IamMusicPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class IMPItemTags {
    public static final TagKey<Item> CASSETTE_TAPE = bind("cassette_tape");

    private static TagKey<Item> bind(String string) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(IamMusicPlayer.MODID, string));
    }
}
