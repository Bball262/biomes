package net.bball_262.redsbiomes.items;

import net.bball_262.redsbiomes.entities.ModEntities;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.bball_262.redsbiomes.RedsBiomes.MOD_ID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<Item> CRAB_SPAWN_EGG = ITEMS.register("crab_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.CRAB_ENTITY_TYPE, -1354444, -1518439, new Item.Properties()));
}
