package org.studio4sv.tponr.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.SyncFilterStatusS2CPacket;
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
    private final Set<BlockPos> visited = new HashSet<>();
    private final Queue<BlockPos> queue = new ArrayDeque<>();
    private final Set<BlockPos> affectedBlocks = new HashSet<>();

    private boolean wasSealed = false;
    private boolean sealed = false;
    private boolean finished = false;
    private long lastRescanTime = 0;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (level != null) {
            if (!level.isClientSide) {
                for (Player player : level.players()) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModMessages.sendToPlayer(new SyncFilterStatusS2CPacket(this.getBlockPos(), enabled), serverPlayer);
                    }
                }
            }
        }
        setChanged();
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

    public Queue<BlockPos> getQueue() {
        return queue;
    }

    public Set<BlockPos> getVisited() {
        return visited;
    }

    public void addToQueue(BlockPos pos) {
        queue.add(pos);
    }

    public void addToVisited(BlockPos pos) {
        visited.add(pos);
    }

    public Set<BlockPos> getAffectedBlocks() {
        return affectedBlocks;
    }

    public void addToAffectedBlocks(BlockPos pos) {
        affectedBlocks.add(pos);
    }

    public void reset() {
        queue.clear();
        visited.clear();
        affectedBlocks.clear();
        finished = false;
        sealed = false;
        wasSealed = false;
    }

    public FilterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FILTER_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("enabled", enabled);
        pTag.putBoolean("sealed", sealed);
        pTag.putBoolean("finished", finished);
        pTag.putBoolean("wasSealed", wasSealed);
        pTag.putLong("lastRescanTime", lastRescanTime);

        if (visited.size() < 1000) {
            ListTag visitedList = new ListTag();
            for (BlockPos pos : visited) {
                CompoundTag posTag = new CompoundTag();
                posTag.putInt("x", pos.getX());
                posTag.putInt("y", pos.getY());
                posTag.putInt("z", pos.getZ());
                visitedList.add(posTag);
            }
            pTag.put("visited", visitedList);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        enabled = pTag.getBoolean("enabled");
        sealed = pTag.getBoolean("sealed");
        finished = pTag.getBoolean("finished");
        wasSealed = pTag.getBoolean("wasSealed");
        lastRescanTime = pTag.getLong("lastRescanTime");

        if (pTag.contains("visited", Tag.TAG_LIST)) {
            ListTag visitedList = pTag.getList("visited", Tag.TAG_COMPOUND);
            visited.clear();
            for (int i = 0; i < visitedList.size(); i++) {
                CompoundTag posTag = visitedList.getCompound(i);
                BlockPos pos = new BlockPos(
                        posTag.getInt("x"),
                        posTag.getInt("y"),
                        posTag.getInt("z")
                );
                visited.add(pos);
            }

            if (sealed && finished) {
                affectedBlocks.clear();
                affectedBlocks.addAll(visited);
            }
        } else if (enabled && sealed && finished) {
            finished = false;
            sealed = false;
            queue.add(getBlockPos());
            visited.add(getBlockPos());
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putBoolean("enabled", enabled);
        tag.putBoolean("sealed", sealed);
        tag.putBoolean("finished", finished);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        enabled = tag.getBoolean("enabled");
        sealed = tag.getBoolean("sealed");
        finished = tag.getBoolean("finished");
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