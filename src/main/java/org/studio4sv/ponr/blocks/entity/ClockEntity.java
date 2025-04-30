package org.studio4sv.ponr.blocks.entity;

import org.studio4sv.ponr.registers.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class ClockEntity extends BlockEntity implements GeoBlockEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public ClockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<ClockEntity> clockEntityAnimationState) {
        clockEntityAnimationState.getController().forceAnimationReset();
        
        boolean isDay = false;
        if (this.getLevel() != null) {
            long timeOfDay = this.getLevel().getDayTime() % 24000;
            isDay = timeOfDay >= 0 && timeOfDay < 13000;
        }
        
        clockEntityAnimationState.getController().setAnimation(RawAnimation.begin().then(isDay ? "day" : "night", Animation.LoopType.HOLD_ON_LAST_FRAME));
        
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