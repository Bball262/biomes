package net.bball_262.redsbiomes.items.custom;

import net.bball_262.redsbiomes.entities.CrabVariant;
import net.bball_262.redsbiomes.entities.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public class CrabBucketItem extends MobBucketItem {
    public CrabBucketItem(Fluid content, SoundEvent emptySound, Properties properties) {
        super(ModEntities.CRAB_ENTITY_TYPE.get(), content, emptySound, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        CustomData data = stack.get(DataComponents.BUCKET_ENTITY_DATA);
        CompoundTag tag = data != null ? data.copyTag() : null;
        CrabVariant variant = tag != null && tag.contains("Variant") ? CrabVariant.BY_ID.apply(tag.getInt("Variant")) : null;

        if (variant != null) {
            tooltipComponents.add(Component.literal(variant.getName()).withColor(variant.getColor()));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}