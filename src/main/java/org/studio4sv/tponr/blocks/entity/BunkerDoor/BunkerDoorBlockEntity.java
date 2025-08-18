package org.studio4sv.tponr.blocks.entity.BunkerDoor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.BunkerDoorOpenSyncS2CPacket;
import org.studio4sv.tponr.registers.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class BunkerDoorBlockEntity extends BlockEntity implements GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private boolean open = false;
    private boolean changed = false;

    public BunkerDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BUNKER_DOOR_ENTITY.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putBoolean("open", open);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("open")) {
            open = nbt.getBoolean("open");
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putBoolean("open", this.open);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        if (tag.contains("open")) {
            this.open = tag.getBoolean("open");
            this.changed = true;
        }
    }

    public void toggleOpen() {
        this.open = !this.open;
        this.changed = true;
        this.setChanged();

        if (level != null && !level.isClientSide) {
            for (ServerPlayer player : ((ServerLevel) level).players()) {
                ModMessages.sendToPlayer(new BunkerDoorOpenSyncS2CPacket(this.getBlockPos(), this.open), player);
            }
        }
    }

    public void setOpen(boolean open) {
        this.open = open;
        this.changed = true;
        this.setChanged();
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<BunkerDoorBlockEntity> state) {
        AnimationController<?> controller = state.getController();

        if ((this.changed) || (!controller.getAnimationState().equals(AnimationController.State.RUNNING) && !controller.getAnimationState().equals(AnimationController.State.PAUSED))) {
            this.changed = false;
            controller.forceAnimationReset();
            controller.setAnimation(
                    RawAnimation.begin().then(this.open ? "open" : "close", Animation.LoopType.HOLD_ON_LAST_FRAME)
            );
        }

        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
}
