package net.bball_262.redsbiomes.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.bball_262.redsbiomes.RedsBiomes.MOD_ID;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> CRAB_SPAWNABLE_ON = registerBlockTag("crab_spawnable_on");

        public static TagKey<Block> registerBlockTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
        }
    }
}
