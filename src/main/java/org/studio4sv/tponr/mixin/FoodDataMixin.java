package org.studio4sv.tponr.mixin;

import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.studio4sv.tponr.client.hud.HungerHud;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    @Unique
    private static final int newFoodLevel = 60;

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 20))
    private static int modifyInitFoodLevel(int original) {
        return newFoodLevel;
    }

    @ModifyConstant(method = "eat(IF)V", constant = @Constant(intValue = 20))
    private int modifyEatMaxFoodLevel(int original) {
        return newFoodLevel;
    }

    @ModifyConstant(method = "needsFood", constant = @Constant(intValue = 20))
    private int modifyNeedsFoodThreshold(int constant) {
        return newFoodLevel;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void boostrapHungerHud(CallbackInfo ci) {
        int foodLevel = ((FoodData) (Object) this).getFoodLevel();
        HungerHud.setHungerStage((int)(6 * foodLevel / (float)newFoodLevel));
    }
}