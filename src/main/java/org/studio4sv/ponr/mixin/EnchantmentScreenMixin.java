package org.studio4sv.ponr.mixin;

import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.studio4sv.ponr.util.xpConverter;

@Mixin(EnchantmentScreen.class)
public class EnchantmentScreenMixin {

    @ModifyArg(method = "render",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"),
            index = 1)
    private Object[] customCost(Object[] cost) {
        Object[] modified = new Object[cost.length];
        for (int i = 0; i < cost.length; i++) {
            if (cost[i] instanceof Integer) {
                modified[i] = xpConverter.calculate((Integer) cost[i]);
            } else {
                modified[i] = cost[i];
            }
        }
        return modified;
    }

    @ModifyArg(method = "renderBg",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;width(Ljava/lang/String;)I"),
            index = 0)
    private @Nullable String customButtonVarCost(@Nullable String cost) {
        if (cost != null) {
            return String.valueOf(xpConverter.calculate(Integer.parseInt(cost)));
        } else {
            return null;
        }
    }

    @ModifyArg(method = "renderBg",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I"),
            index = 1)
    private @Nullable String customButtonCost(@Nullable String cost) {
        if (cost != null) {
            return String.valueOf(xpConverter.calculate(Integer.parseInt(cost)));
        } else {
            return null;
        }
    }

}
