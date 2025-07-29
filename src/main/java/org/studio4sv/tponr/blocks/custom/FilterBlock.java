package org.studio4sv.tponr.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.blocks.entity.FilterBlockEntity;

public class FilterBlock extends BaseEntityBlock {
    public FilterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FilterBlockEntity(blockPos, blockState);
    }
}
