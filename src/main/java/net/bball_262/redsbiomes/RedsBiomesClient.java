package net.bball_262.redsbiomes;

import net.bball_262.redsbiomes.entities.ModEntities;
import net.bball_262.redsbiomes.entities.client.CrabRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = RedsBiomes.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = RedsBiomes.MOD_ID, value = Dist.CLIENT)
public class RedsBiomesClient {
    public RedsBiomesClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.CRAB_ENTITY_TYPE.get(), CrabRenderer::new);
    }
}