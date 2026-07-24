package net.bball_262.redsbiomes.worldgen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static net.bball_262.redsbiomes.RedsBiomes.MOD_ID;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_CRAB = registerKey("spawn_crab");

    private static ResourceKey<BiomeModifier> registerKey(String key) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MOD_ID, key));
    }
}
