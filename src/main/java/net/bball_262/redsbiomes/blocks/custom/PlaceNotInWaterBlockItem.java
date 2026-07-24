package net.bball_262.redsbiomes.blocks.custom;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;

public class PlaceNotInWaterBlockItem extends BlockItem {
    public PlaceNotInWaterBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        boolean partiallyInWater = level.getFluidState(player.blockPosition()).is(FluidTags.WATER) && !level.getFluidState(player.blockPosition().above()).is(FluidTags.WATER);
        boolean notInWater = !level.getFluidState(player.blockPosition()).is(FluidTags.WATER) && !level.getFluidState(player.blockPosition().above()).is(FluidTags.WATER);

        if (partiallyInWater || notInWater) {
            BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            BlockHitResult blockhitresult1 = blockhitresult.withPosition(blockhitresult.getBlockPos().above());
            InteractionResult interactionresult = super.useOn(new UseOnContext(player, hand, blockhitresult1));

            return new InteractionResultHolder<>(interactionresult, player.getItemInHand(hand));
        }

        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        InteractionResult result = super.useOn(new UseOnContext(player, hand, blockhitresult));

        return new InteractionResultHolder<>(result, player.getItemInHand(hand));
    }
}
