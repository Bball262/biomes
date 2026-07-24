package net.bball_262.redsbiomes.blocks;

import net.bball_262.redsbiomes.RedsBiomes;
import net.bball_262.redsbiomes.blocks.custom.PlaceNotInWaterBlockItem;
import net.bball_262.redsbiomes.blocks.custom.SeaweedBlock;
import net.bball_262.redsbiomes.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RedsBiomes.MOD_ID);
    public static final DeferredBlock<Block> SEA_WEED = registerWaterBlock("sea_weed",
            () -> new SeaweedBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().instabreak().noCollission()
                    .sound(SoundType.GLOW_LICHEN)));

    public static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static <T extends Block> void registerWaterPlaceableBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new PlaceNotInWaterBlockItem(block.get(), new Item.Properties()));
    }

    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> DeferredBlock<T> registerWaterBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerWaterPlaceableBlockItem(name, toReturn);
        return toReturn;
    }
}