package net.bball_262.redsbiomes.entities.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.bball_262.redsbiomes.entities.CrabVariant;
import net.bball_262.redsbiomes.entities.custom.CrabEntity;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

import static net.bball_262.redsbiomes.RedsBiomes.MOD_ID;

public class CrabRenderer extends MobRenderer<CrabEntity, CrabModel<CrabEntity>> {
    public static final Map<CrabVariant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(CrabVariant.class), map -> {
        map.put(CrabVariant.GRAY, loc("textures/entity/crab/gray_crab.png"));
        map.put(CrabVariant.RED, loc("textures/entity/crab/red_crab.png"));
    });

    public CrabRenderer(EntityRendererProvider.Context context) {
        super(context, new CrabModel<>(context.bakeLayer(CrabModel.LAYER_LOCATION)), 0.25F);
    }

    private static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public ResourceLocation getTextureLocation(CrabEntity crabEntity) {
        return LOCATION_BY_VARIANT.get(crabEntity.getVariant());
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(.45F, .45F, .45F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
