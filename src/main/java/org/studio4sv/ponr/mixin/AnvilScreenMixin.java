package org.studio4sv.ponr.mixin;

import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.studio4sv.ponr.util.xpConverter;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {

    @ModifyArg(method = "renderLabels",
            at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"),
            index = 1)
    private Object[] customCost(Object[] cost) {
        if (cost != null && cost.length > 0 && cost[0] instanceof Integer) {
            cost[0] = xpConverter.calculate((Integer) cost[0]);
        }
        return cost;
    }

}