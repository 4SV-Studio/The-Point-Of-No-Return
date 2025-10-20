package org.studio4sv.tponr.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.blocks.entity.ClockEntity;
import org.studio4sv.tponr.blocks.entity.SuitStandEntity;
import org.studio4sv.tponr.items.HazmatSuitPackItem;

public class SuitStandBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public SuitStandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SuitStandEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pPlayer.getItemInHand(pHand).getItem() instanceof HazmatSuitPackItem) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof SuitStandEntity mainBlockEntity) {
                    if (mainBlockEntity.getStoredItem().isEmpty()) {
                        mainBlockEntity.setStoredItem(pPlayer.getItemInHand(pHand).copyAndClear());
                    }
                }
            } else if (pPlayer.getItemInHand(pHand).isEmpty()) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof SuitStandEntity mainBlockEntity) {
                    pPlayer.setItemInHand(pHand, mainBlockEntity.getStoredItem());
                    mainBlockEntity.setStoredItem(ItemStack.EMPTY);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 4, 16);
    }
}