package org.studio4sv.tponr.items;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.studio4sv.tponr.client.item.FakeSuitItem.FakeSuitItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.function.Consumer;

public class FakeSuitItem extends Item implements GeoItem, DyeableLeatherItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public FakeSuitItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasCustomColor(@NotNull ItemStack stack) {
        return DyeableLeatherItem.super.hasCustomColor(stack);
    }

    @Override
    public int getColor(@NotNull ItemStack stack) {
        return DyeableLeatherItem.super.hasCustomColor(stack) ? DyeableLeatherItem.super.getColor(stack) : 0xFFFFFF;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<FakeSuitItem> animationState) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FakeSuitItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new FakeSuitItemRenderer();
                }
                return this.renderer;
            }
        });
    }
}
