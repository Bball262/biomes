package net.bball_262.redsbiomes.entities;

import net.bball_262.redsbiomes.entities.custom.CrabEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.bball_262.redsbiomes.RedsBiomes.MOD_ID;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MOD_ID);
    public static final Supplier<EntityType<CrabEntity>> CRAB_ENTITY_TYPE = ENTITY_TYPES.register("crab",
            () -> EntityType.Builder.of(CrabEntity::new, MobCategory.CREATURE)
                    .sized(.55F, .35F).build("crab"));
}
