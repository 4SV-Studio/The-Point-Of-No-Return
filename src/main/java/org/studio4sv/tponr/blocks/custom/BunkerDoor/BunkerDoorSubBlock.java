package org.studio4sv.tponr.blocks.custom.BunkerDoor;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.block.BaseEntityBlock;
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import org.studio4sv.tponr.blocks.entity.BunkerDoor.BunkerDoorSubBlockEntity;

import javax.annotation.Nullable;

public class BunkerDoorSubBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SOLID_SHAPE = Shapes.block();
    private static final VoxelShape EMPTY_SHAPE = Shapes.empty();

    public BunkerDoorSubBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BunkerDoorSubBlockEntity(blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos mainBlockPos = getMainBlock(level, pos);
        if (mainBlockPos != null) {
            BlockEntity blockEntity = level.getBlockEntity(mainBlockPos);
            if (blockEntity instanceof BunkerDoorBlockEntity) {
                ((BunkerDoorBlockEntity) blockEntity).toggleOpen();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!blockState.is(newState.getBlock()) && !level.isClientSide()) {
            BlockPos mainBlockPos = getMainBlock(level, pos);
            if (mainBlockPos != null) {
                BlockState mainBlockState = level.getBlockState(mainBlockPos);
                if (mainBlockState.getBlock() instanceof BunkerDoorBlock) {
                    level.removeBlock(mainBlockPos, false);
                }
            }
        }
        super.onRemove(blockState, level, pos, newState, isMoving);
    }

    @Nullable
    private BlockPos getMainBlock(Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BunkerDoorSubBlockEntity subEntity) {
            return subEntity.getMainBlockPos();
        }
        return null;
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SOLID_SHAPE;
    }
    
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (level instanceof Level) {
            BlockPos mainBlockPos = getMainBlock((Level) level, pos);
            if (mainBlockPos != null) {
                BlockEntity blockEntity = level.getBlockEntity(mainBlockPos);
                if (blockEntity instanceof BunkerDoorBlockEntity) {
                    return ((BunkerDoorBlockEntity) blockEntity).isOpen() ? EMPTY_SHAPE : SOLID_SHAPE;
                }
            }
        }
        return SOLID_SHAPE;
    }
}