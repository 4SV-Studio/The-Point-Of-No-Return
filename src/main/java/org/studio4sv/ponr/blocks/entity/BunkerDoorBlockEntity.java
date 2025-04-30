package org.studio4sv.ponr.blocks.entity;

import org.studio4sv.ponr.registers.ModBlockEntities;
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

    public void toggleOpen() {
        this.open = !this.open;
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
