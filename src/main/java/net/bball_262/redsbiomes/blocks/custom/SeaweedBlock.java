package net.bball_262.redsbiomes.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SeaweedBlock extends BushBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

    public SeaweedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(SeaweedBlock::new);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockState state = level.getBlockState(context.getClickedPos());
        BlockState state1 = level.getBlockState(context.getClickedPos().above());

        BlockState toReturn = super.getStateForPlacement(context);

        boolean inWater = state.getFluidState().is(FluidTags.WATER);
        boolean exposed = state1.getFluidState().isEmpty();
        if (inWater && exposed) {
            toReturn.setValue(WATERLOGGED, false);
        } else if (inWater && !exposed) {
            toReturn.setValue(WATERLOGGED, true);
        }
        return toReturn;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        FluidState fluidstate = level.getFluidState(pos);
        FluidState fluidstate1 = level.getFluidState(pos.above());
        FluidState fluidstate2 = level.getFluidState(pos.below());

        boolean flag = (fluidstate.getType() == Fluids.WATER || state.getBlock() instanceof IceBlock) && fluidstate1.getType() == Fluids.EMPTY;
        boolean flag1 = fluidstate.getType() == Fluids.WATER && fluidstate2.getType() == Fluids.EMPTY;
        return flag || flag1;
    }

    public boolean canSupportBlock(BlockGetter level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return isCollisionShapeFullBlock(below, level, pos) || below.is(this);
    }
}