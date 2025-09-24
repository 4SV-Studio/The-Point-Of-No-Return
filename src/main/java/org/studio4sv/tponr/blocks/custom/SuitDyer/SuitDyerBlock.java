package org.studio4sv.tponr.blocks.custom.SuitDyer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerBlockEntity;
import org.studio4sv.tponr.blocks.entity.SuitDyer.SuitDyerSubBlockEntity;
import org.studio4sv.tponr.client.gui.SuitDyerGui.SuitDyerMenu;
import org.studio4sv.tponr.registers.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class SuitDyerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public SuitDyerBlock(Properties pProperties) {
        super(pProperties);
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
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SuitDyerBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            MenuProvider containerProvider = new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Suit Dyer");
                }

                @Override
                public @NotNull AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
                    return new SuitDyerMenu(windowId, pos, inv, inv.player);
                }
            };

            NetworkHooks.openScreen(serverPlayer, containerProvider, buf -> buf.writeBlockPos(pos));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(blockState, level, pos, oldState, moved);

        if (level.isClientSide()) {
            return;
        }

        Direction facing = blockState.getValue(FACING);
        List<BlockPos> subBlockPositions = new ArrayList<>();

        Direction rightDirection = facing.getCounterClockWise();

        // Block 0 and 1 to the right
        subBlockPositions.add(pos.relative(rightDirection));

        // Block 1 above and 1 to the right
        subBlockPositions.add(pos.above(1).relative(rightDirection));

        BlockState subBlockState = ModBlocks.SUIT_DYER_SUB.get().defaultBlockState();
        for (BlockPos subPos : subBlockPositions) {
            level.setBlock(subPos, subBlockState, 3);
            BlockEntity be = level.getBlockEntity(subPos);
            if (be instanceof SuitDyerSubBlockEntity subEntity) {
                subEntity.setMainBlockPos(pos);
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos pos, BlockState state, boolean moved) {
        if (!level.isClientSide() && !moved && !blockState.is(state.getBlock())) {
            Direction facing = blockState.getValue(FACING);
            Direction rightDirection = facing.getCounterClockWise();

            List<BlockPos> subBlockPositions = new ArrayList<>();

            // Block 0 and 1 to the right
            subBlockPositions.add(pos.relative(rightDirection));

            // Block 1 above and 1 to the right
            subBlockPositions.add(pos.above(1).relative(rightDirection));

            for (BlockPos subPos : subBlockPositions) {
                BlockState stateAtPos = level.getBlockState(subPos);
                if (stateAtPos.getBlock() instanceof SuitDyerSubBlock) {
                    level.removeBlock(subPos, false);
                }
            }
        }

        super.onRemove(blockState, level, pos, state, moved);
    }
}
