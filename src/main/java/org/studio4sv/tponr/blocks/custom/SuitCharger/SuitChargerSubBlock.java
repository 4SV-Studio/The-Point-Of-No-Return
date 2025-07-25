package org.studio4sv.tponr.blocks.custom.SuitCharger;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerBlockEntity;
import org.studio4sv.tponr.blocks.entity.SuitCharger.SuitChargerSubBlockEntity;
import org.studio4sv.tponr.items.HazmatSuitPackItem;

public class SuitChargerSubBlock extends BaseEntityBlock {
    public SuitChargerSubBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SuitChargerSubBlockEntity(pPos, pState);
    }

    @Override
    public @NotNull InteractionResult use(BlockState pBlockState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pPlayer.getItemInHand(pHand).getItem() instanceof HazmatSuitPackItem) {
                BlockEntity subBlockEntity = pLevel.getBlockEntity(pPos);
                if (subBlockEntity instanceof SuitChargerSubBlockEntity) {
                    if (pLevel.getBlockEntity(((SuitChargerSubBlockEntity) subBlockEntity).getMainBlockPos()) instanceof SuitChargerBlockEntity mainBlockEntity) {
                        if (mainBlockEntity.getStoredItem().isEmpty()) {
                            mainBlockEntity.setStoredItem(pPlayer.getItemInHand(pHand).copyAndClear());
                        }
                    }
                }
            } else if (pPlayer.getItemInHand(pHand).isEmpty()) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if (blockEntity instanceof SuitChargerSubBlockEntity) {
                    if (pLevel.getBlockEntity(((SuitChargerSubBlockEntity) blockEntity).getMainBlockPos()) instanceof SuitChargerBlockEntity mainBlockEntity) {
                        pPlayer.setItemInHand(pHand, mainBlockEntity.getStoredItem());
                        mainBlockEntity.setStoredItem(ItemStack.EMPTY);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);

        if (!pState.is(pNewState.getBlock()) && !pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SuitChargerSubBlockEntity subEntity) {
                BlockPos mainBlockPos = subEntity.getMainBlockPos();
                if (mainBlockPos != null) {
                    BlockState mainBlockState = pLevel.getBlockState(mainBlockPos);
                    if (mainBlockState.getBlock() instanceof SuitChargerBlock) {
                        pLevel.removeBlock(mainBlockPos, false);
                    }
                }
            }
        }
    }
}
