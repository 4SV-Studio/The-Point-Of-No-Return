package org.studio4sv.tponr.blocks.custom.SuitCharger;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerBlockEntity;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerSubBlockEntity;
import org.studio4sv.tponr.items.HazmatSuitPackItem;
import org.studio4sv.tponr.registers.ModBlocks;
import org.studio4sv.tponr.util.RadiationUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class SuitChargerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public SuitChargerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SuitChargerBlockEntity(pPos, pState) ;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof SuitChargerBlockEntity chargerBlockEntity) {
            if (!chargerBlockEntity.getStoredItem().isEmpty()) {
                chargerBlockEntity.addCharge(0.111F / Math.max(1, RadiationUtils.levelFromPos(pPos, pLevel)));
                pLevel.scheduleTick(pPos, this, 1);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @NotNull InteractionResult use(BlockState pBlockState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pPlayer.getItemInHand(pHand).getItem() instanceof HazmatSuitPackItem) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                    if (blockEntity instanceof SuitChargerBlockEntity mainBlockEntity) {
                        if (mainBlockEntity.getStoredItem().isEmpty()) {
                            mainBlockEntity.setStoredItem(pPlayer.getItemInHand(pHand).copyAndClear());
                            pLevel.scheduleTick(pPos, this, 1);
                        }
                    }
            } else if (pPlayer.getItemInHand(pHand).isEmpty()) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof SuitChargerBlockEntity mainBlockEntity) {
                    pPlayer.setItemInHand(pHand, mainBlockEntity.getStoredItem());
                    mainBlockEntity.setStoredItem(ItemStack.EMPTY);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(blockState, level, pos, oldState, moved);

        if (level.isClientSide()) {
            return;
        }

        Direction facing = blockState.getValue(FACING);
        List<BlockPos> subBlockPositions = new ArrayList<>();

        Direction leftDirection = facing.getClockWise();

        // Block 0 and 1 to the left
        subBlockPositions.add(pos.relative(leftDirection));

        // Block 1 above and 1 to the left
        subBlockPositions.add(pos.above(1).relative(leftDirection));

        // Block 2 above and 1 to the left
        subBlockPositions.add(pos.above(2).relative(leftDirection));

        // Block 1 above
        subBlockPositions.add(pos.above(1));

        // Block 2 above
        subBlockPositions.add(pos.above(2));

        // Block 0, then 1 forward
        subBlockPositions.add(pos.relative(facing.getOpposite()));

        // Block 0 and 1 to the left, then 1 forward
        subBlockPositions.add(pos.relative(leftDirection).relative(facing.getOpposite()));

        // Block 1 above and 1 to the left, then 1 forward
        subBlockPositions.add(pos.above(1).relative(leftDirection).relative(facing.getOpposite()));

        // Block 2 above and 1 to the left, then 1 forward
        subBlockPositions.add(pos.above(2).relative(leftDirection).relative(facing.getOpposite()));

        // Block 1 above, then 1 forward
        subBlockPositions.add(pos.above(1).relative(facing.getOpposite()));

        // Block 2 above, then 1 forward
        subBlockPositions.add(pos.above(2).relative(facing.getOpposite()));


        BlockState subBlockState = ModBlocks.SUIT_CHARGER_SUB.get().defaultBlockState();
        for (BlockPos subPos : subBlockPositions) {
            level.setBlock(subPos, subBlockState, 3);
            if (level.getBlockEntity(subPos) instanceof SuitChargerSubBlockEntity subBlockEntity) {
                subBlockEntity.setMainBlockPos(pos);
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState state, boolean moved) {
        if (!level.isClientSide() && !moved && !blockState.is(state.getBlock())) {
            Direction facing = blockState.getValue(FACING);
            List<BlockPos> subBlockPositions = new ArrayList<>();

            Direction leftDirection = facing.getClockWise();

            // Block 0 and 1 to the left
            subBlockPositions.add(pos.relative(leftDirection));

            // Block 1 above and 1 to the left
            subBlockPositions.add(pos.above(1).relative(leftDirection));

            // Block 2 above and 1 to the left
            subBlockPositions.add(pos.above(2).relative(leftDirection));

            // Block 1 above
            subBlockPositions.add(pos.above(1));

            // Block 2 above
            subBlockPositions.add(pos.above(2));

            // Block 0, then 1 forward
            subBlockPositions.add(pos.relative(facing.getOpposite()));

            // Block 0 and 1 to the left, then 1 forward
            subBlockPositions.add(pos.relative(leftDirection).relative(facing.getOpposite()));

            // Block 1 above and 1 to the left, then 1 forward
            subBlockPositions.add(pos.above(1).relative(leftDirection).relative(facing.getOpposite()));

            // Block 2 above and 1 to the left, then 1 forward
            subBlockPositions.add(pos.above(2).relative(leftDirection).relative(facing.getOpposite()));

            // Block 1 above, then 1 forward
            subBlockPositions.add(pos.above(1).relative(facing.getOpposite()));

            // Block 2 above, then 1 forward
            subBlockPositions.add(pos.above(2).relative(facing.getOpposite()));

            for (BlockPos subPos : subBlockPositions) {
                BlockState stateAtPos = level.getBlockState(subPos);
                if (stateAtPos.getBlock() instanceof SuitChargerSubBlock) {
                    level.removeBlock(subPos, false);
                }
            }
        }

        super.onRemove(blockState, level, pos, state, moved);
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof SuitChargerBlockEntity charger) {
            float charge = charger.getCharge();
            return Math.round(charge / 100f * 15);
        }
        return 0;
    }
}
