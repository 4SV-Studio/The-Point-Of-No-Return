package org.studio4sv.tponr.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.studio4sv.tponr.blocks.entity.FilterBlockEntity;
import org.studio4sv.tponr.util.SafeAreaTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterBlock extends BaseEntityBlock {
    private static final int MAX_BLOCKS = 5000; // TODO: implement config here
    private static final int BLOCKS_PER_TICK = 100;

    public FilterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FilterBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof FilterBlockEntity be)) return InteractionResult.FAIL;

        boolean enabled = be.isEnabled();
        be.setEnabled(!enabled);
        if (!pLevel.isClientSide) {
            if (!enabled) {
                be.reset();
                be.addToQueue(pPos);
                be.addToVisited(pPos);
                be.addScannedBlock();
            } else {
                be.setFinished(true);
                be.setSealed(false);
                be.setWasSealed(true);
            }
        }
        pLevel.scheduleTick(pPos, pState.getBlock(), 1);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);

        if (!pState.is(pNewState.getBlock()) && !pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FilterBlockEntity be) {
                for (BlockPos pos : be.getVisited()) {
                    SafeAreaTracker.removeSafeArea(pos);
                }
            }
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.isClientSide) return;
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof FilterBlockEntity be)) return;

        if (!be.isEnabled()) {
            if (be.wasSealed()) {
                emitParticlesInward(pLevel, be, pPos, ParticleTypes.SMOKE);
                for (BlockPos pos : be.getVisited()) {
                    SafeAreaTracker.removeSafeArea(pos);
                }
                be.reset();
            }
            return;
        }

        for (int i = 0; i < BLOCKS_PER_TICK && !be.getQueue().isEmpty() && !be.isFinished(); i++) {
            if (be.getScannedBlocks() >= MAX_BLOCKS) {
                be.setSealed(false);
                be.setFinished(true);
                return;
            }

            BlockPos current = be.getQueue().poll();
            if (current == null) return;
            be.addScannedBlock();

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (be.getVisited().contains(neighbor)) continue;

                BlockState state = pLevel.getBlockState(neighbor);

                be.addToVisited(neighbor);
                if (!(state.is(Blocks.IRON_BLOCK))) be.addToQueue(neighbor); // TODO: implement tag here
            }
        }
        if (be.getQueue().isEmpty()) {
            be.setSealed(true);
            be.setFinished(true);
        }

        if (be.isFinished() && be.isSealed() && !be.wasSealed()) {
            for (BlockPos pos : be.getVisited()) {
                SafeAreaTracker.addSafeArea(pos);
            }
            emitParticlesOutward(pLevel, be, ParticleTypes.CLOUD);
        }

        if (be.isFinished() && !be.isSealed() && be.wasSealed()) {
            for (BlockPos pos : be.getVisited()) {
                SafeAreaTracker.removeSafeArea(pos);
            }
            emitParticlesInward(pLevel, be, pPos, ParticleTypes.SMOKE);
        }

        be.setWasSealed(be.isSealed());

        if (be.isEnabled()) {
            pLevel.scheduleTick(pPos, pState.getBlock(), 1);
        }
    }

    private void emitParticlesOutward(ServerLevel level, FilterBlockEntity be, ParticleOptions particle) {
        for (BlockPos pos : be.getVisited()) {
            level.sendParticles(particle, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    1, 0.3, 0.3, 0.3, 0.01);
        }
    }

    private void emitParticlesInward(ServerLevel level, FilterBlockEntity be, BlockPos center, ParticleOptions particle) {
        List<BlockPos> visited = new ArrayList<>(be.getVisited());
        Collections.reverse(visited);
        for (BlockPos pos : visited) {
            level.sendParticles(particle, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    1, (center.getX() - pos.getX()) * 0.1, (center.getY() - pos.getY()) * 0.1, (center.getZ() - pos.getZ()) * 0.1, 0.01);
        }
    }
}
