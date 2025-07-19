package org.studio4sv.tponr.blocks.custom.BunkerDoor;

import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorBlockEntity;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorSubBlockEntity;
import org.studio4sv.tponr.registers.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BunkerDoorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SOLID_SHAPE = Shapes.block();
    private static final VoxelShape EMPTY_SHAPE = Shapes.empty();

    public BunkerDoorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        return new BunkerDoorBlockEntity(blockPos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BunkerDoorBlockEntity) {
            ((BunkerDoorBlockEntity) blockEntity).toggleOpen();
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
        
        // Get the direction vectors for right and left relative to facing
        Direction rightDirection = facing.getClockWise();
        Direction leftDirection = facing.getCounterClockWise();

        // Block 0 and 1 to the left
        subBlockPositions.add(pos.relative(leftDirection));

        // Block 0 and 1 to the right
        subBlockPositions.add(pos.relative(rightDirection));
        
        // Block 1 above and 1 to the right
        subBlockPositions.add(pos.above(1).relative(rightDirection));
        
        // Block 1 above and 1 to the left
        subBlockPositions.add(pos.above(1).relative(leftDirection));

        // Block 2 above and 1 to the right
        subBlockPositions.add(pos.above(2).relative(rightDirection));

        // Block 2 above and 1 to the left
        subBlockPositions.add(pos.above(2).relative(leftDirection));

        // Block 1
        subBlockPositions.add(pos.above(1));

        // Block 2
        subBlockPositions.add(pos.above(2));

        BlockState subBlockState = ModBlocks.BUNKER_DOOR_SUB.get().defaultBlockState();
        for (BlockPos subPos : subBlockPositions) {
            level.setBlock(subPos, subBlockState, 3);
            BlockEntity be = level.getBlockEntity(subPos);
            if (be instanceof BunkerDoorSubBlockEntity subEntity) {
                subEntity.setMainBlockPos(pos);
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState state, boolean moved) {
        if (!level.isClientSide() && !moved && !blockState.is(state.getBlock())) {
            Direction facing = blockState.getValue(FACING);
            Direction rightDirection = facing.getClockWise();
            Direction leftDirection = facing.getCounterClockWise();
            
            List<BlockPos> subBlockPositions = new ArrayList<>();

            // Block 0 and 1 to the left
            subBlockPositions.add(pos.relative(leftDirection));

            // Block 0 and 1 to the right
            subBlockPositions.add(pos.relative(rightDirection));

            // Block 1 above and 1 to the right
            subBlockPositions.add(pos.above(1).relative(rightDirection));

            // Block 1 above and 1 to the left
            subBlockPositions.add(pos.above(1).relative(leftDirection));

            // Block 2 above and 1 to the right
            subBlockPositions.add(pos.above(2).relative(rightDirection));

            // Block 2 above and 1 to the left
            subBlockPositions.add(pos.above(2).relative(leftDirection));

            // Block 1
            subBlockPositions.add(pos.above(1));

            // Block 2
            subBlockPositions.add(pos.above(2));

            for (BlockPos subPos : subBlockPositions) {
                BlockState stateAtPos = level.getBlockState(subPos);
                if (stateAtPos.getBlock() instanceof BunkerDoorSubBlock) {
                    level.removeBlock(subPos, false);
                }
            }
        }
        
        super.onRemove(blockState, level, pos, state, moved);
    }

    private boolean isDoorOpen(BlockGetter level, BlockPos pos) {
        if (level instanceof Level) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BunkerDoorBlockEntity) {
                return ((BunkerDoorBlockEntity) blockEntity).isOpen();
            }
        }
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SOLID_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        boolean isDoorOpen = isDoorOpen(level, pos);

        return isDoorOpen ? EMPTY_SHAPE : SOLID_SHAPE;
    }
}