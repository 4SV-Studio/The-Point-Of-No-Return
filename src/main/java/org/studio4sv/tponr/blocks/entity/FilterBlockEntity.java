package org.studio4sv.tponr.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.registers.ModBlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.*;

public class FilterBlockEntity extends BlockEntity implements GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private boolean enabled = false;
    private final Queue<BlockPos> visited = new ArrayDeque<>();
    private final Queue<BlockPos> queue = new ArrayDeque<>();
    private final Queue<BlockPos> affectedBlocks = new ArrayDeque<>();
    private final Queue<BlockPos> particleQueue = new ArrayDeque<>();
    private int scannedBlocks = 0;

    private boolean wasSealed = false;
    private boolean sealed = false;
    private boolean finished = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSealed() {
        return sealed;
    }

    public void setSealed(boolean sealed) {
        this.sealed = sealed;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean wasSealed() {
        return wasSealed;
    }

    public void setWasSealed(boolean wasSealed) {
        this.wasSealed = wasSealed;
    }

    public int getScannedBlocks() {
        return scannedBlocks;
    }

    public void addScannedBlock() {
        this.scannedBlocks++;
    }

    public Queue<BlockPos> getQueue() {
        return queue;
    }

    public Queue<BlockPos> getVisited() {
        return visited;
    }

    public void addToQueue(BlockPos pos) {
        queue.add(pos);
    }

    public void addToVisited(BlockPos pos) {
        visited.add(pos);
    }

    public Queue<BlockPos> getAffectedBlocks() {
        return affectedBlocks;
    }

    public void addToAffectedBlocks(BlockPos pos) {
        affectedBlocks.add(pos);
    }

    public Queue<BlockPos> getParticleQueue() {
        return particleQueue;
    }

    public void resetParticleQueue() {
        particleQueue.clear();
    }

    public void fillParticleQueueInward() {
        List<BlockPos> reversed = new ArrayList<>(visited);
        Collections.reverse(reversed);
        particleQueue.addAll(reversed);
    }

    public void reset() {
        scannedBlocks = 0;
        queue.clear();
        visited.clear();
        finished = false;
    }

    public FilterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FILTER_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("enabled", enabled);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("enabled")) {
            enabled = pTag.getBoolean("enabled");
        } else {
            enabled = false;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putBoolean("enabled", enabled);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        if (tag.contains("enabled")) {
            enabled = tag.getBoolean("enabled");
        } else {
            enabled = false;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<FilterBlockEntity> state) {
        AnimationController<?> controller = state.getController();

        controller.setAnimation(RawAnimation.begin().then("on", Animation.LoopType.LOOP));

        if (enabled) {
            controller.setAnimationSpeed(1);
        } else {
            controller.setAnimationSpeed(0);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
