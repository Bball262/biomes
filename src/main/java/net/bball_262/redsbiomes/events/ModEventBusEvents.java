package net.bball_262.redsbiomes.events;

import net.bball_262.redsbiomes.entities.ModEntities;
import net.bball_262.redsbiomes.entities.client.CrabModel;
import net.bball_262.redsbiomes.entities.custom.CrabEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import static net.bball_262.redsbiomes.RedsBiomes.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CrabModel.LAYER_LOCATION, CrabModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CRAB_ENTITY_TYPE.get(), CrabEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.CRAB_ENTITY_TYPE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CrabEntity::checkCrabSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}