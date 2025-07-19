package org.studio4sv.tponr.mixin;

import ichttt.mods.firstaid.client.gui.GuiHealthScreen;
import net.minecraft.client.gui.components.AbstractButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiHealthScreen.class)
public class GuiHealthScreenMixin {
    @Shadow private AbstractButton body;

    @ModifyConstant(
            method = "init",
            constant = @Constant(intValue = 4)
    )
    private static int modifyOne(int original) {
        return 9;
    }

    @ModifyConstant(
            method = "init",
            constant = @Constant(intValue = 8)
    )
    private static int modifyTwo(int original) {
        return 13;
    }

    @ModifyConstant(
            method = "init",
            constant = @Constant(intValue = 33)
    )
    private static int modifyThree(int original) {
        return 38;
    }

    @ModifyConstant(
            method = "init",
            constant = @Constant(intValue = 58)
    )
    private static int modifyFour(int original) {
        return 63;
    }

    @ModifyConstant(
            method = "init",
            constant = @Constant(intValue = 83)
    )
    private static int modifyFive(int original) {
        return 88;
    }

    @ModifyConstant(
            method = "init",
            constant = @Constant(intValue = 199)
    )
    private static int modifySix(int original) {
        return 194;
    }

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lichttt/mods/firstaid/client/gui/GuiHealthScreen;drawHealth(Lnet/minecraft/client/gui/GuiGraphics;Lichttt/mods/firstaid/api/damagesystem/AbstractDamageablePart;ZI)V"
            ),
            index = 3,
            remap = false
    )
    private int modifySeven(int original) {
        return switch (original) {
            case 14 -> 19;
            case 39 -> 44;
            case 64 -> 69;
            case 89 -> 94;
            default -> original;
        };
    }

    @ModifyConstant(
            method = "render",
            constant = @Constant(intValue = 3)
    )
    private static int modifyEight(int original) {
        return 8;
    }

    @ModifyConstant(
            method = "render",
            constant = @Constant(intValue = 19)
    )
    private static int modifyNine(int original) {
        return 28;
    }

    @ModifyConstant(
            method = "drawHealth",
            constant = @Constant(intValue = 57),
            remap = false
    )
    private static int modifyTen(int original) {
        return 62;
    }

    @ModifyConstant(
            method = "getRightOffset",
            constant = @Constant(intValue = 2),
            remap = false
    )
    private static int modifyEleven(int original) {
        return 7;
    }
}
