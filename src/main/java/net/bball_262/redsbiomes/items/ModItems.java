package net.bball_262.redsbiomes.items;

import net.bball_262.redsbiomes.entities.ModEntities;
import net.bball_262.redsbiomes.items.custom.CrabBucketItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.bball_262.redsbiomes.RedsBiomes.MOD_ID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<Item> CRAB_SPAWN_EGG = ITEMS.register("crab_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.CRAB_ENTITY_TYPE, -1354444, -1518439, new Item.Properties()));
    public static final DeferredItem<Item> CRAB_BUCKET = ITEMS.register("bucket_of_crab",
            () -> new CrabBucketItem(Fluids.WATER, SoundEvents.BUCKET_EMPTY, new Item.Properties().stacksTo(1)));


    public static void registerItemProperties() {
        ItemProperties.register(
                CRAB_BUCKET.value(),
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "crab_variant"),
                (stack, level, entity, seed) -> {
                    CustomData data = stack.get(DataComponents.BUCKET_ENTITY_DATA);
                    CompoundTag tag = data != null ? data.copyTag() : null;

                    if (tag == null) {
                        return 0;
                    }

                    if (tag.contains("Variant")) {
                        return tag.getInt("Variant");
                    }

                    return 0;
                }
        );
    }

    public static class ModDataComponents {
        public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister
                .createDataComponents(Registries.DATA_COMPONENT_TYPE, MOD_ID);
    }
}