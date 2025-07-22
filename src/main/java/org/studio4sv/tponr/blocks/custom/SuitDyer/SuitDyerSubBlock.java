package org.studio4sv.tponr.blocks.custom.SuitDyer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerSubBlockEntity;

public class SuitDyerSubBlock extends BaseEntityBlock {
    public SuitDyerSubBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SuitDyerSubBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!blockState.is(newState.getBlock()) && !level.isClientSide() && !isMoving) {
            BlockPos mainBlockPos;
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SuitDyerSubBlockEntity subEntity) {
                mainBlockPos = subEntity.getMainBlockPos();
            } else {
                mainBlockPos = null;
            }
            if (mainBlockPos != null) {
                BlockState mainBlockState = level.getBlockState(mainBlockPos);
                if (mainBlockState.getBlock() instanceof SuitDyerBlock) {
                    level.removeBlock(mainBlockPos, false);
                }
            }
        }
        super.onRemove(blockState, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
