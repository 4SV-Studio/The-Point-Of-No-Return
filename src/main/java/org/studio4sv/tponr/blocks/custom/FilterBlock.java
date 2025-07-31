package org.studio4sv.tponr.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.SyncFilterStatusS2CPacket;
import org.studio4sv.tponr.util.SafeAreaTracker;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FilterBlock extends BaseEntityBlock {
    private static final int MAX_BLOCKS = 5000; // TODO: implement config here
    private static final int BLOCKS_PER_TICK = 100;
    private static final int RESCAN_INTERVAL = 10;

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
                emitParticles(pLevel, be, ParticleTypes.SMOKE);
                for (BlockPos pos : be.getVisited()) {
                    SafeAreaTracker.removeSafeArea(pos);
                }
                be.reset();
            }
            return;
        }

        if (be.isSealed() && be.isFinished() && pLevel.getGameTime() % RESCAN_INTERVAL == 0) {
            if (hasAreaChanged(pLevel, pPos, be)) {
                disableFilter(pLevel, be);
                return;
            }
        }

        for (int i = 0; i < BLOCKS_PER_TICK && !be.getQueue().isEmpty() && !be.isFinished(); i++) {

            if (be.getVisited().size() >= MAX_BLOCKS) {
                be.setSealed(false);
                be.setFinished(true);
                be.getAffectedBlocks().clear();
                return;
            }

            BlockPos current = be.getQueue().poll();
            if (current == null) return;

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
            be.getAffectedBlocks().clear();
            for (BlockPos pos : be.getVisited()) {
                be.addToAffectedBlocks(pos);
            }
        }

        if (be.isFinished() && be.isSealed() && !be.wasSealed()) {
            for (BlockPos pos : be.getVisited()) {
                SafeAreaTracker.addSafeArea(pos);
            }
            emitParticles(pLevel, be, ParticleTypes.CLOUD);
        }

        if (be.isFinished() && be.getAffectedBlocks().size() != be.getVisited().size()) {
            be.setEnabled(false);
            be.setFinished(true);
            be.reset();
            emitParticles(pLevel, be, ParticleTypes.SMOKE);
        }

        be.setWasSealed(be.isSealed());

        if (be.isEnabled()) {
            pLevel.scheduleTick(pPos, pState.getBlock(), 1);
        }
    }

    private boolean hasAreaChanged(ServerLevel level, BlockPos filterPos, FilterBlockEntity be) {
        Set<BlockPos> newVisited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();

        queue.add(filterPos);
        newVisited.add(filterPos);

        while (!queue.isEmpty() && newVisited.size() < MAX_BLOCKS) {
            BlockPos current = queue.poll();

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (newVisited.contains(neighbor)) continue;

                BlockState state = level.getBlockState(neighbor);
                newVisited.add(neighbor);

                if (!state.is(Blocks.IRON_BLOCK)) { // TODO: implement tag here
                    queue.add(neighbor);
                }
            }
        }

        Set<BlockPos> originalVisited = new HashSet<>(be.getVisited());
        if (newVisited.size() != originalVisited.size()) {
            return true;
        }

        return !newVisited.equals(originalVisited);
    }

    private void disableFilter(ServerLevel level, FilterBlockEntity be) {
        for (BlockPos pos : be.getVisited()) {
            SafeAreaTracker.removeSafeArea(pos);
        }

        be.setEnabled(false);
        be.setFinished(true);
        be.setSealed(false);
        be.setWasSealed(true);

        emitParticles(level, be, ParticleTypes.SMOKE);

        be.reset();
    }

    private void emitParticles(ServerLevel level, FilterBlockEntity be, ParticleOptions particle) {
        for (BlockPos pos : be.getAffectedBlocks()) {
            level.sendParticles(particle, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    1, 0.3, 0.3, 0.3, 0.01);
        }
    }
}