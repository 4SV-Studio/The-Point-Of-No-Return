package org.studio4sv.tponr.mixin;

import dev.gigaherz.hudcompass.client.HudOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.studio4sv.tponr.armor.HazmatSuitItem;

@Mixin(HudOverlay.class)
public class HudOverlayMixin {
    @ModifyArg(method = "render", at = @At(
            value = "INVOKE",
            target = "Ldev/gigaherz/hudcompass/client/HudOverlay;drawCardinalDirection(Lnet/minecraft/client/gui/GuiGraphics;FFILjava/lang/String;)V"),
            index = 4,
            remap = false
    )
    private String customCardinalDirection(String cardinalDirection) {
        return Component.translatable("gui.hudcompass.direction." + cardinalDirection).getString();
    }

    /**
     * @author Lemonnik6484
     * @reason Toggle rendering according to hazmat suit
     */
    @Overwrite(remap = false)
    private boolean canRender() {
        if (Minecraft.getInstance().player != null) {
            return Minecraft.getInstance().player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HazmatSuitItem;
        } else {
            return false;
        }
    }

    @ModifyVariable(
            method = "drawPoi",
            at = @At("STORE"),
            ordinal = 0,
            remap = false
    )
    private double customRenderExpression(double original) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCrouching()) {
            return Double.MAX_VALUE; // fadeSqr > distance2
        } else {
            return -1.0; // fadeSqr < distance2
        }
    }

    @ModifyArg(
            method = "drawCardinalDirection",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/gigaherz/hudcompass/client/HudOverlay;fillRect(Lnet/minecraft/client/gui/GuiGraphics;FFFFI)V"
            ),
            index = 5,
            remap = false
    )
    private int modifyBarAlpha(int original) {
        return 0x3F70B8FC;
    }

    @ModifyArg(
            method = "drawCardinalDirection",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/gigaherz/hudcompass/client/HudOverlay;drawCenteredShadowString(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;Ljava/lang/String;FFI)V"
            ),
            index = 5,
            remap = false
    )
    private int modifyTextColor(int original) {
        return 0x70B8FC;
    }
}
